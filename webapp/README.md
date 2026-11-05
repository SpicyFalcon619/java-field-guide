# Java Field Guide: the web app

Vite + React + TypeScript + Tailwind v4. A static site: no server, no database. Progress, saved lessons and settings live in the visitor's own browser.

## Running locally

```bash
npm install
npm run dev          # http://localhost:5173
npm run build        # production build into dist/
npm run lint
```

`npm run capture-outputs` and `npm run verify-outputs` need a JDK (`java` and `javac` on the PATH).

## Where lessons live

Every top-level folder of the repository (except `webapp`, `tools` and `Test`) is a **track**, drawn as one line on the home map. Inside it, every `.java` file is a lesson, shown in the order of its numeric filename prefix (`01_basic.java`, `02_...`). Sub-folders become sections in the sidebar.

The repository's `ROADMAP.md` lists the two courses. `src/lib/roadmap.ts` maps each course (a "basecamp") to its track folders: add a new track folder there, and write its blurb in `src/lib/categoryInfo.ts` and `src/lib/journey.ts`.

## Writing a lesson

A lesson is one `.java` file: explanation and code blocks, in the order the reader meets them.

- **The title** is the first comment, `/* Section: Title */`. The text after the first colon is the page title.
- **Explanation** is any line that starts with `///` (three slashes) at column 0. Ordinary `//` comments are code, wherever they sit, so an example keeps its own comments.
- **A code block** is everything between explanation lines. It must be a complete program with its own `main`, with its public class called `Main`, so a reader can copy it and run it as it is. A block with no `main` (a class shown on its own) is shown but has no Run button.
- `/// ### Topic 1: Title ###` starts a numbered section.
- A card is fenced by `/// ====...` lines. One whose first line is `WHAT YOU WILL LEARN` (bullets as `///   - item`) or `YOUR TURN` (numbered `///   1. item`) gets that card style.
- In prose: `- item` and `1. item` lines (indented three spaces) make lists; lines with a run of spaces in them (aligned columns, diagrams) stay preformatted; `/// --- Title -----` is a sub-heading; `` `backticks` `` and `name(...)` calls are shown as code.
- **Typed input.** `/// @input Noman | 35` goes right after a block and says what is typed for its `Scanner` calls, one line per call separated by ` | `. Several `@input` lines are several example runs; `/// @input(Label): ...` names one. Give one token per line when a program reads several numbers, because each typed line is echoed when it is read.
- **Files.** `/// @file example.txt: first line | second line` creates a file next to the program before it runs, so file-reading examples have something to read.

## After adding or editing a lesson: capture its output

The site never runs Java to show a lesson. Every "Run" shows real, pre-captured output from `src/lib/output-cache.json`:

```bash
npm run capture-outputs                 # every lesson
npm run capture-outputs -- 05_methods   # only paths containing this text
```

Each code block is compiled and run with your JDK (`scripts/capture-outputs.mjs`), through `java/Runner.java`, which feeds the typed input and records which line printed what. A block whose compile fails is reported with the compiler's message. Commit the updated JSON with the lesson.

`npm run verify-outputs` compares the captured output with what the author wrote down in Notion (`tools/expected.json`), and reports any recorded line the code does not print.

## Things not to do

- Do not put an `@input` directive inside a block; it belongs on the line after the block.
- Do not run an auto-formatter over a lesson: it rewrites the comments and spacing the explanations point at.
- Do not use decorative emoji anywhere on the site.
