/*
 * Beyond the Course: Wrapper classes, Math, Random and dates
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Wrapper classes (Integer, Double...), autoboxing, and parsing
///   - The Math class
///   - Random numbers, with Random and Math.random()
///   - Dates and times with LocalDate, LocalTime and LocalDateTime
///   - Comparing the right way: Strings, wrappers and the == trap
/// ============================================================================


/// ### Topic 1: Wrapper classes ###
/// Every primitive type has an object twin, a WRAPPER class, so that primitives can live where only objects are allowed
/// (inside an `ArrayList`, as a map key, as a generic type).
///
///     int -> Integer      double -> Double      char -> Character     boolean -> Boolean
///     long -> Long        float -> Float        short -> Short        byte -> Byte
///
/// The conversion between the two is automatic. Java wraps a primitive into an object ("AUTOBOXING") and unwraps it back
/// ("UNBOXING") whenever needed.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Integer boxed = 42;                  // autoboxing: int -> Integer
        int plain = boxed;                   // unboxing: Integer -> int

        ArrayList<Integer> list = new ArrayList<>();
        list.add(7);                         // boxed for you
        int first = list.get(0) + 1;         // unboxed for you

        System.out.println(boxed + " " + plain + " " + first);
    }
}

/// ### Topic 2: What wrappers give you ###
/// Wrapper classes carry useful constants and methods. You have used `Integer.parseInt` already.

public class Main {
    public static void main(String[] args) {
        System.out.println(Integer.parseInt("123") + 1);
        System.out.println(Double.parseDouble("3.5") * 2);
        System.out.println(Integer.valueOf(7));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.toBinaryString(10));
        System.out.println(Integer.toHexString(255));
        System.out.println(Integer.compare(3, 9));
        System.out.println(Character.isDigit('7'));
        System.out.println(Character.toUpperCase('a'));
        System.out.println(Boolean.parseBoolean("true"));
    }
}

/// Converting text to numbers can fail, which is a runtime exception you can catch:

public class Main {
    public static void main(String[] args) {
        String typed = "12x";
        try {
            int n = Integer.parseInt(typed);
            System.out.println(n);
        } catch (NumberFormatException e) {
            System.out.println("That is not a whole number: " + typed);
        }
    }
}

/// ### Topic 3: The == trap with wrappers ###
/// Wrappers are objects, so `==` compares ADDRESSES, not values. Small numbers happen to be cached and look equal, which
/// hides the bug until the numbers get bigger.

public class Main {
    public static void main(String[] args) {
        Integer a = 100, b = 100;
        Integer c = 1000, d = 1000;

        System.out.println(a == b);          // true, but only by luck of the cache
        System.out.println(c == d);          // false!
        System.out.println(c.equals(d));     // true: compare values with equals
    }
}

/// MISTAKE: Compare wrappers (and Strings) with `equals`, never `==`. And remember that an `Integer` can be `null`, so
/// unboxing a null throws a `NullPointerException`.

/// ### Topic 4: The Math class ###
/// `Math` is full of static methods (so no object is needed) and two constants, `Math.PI` and `Math.E`.
///
///     abs(x)  max(a,b)  min(a,b)  pow(a,b)  sqrt(x)  cbrt(x)
///     round(x)  floor(x)  ceil(x)  random()  sin cos tan  log  log10  hypot

public class Main {
    public static void main(String[] args) {
        System.out.println(Math.abs(-7));
        System.out.println(Math.max(3, 9) + " " + Math.min(3, 9));
        System.out.println(Math.pow(2, 10));
        System.out.println(Math.sqrt(81));
        System.out.println(Math.round(3.5) + " " + Math.round(3.4) + " " + Math.round(-3.5));
        System.out.println(Math.floor(3.9) + " " + Math.ceil(3.1));
        System.out.println(Math.PI);
        System.out.printf("%.2f%n", Math.hypot(3, 4));
    }
}

/// `round` returns a whole number, `floor` rounds down and `ceil` rounds up, but both return a `double`. To round to 2
/// decimals, use `Math.round(x * 100) / 100.0`, or `printf("%.2f")` for display.

/// ### Topic 5: Random numbers ###
/// `Math.random()` gives a double from 0.0 up to (not including) 1.0. For more control use the `Random` class in `java.util`.
///
///     nextInt(n)       a whole number from 0 to n-1
///     nextInt(a, b)    from a to b-1 (Java 17+)
///     nextDouble()     0.0 up to 1.0
///     nextBoolean()    true or false
///
/// A `Random` made with a SEED (`new Random(42)`) gives the same "random" sequence every time, which is useful for tests,
/// and is why the output here is the same each run. Without a seed it differs every time.

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random dice = new Random(42);                     // seeded: repeatable

        for (int i = 0; i < 5; i++) {
            System.out.print((dice.nextInt(6) + 1) + " ");        // 1 to 6
        }
        System.out.println();

        int pick = (int) (Math.random() * 10);            // 0 to 9, different each run
        System.out.println(pick >= 0 && pick < 10);
    }
}

/// To pick a random item from a list, use a random index: `list.get(random.nextInt(list.size()))`. To shuffle one, use
/// `Collections.shuffle(list)`.

/// ### Topic 6: Dates and times ###
/// Java 8 added a clear, safe set of date classes in `java.time`. They are IMMUTABLE (each change gives back a new object).
///
///     LocalDate        a date with no time:     2026-11-05
///     LocalTime        a time with no date:     14:30:00
///     LocalDateTime    both together
///     Duration, Period   a length of time
///     DateTimeFormatter  to print in a chosen pattern

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate birthday = LocalDate.of(2004, 3, 15);
        LocalDate day = LocalDate.of(2026, 11, 5);

        System.out.println(birthday);
        System.out.println(day.getDayOfWeek());
        System.out.println(day.plusDays(30));
        System.out.println(day.minusMonths(2));
        System.out.println(day.isAfter(birthday));
        System.out.println(day.isLeapYear());
        System.out.println(birthday.getYear() + " " + birthday.getMonth() + " " + birthday.getDayOfMonth());
    }
}

/// `LocalDate.now()` gives today's date, and `LocalTime.now()` the current time. They are not used in the example because
/// their output would differ every day.

/// ### Topic 7: Time between dates, and formatting ###

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        LocalDate born = LocalDate.of(2004, 3, 15);
        LocalDate today = LocalDate.of(2026, 11, 5);

        Period age = Period.between(born, today);
        System.out.println(age.getYears() + " years, " + age.getMonths() + " months, " + age.getDays() + " days");
        System.out.println(ChronoUnit.DAYS.between(born, today) + " days old");

        LocalDateTime meeting = LocalDateTime.of(2026, 12, 1, 14, 30);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println(meeting.format(pattern));

        LocalDate parsed = LocalDate.parse("2027-01-31");
        System.out.println(parsed.plusMonths(1));           // 31 January + 1 month: no 31 February
    }
}

/// An invalid date such as 31 February is rejected with an exception, rather than silently turning into March.
///
/// WHY: The older classes `Date` and `Calendar` are confusing and easy to get wrong (months counted from 0, for example). Use
/// `java.time` for anything new.
///
/// REMEMBER: Wrappers let primitives live in collections, and Java boxes and unboxes for you. Compare wrappers with `equals`.
/// `Math` is all static methods. `Random` with a seed repeats. `LocalDate` and friends are immutable, so each change gives a
/// new object.

/// ============================================================================
/// YOUR TURN
///   1. Convert the text "2500" to an int, add 500, and print it. Wrap it so a bad input like "25x" prints a friendly message.
///   2. Show the `Integer` == trap with 127 and 128 and explain it.
///   3. Calculate the hypotenuse of a triangle with sides 6 and 8 using `Math`.
///   4. Simulate rolling two dice 10 times and count how many rolls total 7. Use a seeded Random.
///   5. Print what date it will be 100 days after 1 January 2027, and which day of the week it is.
///   6. Work out your age in years and in days as of a date you choose.
/// ============================================================================
