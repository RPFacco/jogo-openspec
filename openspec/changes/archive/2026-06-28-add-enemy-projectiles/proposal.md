## Why

Enemies currently have no offensive capability — they move but do nothing else. The game-design.md specifies that enemies attack with projectiles, and map03 in particular should have an enemy that shoots bursts of bullets at the player. This change adds the projectile system infrastructure and the first shoot pattern (burst), laying the groundwork for future enemy attack patterns and the player's homing projectile.

## What Changes

- Create `ProjectileEntity` data class (x, y, vx, vy, speed, size, alive, optional homing flag for future player projectiles)
- Create `ProjectileSystem` that manages all projectiles: moves them in straight line each frame, removes when off-screen
- Create `ShootPattern` interface with `generate(EnemyEntity, Player, float delta)` method
- Create `BurstPattern` implementation: fires N bullets toward player's current position, then waits cooldown seconds
- Add `ShootPattern shootPattern` field to `EnemyEntity`
- `EnemySystem` gains `updateShooting(Player, float delta, ProjectileSystem)` that delegates to each enemy's shoot pattern
- `EnemyLoader` parses new `"shoot"` block with `"type"` switch (same pattern as movement)
- Update `enemies.json` for map03 enemy with burst pattern (3 bullets, 2s cooldown)
- Render projectiles as red circles (16×16) via `ShapeRenderer`
- No damage/HP on player or enemies — projectiles are visual only

## Capabilities

### New Capabilities
- `enemy-projectiles`: Enemies can fire projectiles at the player using pluggable shoot patterns. Projectiles move in straight lines and are removed when off-screen.

### Modified Capabilities
*(none)*

## Impact

- **Files to create:** `ProjectileEntity.java`, `ProjectileSystem.java`, `ShootPattern.java`, `BurstPattern.java`
- **Files to modify:** `EnemyEntity.java`, `EnemySystem.java`, `EnemyLoader.java`, `assets/data/enemies.json`, `GameplayScreen.java`
- **No impact on:** NpcSystem, Player, maps, quizzes
