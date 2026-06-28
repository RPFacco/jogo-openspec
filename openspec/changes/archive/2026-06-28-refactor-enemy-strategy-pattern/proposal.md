## Why

The current enemy system has the movement algorithm hardcoded in `EnemySystem.update()` and `EnemyEntity` with waypoint-specific fields. Adding a new movement pattern (sine wave, circle, follow player, etc.) currently requires modifying 3 files — Entity (add fields), System (add logic), Loader (add parser). This violates the Open/Closed Principle and makes the code fragile as more patterns are added.

Refactoring to Strategy Pattern decouples movement algorithms from the entity and system, making each new pattern a single new class with zero changes to existing code.

## What Changes

- Extract waypoint movement from `EnemySystem.update()` into a dedicated `WaypointMovement` class implementing a `MovementStrategy` interface
- Strip waypoint-specific fields (`waypointX[]`, `waypointY[]`, `currentWaypoint`) from `EnemyEntity` and replace with a single `MovementStrategy strategy` field
- `EnemySystem.update()` delegates to `enemy.strategy.update(enemy, delta)` — no movement logic lives in the system anymore
- `EnemyLoader` parses a `"movement"` block with a `"type"` field and uses a switch to instantiate the correct strategy via its `fromJson()` factory method
- JSON schema for enemies changes: waypoint data moves under `"movement": { "type": "waypoint", "waypoints": [...] }`
- The `moving` field stays on `EnemyEntity` as a universal on/off switch
- Visual rendering remains unchanged (red rectangle placeholder) — render concerns are deferred

## Capabilities

### New Capabilities
- `enemy-movement`: Defines how enemies move in the game world. Supports pluggable movement patterns via a common interface, starting with waypoint-based movement.

### Modified Capabilities
*(none — this is a pure refactoring with no spec-level requirement changes)*

## Impact

- **Files to modify:** `EnemyEntity.java`, `EnemySystem.java`, `EnemyLoader.java`, `assets/data/enemies.json`
- **Files to create:** `MovementStrategy.java` (interface), `WaypointMovement.java` (implementation)
- **No impact on:** `GameplayScreen.java`, `NpcSystem.java`, `Player.java`, maps, quizzes, or any other system
- **Data format:** `enemies.json` gains a `movement` wrapper with a `type` discriminator
