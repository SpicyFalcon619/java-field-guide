/*
 * Collections and Sorting: HashMap, HashSet and iterating
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a Set is (no duplicates) and what a Map is (key and value pairs)
///   - HashSet, HashMap, and the ordered cousins LinkedHashMap and TreeMap
///   - Putting, getting, checking and removing, and looping over a map
///   - A classic use: counting how often each word appears
/// ============================================================================


/// ### Topic 1: Choosing the right collection ###
/// A list keeps items in order and allows repeats. Two other shapes solve different problems:
///
///     List   an ordered row, repeats allowed       [Rafi, Mim, Rafi]
///     Set    a bag of UNIQUE items                 {Rafi, Mim}
///     Map    a dictionary: KEY -> VALUE            {Rafi=91, Mim=67}
///
/// ANALOGY: A list is a queue of people. A set is the list of members of a club (nobody joins twice). A map is a phone
/// book: you look up a name (the key) and get a number (the value).

/// ### Topic 2: HashSet, an unordered bag with no repeats ###
/// Adding something already in a set does nothing (and `add` returns `false` to tell you). That makes a set the
/// natural way to remove duplicates or to ask "have I seen this before?" quickly.

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        HashSet<String> tags = new HashSet<>();

        System.out.println(tags.add("java"));
        System.out.println(tags.add("oop"));
        System.out.println(tags.add("java"));     // already there

        System.out.println(tags.size());
        System.out.println(tags.contains("oop"));
        System.out.println(tags.contains("python"));
    }
}

/// Removing duplicates from a list is a classic job. Put the list into a set:

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> withRepeats = new ArrayList<>(List.of(3, 1, 3, 2, 1, 3));
        HashSet<Integer> unique = new HashSet<>(withRepeats);

        System.out.println(withRepeats.size() + " numbers, " + unique.size() + " different");
        System.out.println(unique);
    }
}

/// WHY is it called "Hash"? A HashSet files each item by a number calculated from it (its "hash code"), the way a library
/// files a book by its code. That makes `contains` very fast even for a million items. The price is that the items are not
/// kept in the order you added them. If you need the order, see the next topic.

/// ### Topic 3: HashMap, key and value ###
/// A map stores PAIRS. Each key appears once and has one value. You give a key, and get the value back.
///
///     put(key, value)       add or replace a pair
///     get(key)              the value, or null if the key is missing
///     getOrDefault(key, d)  the value, or d if the key is missing
///     containsKey(key)      is this key present?
///     remove(key)           delete the pair
///     size(), isEmpty(), clear()
///     keySet()              all the keys
///     values()              all the values
///
/// Like ArrayList, a map is generic: `HashMap<String, Integer>` is a map from Strings to Integers. (Two type
/// arguments, because there are two kinds of thing.)

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> marks = new HashMap<>();

        marks.put("Rafi", 91);
        marks.put("Mim", 67);
        marks.put("Anik", 45);

        System.out.println(marks.get("Mim"));
        System.out.println(marks.get("Nobody"));
        System.out.println(marks.getOrDefault("Nobody", 0));
        System.out.println(marks.containsKey("Rafi"));
        System.out.println(marks.size());

        marks.put("Mim", 70);               // same key: the old value is replaced
        marks.remove("Anik");

        System.out.println(marks.get("Mim"));
        System.out.println(marks.size());
    }
}

/// Two details. A missing key gives `null`, not an error, which is why `getOrDefault` is handy. And `put` with an
/// existing key REPLACES the value. A key can never appear twice.

/// ### Topic 4: Looping over a map ###
/// Use `keySet()` for the keys, `values()` for the values, or `entrySet()` for both at once. An ENTRY is one
/// key-and-value pair.

import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> marks = new TreeMap<>();      // TreeMap keeps keys sorted
        marks.put("Rafi", 91);
        marks.put("Mim", 67);
        marks.put("Anik", 45);

        for (String name : marks.keySet()) {
            System.out.println(name + " has " + marks.get(name));
        }

        for (Map.Entry<String, Integer> entry : marks.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        int total = 0;
        for (int mark : marks.values()) {
            total += mark;
        }
        System.out.println("Total: " + total);
    }
}

/// ### Topic 5: Order matters (or does not) ###
/// The three kinds of map and set differ in order:
///
///     HashMap / HashSet               no promised order; the fastest
///     LinkedHashMap / LinkedHashSet   keeps the order you added things
///     TreeMap / TreeSet               keeps keys SORTED (A to Z, smallest first)
///
/// WARNING: Never depend on the order of a HashMap or HashSet. It can change between Java versions or when the map
/// grows. If the order matters, choose one of the other two.

import java.util.LinkedHashMap;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        LinkedHashMap<String, Integer> inOrder = new LinkedHashMap<>();
        inOrder.put("zebra", 1);
        inOrder.put("apple", 2);
        inOrder.put("mango", 3);
        System.out.println(inOrder);               // the order we added them

        TreeSet<String> sorted = new TreeSet<>();
        sorted.add("zebra");
        sorted.add("apple");
        sorted.add("mango");
        System.out.println(sorted);                // alphabetical, whatever the order added
    }
}

/// ### Topic 6: A real use: counting words ###
/// A map is perfect for counting. The word is the key and the number of times is the value. For each word, add one to
/// what is already there.

import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        String text = "the cat and the dog and the bird";
        Map<String, Integer> counts = new TreeMap<>();

        for (String word : text.split(" ")) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }

        System.out.println(counts);
    }
}

/// Read the middle line carefully, because it is a pattern you will use again: "take the current count (or 0 if the word
/// is new), add one, and store it back".
///
/// --- Keys must behave well -----
/// Maps and sets decide whether two keys are "the same" using `equals` and `hashCode`. Strings and the number wrappers
/// already do this properly. If you use your OWN class as a key, you must override both, or two equal-looking objects
/// will be treated as different:

import java.util.HashSet;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    public static void main(String[] args) {
        HashSet<Point> points = new HashSet<>();
        points.add(new Point(1, 2));
        points.add(new Point(1, 2));
        System.out.println(points.size());
    }
}

/// The set holds two "identical" points, because without `equals` and `hashCode`, two separately built objects are
/// never equal. Adding those two methods (your IDE can generate them) makes the set see them as one.
///
/// REMEMBER: Set = no repeats. Map = key to value. HashMap and HashSet are fast but unordered, LinkedHash keeps your order,
/// Tree keeps them sorted. `getOrDefault` makes counting easy.

/// ============================================================================
/// YOUR TURN
///   1. Store five students and their marks in a `HashMap<String, Integer>`. Print one student's mark, then change it.
///   2. Print every key and value of the map using `entrySet()`.
///   3. Count how many times each letter appears in "mississippi" using a TreeMap.
///   4. Take a list of names with repeats, put it in a `LinkedHashSet`, and print the names without repeats in the
///      order they first appeared.
///   5. Make a phone book: ask the user for a name and print the number, or "not found".
///   6. Explain why `put` with an existing key does not create a second entry.
/// ============================================================================
