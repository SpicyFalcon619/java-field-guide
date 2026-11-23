/*
 * Practice: Java I/O problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Reading a text file line by line (`BufferedReader`) and character by character (`FileReader`)
///   - Writing a text file with `BufferedWriter`
///   - Copying a binary file with `InputStream` and `OutputStream`, and tracking the progress
///   - Closing files safely with try-with-resources, and handling `FileNotFoundException` and `IOException`
/// ============================================================================

/// Three bigger problems on files. Each program below creates or is given the files it needs, so you can run it as it is. When
/// you try your own version on your computer, put the input files next to the program.

/// ### Problem 1: File concatenation ###
/// Write a program that joins the contents of two text files into a third. It asks the user for the names of the two input
/// files and the output file. It reads each input line by line and writes the lines of the first file followed by the lines
/// of the second into the output file, then closes every stream.
///
/// The requirements, in short:
///
/// - Prompt for the two input names and the output name.
/// - Read both inputs line by line.
/// - Put the content of the second file below the content of the first.
/// - Keep every line as it was, and close all the streams.

import java.io.*;
import java.util.Scanner;

public class Main {
    // Copies every line of one file onto the end of an already open writer.
    static void copyLines(String fileName, BufferedWriter out) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = in.readLine()) != null) {
                out.write(line);
                out.newLine();
            }
        }                                              // the reader is closed here, even after an error
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("First input file: ");
        String first = scan.nextLine();
        System.out.print("Second input file: ");
        String second = scan.nextLine();
        System.out.print("Output file: ");
        String output = scan.nextLine();

        try (BufferedWriter out = new BufferedWriter(new FileWriter(output))) {
            copyLines(first, out);
            copyLines(second, out);
            System.out.println("Done. " + output + " now holds both files.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: one of the input files was not found. Check the names.");
            return;
        } catch (IOException e) {
            System.out.println("Error while copying: " + e.getMessage());
            return;
        }

        System.out.println("--- " + output + " ---");
        try (BufferedReader in = new BufferedReader(new FileReader(output))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Could not read the result back: " + e.getMessage());
        }
    }
}
/// @file input1.txt: Java is a programming language. | It runs on the JVM.
/// @file input2.txt: Files hold data. | Streams move it. | Always close them.
/// @input input1.txt | input2.txt | output.txt

/// The two copies happen inside ONE `try (...)` that owns the output writer, so the output is opened once and both inputs
/// are appended to it in order. Each `try-with-resources` closes its file automatically, which covers the "close all file
/// streams" rule without a single `close()` call.

/// ### Problem 2: File copy with a progress display ###
/// Copy one binary file into another and show the progress as a percentage. Keep the two file names in the String variables
/// `source` and `destination` (for example "src.mp4" and "copy.mp4"). Create an `InputStream` and an `OutputStream`, show the
/// size of the source with `available()`, then use a `while` loop that `read()`s a byte and `write()`s it, counting the bytes
/// copied so far in `copiedBytes`. In the loop, print the progress as "File copied XX.XX%". Close both streams at the end,
/// and handle I/O exceptions gracefully.
///
/// NOTE: A real video would make this page very long, so the program first writes a tiny stand-in file called `src.mp4` with
/// 20 bytes in it. Delete that part and the program copies any binary file you like.

import java.io.*;

public class Main {
    // Only for this demo: makes a small file to copy.
    static void makeDemoFile(String name, int size) throws IOException {
        try (OutputStream out = new FileOutputStream(name)) {
            for (int i = 0; i < size; i++) {
                out.write(i * 7);
            }
        }
    }

    public static void main(String[] args) {
        String source = "src.mp4";
        String destination = "copy.mp4";

        try {
            makeDemoFile(source, 20);
        } catch (IOException e) {
            System.out.println("Could not create the demo file");
            return;
        }

        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {

            int total = in.available();                      // how many bytes the source holds
            System.out.println("Size of " + source + ": " + total + " bytes");
            if (total == 0) {
                System.out.println("Nothing to copy: the file is empty");
                return;
            }

            int copiedBytes = 0;
            int b;
            while ((b = in.read()) != -1) {                  // -1 means the end of the file
                out.write(b);
                copiedBytes++;
                double percent = copiedBytes * 100.0 / total;
                System.out.printf("File copied %.2f%%%n", percent);
            }
            System.out.println("Copy finished: " + copiedBytes + " bytes written to " + destination);
        } catch (FileNotFoundException e) {
            System.out.println("Error: the source file was not found");
        } catch (IOException e) {
            System.out.println("Error while copying: " + e.getMessage());
        }
    }
}

/// `copiedBytes * 100.0` uses `100.0` (a double) on purpose. With `100` and two ints, Java would divide as whole numbers and the
/// percentage would jump from 0 to 100. In `printf`, `%%` prints a real percent sign.
///
/// TIP: Reading ONE byte at a time is slow for a big file, and printing a line per byte is far too noisy. Real programs read a
/// whole chunk (a `byte[]` buffer) and print only when the progress moves on. Here is that version, on a 50,000 byte file,
/// printing about every 10 percent:

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String source = "src.mp4";
        String destination = "copy.mp4";

        try (OutputStream demo = new FileOutputStream(source)) {      // a 50,000 byte stand-in file
            demo.write(new byte[50_000]);
        } catch (IOException e) {
            System.out.println("Could not create the demo file");
            return;
        }

        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(destination)) {

            int total = in.available();
            byte[] buffer = new byte[4096];
            int copiedBytes = 0;
            int nextMark = 10;                       // print again when the progress passes this percent
            int count;

            System.out.println("Size of " + source + ": " + total + " bytes");
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);         // write only the bytes that were really read
                copiedBytes += count;

                double percent = copiedBytes * 100.0 / total;
                if (percent >= nextMark) {
                    System.out.printf("File copied %.2f%%%n", percent);
                    nextMark += 10;
                }
            }
            System.out.println("Copy finished");
        } catch (IOException e) {
            System.out.println("Error while copying: " + e.getMessage());
        }
    }
}

/// ### Problem 3: Character frequency counter ###
/// Write a program that reads a text file and counts how many times each character appears. It asks for the file name, reads
/// the file character by character, keeps the counts in a data structure such as an array, and finally prints every character
/// with its count. Handle I/O exceptions, a file that does not exist and a file that is empty with proper messages.

import java.io.*;
import java.util.Scanner;

public class Main {
    // Makes the invisible characters readable in the output.
    static String label(int ch) {
        if (ch == '\n') return "\\n (new line)";
        if (ch == '\r') return "\\r (carriage return)";
        if (ch == '\t') return "\\t (tab)";
        if (ch == ' ') return "space";
        return String.valueOf((char) ch);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("File to analyse: ");
        String name = scan.nextLine();

        int[] counts = new int[256];             // one slot for each of the first 256 characters
        int total = 0;

        try (FileReader in = new FileReader(name)) {
            int ch;
            while ((ch = in.read()) != -1) {     // read() gives the next character as a number
                total++;
                if (ch < counts.length) {
                    counts[ch]++;                // the character's own number is the index
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: the file \"" + name + "\" was not found");
            return;
        } catch (IOException e) {
            System.out.println("Error while reading: " + e.getMessage());
            return;
        }

        if (total == 0) {
            System.out.println("The file is empty, so there is nothing to count");
            return;
        }

        System.out.println(total + " characters in " + name);
        for (int ch = 0; ch < counts.length; ch++) {
            if (counts[ch] > 0) {
                System.out.println(label(ch) + " : " + counts[ch]);
            }
        }
    }
}
/// @file story.txt: hello java | hello world
/// @input(A real file): story.txt
/// @input(A missing file): nofile.txt

/// The array index IS the character: `counts['h']` is the slot for the letter h, because a `char` is really a small number.
/// `counts[ch]++` adds one to that letter's tally. Slots that stay at zero are skipped when printing. The program also works
/// with an empty file, which prints a message instead of an empty table.
