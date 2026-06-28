## Context

Enemies currently have movement via Strategy Pattern (`MovementStrategy` → `WaypointMovement`). The game-design.md specifies ranged enemy attacks. This design extends the same pluggable strategy approach to enemy shooting.

The codebase uses Java 21 with libGDX 1.14.2. Existing patterns: `Strategy` interface + concrete implementation + JSON `"type"` switch in loader, `ProjectileSystem` will be used by both enemies (now) and player (future homing projectile).

## Goals / Non-Goals

**Goals:**
- `ProjectileSystem` manages all projectiles: update position, remove off-screen, render
- `ShootPattern` interface with `generate()` method returns projectiles to spawn
- `BurstPattern`: fires N bullets toward player, then waits cooldown
- Wire into `EnemySystem.updateShooting()` and `GameplayScreen`
- Map03 enemy shoots 3 bullets at once, 2s cooldown

**Non-Goals:**
- No damage or collision (visual only)
- No player projectiles (future change)
- No melee attack patterns (future)
- No HP system for enemies (future)

## Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Projectile container | Separate `ProjectileSystem` | Reusable for player projectiles, SRP |
| Shoot pattern interface | `generate(EnemyEntity, Player, float delta)` | Same Strategy pattern as movement; returns projectiles to spawn |
| Projectile direction | Static toward player position at moment of fire | No homing (player projectiles get `homing` flag later) |
| Projectile format | `ProjectileEntity` with `vx, vy` (normalized) | Simple, deterministic movement |
| `ShootPattern` return type | `Array<ProjectileEntity>` | Patterns can spawn 0, 1, or N projectiles per call |
| Wiring | `GameplayScreen` passes `Player` + `ProjectileSystem` to `EnemySystem.updateShooting()` | Avoids coupling EnemySystem → ProjectileSystem at construction |
| Removal | Projectile removed when fully off-screen (all sides) | Simple, no max-range needed for now |

**Alternatives considered:**
- **ShootPattern with callback** (e.g., `Consumer<ProjectileEntity>`): Cleaner decoupling but harder to reason about. Return array is simpler.
- **ProjectileSystem inside EnemySystem**: Would need refactoring when player projectiles are added. Separate is future-proof.
- **Projectile as ShapeRenderer arc/filled circle**: `circle()` in ShapeRenderer — 16×16 diameter, red.

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| [Performance] Many projectiles active simultaneously | LibGDX `Array` iteration is fast; cap at ~100 if needed |
| [Wiring] `EnemySystem.updateShooting()` needs Player ref | GameplayScreen passes it each frame — no permanent coupling |
| [Homing flag] Future player projectiles need it | `ProjectileEntity` gets `boolean homing` now, unused until player shooting is added |
