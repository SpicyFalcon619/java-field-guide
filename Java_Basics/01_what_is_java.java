/*
 * Java Basics: What is Java, and how does a program run?
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a program is, and why we write it in a language like Java
///   - What happens between typing code and seeing it run: the compiler and the JVM
///   - The difference between the JDK, the JRE and the JVM, and which one to install
///   - How to compile and run a program yourself, and how to read this guide
/// ============================================================================


/// ### Topic 1: What is a program? ###
/// A computer does exactly what it is told, and nothing else. A PROGRAM is a list of instructions written so
/// precisely that a machine with no common sense can follow it: "print this on the screen", "add these two
/// numbers", "if the answer is above 50, say Pass".
///
/// Think of a recipe. A recipe is a list of steps, in order, and a cook follows them one by one. A program is a
/// recipe and the computer is a very fast, very literal cook. It will never guess what you meant, so every step
/// has to be spelled out.
///
/// WHY: People do not write programs in the 0s and 1s a processor understands. We write in a PROGRAMMING LANGUAGE
/// that is close to human words, and a tool translates it. Java is one such language. Others you may have heard
/// of are Python, C and JavaScript. They all do the same job, with different rules and different strengths.
///
/// Java is used almost everywhere: Android apps, banking systems, online shops, games (Minecraft is written in
/// Java) and the software inside many large companies. It is a good first language because it is strict, and
/// being strict teaches you to be precise.

/// ### Topic 2: From your text to a running program ###
/// You write your instructions in a plain text file, called SOURCE CODE. A Java source file always ends in
/// `.java`. The computer cannot run that text directly, so two tools take over.
///
/// --- The journey of a Java program -----
///
///     Main.java            Main.class              your screen
///    (what you write)    (what the compiler        (the result)
///                         produces: bytecode)
///         |                     |                      ^
///         |   javac             |   java               |
///         +--- COMPILER ------->+--- JVM -------------+
///
/// 1. The COMPILER (a tool called `javac`) reads your `.java` file. First it checks it for mistakes, the way a
///    proofreader checks grammar. If everything is fine it translates the file into BYTECODE and saves it as a
///    `.class` file. If it finds mistakes it stops and tells you what is wrong. These are called compile errors,
///    and you will meet plenty. They are your friends: the compiler is catching the mistake before your program
///    ever runs.
/// 2. The JVM (the Java Virtual Machine, started with the command `java`) reads the `.class` file and carries out
///    the instructions on your real computer.
///
/// ANALOGY: Imagine you write a letter in English (your source code). A translator turns it into a neutral
/// language everybody's interpreter knows (bytecode). Every country has its own interpreter (the JVM for Windows,
/// for Mac, for Linux) who reads that neutral language aloud. You wrote the letter once, and it works everywhere.
/// That is Java's famous promise: "write once, run anywhere".

/// ### Topic 3: JDK, JRE and JVM: which one do I install? ###
/// You will see these three names, and they sound alike. Here is what each one is.
///
///     JVM   the Java Virtual Machine. The part that RUNS bytecode.
///     JRE   the Java Runtime Environment. The JVM plus the library it needs. Enough to RUN Java programs.
///     JDK   the Java Development Kit. The JRE plus the compiler and other tools. What you need to WRITE Java.
///
/// TIP: Install the JDK. It contains the other two. This guide was tested with a recent long-term-support
/// version of the JDK, but anything from Java 17 onwards works for everything you will see here.
///
/// You also want somewhere comfortable to type. A plain text editor works, but most people use an IDE
/// (Integrated Development Environment), a program that gives you an editor, a Run button and help with
/// mistakes in one place. IntelliJ IDEA and Visual Studio Code are the two most common choices.

/// ### Topic 4: Compile and run your first program ###
/// Here is the smallest useful Java program. Do not worry about what each word means yet; the next lesson takes
/// it apart word by word. For now, just notice it prints a greeting.

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}

/// To run it on your own computer, save it in a file named exactly `Main.java`. Then, in a terminal opened in the
/// same folder, type two commands:
///
///     javac Main.java      (compile: creates Main.class)
///     java Main            (run: the JVM prints Hello, World!)
///
/// If you use an IDE you press its Run button instead, and it does both steps for you.
///
/// MISTAKE: The file name must match the class name exactly, including capital letters. Save it as `main.java`
/// or `Hello.java` and the compiler complains. Java is CASE-SENSITIVE: `Main` and `main` are two different
/// words.
///
/// REMEMBER: You do not need to install anything to follow this guide. Every program on these pages was really
/// compiled and run, and the Run button under each one shows exactly what it printed.

/// ### Topic 5: How to read this guide ###
/// Every lesson is built the same way, so you always know where you are.
///
///   - Explanations are the paragraphs you are reading now. Code is always in a dark box beneath them.
///   - Every code block is a COMPLETE program. You can copy it into a file called `Main.java` and run it exactly
///     as it is.
///   - The Run button under a block shows what it printed. When a program asks you to type something, a
///     "Typed input" menu lets you pick what was typed.
///   - Words with a dotted underline, like the ones you may have noticed above, are explained when you hover over
///     them or tap them. The Glossary in the menu lists every one of them in one place.
///   - Coloured notes appear throughout: WHY explains the reason behind something, MISTAKE shows a trap
///     beginners fall into, TIP is a shortcut, and ANALOGY compares an idea with everyday life.
///   - Each lesson ends with a YOUR TURN card. Typing the exercises yourself, instead of only reading, is how
///     this actually sticks.
///
/// TIP: Do not try to memorise syntax. Nobody does. Understand what each piece is for, and you will remember
/// the spelling after you have typed it a few times.

/// ============================================================================
/// YOUR TURN  (do these on your own computer)
///   1. Install a JDK, then open a terminal and run `java -version` and `javac -version`. Both should print a
///      version number.
///   2. Save the Hello program above as `Main.java`, compile it with `javac Main.java` and run it with `java Main`.
///   3. Look in the folder afterwards. A new file called `Main.class` has appeared. What produced it?
///   4. Change "Hello, World!" to your own name and run it again. Do you have to compile again first? Why?
/// ============================================================================
