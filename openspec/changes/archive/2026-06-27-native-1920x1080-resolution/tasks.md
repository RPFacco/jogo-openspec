## 1. Core Code Changes

- [x] 1.1 Update `GameplayScreen.java`: change `MAP_WIDTH` from 640 to 1920, `MAP_HEIGHT` from 480 to 1080
- [x] 1.2 Update `GameplayScreen.java`: change `FitViewport` from (640, 480) to (1920, 1080)
- [x] 1.3 Update `GameplayScreen.java`: recalculate player spawn to `1920/2 - 12 = 948, 1080/2 - 12 = 528`
- [x] 1.4 Update `Lwjgl3Launcher.java`: change `setWindowedMode(640, 480)` to `setWindowedMode(1920, 1080)`

## 2. TMX Map Files

- [x] 2.1 Recreate `map01.tmx` with `width="60"` `height="34"` and 60×34 CSV of tile ID 1 (green)
- [x] 2.2 Recreate `map02.tmx` with `width="60"` `height="34"` and 60×34 CSV of tile ID 2 (blue)
- [x] 2.3 Recreate `map03.tmx` with `width="60"` `height="34"` and 60×34 CSV of tile ID 3 (gold)

## 3. Entity Coordinates in maps.json

- [x] 3.1 Calculate and set new move entity positions and sizes for map01
- [x] 3.2 Calculate and set new move entity positions, sizes, and spawn coords for map02
- [x] 3.3 Calculate and set new move entity positions, sizes, and spawn coords for map03
- [x] 3.4 Verify no move entity overlaps with its own map's player spawn point

## 4. Verification

- [x] 4.1 Build the project: `./gradlew build`
- [x] 4.2 Launch the game and confirm window is 1920×1080
- [x] 4.3 Confirm player starts on map01 at center of green map
- [x] 4.4 Test all map transitions via move entities (map01↔map02↔map03)
- [x] 4.5 Confirm clamping works at new world edges
