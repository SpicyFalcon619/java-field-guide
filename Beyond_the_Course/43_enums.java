/*
 * Beyond the Course: Enums
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What an enum is, and why it beats plain numbers or Strings for a fixed set of choices
///   - Declaring an enum, using it, and looping over its values
///   - Enums with fields, constructors and methods
///   - Enums in a switch, and the useful built-in methods
/// ============================================================================


/// ### Topic 1: The problem: magic numbers and strings ###
/// Suppose a program needs the day of the week, the state of an order or the size of a drink. You might use numbers (1 for
/// Monday, 2 for Tuesday) or Strings ("SMALL", "MEDIUM"). Both invite bugs: what does 7 mean? Is it "Small" or "small" or
/// "SMALL "? Nothing stops someone from passing 42 or "Smal".

public class Main {
    static void order(String size) {
        if (size.equals("SMALL")) {
            System.out.println("a small drink");
        } else if (size.equals("LARGE")) {
            System.out.println("a large drink");
        } else {
            System.out.println("unknown size: " + size);
        }
    }

    public static void main(String[] args) {
        order("SMALL");
        order("Smal");              // a typo, and the compiler cannot warn you
    }
}

/// An ENUM (short for enumeration) is a type whose only possible values are a fixed list that YOU name. The compiler then
/// rejects everything else.
///
/// ANALOGY: The buttons on a lift. There are exactly the floors the building has. You cannot press floor 42.

/// ### Topic 2: Declaring and using an enum ###
/// Use the keyword `enum`, and list the possible values, in capital letters by convention (they are constants).

enum Size {
    SMALL, MEDIUM, LARGE
}

public class Main {
    static void order(Size size) {
        if (size == Size.SMALL) {
            System.out.println("a small drink");
        } else if (size == Size.LARGE) {
            System.out.println("a large drink");
        } else {
            System.out.println("a medium drink");
        }
    }

    public static void main(String[] args) {
        Size mine = Size.LARGE;
        order(mine);
        order(Size.SMALL);
        System.out.println(mine);
    }
}

/// A variable of type `Size` can only hold SMALL, MEDIUM, LARGE or null. A typo is now a compile error:

enum Size {
    SMALL, MEDIUM, LARGE
}

public class Main {
    public static void main(String[] args) {
        Size mine = Size.EXTRA_LARGE;
    }
}
/// @error

/// "cannot find symbol". You compare enums with `==` (unlike Strings), because each value exists exactly once.

/// ### Topic 3: Enums in a switch ###
/// Enums and `switch` are made for each other. Inside the `case` you write just the constant name, with no `Size.`.

enum Day {
    MON, TUE, WED, THU, FRI, SAT, SUN
}

public class Main {
    static String kind(Day d) {
        switch (d) {
            case SAT:
            case SUN:
                return "weekend";
            default:
                return "weekday";
        }
    }

    public static void main(String[] args) {
        System.out.println(kind(Day.MON));
        System.out.println(kind(Day.SAT));

        Day today = Day.WED;
        String mood = switch (today) {
            case MON -> "ugh";
            case FRI -> "yay";
            default -> "okay";
        };
        System.out.println(mood);
    }
}

/// ### Topic 4: Built-in methods ###
/// Every enum gets some methods for free:
///
///     values()           an array of all the constants, in the order declared
///     valueOf("MON")     the constant with that name (error if there is none)
///     name()             the constant's name as a String
///     ordinal()          its position, counting from 0
///     compareTo(other)   compares by position
///     toString()         the name by default

enum Level {
    LOW, MEDIUM, HIGH
}

public class Main {
    public static void main(String[] args) {
        for (Level l : Level.values()) {
            System.out.println(l.ordinal() + ": " + l.name());
        }

        Level chosen = Level.valueOf("HIGH");
        System.out.println(chosen == Level.HIGH);
        System.out.println(Level.LOW.compareTo(Level.HIGH));
    }
}

/// `valueOf` is handy for turning typed text into an enum. If the text matches no constant, it throws an
/// `IllegalArgumentException`, so wrap it in a try and catch when the text comes from a user.

/// ### Topic 5: Enums can have fields, a constructor and methods ###
/// Behind the scenes an enum is a special class, and each constant is an object. So an enum can carry data. Give it fields, a
/// private constructor, and methods. Each constant passes values to the constructor in brackets.

enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    EARTH(5.976e+24, 6.37814e6),
    JUPITER(1.9e+27, 7.1492e7);

    private final double mass;       // in kilograms
    private final double radius;     // in metres

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    double surfaceGravity() {
        double G = 6.67300E-11;
        return G * mass / (radius * radius);
    }
}

public class Main {
    public static void main(String[] args) {
        for (Planet p : Planet.values()) {
            System.out.printf("%s: gravity %.2f%n", p, p.surfaceGravity());
        }
    }
}

/// A more everyday example is a coffee size that knows its price:

enum Coffee {
    SMALL(2.5), MEDIUM(3.0), LARGE(3.75);

    private final double price;

    Coffee(double price) {
        this.price = price;
    }

    double getPrice() {
        return price;
    }
}

public class Main {
    public static void main(String[] args) {
        Coffee c = Coffee.MEDIUM;
        System.out.println(c + " costs " + c.getPrice());

        double total = 0;
        for (Coffee each : Coffee.values()) {
            total += each.getPrice();
        }
        System.out.println("One of each costs " + total);
    }
}

/// NOTE: The enum's constructor is always private (you cannot write `new Coffee(...)`), because the list of constants is
/// fixed. Notice the semicolon after the last constant when the enum has more than just the list.

/// ### Topic 6: Maps and sets made for enums ###
/// When enums are keys in a map or members of a set, use `EnumMap` and `EnumSet`. They are faster and compact, and keep the
/// declared order.

import java.util.EnumMap;

enum Day {
    MON, TUE, WED
}

public class Main {
    public static void main(String[] args) {
        EnumMap<Day, String> plan = new EnumMap<>(Day.class);
        plan.put(Day.WED, "Java lab");
        plan.put(Day.MON, "Lecture");

        System.out.println(plan);               // in declared order, not the order we added
    }
}

/// REMEMBER: Use an enum whenever a variable can only be one of a fixed list of named things. Compare with `==`, loop with
/// `values()`, and give it fields and methods when each constant needs to carry data.

/// ============================================================================
/// YOUR TURN
///   1. Declare an enum `Season` with four constants. Print each one with its `ordinal()`.
///   2. Write a method that takes a `Season` and returns a message using a switch.
///   3. Make an enum `Grade` with the letter and the minimum marks (A = 80, B = 60, C = 40). Write a method that turns a
///      number into a Grade.
///   4. Use `valueOf` to convert text typed by the user into a `Day`, and catch the exception for bad input.
///   5. Why are enums better than Strings for choices like "SMALL" and "LARGE"?
/// ============================================================================
