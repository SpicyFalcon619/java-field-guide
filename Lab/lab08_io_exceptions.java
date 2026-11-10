/*
 * Lab: Lab 8: Input and output, and exceptions
 */

/// ============================================================================
/// WHAT YOU WILL PRACTISE
///   - Writing, appending and reading text files
///   - Handling exceptions with try, catch, finally and try-with-resources
///   - Throwing and declaring exceptions, and writing a custom exception
///   - Putting both together: reading a file of records safely
/// ============================================================================


/// ### Task 1: Save and read back a note ###
/// Write three lines to `notes.txt`, then read the file and print it with line numbers.

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileWriter("notes.txt"))) {
            out.println("Buy milk");
            out.println("Finish the lab report");
            out.println("Call mum");
        } catch (IOException e) {
            System.out.println("Could not write: " + e.getMessage());
            return;
        }

        try (BufferedReader in = new BufferedReader(new FileReader("notes.txt"))) {
            String line;
            int number = 1;
            while ((line = in.readLine()) != null) {
                System.out.println(number++ + ". " + line);
            }
        } catch (IOException e) {
            System.out.println("Could not read: " + e.getMessage());
        }
    }
}

/// ### Task 2: Append to a log ###
/// Each time the program runs it should add one line to `log.txt` without erasing the old ones. (`FileWriter` with `true`.)

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try (FileWriter log = new FileWriter("log.txt", true)) {
            log.write("Program started\n");
        } catch (IOException e) {
            System.out.println("Logging failed");
        }

        try (BufferedReader in = new BufferedReader(new FileReader("log.txt"))) {
            String line;
            int count = 0;
            while ((line = in.readLine()) != null) {
                count++;
            }
            System.out.println("The log has " + count + " lines");
        } catch (IOException e) {
            System.out.println("Could not read the log");
        }
    }
}
/// @file log.txt: Program started | Program started

/// ### Task 3: Count lines, words and characters ###
/// Read a text file and print how many lines, words and characters it has.

import java.io.*;

public class Main {
    public static void main(String[] args) {
        int lines = 0, words = 0, chars = 0;

        try (BufferedReader in = new BufferedReader(new FileReader("story.txt"))) {
            String line;
            while ((line = in.readLine()) != null) {
                lines++;
                chars += line.length();
                if (!line.trim().isEmpty()) {
                    words += line.trim().split("\\s+").length;
                }
            }
            System.out.println(lines + " lines, " + words + " words, " + chars + " characters");
        } catch (FileNotFoundException e) {
            System.out.println("story.txt was not found");
        } catch (IOException e) {
            System.out.println("Read error: " + e.getMessage());
        }
    }
}
/// @file story.txt: Once upon a time | there was a small robot | who loved to learn Java

/// ### Task 4: Student marks from a file ###
/// `marks.txt` has one record per line in the form `name,marks`. Read it, skip the lines that are not valid (a missing comma or
/// non-numeric marks) with a message, and print the average and the top student.

import java.io.*;

public class Main {
    public static void main(String[] args) {
        double total = 0;
        int count = 0;
        String best = "";
        int bestMarks = -1;

        try (BufferedReader in = new BufferedReader(new FileReader("marks.txt"))) {
            String line;
            int lineNumber = 0;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    System.out.println("Line " + lineNumber + " skipped: " + line);
                    continue;
                }
                try {
                    int marks = Integer.parseInt(parts[1].trim());
                    total += marks;
                    count++;
                    if (marks > bestMarks) {
                        bestMarks = marks;
                        best = parts[0].trim();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNumber + " has bad marks: " + parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read marks.txt");
            return;
        }

        if (count > 0) {
            System.out.printf("Average %.1f, top student %s (%d)%n", total / count, best, bestMarks);
        }
    }
}
/// @file marks.txt: Rafi,91 | Mim,67 | broken line | Anik,abc | Dee,80

/// ### Task 5: A custom exception ###
/// Write `InvalidMarksException` and a method `checkMarks(int)` that throws it for values outside 0 to 100. Test it with three
/// values and catch it in `main`.

class InvalidMarksException extends Exception {
    private final int marks;

    InvalidMarksException(int marks) {
        super("Marks must be between 0 and 100, but were " + marks);
        this.marks = marks;
    }

    int getMarks() {
        return marks;
    }
}

public class Main {
    static void checkMarks(int marks) throws InvalidMarksException {
        if (marks < 0 || marks > 100) {
            throw new InvalidMarksException(marks);
        }
        System.out.println(marks + " is valid");
    }

    public static void main(String[] args) {
        int[] tests = {85, 120, -5};
        for (int m : tests) {
            try {
                checkMarks(m);
            } catch (InvalidMarksException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

/// ### Task 6: finally and the order of events ###
/// Predict the exact output, then run it. When does `finally` print relative to the other lines?

public class Main {
    static int divide(int a, int b) {
        try {
            System.out.println("dividing " + a + " by " + b);
            return a / b;
        } catch (ArithmeticException e) {
            System.out.println("caught: " + e.getMessage());
            return -1;
        } finally {
            System.out.println("finally runs");
        }
    }

    public static void main(String[] args) {
        System.out.println("result 1: " + divide(10, 2));
        System.out.println("result 2: " + divide(10, 0));
    }
}

/// ### Task 7: Copy a file ###
/// Copy `source.txt` to `copy.txt` line by line, then report how many lines were copied. Handle a missing source file.

import java.io.*;

public class Main {
    public static void main(String[] args) {
        int copied = 0;
        try (BufferedReader in = new BufferedReader(new FileReader("source.txt"));
             BufferedWriter out = new BufferedWriter(new FileWriter("copy.txt"))) {
            String line;
            while ((line = in.readLine()) != null) {
                out.write(line);
                out.newLine();
                copied++;
            }
            System.out.println("Copied " + copied + " lines");
        } catch (FileNotFoundException e) {
            System.out.println("The source file does not exist");
        } catch (IOException e) {
            System.out.println("Copy failed: " + e.getMessage());
        }
    }
}
/// @file source.txt: alpha | beta | gamma | delta

/// ### Check yourself ###
///   - What is the difference between a checked and an unchecked exception? Which is `IOException`?
///   - Why does try-with-resources need no `close()` call?
///   - What does `new FileWriter(name, true)` do differently from `new FileWriter(name)`?
///   - In what order do `try`, `catch` and `finally` run when an exception happens?

/// ### Try next ###
///   1. Read numbers from a file and print their sum, skipping bad lines.
///   2. Write a phone book to a file, and a program that looks a name up in it.
///   3. Add a custom `InsufficientFundsException` to the bank account from Lab 3.
///   4. Read a file in reverse line order.
