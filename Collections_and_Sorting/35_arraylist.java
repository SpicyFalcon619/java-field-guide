/*
 * Collections and Sorting: ArrayList and the Collections framework
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why arrays are not always enough, and what the Collections framework is
///   - How to create an ArrayList and add, read, change, remove and search items
///   - What `<String>` means, and why `ArrayList<int>` does not work
///   - Looping over a list, and the two classic mistakes: remove while looping and remove(int) versus remove(Object)
///   - Sorting and other helpers from the Collections class
/// ============================================================================


/// ### Topic 1: The problem with arrays ###
/// An array has a FIXED size, set when you create it. You cannot add a sixth item to an array of five. If you do not
/// know in advance how many items you will have (students who enrol, messages in a chat, items in a cart), an
/// array is awkward. You would have to create a bigger one and copy everything over, every time.
///
/// Java's answer is the COLLECTIONS FRAMEWORK: a set of ready-made classes and interfaces for holding groups of
/// objects. They live in the `java.util` package (remember the import from the Packages lesson). The most used one
/// is `ArrayList`.
///
/// ANALOGY: An array is a fixed row of lockers. An ArrayList is a magic row that grows when you add an item and
/// closes the gap when you remove one.
///
/// Inside, an ArrayList really does use an array, and quietly makes a bigger one when it fills up. You get the
/// convenience without doing that work.

/// ### Topic 2: Creating and using an ArrayList ###
/// `ArrayList<String>` reads "an ArrayList of Strings". The part in angle brackets says what kind of thing the list may
/// hold. (This feature is called generics, and the next lessons explain it fully. For now, treat it as "the type of the
/// items".)

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();

        names.add("Rafi");
        names.add("Mim");
        names.add("Anik");

        System.out.println(names);
        System.out.println("Size: " + names.size());
        System.out.println("First: " + names.get(0));
    }
}

/// Printing a list shows its items in square brackets. The empty `<>` on the right is allowed because Java can work
/// out the type from the left side (the "diamond").
///
/// --- The methods you will use all the time -----
///     add(x)            put x at the end
///     add(i, x)         insert x at position i, shifting the rest along
///     get(i)            the item at position i (counting from 0, like arrays)
///     set(i, x)         replace the item at position i
///     remove(i)         remove the item at position i
///     remove(x)         remove the first item equal to x
///     size()            how many items
///     isEmpty()         true when there are none
///     contains(x)       true if x is in the list
///     indexOf(x)        the position of x, or -1
///     clear()           remove everything

public class Main {
    public static void main(String[] args) {
        java.util.ArrayList<String> fruits = new java.util.ArrayList<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");

        fruits.add(1, "Mango");          // insert at position 1
        fruits.set(0, "Apricot");        // replace position 0
        fruits.remove("Banana");         // remove by value
        fruits.remove(0);                // remove by position

        System.out.println(fruits);
        System.out.println(fruits.contains("Cherry"));
        System.out.println(fruits.indexOf("Cherry"));
        System.out.println(fruits.indexOf("Grape"));
        System.out.println(fruits.isEmpty());
    }
}

/// Follow the list through each step on paper: [Apple, Banana, Cherry] becomes [Apple, Mango, Banana, Cherry], then
/// [Apricot, Mango, Banana, Cherry], then [Apricot, Mango, Cherry], then [Mango, Cherry].

/// ### Topic 3: Going through a list ###
/// You loop over a list just like an array, with an index or with for-each. Note `size()` with brackets, not
/// `length`.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> marks = new ArrayList<>();
        marks.add(72);
        marks.add(95);
        marks.add(60);

        for (int i = 0; i < marks.size(); i++) {
            System.out.println("Mark " + i + ": " + marks.get(i));
        }

        int sum = 0;
        for (int m : marks) {
            sum += m;
        }
        System.out.println("Average: " + (double) sum / marks.size());
    }
}

/// ### Topic 4: Lists hold objects, not primitives ###
/// A list can only hold OBJECTS. The primitive types (`int`, `double`, `char`, `boolean`) are not objects, so
/// `ArrayList<int>` is not allowed. Java supplies an object version, called a WRAPPER class, for each one:
///
///     int -> Integer     double -> Double     char -> Character     boolean -> Boolean     long -> Long
///
/// The conversion is automatic in both directions (this is called autoboxing), so in practice you just write
/// `list.add(5)` and `int x = list.get(0)`.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<int> numbers = new ArrayList<>();
        numbers.add(5);
    }
}
/// @error

/// "unexpected type; required: reference, found: int". Change it to `ArrayList<Integer>` and it works.

/// ### Topic 5: A list of your own objects ###
/// The items can be any class, including ones you wrote. This is how real programs store things: a list of students,
/// of products, of messages.

import java.util.ArrayList;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return name + " (" + marks + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Rafi", 91));
        students.add(new Student("Mim", 67));
        students.add(new Student("Anik", 45));

        System.out.println(students);          // uses our toString()

        for (Student s : students) {
            if (s.marks >= 60) {
                System.out.println(s.name + " passed");
            }
        }
    }
}

/// Without the `toString()` method, printing a Student would show something like `Student@1b6d3586`, which is the
/// default text from `Object`. Overriding `toString()` is how you make an object print nicely, and every list print uses
/// it.

/// ### Topic 6: Two classic mistakes ###
/// --- Mistake 1: changing the list while looping over it with for-each -----
/// If you add or remove items inside a for-each loop, Java notices that the list changed under it and stops with a
/// `ConcurrentModificationException`.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);

        for (int n : numbers) {
            if (n % 2 == 0) {
                numbers.remove(Integer.valueOf(n));
            }
        }
        System.out.println(numbers);
    }
}
/// @error

/// The safe way is `removeIf`, which does the whole job in one go:

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            numbers.add(i);
        }

        numbers.removeIf(n -> n % 2 == 0);       // remove every even number
        System.out.println(numbers);
    }
}

/// (The `n -> n % 2 == 0` is a lambda, a short piece of code handed to the method. You will meet lambdas in their own
/// lesson.)
///
/// --- Mistake 2: remove(int) or remove(Object)? -----
/// For a list of Integers, `remove(1)` could mean "remove the item AT position 1" or "remove the item EQUAL to 1". Java
/// picks the position version, because `1` is an `int`. To remove the VALUE, wrap it: `remove(Integer.valueOf(1))`.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(20);
        list.add(1);

        list.remove(1);                       // position 1, so 20 goes
        System.out.println(list);

        list.remove(Integer.valueOf(1));      // the value 1
        System.out.println(list);
    }
}

/// ### Topic 7: Going out of range ###
/// Like arrays, asking for a position that does not exist throws an exception, here `IndexOutOfBoundsException`.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> items = new ArrayList<>();
        items.add("one");
        System.out.println(items.get(3));
    }
}
/// @error

/// The message tells you the index you asked for and the size: "Index 3 out of bounds for length 1".

/// ### Topic 8: Sorting and the Collections class ###
/// `Collections` (with an s) is a helper class full of static methods that work on lists. The most useful:
///
///     Collections.sort(list)       put the items in order (smallest first, A to Z)
///     Collections.reverse(list)    flip the order
///     Collections.shuffle(list)    mix them randomly
///     Collections.max(list)        the biggest
///     Collections.min(list)        the smallest

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(72);
        scores.add(95);
        scores.add(60);
        scores.add(88);

        Collections.sort(scores);
        System.out.println(scores);

        Collections.reverse(scores);
        System.out.println(scores);

        System.out.println("Highest: " + Collections.max(scores));
        System.out.println("Lowest: " + Collections.min(scores));
    }
}

/// Sorting Strings works the same way, alphabetically. Sorting your OWN objects needs one extra piece of information,
/// how to compare two of them, and that is the topic of the Comparable and Comparator lesson.
///
/// --- Other lists: LinkedList -----
/// `LinkedList` offers the same methods as ArrayList (they both implement the `List` interface) but is organised
/// differently inside. It is quicker to add or remove at the very start, and slower to jump to position 5000. When in
/// doubt, use ArrayList. Programmers often declare the variable with the interface, `List<String> names = new
/// ArrayList<>();`, so the kind of list can be swapped later without touching the rest of the code.
///
/// REMEMBER: ArrayList is a growable array of objects. Use wrapper types for numbers. `size()`, not `length`. Do not
/// change a list while for-each is walking it; use `removeIf`.

/// ============================================================================
/// YOUR TURN
///   1. Make an `ArrayList<String>` of five friends' names. Print it, print its size, remove the third name, and print
///      it again.
///   2. Read numbers from the user until they type 0, storing them in an `ArrayList<Integer>` (you cannot know the
///      count in advance). Then print the largest and the average.
///   3. Make a list of `Student` objects with a `toString()` and print only those with marks above 50.
///   4. Remove all names that start with "A" using `removeIf`.
///   5. Explain the difference between `list.remove(2)` and `list.remove("Rafi")` for a list of Strings.
///   6. Why does `ArrayList<double>` not compile, and what do you write instead?
/// ============================================================================
