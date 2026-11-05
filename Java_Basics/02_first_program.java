/*
 * Java Basics: Your first program, word by word
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What every word of the Hello World program means, and why it is there
///   - The difference between print and println, and how to print special characters
///   - How to leave notes for humans with comments
///   - What a compile error looks like, and how to read one
/// ============================================================================


/// ### Topic 1: Anatomy of a Java program ###
/// You saw this program in the last lesson. It looks like a lot of ceremony for one line of output, but every
/// word has a job. Here it is again, so we can take it apart.

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}

/// Read it from the outside in.
///
///   - `public class Main { ... }` declares a CLASS called Main. In Java, all code lives inside a class. For
///     now, think of a class as a named box that holds your program. Everything between its `{` and `}` belongs
///     to it.
///   - `public static void main(String[] args) { ... }` is the METHOD called main. A method is a named group of
///     instructions. The method called `main` is special: it is the front door. When you run a program, the JVM
///     goes looking for `main` and starts there.
///   - `System.out.println("Hello, World!");` is a single instruction, called a STATEMENT. It asks Java to print
///     the text between the quotes, then move to the next line.
///
/// ANALOGY: A class is a recipe book, a method is one recipe inside it, and a statement is one step of that
/// recipe. The cook (the JVM) always starts at the recipe named `main`.
///
/// --- What do the extra words mean? -----
///
/// You do not need to master these yet. A short meaning for each, so none of them feels like magic:
///
///     public   anyone is allowed to use this. The JVM must be able to reach main, so it has to be public.
///     static   main belongs to the class itself, so the JVM can call it without first creating an object.
///     void     this method gives nothing back when it finishes. It just does its job.
///     String[] args   a list of words typed after the program name on the command line. We ignore it for now.
///
/// JARGON: A CLASS is a blueprint that groups related code and data. A METHOD is a named block of instructions
/// you can run. A STATEMENT is one complete instruction, and it ends with a semicolon. A BLOCK is anything inside
/// a pair of curly braces.
///
/// REMEMBER: Every statement ends with a semicolon `;`. Braces `{ }` and the semicolon are how Java knows
/// where things start and stop. Spaces and blank lines are only for people. Java ignores them.

/// ### Topic 2: Printing text ###
/// The instruction you will use the most is `System.out.println(...)`. Read it as "System's output, print a
/// line". The text goes inside double quotes. A piece of text in quotes is called a STRING.

public class Main {
    public static void main(String[] args) {
        System.out.println("My name is Rafi.");
        System.out.println("I am learning Java.");
        System.out.println("It is fun!");
    }
}

/// Each `println` prints its text and then moves to a new line, so the three sentences come out on three lines.
/// The `ln` at the end of `println` stands for "line".
///
/// There is a sibling called `print` that does NOT move to a new line. The next thing printed carries on right
/// after it.

public class Main {
    public static void main(String[] args) {
        System.out.print("One ");
        System.out.print("Two ");
        System.out.println("Three");
        System.out.println("Four");
    }
}

/// The first three pieces ended up on one line because `print` never starts a new line. Only the `println` on
/// the "Three" line did. That is why "Four" appears on a line of its own.
///
/// TIP: Use `println` when each message should be on its own line, and `print` when you want to build a line from
/// several pieces. You can also print numbers: `System.out.println(2 + 3);` prints 5, without quotes, because
/// there is no text involved.

public class Main {
    public static void main(String[] args) {
        System.out.println(2 + 3);
        System.out.println("2 + 3");
    }
}

/// The first line printed the answer to a sum. The second printed the characters 2, +, 3 exactly as typed,
/// because anything inside quotes is text and Java does not calculate it.

/// ### Topic 3: Special characters inside text ###
/// What if you want a quotation mark, or a new line, or a tab, inside your text? Typing them directly would
/// confuse Java. A double quote would look like the end of the text. So we use an ESCAPE SEQUENCE: a backslash
/// followed by a letter, which Java replaces with a special character.
///
///     \n   a new line
///     \t   a tab (a wide gap)
///     \"   a double quote character
///     \\   one backslash character

public class Main {
    public static void main(String[] args) {
        System.out.println("Name:\tRafi");
        System.out.println("She said, \"Hello!\"");
        System.out.println("Line one\nLine two");
        System.out.println("C:\\Users\\Rafi");
    }
}

/// WHY: The backslash means "the next character is special, treat it with care". To print a backslash itself you
/// have to escape it too, which is why the folder path above needs two of them.

/// ### Topic 4: Comments: notes for humans ###
/// A COMMENT is text that Java completely ignores. You write comments to explain your code to other people, and
/// to yourself next month when you have forgotten why you wrote it.
///
///     // a single-line comment, runs to the end of the line
///     /* a multi-line comment,
///        which can span several lines */

public class Main {
    public static void main(String[] args) {
        // This line is a comment. Java skips it.
        System.out.println("Comments are ignored."); // this part too

        /* Everything between these markers is ignored,
           even over several lines. */
        // System.out.println("This line is switched off.");
        System.out.println("Done.");
    }
}

/// Notice that one `println` is missing from the output. It sits behind `//`, so Java never saw it. Programmers
/// call this COMMENTING OUT a line, and use it all the time to switch code off while testing.
///
/// TIP: Good comments explain WHY something is done, not what the code plainly says. `// add tax` beside a line
/// that multiplies by 1.15 helps. `// print the name` beside a println does not.

/// ### Topic 5: Compile errors: when the compiler says no ###
/// Java is strict. If your code breaks the rules of the language, the compiler refuses to build it, and the
/// program does not run at all. That message is a COMPILE ERROR, sometimes called a syntax error. Here is the most
/// common one: a forgotten semicolon.

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello")
    }
}
/// @error

/// Run it and you get a message like `Main.java:3: error: ';' expected`. Learn to read it in three pieces:
///
///   - WHERE: `Main.java:3` is the file and the line number the compiler was looking at.
///   - WHAT: `';' expected` tells you what it needed to see.
///   - A pointer: the compiler also prints the line and a `^` marking the spot.
///
/// TIP: Read the FIRST error first and fix only that one. One small mistake can make the compiler report several
/// errors after it, and they often all vanish once you fix the first. If the line it points at looks fine, check
/// the line just above it.
///
/// Here are two more mistakes every beginner makes. Look at each and guess what is wrong before you run it.

public class Main {
    public static void main(String[] args) {
        system.out.println("Hello");
    }
}
/// @error

/// MISTAKE: Java is case-sensitive. `System` starts with a capital S because it is the name of a class that Java
/// provides. Writing `system` makes the compiler complain that "package system does not exist", because it has never heard of anything called `system`. The same goes for
/// `Println`, `PrintLn` and `main` written as `Main`.

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello);
    }
}
/// @error

/// MISTAKE: A string must be closed with a second quote. Forgetting it makes everything after the opening
/// quote look like text, and the compiler complains about an "unclosed string literal". Good editors colour
/// text differently inside quotes, so a missing quote is easy to spot once you look for it.
///
/// REMEMBER: Compile errors are not failures. They are the compiler doing its job. Even experienced programmers
/// see them many times a day.

/// ============================================================================
/// YOUR TURN
///   1. Write a program that prints your name, your city and your favourite food on three separate lines.
///   2. Print the same three things on ONE line, using `print` and a space between them.
///   3. Print this exactly, including the quotes:  He said, "Java is fun!"
///   4. Print a small table with tabs, such as a name, a tab, and a score, for three people.
///   5. Take any working program above and delete one semicolon. Read the error. Put it back.
///   6. Comment out one line of a program and predict what the output will be before you run it.
/// ============================================================================
