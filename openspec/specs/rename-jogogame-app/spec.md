# rename-jogogame-app Specification

## Purpose

Rename the `jogoGame` field and constructor parameter to `app` across all Screen classes for naming consistency.

## Requirements

### Requirement: Rename jogoGame to app
The field and constructor parameter `jogoGame` in all Screen classes SHALL be renamed to `app`.

#### Scenario: Field renamed
- **WHEN** inspecting any Screen class
- **THEN** the field storing the `OopQuest` reference SHALL be named `app`
- **AND** the constructor parameter SHALL be named `app`

#### Scenario: All references updated
- **WHEN** any Screen class uses the `OopQuest` reference
- **THEN** it SHALL use `app` instead of `jogoGame`
- **AND** the project SHALL compile without errors
