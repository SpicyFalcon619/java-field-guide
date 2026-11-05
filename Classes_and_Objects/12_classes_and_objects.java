/*
 * Classes and Objects: Classes and objects
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What object-oriented programming is, and why it exists
///   - The difference between a class (the blueprint) and an object (the real thing)
///   - How to write a class with fields and methods, and create objects from it
///   - How an object variable really works: references, null and the NullPointerException
/// ============================================================================


/// ### Topic 1: Why object-oriented programming? ###
/// So far you have written programs as a list of steps: read this, calculate that, print the result. That works
/// for small programs. But think about a school system. It has students, teachers, courses and fees. Each
/// student has a name, an age and a set of marks, and can do things: enrol, pay fees, print a report card. If you
/// kept all of that in separate variables and loose methods, you would drown.
///
/// OBJECT-ORIENTED PROGRAMMING (OOP) is a way of organising a program around THINGS instead of steps. You describe
/// what a thing KNOWS (its data) and what it can DO (its methods), together, in one place. Then you create as
/// many of that thing as you need.
///
/// ANALOGY: Think of a mobile phone factory. There is one design (the blueprint) that says every phone has a
/// colour, a battery level, and can make calls. The factory then builds thousands of real phones from that one
/// design. Each phone is separate: yours can be red at 40 percent battery while mine is blue at 90 percent. But
/// all of them have the same shape and abilities.
///
/// That factory idea is the heart of OOP, and it has two words.

/// ### Topic 2: Class and object ###
///   - A CLASS is the blueprint. It describes what every thing of that kind has and can do. It is just a
///     description, so it does not take up a phone's worth of memory by itself.
///   - An OBJECT is one real thing built from the blueprint. It is also called an INSTANCE of the class. You can
///     build as many as you like, and each has its own data.
///
/// ANALOGY: The class is a cookie cutter; the objects are the cookies. One cutter, many cookies, and you can
/// decorate each cookie differently.
///
/// In a class we write two kinds of members:
///
///     FIELDS     the variables that hold the data   (what it knows)   also called attributes
///     METHODS    the actions it can perform         (what it can do)   also called behaviours
///
/// JARGON: "Instance variable", "field", "attribute" and "member variable" all mean the same thing: a variable
/// that lives inside an object. Different books prefer different words, so you will meet all of them.

/// ### Topic 3: Your first class ###
/// Let us describe a student. Every student has a name and an age (fields), and can introduce themselves
/// (a method).

class Student {
    String name;
    int age;

    public void info() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();

        s1.name = "Noman";
        s1.age = 35;

        s1.info();
    }
}

/// There are two classes in this file. `Student` is the blueprint. `Main` is only the front door of the program,
/// because it contains `main`. Now read the code in `Main` line by line:
///
///   - `Student s1 = new Student();` builds one Student and gives it the name `s1`.
///   - `s1.name = "Noman";` fills in that student's name. The dot means "the name that belongs to s1".
///   - `s1.info();` asks s1 to run its `info` method.
///
/// Notice that `info()` uses the variables `name` and `age` without saying whose they are. Inside a method of the
/// class, they automatically mean "the name and age of whichever object this method was called on".
///
/// JARGON: `new Student()` is the moment of INSTANTIATION: Java sets aside memory for a new object, fills its
/// fields with starting values, and hands back a way to reach it. The brackets after `Student` are a call to a
/// constructor, which the next lesson explains.
///
/// NOTE: Only ONE class in a file may be `public`, and its name must match the file name. That is why the class
/// that holds `main` is called `Main` throughout this guide, saved as `Main.java`, while helper classes like
/// `Student` have no `public` in front.

/// ### Topic 4: Many objects, separate data ###
/// The real power appears when you create several objects. Each one carries its own copy of the fields, so
/// changing one does not touch the others.

class Student {
    String name;
    int age;

    public void info() {
        System.out.println(name + " (" + age + ")");
    }
}

public class Main {
    public static void main(String[] args) {
        Student a = new Student();
        a.name = "Rafi";
        a.age = 20;

        Student b = new Student();
        b.name = "Mim";
        b.age = 19;

        a.info();
        b.info();

        b.age = 25;            // only b changes
        a.info();
        b.info();
    }
}

/// TIP: Whenever you find yourself writing `name1`, `name2`, `name3`, you are really describing a class and
/// several objects. That is the sign to stop and create a class.

/// ### Topic 5: Fields get default values ###
/// What if you create a Student and never fill in its fields? Unlike a local variable, a field is never left
/// unset. Java gives it a DEFAULT VALUE: 0 for numbers, `false` for booleans, and `null` for anything that is an
/// object, including Strings.

class Student {
    String name;
    int age;
    boolean enrolled;
    double gpa;
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
        System.out.println(s.name);
        System.out.println(s.age);
        System.out.println(s.enrolled);
        System.out.println(s.gpa);
    }
}

/// `null` is a special value meaning "no object here yet". It is not the same as an empty string; it means
/// there is nothing at all. The next topic shows why that matters.

/// ### Topic 6: What is a variable like s1, really? ###
/// This is the idea that explains a surprising number of Java behaviours. When you write
/// `Student s1 = new Student();`, two things happen:
///
///   1. `new Student()` creates the object somewhere in memory.
///   2. The variable `s1` does NOT contain the student. It stores a REFERENCE: the address of where the student
///      lives.
///
/// ANALOGY: A house and its address. The variable is a piece of paper with the address written on it. Copying
/// the paper does not copy the house. Two papers can carry the same address, and then anyone who walks to that
/// address and repaints the door has changed the house for both of them.

class Student {
    String name;
}

public class Main {
    public static void main(String[] args) {
        Student first = new Student();
        first.name = "Rafi";

        Student second = first;        // copies the ADDRESS, not the student
        second.name = "Mim";

        System.out.println(first.name);
        System.out.println(second.name);
        System.out.println(first == second);   // same object?
    }
}

/// Both variables point at one and the same student, so renaming through `second` is visible through `first`.
/// There was only ever one object. To get two separate students you must write `new` twice.

/// ### Topic 7: null and the NullPointerException ###
/// If a reference variable holds `null`, it points at nothing. Asking nothing to do something fails, and Java
/// stops the program with a `NullPointerException`, usually shortened to NPE. It is the most common error in
/// Java programs of every size.

class Student {
    String name;

    public void info() {
        System.out.println("Name: " + name);
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = null;
        s.info();
    }
}
/// @error

/// The message says the variable `s` is null. The fix is to make sure there is a real object before you use it:
/// `Student s = new Student();`.
///
/// MISTAKE: Declaring an object variable is not the same as creating the object. `Student s;` only makes the
/// piece of paper. It is `new Student()` that builds the house. A local variable that you have not assigned at all
/// cannot even be used, and the compiler says "variable s might not have been initialized".
///
/// REMEMBER: Class = blueprint. Object = one real thing. `new` builds an object. A variable holds a reference to
/// it, not the object itself.

/// ============================================================================
/// YOUR TURN
///   1. Write a class `Book` with fields `title`, `author` and `pages`, and a method `info()` that prints them.
///      Create three books and print each.
///   2. Write a class `Car` with `brand`, `speed` and a method `accelerate()` that adds 10 to the speed. Create two
///      cars, accelerate only one, and print both speeds.
///   3. Without running it, predict the output: create a `Student a`, set `a.age = 10`, do `Student b = a;`, set
///      `b.age = 99`, and print `a.age`.
///   4. Create a Student variable set to null and call a method on it. Find the line number in the error.
///   5. Think of a real thing you use (a bank account, a song, a game character). List 3 fields and 2 methods it
///      would need.
/// ============================================================================
