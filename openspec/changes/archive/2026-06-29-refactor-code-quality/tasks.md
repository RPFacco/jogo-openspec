## 1. Setup — New Files

- [x] 1.1 Create `GameConfig.java` with `MAP_WIDTH`/`MAP_HEIGHT` constants
- [x] 1.2 Create `InputHandler.java` with `handleTouch()` and `isEscPressed()` methods
- [x] 1.3 Create `HudRenderer.java` with `render(GameState)` method

## 2. Encapsulation — GameState

- [x] 2.1 Make `lives` and `completedQuizzes` private in `GameState`
- [x] 2.2 Add domain methods: `getLives()`, `takeDamage()`, `reset()`, `isCompleted(id)`, `markCompleted(id)`, `getCompletedCount()`
- [x] 2.3 Update all access sites in `GameplayScreen`, `QuizScreen`, `NpcSystem`, `MainMenuScreen` to use domain methods

## 3. Encapsulation — Player

- [x] 3.1 Make all fields private in `Player`, add getters
- [x] 3.2 Update all access sites in `GameplayScreen`, `ProjectileSystem`, `BurstPattern`, `EnemySystem`

## 4. Encapsulation — Data Entities

- [x] 4.1 Encapsulate `ProjectileEntity` (fields private + getters/setters)
- [x] 4.2 Encapsulate `NpcEntity` (fields private + getters/setters)
- [x] 4.3 Encapsulate `MoveEntity` (fields private + getters/setters)
- [x] 4.4 Encapsulate `MapData` and `MapEntry` (fields private + getters/setters)
- [x] 4.5 Encapsulate `QuizData` (fields private + getters/setters)
- [x] 4.6 Update all access sites in systems, loaders, and screens

## 5. Constants — GameConfig Migration

- [x] 5.1 Replace `MAP_WIDTH`/`MAP_HEIGHT` in `GameplayScreen` with `GameConfig` references
- [x] 5.2 Replace `MAP_WIDTH`/`MAP_HEIGHT` in `ProjectileSystem` with `GameConfig` references
- [x] 5.3 Replace `SCREEN_WIDTH`/`SCREEN_HEIGHT` in `MainMenuScreen` with `GameConfig` references
- [x] 5.4 Replace `SCREEN_WIDTH`/`SCREEN_HEIGHT` in `QuizScreen` with `GameConfig` references

## 6. Package Boundary — EnemyEntity Move

- [x] 6.1 Move `EnemyEntity.java` from `game/data/` to `game/`
- [x] 6.2 Update imports in `EnemyLoader`, `EnemySystem`, `BurstPattern`

## 7. Loader Consistency — MapLoader Cache

- [x] 7.1 Add static cache field to `MapLoader`, return cached value when available

## 8. God Class — GameplayScreen Extraction

- [x] 8.1 Wire `InputHandler` into `GameplayScreen.show()`, use in `render()`
- [x] 8.2 Wire `HudRenderer` into `GameplayScreen.show()`, use in `render()`

## 9. Verification

- [x] 9.1 Build — zero compilation errors
- [x] 9.2 Run — game starts without crashes
- [x] 9.3 Navigate between maps and screens — no regressions
