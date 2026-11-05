/*
 * Classes and Objects: Instance Initialization Block (IIB)
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is IIB?
///   - Syntax
/// ============================================================================


/// ### Topic 1: What is IIB? ###
/// An IIB is a block of code that runs every time an object is created, before the constructor.


/// ### Topic 2: Syntax ###
class MyClass {
    {
        System.out.println("IIB executed");
    }

    MyClass() {
        System.out.println("Constructor executed");
    }
}

public class Main {
    public static void main(String[] args) {
        MyClass obj1 = new MyClass();
        System.out.println("-------");
        MyClass obj2 = new MyClass();
    }
}
