/*
 * Inheritance and Polymorphism: Polymorphism
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What polymorphism means, and the two kinds Java has
///   - Runtime polymorphism: the real object decides which method runs
///   - Why it is useful: one piece of code that works for many types
///   - Upcasting, downcasting and `instanceof`, and what a ClassCastException is
/// ============================================================================


/// ### Topic 1: What is polymorphism? ###
/// Polymorphism is the word that scares newcomers the most, and the idea is much friendlier than the name.
///
/// "Poly" means many and "morph" means forms. POLYMORPHISM is "one thing, many forms". In Java it means: the same
/// method call can behave differently depending on the kind of object it is made on.
///
/// ANALOGY: You press the "Play" button. On a music player it plays a song. On a video app it plays a film. On a
/// game console it starts the game. You always do the same thing (press Play), and each device does what is right
/// for itself. You do not need to know which device you are holding to use the button.
///
/// That is the whole idea, and why it is so useful: the code that PRESSES the button does not need to know the
/// exact kind of device. You write it once, and new kinds of devices can be added later without touching it.
///
/// Java has two kinds of polymorphism:
///
///     Compile-time polymorphism    the compiler decides which version to run     Method overloading
///     Runtime polymorphism         the program decides while it is running       Method overriding + inheritance
///
/// The first was covered in the Method Overloading lesson. This lesson is mostly about the second.

/// ### Topic 2: Runtime polymorphism ###
/// For this to work, three things must be in place:
///
///   - A parent class has a method.
///   - Child classes override it (same name, return type and parameters).
///   - A parent-type VARIABLE holds a child OBJECT, and the method is called through it.
///
/// The decision of which version to run depends on the actual object, not on the type of the variable.

class Animal7 {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog6 extends Animal7 {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

class Cat3 extends Animal7 {
    @Override
    void sound() {
        System.out.println("Cat meows");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal7 a1 = new Animal7();  // reference: Animal, object: Animal
        Animal7 a2 = new Dog6();     // reference: Animal, object: Dog
        Animal7 a3 = new Cat3();     // reference: Animal, object: Cat

        a1.sound();  // Calls Animal's sound
        a2.sound();  // Calls Dog's sound (overridden)
        a3.sound();  // Calls Cat's sound (overridden)
    }
}

/// All three variables have the type `Animal7`, and all three calls are exactly `x.sound()`. Yet three different
/// things print. The variable's type is only the label on the remote control; the object it points at is what
/// really answers.
///
/// JARGON: Java decides at RUN TIME which version to call, by checking the real object. This is called DYNAMIC
/// DISPATCH, or late binding. It is the "runtime" in runtime polymorphism.

/// ### Topic 3: Why it is so useful ###
/// The example above does not yet show the payoff, because we knew each object's type. The payoff comes when one
/// piece of code handles many types without knowing which is which. Here a single loop handles every animal.

class Animal {
    void sound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Woof");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Meow");
    }
}

class Cow extends Animal {
    @Override
    void sound() {
        System.out.println("Moo");
    }
}

public class Main {
    static void makeItSpeak(Animal a) {       // accepts ANY kind of Animal
        a.sound();
    }

    public static void main(String[] args) {
        Animal[] farm = { new Dog(), new Cat(), new Cow(), new Dog() };

        for (Animal a : farm) {
            a.sound();
        }

        makeItSpeak(new Cat());
    }
}

/// Look at what this gives you. The array holds different kinds of animals in one list. The loop is written once
/// and never says "if it is a dog, do this; if it is a cat, do that". If tomorrow you add a `Sheep` class, the loop
/// and the `makeItSpeak` method do not need a single change. That flexibility is why polymorphism is considered the
/// most powerful idea in object-oriented programming.
///
/// WHY: Without polymorphism you would write a long chain of `if (x is Dog) ... else if (x is Cat) ...`, and edit
/// it again for every new animal. With it, every new kind brings its own behaviour.

/// ### Topic 4: Upcasting ###
/// Putting a child object into a parent-type variable is called UPCASTING. You have just been doing it in every
/// example above: `Animal a = new Dog();`. It is automatic and always safe, because a Dog IS an Animal.
///
///     Animal a = new Dog();
///
///   - `Dog` is a subclass of `Animal`.
///   - Even though `a` has the type `Animal`, it will call `Dog`'s overridden methods.
///
/// There is a catch. Through an `Animal` variable you can only use what the Animal type promises. The Dog's own
/// extras, like `wagTail()`, are hidden, even though the object really has them.

class Animal8 {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog7 extends Animal8 {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }

    void wagTail() {
        System.out.println("Dog wags tail");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal8 a = new Dog7();  // Upcasting
        a.sound();   // Calls Dog's version
        a.wagTail(); // Not allowed: reference type is Animal
    }
}
/// @error

/// The compiler says it cannot find `wagTail()` in Animal8. It only checks what the variable's TYPE offers. The
/// object does have the method, but the compiler cannot be sure from the label alone. Downcasting is the way
/// around it.

/// ### Topic 5: Downcasting ###
/// DOWNCASTING converts a parent reference back to a child reference, so you regain access to the child's extras.
///
///   - It must be done manually, with a cast in brackets.
///   - It is risky: if the object is not really of that child type, Java throws a `ClassCastException`.
///
///     Dog d = (Dog) a;     // a must actually refer to a Dog object

class Animal9 {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog8 extends Animal9 {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }

    void wagTail() {
        System.out.println("Dog wags tail");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal9 a = new Dog8();  // Upcasting
        Dog8 d = (Dog8) a;       // Downcasting

        d.sound();               // Works - overridden
        d.wagTail();             // Now accessible after downcasting
        ((Dog8) a).wagTail();    // Downcasting w/o creating a new variable
    }
}

/// The cast does not create a new object. It only tells the compiler "trust me, this is really a Dog", and the
/// same object is reached through a more specific label. But if you are wrong:

class Animal {
}

class Dog extends Animal {
    void wagTail() {
        System.out.println("Dog wags tail");
    }
}

class Cat extends Animal {
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Cat();    // really a Cat
        Dog d = (Dog) a;         // you claim it is a Dog
        d.wagTail();
    }
}
/// @error

/// The compiler allowed the cast, since a Cat could in principle be a Dog as far as the Animal label goes. At run
/// time Java checked the real object and stopped: "class Cat cannot be cast to class Dog".

/// ### Topic 6: The `instanceof` operator ###
/// To downcast safely, first ask the object what it really is. `instanceof` answers with true or false.
///
///     if (a instanceof Dog) {
///         Dog d = (Dog) a;
///     }

class Animal10 {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog9 extends Animal10 {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }

    void wagTail() {
        System.out.println("Dog wags tail");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal10 a = new Dog9();

        if (a instanceof Dog9) {
            Dog9 d = (Dog9) a;
            d.sound();
            d.wagTail();
        } else {
            System.out.println("Not a Dog");
        }
    }
}

/// Newer Java versions let you combine the check and the cast, with a variable name after `instanceof`:

class Animal {
}

class Dog extends Animal {
    void wagTail() {
        System.out.println("Dog wags tail");
    }
}

class Cat extends Animal {
}

public class Main {
    public static void main(String[] args) {
        Animal[] pets = { new Dog(), new Cat() };

        for (Animal p : pets) {
            if (p instanceof Dog d) {      // test and cast in one step
                d.wagTail();
            } else {
                System.out.println("Not a dog");
            }
        }
    }
}

/// TIP: If you find yourself writing many `instanceof` checks, polymorphism is usually the better tool. Give each
/// class its own overriding method, and let Java choose.

/// ### Topic 7: Compile-time polymorphism, a closer look (optional) ###
/// Overloading is also polymorphism, resolved by the compiler. The earlier lesson covered the basics. Here are
/// the cases that surprise people. You may skip this on a first read.
///
/// --- Type promotion: when there is an exact match, it wins -----

public class Main {
    void show(int a) {
        System.out.println("int");
    }

    void show(long a) {
        System.out.println("long");
    }

    public static void main(String[] args) {
        Main t = new Main();
        t.show(100);
    }
}

/// --- If there is no exact match, Java promotes the argument to a wider type -----

public class Main {
    void show(long a) {
        System.out.println("long");
    }

    public static void main(String[] args) {
        Main t = new Main();
        t.show(100);
    }
}

/// --- With several possible promotions, Java chooses the closest -----

public class Main {
    void show(double a) {
        System.out.println("double");
    }

    void show(float a) {
        System.out.println("float");
    }

    public static void main(String[] args) {
        Main t = new Main();
        t.show(5);
    }
}

/// --- Autoboxing: the primitive `int` is preferred over the wrapper `Integer` -----
/// Java can convert an `int` to its object wrapper, `Integer`, automatically. That is called AUTOBOXING. When both
/// versions exist, the exact primitive match goes first.

public class Main {
    void show(Integer a) {
        System.out.println("Integer");
    }

    void show(int a) {
        System.out.println("int");
    }

    public static void main(String[] args) {
        Main b = new Main();
        b.show(10);
    }
}

/// REMEMBER: One call, many behaviours. At compile time (overloading) the types of the arguments decide. At run
/// time (overriding) the real object decides. Upcasting is automatic and safe; downcasting needs a cast and an
/// `instanceof` check.

/// ============================================================================
/// YOUR TURN
///   1. Create `Shape` with `area()`, and children `Circle`, `Rectangle` and `Triangle` that override it. Put one
///      of each in an array and print every area using a single loop.
///   2. Add a method `describe(Shape s)` that works for all the shapes. Add a fourth shape without changing it.
///   3. Upcast a `Dog` to `Animal`, then try calling a Dog-only method. Fix it with a downcast.
///   4. Cause a ClassCastException on purpose, then prevent it using `instanceof`.
///   5. Explain in your own words, with a real-life example, why one method call can behave differently.
/// ============================================================================
