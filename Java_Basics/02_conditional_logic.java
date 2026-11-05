/*
 * Java Basics: Conditional Logic
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - If-Else & Conditions
///   - Nested If-Else
///   - Switch Statement
///   - Break and Continue
/// ============================================================================


/// ### Topic 1: If-Else & Conditions ###
public class Main {
    public static void main(String[] args) {
        int number = 10;

        if (number > 0) {
            System.out.println("Positive number");
        } else if (number < 0) {
            System.out.println("Negative number");
        } else {
            System.out.println("Zero");
        }
    }
}


/// ### Topic 2: Nested If-Else ###
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Enter your marks: ");
        int marks = scan.nextInt();

        if (marks >= 60) {
            if (marks >= 90) {
                System.out.println("Grade: A+");
            } else {
                System.out.println("Grade: A");
            }
        } else {
            if (marks >= 40) {
                System.out.println("Grade: B");
            } else {
                System.out.println("Fail");
            }
        }
        scan.close();
    }
}
/// @input 92
/// @input 75
/// @input 50
/// @input 30


/// ### Topic 3: Switch Statement ###
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter a number between 1 and 3: ");
        int choice = scan.nextInt();

        switch (choice) {
            case 1:
                System.out.println("You chose One");
                break;
            case 2:
                System.out.println("You chose Two");
                break;
            case 3:
                System.out.println("You chose Three");
                break;
            default:
                System.out.println("Invalid choice");
        }
        scan.close();
    }
}
/// @input 2


/// ### Topic 4: Break and Continue ###
/// --- Break -----

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            if (i == 3) {
                break;
            }
            System.out.println(i);
        }
    }
}

/// --- Continue -----

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            if (i == 3) {
                continue;
            }
            System.out.println(i);
        }
    }
}
