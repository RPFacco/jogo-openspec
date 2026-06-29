## Context

The codebase has ~25 classes built over multiple iterations. Five quality issues have been identified:

1. **Public fields**: `GameState`, `Player`, and all `data/` entities expose fields as `public`, bypassing encapsulation. Most critically, `GameState.completedQuizzes` is a raw `Set<String>` — any caller can `clear()` or `add()` without going through the owning class.
2. **Duplicated constants**: `MAP_WIDTH = 1920` / `MAP_HEIGHT = 1080` repeated in `GameplayScreen`, `ProjectileSystem`, `MainMenuScreen`, `QuizScreen`.
3. **Package boundary**: `EnemyEntity` lives in `data/` but imports `game.MovementStrategy` and `game.ShootPattern`. Other `data/` entities (`NpcEntity`, `MoveEntity`, `ProjectileEntity`) have no such dependencies.
4. **Inconsistent caching**: `EnemyLoader`, `NpcLoader`, `QuizLoader` use static cache. `MapLoader` reads from disk every call.
5. **God class**: `GameplayScreen` handles input, HUD rendering, map transitions, and orchestrates all game systems in a single class.

## Goals / Non-Goals

**Goals:**
- Encapsulate all entity fields with private visibility + getters/setters
- Eliminate duplicated map dimension constants
- Move `EnemyEntity` to the `game` package
- Add static caching to `MapLoader` matching the existing pattern
- Extract `InputHandler` and `HudRenderer` from `GameplayScreen`
- Zero behavior change — all refactoring is mechanically equivalent

**Non-Goals:**
- Changing the `ShootPattern` interface or other public APIs
- Adding new features or gameplay behavior
- Performance optimization beyond the caching fix
- Full MVC/MVP architecture — just targeted extraction

## Decisions

### 1. Encapsulation approach

All entity fields become `private` with standard Java getters. For mutable fields, setters are provided. For `GameState.completedQuizzes`, domain-specific methods replace direct collection access:

```java
// GameState
public int getLives() { return lives; }
public void takeDamage() { lives--; }  // encapsulates the decrement + zero check
public void reset() { lives = 5; completedQuizzes.clear(); }
public boolean isCompleted(String quizId) { return completedQuizzes.contains(quizId); }
public void markCompleted(String quizId) { completedQuizzes.add(quizId); }
public int getCompletedCount() { return completedQuizzes.size(); }
```

For `Player`, getters for position are provided. Movement is already encapsulated via `setTarget()` + `update()` — external mutation of `x`/`y` is only needed by `transitionTo()` and `clampPlayerToBounds()`, which stay as package-private or public methods.

For data entities (`ProjectileEntity`, `NpcEntity`, `MoveEntity`, `MapData`, `MapEntry`, `QuizData`), fields become private with getters/setters. The number of access sites is small for most (some are only written once by loaders and read by systems).

Alternatives considered:
- **Lombok `@Data`** — reduces boilerplate but adds a dependency only for getters/setters; not worth it for ~9 small classes
- **Records (Java 16+)** — perfect for `ProjectileEntity`, `NpcEntity`, `MoveEntity` but incompatible with the mutable `alive` flag on `ProjectileEntity` and libGDX `Array` serialization patterns

### 2. GameConfig class

```java
package com.rpfacco.oopquest.game;

public class GameConfig {
    public static final float MAP_WIDTH = 1920;
    public static final float MAP_HEIGHT = 1080;

    private GameConfig() {}
}
```

Replace all `MAP_WIDTH`/`MAP_HEIGHT`/`SCREEN_WIDTH`/`SCREEN_HEIGHT` references with `GameConfig.MAP_WIDTH`/`GameConfig.MAP_HEIGHT`. Sites:
- `GameplayScreen.java:30-31` — replace fields with `GameConfig` reference
- `ProjectileSystem.java:11-12` — replace fields with `GameConfig` reference
- `MainMenuScreen.java:15-16` — use `GameConfig` instead of local constants
- `QuizScreen.java:21-22` — use `GameConfig` instead of local constants

