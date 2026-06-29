## Context

Currently, 4 static loader classes (`EnemyLoader`, `QuizLoader`, `NpcLoader`, `MapLoader`) each hold a `private static` cache. Game systems call `XxxLoader.load()` directly from constructors and methods, creating hidden global state. On game restart, `EnemyLoader.clearCache()` is the only reset mechanism — and it's only called for enemies. The other loaders never refresh, so stale object state (`alive = false` on old enemy instances) persists across play sessions if the cache is not cleared.

This design also makes it impossible to run tests in isolation, since the static caches persist across test cases.

## Goals / Non-Goals

**Goals:**
- Eliminate all `static` cached state from data loaders
- Introduce a single `DataManager` that owns all loaded data
- Inject `DataManager` via constructors into all systems that need data
- Preserve the lazy-loading behavior (JSON files are read once and cached)
- Keep all JSON file formats and schema unchanged

**Non-Goals:**
- Not changing any data file format (`enemies.json`, `npcs.json`, `quizzes.json`, `maps.json`)
- Not changing gameplay behavior or screen flow
- Not introducing a full DI framework — manual constructor injection only

## Decisions

### Decision 1: DataManager as a plain class (not singleton)

Rather than keeping the static pattern but moving it to a new class, `DataManager` will be a plain class instantiated once in `OopQuest.create()` and passed down. This makes the dependency graph explicit and testable.

- **Alternative considered**: Singleton `DataManager.getInstance()` — rejected because it's the same problem in a different wrapper.
- **Alternative considered**: Keep static loaders but make them return deep copies — rejected because it doesn't solve the global state problem and adds complexity.

### Decision 2: Loaders become non-static internal helpers

The 4 loaders will drop their `static` keyword. They can remain `public` (for test access) but will no longer cache globally — the `DataManager` instance owns the cache.

`DataManager` will compose the loaders:

```
DataManager
  ├── EnemyLoader (instance) → cache: Map<String, Array<EnemyEntity>>
  ├── QuizLoader (instance)  → cache: Map<String, QuizData>
  ├── NpcLoader (instance)   → cache: Map<String, Array<NpcEntity>>
  └── MapLoader (instance)   → cache: MapData
```

### Decision 3: `reloadEnemies()` replaces `clearCache()`

`DataManager.reloadEnemies()` will re-parse enemies.json and replace the cached map, creating fresh `EnemyEntity` objects. This mirrors the current `EnemyLoader.clearCache()` + next `load()` behavior but without the static mutation.

### Decision 4: Injection via constructor parameters

| Class | Current | New |
|-------|---------|-----|
| `MapManager` | `MapLoader.load()` | `DataManager` injected |
| `NpcSystem` | `QuizLoader.load()` | `DataManager` injected |
| `GameplayScreen` | — (creates systems) | Creates `DataManager`, passes to systems |
| `QuizScreen` | `QuizLoader.load().size()` | Receives `DataManager` or quiz count |
| `OopQuest` | `EnemyLoader.clearCache()` | `dataManager.reloadEnemies()` |

## Risks / Trade-offs

- **Risk: Wider refactoring surface** — 9 Java files touched. **Mitigation**: Changes are mechanical (constructor wiring, method delegation). Each change is a simple find-and-replace pattern.
- **Risk: Forgetting to wire DataManager into a new system** — **Mitigation**: The compiler will catch any `XxxLoader.load()` call that wasn't replaced (those methods no longer exist as static).
- **Trade-off**: More constructor parameters — but each class only gets what it needs, and the dependency graph becomes explicit rather than hidden.
