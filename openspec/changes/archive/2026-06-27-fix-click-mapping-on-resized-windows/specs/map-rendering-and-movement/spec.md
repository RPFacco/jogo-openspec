## MODIFIED Requirements

### Requirement: Player click-to-move movement
The player SHALL move toward the clicked position on the map when the mouse is clicked, regardless of window size or fullscreen state.

#### Scenario: Player clicks on a valid position within map bounds at default window size
- **WHEN** the player left-clicks on a position within the map area at the default 640x480 window size
- **THEN** the player character SHALL move toward that position at a constant speed
- **THEN** the player SHALL stop when reaching the clicked position

#### Scenario: Player clicks on a valid position at a larger window size
- **WHEN** the player resizes the window to a larger size and left-clicks on a position within the map area
- **THEN** the click position SHALL be correctly mapped to the world coordinate
- **THEN** the player character SHALL stop at the exact clicked world position

#### Scenario: Player clicks in fullscreen mode
- **WHEN** the game is in fullscreen and the player left-clicks on a position within the map area
- **THEN** the click position SHALL be correctly mapped to the world coordinate
- **THEN** the player character SHALL stop at the exact clicked world position

#### Scenario: Player clicks outside map bounds
- **WHEN** the player clicks outside the map area (in the letterbox bars)
- **THEN** no movement SHALL occur
