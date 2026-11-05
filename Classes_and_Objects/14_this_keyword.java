/*
 * Classes and Objects: The this keyword
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What `this` means: the object that is running the code right now
///   - Why parameters often share a name with fields, and how `this` solves the clash
///   - Using `this()` to call another constructor
///   - Returning `this` to chain method calls
/// ============================================================================


/// ### Topic 1: What is `this`? ###
/// Inside a method or constructor, you sometimes need to say "the object I am working on right now". Java has a
/// keyword for exactly that: `this`.
///
/// ANALOGY: When you say "my" in a sentence, it means something different depending on who is speaking. When Rafi
/// says "my name", it is Rafi's. When Mim says it, it is Mim's. The word `this` works the same way: the same method
/// runs for every object, and `this` always points at the one that was asked to run it.
///
/// JARGON: `this` is a REFERENCE (an address, as you saw in the Classes and Objects lesson) to the current
/// object. You never declare it; it exists automatically inside every non-static method and constructor.
///
/// It is used in three situations:
///
///   - When a parameter has the same name as a field, to tell them apart.
///   - To call another constructor of the same class.
///   - To hand over, or return, the current object itself.

/// ### Topic 2: Telling a field from a parameter ###
/// A parameter named `name` and a field named `name` both exist at once, and inside the constructor the parameter
/// wins, because it is closer. Without help, `name = name;` would just copy the parameter onto itself and the
/// field would stay empty.

class Student4 {
    String name;
    int age;

    public Student4(String name, int age) {
        this.name = name; // 'this.name' refers to instance variable
        this.age = age;   // 'age' on the right is the parameter
    }

    public void info() {
        System.out.println("Name: " + this.name);
        System.out.println("Age: " + this.age);
    }
}

public class Main {
    public static void main(String[] args) {
        Student4 s1 = new Student4("Noman", 35);
        s1.info();
    }
}

/// Read `this.name = name;` as "the name belonging to this object becomes the name that was passed in". The part
/// on the left, with `this.`, is the field. The one on the right, plain, is the parameter.
///
/// WHY: Why not give the parameter a different name, like `n`? You can, and the Constructors lesson did exactly
/// that. But `name` is clearer for the person calling the constructor, and `this.name = name` is the standard
/// habit in Java. Every IDE can generate it for you.
///
/// Here is what happens if you forget `this`:

class Student {
    String name;

    public Student(String name) {
        name = name;          // the parameter is assigned to itself; the field is untouched
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student("Rafi");
        System.out.println(s.name);
    }
}

/// It prints `null`. There is no error and no warning from the compiler, which makes this one of the sneakiest
/// beginner bugs. If a field mysteriously stays at its default value, check for a missing `this.`.
///
/// TIP: Inside a method where there is no clash, `this.` is optional. `name` and `this.name` mean the same thing.
/// Many people still write it to make clear that they mean a field and not a local variable.

/// ### Topic 3: Calling another constructor with this() ###
/// When a class has several constructors, they often repeat the same lines. Instead of copying, one constructor
/// can hand the work to another with `this(...)`, using the brackets with arguments. This is called CONSTRUCTOR
/// CHAINING.

class Student5 {
    String name;
    int age;

    // Default constructor
    public Student5() {
        this("Nothing", 0);  // calls the parameterized constructor
    }

    // Parameterized constructor
    public Student5(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void info() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        Student5 s1 = new Student5();             // uses default
        Student5 s2 = new Student5("Noman", 35);  // uses parameterized

        s1.info();
        s2.info();
    }
}

/// `new Student5()` ran the no-argument constructor, which immediately passed the work to the other constructor
/// with the values "Nothing" and 0. All the real setup lives in one place.
///
/// MISTAKE: `this(...)` belongs on the very FIRST line of the constructor (Java 25 allows some lines before it, but first
/// is always correct), and it can only be used inside a
/// constructor. Put it on a later line, and most compilers refuse. Constructor chaining gets its own lesson later.

/// ### Topic 4: Returning this, to chain method calls ###
/// If a method ends with `return this;`, it hands the same object back to the caller. That lets you call another
/// method straight away on the result, one after another on a single line. This is METHOD CHAINING, and you have
/// already used it: `sb.append(1).append(2)` with StringBuilder worked exactly like this.

class Pizza {
    String size = "medium";
    boolean cheese = false;
    boolean olives = false;

    public Pizza size(String size) {
        this.size = size;
        return this;           // hand the same pizza back
    }

    public Pizza addCheese() {
        this.cheese = true;
        return this;
    }

    public Pizza addOlives() {
        this.olives = true;
        return this;
    }

    public void show() {
        System.out.println(size + " pizza, cheese: " + cheese + ", olives: " + olives);
    }
}

public class Main {
    public static void main(String[] args) {
        // One long chain
        new Pizza().size("large").addCheese().addOlives().show();

        // The same thing written step by step
        Pizza p = new Pizza();
        p.size("small");
        p.addCheese();
        p.show();
    }
}

/// Each call returns the pizza itself, so the next call has something to work on. The chained line and the
/// step-by-step version do exactly the same thing. Chaining is only about reading comfort.

/// ### Topic 5: this cannot be used in static code ###
/// `this` means "the object running this code". A static method belongs to the class and runs with no object at
/// all, so there is no `this` to refer to. The Static keyword lesson explains this properly. For now, remember the
/// rule and what the compiler says when you break it.

public class Main {
    int count = 5;

    public static void main(String[] args) {
        System.out.println(this.count);
    }
}
/// @error

/// REMEMBER: `this` is the object that is currently running the code. Use it to separate fields from parameters,
/// to chain constructors, and to return the object itself.

/// ============================================================================
/// YOUR TURN
///   1. Write a class `Book` with a constructor `Book(String title, double price)` that uses `this`. Print a book.
///   2. Remove the `this.` from one of the assignments on purpose and see what the field prints.
///   3. Give `Book` a second constructor that takes only the title and uses `this(...)` to set the price to 100.
///   4. Add a `Counter` class with an `add()` method that returns `this`, and chain
///      `new Counter().add().add().add().show()`.
///   5. In your own words: why can `main` not use `this`?
/// ============================================================================
