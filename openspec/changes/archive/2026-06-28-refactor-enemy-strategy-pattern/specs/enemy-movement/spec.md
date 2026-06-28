## ADDED Requirements

### Requirement: Movement strategy interface
The system SHALL define a `MovementStrategy` interface with a single method `update(EnemyEntity entity, float delta)` that mutates the entity's position.

#### Scenario: Strategy updates enemy position
- **WHEN** `strategy.update(enemy, delta)` is called
- **THEN** `enemy.x` and `enemy.y` MAY change based on the strategy's algorithm

### Requirement: Pluggable strategies via factory
The loader SHALL parse a `"movement"` block from each enemy's JSON entry, read the `"type"` field, and instantiate the corresponding strategy via a switch.

#### Scenario: Loader creates WaypointMovement from JSON
- **WHEN** the loader encounters `"movement": { "type": "waypoint", "waypoints": [...] }`
- **THEN** it SHALL create a `WaypointMovement` instance configured with the given waypoints

#### Scenario: Unknown type fails
- **WHEN** the loader encounters a `"movement"` block with an unrecognized `"type"`
- **THEN** the system SHALL throw or log a descriptive error

### Requirement: `EnemySystem.update()` delegates to strategy
`EnemySystem.update(float delta)` SHALL iterate all enemies and call `enemy.strategy.update(enemy, delta)` for each, containing zero movement algorithm logic itself.

#### Scenario: System delegates to waypoint movement
- **WHEN** `enemySystem.update(delta)` is called and an enemy has a `WaypointMovement` strategy
- **THEN** the enemy moves toward its current waypoint at its speed

### Requirement: Waypoint-based movement
A `WaypointMovement` strategy SHALL move the enemy in sequence through an ordered list of waypoints, advancing to the next upon arrival and looping back to the first after the last.

#### Scenario: Enemy moves to next waypoint
- **WHEN** the enemy is within arrival distance of its current target waypoint
- **THEN** its position snaps to that waypoint and the target advances to the next index

#### Scenario: Waypoint loop
- **WHEN** the enemy arrives at the last waypoint
- **THEN** the target resets to the first waypoint (index 0)

### Requirement: Universal on/off via `EnemyEntity.moving`
When `enemy.moving` is `false`, the system SHALL skip calling `update()` on that enemy's strategy regardless of its internal state.

#### Scenario: Disabled enemy does not move
- **WHEN** `enemy.moving` is set to `false`
- **THEN** the enemy position remains unchanged during `EnemySystem.update()`

### Requirement: JSON data format
Enemy entries in `assets/data/enemies.json` SHALL use a nested `"movement"` block with a `"type"` discriminator.

#### Scenario: Waypoint JSON structure
- **WHEN** the loader parses an enemy entry
- **THEN** waypoint data SHALL be nested under `"movement"` rather than at the root level
