# Java Field Guide

A guide to Java and object oriented programming for people who are starting from zero. It follows two university courses, CSE 1115 (theory) and CSE 1116 (lab). Every lesson explains an idea in plain words, then shows complete programs that really run, beside the real output they print.

**[Read it as a site: the Java Field Guide](https://spicyfalcon619.github.io/java-field-guide/)**

It is one of three guides built on the same app. The others are the [AI Engineering Field Guide](https://spicyfalcon619.github.io/ai-engineering-journey/) (JavaScript to a full AI app) and the [ML Field Guide](https://spicyfalcon619.github.io/ml-research-journey/) (Python to deep learning). They link to each other from the top bar.

## The roadmap

| Basecamp | What it covers | Status |
|---|---|---|
| 0. Theory | The 24 classes of CSE 1115, from the first program to threads: Java basics, classes and objects, inheritance and polymorphism, encapsulation, abstraction and interfaces, exceptions and files, collections, threads and GUI | Written |
| 1. Lab | The twelve labs of CSE 1116: tasks to try yourself, each with a worked solution you can run | In progress |
| 2. Beyond the course | What real Java projects use but the course skips: enums, inner classes, lambdas, streams, records, dates and times | In progress |

The checklist is [ROADMAP.md](ROADMAP.md); the site reads it for its progress view.

## What the site gives you

- **Complete programs, not fragments.** Every code block has its own `main`, so it can be copied, compiled and run exactly as shown.
- **Real output.** Each block was compiled and run with a real JDK; the output under it is what it printed.
- **Real Java in your browser.** Edit a block, type your own input for `Scanner`, or use the Sandbox. The code is compiled and run in the page, so nothing you type is sent anywhere.
- **A glossary.** Words such as *constructor* and *polymorphism* are underlined where they first appear; hover or tap for the meaning.
- **Search** (`Ctrl+K`), **progress** you can tick off, and several themes. Progress stays in your own browser.

## What's in the repo

| Folder | What it holds |
|---|---|
| `Java_Basics`, `Classes_and_Objects`, `Inheritance_and_Polymorphism`, ... | The theory track: one folder per section, one `.java` file per lesson |
| `Lab` | The lab course |
| `Beyond_the_Course` | Modern and practical Java outside the course outline |
| `webapp/` | The website, and the scripts that capture real output |
| `tools/` | One-off tools, such as the converter that built the first lessons from Notion |
| `ROADMAP.md` | The course outlines as a checklist |

The lessons are plain `.java` files, so they can be read and run without the site.

## How a lesson is written

A lesson is one `.java` file made of explanation and code blocks.

```java
/*
 * Java Basics: Reading input with Scanner
 */

/// ### Topic 1: Why Scanner needs an import ###
/// Java keeps its tools in packages. Scanner lives in java.util, so we ask for it first.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.println("Hello " + name);
    }
}
/// @input Noman
```

- Lines starting with `///` (three slashes, at column 0) are the explanation. Everything else, including ordinary `//` comments, is code.
- `/// @input a | b` is what is typed for the program's `Scanner` calls (` | ` separates lines; each `@input` line is one example run). `/// @file name: one | two` creates a file the program can read.
- The full writing guide is in [webapp/README.md](webapp/README.md).

## Running the site locally

```bash
cd webapp
npm install
npm run dev
```

## How code runs

1. **Sample output** under every block is captured ahead of time. `npm run capture-outputs` compiles and runs every block with a real JDK (it needs `java` and `javac` on your PATH) and stores what each printed in `webapp/src/lib/output-cache.json`. Run it after adding or editing a lesson, and commit the result. `npm run verify-outputs` compares the captured output with the output originally written down in Notion (`tools/expected.json`).

2. **Live Java in the reader's browser**, through [CheerpJ](https://cheerpj.com). The sample output is shown instantly; the live run is the slower, optional extra (the first run in a visit takes about half a minute while the compiler warms up).
   - `webapp/public/runner.html` starts CheerpJ in a hidden frame.
   - `webapp/java/LiveRunner.java` (compiled to `webapp/public/java/live.jar`) compiles the code, runs it and streams the output back. When the program reads the keyboard, it asks the page for a line, so `Scanner` programs wait for the reader to type.
   - After editing `LiveRunner.java`, run `npm run build-runner` (needs a JDK) and commit the jar.
   - `npm run capture-outputs` also saves each block compiled as `webapp/public/classes/<lesson>/<line>.jar`, so a block the reader has not edited runs without the compiler. Commit those too.
   - Swing windows are not supported in the browser.

## Credits

Built with [CheerpJ](https://cheerpj.com) (Java in the browser, free for personal and educational use) and the Field Guide web app. The first lessons come from the author's own Notion notes.
