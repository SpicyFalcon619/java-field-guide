/*
 * Java Basics: Variables and data types
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a variable is, and how to declare one, give it a value and change it
///   - The main data types: int, double, char, boolean and String
///   - Naming rules and conventions, and what a constant is
///   - Type casting, and the traps of integer size and decimal precision
/// ============================================================================


/// ### Topic 1: What is a variable? ###
/// A program needs to remember things: a name, a score, a price. A VARIABLE is a named place in the computer's
/// memory where a value is kept. You give it a name, and you can read the value back or change it later.
///
/// ANALOGY: A variable is a labelled box. The label is the variable's name, and the thing inside is its value.
/// Write "age" on a box, put 20 in it, and later you can open the box labelled "age" and find 20. You can swap
/// what is inside whenever you like.
///
/// In Java every box also has a TYPE, which says what kind of thing may go in it. A box meant for whole numbers
/// will not accept a sentence. This is called being STATICALLY TYPED, and it is one of the ways the compiler
/// catches your mistakes before the program runs.
///
/// Creating a variable has two parts, and usually we do both at once:
///
///     int age;        declare: make a box that holds a whole number, called age
///     age = 20;       assign: put the value 20 into the box
///     int age = 20;   both together (the usual way)

public class Main {
    public static void main(String[] args) {
        int age = 20;
        System.out.println(age);

        age = 21;                 // the box now holds a different value
        System.out.println(age);

        age = age + 1;            // read it, add 1, store the result back
        System.out.println(age);
    }
}

/// Look at `age = age + 1;`. In maths that would be nonsense. In Java the single `=` is not "equals", it is
/// ASSIGNMENT: work out the right side first, then store the result in the variable on the left. Read it as
/// "take the current age, add one, and put that back into age".
///
/// JARGON: To DECLARE a variable is to create it by giving it a type and a name. To INITIALISE it is to give it
/// its first value. To ASSIGN is to store a value in it, at any time.

/// ### Topic 2: The basic data types ###
/// Java has eight built-in "primitive" types. As a beginner you need five of them. The first four hold single
/// values; `String` is a special case that holds text.
///
///     int       whole numbers          20, -7, 0, 1000000
///     double    decimal numbers        10.5, 3.14, -0.001
///     char      one single character   'A', 'z', '7'   (single quotes)
///     boolean   true or false          true, false
///     String    text                   "Noman", "Hello"   (double quotes)

public class Main {
    public static void main(String[] args) {
        int age = 20;                  // Integer variable
        double price = 10.5;           // Decimal number
        char grade = 'A';              // Character
        boolean b = true;              // Boolean (true/false)
        String name = "Noman";         // String of text

        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("T/F: " + b);
    }
}

/// The `+` between a string and a variable joins them into one longer string. This is called CONCATENATION. You
/// will use it constantly to build messages.
///
/// WHY: Why so many number types? Because memory is limited and different jobs need different amounts. Java
/// stores an `int` in 4 bytes and a `double` in 8, and they behave differently when you divide them (more on that
/// in the Operators lesson). The other primitive types you may meet are `byte`, `short`, `long` and `float`.
/// `long` is a bigger `int` for very large whole numbers, and is written with an `L`: `long big = 3000000000L;`.
///
/// MISTAKE: Single quotes and double quotes are NOT interchangeable. `'A'` is one char. `"A"` is a String that
/// happens to contain one letter. `'Hello'` is an error, because a char holds exactly one character.
///
/// JARGON: `String` begins with a capital letter because it is not a primitive type like `int`. It is a CLASS,
/// built from characters. We will understand the difference properly when we reach objects.

/// ### Topic 3: Printing and mixing variables ###
/// Anything you put next to a String with `+` is converted to text and joined on. That lets you build sentences
/// from variables.

public class Main {
    public static void main(String[] args) {
        String name = "Rafi";
        int age = 20;
        double height = 5.9;
        char section = 'B';
        boolean enrolled = true;

        System.out.println("Name: " + name);
        System.out.println("Age: " + age + " years");
        System.out.println(name + " is " + height + " feet tall.");
        System.out.println("Section " + section + ", enrolled: " + enrolled);
    }
}

/// ### Topic 4: Naming variables ###
/// You choose the names, but Java has rules, and the community has habits.
///
/// --- The rules (break these and it will not compile) -----
///
///   - A name can contain letters, digits, `_` and `$`, but it cannot START with a digit.
///   - No spaces and no symbols such as `-` or `#`.
///   - It cannot be a reserved word, such as `int`, `class`, `public` or `new`.
///   - Names are case-sensitive: `score`, `Score` and `SCORE` are three different variables.
///
/// --- The conventions (not enforced, but everyone follows them) -----
///
///   - Variable names start with a lowercase letter and use camelCase: `studentAge`, `totalMarks`.
///   - Choose meaningful names. `totalMarks` is far better than `tm` or `x`.
///   - Class names start with a capital letter: `Student`, `BankAccount`.
///
/// WHY: Code is read many more times than it is written. `double a = b * c;` tells you nothing, while
/// `double total = price * quantity;` explains itself. Good names are the cheapest documentation there is.

/// ### Topic 5: Constants with final ###
/// Some values should never change: the value of pi, the number of days in a week, a tax rate. Put the keyword
/// `final` in front and Java will refuse to let anything change the variable after it is set. By habit,
/// constants are written in UPPER_SNAKE_CASE.

public class Main {
    public static void main(String[] args) {
        final double PI = 3.14159;
        int radius = 5;
        double area = PI * radius * radius;
        System.out.println("Area: " + area);
    }
}

/// Now the opposite: what happens if we try to change a constant?

public class Main {
    public static void main(String[] args) {
        final int DAYS_IN_WEEK = 7;
        DAYS_IN_WEEK = 8;
        System.out.println(DAYS_IN_WEEK);
    }
}
/// @error

/// The compiler stops the second program with "cannot assign a value to final variable DAYS_IN_WEEK". That is
/// exactly what you want: a constant that cannot be changed by accident.

/// ### Topic 6: Types must match ###
/// Because every variable has a type, Java checks that what you put in fits. Putting a decimal into an `int`
/// box would lose the part after the point, so the compiler refuses.

public class Main {
    public static void main(String[] args) {
        int count = 3.7;
        System.out.println(count);
    }
}
/// @error

/// The message is "incompatible types: possible lossy conversion from double to int". "Lossy" means information
/// would be lost, the `.7` in this case. Java will not do that silently. The other direction is fine: an `int`
/// fits happily inside a `double`.
///
/// The same applies to text and numbers. `int x = "5";` is an error too, because "5" is a String, not a number.
/// And a variable must be declared before you use it, otherwise the compiler says "cannot find symbol".

/// ### Topic 7: Type casting ###
/// Sometimes you do want to squeeze a decimal into an int, and you accept losing the fraction. You tell Java
/// that you know what you are doing with a CAST: the target type in brackets before the value.
///
///     int whole = (int) 3.7;     // 3
///
/// Casting from a smaller type to a larger one (int to double) happens automatically and is called WIDENING.
/// Going the other way (double to int) must be written by you and is called NARROWING.

public class Main {
    public static void main(String[] args) {
        double d = 9.78;
        int whole = (int) d;          // cut off the decimals, NOT round
        System.out.println(whole);

        int n = 7;
        double wide = n;              // widening: happens by itself
        System.out.println(wide);

        char letter = 'A';
        int code = letter;            // every char has a number behind it
        System.out.println(code);
        System.out.println((char) (code + 1));
    }
}

/// MISTAKE: Casting does not round. `(int) 9.78` is 9, not 10, because the decimals are simply thrown away. To
/// round, use `Math.round`, which we meet later.
///
/// The last lines show something surprising: a `char` is secretly a number. 'A' is stored as 65, 'B' as 66, and
/// so on, following a table called Unicode. That is why adding 1 to the code and casting back gives 'B'.

/// ### Topic 8: Limits and surprises ###
/// A box has a fixed size. An `int` holds numbers between about -2.1 billion and +2.1 billion. What happens when
/// you go past the top? It does not crash. It silently wraps around to the bottom, like a car's odometer rolling
/// over. This is called OVERFLOW.

public class Main {
    public static void main(String[] args) {
        int biggest = Integer.MAX_VALUE;
        System.out.println(biggest);
        System.out.println(biggest + 1);   // wraps around to a huge negative number

        long safe = biggest;               // a long has room to spare
        System.out.println(safe + 1);
    }
}

/// Decimals have a different quirk. A `double` stores an approximation, because computers count in binary and
/// cannot represent some fractions exactly, in the same way that 1/3 cannot be written exactly in decimal.

public class Main {
    public static void main(String[] args) {
        System.out.println(0.1 + 0.2);
        System.out.println(0.1 + 0.2 == 0.3);
    }
}

/// So never compare decimals with `==` and expect exactness. For money, real programs use a type called
/// `BigDecimal`, or they count whole cents as an `int`.
///
/// REMEMBER: Choose `int` for counting, `double` for measuring, `boolean` for yes/no questions, `char` for a
/// single symbol and `String` for text.

/// ============================================================================
/// YOUR TURN
///   1. Declare variables for your name, age, height and whether you are a student. Print one sentence using all
///      four.
///   2. Create a `final` variable for the number of seconds in a minute and use it to print the seconds in an
///      hour.
///   3. Predict the output of `System.out.println((int) 'a');` and then run it.
///   4. Declare `int x = 10;` and `double y = x / 4;`. Print y. Is it 2.5? We will explain why in the Operators
///      lesson, but make a guess now.
///   5. Which of these names is invalid, and why: `2ndPlace`, `total_marks`, `class`, `studentName`?
///   6. Make an `int` overflow on purpose, then fix it by changing the type to `long`.
/// ============================================================================
