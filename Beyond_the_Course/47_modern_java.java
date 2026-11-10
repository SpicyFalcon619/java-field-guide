/*
 * Beyond the Course: Records, var, text blocks and pattern matching
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - records: a one-line class for plain data
///   - var: letting Java work out a variable's type
///   - text blocks: multi-line Strings without the clutter
///   - switch expressions and pattern matching for instanceof
///   - Immutable collections with List.of, Set.of and Map.of
/// ============================================================================


/// ### Topic 1: Why this lesson? ###
/// Java has changed a lot since many textbooks were written. Newer versions added shortcuts that remove boilerplate, the
/// repeated code you write again and again. Code you read online, and code your future teammates write, will use them. Each
/// feature here saves lines, and all of them work in Java 17 or later (the version the examples on this page are
/// compiled with).

/// ### Topic 2: records ###
/// Think of the classes you wrote just to hold data: a `Student` with a name and marks, a `Point` with x and y. For each you
/// wrote private fields, a constructor, getters, `toString`, `equals` and `hashCode`: dozens of lines. A RECORD does all of it in
/// one line.

record Point(int x, int y) { }

public class Main {
    public static void main(String[] args) {
        Point p = new Point(3, 4);

        System.out.println(p);                       // a readable toString, for free
        System.out.println(p.x() + ", " + p.y());    // accessors are named after the fields (no "get")
        System.out.println(p.equals(new Point(3, 4)));
    }
}

/// From `record Point(int x, int y) { }` Java writes: private final fields, a constructor, the accessors `x()` and `y()`, and
/// proper `toString`, `equals` and `hashCode`. Compare `p.equals(new Point(3, 4))`: records compare by CONTENT, which is why it
/// printed true. (Plain classes would say false, as you saw in the Passing Objects lesson.)
///
/// A record is IMMUTABLE: its fields cannot change after creation. You can still add methods and check the input in a compact
/// constructor:

record Student(String name, int marks) {
    Student {                                         // compact constructor: runs before the fields are set
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("marks must be 0 to 100");
        }
    }

    String grade() {
        return marks >= 80 ? "A" : marks >= 60 ? "B" : "C";
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student("Rafi", 91);
        System.out.println(s + " -> " + s.grade());

        Student bad = new Student("Mim", 150);
    }
}
/// @error

/// Records are ideal for data you pass around and never change: a row of a table, a result, a coordinate.
///
/// TIP: Use a record when a class is "just data". Use a normal class when you need changing state or behaviour with a
/// lifecycle.

/// ### Topic 3: var ###
/// `var` lets Java work out the type of a LOCAL variable from the value you give it. It does not make Java dynamically typed:
/// the type is still fixed, only you did not have to write it.

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        var count = 5;                                  // int
        var price = 9.99;                               // double
        var name = "Rafi";                              // String
        var scores = new ArrayList<Integer>();          // ArrayList<Integer>
        var ages = new HashMap<String, Integer>();      // no need to write the long type twice

        scores.add(90);
        ages.put(name, 20);

        System.out.println(count + " " + price + " " + name + " " + scores + " " + ages);
    }
}

/// `var` is most useful where the type is long and obvious from the right-hand side. The rules:
///    - Only for LOCAL variables (inside methods), never for fields or parameters.
///    - It needs a value to work from: `var x;` is an error.
///    - Once decided, the type is fixed: `var n = 5; n = "hi";` does not compile.

public class Main {
    public static void main(String[] args) {
        var n = 5;
        n = "hi";
    }
}
/// @error

/// TIP: Do not use `var` when it hides the type from a reader. `var data = load();` leaves the reader guessing, while
/// `List<Student> data = load();` tells them.

/// ### Topic 4: Text blocks ###
/// Writing multi-line text with `\n` and `+` is painful. A TEXT BLOCK starts and ends with three double quotes. Line breaks and
/// quotes inside work as you see them, and the indentation is tidied for you.

public class Main {
    public static void main(String[] args) {
        String json = """
                {
                  "name": "Rafi",
                  "age": 20
                }
                """;

        System.out.println(json);

        String poem = """
                Roses are red,
                Violets are blue,
                Java is fun,
                And so are you.""";
        System.out.println(poem);
    }
}

/// Java removes the common leading spaces, so you can indent the text to match your code. Text blocks are very handy for
/// JSON, SQL, HTML and any long message. Escape sequences such as `\t` still work inside them.

/// ### Topic 5: Switch expressions ###
/// You met the arrow `switch` in the Switch lesson. As an EXPRESSION it gives back a value, which you can assign. There is no
/// fall-through and no `break`. If a branch needs several statements, use braces and `yield`.

public class Main {
    public static void main(String[] args) {
        int day = 6;

        String kind = switch (day) {
            case 1, 2, 3, 4, 5 -> "weekday";
            case 6, 7 -> "weekend";
            default -> "invalid";
        };
        System.out.println(kind);

        int length = switch (kind) {
            case "weekday" -> 5;
            case "weekend" -> {
                System.out.println("(two days)");
                yield 2;                      // the value this branch produces
            }
            default -> 0;
        };
        System.out.println(length);
    }
}

/// ### Topic 6: Pattern matching for instanceof ###
/// From the Polymorphism lesson: to use a subclass's method you checked `instanceof` and then cast. Pattern matching does both in
/// one step, by giving the checked value a name.

class Animal { }

class Dog extends Animal {
    void fetch() {
        System.out.println("fetching!");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Dog();

        // the old way
        if (a instanceof Dog) {
            Dog d = (Dog) a;
            d.fetch();
        }

        // the new way: test and name in one go
        if (a instanceof Dog dog) {
            dog.fetch();
        }

        Object thing = "hello";
        if (thing instanceof String text && text.length() > 3) {
            System.out.println(text.toUpperCase());
        }
    }
}

/// The variable (`dog`, `text`) exists only where the test is known to be true.

/// ### Topic 7: Immutable collections: List.of, Set.of, Map.of ###
/// For small fixed collections, the `of` methods create them in one line. They are IMMUTABLE: no adding, removing or changing.
/// Any attempt throws an `UnsupportedOperationException`. If you need to change one, copy it into an ArrayList first.

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<String> days = List.of("Sat", "Sun");
        Set<Integer> primes = Set.of(2, 3, 5);
        Map<String, Integer> sizes = Map.of("S", 1, "M", 2);

        System.out.println(days + " " + days.size());
        System.out.println(primes.contains(3));
        System.out.println(sizes.get("M"));

        days.add("Mon");
    }
}
/// @error

/// --- A look further ahead -----
/// Newer Java versions keep adding features in the same spirit: sealed classes (limiting who can extend a class) and
/// pattern matching in `switch`, which test the type of an object in each branch. They build on records and `instanceof` patterns,
/// and you are now ready to read about them.
///
/// REMEMBER: `record` for plain data, `var` for obvious local types, `"""` for multi-line text, arrow `switch` expressions
/// for values, `instanceof` with a name to skip the cast, and `List.of` for small fixed collections.

/// ============================================================================
/// YOUR TURN
///   1. Declare `record Book(String title, double price)`, create two books, and print them. Compare two equal ones with
///      `equals`.
///   2. Add a method `discounted()` to the record that returns the price minus 10 percent.
///   3. Rewrite a long `ArrayList<String> names = new ArrayList<String>();` declaration using `var`.
///   4. Store a short SQL query in a text block and print it.
///   5. Rewrite an old `switch` with `break` into a switch expression that returns a value.
///   6. Use `List.of` to make a list, try to add to it, and read the exception. How do you get a changeable copy?
/// ============================================================================
