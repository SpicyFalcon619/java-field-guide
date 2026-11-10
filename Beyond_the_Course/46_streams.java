/*
 * Beyond the Course: Streams
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a stream is, and how it differs from a collection
///   - The three parts of a stream pipeline: source, intermediate steps and a terminal step
///   - filter, map, sorted, distinct, limit, and the terminal operations count, sum, collect and forEach
///   - Optional, and grouping results
/// ============================================================================


/// ### Topic 1: What is a stream? ###
/// A STREAM is a way of processing a sequence of items by describing WHAT you want done, step by step, instead of writing a
/// loop that says HOW. It does not store the items. It flows them through a pipeline, like water through pipes with filters
/// and valves on the way.
///
/// ANALOGY: A factory conveyor belt. Items start on the belt (the source). One worker removes the faulty ones (filter). Another
/// paints each one (map). At the end, a box collects them (the terminal step).
///
/// Here is the same job done both ways, "the names longer than 3 letters, in capitals":

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = List.of("Rafi", "Mim", "Anik", "Dee", "Shafiq");

        // With a loop
        List<String> result1 = new ArrayList<>();
        for (String n : names) {
            if (n.length() > 3) {
                result1.add(n.toUpperCase());
            }
        }
        System.out.println(result1);

        // With a stream
        List<String> result2 = names.stream()
                .filter(n -> n.length() > 3)
                .map(n -> n.toUpperCase())
                .toList();
        System.out.println(result2);
    }
}

/// The stream version reads almost like a sentence: take the names, keep those longer than 3, turn each into capitals, make a
/// list. It uses lambdas (the previous lesson), so you need those first.

/// ### Topic 2: The three parts of a pipeline ###
///     1. SOURCE             where the items come from: list.stream(), Arrays.stream(array), Stream.of(...), IntStream.range(...)
///     2. INTERMEDIATE steps transform the stream and give back a stream: filter, map, sorted, distinct, limit, skip
///     3. TERMINAL step      ends the pipeline and produces a result: count, sum, collect, toList, forEach, min, max, anyMatch
///
/// NOTE: Nothing happens until the terminal step runs. The intermediate steps are only a recipe. This is called LAZY
/// evaluation. And a stream can be used only once.
///
/// A stream NEVER changes its source. `names.stream()...` leaves `names` exactly as it was.

/// ### Topic 3: Filtering and mapping ###
/// `filter` keeps the items that pass a test (a Predicate). `map` turns each item into something else (a Function).

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> evens = numbers.stream().filter(n -> n % 2 == 0).toList();
        List<Integer> squares = numbers.stream().map(n -> n * n).toList();
        List<Integer> bigSquaresOfEvens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .filter(n -> n > 10)
                .toList();

        System.out.println(evens);
        System.out.println(squares);
        System.out.println(bigSquaresOfEvens);
    }
}

/// ### Topic 4: Sorting, distinct, limit and skip ###

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> data = List.of(5, 3, 9, 3, 1, 9, 7);

        System.out.println(data.stream().distinct().toList());                           // remove repeats
        System.out.println(data.stream().sorted().toList());                             // ascending
        System.out.println(data.stream().sorted((a, b) -> b - a).toList());              // descending
        System.out.println(data.stream().sorted().limit(3).toList());                    // the first three
        System.out.println(data.stream().sorted().skip(5).toList());                     // all but the first five
    }
}

/// ### Topic 5: Terminal operations that give a single answer ###
///     count()              how many
///     sum(), average()     for number streams (IntStream, DoubleStream)
///     min(), max()         the smallest or largest (these return an Optional, see below)
///     anyMatch / allMatch / noneMatch    does any/all/none match a test? (true or false)
///     forEach(...)         do something with each item
///
/// For numbers, `mapToInt` turns a stream of objects into an `IntStream`, which has `sum()` and `average()`.

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> marks = List.of(72, 95, 60, 88, 45);

        System.out.println(marks.stream().count());
        System.out.println(marks.stream().mapToInt(m -> m).sum());
        System.out.println(marks.stream().mapToInt(m -> m).average().getAsDouble());
        System.out.println(marks.stream().anyMatch(m -> m < 50));
        System.out.println(marks.stream().allMatch(m -> m >= 40));
        System.out.println(marks.stream().filter(m -> m >= 60).count() + " passed with 60 or more");

        marks.stream().sorted().forEach(m -> System.out.print(m + " "));
        System.out.println();
    }
}

/// ### Topic 6: Optional: an answer that might not exist ###
/// `max`, `min` and `findFirst` on a stream might have nothing to return (an empty stream). Instead of returning `null` and
/// inviting a NullPointerException, they return an `Optional`: a box that either holds a value or is empty.
///
///     isPresent()          is there a value?
///     get()                the value (error if empty, so check first)
///     orElse(x)            the value, or x if empty
///     ifPresent(lambda)    do something only if there is a value

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<String> names = List.of("Rafi", "Mim", "Anik");

        Optional<String> longest = names.stream().max((a, b) -> a.length() - b.length());
        System.out.println(longest.get());

        Optional<String> startsWithZ = names.stream().filter(n -> n.startsWith("Z")).findFirst();
        System.out.println(startsWithZ.isPresent());
        System.out.println(startsWithZ.orElse("nobody"));

        longest.ifPresent(n -> System.out.println("Longest: " + n));
    }
}

/// ### Topic 7: Collecting results ###
/// `collect` gathers the stream into a collection or a summary, using ready-made `Collectors`. Streams of objects are
/// where this shines.

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Student {
    String name;
    String dept;
    int marks;

    Student(String name, String dept, int marks) {
        this.name = name;
        this.dept = dept;
        this.marks = marks;
    }

    String getName() { return name; }
    String getDept() { return dept; }
    int getMarks() { return marks; }
}

public class Main {
    public static void main(String[] args) {
        List<Student> students = List.of(
                new Student("Rafi", "CSE", 91),
                new Student("Mim", "EEE", 67),
                new Student("Anik", "CSE", 45),
                new Student("Dee", "EEE", 80));

        // names of those who passed, joined with commas
        String passed = students.stream()
                .filter(s -> s.getMarks() >= 60)
                .map(Student::getName)
                .collect(Collectors.joining(", "));
        System.out.println(passed);

        // group by department
        Map<String, List<String>> byDept = students.stream()
                .collect(Collectors.groupingBy(Student::getDept, Collectors.mapping(Student::getName, Collectors.toList())));
        System.out.println(byDept);

        // the average mark per department
        Map<String, Double> average = students.stream()
                .collect(Collectors.groupingBy(Student::getDept, Collectors.averagingInt(Student::getMarks)));
        System.out.println(average);
    }
}

/// `groupingBy` is the stream version of "put every item in the right pile". Compare it with the loop, `HashMap` and
/// `getOrDefault` you would have written otherwise.

/// ### Topic 8: Streams of numbers ###
/// `IntStream.range(1, 6)` produces 1, 2, 3, 4, 5 (the end is excluded), a tidy replacement for a counting loop.

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        System.out.println(IntStream.range(1, 6).sum());
        System.out.println(IntStream.rangeClosed(1, 5).map(n -> n * n).sum());

        IntStream.rangeClosed(1, 3).forEach(i -> System.out.println("Round " + i));
    }
}

/// --- When to use a stream, and when not -----
///    - Use a stream for "filter, transform, summarise" jobs: they read clearly.
///    - Use a plain loop when you need an index, early exit with several conditions, or to change variables as you go.
///    - Do not change outside variables inside a stream's lambdas. Let the stream produce a result and use that.
///
/// REMEMBER: A stream is source, then intermediate steps, then one terminal step. Nothing runs until the terminal step. It does
/// not change the source, and can be used once. `Optional` represents "maybe a value", and `Collectors` gather results.

/// ============================================================================
/// YOUR TURN
///   1. From a list of numbers, use a stream to get the squares of the odd numbers.
///   2. Count the words in a list that have more than four letters.
///   3. Print the three highest marks from a list, highest first.
///   4. Join a list of names into one String with ", " between them.
///   5. With the Student class above, find the student with the highest marks using `max` and print the name from the Optional.
///   6. Group a list of words by their first letter using `groupingBy`.
/// ============================================================================
