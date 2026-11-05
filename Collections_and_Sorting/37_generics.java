/*
 * Collections and Sorting: Generics
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What generics are, and the problem they solve
///   - Writing your own generic class with a type parameter such as T
///   - Writing a generic method
///   - Limiting the allowed types with `extends`, and a first look at wildcards
/// ============================================================================


/// ### Topic 1: The problem: a box that holds anything ###
/// Before generics (Java 5), a collection held plain `Object`s. Anything could go in, and you had to CAST when taking it
/// out. Mistakes appeared only at run time:

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList list = new ArrayList();      // no type given: holds any Object
        list.add("hello");
        list.add(42);

        String first = (String) list.get(0);
        String second = (String) list.get(1);  // 42 is not a String
        System.out.println(first + second);
    }
}
/// @error

/// The code compiled (with a warning), then crashed with a `ClassCastException`. It would be far better if the compiler
/// stopped you before the program ever ran.
///
/// GENERICS let a class or method be written for "some type T", and let the user say which type they want. The compiler
/// then checks every use.

/// ### Topic 2: Generics in action: the type in angle brackets ###
/// You have already used generics: the `<String>` in `ArrayList<String>`. It tells the compiler that this list holds only
/// Strings, so adding anything else is an error at compile time, and no casting is needed when reading.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Rafi");
        names.add(42);
    }
}
/// @error

/// "incompatible types: int cannot be converted to String". The mistake is caught immediately, in the editor, before the
/// program runs. That is the whole benefit: TYPE SAFETY.
///
/// WHY: Generics move a whole class of bugs from run time (a crash for a user) to compile time (a red underline for you).

/// ### Topic 3: Writing your own generic class ###
/// To write a class that works with any type, declare a TYPE PARAMETER in angle brackets after the class name. By
/// convention it is a single capital letter: `T` for Type, `E` for Element, `K` and `V` for Key and Value. Inside the
/// class, `T` is used like any other type.

class Box<T> {
    private T item;

    Box(T item) {
        this.item = item;
    }

    T get() {
        return item;
    }

    void set(T item) {
        this.item = item;
    }
}

public class Main {
    public static void main(String[] args) {
        Box<String> word = new Box<>("hello");
        Box<Integer> number = new Box<>(42);

        String w = word.get();               // no cast needed
        int n = number.get();

        System.out.println(w.toUpperCase());
        System.out.println(n + 1);
    }
}

/// One class `Box`, written once, works for Strings, Integers, and anything else. Each use fixes `T`: in `Box<String>`,
/// `get()` returns a String; in `Box<Integer>` it returns an Integer. The compiler even stops you from putting the
/// wrong thing in:

class Box<T> {
    private T item;

    Box(T item) {
        this.item = item;
    }

    T get() {
        return item;
    }
}

public class Main {
    public static void main(String[] args) {
        Box<String> word = new Box<>("hello");
        Integer n = word.get();
    }
}
/// @error

/// ### Topic 4: Two type parameters ###
/// A class can have more than one, like a Map's key and value. Here is a tiny pair class.

class Pair<K, V> {
    private K first;
    private V second;

    Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    K getFirst() {
        return first;
    }

    V getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        Pair<String, Integer> student = new Pair<>("Rafi", 91);
        Pair<Double, Double> point = new Pair<>(2.5, 4.0);

        System.out.println(student);
        System.out.println(point);
        System.out.println(student.getFirst() + " scored " + student.getSecond());
    }
}

/// ### Topic 5: Generic methods ###
/// A single METHOD can be generic too. Put the type parameter in angle brackets BEFORE the return type. Java works out
/// `T` from the arguments, so you do not write it when calling.

public class Main {
    static <T> void printAll(T[] items) {
        for (T item : items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    static <T> T firstOf(T[] items) {
        return items[0];
    }

    public static void main(String[] args) {
        String[] names = {"Rafi", "Mim", "Anik"};
        Integer[] numbers = {10, 20, 30};

        printAll(names);
        printAll(numbers);
        System.out.println(firstOf(names));
        System.out.println(firstOf(numbers));
    }
}

/// The same `printAll` works for any kind of array. Note it uses `Integer[]` and not `int[]`: type parameters stand for
/// objects, never primitives, just as with `ArrayList<int>`.

/// ### Topic 6: Limiting the type with extends ###
/// Sometimes "any type" is too loose. A method that finds the biggest item needs to compare items, so `T` must be
/// something comparable. We say `T extends Comparable<T>`, which reads "T is any type that can be compared with its own
/// kind". (The Comparable lesson covers that interface.) This is a BOUNDED type.

public class Main {
    static <T extends Comparable<T>> T biggest(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    public static void main(String[] args) {
        System.out.println(biggest(3, 9));
        System.out.println(biggest("apple", "banana"));
        System.out.println(biggest(2.5, 1.5));
    }
}

/// `compareTo` returns a positive number, zero or a negative number, which is how the method decides. Integers, Strings
/// and Doubles all know how to compare themselves, so all three calls work. Pass something that cannot be compared (a
/// plain object) and the compiler refuses.

/// ### Topic 7: A first look at wildcards ###
/// What if a method wants "a list of anything" and only reads it? You might expect `List<Object>` to accept a
/// `List<String>`. It does NOT, because a list of Strings is not a list of Objects (you could sneak an Integer into it).
/// The wildcard `?` means "some unknown type", and fits.

import java.util.ArrayList;
import java.util.List;

public class Main {
    static void show(List<?> items) {
        for (Object item : items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<String> words = new ArrayList<>(List.of("a", "b"));
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3));

        show(words);
        show(numbers);
    }
}

/// TIP: You will read generics far more often than you write them. Knowing what `List<String>`, `Map<String, Integer>`
/// and `<T>` mean is the important part. Writing your own generic class is for when you build a reusable library.
///
/// REMEMBER: Generics give type safety: a collection or class is written once for "some type T", and the compiler checks
/// each use. T cannot be a primitive. `extends` limits what T may be.

/// ============================================================================
/// YOUR TURN
///   1. Add a method `isEmpty()` to `Box<T>` that returns true when the item is null.
///   2. Write a generic method `swap(T[] array, int i, int j)` that swaps two elements, and test it on Strings and
///      Integers.
///   3. Create `Pair<String, Double>` for a product and its price, and print it.
///   4. Write `static <T extends Comparable<T>> T smallest(T[] items)` that returns the smallest item.
///   5. Try to add an int to an `ArrayList<String>` and read the error.
///   6. In your own words: what problem did generics solve?
/// ============================================================================
