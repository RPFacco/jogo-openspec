## Why

All 4 data loaders (`EnemyLoader`, `QuizLoader`, `NpcLoader`, `MapLoader`) use `private static` caches — global mutable state. When the game restarts, enemy objects still hold `alive = false` from the previous run. Only `EnemyLoader.clearCache()` exists; the other 3 have no reset path. This is a bug waiting to happen and makes the code untestable.

## What Changes

- Introduce a `DataManager` class that owns all loaded data as instance fields
- Remove `static` caches from all 4 loaders (they become internal, possibly private)
- Inject `DataManager` via constructors into `MapManager`, `NpcSystem`, `GameplayScreen`, and `QuizScreen`
- Replace `EnemyLoader.clearCache()` with `dataManager.reloadEnemies()`
- Remove all direct `XxxLoader.load()` calls from game logic

## Capabilities

### New Capabilities

- `data-manager`: Centralized data loading, caching, and lifecycle management for all game data (enemies, NPCs, quizzes, maps). Single source of truth injected into systems that need data.

### Modified Capabilities

None — this is purely an architectural refactor. No spec-level behavior changes.

## Impact

- **Files removed**: none
- **Files created**: `DataManager.java`
- **Files modified**: `MapManager.java`, `NpcSystem.java`, `GameplayScreen.java`, `QuizScreen.java`, `OopQuest.java`, `EnemyLoader.java`, `QuizLoader.java`, `NpcLoader.java`, `MapLoader.java`
- **No JSON schema changes** — data files stay identical
- **No gameplay behavior changes** — all loading semantics preserved
