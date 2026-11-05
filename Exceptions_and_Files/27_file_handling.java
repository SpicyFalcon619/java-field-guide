/*
 * Exceptions and Files: File Handling
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why File Handling?
///   - Creating a File (if not exists)
///   - Writing to a File (Overwrites)
///   - Appending to a File
///   - Reading from a File (using `Scanner`)
///   - Deleting a File
/// ============================================================================


/// ### Topic 1: Why File Handling? ###
/// We use file handling to read from or write to files (like `.txt`, `.csv`, etc.) from within Java
/// programs.
/// Java provides several classes for this in the `java.io` package:
///    - `File`
///    - `FileWriter`, `BufferedWriter`
///    - `FileReader`, `BufferedReader`
///    - `Scanner` (also usable for reading files)


/// ### Topic 2: Creating a File (if not exists) ###
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


/// ### Topic 3: Writing to a File (Overwrites) ###
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


/// ### Topic 4: Appending to a File ###
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


/// ### Topic 5: Reading from a File (using `Scanner`) ###
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


/// ### Topic 6: Deleting a File ###
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


/// ### Topic 7: Reading a File using `BufferedReader` ###
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


/// ### Topic 8: Writing to a File using `BufferedWriter` ###
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


/// ### Topic 9: Appending to a File ###
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


/// ### Topic 10: Cleaning Up: Always Close the File ###
/// Why?
/// Closing the reader/writer:
///    - Ensures data is flushed from buffer to file
///    - Releases system resources
///    - Prevents memory leaks or corruption
///
/// Better: Use `try-with-resources` for auto-closing (optional for now)


/// ### Topic 11: Writing with try-with-resources ###
///     try (BufferedWriter bw = new BufferedWriter(new FileWriter("file.txt"))) {
///         bw.write("Hello!");
///     } catch (IOException e) {
///         System.out.println("Error: " + e.getMessage());
///     }
