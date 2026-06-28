## Context

The enemy system currently has movement hardcoded in `EnemySystem.update()` with waypoint data fields (`waypointX[]`, `waypointY[]`, `currentWaypoint`) embedded directly in `EnemyEntity`. Adding a new movement pattern requires touching Entity (add fields), System (add logic), and Loader (add parser).

The codebase uses Java 21 with libGDX 1.14.2. Existing patterns: `NpcSystem`/`NpcLoader` (static data cache), `MapLoader` (same pattern), `Player.update()` (point-to-point movement algorithm).

## Goals / Non-Goals

**Goals:**
- Decouple movement algorithm from entity data and system logic via Strategy Pattern
- Make `EnemySystem.update()` a simple delegation loop with zero movement logic
- Keep `EnemyEntity` as a generic container (position, speed, strategy reference)
- Preserve existing waypoint behavior exactly (triangle movement on map03)
- All JSON enemy data remains under `assets/data/enemies.json`

**Non-Goals:**
- No new movement patterns in this change (pure refactoring)
- No rendering changes (visuals stay as red rectangles)
- No changes to `GameplayScreen.java` or any system outside enemies
- No performance optimization — same algorithm, same cost

## Decisions

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Pattern | Strategy (interface) | OCP-compliant, zero existing code changes to add new patterns |
| `moving` location | `EnemyEntity` | Universal on/off (stun, pause). Strategies can have additional internal state. |
| Factory | Switch in `EnemyLoader` | Simple, co-located with parsing, good for 2-5 patterns. Extract later if needed. |
| Render | Stays in `EnemySystem` | Red rectangle is a placeholder. Future sprite system will replace it independently. |
| Strategy creation | Static `fromJson()` method per strategy | Self-contained parsing, no separate factory class needed yet |
| Interface method | `update(EnemyEntity e, float delta)` | Clean — strategy mutates entity position, both caller and strategy share same entity reference |

**Alternatives considered:**
- **Abstract class vs interface**: Interface chosen because strategies share no default state or behavior
- **Entity-Component (ECS)**: Overkill for current scale; revisit if enemy count exceeds ~50
- **Data-driven only (no interface, just switch in System)**: Violates OCP — modifications pile up in EnemySystem
- **Render in strategy**: Deferred until sprite system is designed — would add `render()` to the interface later if needed

## Risks / Trade-offs

| Risk | Mitigation |
|------|------------|
| [Performance] Virtual call per enemy per frame | Negligible for <100 enemies. If performance becomes an issue, inline hot strategies. |
| [Coupling] Strategy depends on EnemyEntity | Intentional — strategy needs position/speed. Entity exposes public fields (existing convention). |
| [JSON] Old enemies.json format breaks | This change IS the migration — old format was just created in the previous change, already archived. |
