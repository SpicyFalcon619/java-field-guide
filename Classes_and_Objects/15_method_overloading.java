/*
 * Classes and Objects: Method overloading
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What method overloading is, and why it is useful
///   - The rules: what must differ and what does not count
///   - How Java picks which version to run
///   - Why this is called compile-time polymorphism
/// ============================================================================


/// ### Topic 1: What is method overloading? ###
/// Suppose you write a method to add two numbers, and later you also need to add three, or add decimals. You
/// could invent new names: `sumTwo`, `sumThree`, `sumDoubles`. That quickly turns ugly.
///
/// Java lets you give them all the SAME name, as long as their parameter lists are different. This is METHOD
/// OVERLOADING. When you call `sum(...)`, Java looks at what you passed and runs the matching version.
///
/// ANALOGY: The word "open" means something slightly different for a door, a jar and a bank account, yet we use
/// one word and everybody understands which action is meant from the thing it is applied to. Overloaded methods
/// work the same way: one friendly name, with the arguments telling Java which action you mean.
///
/// WHY: It keeps code cleaner, easier to read and more flexible. The person using your class remembers one name
/// (`print`, `sum`, `find`) and not five. Java itself does this everywhere: `System.out.println` accepts a
/// String, an int, a double, a boolean, and more.

/// ### Topic 2: The rules ###
/// Two methods in the same class are overloads of each other when:
///
///   - They have the SAME name, and
///   - Their PARAMETERS differ in the number of parameters, or in their types, or in the order of the types.
///
/// The return type may be the same or different, but a different return type alone is NOT enough to overload.
/// The parameter names do not count either, because Java only looks at their types.
///
/// JARGON: The name plus the parameter types together is called the method's SIGNATURE. Overloading means several
/// methods with the same name and different signatures.

/// ### Topic 3: Basic method overloading ###
/// Four methods share the name `sum`. Notice that each one has a different parameter list.

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

/// Java chose each version purely from the arguments:
///
///     calc.sum(5, 10)       two ints            -> sum(int, int)
///     calc.sum(1, 2, 3)     three ints          -> sum(int, int, int)
///     calc.sum(4.5, 5.5)    two doubles         -> sum(double, double)
///     calc.sum(5, 2.5)      an int, a double    -> sum(int, double)
///
/// Why `Main calc = new Main();`? The `sum` methods in this example are not `static`, so they belong to an
/// object, and we have to create one before calling them. That is exactly the object idea from the earlier
/// lessons.

/// ### Topic 4: What does NOT count as overloading ###
/// A different return type with identical parameters is not allowed, because Java would have no way to tell the
/// two calls apart. When you write `calc.sum(1, 2)`, the call itself does not say what type you want back.

public class Main {
    public int sum(int a, int b) {
        return a + b;
    }

    public double sum(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        System.out.println(new Main().sum(1, 2));
    }
}
/// @error

/// The compiler reports "method sum(int,int) is already defined". Changing the parameter NAMES also would not
/// help, because only the types matter.

/// ### Topic 5: How Java chooses ###
/// When your arguments do not match exactly, Java tries to find the closest fit. It prefers an exact match first.
/// If there is none, it widens a type (int to double, as you saw in the Variables lesson), and only then looks
/// for other conversions.

public class Main {
    static void show(int x) {
        System.out.println("int version: " + x);
    }

    static void show(double x) {
        System.out.println("double version: " + x);
    }

    static void show(String x) {
        System.out.println("String version: " + x);
    }

    public static void main(String[] args) {
        show(5);          // exact match: int
        show(5.5);        // exact match: double
        show("hi");       // exact match: String
        show('A');        // no char version: widened to int
        show(10L);        // no long version: widened to double
    }
}

/// `show('A')` printed 65 because a `char` is secretly a number, and widens to `int`. If you call a method with
/// arguments that fit nothing at all, for example `show(true)`, the compiler tells you no suitable method exists.
///
/// MISTAKE: Overloading is decided by the types Java sees at COMPILE time. It is a different idea from
/// overriding, which you will meet in the Inheritance lessons, where a child class replaces a method of its
/// parent. Beginners mix the two names up, so remember: OVER-LOAD = same class, different parameters.

/// ### Topic 6: Why it is called compile-time polymorphism ###
/// POLYMORPHISM is a Greek-sounding word that simply means "many forms": one name, several behaviours.
/// Overloading is the gentlest kind. The choice of which `sum` to run is made by the compiler before the program
/// ever starts, which is why it is called COMPILE-TIME polymorphism (or static polymorphism). The other kind, where
/// the choice happens while the program runs, comes in the Polymorphism lesson.
///
/// Constructors can be overloaded in just the same way. You saw that in the Constructors lesson, where `Student3`
/// had one version with no parameters and one with a name and an age.
///
/// REMEMBER: Same name, same class, different parameter lists. The return type alone never makes an overload.

/// ============================================================================
/// YOUR TURN
///   1. Write three overloaded `area` methods: one for a square (one side), one for a rectangle (length and width)
///      and one for a circle (radius as a double). Call each.
///   2. Write `print(int)`, `print(String)` and `print(int, int)` that each print a different message.
///   3. Try writing two methods with the same name and parameters but different return types. Read the error.
///   4. What does `show('A')` print in the example above, and why? Change it and check.
///   5. Overload the constructor of a `Book` class: one with a title, one with a title and a price.
/// ============================================================================
