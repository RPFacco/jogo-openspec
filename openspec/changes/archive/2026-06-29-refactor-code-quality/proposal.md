## Why

Five code quality issues accumulated during iterative development: most entity fields are public breaking encapsulation, map dimensions are hardcoded in 4 files, `EnemyEntity` in the `data/` package references `game/` interfaces, loaders follow inconsistent caching patterns, and `GameplayScreen` coordinates too many concerns.

## What Changes

- **Encapsulation**: Make all entity fields private with getters/setters — `GameState`, `Player`, `ProjectileEntity`, `EnemyEntity`, `NpcEntity`, `MoveEntity`, `MapData`, `MapEntry`, `QuizData`
- **Constants**: Extract `MAP_WIDTH`/`MAP_HEIGHT` into a shared `GameConfig` class, replace hardcoded values in `GameplayScreen`, `ProjectileSystem`, `MainMenuScreen`, `QuizScreen`
- **Package boundary**: Move `EnemyEntity` from `game.data` to `game` package to resolve the `data/` → `game/` reference
- **Loader consistency**: Add static caching to `MapLoader`, matching the pattern used by `EnemyLoader`, `NpcLoader`, `QuizLoader`
- **God class**: Extract `InputHandler` and `HudRenderer` from `GameplayScreen`, keeping orchestration in the screen

## Capabilities

### New Capabilities
None — this is a pure refactoring change with no new requirements.

### Modified Capabilities
None — no behavior changes, all refactoring is mechanically equivalent.

## Impact

- ~20 files touched across the codebase (entity classes, systems, screens, loaders)
- No behavior changes — all refactoring is mechanical with verified build equivalence
- JSON data files unchanged — backwards compatible at the config level
- New files: `GameConfig.java`, `InputHandler.java`, `HudRenderer.java`
- Moved file: `EnemyEntity.java` from `data/` to `game/`
