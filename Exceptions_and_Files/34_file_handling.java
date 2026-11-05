/*
 * Exceptions and Files: File handling
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why programs read and write files, and the classes Java uses (the java.io package)
///   - Creating, writing, appending, reading and deleting a file
///   - Reading with Scanner and with BufferedReader, writing with FileWriter and BufferedWriter
///   - Why you must close a file, and how try-with-resources does it for you
/// ============================================================================


/// ### Topic 1: Why file handling? ###
/// Variables vanish the moment a program ends. A file is how you keep data between runs: scores, settings, a
/// shopping list, a log. We use file handling to read from or write to files (like `.txt`, `.csv`, etc.) from within
/// Java programs.
///
/// Java provides several classes for this in the `java.io` package (remember from the Scanner lesson that packages
/// need an `import`):
///    - `File` (a file or folder's name and details)
///    - `FileWriter`, `BufferedWriter` (writing)
///    - `FileReader`, `BufferedReader` (reading)
///    - `Scanner` (also usable for reading files, as you already know it)
///
/// IMPORTANT: Almost every file operation can fail (the file is missing, the disk is full, you are not allowed). So
/// these methods throw a CHECKED exception, `IOException`, and the compiler forces you to handle it. That is why every
/// example is wrapped in try and catch, using what you learned in the exceptions lessons.
///
/// NOTE: A relative name like "example.txt" means "in the folder the program was started from".

/// ### Topic 2: Creating a file (if it does not exist) ###

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            File myFile = new File("example.txt");
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}

/// `new File("example.txt")` does NOT create anything on disk. It only makes a Java object that stands for that name.
/// `createNewFile()` is the call that creates it, and it returns `true` if it made a new file or `false` if one was
/// already there.

/// ### Topic 3: Writing to a file (overwrites) ###

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("example.txt");
            writer.write("Hello, this is written to the file!");
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing.");
        }
    }
}

/// WARNING: `new FileWriter("example.txt")` ERASES whatever was in the file before and starts again. If the file does
/// not exist, it creates it, so `createNewFile()` is not even needed first.

/// ### Topic 4: Appending to a file ###
/// To add to the end instead of replacing, pass `true` as a second argument. That switches on "append mode".

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("example.txt", true); // true = append mode
            writer.write("\nThis line is appended.");
            writer.close();
            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("Error appending file.");
        }
    }
}

/// The `\n` at the start of the text begins a new line, because `write` does not add one by itself, unlike
/// `println`.

/// ### Topic 5: Reading from a file (using Scanner) ###
/// You already know `Scanner`. Give it a `File` instead of `System.in` and it reads from the file, line by line.
/// `hasNextLine()` asks "is there another line?", which is how the loop knows when to stop.
///
/// The file has to exist for the program to read it. For this lesson it already contains the two lines written above.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("example.txt");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
/// @file example.txt: Hello, this is written to the file! | This line is appended.

/// And what if the file is not there? That is exactly why the `catch` exists:

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner reader = new Scanner(new File("no_such_file.txt"));
            System.out.println(reader.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}

/// ### Topic 6: Deleting a file ###

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("example.txt");
        if (file.delete()) {
            System.out.println("Deleted file: " + file.getName());
        } else {
            System.out.println("File could not be deleted.");
        }
    }
}
/// @file example.txt: temporary

/// `delete()` returns `true` if it removed the file and `false` otherwise (for example, if it did not exist). It
/// does not throw an exception, so no try and catch is needed.

/// ### Topic 7: Reading a file using BufferedReader ###
/// A `BufferedReader` reads text in larger chunks, which is faster for big files, and gives you `readLine()`, which
/// returns the next line or `null` at the end of the file. The loop `while ((line = br.readLine()) != null)` is the
/// standard idiom: read a line, store it, and stop when there is none.

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("example.txt"));

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);  // Print each line
            }

            br.close(); // Always close!
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
/// @file example.txt: Hello, this is written to the file! | This line is appended.

/// ### Topic 8: Writing to a file using BufferedWriter ###

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("example.txt"));

            bw.write("Hello from Java!");
            bw.newLine(); // Inserts a newline
            bw.write("This is a second line.");

            bw.close(); // Always close!
            System.out.println("Write successful!");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}

/// `newLine()` inserts the correct line break for the computer you are on, which is more portable than writing
/// `\n` by hand.

/// ### Topic 9: Appending with BufferedWriter ###

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("example.txt", true)); // true = append

            bw.newLine();
            bw.write("This is an appended line.");

            bw.close();
            System.out.println("Append successful!");
        } catch (IOException e) {
            System.out.println("Error appending file: " + e.getMessage());
        }
    }
}

/// ### Topic 10: Cleaning up: always close the file ###
/// Why close a reader or writer?
///    - It ensures data is flushed from the buffer to the file. A writer keeps text in memory and only sends it to the
///      disk when full or closed, so a file you forgot to close may be missing its last lines.
///    - It releases system resources (an operating system allows only so many open files).
///    - It prevents memory leaks and corruption.
///
/// The problem with `close()` at the end of the try block is that if an exception happens before it, `close()` is
/// skipped. The better way is `try-with-resources`.

/// ### Topic 11: Writing with try-with-resources ###
/// Put the thing to close in brackets after `try`. Java closes it for you at the end of the block, whether it finished
/// normally or with an exception. No `close()` call and no `finally` is needed.

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("file.txt"))) {
            bw.write("Hello!");
            System.out.println("Written, and closed automatically.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

/// Reading works the same way, and now the file closes itself even if a line cannot be read:

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("notes.txt"))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                System.out.println(count + ": " + line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
/// @file notes.txt: buy milk | call mum | finish the Java lab

/// TIP: Prefer try-with-resources for every file, Scanner on a file, and anything else with a `close()` method.
///
/// REMEMBER: File operations throw `IOException`, so wrap them in try and catch. `FileWriter` replaces, and
/// `FileWriter(name, true)` appends. Close what you open, or better, let try-with-resources do it.

/// ============================================================================
/// YOUR TURN
///   1. Write a program that writes your name and your city on two lines of `me.txt`, then reads the file back and
///      prints it.
///   2. Append a third line to the file and print the file again.
///   3. Count how many lines a file has, using a BufferedReader.
///   4. Ask the user for a name, and write "Hello, <name>" to a file. (Use Scanner for the typing, FileWriter for the
///      file.)
///   5. Read a file of numbers, one per line, and print their sum. Use `Integer.parseInt`.
///   6. Try to read a file that does not exist, and make the program print a helpful message instead of crashing.
/// ============================================================================
