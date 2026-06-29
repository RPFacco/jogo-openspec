## Context

All Screen classes (`GameplayScreen`, `MainMenuScreen`, `QuizScreen`, `GameOverScreen`, `VictoryScreen`) store a reference to the main `OopQuest` instance in a field called `jogoGame` — a mix of Portuguese and English. libGDX convention is to use `app` for the main `Game` reference.

## Goals / Non-Goals

**Goals:**
- Rename `jogoGame` field to `app` in all 5 Screen classes
- Rename constructor parameter to `app` in all 5 Screen classes
- Update all internal references throughout each file

**Non-Goals:**
- No behavioral changes
- No structural refactoring
- No renaming of the `OopQuest` class or any other identifiers

## Decisions

- **`app` over alternatives** — `game` would shadow `extends Game`, `oopQuest` is verbose, `quest` loses context. `app` is idiomatic libGDX and unambiguous.

## Risks / Trade-offs

- Low risk: pure rename with no logic changes. Compiler will catch any missed references.
