/*
 * Classes and Objects: Variable Scope & Lifetime
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Local Variable
///   - Instance Variable
///   - Static Variable (Class Variable)
///   - Block Scoped Variable
///   - Example
/// ============================================================================

/// Scope = where a variable is accessible
/// Lifetime = how long a variable lives in memory


/// ### Topic 1: Local Variable ###
///    - Declared inside a method/block
///    - Exists only during method execution
///
///     public void greet() {
///         String message = "Hello";
///         System.out.println(message);
///     }


/// ### Topic 2: Instance Variable ###
///    - Declared inside class, but outside methods
///    - Belongs to object
///    - Each objects gets its own copy

public class Main {
    String name;

    public void sayName() {
        System.out.println("Name: " + name);
    }
}


/// ### Topic 3: Static Variable (Class Variable) ###
///    - Declared with `static` keyword
///    - Shared by all objects of the class
///    - Belongs to class, not instances

public class Main {
    static int count = 0;

    public Main() {
        count++;
        System.out.println("Created object #" + count);
    }
}


/// ### Topic 4: Block Scoped Variable ###
///    - Declared inside `{}` braces
///    - Only accessible within that block
///
///     if (true) {
///         int x = 5; // only accessible here
///         System.out.println(x);
///     }
///     // System.out.println(x); ❌ Error: not accessible here


/// ### Topic 5: Example ###
public class Main {
    public static void main(String[] args) {
        int a = 10; // scope: main method

        if (a > 5) {
            int b = 20; // scope: inside if block
            System.out.println(a + b); // valid
        }

        // System.out.println(b); //  Error: b not accessible here
    }
}
