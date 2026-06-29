## 1. Create DataManager class

- [x] 1.1 Create `DataManager.java` in `com.rpfacco.oopquest.game.data.loader` package — compose instances of the 4 loaders, with eager-initialization helpers and accessor methods (`getEnemies(mapId)`, `getNpcs(mapId)`, `getQuiz(id)`, `getQuizCount()`, `getMapData()`, `getStartMap()`)
- [x] 1.2 Add `reloadEnemies()` method that re-invokes the internal `EnemyLoader` to produce fresh enemy instances

## 2. De-static the loaders

- [x] 2.1 `EnemyLoader` — remove `static` from cache and methods; replace `clearCache()` logic with a public `reload()` method; keep constructor public
- [x] 2.2 `QuizLoader` — remove `static` from cache and methods
- [x] 2.3 `NpcLoader` — remove `static` from cache and methods
- [x] 2.4 `MapLoader` — remove `static` from cache and methods

## 3. Wire DataManager into OopQuest

- [x] 3.1 In `OopQuest.java`: create `DataManager` field, instantiate in `create()`, replace `EnemyLoader.clearCache()` with `dataManager.reloadEnemies()` in `resetGame()`

## 4. Inject DataManager into GameplayScreen and subsystems

- [x] 4.1 `GameplayScreen.show()` — create `DataManager` (or receive from OopQuest), pass to `MapManager`, `NpcSystem`, and `QuizScreen` constructors
- [x] 4.2 `MapManager` constructor — accept `DataManager`, replace `MapLoader.load()`, `NpcLoader.load()`, and `EnemyLoader.load()` calls with `dataManager` accessors
- [x] 4.3 `NpcSystem` constructor — accept `DataManager`, replace `QuizLoader.load()` with `dataManager` accessor
- [x] 4.4 `QuizScreen` — accept `DataManager` (or quiz count), replace `QuizLoader.load().size()` with `dataManager.getQuizCount()`
- [x] 4.5 `GameplayScreen.onEnemyDeath()` — replace `QuizLoader.load().get(quizId)` with `dataManager.getQuiz(quizId)`

## 5. Remove dead code and verify

- [x] 5.1 Remove `EnemyLoader.clearCache()` (replaced by `reloadEnemies()`)
- [x] 5.2 Remove unused imports (`Gdx`, `FileHandle`, etc. if no longer needed in changed files)
- [x] 5.3 Run build to confirm no static `XxxLoader.load()` calls remain
- [ ] 5.4 Run the game, play through full flow (start → maps → NPCs → kill enemy → quizzes → victory) to confirm behavior is unchanged
