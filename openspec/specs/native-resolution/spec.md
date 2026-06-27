## ADDED Requirements

### Requirement: World resolution
The game world SHALL render at native 1920×1080 resolution using a FitViewport.

#### Scenario: Viewport matches new resolution
- **WHEN** the game starts
- **THEN** the FitViewport SHALL be configured with width 1920 and height 1080

#### Scenario: Window matches new resolution
- **WHEN** the game window opens
- **THEN** the window SHALL be 1920×1080 pixels

#### Scenario: Camera centers on new world
- **WHEN** the camera initializes
- **THEN** the camera SHALL be positioned at (960, 540)

### Requirement: Map dimensions
Each map SHALL be 60 columns × 34 rows of 32px tiles, producing a 1920×1088 pixel grid.

#### Scenario: Map tile dimensions
- **WHEN** a .tmx map is loaded
- **THEN** the map SHALL have exactly 60 tiles width and 34 tiles height

#### Scenario: Tile set unchanged
- **WHEN** a .tmx map is loaded
- **THEN** the map SHALL reference `../tileset.png` with tile size 32×32

### Requirement: Player spawn
The player SHALL spawn at the center of the new world on map01.

#### Scenario: Spawn coordinates
- **WHEN** the game starts on map01
- **THEN** the player SHALL be positioned at (948, 528) — center minus half player size (24×24)

### Requirement: Player clamping
The player SHALL be clamped within the new world bounds (0–1920 on X, 0–1080 on Y).

#### Scenario: Clamp to right edge
- **WHEN** the player moves to x > 1896
- **THEN** the player x SHALL be clamped to 1896

#### Scenario: Clamp to top edge
- **WHEN** the player moves to y > 1056
- **THEN** the player y SHALL be clamped to 1056

### Requirement: Click-to-move bounds
Click-to-move target validation SHALL accept clicks within the 1920×1080 world bounds.

#### Scenario: Click within bounds
- **WHEN** the player clicks at x=1000, y=500
- **THEN** the player SHALL move toward that position

#### Scenario: Click outside bounds ignored
- **WHEN** the player clicks at x=2000, y=500
- **THEN** the click SHALL be ignored

### Requirement: Move entities in new coordinate space
Move entities and their spawn coordinates SHALL be defined in the 1920×1080 coordinate space in maps.json.

#### Scenario: Move entity positions
- **WHEN** a move entity is rendered
- **THEN** its position SHALL be within the 0–1920, 0–1080 bounds

#### Scenario: Spawn positions valid
- **WHEN** a player transitions to a new map via a move entity
- **THEN** the player SHALL spawn at the specified spawnX/spawnY within the target map's bounds

### Requirement: 8px overflow handling
The tile grid extending 8px past the viewport height SHALL NOT cause visual or gameplay issues.

#### Scenario: Last tile row partially visible
- **WHEN** the player is at y=0 (bottom of map)
- **THEN** the bottom of row 0 SHALL be visible at y=0

#### Scenario: Clipping invisible
- **WHEN** the camera renders at y=540
- **THEN** tiles at y>1080 SHALL NOT be visible
- **THEN** no error or warning SHALL be produced by the renderer
