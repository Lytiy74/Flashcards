# Flashcards (Java)

A console flashcards app for studying. It is the JetBrains Academy / Hyperskill "Flashcards (Java)" course project (Java 11+), completed across 7 stages.

## Features

- add and remove cards (term + definition, no duplicates);
- practice mode: the app asks for random cards and tells you if your answer belongs to another card;
- import and export cards to a file (including each card's mistake count);
- session log: every console input and output line can be saved to a file;
- statistics: find the hardest cards and reset mistake counters;
- command-line arguments to import cards on startup and export them on exit.

## Running

```bash
./gradlew :Flashcards__Java_-task:run --console=plain
```

Or run the `flashcards.Main` class from IntelliJ IDEA. Use `./gradlew projects` to confirm the module name.

### Command-line arguments

| Argument         | Description                                         |
|------------------|-----------------------------------------------------|
| `-import <file>` | load cards from a file right after startup          |
| `-export <file>` | save cards to a file on exit (`exit` command)       |

```bash
./gradlew :Flashcards__Java_-task:run --args="-import cards.txt -export cards.txt"
```

## Commands

On startup the app shows this prompt:

```
Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):
```

| Command        | Action                                                        |
|----------------|---------------------------------------------------------------|
| `add`          | add a card (term and definition must both be unique)          |
| `remove`       | remove a card by its term                                     |
| `import`       | load cards from a file                                        |
| `export`       | save cards to a file                                          |
| `ask`          | quiz yourself on a chosen number of cards                     |
| `log`          | save the session log to a file                                |
| `hardest card` | show the card(s) with the most mistakes                       |
| `reset stats`  | reset all mistake counters                                    |
| `exit`         | quit (and run the `-export` argument if one was given)        |

## Card file format

Each card takes three lines: term, definition, mistake count.

```
capital of France
Paris
2
```

Incomplete records or a non-numeric mistake count result in `Error reading file.`

## Code structure

All application code lives in `Flashcards (Java)/task/src/flashcards/`:

| File                 | Purpose                                                    |
|----------------------|------------------------------------------------------------|
| `Main`               | entry point                                                |
| `CliArgs`            | parses `-import` / `-export`                               |
| `Menu`               | command loop, all prompts and output                       |
| `Messages`           | output text constants                                      |
| `FlashcardGame`      | game logic (no console I/O)                                |
| `Deck`, `FlashCard`  | data model                                                 |
| `CardRepository`     | reads/writes card and log files                            |
| `InputOutputConsole` | stdin/stdout wrapper that records the log                  |

## Tests

The stage checker is written with hs-test (`Flashcards (Java)/task/test/FlashcardsTest.java`):

```bash
./gradlew :Flashcards__Java_-task:test
```

Network access is required to download `hs-test` (jitpack and JetBrains Maven).

## Requirements

- A JDK (the code uses Java 21 APIs such as `List.getFirst()`; the toolchain follows the installed JDK);
- the Gradle Wrapper is included in the repository.

## Repository layout

- `Flashcards (Java)/task/` — the active stage (code and tests);
- `Flashcards (Java)/<stage name>/` — each stage's spec (`task.html`) and metadata;
- `util/` — shared course helper module;
- `*-info.yaml` — course metadata managed by the IDE plugin (don't edit by hand).
