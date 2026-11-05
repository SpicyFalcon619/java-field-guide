/*
 * Java Basics: Strings
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a String is and what it can do: length, charAt, substring, case, search and compare
///   - Why strings are immutable
///   - The most important rule: compare strings with equals, never with ==
///   - StringBuilder for building text efficiently, and printf for neat output
/// ============================================================================


/// ### Topic 1: What is a String? ###
/// A String is a sequence of characters: a word, a sentence, a whole paragraph. In Java, String is a class, so a
/// string is an OBJECT, and objects come with METHODS that you call with a dot: `name.length()`.
///
/// Write a String literal between double quotes. The empty string `""` has no characters. Using `+` joins
/// strings together, which you have been doing since the first lesson.

public class Main {
    public static void main(String[] args) {
        String first = "Ada";
        String last = "Lovelace";
        String full = first + " " + last;

        System.out.println(full);
        System.out.println(full.length());
    }
}

/// `length()` has brackets here, while an array's `.length` did not. A String's length is a method, an array's is
/// a plain field. It is a quirk, but a compile error will remind you.

/// ### Topic 2: Looking inside a string ###
/// Just like an array, characters in a String are numbered from 0.
///
///     charAt(i)             the character at position i
///     substring(a, b)       from position a up to (but NOT including) b
///     substring(a)          from position a to the end
///     indexOf("x")          the position where "x" first appears, or -1
///     contains("x")         true if it appears anywhere
///     startsWith("x")       true if it begins with x
///     endsWith("x")         true if it ends with x

public class Main {
    public static void main(String[] args) {
        String s = "Hello, Java!";

        System.out.println(s.charAt(0));
        System.out.println(s.substring(7, 11));
        System.out.println(s.substring(7));
        System.out.println(s.indexOf("Java"));
        System.out.println(s.indexOf("Python"));
        System.out.println(s.contains("Hello"));
        System.out.println(s.startsWith("Hi"));
    }
}

/// `indexOf` returns -1 when the text is not found, which is how you test for absence. Notice that `substring(7,
/// 11)` includes position 7 and excludes position 11. The "up to but not including" rule is the same one used by
/// loops (`i < n`), and it means the length of the piece is simply `end - start`.

/// ### Topic 3: Changing case, trimming, replacing ###
/// These methods return a NEW string. They never change the one they are called on.

public class Main {
    public static void main(String[] args) {
        String s = "  Hello Java  ";

        System.out.println(s.toUpperCase());
        System.out.println(s.toLowerCase());
        System.out.println("[" + s.trim() + "]");
        System.out.println(s.replace("Java", "World"));

        System.out.println(s);                 // unchanged!
    }
}

/// That last line shows an important property: a String is IMMUTABLE. Once created, its characters can never be
/// changed. Methods like `toUpperCase` build a fresh copy and hand it to you. If you want to keep the new
/// version, store it:
///
///     s = s.toUpperCase();
///
/// MISTAKE: Writing `s.toUpperCase();` on its own line and expecting `s` to change. The result is thrown away.
///
/// WHY: Immutability makes strings safe to share between different parts of a program, and lets Java reuse
/// identical strings to save memory.

/// ### Topic 4: Comparing strings: equals, not == ###
/// This is the number one trap with strings, and it connects to how objects work in memory.
///
/// A String variable does not hold the text itself. It holds a REFERENCE, like an address on an envelope, pointing
/// to where the text lives. The `==` operator compares the addresses. Two different envelopes can contain
/// identical letters, so `==` may say "different" even when the text is the same. The `equals` method compares the
/// actual text.

public class Main {
    public static void main(String[] args) {
        String a = "java";
        String b = new String("java");     // forces a separate copy
        String c = "java";

        System.out.println(a == b);        // false: two different objects
        System.out.println(a == c);        // true: Java reuses identical literals
        System.out.println(a.equals(b));   // true: same text
    }
}

/// The middle line is true only because Java shares identical literals, which makes `==` look like it works some of
/// the time and fail at others. That is the worst kind of bug. Text typed by a user is never a shared literal, so
/// `==` fails there.
///
/// REMEMBER: Always compare strings with `equals`. To ignore capital letters use `equalsIgnoreCase`. And for
/// ordering (alphabetical), `compareTo` returns a negative number, zero or a positive number.

public class Main {
    public static void main(String[] args) {
        String password = "Secret";
        String typed = "secret";

        System.out.println(password.equals(typed));
        System.out.println(password.equalsIgnoreCase(typed));
        System.out.println("apple".compareTo("banana"));     // negative: apple comes first
        System.out.println("apple".compareTo("apple"));      // zero: identical
    }
}

/// ### Topic 5: Reading and splitting text ###
/// `split` cuts a string into pieces around a separator and gives back an array. That is handy for processing a
/// line such as "Rafi,20,CSE".

public class Main {
    public static void main(String[] args) {
        String line = "Rafi,20,CSE";
        String[] parts = line.split(",");

        System.out.println(parts.length);
        for (String p : parts) {
            System.out.println(p);
        }

        int age = Integer.parseInt(parts[1]);       // text "20" -> number 20
        System.out.println(age + 1);
    }
}

/// `Integer.parseInt` converts digits in a String into an actual `int`, which is the other half of reading
/// numbers safely. If the text is not a number it throws a `NumberFormatException`. The reverse trip,
/// `String.valueOf(20)`, turns a number back into text.
///
/// You can also walk through a string character by character with a loop, using `charAt`:

public class Main {
    public static void main(String[] args) {
        String word = "Programming";
        int vowels = 0;

        for (int i = 0; i < word.length(); i++) {
            char c = Character.toLowerCase(word.charAt(i));
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                vowels++;
            }
        }
        System.out.println("Vowels: " + vowels);
    }
}

/// ### Topic 6: StringBuilder, for building text ###
/// Because a String can never change, joining in a loop creates a brand-new String every round, and the old ones
/// are thrown away. With a few items that is fine; with thousands it is wasteful. `StringBuilder` is a string you
/// CAN change, designed for building text piece by piece.

public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= 5; i++) {
            sb.append(i).append(" ");
        }
        sb.insert(0, "Numbers: ");

        System.out.println(sb);
        System.out.println(sb.length());
        System.out.println(new StringBuilder("stressed").reverse());
    }
}

/// `append` adds to the end and returns the builder itself, which is why calls can be chained with dots. When you
/// are done, `toString()` (or printing it) gives you a normal String.

/// ### Topic 7: Neat output with printf ###
/// `printf` prints text with placeholders that are filled in from the values you pass after it. The placeholders
/// start with `%`:
///
///     %d   a whole number         %s   a String
///     %f   a decimal number       %.2f   a decimal with two digits after the point
///     %n   a new line             %5d  a number padded to width 5

public class Main {
    public static void main(String[] args) {
        String name = "Rafi";
        int age = 20;
        double gpa = 3.756;

        System.out.printf("Name: %s, Age: %d%n", name, age);
        System.out.printf("GPA: %.2f%n", gpa);
        System.out.printf("%-8s|%5d|%n", "Ann", 7);
    }
}

/// TIP: `%.2f` rounds to two decimals and is the standard way to print money and averages. A `-` before the width
/// (`%-8s`) left-aligns the text.

/// ============================================================================
/// YOUR TURN
///   1. Read a sentence and print its length, first letter and last letter.
///   2. Check whether a word is a palindrome (reads the same backward), using StringBuilder.reverse().
///   3. Count how many times a chosen letter appears in a sentence.
///   4. Read a full name and print the initials, for example "Ada Lovelace" becomes "A.L.".
///   5. Ask for a password twice and print whether the two match, ignoring case.
///   6. Split "10,20,30,40" into numbers and print their sum.
///   7. Print a small receipt table with printf so the prices line up on the decimal point.
/// ============================================================================
