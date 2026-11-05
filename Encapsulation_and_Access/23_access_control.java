/*
 * Encapsulation and Access: Access control
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why a class should hide some of its insides
///   - The four access levels: public, private, protected and default (package-private)
///   - What a package is, and why two of the levels depend on it
///   - How to choose the right level
/// ============================================================================


/// ### Topic 1: Why hide anything? ###
/// So far, every field we wrote could be read and changed by any code anywhere: `student.age = -500;` was
/// perfectly legal. Nothing stopped a careless line from putting the object into a nonsense state.
///
/// ANALOGY: Think of a bank. The vault, the ledgers and the safe are for staff only. Customers see the counter,
/// the ATM and the app, which are controlled doorways to the money. If anyone could walk into the vault, the bank
/// could not guarantee a single balance.
///
/// Java lets a class decide, for every field, method and constructor, WHO is allowed to use it. This is ACCESS
/// CONTROL, and the words that set it are ACCESS MODIFIERS. They are the main tool of the next lesson,
/// encapsulation.

/// ### Topic 2: The four access levels ###
/// Java has four levels, from most open to most closed:
///
///     Modifier        Same class   Same package   Subclass (other package)   Everywhere
///     public          yes          yes            yes                        yes
///     protected       yes          yes            yes                        no
///     (nothing)       yes          yes            no                         no
///     private         yes          no             no                         no
///
/// The "nothing" row is called DEFAULT access, or PACKAGE-PRIVATE, because when you write no modifier the member is
/// visible only inside its own package. You write nothing at all, and that is the rule.
///
/// Notice that two of the columns mention a package. Let us pause and say what that is.

/// ### Topic 3: What is a package? ###
/// A PACKAGE is a folder of related classes, a way to group a big program into sections, just like the folders on
/// your computer. The Java library is organised this way: `java.util` holds the Scanner, `java.io` holds files, and
/// `java.lang` holds `String` and `System`. You met this in the Scanner lesson.
///
/// Your own classes can be placed in a package with a line at the very top of the file, `package school;`, and
/// other packages then pull them in with an `import`. All the examples in this guide use the unnamed default
/// package, so every class in a file counts as being in the "same package". That is a useful fact when you read
/// the table above: for our examples, "same package" is always true.
///
/// JARGON: When a modifier is "package-private", the word package is doing real work: classes in other folders
/// cannot see it. Real projects always use packages. Small practice programs do not.

/// ### Topic 4: public: open to everyone ###
///   - Visibility: accessible from any class in any package.
///   - Use: the things that are meant to be used from outside, such as a class's main methods.

public class Main {
    public String name; // Public variable

    // Public method
    public void greet() {
        System.out.println("Hello, " + name);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.name = "Rafi";
        m.greet();
    }
}

/// ### Topic 5: private: only inside the class ###
///   - Visibility: accessible only inside the class where it is written. Not even a child class or a neighbour in
///     the same package can touch it.
///   - Use: to hide the details, so the class has full control over its own data. This is the heart of
///     encapsulation, with controlled access through methods called getters and setters.

class Account {
    private double balance = 100;
}

public class Main {
    public static void main(String[] args) {
        Account a = new Account();
        a.balance = 1000000;
        System.out.println(a.balance);
    }
}
/// @error

/// The compiler refuses: "balance has private access in Account". The balance can only be changed from inside the
/// Account class, which would then decide the rules. Here is the same idea written properly. Notice that the
/// private field is still used freely by the class's own methods:

public class Main {
    private String name; // Private variable

    // Getter method to access the private variable
    public String getName() {
        return name;
    }

    // Setter method to modify the private variable
    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.setName("Rafi");
        System.out.println(m.getName());
    }
}

/// Wait: `main` is inside the `Main` class, so it could even read `m.name` directly. Privacy is per CLASS, not per
/// object, and that is why this example works. From any OTHER class, only the getter and setter are visible.

/// ### Topic 6: protected: for the family ###
///   - Visibility: the same package, and also subclasses, even in other packages.
///   - Use: you want children to use a member, but you still want to hide it from the rest of the world.
///
/// In our single-package examples, `protected` behaves much like default, so the difference cannot be seen here.
/// It shows when a child class lives in a different package. Remember it as: private plus "my children may
/// use it".

class Animal {
    protected String name = "Rex";

    protected void greet() {
        System.out.println("Hello, " + name);
    }
}

class Dog extends Animal {
    void introduce() {
        greet();                       // a child may use protected members
        System.out.println("I am " + name);
    }
}

public class Main {
    public static void main(String[] args) {
        new Dog().introduce();
    }
}

/// ### Topic 7: Default (package-private): no modifier ###
///   - Visibility: only classes in the same package.
///   - Use: helper classes and methods that belong to a package's inner workings, and should not be seen from
///     outside.

public class Main {
    String name; // No access modifier, package-private

    void greet() { // Package-private method
        System.out.println("Hello, " + name);
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.name = "Mim";
        m.greet();
    }
}

/// ### Topic 8: All four side by side ###
/// This class has one of each. Inside the class, everything is reachable. From `main`, which is also inside the
/// class, all four work, except the commented lines, which show what would fail from a different class.

public class Main {
    public String publicVariable = "I am public";
    private String privateVariable = "I am private";
    protected String protectedVariable = "I am protected";
    String defaultVariable = "I am package-private";

    public void publicMethod() {
        System.out.println("Public method called");
    }

    private void privateMethod() {
        System.out.println("Private method called");
    }

    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    void defaultMethod() {
        System.out.println("Package-private method called");
    }

    public static void main(String[] args) {
        Main example = new Main();

        // Accessing public variable and method
        System.out.println(example.publicVariable);
        example.publicMethod();

        // Accessing private variable and method (allowed here, but not from another class)
        System.out.println(example.privateVariable);
        example.privateMethod();

        // Accessing protected variable and method
        System.out.println(example.protectedVariable);
        example.protectedMethod();

        // Accessing default variable and method
        System.out.println(example.defaultVariable);
        example.defaultMethod();
    }
}

/// ### Topic 9: Which one should I use? ###
///   - Use `public` when a class, method or variable has to be usable from anywhere.
///   - Use `private` for hiding implementation details. This is the default choice for FIELDS.
///   - Use `protected` when you want subclasses to reach something, but nobody else.
///   - Use default (package-private) for helpers that should stay inside their package.
///
/// WHY: A good rule is "as private as possible". Start with `private`. Open something up only when a real need
/// appears. Every `public` member is a promise to every user of the class that it will always exist, which makes
/// the class harder to change later.
///
/// TIP: Classes themselves can be `public` or default, but not private or protected (unless nested inside another
/// class). Fields are almost always private, and methods that make up the class's public face are public.
///
/// REMEMBER: private, then default, then protected, then public: from closed to open.

/// ============================================================================
/// YOUR TURN
///   1. Write a class `Student` with private `name` and `marks`. Try to read `marks` directly from `Main` and read
///      the compiler error.
///   2. Add public `getMarks()` and `setMarks(int m)` methods. Make `setMarks` ignore values above 100.
///   3. Make a parent class with a `protected` field and use it inside a child class.
///   4. From the table in Topic 2, answer: can a subclass in another package use a default member? Can it use a
///      protected one?
///   5. In your own words, why should fields usually be private?
/// ============================================================================
