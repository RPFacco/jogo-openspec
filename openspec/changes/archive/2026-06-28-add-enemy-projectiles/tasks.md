## 1. Projectile Core

- [x] 1.1 Create `ProjectileEntity.java` in `com.rpfacco.oopquest.game.data` with fields: `x`, `y`, `vx`, `vy`, `speed`, `size`, `alive`
- [x] 1.2 Create `ProjectileSystem.java` in `com.rpfacco.oopquest.game` with:
  - `Array<ProjectileEntity>` storage
  - `update(float delta)` — moves each projectile, removes off-screen ones
  - `render(ShapeRenderer)` — draws alive projectiles as filled red circles (size×size diameter)
  - `add(ProjectileEntity)` method

## 2. Shoot Pattern

- [x] 2.1 Create `ShootPattern.java` interface in `com.rpfacco.oopquest.game` with `Array<ProjectileEntity> generate(EnemyEntity enemy, Player player, float delta)`
- [x] 2.2 Create `BurstPattern.java` implementing `ShootPattern` in `com.rpfacco.oopquest.game` with fields: `burstSize`, `cooldown`, `float timer`
  - After `cooldown` seconds: generate `burstSize` projectiles toward player position, reset timer
  - Projectile `vx, vy` = normalized direction from enemy center to player center
  - Projectile speed = enemy speed
  - Spawn position = enemy center
- [x] 2.3 Add static `fromJson(JsonValue config)` to `BurstPattern` parsing `burstSize` and `cooldown`

## 3. Enemy Integration

- [x] 3.1 Add `ShootPattern shootPattern` field to `EnemyEntity`
- [x] 3.2 Add `void updateShooting(Player player, float delta, ProjectileSystem ps)` to `EnemySystem`:
  - Iterates enemies, calls `shootPattern.generate()`, adds returned projectiles to `ps`
- [x] 3.3 Update `EnemyLoader` to parse `"shoot"` block with `"type"` switch (add `"burst"` case)
- [x] 3.4 Update `GameplayScreen`:
  - Add `ProjectileSystem` field, create in `show()`
  - In `render()`: call `enemySystem.updateShooting(player, delta, projectileSystem)` and `projectileSystem.update(delta)`
  - In render shape block: call `projectileSystem.render(shapeRenderer)` after enemy render
- [x] 3.5 Update `assets/data/enemies.json` to add `"shoot": { "type": "burst", "burstSize": 3, "cooldown": 2 }` to map03 enemy

## 4. Verification

- [x] 4.1 Build — zero compilation errors
- [x] 4.2 Run — game starts without crashes
- [x] 4.3 Navigate to map03 — confirm enemy shoots 3 red circles toward player every 2 seconds (manual)
