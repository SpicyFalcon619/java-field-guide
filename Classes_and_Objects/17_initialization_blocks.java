/*
 * Classes and Objects: Instance initialization block
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What an instance initialization block (IIB) is, and when it runs
///   - Why code runs in a particular order when an object is created
///   - How static blocks, IIBs and constructors work together, traced step by step
/// ============================================================================


/// ### Topic 1: What is an IIB? ###
/// You know the constructor runs when an object is created. Java has one more place for setup code: a pair of
/// curly braces written directly inside the class, with no name and no `static`. This is an INSTANCE
/// INITIALIZATION BLOCK, shortened to IIB.
///
/// An IIB runs every time an object is created, BEFORE the constructor.
///
/// WHY: It is handy when several constructors need the same starting steps. Instead of repeating the steps in
/// each constructor, put them once in the IIB, and Java runs them for you before any constructor. In everyday
/// code, though, most people prefer constructors and `this(...)` chaining, so think of the IIB as something you
/// must be able to read, and will rarely write.

/// ### Topic 2: Syntax and order ###
/// Watch the order of the messages.

class MyClass {
    {
        System.out.println("IIB executed");
    }

    MyClass() {
        System.out.println("Constructor executed");
    }
}

public class Main {
    public static void main(String[] args) {
        MyClass obj1 = new MyClass();
        System.out.println("-------");
        MyClass obj2 = new MyClass();
    }
}

/// For each `new`, the IIB printed first and the constructor second. It happened again for the second object,
/// because the IIB belongs to every object, not to the class as a whole.
///
/// If a class has several constructors, the IIB runs before whichever one you call.

class Light {
    Light() {
        System.out.println("Constructor: plain light");
    }

    Light(String colour) {
        System.out.println("Constructor: " + colour + " light");
    }

    {
        System.out.println("IIB: wiring the light");
    }
}

public class Main {
    public static void main(String[] args) {
        new Light();
        new Light("red");
    }
}

/// ### Topic 3: Putting it all together ###
/// A class can now have a static variable, an instance variable, a static block, an IIB, a constructor, a static
/// method and an instance method. It is worth reading one program that has all of them, and tracing what prints
/// and when. Do not rush; follow the output line by line.

class Demo2 {
    // Static variable
    static int staticCount = 0;
    // Instance variable
    int instanceCount = 0;

    // Static block
    static {
        System.out.println("Static block executed");
        staticCount = 10;
    }

    // IIB (Instance Initialization Block)
    {
        System.out.println("IIB executed");
        instanceCount = 5;
        staticCount += 1; // can access static members
    }

    // Constructor
    Demo2() {
        System.out.println("Constructor executed");
        instanceCount += 2;
    }

    // Static method
    static void staticMethod() {
        System.out.println("Static method called");
        System.out.println("Static Count = " + staticCount);
        // Can't use instanceCount here (no object)
    }

    // Non-static method
    void instanceMethod() {
        System.out.println("Instance method called");
        System.out.println("Instance Count = " + instanceCount);
        System.out.println("Static Count = " + staticCount);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("ExampleStaticIibObjectCreationCode method starts");

        // Call static method (no object needed)
        Demo2.staticMethod();

        System.out.println("Creating first object:");
        Demo2 d1 = new Demo2();
        d1.instanceMethod();

        System.out.println("Creating second object:");
        Demo2 d2 = new Demo2();
        d2.instanceMethod();
    }
}

/// --- Why this order? -----
///
///   - "Static block executed" appears the first time `Demo2` is used. The class is loaded once, so the static
///     block runs once, and sets `staticCount` to 10. It appears right after the "starts" message, because that
///     is the moment `Demo2.staticMethod()` first touches the class.
///   - "Static method called" comes next, and shows Static Count = 10. No object exists yet.
///   - For each `new Demo2()`: first the IIB runs (it sets `instanceCount` to 5 and adds 1 to the shared
///     `staticCount`), then the constructor runs (adding 2 to `instanceCount`, making 7).
///   - The first object ends with Instance Count = 7 and Static Count = 11. The second object also gets 7 for
///     its own `instanceCount`, because each object has its own copy. But `staticCount` keeps growing to 12,
///     because there is only one of it, shared.
///
/// --- The golden order -----
///
///     1. Static block        once, when the class is first loaded
///     2. IIB                 every time an object is created, before the constructor
///     3. Constructor         every time an object is created, after the IIB
///
/// ANALOGY: Opening a restaurant branch. Setting up the brand and the menu happens once for the whole chain (the
/// static block). Every new table that is laid (an object) gets the cloth and cutlery first (the IIB) and then the
/// waiter greets the guests (the constructor).
///
/// REMEMBER: Static block once per class. IIB and constructor once per object, in that order.

/// ============================================================================
/// YOUR TURN
///   1. Add an IIB to a class that prints "A new object is being created". Create three objects and count the
///      messages.
///   2. Predict the exact output before running: a class with a static block, an IIB and a constructor, and a
///      `main` that creates two objects.
///   3. In the Demo2 example, what would Static Count be after creating a third object? Work it out first.
///   4. Put two IIBs in one class. In what order do they run?
///   5. Move the common code of two constructors into an IIB, and describe what you saved.
/// ============================================================================
