/*
 * Java Basics: Basic
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Hello, World!
///   - Variables & Data Types
///   - User Input
/// ============================================================================


/// ### Topic 1: Hello, World! ###
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}


/// ### Topic 2: Variables & Data Types ###
public class Main {
    public static void main(String[] args) {
        int age = 20;                  // Integer variable
        double price = 10.5;           // Decimal number
        char grade = 'A';              // Character
        boolean b = true;              // Boolean (true/false)
        String name = "Noman";         // String of text

        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("T/F: " + b);
    }
}


/// ### Topic 3: User Input ###
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); 

        System.out.print("Enter your name: ");
        String name = scan.nextLine();         // Reads full line of text

        System.out.print("Enter your age: ");
        int age = scan.nextInt();              // Reads an integer

        System.out.println("Hello " + name + ", you are " + age + " years old.");
        scan.close();
    }
}
/// @input Noman | 35
