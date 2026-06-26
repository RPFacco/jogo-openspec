# Game Design — JogoOpenSpec

## Concept

A 2D top-down educational game in which the player explores interconnected maps, defeats enemies, and interacts with peaceful NPCs to answer quizzes about Object-Oriented Programming. The goal is to answer all available quizzes in the game.

---

## Core Mechanics

### Movement
The player moves freely across the map in all directions (top-down). Upon reaching a map border, a transition occurs to the map connected to that direction.

### Lives
The player starts with **5 lives**. Lives are lost by:
- A wrong answer in a quiz
- Damage dealt by enemies

When lives reach zero, the **Game Over** screen is displayed.

### Combat
The player attacks with **projectiles**. Enemies have varied attacks (defined individually per enemy). Defeating an enemy triggers the quiz linked to it.

### Quizzes
Each quiz has one question and four answer choices. The player **must answer correctly** to exit the quiz screen. Wrong answers cost lives, but the quiz remains active until answered correctly or the game ends via Game Over.

Quizzes are triggered in two ways:
- **Peaceful NPC:** triggers the quiz automatically upon proximity
- **Enemy NPC:** triggers the quiz linked to it upon being defeated

Each NPC has a specific, fixed quiz. The quiz bank is stored in `assets/data/quizzes.json`, indexed by `quiz_id`. Total number of quizzes: **to be defined**.

---

## NPCs

| Type | Behavior | Quiz trigger |
|---|---|---|
| Peaceful | Static or patrolling. Does not attack. | Player proximity |
| Enemy | Varied attacks (defined per NPC). | Upon being defeated by the player |

---

## Maps

The architecture is **data-driven**: adding a new map requires no changes to any `.java` file. The process is:
1. Create `assets/maps/new-map.tmx` in Tiled with layout, NPCs, and custom properties (`quiz_id` per NPC)
2. Register the map and its directional connections in `assets/data/maps.json`

Map transitions are **directional** — each border (north, south, east, west) can be connected to a specific map, configured in `maps.json`. The player can move freely between connected maps. Total number of maps: **to be defined**.

### Asset structure

```
assets/
├── maps/
│   ├── map01.tmx        # visual layout + NPC positions with quiz_id
│   └── map02.tmx
└── data/
    ├── maps.json        # map list and directional connections between maps
    └── quizzes.json     # quiz bank indexed by quiz_id
```

---

## Screens and Flow

```
Main Menu
    │
    └─▶ Gameplay (current map)
            │
            ├─▶ Quiz Screen
            │       │
            │       └─▶ Gameplay (on correct answer)
            │
            ├─▶ Game Over (lives = 0)
            │       │
            │       └─▶ Main Menu (restart)
            │
            └─▶ Game Completed (all quizzes answered)
```

---

## Out of Scope

- Maps and quizzes are hand-crafted data files — no procedural generation
