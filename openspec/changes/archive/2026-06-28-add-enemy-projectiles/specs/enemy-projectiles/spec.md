## ADDED Requirements

### Requirement: Projectile moves in straight line
The system SHALL move each projectile by `vx * speed * delta` and `vy * speed * delta` every frame.

#### Scenario: Projectile moves toward target
- **WHEN** a projectile has direction vector (0.707, 0.707) and speed 480
- **THEN** after 1 second it SHALL have moved approximately (339, 339) pixels

#### Scenario: Projectile removed when off-screen
- **WHEN** a projectile's position is outside the map bounds (0, 0, 1920, 1080)
- **THEN** it SHALL be removed from the projectile list

### Requirement: Projectile rendered as red circle
The system SHALL render each alive projectile as a filled red circle using `ShapeRenderer.circle()`.

#### Scenario: Projectile visible on screen
- **WHEN** a projectile is alive and within map bounds
- **THEN** it SHALL be drawn as a filled red circle with diameter 16

### Requirement: ShootPattern interface
The system SHALL define a `ShootPattern` interface with `Array<ProjectileEntity> generate(EnemyEntity enemy, Player player, float delta)`.

#### Scenario: Pattern returns projectiles
- **WHEN** `pattern.generate(enemy, player, delta)` is called
- **THEN** it SHALL return an array of new `ProjectileEntity` instances (possibly empty)

### Requirement: BurstPattern fires N bullets at cooldown interval
The system SHALL provide a `BurstPattern` that fires a fixed number of bullets toward the player's current position, then waits a cooldown before firing again.

#### Scenario: Burst fires at cooldown
- **WHEN** `BurstPattern` with `cooldown=2` and `burstSize=3` is updated for 2 seconds
- **THEN** it SHALL generate 3 projectiles aimed at the player's position

#### Scenario: Burst waits during cooldown
- **WHEN** `BurstPattern` has just fired and less than `cooldown` seconds have passed
- **THEN** it SHALL return an empty array

### Requirement: Shooting wired via EnemySystem
`EnemySystem` SHALL provide an `updateShooting(Player player, float delta, ProjectileSystem projectileSystem)` method that delegates to each enemy's shoot pattern and adds returned projectiles.

#### Scenario: Enemy with shoot pattern fires
- **WHEN** `enemySystem.updateShooting(player, delta, projectileSystem)` is called and an enemy has a `ShootPattern`
- **THEN** projectiles from the pattern SHALL be added to `projectileSystem`

### Requirement: JSON data format for shooting
Enemy entries in `enemies.json` SHALL support a `"shoot"` block with a `"type"` discriminator, parsed by `EnemyLoader` via switch.

#### Scenario: Burst JSON structure
- **WHEN** the loader encounters `"shoot": { "type": "burst", "burstSize": 3, "cooldown": 2 }`
- **THEN** it SHALL create a `BurstPattern` with those parameters
