## Why

The game world is currently 640×480 (20×15 tiles of 32px), occupying a tiny fraction of a modern 1920×1080 display. Moving to a native 1920×1080 world lets the game fill the screen with meaningful content — bigger maps, more room for gameplay, and a visually immersive experience without black bars or pixel scaling artifacts.

## What Changes

- Game world resolution changes from 640×480 to 1920×1080
- `MAP_WIDTH` / `MAP_HEIGHT` constants updated
- `FitViewport` updated to native resolution
- All 3 `.tmx` map files recreated from 20×15 to 60×34 tiles (32px tiles, 1920×1088 — 8px overflow at bottom)
- All move entity coordinates in `maps.json` recalculated for the new 1920×1080 space
- Player spawn position recalculated for center of new world
- Window size in `Lwjgl3Launcher` updated to 1920×1080

## Capabilities

### New Capabilities
- `native-resolution`: The game world runs at native 1920×1080 with 60×34 tile maps, correct hit detection, move entity placement, and player spawning in the new coordinate space

### Modified Capabilities
<!-- No existing specs to modify -->

## Impact

- `core/.../GameplayScreen.java`: MAP_WIDTH, MAP_HEIGHT, FitViewport, camera, player spawn, clamping
- `assets/maps/map01.tmx`, `map02.tmx`, `map03.tmx`: new dimensions (60×34) and tile data
- `assets/data/maps.json`: all move entity x/y/width/height/spawnX/spawnY values
- `lwjgl3/.../Lwjgl3Launcher.java`: window size
- `assets/tileset.png`: visual appearance unchanged, but tile size decision (32px vs 40px) affects whether this stays or is re-exported
