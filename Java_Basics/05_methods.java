/*
 * Java Basics: Methods
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Basic Method with No Return
///   - Method with Parameters and Return Type
/// ============================================================================


/// ### Topic 1: Basic Method with No Return ###
public class Main {
    public static void greet() {
        System.out.println("Hello from a method!");
    }

    public static void main(String[] args) {
        greet();
    }
}


/// ### Topic 2: Method with Parameters and Return Type ###
public class Main {
    public static int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        int result = add(5, 7);
        System.out.println("Sum: " + result);
    }
}
