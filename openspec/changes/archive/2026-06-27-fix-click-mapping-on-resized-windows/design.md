## Context

`GameplayScreen` currently calls `camera.unproject()` to convert screen click coordinates to world coordinates. With a `FitViewport`, the actual rendered area is inset within the window (letterboxed). `camera.unproject()` assumes the viewport fills the entire window, so clicks near the edges are mapped incorrectly — the player never reaches where the mouse pointed.

The fix is to use `viewport.unproject()` instead, which accounts for the viewport's position and size within the window.

## Goals / Non-Goals

**Goals:**
- Click positions correctly map to world positions at any window size or fullscreen
- Only the unproject call is changed — no behavioural or structural changes

**Non-Goals:**
- Changing viewport type or behaviour
- Any other touch input changes

## Decisions

### Decision 1: `viewport.unproject()` over `camera.unproject()`
- **Chosen**: Replace `camera.unproject(touchPos)` with `viewport.unproject(touchPos)`
- **Rationale**: `viewport.unproject()` internally uses the viewport's screen bounds to correctly map screen → world coordinates. `camera.unproject()` naively divides by `Gdx.graphics.getWidth()/getHeight()` which is wrong when the viewport doesn't fill the entire screen.

## Risks / Trade-offs

| Risk | Mitigation |
|---|---|
| `viewport` is null if accessed before `show()` | Already guarded — input is only processed during `render()`, after `show()` has run |
