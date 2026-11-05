/*
 * Classes and Objects: Constructors
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a constructor is, and the problem it solves
///   - The rules: same name as the class, and no return type
///   - The invisible default constructor, and the trap when you write your own
///   - How to offer more than one way to create an object
/// ============================================================================


/// ### Topic 1: The problem constructors solve ###
/// In the last lesson, creating a student took three steps: build it, set the name, set the age. It is easy to
/// forget one, and then you have a Student with a `null` name wandering around your program.
///
/// A CONSTRUCTOR is a special method that Java runs automatically, exactly once, at the moment an object is
/// created. It is where you put the setup, so that every object starts life complete and valid.
///
/// ANALOGY: When a new student joins a school, the admission office does not hand over a blank person and hope for
/// the best. A form is filled in at the door: name, age, ID. The constructor is that form. You cannot become a
/// student without filling it in.
///
/// There are two rules that make a constructor different from an ordinary method:
///
///   1. Its name is EXACTLY the name of the class.
///   2. It has NO return type, not even `void`.

/// ### Topic 2: A constructor with parameters ###
/// Here the constructor takes a name and an age, and stores them in the new object's fields.

class Student2 {
    String name;
    int age;

    // Constructor
    public Student2(String n, int a) {
        name = n;
        age = a;
    }

    public void introduce() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {

        Student2 s1 = new Student2("Noman", 35);
        Student2 s2 = new Student2("Tortilla", 100);

        s1.introduce();
        s2.introduce();
    }
}

/// Now `new Student2("Noman", 35)` does everything in one line: it creates the object AND runs the constructor,
/// which fills in the fields with the values you passed. The values inside the brackets are the arguments, just
/// like with a method call.
///
/// WHY: This is safer. If the constructor requires a name and an age, nobody can create a student without them.
/// The class is protecting itself from being half-built.

/// ### Topic 3: Mistakes with constructors ###
/// Because a constructor looks so much like a method, one small slip turns it into something else. If you add a
/// return type, Java treats it as an ordinary method that happens to share the class name, and no constructor
/// exists.

class Student {
    String name;

    public void Student(String n) {      // the "void" makes this a normal method, not a constructor
        name = n;
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student("Noman");
        System.out.println(s.name);
    }
}
/// @error

/// The compiler says it cannot find a constructor that takes a String. The `void` on the line above silently
/// turned it into a method. Remove the `void` and it works.

/// ### Topic 4: The default constructor, and a trap ###
/// If you write no constructor at all, Java quietly gives your class one that takes no arguments and does nothing
/// special. That is the DEFAULT CONSTRUCTOR. It is why `new Student()` worked in the last lesson even though we
/// never wrote any constructor.
///
/// MISTAKE: The moment you write ANY constructor of your own, Java stops providing the free default one. Code
/// that still says `new Student2()` with no arguments then fails to compile, because the only constructor that now
/// exists demands a name and an age.

public class Main {
    public static void main(String[] args) {
        Student2 s = new Student2();
    }
}

class Student2 {
    String name;
    int age;

    public Student2(String n, int a) {
        name = n;
        age = a;
    }
}
/// @error

/// The error says the constructor `Student2(String,int)` cannot be applied to no arguments. If you want both ways
/// of creating an object, you write both constructors yourself, as the next section shows.

/// ### Topic 5: More than one constructor ###
/// A class may have several constructors, as long as their parameter lists differ. This is CONSTRUCTOR
/// OVERLOADING, the same idea as method overloading. Java picks the one that matches the arguments in your
/// `new` call.
///
/// A constructor with no parameters, written by you, is also called a default constructor, because it gives
/// every object some sensible default values. One that takes parameters is a PARAMETERIZED constructor.

class Student3 {
    String name;
    int age;

    public Student3() {
        name = "nothing";
        age = 0;
    }

    public Student3(String n, int a) {
        name = n;
        age = a;
    }

    public void introduce() {
        System.out.println("Hi, I'm " + name + " and I'm " + age + " years old.");
    }
}

public class Main {
    public static void main(String[] args) {
        Student3 s1 = new Student3();
        Student3 s2 = new Student3("Noman", 35);

        s1.introduce();
        s2.introduce();
    }
}

/// `new Student3()` matched the constructor with no parameters. `new Student3("Noman", 35)` matched the one with a
/// String and an int. Java decides by the NUMBER and TYPES of the arguments.

/// ### Topic 6: Constructors can check their input ###
/// Because a constructor is ordinary code, it can refuse bad data or fix it. This is a hint of where we are
/// heading in the Encapsulation lesson: a class that guards its own data.

class Account {
    String owner;
    double balance;

    public Account(String owner, double startingBalance) {
        this.owner = owner;
        if (startingBalance < 0) {
            this.balance = 0;
            System.out.println("Negative balance not allowed, starting at 0.");
        } else {
            this.balance = startingBalance;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Account a = new Account("Rafi", 500);
        Account b = new Account("Mim", -50);
        System.out.println(a.owner + ": " + a.balance);
        System.out.println(b.owner + ": " + b.balance);
    }
}

/// You may have noticed `this.owner = owner;` and wondered what `this` is. That is exactly the next lesson, and
/// it also explains why the parameter can safely have the same name as the field.
///
/// REMEMBER: A constructor has the class's name, no return type, runs once per `new`, and exists to give every
/// object a valid start.

/// ============================================================================
/// YOUR TURN
///   1. Write a class `Book` with `title` and `price`, and a constructor that sets both. Create two books.
///   2. Add a second constructor to Book that takes only the title and sets the price to 0.
///   3. Remove the no-argument constructor from Student3 and see what breaks in `Main`. Why?
///   4. Write a class `Rectangle` whose constructor takes length and width, and a method `area()`.
///   5. Make a constructor refuse a negative age by setting it to 0 instead.
/// ============================================================================
