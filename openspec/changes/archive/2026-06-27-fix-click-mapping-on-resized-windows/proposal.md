## Why

When the game window is resized or set to fullscreen, clicking to move the player is misaligned: the click position doesn't map to the correct world position, so the player stops short of where the mouse was clicked. This makes the game unplayable at anything other than the default 640x480 window size.

## What Changes

- Replace `camera.unproject()` with `viewport.unproject()` in `GameplayScreen.handleInput()` to properly account for viewport letterboxing
- No other changes — `FitViewport` behaviour itself stays the same

## Capabilities

### New Capabilities
None — this is a bug fix to existing behaviour.

### Modified Capabilities
- `map-rendering-and-movement`: update the click-to-move requirement to specify that click positions are correctly mapped regardless of window size or viewport scaling

## Impact

- `core/src/main/java/com/jogoopenspec/game/GameplayScreen.java` — single line change in `handleInput()`
