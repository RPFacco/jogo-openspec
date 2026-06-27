## Context

The game currently runs at 640×480 logical resolution with 20×15 tiles of 32px each. The window opens at 640×480. FitViewport maps this to the physical display. All game entities, move triggers, and clamping operate in this 640×480 coordinate space.

Moving to native 1920×1080 means the game world itself expands to ~3× the width and ~2.25× the height. Maps need new tile grids, entity coordinates need recalculation, and the viewport/window must match.

## Goals / Non-Goals

**Goals:**
- Game world renders at native 1920×1080 (no letterbox, no scaling artifacts)
- All 3 maps use 60×34 tiles of 32px (1920×1088, with 8px overflow clipped by viewport)
- All move entity positions and spawns operate in the new coordinate space
- Player spawns at the center of the new world, not overlapping any move entity
- Window opens at 1920×1080 native resolution

**Non-Goals:**
- No new art assets (tileset.png unchanged)
- No new game mechanics
- No camera scrolling or world beyond viewport bounds
- No changes to player speed, size, or movement logic

## Decisions

### Tile size stays 32px
Using 32px tiles avoids any change to `tileset.png` (32×96, 3 tiles). The grid becomes 60 columns × 34 rows = 1920×1088. The 8 vertical pixel overflow is invisible — the camera/renderer naturally clips at viewport edges. Alternative considered: switching to 40px tiles (48×27 grid for exact 1920×1080) would require stretching or recreating the tileset for minimal benefit.

### Viewport clips 8px overflow
With `FitViewport(1920, 1080)` and camera at `(960, 540)`, the visible area is X: 0–1920, Y: 0–1080. The tile grid extends to Y: 1088. Row 33 (Y: 1056–1088) shows only its top 24px — completely fine for solid-color tiles. No special handling needed.

### Coordinate positions use tile-grid alignment
Rather than scaling old coordinates (which produces fractional values), all move entity positions, spawns, and trigger zones snap to tile-grid multiples of 32px. This ensures pixel-perfect alignment with the rendered map.

### Move entity sizes reconsidered for new scale
Old move entities (e.g., 12×40) represented door-like zones on a 640-wide map. At 1920-wide, these same pixel sizes would look disproportionately tiny. Sizes should be adjusted to feel natural — exact dimensions left to implementation discretion, documented in maps.json.

## Risks / Trade-offs

- **[Low] 8px vertical overflow**: Bottom tile row shows 24 of its 32px. Visible only as a partial tile at the screen bottom. Mitigation: if objectionable, change to 60×33 tiles (1920×1056) centered with 12px black bars top/bottom.
- **[Low] Coordinate recalculation errors**: All maps.json positions must be hand-calculated. A wrong coordinate could send the player to the wrong spawn or create unreachable areas. Mitigation: test each move entity after implementation.
- **[None] Performance**: 60×34 = 2040 tiles (vs 300 before) is still trivial for any GPU. No performance concern.
