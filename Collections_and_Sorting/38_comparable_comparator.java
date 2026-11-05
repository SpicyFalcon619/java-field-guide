/*
 * Collections and Sorting: Comparable and Comparator
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why Java needs to be told how to sort your own objects
///   - Comparable: giving a class one natural order with compareTo
///   - Comparator: choosing any order from outside, and combining orders
///   - Sorting lists and arrays, ascending, descending and by several keys
/// ============================================================================


/// ### Topic 1: How does sorting know what comes first? ###
/// Numbers and Strings sort without help: 3 comes before 7, "apple" before "banana". Java knows how to compare them.
/// But how should it sort a list of Students? By name? By marks? Highest first? Java cannot guess. Try it:

import java.util.ArrayList;
import java.util.Collections;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("Rafi", 91));
        list.add(new Student("Mim", 67));
        Collections.sort(list);
    }
}
/// @error

/// The compiler refuses: Student is not `Comparable`. We must tell Java how to compare two students. There are two ways,
/// and they are the topic of this lesson:
///
///     Comparable   the CLASS knows its own natural order (one order, built in)
///     Comparator   a SEPARATE object defines an order (as many as you like)
///
/// ANALOGY: Comparable is a person who always lines up by height, because that is their rule. A Comparator is a teacher who
/// says "today, line up by birthday", and tomorrow "by name". The teacher can change the order without changing the people.

/// ### Topic 2: Comparable: the natural order ###
/// Make the class implement `Comparable<Student>` (an interface, from the Interfaces lesson) and write its one method,
/// `compareTo`. It compares THIS object with another and returns an int:
///
///     negative   this comes BEFORE other
///     zero       they are equal in order
///     positive   this comes AFTER other
///
/// Here the natural order is by marks, lowest first. Subtracting the marks gives exactly the right sign.

import java.util.ArrayList;
import java.util.Collections;

class Student implements Comparable<Student> {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    @Override
    public int compareTo(Student other) {
        return this.marks - other.marks;            // lowest marks first
    }

    @Override
    public String toString() {
        return name + " (" + marks + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("Rafi", 91));
        list.add(new Student("Mim", 67));
        list.add(new Student("Anik", 45));
        list.add(new Student("Dee", 80));

        Collections.sort(list);
        System.out.println(list);
    }
}

/// `Collections.sort` now works, because it can call `compareTo` on any two students. Everything that sorts uses it:
/// `Arrays.sort`, `TreeSet`, `TreeMap`.
///
/// MISTAKE: Subtracting works for marks, but it can overflow for very large or very negative numbers. The safer way is
/// the helper `Integer.compare(this.marks, other.marks)`. For Strings, use `this.name.compareTo(other.name)`.
///
/// --- Comparing Strings -----
/// `String` already implements Comparable (alphabetical order), so you can reuse it inside your own compareTo:

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String[] names = {"Mim", "Rafi", "Anik", "dee"};
        Arrays.sort(names);
        System.out.println(Arrays.toString(names));

        System.out.println("apple".compareTo("banana"));     // negative: apple first
        System.out.println("b".compareTo("a"));              // positive
        System.out.println("same".compareTo("same"));        // zero
    }
}

/// Notice "dee" (lower case) landed last. Capital letters sort before lower case in this order. For a case-insensitive
/// sort, use `String.CASE_INSENSITIVE_ORDER`, which is a Comparator (next topic).

/// ### Topic 3: Comparator: any order you like ###
/// A `Comparator<T>` is a separate piece of code with one method, `compare(a, b)`, that returns the same
/// negative/zero/positive answer for two objects. You can write as many as you like, for the same class, without
/// changing it. Pass one to `sort`.
///
/// First the long way, with a class:

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

class ByName implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return a.name.compareTo(b.name);
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("Rafi", 91));
        list.add(new Student("Mim", 67));
        list.add(new Student("Anik", 45));

        Collections.sort(list, new ByName());
        System.out.println(list);
    }
}

/// Writing a whole class for each order is heavy. Since Comparator has one method, you can write it as a LAMBDA, a short
/// anonymous function (the next lessons explain lambdas fully):

import java.util.ArrayList;
import java.util.Comparator;

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
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("Rafi", 91));
        list.add(new Student("Mim", 67));
        list.add(new Student("Anik", 45));

        list.sort((a, b) -> a.marks - b.marks);                  // by marks, lowest first
        System.out.println(list);

        list.sort((a, b) -> b.marks - a.marks);                  // highest first: swap a and b
        System.out.println(list);

        list.sort(Comparator.comparing(s -> s.name));            // by name, with a helper
        System.out.println(list);
    }
}

/// `(a, b) -> a.marks - b.marks` reads "given two students a and b, the answer is a's marks minus b's marks". To reverse an
/// order, swap the two. `Comparator.comparing(...)` takes a function that picks the part to compare, which is the tidiest
/// way.

/// ### Topic 4: Combining orders: thenComparing and reversed ###
/// Real sorts have tie-breakers: "highest marks first, and among equal marks, by name". Chain Comparators.

import java.util.ArrayList;
import java.util.Comparator;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    String getName() { return name; }
    int getMarks() { return marks; }

    @Override
    public String toString() {
        return name + " (" + marks + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("Rafi", 80));
        list.add(new Student("Mim", 80));
        list.add(new Student("Anik", 95));
        list.add(new Student("Dee", 60));

        list.sort(Comparator.comparing(Student::getMarks).reversed().thenComparing(Student::getName));
        System.out.println(list);
    }
}

/// Read it left to right: compare by marks, REVERSED (highest first), and where marks tie, `thenComparing` the name. Mim
/// and Rafi both have 80, so Mim (M before R) comes first. `Student::getMarks` is a METHOD REFERENCE, a shorthand for the
/// lambda `s -> s.getMarks()`.

/// ### Topic 5: Comparable or Comparator? ###
///     Comparable                              Comparator
///     the class itself implements it          a separate object or lambda
///     one natural order only                  as many orders as you want
///     method: compareTo(other)                method: compare(a, b)
///     you must own (edit) the class           works on classes you cannot edit (like String)
///     used automatically by sort, TreeSet     passed in: sort(list, comparator)
///
/// TIP: Use Comparable for the one obvious order (people by ID, dates by time). Use Comparator for everything else, or for
/// "sort by X this time".
///
/// --- A sorted collection in one go -----
/// A `TreeSet` or `TreeMap` keeps its contents sorted all the time, using Comparable or a Comparator you give it.

import java.util.Comparator;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        TreeSet<String> byLength = new TreeSet<>(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()));
        byLength.add("banana");
        byLength.add("fig");
        byLength.add("apple");
        byLength.add("kiwi");

        System.out.println(byLength);
    }
}

/// REMEMBER: `compareTo` and `compare` return negative, zero or positive. Comparable is built in and singular;
/// Comparator is external and flexible. Reverse an order with `.reversed()` or by swapping the two arguments, and break ties
/// with `thenComparing`.

/// ============================================================================
/// YOUR TURN
///   1. Make `Book` implement `Comparable<Book>` so that books sort by title. Sort a list of five books.
///   2. Sort the same books by price, cheapest first, using a Comparator lambda. Then most expensive first.
///   3. Sort students by marks descending and, for equal marks, by name ascending.
///   4. Sort an array of Strings by length, shortest first, with `Arrays.sort(array, comparator)`.
///   5. What happens if `compareTo` always returns 0? Try it with `Collections.sort` and with a `TreeSet`.
///   6. Explain when you would pick Comparator over Comparable.
/// ============================================================================
