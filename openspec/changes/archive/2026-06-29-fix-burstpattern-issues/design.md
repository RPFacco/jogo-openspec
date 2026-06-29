## Context

`BurstPattern` is the only `ShootPattern` implementation. It fires bursts of projectiles toward the player. Four issues were identified during code review of `core/src/main/java/com/rpfacco/oopquest/game/BurstPattern.java`:

1. `new Array<>()` allocated every frame during cooldown and between shots — GC pressure
2. `dx / dist` produces `NaN` when player is exactly at enemy center, causing projectile to stay in the list forever
3. `p.speed = enemy.speed` couples two independent concerns
4. `timer = 0` discards overshoot when a frame exceeds `BURST_INTERVAL`

## Goals / Non-Goals

**Goals:**
- Eliminate per-frame `Array` allocations in `BurstPattern`
- Fix NaN edge case in projectile direction calculation
- Decouple projectile speed from enemy movement speed
- Compensate timer overshoot instead of discarding it
- Backwards compatible — existing `enemies.json` files work without changes

**Non-Goals:**
- Changing the `ShootPattern` interface signature
- Adding new shoot pattern types
- Other performance optimizations beyond the four listed issues

## Decisions

### 1. Reusable buffer for Array allocation
Use an internal `Array<ProjectileEntity> resultBuffer` cleared and reused across `generate()` calls. This eliminates all per-frame allocations without changing the `ShootPattern` interface.

Alternatives considered:
- **Change interface to `void generate(..., Array<ProjectileEntity> out)`** — cleaner but more invasive; the internal buffer achieves the same effect with less churn
- **Object pool for ProjectileEntity** — overkill; the entity creation is 1 per shot, not per frame

### 2. NaN guard
Add `if (dist == 0) dist = 1f;` before the division. Zero distance means player is at enemy center — any direction works, so defaulting to 1 (no normalization change needed for equal dx/dy = 0) prevents NaN. The projectile will sit at the enemy center briefly before being moved by `vx * speed * delta` (which will be 0 since both components are 0), eventually the player moves and a new projectile will have a valid direction.

Actually, if dist == 0 and we set dist = 1f, then vx = 0/1 = 0 and vy = 0/1 = 0, so the projectile won't move. That's fine — the player will move away, and the next burst will fire correctly. Setting dist = 1f is an arbitrary safe divisor.

### 3. Bullet speed decoupling
Add `bulletSpeed` field to `EnemyEntity`. `EnemyLoader` reads it from JSON, defaulting to `speed` if absent. `BurstPattern.createProjectile()` uses `enemy.bulletSpeed`. No JSON files need updating — all existing enemies get `bulletSpeed = speed`.

### 4. Timer overshoot compensation
Change `timer = 0` to `timer -= fireTime` after firing. This preserves any excess time beyond `BURST_INTERVAL`, so the next shot in the burst fires earlier by the overshoot amount, maintaining the correct cadence.

## Risks / Trade-offs

- **[Low] Internal buffer lifetime** → `resultBuffer` lives as long as `BurstPattern`. If a future refactor makes `BurstPattern` long-lived with many enemies sharing instances, the buffer is harmless. If enemies are recreated, each new `BurstPattern` gets its own buffer.
- **[Low] dist == 0 guard hides the real bug** → In practice this only fires if player position exactly equals enemy center, which requires precise alignment (pixel-perfect on a 1920x1080 map). The guard is a safety net, not a workflow.
- **[Low] bulletSpeed default coupling** → If `bulletSpeed` isn't in JSON, it silently falls back to `speed`. If someone later adds `speed` to one enemy but forgets `bulletSpeed`, the projectile will match movement speed — reasonable default.
