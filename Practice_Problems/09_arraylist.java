/*
 * Practice: ArrayList problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Creating an `ArrayList` of a chosen type, and adding, reading, removing and checking items
///   - Looping over a list with a `for` loop and with an enhanced `for` loop
///   - Sorting and reversing with `Collections.sort()` and `Collections.reverse()`
///   - Removing duplicates, merging lists, and removing every copy of one value
///   - Storing your own objects (a `Book` class) in a list
/// ============================================================================

/// Eleven problems on `ArrayList`. Remember that a list holds objects, so the type goes in angle brackets and a number list
/// is `ArrayList<Integer>`, with the wrapper class, not `int`.

/// ### Problem 1: A list of names ###
/// Create an `ArrayList` of `String` to store names. Add five names, then print each one with a `for` loop.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Rafi");
        names.add("Mim");
        names.add("Anik");
        names.add("Dee");
        names.add("Sara");

        for (int i = 0; i < names.size(); i++) {
            System.out.println(i + ": " + names.get(i));
        }
    }
}

/// A list does not use `names[i]` or `names.length` like an array. It uses `get(i)` and `size()`.

/// ### Problem 2: Numbers from 1 to 10 ###
/// Create an `ArrayList` of `Integer` holding the numbers 1 to 10. Print the size of the list and the third element using
/// `get()`.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        System.out.println("Size: " + numbers.size());
        System.out.println("Third element: " + numbers.get(2));     // positions start at 0
    }
}

/// The third element is at index 2, because counting starts from zero.

/// ### Problem 3: Removing a city ###
/// Create an `ArrayList` of `String` with five city names. Remove the third city and print the list.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> cities = new ArrayList<>();
        cities.add("Dhaka");
        cities.add("Chittagong");
        cities.add("Khulna");
        cities.add("Sylhet");
        cities.add("Rajshahi");

        System.out.println("Before: " + cities);
        String removed = cities.remove(2);          // index 2 is the third city
        System.out.println("Removed: " + removed);
        System.out.println("After: " + cities);
    }
}

/// Printing a list directly gives a readable `[a, b, c]` form. The items after the removed one slide up to fill the gap.

/// ### Problem 4: Does the list contain 3? ###
/// Create an `ArrayList` of `Integer` with the numbers 1 to 5. Check whether it contains 3 and print a message.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            numbers.add(i);
        }

        if (numbers.contains(3)) {
            System.out.println("The list contains 3");
        } else {
            System.out.println("The list does not contain 3");
        }

        System.out.println("Contains 9? " + numbers.contains(9));
    }
}

/// ### Problem 5: Doubles and the enhanced for loop ###
/// Create an `ArrayList` of `Double` with five decimal numbers. Print each one using an enhanced `for` loop.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Double> prices = new ArrayList<>();
        prices.add(12.5);
        prices.add(99.99);
        prices.add(3.14);
        prices.add(250.0);
        prices.add(0.75);

        for (double price : prices) {
            System.out.println(price);
        }
    }
}

/// The enhanced `for` loop (read it as "for each price in prices") is shorter when you do not need the index.

/// ### Problem 6: Sorting fruits ###
/// Create an `ArrayList` of `String` with five fruit names. Sort it alphabetically with `Collections.sort()` and print it.

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> fruits = new ArrayList<>();
        fruits.add("Mango");
        fruits.add("Banana");
        fruits.add("Orange");
        fruits.add("Apple");
        fruits.add("Guava");

        System.out.println("Before: " + fruits);
        Collections.sort(fruits);
        System.out.println("Sorted: " + fruits);
    }
}

/// ### Problem 7: Reversing a list ###
/// Create an `ArrayList` of `Integer` with the numbers 1 to 10. Reverse it with `Collections.reverse()` and print it.

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numbers.add(i);
        }

        Collections.reverse(numbers);
        System.out.println("Reversed: " + numbers);
    }
}

/// ### Problem 8: Removing duplicate names ###
/// Create an `ArrayList` of `String` that contains some duplicate names. Write a method that removes the duplicates and print
/// the unique list.

import java.util.ArrayList;

public class Main {
    // Builds a new list that keeps only the first copy of each name.
    static ArrayList<String> removeDuplicates(ArrayList<String> names) {
        ArrayList<String> unique = new ArrayList<>();
        for (String name : names) {
            if (!unique.contains(name)) {
                unique.add(name);
            }
        }
        return unique;
    }

    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Rafi");
        names.add("Mim");
        names.add("Rafi");
        names.add("Anik");
        names.add("Mim");
        names.add("Rafi");

        System.out.println("With duplicates: " + names);
        System.out.println("Unique:          " + removeDuplicates(names));
    }
}

/// The method asks "is this name already in the new list?" before adding it, and the order of first appearance is kept.
///
/// TIP: Java also has a shortcut. A `LinkedHashSet` never holds the same value twice and keeps the order, so
/// `new ArrayList<>(new LinkedHashSet<>(names))` does the same job in one line. You will meet sets in the collections lessons.

/// ### Problem 9: Merging two lists ###
/// Create two `ArrayList` of `String`, one with five country names and one with five city names. Merge them into one list and
/// print it.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("Bangladesh");
        countries.add("Japan");
        countries.add("Brazil");
        countries.add("Kenya");
        countries.add("Norway");

        ArrayList<String> cities = new ArrayList<>();
        cities.add("Dhaka");
        cities.add("Tokyo");
        cities.add("Rio");
        cities.add("Nairobi");
        cities.add("Oslo");

        ArrayList<String> merged = new ArrayList<>(countries);   // start with a copy of the first list
        merged.addAll(cities);                                   // add every item of the second

        System.out.println("Merged (" + merged.size() + " items): " + merged);
    }
}

/// ### Problem 10: A list of Book objects ###
/// Create a `Book` class with a title and an author. Create an `ArrayList` of `Book` holding five books and print the title
/// and author of each.

import java.util.ArrayList;

class Book {
    String title;
    String author;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}

public class Main {
    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("Clean Code", "Robert C. Martin"));
        books.add(new Book("Effective Java", "Joshua Bloch"));
        books.add(new Book("Head First Java", "Kathy Sierra"));
        books.add(new Book("Refactoring", "Martin Fowler"));
        books.add(new Book("The Pragmatic Programmer", "Andrew Hunt"));

        for (Book book : books) {
            System.out.println(book.title + " by " + book.author);
        }
    }
}

/// A list can hold objects of any class you write. Each item is a `Book`, so you can use its fields after `get(i)` or in a
/// loop variable.

/// ### Problem 11: Removing every 5 ###
/// Create an `ArrayList` of `Integer` that holds the number 5 several times. Write a method that removes every occurrence of 5
/// and print the changed list.
///
/// WARNING: `list.remove(5)` does NOT remove the value 5. For an `int` argument, `remove` means "remove the item at INDEX 5".
/// To remove a value, wrap it: `list.remove(Integer.valueOf(5))`.

import java.util.ArrayList;

public class Main {
    static void removeAll(ArrayList<Integer> list, int value) {
        while (list.contains(value)) {
            list.remove(Integer.valueOf(value));     // removes the first matching VALUE
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(1);
        numbers.add(5);
        numbers.add(5);
        numbers.add(7);
        numbers.add(5);
        numbers.add(9);

        System.out.println("Before: " + numbers);
        removeAll(numbers, 5);
        System.out.println("After:  " + numbers);
    }
}

/// The loop removes one 5 per turn and checks again, so even several 5 in a row are all removed. A one-line alternative is
/// `numbers.removeIf(n -> n == 5)`, which uses a lambda (see the Beyond the Course lessons).
///
/// MISTAKE: Do not remove items from a list inside an ordinary `for (int i = 0; ...)` loop without care. After a removal the
/// next item slides into the spot you just visited, so the loop skips it. Two 5s side by side would leave the second one
/// behind.
