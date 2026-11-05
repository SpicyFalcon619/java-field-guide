/*
 * Working with Objects: Constructor chaining
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What constructor chaining is, and the repetition it removes
///   - How `this(...)` works, and the rule that it comes first
///   - The order things run in when constructors call each other
///   - Chaining with `super(...)` up a family of classes
/// ============================================================================


/// ### Topic 1: What is constructor chaining? ###
/// A class often has several constructors: one with no information, one with a name, one with everything. Writing
/// the same assignments in each of them is repetitive, and every repetition is a place for a bug.
///
/// CONSTRUCTOR CHAINING means calling one constructor from another constructor using `this(...)`. The short
/// constructors pass the work on to the fullest one, so the real setup lives in exactly one place.
///
/// ANALOGY: A receptionist who cannot answer a question does not invent an answer; they hand you to the person who
/// can. The simple constructors hand over to the one that does the real work, filling in sensible defaults on the way.

/// ### Topic 2: Example ###

class Student10 {
    String name;
    int age;

    // Default constructor
    Student10() {
        this("Unknown", 0); // Chaining to parameterized constructor
    }

    // Constructor with one parameter
    Student10(String name) {
        this(name, 0); // Chaining to two-parameter constructor
    }

    // Constructor with two parameters
    Student10(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        Student10 s1 = new Student10();
        Student10 s2 = new Student10("Noman");
        Student10 s3 = new Student10("Tortilla", 35);

        s1.display(); // Name: Unknown, Age: 0
        s2.display(); // Name: Noman, Age: 0
        s3.display(); // Name: Tortilla, Age: 35
    }
}

/// Only the two-parameter constructor contains `this.name = name; this.age = age;`. The others just supply defaults
/// and forward. If you later add a third field, there is one constructor to change, and not three.

/// ### Topic 3: Following the order ###
/// Let us watch the chain run. Each constructor prints a line when it starts and when it ends, so you can see that
/// the call goes down the chain first and the work finishes on the way back.

class Trace {
    Trace() {
        this(1);
        System.out.println("no-argument constructor finished");
    }

    Trace(int a) {
        this(a, 2);
        System.out.println("one-argument constructor finished");
    }

    Trace(int a, int b) {
        System.out.println("two-argument constructor: a = " + a + ", b = " + b);
    }
}

public class Main {
    public static void main(String[] args) {
        new Trace();
    }
}

/// `new Trace()` entered the first constructor, which immediately jumped to the second, which jumped to the third.
/// The third one finished first, then the second, then the first. Like Russian dolls, the innermost one is
/// completed first.

/// ### Topic 4: The rules ###
///    - `this(...)` goes at the START of the constructor. In the Java versions used by almost every course and
///      textbook, it must be the very first statement. (Java 25 relaxes this slightly, allowing lines that do not touch
///      the object before it, but writing it first is always correct and is what you should do.)
///    - It can only be used inside a constructor, never in an ordinary method.
///    - Constructors cannot call each other in a circle.
///    - A constructor cannot use both `this(...)` and `super(...)`, because each wants the first place.
///
/// A constructor written the wrong way round looks like this, and older compilers reject it with "call to this must
/// be first statement in constructor":
///
///     Student() {
///         System.out.println("setting up");   // a statement before this(...)
///         this("Unknown");
///     }
///
/// The fix is to put `this("Unknown");` on the first line. The second rule has its own error, and it is easy to cause
/// by accident:

class Loop {
    Loop() {
        this(5);
    }

    Loop(int n) {
        this();
    }
}

public class Main {
    public static void main(String[] args) {
        new Loop();
    }
}
/// @error

/// "recursive constructor invocation": the first constructor calls the second, which calls the first, forever. Java
/// detects it at compile time and refuses.

/// ### Topic 5: Chaining up the family with super ###
/// There is a second kind of chain. `this(...)` calls a constructor in the SAME class. `super(...)`, from the
/// Inheritance lesson, calls a constructor in the PARENT. They combine: a child constructor may chain with
/// `this(...)` to another child constructor, and the last one of those finally calls `super(...)`.

class Person {
    String name;

    Person(String name) {
        this.name = name;
        System.out.println("Person(" + name + ")");
    }
}

class Employee extends Person {
    int salary;

    Employee(String name) {
        this(name, 30000);                  // same class, fill in a default
        System.out.println("Employee(name) finished");
    }

    Employee(String name, int salary) {
        super(name);                        // up to the parent
        this.salary = salary;
        System.out.println("Employee(name, salary): " + salary);
    }
}

public class Main {
    public static void main(String[] args) {
        Employee e = new Employee("Rafi");
        System.out.println(e.name + " earns " + e.salary);
    }
}

/// Follow it: `new Employee("Rafi")` hands over to the two-argument constructor, which calls `super(name)` first (the
/// parent is always built first), then sets the salary, then returns to finish the one-argument constructor.
///
/// REMEMBER: `this(...)` hands the work to another constructor of the same class, `super(...)` to the parent's. Either
/// must be the first line, and the one that runs last in the chain is the one that does the real setting up.

/// ============================================================================
/// YOUR TURN
///   1. Write a `Book` with `title`, `author` and `pages`. Give it three constructors that chain to the fullest one:
///      no arguments, title only, and everything.
///   2. Add a print statement to each constructor and predict the exact order of the output before running.
///   3. Put `this(...)` on the second line of a constructor, after a println. Does your Java version accept it or
///      give an error? Either way, move it back to the first line, which is correct in every version.
///   4. Make a three-level family (`Person`, `Employee`, `Manager`) where each constructor prints its name. Create a
///      Manager and describe the order.
///   5. Why is it better to have the real work in one constructor than in all of them?
/// ============================================================================
