/*
 * Beyond the Course: Inner and anonymous classes
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a class inside a class is, and why you would write one
///   - Member (inner) classes, static nested classes, and local classes
///   - Anonymous classes: one-off implementations with no name
///   - How these connect to events and lambdas
/// ============================================================================


/// ### Topic 1: A class inside a class ###
/// So far every class stood on its own. Java lets you put a class INSIDE another class. You do this when the inner class
/// exists only to help the outer one, and nobody else needs it.
///
/// ANALOGY: A car has an engine. Nobody builds an Engine on its own, in a vacuum: it only makes sense as part of a Car. Putting
/// `Engine` inside `Car` says exactly that, and keeps the rest of the program tidy.
///
/// There are four kinds:
///
///     member (inner) class       a class declared inside another, tied to an object of the outer class
///     static nested class        like an inner class, but not tied to an outer object
///     local class                a class declared inside a method
///     anonymous class            a class with no name, created on the spot

/// ### Topic 2: Member (inner) classes ###
/// An inner class is written inside the outer class, without `static`. Each inner object belongs to one outer object and can
/// use that outer object's fields directly, even private ones.

class Car {
    private String model;

    Car(String model) {
        this.model = model;
    }

    class Engine {
        void start() {
            System.out.println("The engine of the " + model + " starts");      // uses Car's private field
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car("Corolla");
        Car.Engine engine = car.new Engine();          // an inner object needs an outer object
        engine.start();
    }
}

/// The odd-looking `car.new Engine()` says "create an Engine that belongs to THIS car". The type is written
/// `Car.Engine`. That is also why inner classes are useful: the Engine can see inside its Car.
///
/// The usual pattern is to let the outer class create its inner objects, so the caller never sees this syntax:

class Bank {
    private int balance = 100;

    class Teller {
        void showBalance() {
            System.out.println("Balance: " + balance);
        }
    }

    Teller hire() {
        return new Teller();                    // inside Bank, plain "new" is enough
    }
}

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Bank.Teller t = bank.hire();
        t.showBalance();
    }
}

/// ### Topic 3: Static nested classes ###
/// Add `static` and the nested class no longer belongs to an outer OBJECT, only to the outer CLASS. It cannot use the outer's
/// instance fields, but it can be created on its own: `new Outer.Nested()`. Use it when the nested class is just a helper that
/// does not need the outer object.

class Library {
    static class Book {
        String title;

        Book(String title) {
            this.title = title;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library.Book b = new Library.Book("Effective Java");     // no Library object needed
        System.out.println(b.title);
    }
}

/// A common real use is a small data holder or a "builder" next to the class it serves. Java's own `Map.Entry` is a nested
/// type of `Map`.
///
/// --- Inner versus static nested -----
///     inner class           needs an outer object, can use its fields, created with outer.new Inner()
///     static nested class   needs no outer object, cannot use its instance fields, created with new Outer.Nested()
///
/// TIP: If your nested class does not need to touch the outer object, make it `static`. It is simpler and avoids carrying a
/// hidden reference to the outer object around.

/// ### Topic 4: Local classes ###
/// A class can be declared inside a method, and can only be used there. It is rare, but you will see it.

public class Main {
    public static void main(String[] args) {
        class Greeter {
            void greet(String name) {
                System.out.println("Hello, " + name);
            }
        }

        new Greeter().greet("Rafi");
    }
}

/// ### Topic 5: Anonymous classes ###
/// Sometimes you need an object of an interface or class just once, for one small job. Writing a whole named class for it is
/// heavy. An ANONYMOUS CLASS defines and creates it at the same time, with no name:
///
///     new SomeInterface() {
///         // implement its methods here
///     }
///
/// You saw it in the GUI lessons with `new ActionListener() { ... }`. Here it is on its own:

interface Greeting {
    void say(String name);
}

public class Main {
    public static void main(String[] args) {
        Greeting formal = new Greeting() {
            @Override
            public void say(String name) {
                System.out.println("Good day, " + name + ".");
            }
        };

        Greeting casual = new Greeting() {
            @Override
            public void say(String name) {
                System.out.println("Hey " + name + "!");
            }
        };

        formal.say("Dr. Rahman");
        casual.say("Mim");
    }
}

/// Two different implementations of Greeting, created without writing two named classes. Read it as "create an object of a
/// new, nameless class that implements Greeting, like this".
///
/// An anonymous class can also extend a class, and override only the methods it wants:

class Animal {
    void sound() {
        System.out.println("some sound");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal cow = new Animal() {
            @Override
            void sound() {
                System.out.println("Moo");
            }
        };

        cow.sound();
    }
}

/// ### Topic 6: Captured variables must not change ###
/// An anonymous (or local) class can read local variables of the method it sits in, but only if they never change after being
/// set, that is, they are `final` or "effectively final". This is a rule you will meet again with lambdas.

interface Printer {
    void print();
}

public class Main {
    public static void main(String[] args) {
        int base = 10;

        Printer p = new Printer() {
            public void print() {
                System.out.println("base is " + base);
            }
        };

        base = 20;               // changing it after it was captured
        p.print();
    }
}
/// @error

/// The error says local variables referenced from an inner class must be final or effectively final. The class takes a COPY
/// of `base`, so letting it change afterwards would leave the copy stale. Java forbids it, to keep things simple.

/// ### Topic 7: Anonymous classes and lambdas ###
/// When the interface has a single method (a "functional interface"), an anonymous class is a lot of words for one line of
/// real code. A lambda is the compact form of the same thing, and the next lesson covers it. Compare:

interface Greeting {
    void say(String name);
}

public class Main {
    public static void main(String[] args) {
        Greeting anonymous = new Greeting() {
            @Override
            public void say(String name) {
                System.out.println("Hello, " + name);
            }
        };

        Greeting lambda = name -> System.out.println("Hello, " + name);

        anonymous.say("Rafi");
        lambda.say("Rafi");
    }
}

/// Same behaviour, much less typing. Anonymous classes are still needed when the interface has more than one method or when
/// you extend a class.
///
/// REMEMBER: A class inside a class groups helpers with the thing they help. An inner class needs an outer object and can use
/// its fields. A static nested class does not. An anonymous class is a one-off, nameless implementation, and lambdas are its
/// shorter form for single-method interfaces.

/// ============================================================================
/// YOUR TURN
///   1. Write a `House` class with an inner class `Room` that prints the house's address. Create a room from `main`.
///   2. Make a static nested class `Config.Settings` and create one without a `Config` object.
///   3. Create an anonymous `Runnable` that prints a message, and run it in a Thread.
///   4. Use an anonymous class to override one method of an `Animal` object, as in Topic 5.
///   5. Cause the "effectively final" error, then fix it by not changing the variable.
///   6. In your own words: when would you choose a static nested class over an inner class?
/// ============================================================================
