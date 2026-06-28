## 1. Interface & Core

- [x] 1.1 Create `MovementStrategy.java` interface with `void update(EnemyEntity entity, float delta)` in `com.rpfacco.oopquest.game`
- [x] 1.2 Strip waypoint fields from `EnemyEntity` (`waypointX[]`, `waypointY[]`, `currentWaypoint`), add `MovementStrategy strategy` field, keep `moving`
- [x] 1.3 Replace hardcoded movement logic in `EnemySystem.update()` with delegation: `enemy.strategy.update(enemy, delta)` for each moving enemy

## 2. Waypoint Movement Implementation

- [x] 2.1 Create `WaypointMovement.java` implementing `MovementStrategy` in `com.rpfacco.oopquest.game`, with fields: `waypointX[]`, `waypointY[]`, `int currentWaypoint`, `float arrivalDistance`
- [x] 2.2 Implement `update()` with same algorithm as current `EnemySystem.update()` (move toward waypoint, snap on arrival, wrap around)
- [x] 2.3 Add static `fromJson(JsonValue config)` method that parses waypoints array and returns a `WaypointMovement`

## 3. Loader & Data

- [x] 3.1 Update `EnemyLoader` to read `"movement"` block with `"type"` switch, delegating to each strategy's `fromJson()`
- [x] 3.2 Update `assets/data/enemies.json` to nest waypoint data under `"movement": { "type": "waypoint", "waypoints": [...] }`

## 4. Verification

- [x] 4.1 Build (`./gradlew.bat :lwjgl3:dist`) — confirm zero compilation errors
- [x] 4.2 Run (`./gradlew.bat :lwjgl3:run`) — confirm game starts and runs without crashes
- [x] 4.3 Navigate to map03 and confirm enemy still moves in triangle pattern at same speed/size
