/*
 * Classes and Objects: Variable scope and lifetime
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What scope and lifetime mean
///   - Local, instance, static and block variables, and where each can be used
///   - Why a variable "does not exist" outside its braces
///   - Shadowing: when two variables share one name
/// ============================================================================


/// ### Topic 1: Two questions about every variable ###
/// Every variable in a program has two properties, and mixing them up is the root of many errors.
///
///   - SCOPE: WHERE in the code can I use this variable? (a question about places)
///   - LIFETIME: HOW LONG does it stay in memory? (a question about time)
///
/// ANALOGY: A whiteboard in a meeting room. Its scope is "people inside that room". Its lifetime is "until
/// somebody wipes it". Notes written on a sticky note in your pocket have a much smaller scope and a shorter
/// lifetime.
///
/// A simple rule of thumb: a variable is visible from the line where it is declared down to the closing brace `}`
/// of the block where it was declared.

/// ### Topic 2: Local variables ###
/// A variable declared INSIDE a method or a block is a LOCAL variable.
///
///   - Scope: only inside that method (or block).
///   - Lifetime: created when the method is called, destroyed when it ends. Each call gets a fresh one.
///   - Not given a default value. You must assign it before you use it.

public class Main {
    public static void greet() {
        String message = "Hello";
        System.out.println(message);
    }

    public static void main(String[] args) {
        greet();
        greet();
    }
}

/// Each call to `greet` makes a new `message` and throws it away at the closing brace. Another method cannot see
/// it, as the Methods lesson showed.

/// ### Topic 3: Instance variables ###
/// A variable declared inside a class but OUTSIDE every method is an INSTANCE variable (a field).
///
///   - Scope: all the non-static methods of the class.
///   - Lifetime: as long as the object exists.
///   - Each object gets its own copy, and a default value (0, false or null).

public class Main {
    String name;

    public void sayName() {
        System.out.println("Name: " + name);
    }

    public static void main(String[] args) {
        Main a = new Main();
        Main b = new Main();
        a.name = "Rafi";

        a.sayName();
        b.sayName();
    }
}

/// `a` and `b` each carry their own `name`, so changing one does not affect the other. `b`'s name is still
/// `null`, the default for a String.

/// ### Topic 4: Static variables ###
/// A static variable (a class variable) was introduced in the last lessons.
///
///   - Scope: the whole class, reachable by every object and through the class name.
///   - Lifetime: from the moment the class is loaded until the program ends.
///   - ONE copy, shared by all objects.

public class Main {
    static int count = 0;

    public Main() {
        count++;
        System.out.println("Created object #" + count);
    }

    public static void main(String[] args) {
        new Main();
        new Main();
        new Main();
    }
}

/// ### Topic 5: Block-scoped variables ###
/// Any pair of curly braces creates a block: an `if`, a loop, even a bare `{ }`. A variable declared in a block
/// exists only inside it.
///
///     if (true) {
///         int x = 5;                  // only accessible here
///         System.out.println(x);
///     }
///     // x is gone here: using it is an error
///
/// The loop counter of a `for` loop is a block variable too. That is why you could write `for (int i ...)` twice
/// in a row without a clash: each `i` died when its loop ended.

/// ### Topic 6: A full example ###
/// Follow which variable can be used where.

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

/// The commented line at the bottom is the mistake to avoid. Let us un-comment it and see exactly what Java says:

public class Main {
    public static void main(String[] args) {
        int a = 10;
        if (a > 5) {
            int b = 20;
            System.out.println(a + b);
        }
        System.out.println(b);
    }
}
/// @error

/// "cannot find symbol: variable b". By the time Java reaches that line the block has closed, and `b` has been
/// destroyed. The variable `a` is declared in the outer block, so it can be used inside the inner one, but not the
/// other way around. Scopes nest like boxes: inner code can see outward, outer code cannot see inward.
///
/// MISTAKE: A common case is declaring a variable inside an `if` or a loop and then trying to use it after.
/// If you need the value afterwards, declare the variable BEFORE the block:

public class Main {
    public static void main(String[] args) {
        int result;               // declared outside, so it survives

        if (5 > 3) {
            result = 100;
        } else {
            result = 0;
        }

        System.out.println(result);
    }
}

/// ### Topic 7: Shadowing: two variables, one name ###
/// What if a local variable has the same name as a field? Inside that method, the local one wins, and the field is
/// hidden or SHADOWED. You already met this with `this.name = name` in the `this` lesson. The way to reach the
/// shadowed field is `this.`

public class Main {
    int x = 10;                  // field

    void show() {
        int x = 99;              // local variable, shadows the field
        System.out.println("local x = " + x);
        System.out.println("field x = " + this.x);
    }

    public static void main(String[] args) {
        new Main().show();
    }
}

/// --- Summary table -----
///
///     Kind        Declared                    Scope                    Lifetime               Default value
///     Local       inside a method or block    that method or block     one call of the method none: set it first
///     Instance    in class, not static        the object's methods     as long as the object  0 / false / null
///     Static      in class, with static       whole class              whole program          0 / false / null
///     Block       inside { } of if or loop    that block only          one run of the block   none: set it first
///
/// REMEMBER: Declare variables in the smallest scope that works, and give them meaningful names. A variable that
/// can be seen from everywhere can be broken from everywhere.

/// ============================================================================
/// YOUR TURN
///   1. In a `for` loop, declare a variable inside the body, then try to print it after the loop. Read the error.
///   2. Write a class with an instance variable `score` and a static variable `highest`. Create three objects with
///      different scores and keep `highest` updated.
///   3. Declare a local variable without a value and print it. What does the compiler say?
///   4. Create a field and a local variable with the same name, and print both using `this.`
///   5. Explain the difference between scope and lifetime in your own words.
/// ============================================================================
