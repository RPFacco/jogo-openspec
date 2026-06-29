## ADDED Requirements

### Requirement: DataManager owns all game data

- `DataManager` SHALL be a non-static class instantiated once in `OopQuest.create()`
- `DataManager` SHALL compose instances of `EnemyLoader`, `QuizLoader`, `NpcLoader`, and `MapLoader`
- `DataManager` SHALL provide type-safe accessor methods: `getEnemies(mapId)`, `getNpcs(mapId)`, `getQuiz(id)`, `getQuizCount()`, `getMapData()`, `getStartMap()` (delegating to the underlying loader caches)
- Loader classes SHALL drop their `static` caches and become instance-based

#### Scenario: DataManager loads data on first access

- **WHEN** `DataManager` is instantiated and any accessor is called
- **THEN** the corresponding loader reads from its JSON file once and caches the result

#### Scenario: Multiple accessors return the same cached instance

- **WHEN** `getEnemies("map03")` is called twice
- **THEN** both calls return the same `Array<EnemyEntity>` reference

### Requirement: Enemy data can be reloaded mid-session

- `DataManager` SHALL expose `reloadEnemies()` that re-parses `enemies.json` and replaces the cached enemy map with fresh objects
- This replaces the current `EnemyLoader.clearCache()` mechanism

#### Scenario: Enemies are fresh after reload

- **WHEN** `reloadEnemies()` is called after enemies have died
- **THEN** subsequent `getEnemies(mapId)` returns new `EnemyEntity` instances with `alive = true`

### Requirement: DataManager is injected via constructors

- `MapManager` SHALL receive `DataManager` in its constructor instead of calling `MapLoader.load()` directly
- `NpcSystem` SHALL receive `DataManager` in its constructor instead of calling `QuizLoader.load()` directly
- `GameplayScreen` SHALL create `DataManager` once and pass it to the systems it constructs
- `QuizScreen` SHALL receive `DataManager` (or the quiz count) instead of calling `QuizLoader.load().size()`
- `OopQuest.resetGame()` SHALL call `dataManager.reloadEnemies()` instead of `EnemyLoader.clearCache()`

#### Scenario: Systems use injected DataManager

- **WHEN** `MapManager.loadMap(mapId)` is called
- **THEN** it retrieves enemies and NPCs via `dataManager.getEnemies(mapId)` and `dataManager.getNpcs(mapId)`

#### Scenario: Removed static calls fail at compile time

- **WHEN** any code attempts to call `EnemyLoader.load()`, `QuizLoader.load()`, `NpcLoader.load()`, or `MapLoader.load()` as static methods
- **THEN** the code SHALL NOT compile (static keyword removed)