### 3. EnemyEntity package move

Move `EnemyEntity.java` from `game/data/` to `game/`. Update imports in:
- `EnemyLoader.java` — `game.data.EnemyEntity` → `game.EnemyEntity`
- `EnemySystem.java` — `game.data.EnemyEntity` → `game.EnemyEntity`
- `BurstPattern.java` — `game.data.EnemyEntity` → `game.EnemyEntity`

The class body stays unchanged (fields are being encapsulated in point 1).

### 4. MapLoader caching

Add a `private static MapData cache` field to `MapLoader`, same pattern as `EnemyLoader`:

```java
private static MapData cache;

public static MapData load() {
    if (cache != null) return cache;
    // ... existing load logic ...
    cache = data;
    return cache;
}
```

### 5. GameplayScreen extraction

**InputHandler**: A new class responsible for touch/click input:
- Constructor receives `Viewport` and `Player` (as interfaces or concrete)
- `handle()` method returns an `InputResult` (or calls callbacks)
- ESC key handling stays in `GameplayScreen` (it's a screen lifecycle concern)

Given the small surface area, a simpler approach: extract as `handleInput(Viewport viewport, Player player)` static method. But since the user wants a separate **class**, I'll create:

```java
package com.rpfacco.oopquest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputHandler {
    private final Viewport viewport;
    private final Vector3 touchPos = new Vector3();

    public InputHandler(Viewport viewport) {
        this.viewport = viewport;
    }

    /** Returns the unprojected touch position, or null if no touch this frame. */
    public Vector3 handleTouch() {
        if (!Gdx.input.justTouched()) return null;
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(touchPos);
        return touchPos;
    }

    public boolean isEscPressed() {
        return Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE);
    }
}
```

**HudRenderer**: A class for HUD elements:
```java
package com.rpfacco.oopquest.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HudRenderer {
    private final SpriteBatch batch;
    private final BitmapFont font;

    public HudRenderer(SpriteBatch batch, BitmapFont font) {
        this.batch = batch;
        this.font = font;
    }

    public void render(GameState gameState) {
        font.draw(batch, "Lives: " + gameState.getLives(), 20, GameConfig.MAP_HEIGHT - 20);
    }
}
```

Both are wired in `GameplayScreen.show()` and used in `render()`.

Alternatives considered:
- **Inline static methods** — less files but not a real extraction
- **Interface-based handlers** — over-engineering for one implementation each

## Risks / Trade-offs

- **[High] Breakage risk from encapsulation** → Every field access site across ~20 files must be updated. A missed site will fail at compile time (safe), but the sheer number of changes increases review burden. Mitigation: group changes by file, build after every 2-3 files.
- **[Medium] EnemyEntity package move breaks imports invisibly** → Git won't detect the file move as a rename on Windows. The old `data/EnemyEntity.java` will show as deleted, `game/EnemyEntity.java` as new. Mitigation: commit separately for clarity.
- **[Low] InputHandler creates circular update flow** → If `handleTouch()` needs to do more than just return a position (e.g., boundary clamping), it needs access to player state. Mitigation: keep it stateless — return raw position, let `GameplayScreen` decide what to do with it.
- **[Low] MapLoader cache adds memory** → `MapData` is tiny (~10 entries), negligible.

## Migration Plan

1. Create `GameConfig.java` and `InputHandler.java` and `HudRenderer.java` (new files)
2. Encapsulate `GameState` — fields private, domain methods
3. Encapsulate `Player` — fields private, getters
4. Encapsulate data entities — `ProjectileEntity`, `NpcEntity`, `MoveEntity`, `MapData`, `MapEntry`, `QuizData`
5. Move `EnemyEntity` to `game/` package, update imports
6. Replace hardcoded constants with `GameConfig` references
7. Add caching to `MapLoader`
8. Wire `InputHandler` and `HudRenderer` in `GameplayScreen`
9. Build and fix any missed access sites
10. Run to verify no regressions
