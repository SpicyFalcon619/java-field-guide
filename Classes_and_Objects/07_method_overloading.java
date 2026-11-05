/*
 * Classes and Objects: Method Overloading
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is Method Overloading?
///   - Why use Method Overloading?
///   - Key Rules for Overloading
///   - Basic Method Overloading
/// ============================================================================


/// ### Topic 1: What is Method Overloading? ###
/// Method Overloading means having multiple methods with the same name in the same class but with
/// different parameters (type or number).
/// It's part of compile-time polymorphism in Java.


/// ### Topic 2: Why use Method Overloading? ###
/// It makes our code:
///    - Cleaner
///    - Easier to read
///    - More flexible


/// ### Topic 3: Key Rules for Overloading ###
///    - Same method name
///    - Different number of parameters or Different types of parameters
///    - Return type can be same or different


/// ### Topic 4: Basic Method Overloading ###
public class Main {
    public int sum(int a, int b) {
        return a + b;
    }

    public int sum(int a, int b, int c) {
        return a + b + c;
    }

    public double sum(double a, double b) {
        return a + b;
    }

    public double sum(int a, double b) {
        return a + b;
    }

    public static void main(String[] args) {
        Main calc = new Main();

        System.out.println("Sum 2 int: " + calc.sum(5, 10));
        System.out.println("Sum 3 int: " + calc.sum(1, 2, 3));
        System.out.println("Sum 2 double: " + calc.sum(4.5, 5.5));
        System.out.println("Sum int+double: " + calc.sum(5, 2.5));
    }
}
