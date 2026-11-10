/*
 * Beyond the Course: Lambdas and functional interfaces
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a lambda expression is: a short, nameless piece of code you can pass around
///   - Functional interfaces, and the ready-made ones: Predicate, Function, Consumer, Supplier
///   - Method references (Class::method)
///   - Using lambdas with lists, sorting and threads
/// ============================================================================


/// ### Topic 1: What is a lambda? ###
/// A LAMBDA EXPRESSION is a short piece of code, written without a name, that you can store in a variable, pass to a method or
/// return. It reads like a tiny method:
///
///     (parameters) -> expression
///
/// ANALOGY: Instead of hiring a named employee for a one-line job ("when the bell rings, print hello"), you hand over a sticky
/// note with the instruction on it.
///
/// You have already seen lambdas in earlier lessons: `n -> n % 2 == 0` in `removeIf`, `(a, b) -> a.marks - b.marks` in `sort`,
/// `() -> System.out.println("hi")` for a Thread. This lesson explains what they are.

/// ### Topic 2: The shape of a lambda ###
/// To get from a method to a lambda, strip it down:
///
///     int square(int x) { return x * x; }          the method
///     (int x) -> { return x * x; }                 drop the name and return type
///     x -> x * x                                   drop types, brackets and return when they are obvious
///
/// The rules:
///    - No parameters: `() -> ...`
///    - One parameter: the brackets are optional, `x -> ...`
///    - Several: `(a, b) -> ...`
///    - One expression: no braces and no `return`, as in `x -> x * x`.
///    - Several statements: braces and `return`, as in `x -> { int y = x * 2; return y + 1; }`.

/// ### Topic 3: Functional interfaces ###
/// A lambda needs something to be the lambda OF. That is a FUNCTIONAL INTERFACE: an interface with exactly one abstract
/// method (you met one at the end of the Interfaces lesson). The lambda supplies that one method's body.

interface Operation {
    int apply(int a, int b);
}

public class Main {
    static int calculate(int a, int b, Operation op) {
        return op.apply(a, b);
    }

    public static void main(String[] args) {
        Operation add = (a, b) -> a + b;
        Operation multiply = (a, b) -> a * b;

        System.out.println(add.apply(3, 4));
        System.out.println(multiply.apply(3, 4));

        System.out.println(calculate(10, 2, (a, b) -> a - b));        // a lambda passed straight in
        System.out.println(calculate(10, 2, (a, b) -> a / b));
    }
}

/// The method `calculate` does not know WHAT it will compute. The caller passes the behaviour in. This idea, passing code as
/// data, is what makes lambdas so useful. An optional annotation, `@FunctionalInterface`, makes the compiler check that the
/// interface really has just one abstract method.

/// ### Topic 4: The ready-made functional interfaces ###
/// Java provides the common shapes in the package `java.util.function`, so you rarely write your own:
///
///     Predicate<T>        T -> boolean         a yes/no test                  test(x)
///     Function<T, R>      T -> R               turns one thing into another   apply(x)
///     Consumer<T>         T -> nothing         does something with it         accept(x)
///     Supplier<T>         nothing -> T         produces a value               get()
///     BiFunction<T,U,R>   (T, U) -> R          two inputs, one output         apply(a, b)
///     UnaryOperator<T>    T -> T               same type in and out

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Function<String, Integer> length = s -> s.length();
        Consumer<String> shout = s -> System.out.println(s.toUpperCase() + "!");
        Supplier<String> greeting = () -> "hello";

        System.out.println(isEven.test(10));
        System.out.println(isEven.test(7));
        System.out.println(length.apply("lambda"));
        shout.accept(greeting.get());
    }
}

/// --- Combining them -----
/// These interfaces come with helper methods for chaining, which is like building a recipe from small parts.

import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Predicate<String> longWord = s -> s.length() > 4;
        Predicate<String> startsWithA = s -> s.startsWith("a");
        Predicate<String> both = longWord.and(startsWithA);
        Predicate<String> notLong = longWord.negate();

        System.out.println(both.test("apple"));
        System.out.println(both.test("ant"));
        System.out.println(notLong.test("ant"));

        Function<Integer, Integer> doubleIt = x -> x * 2;
        Function<Integer, Integer> plusOne = x -> x + 1;
        System.out.println(doubleIt.andThen(plusOne).apply(5));       // double, then add one
        System.out.println(doubleIt.compose(plusOne).apply(5));       // add one, then double
    }
}

/// ### Topic 5: Lambdas with collections ###
/// This is where lambdas earn their keep. Many collection methods take a lambda: `forEach`, `removeIf`, `sort`,
/// `replaceAll`, and for maps `forEach`, `computeIfAbsent`, `merge`.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Rafi", "Mim", "Anik", "Dee"));

        names.forEach(n -> System.out.println("Hello " + n));

        names.replaceAll(n -> n.toUpperCase());
        System.out.println(names);

        names.removeIf(n -> n.length() <= 3);
        System.out.println(names);

        names.sort((a, b) -> b.compareTo(a));                // Z to A
        System.out.println(names);

        Map<String, Integer> marks = new TreeMap<>();
        marks.put("Rafi", 91);
        marks.put("Mim", 67);
        marks.forEach((name, mark) -> System.out.println(name + " -> " + mark));
        marks.merge("Mim", 5, (oldMark, bonus) -> oldMark + bonus);     // add 5 to Mim's mark
        System.out.println(marks);
    }
}

/// ### Topic 6: Method references ###
/// When a lambda does nothing but call one existing method, you can name that method instead, using `::`. It is a shorthand,
/// and often reads better.
///
///     s -> s.length()               String::length            a method on the parameter
///     s -> System.out.println(s)    System.out::println       a method on a fixed object
///     x -> Integer.parseInt(x)      Integer::parseInt         a static method
///     () -> new ArrayList<>()       ArrayList::new            a constructor

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> words = List.of("apple", "fig", "banana");

        words.forEach(System.out::println);

        List<Integer> lengths = new java.util.ArrayList<>();
        for (String w : words) {
            lengths.add(w.length());
        }
        System.out.println(lengths);

        java.util.function.Function<String, Integer> parse = Integer::parseInt;
        System.out.println(parse.apply("123") + 1);
    }
}

/// ### Topic 7: What a lambda can use from outside ###
/// A lambda can read the local variables around it, as an anonymous class can, but only if they are final or EFFECTIVELY FINAL
/// (assigned once and never changed). It CAN change the fields of an object or an array element, because those do not change
/// the variable itself.

import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        int bonus = 10;
        Supplier<Integer> add = () -> 50 + bonus;
        System.out.println(add.get());

        int count = 0;
        Runnable r = () -> count++;
    }
}
/// @error

/// "local variables referenced from a lambda expression must be final or effectively final". The usual way round is an array
/// or `AtomicInteger` holding the changing value: `int[] count = {0}; () -> count[0]++`.
///
/// --- Lambdas and threads, and back to the Comparator -----
/// Everything you wrote with anonymous classes earlier can now be a lambda: `new Thread(() -> ...)`,
/// `button.addActionListener(e -> ...)`, `Comparator.comparing(s -> s.name)`. Whenever you see an arrow `->`, you are looking
/// at a lambda.
///
/// REMEMBER: A lambda is a nameless method: `(params) -> body`. It works wherever a functional interface is expected. Use
/// the built-in Predicate, Function, Consumer and Supplier. Method references (`Class::method`) shorten a lambda that only
/// calls one method.

/// ============================================================================
/// YOUR TURN
///   1. Write a `Predicate<String>` that tests whether a word has more than five letters, and use it to test three words.
///   2. Write a `Function<Double, Double>` that converts Celsius to Fahrenheit and apply it to 0, 37 and 100.
///   3. Given a list of numbers, use `removeIf` to delete the negatives and `replaceAll` to double the rest.
///   4. Rewrite `names.forEach(n -> System.out.println(n))` using a method reference.
///   5. Define your own functional interface `Validator` with `boolean check(String s)`, and create two lambdas: not empty,
///      and at least 8 characters.
///   6. Make a lambda change a local `int` and read the compiler's complaint. Then fix it with an array.
/// ============================================================================
