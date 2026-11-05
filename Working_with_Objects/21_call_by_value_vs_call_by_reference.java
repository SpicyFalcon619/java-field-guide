/*
 * Working with Objects: Call by Value vs Call by Reference
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Key Idea
///   - Call by Value (for Primitives)
///   - Call by Value (for Objects)
/// ============================================================================


/// ### Topic 1: Key Idea ###
/// Java only uses Call by Value, but it behaves differently for primitive types and objects, which
/// often causes confusion.


/// ### Topic 2: Call by Value (for Primitives) ###
/// When we pass a primitive type (like `int`, `float`, `char`) to a method, a copy of the value is
/// passed.
///
/// NOTE: Changes made inside the method do not affect the original variable.
///
/// --- Example: -----

public class Main {
    static void changeValue(int x) {
        x = 100;
    }

    public static void main(String[] args) {
        int a = 50;
        changeValue(a);
        System.out.println("Value of a: " + a);
    }
}

/// Even though `x` was changed inside `changeValue`, `a` remains the same.


/// ### Topic 3: Call by Value (for Objects) ###
/// In Java, object references are passed by value.
/// That means: a copy of the reference is passed to the method — both point to the same object, but
/// the reference itself is a copy.
///
/// NOTE: We can change the object’s fields inside the method
///
/// But we cannot reassign the original reference
///
/// --- Example: Modifying Object Fields -----


class Student7 {
    String name;
}

public class Main {
    static void updateStudent(Student7 s) {
        s.name = "Noman";
    }

    public static void main(String[] args) {
        Student7 s1 = new Student7();
        s1.name = "Tortilla";

        updateStudent(s1);
        System.out.println(s1.name); 
    }
}

/// The method modified the fields of the object, and the change reflected outside.
///
/// --- Example: Trying to Reassign the Object -----


class Student8 {
    String name;
}

public class Main {
    static void changeReference(Student8 s) {
        s = new Student8();  // this new object is not seen by main()
        s.name = "Noman";
    }

    public static void main(String[] args) {
        Student8 s1 = new Student8();
        s1.name = "Tortilla";

        changeReference(s1);
        System.out.println(s1.name);
    }
}

/// Here, `s` was given a new object, but that doesn't affect `s1` in `main()` — because only a copy
/// of the reference was passed.
