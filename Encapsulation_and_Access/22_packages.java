/*
 * Encapsulation and Access: Packages and imports
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a package is, and why Java groups classes into packages
///   - How to declare a package, and how the folders on disk must match
///   - Three ways to use a class from another package: import, wildcard import and the full name
///   - What happens when two packages have a class with the same name
///   - The special package java.lang, and static imports
/// ============================================================================


/// ### Topic 1: What is a package? ###
/// A PACKAGE is a folder of related classes. It is a way to group a big program into sections, in the same way that you
/// keep your files in folders instead of dumping thousands of them on the desktop.
///
/// Packages do two jobs:
///    - ORGANISATION: related classes live together (`java.util` for utilities, `java.io` for files).
///    - NO NAME CLASHES: two classes may share a name, as long as they live in different packages, just like two files
///      called `notes.txt` can exist in two different folders.
///
/// The Java library you already use is organised this way. You met it with `Scanner`:
///
///     java.lang    the essentials: String, System, Math, Integer (imported for you automatically)
///     java.util    tools: Scanner, ArrayList, HashMap, Random, Arrays
///     java.io      files and streams: File, FileWriter, BufferedReader
///
/// ANALOGY: An address. "Rafi" alone is ambiguous: there are many. "Rafi, Block C, Dhaka" is unique. The package is
/// the "Block C, Dhaka" part of a class's full name.

/// ### Topic 2: The full name of a class ###
/// Every class has a FULLY QUALIFIED NAME: the package, a dot, then the class. Scanner's is `java.util.Scanner`.
/// You can always use a class by its full name, with no `import` at all:

public class Main {
    public static void main(String[] args) {
        java.util.ArrayList<String> names = new java.util.ArrayList<>();
        names.add("Rafi");
        names.add("Mim");
        System.out.println(names);
    }
}

/// It works, but it is long and noisy. This is why imports exist.

/// ### Topic 3: Importing ###
/// An `import` line tells Java "when I write the short name, I mean this one". After it, you can drop the package part.
/// Imports go at the very top of the file, below any `package` line and above the class.
///
///     import java.util.ArrayList;     one specific class
///     import java.util.*;             every class in java.util (the star is a wildcard)

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Rafi");
        names.add("Mim");
        System.out.println(names);
    }
}

/// NOTE: An import does not copy any code into your file or slow your program. It is only a name shortcut for the
/// compiler. Importing something you never use is harmless (your IDE will mark it grey).
///
/// --- The wildcard -----
/// `import java.util.*;` makes every class of `java.util` available by short name. It does NOT include sub-packages:
/// `java.util.*` does not give you `java.util.concurrent` classes.

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(30);
        numbers.add(10);
        numbers.add(20);

        Collections.sort(numbers);
        System.out.println(numbers);
        System.out.println(Arrays.toString(new int[] {3, 1, 2}));
    }
}

/// TIP: Wildcards are convenient and fine in small programs. Many teams prefer listing each class, because then the top
/// of the file shows exactly what the code depends on.
///
/// The compiler complains when you forget:

public class Main {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Rafi");
    }
}
/// @error

/// "cannot find symbol: class ArrayList". Add `import java.util.ArrayList;` (or use the full name) and it works. In
/// an IDE, press Alt+Enter on the red word and accept the import suggestion.

/// ### Topic 4: The package java.lang needs no import ###
/// One package is imported automatically into every file: `java.lang`. It holds the classes nobody could do without:
/// `String`, `System`, `Math`, `Integer`, `Double`, `Object`, `Thread`, the common exceptions. That is why
/// `System.out.println` and `Math.sqrt` work with no import line at all.

public class Main {
    public static void main(String[] args) {
        System.out.println(Math.sqrt(49));
        System.out.println(Integer.parseInt("123") + 1);
        System.out.println("java.lang is already imported".toUpperCase());
    }
}

/// ### Topic 5: Two classes with the same name ###
/// Here is the problem packages solve. Java has TWO classes called `Date`: one in `java.util`, one in `java.sql`. If you
/// import one, you cannot import the other by its short name, because Java would not know which you mean. The fix is to
/// import one and write the other's full name.

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Date today = new Date();                          // java.util.Date, from the import
        java.sql.Date sqlDate = new java.sql.Date(0L);    // the other one, by its full name
        System.out.println(today.getClass().getName());
        System.out.println(sqlDate.getClass().getName());
    }
}

/// Importing both with the short name `Date` would be a compile error, so the full name is how you tell them apart.

/// ### Topic 6: Making your own package ###
/// Your own classes can be placed in a package with a line at the very top of the file:
///
///     package school;
///
///     public class Student { ... }
///
/// Two rules go with it:
///    - The file must live in a folder with the same name. `package school;` means the file is in a folder called
///      `school`. For `package com.school.models;`, the folders are `com/school/models/`.
///    - Other packages use the class by importing it.
///
/// A small project might look like this:
///
///     src/
///       school/
///         Student.java          package school;
///         Teacher.java          package school;
///       app/
///         Main.java             package app;   import school.Student;
///
/// By convention, package names are all lowercase, and companies reverse their website: `com.google.maps`,
/// `org.apache.commons`. That keeps names unique across the world.
///
/// NOTE: This guide's lessons run as single files in the "default package": a file with no `package` line. That is why
/// every example is `public class Main` and runs on its own. Real projects always use packages. Your IDE creates
/// the folders for you when you choose "New Package".
///
/// REMEMBER: A class with no `package` line is in the default package, and classes in the default package can only
/// see each other. They cannot be imported by classes inside a named package.

/// ### Topic 7: Packages and access ###
/// Packages are also why the access levels in the next lesson exist. A member with no modifier (package-private) is
/// visible to everything in the SAME package and invisible outside it. And `protected` extends that to subclasses in
/// other packages. The package is the "neighbourhood" those rules refer to.

/// ### Topic 8: Static imports ###
/// One more kind of import: `import static` lets you use a class's STATIC members without writing the class name each
/// time. It is handy for `Math` in formulas.

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) {
        double r = 3;
        System.out.println(PI * pow(r, 2));       // no "Math." in front
        System.out.println(max(4, 9));
        System.out.println(sqrt(144));
    }
}

/// TIP: Use static imports sparingly. `Math.sqrt(x)` tells a reader where `sqrt` comes from; a bare `sqrt(x)` does not.
///
/// REMEMBER: Packages are folders of classes. `import` shortens names. `java.lang` is automatic. A clash between two
/// same-named classes is solved with the full name.

/// ============================================================================
/// YOUR TURN
///   1. Use `java.util.Random` by its full name, with no import, to print a random number between 0 and 9.
///   2. Rewrite that with an `import`. Then rewrite it with `import java.util.*;`.
///   3. Remove the import from a program that uses `Scanner`, read the error, and put it back.
///   4. Which of these need an import: String, ArrayList, Math, Scanner, Integer, HashMap?
///   5. On your computer, make a folder `shapes` with `Circle.java` (starting with `package shapes;`) and a `Main` in
///      another folder that imports it. Compile from the top folder.
///   6. Explain how two different classes can both be called `Date`.
/// ============================================================================
