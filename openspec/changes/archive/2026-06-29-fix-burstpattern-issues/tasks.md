## 1. Bullet Speed Decoupling

- [x] 1.1 Add `public float bulletSpeed` field to `EnemyEntity`
- [x] 1.2 In `EnemyLoader`, read `bulletSpeed` from JSON value, default to `speed` if absent
- [x] 1.3 In `BurstPattern.createProjectile()`, use `enemy.bulletSpeed` instead of `enemy.speed`

## 2. BurstPattern Internal Fixes

- [x] 2.1 Add reusable `Array<ProjectileEntity> resultBuffer` field to `BurstPattern`, use `resultBuffer.clear()` + `resultBuffer.add()` instead of `new Array<>()` in all paths
- [x] 2.2 Add `if (dist == 0) dist = 1f;` guard in `createProjectile()` before division
- [x] 2.3 Change `timer = 0` to `timer -= fireTime` after firing to preserve overshoot

## 3. Verification

- [x] 3.1 Build — zero compilation errors
- [x] 3.2 Run — game starts without crashes
- [x] 3.3 Navigate to map03, confirm enemies still fire bursts and projectiles behave correctly
