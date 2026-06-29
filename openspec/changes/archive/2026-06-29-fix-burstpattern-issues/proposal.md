## Why

`BurstPattern.generate()` allocates `new Array<>()` every frame during cooldown and between bursts, creating GC pressure that can cause frame hitches on slower devices. Additionally, `createProjectile` has a NaN edge case when `dist == 0`, projectile speed is hard-coupled to enemy movement speed, and the burst timer discards overshooting instead of accounting for it.

## What Changes

- Replace per-frame `new Array<>()` allocations in `BurstPattern` with a reusable buffer to eliminate GC pressure
- Add `dist == 0` guard in `createProjectile` to prevent NaN velocity
- Decouple projectile speed from enemy movement speed by adding a `bulletSpeed` field to `EnemyEntity` and JSON config
- Fix burst timer overshooting by using `timer -= fireTime` instead of `timer = 0`

## Capabilities

### New Capabilities
- `enemy-projectiles`: define projectile behavior including speed, burst pattern, and collision parameters

### Modified Capabilities
- None

## Impact

- `BurstPattern.java` — internal refactor of allocation, timer, and NaN guard
- `EnemyEntity.java` — new `bulletSpeed` field
- `EnemyLoader.java` — read `bulletSpeed` from JSON, provide default if missing
- `ProjectileSystem.java` — no changes needed
- `EnemySystem.java` — no changes needed
- JSON data files — optional `bulletSpeed` field per enemy (backwards compatible, defaults to `speed`)
