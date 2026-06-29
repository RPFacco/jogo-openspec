## Why

The current homing projectile travels in a straight line with gradual turn — visually functional but lacks impact. Replacing it with a high-speed semicircular arc and a comet-style tail makes the attack feel more dramatic and responsive.

## What Changes

- `HomingBehavior` rewritten to accept a specific `EnemyEntity` target and speed at construction
- Turn rate calculated dynamically per shot to produce a consistent semicircular arc
- Initial direction perpendicular to the player-to-enemy line
- Speed increased to 600 px/s
- Visual changed from circle to a trailing line (comet tail)
- Projectile continues straight if target dies mid-flight
- Collision checks against all enemies (not just target)

## Capabilities

### New Capabilities

- `arc-homing`: Player projectile flies in a dynamic semicircular arc toward the targeted enemy

### Modified Capabilities

- `homing-projectile`: Repurposed for arc behavior, faster speed, comet visual, and dead-target handling

## Impact

- `game/HomingBehavior.java`: rewritten — dynamic turnRate, target reference, dead-target check
- `game/ProjectileSystem.java`: render changed from circle to line tail
- `game/GameplayScreen.java`: initial direction set perpendicular; speed 600
