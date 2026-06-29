## ADDED Requirements

### Requirement: Projectile flies in semicircular arc

When the player presses E, the projectile SHALL follow a dynamic semicircular arc trajectory toward the nearest enemy. The arc SHALL be produced by starting the projectile with a velocity perpendicular to the player-enemy line and applying a dynamic turn rate.

#### Scenario: Arc trajectory on fire

- **WHEN** the player presses E with an enemy within range
- **THEN** the projectile SHALL start with initial velocity perpendicular to the line between player and enemy
- **AND** the turn rate SHALL be calculated as `0.3 * 2 * speed / distance` to produce a gentle semicircular arc
- **AND** the projectile SHALL curve toward the enemy along the arc

### Requirement: High projectile speed

The homing projectile SHALL move at 1800 px/s.

#### Scenario: Speed on creation

- **WHEN** the projectile is created
- **THEN** its speed SHALL be 1800 px/s

### Requirement: Projectile follows specific target

The HomingBehavior SHALL track a specific EnemyEntity passed at construction, not just the nearest enemy each frame.

#### Scenario: Target acquired on fire

- **WHEN** the projectile is created
- **THEN** it SHALL home toward the specific enemy that was nearest at the moment of firing

### Requirement: Target death handling

If the target enemy dies or is removed while the projectile is in flight, the projectile SHALL stop homing and continue in a straight line until it exits the map bounds.

#### Scenario: Target removed mid-flight

- **WHEN** the target enemy is removed from the enemy list while the projectile is active
- **THEN** the projectile SHALL stop changing direction
- **AND** the projectile SHALL continue in its current direction

### Requirement: Comet tail visual

The projectile SHALL be rendered as a blue trailing line instead of a circle.

#### Scenario: Comet tail rendering

- **WHEN** the projectile is alive and active
- **THEN** it SHALL be rendered as a line from its current position backward along its velocity direction
- **AND** the line SHALL be blue
