# Java Field Guide

A guide to Java and object oriented programming for people who are starting from zero. It follows two university courses (CSE 1115 theory and CSE 1116 lab), and every lesson explains an idea in plain words, then shows complete programs that really run, beside the real output they print.

The site is a Vite + React + TypeScript app in [`webapp/`](webapp). The lessons themselves are plain `.java` files in the folders at the top of this repository, so they can be read and run without the site.

## What is here

| Folder | What it holds |
|---|---|
| `Java_Basics`, `Classes_and_Objects`, `Inheritance_and_Polymorphism`, ... | The theory track, one folder per section, one `.java` file per lesson |
| `Lab` | The twelve labs of the lab course |
| `webapp/` | The website, and the scripts that capture real output |
| `tools/` | One-off tools, such as the converter that built the first lessons from Notion |
| `ROADMAP.md` | The two course outlines as a checklist; the site reads it for its progress view |

## How a lesson is written

A lesson is one `.java` file made of explanation and code blocks. Every code block is a complete program with its own `main`, so it can be copied, compiled and run exactly as shown.

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
- The full writing guide is in [`webapp/README.md`](webapp/README.md).

## Running the site

```bash
cd webapp
npm install
npm run dev
```

The site never runs Java to show a lesson. `npm run capture-outputs` compiles and runs every block with a real JDK (it needs `java` and `javac` on your PATH) and stores what each printed in `webapp/src/lib/output-cache.json`. Run it after adding or editing a lesson, and commit the result.

`npm run verify-outputs` compares the captured output with the output originally written down in Notion (`tools/expected.json`).

## Credits

Built with [CheerpJ](https://cheerpj.com) (Java in the browser, free for personal and educational use) and the Field Guide web app. The first lessons come from the author's own Notion notes.
