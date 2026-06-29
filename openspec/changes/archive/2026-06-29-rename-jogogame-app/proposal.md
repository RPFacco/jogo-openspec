## Why

The field name `jogoGame` mixes Portuguese ("jogo") with English ("Game"), creating an inconsistent naming pattern. This is a simple rename to `app`, which is the idiomatic convention in libGDX projects for the main `Game` instance passed to screens.

## What Changes

- Rename field `jogoGame` → `app` in all Screen classes
- Rename constructor parameter `jogoGame` → `app` in all Screen classes
- Update all references to use `app` instead of `jogoGame`

Files affected: `GameplayScreen.java`, `MainMenuScreen.java`, `QuizScreen.java`, `GameOverScreen.java`, `VictoryScreen.java`

## Capabilities

### New Capabilities

- `rename-jogogame-app`: Rename the `jogoGame` field to `app` across all Screen classes

### Modified Capabilities

None — no functional behavior changes.

## Impact

- 5 files modified (cosmetic rename only)
- No API or behavioral changes
- No dependency changes
