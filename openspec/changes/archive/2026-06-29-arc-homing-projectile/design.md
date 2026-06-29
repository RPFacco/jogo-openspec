## Context

The `HomingBehavior` and `GameplayScreen` currently fire a circle projectile that gradually turns toward the nearest enemy. The visual and mechanical feel is soft. This change replaces it with a high-speed arc trajectory and a comet-line visual.

## Goals / Non-Goals

**Goals:**
- Dynamic semicircular arc trajectory via perpendicular start + calculated turn rate
- Speed increased to 600 px/s
- Projectile tracks specific target enemy (not just nearest)
- If target dies, projectile continues straight
- Visual changes from circle to trailing line (comet tail)

**Non-Goals:**
- No changes to `ProjectileSystem` update/collision logic beyond render
- No changes to `InputHandler` or `EnemySystem`

## Decisions

### Decision 1: HomingBehavior rewritten

New constructor: `HomingBehavior(EnemyEntity target, float speed)`. The behavior stores the target reference. Each frame it checks if the target is still in the enemy array via reference equality. If not, it stops modifying velocity (projectile continues straight). The turn rate is computed once at construction: `turnRate = 2f * speed / distance`.

```
Arc geometry:
  D = distance(player, target)
  initial direction = perpendicular to (target - player)
  turnRate = 2 * speed / D
  → projectile traces exactly one semicircle as it crosses distance D
```

### Decision 2: Initial direction set in GameplayScreen

`GameplayScreen.handleInput()` computes the perpendicular direction:
```
float dx = target.getCenterX() - player.getCenterX();
float dy = target.getCenterY() - player.getCenterY();
// Perpendicular (rotate 90°)
p.setVx(-dy / dist);
p.setVy(dx / dist);
```
Speed set to 600. Turn rate handled by HomingBehavior internally.

### Decision 3: Comet tail in ProjectileSystem.render()

Player projectiles (those with behavior) render as a line instead of a circle:
```
float tailX = p.getX() - p.getVx() * tailLength;
float tailY = p.getY() - p.getVy() * tailLength;
shapeRenderer.rectLine(tailX, tailY, p.getX(), p.getY(), tailWidth);
```
The `render()` method already distinguishes player vs enemy projectiles via `behavior != null`.

## Risks / Trade-offs

- **[Dangling reference]** Behavior holds `EnemyEntity` reference — mitigated by checking `enemies.contains(target, true)` each frame
- **[Arc overshoot]** If target moves toward the projectile, the arc may overshoot — but since it homes each frame, it self-corrects
