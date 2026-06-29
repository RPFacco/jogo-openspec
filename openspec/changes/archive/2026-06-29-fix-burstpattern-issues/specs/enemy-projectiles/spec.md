## ADDED Requirements

### Requirement: Projectile speed configurable per enemy
Each enemy SHALL support a `bulletSpeed` field in its JSON configuration. If omitted, the projectile speed SHALL default to the enemy's movement `speed`.

#### Scenario: bulletSpeed specified in JSON
- **WHEN** an enemy has `"bulletSpeed": 600` in its JSON config
- **THEN** projectiles fired by that enemy SHALL move at 600 px/s

#### Scenario: bulletSpeed omitted
- **WHEN** an enemy has no `bulletSpeed` in its JSON config
- **THEN** projectiles SHALL move at the enemy's `speed` value

### Requirement: No per-frame Array allocation
`BurstPattern.generate()` SHALL NOT allocate `new Array<>()` on any code path. It SHALL reuse an internal buffer to avoid GC pressure.

#### Scenario: No allocation during cooldown
- **WHEN** `BurstPattern` is on cooldown and `generate()` is called every frame for 60 frames
- **THEN** zero `Array` objects SHALL be allocated by the pattern

#### Scenario: No allocation between shots
- **WHEN** `BurstPattern` is between burst shots and `generate()` is called
- **THEN** no new `Array` object SHALL be allocated

### Requirement: Projectile direction NaN guard
`BurstPattern.createProjectile()` SHALL handle the case where the player is at the exact same position as the enemy center, producing a valid direction vector instead of `NaN`.

#### Scenario: Player at enemy center
- **WHEN** the player's center equals the enemy's center
- **THEN** the created projectile SHALL have `vx = 0` and `vy = 0` (no NaN)

### Requirement: Burst timer overshoot compensated
When `BurstPattern` fires a shot and the accumulated timer exceeds the fire interval, the excess time SHALL carry over to the next shot rather than being discarded.

#### Scenario: Timer carries over
- **WHEN** `timer = 0.3s` and `fireTime = 0.25s` and a shot is fired
- **THEN** the timer SHALL be set to 0.05s (not reset to 0)
