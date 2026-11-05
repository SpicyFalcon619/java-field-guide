/*
 * Inheritance and Polymorphism: Method overriding
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What method overriding is, and how it differs from overloading
///   - Why `@Override` is worth writing, and what it protects you from
///   - The rules of overriding, and what cannot be overridden
///   - A first look at how a parent reference can call a child's version
/// ============================================================================


/// ### Topic 1: What is method overriding? ###
/// A child class inherits its parent's methods. Usually that is exactly what you want. But sometimes the inherited
/// behaviour is not right for the child. Every Animal can `makeSound()`, but a generic "some animal sound" makes no
/// sense for a Dog, which should bark.
///
/// OVERRIDING means: the child writes its OWN version of a method it inherited, with the same name and the same
/// parameters, and that version replaces the parent's for objects of the child type.
///
/// NOTE: A subclass (child) gives its own version of a method that it inherits from the superclass (parent). We
/// use it to change or customise behaviour that comes from the parent class.
///
/// ANALOGY: A family restaurant has a standard recipe for curry. One branch decides to cook it spicier. That
/// branch keeps the same dish name and the same ingredients list, but its own method of cooking replaces the
/// head office version, only there.

/// ### Topic 2: Basic method overriding ###
/// `Animal` has a general sound. `Dog` overrides it. Notice the `@Override` marker above the child's method; we
/// come back to it in a moment.

class Animal {
    public void makeSound() {
        System.out.println("Some animal sound");
    }
}

class Dog extends Animal {
    // Overriding makeSound method
    @Override
    public void makeSound() {
        System.out.println("Woof woof");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Animal();
        Dog d = new Dog();

        a.makeSound();
        d.makeSound();
    }
}

/// The Animal object used the parent's version, while the Dog object used its own. Same method name, different
/// behaviour depending on the object.

/// ### Topic 3: Overriding versus overloading ###
/// These two names sound alike, and almost every beginner mixes them up. Keep them apart with one table.
///
///     Overloading                             Overriding
///     same class                              parent and child classes
///     same name, DIFFERENT parameters         same name, SAME parameters
///     chosen at compile time                  chosen while the program runs
///     no inheritance needed                   needs inheritance
///
/// TIP: OVER-LOAD = load more versions into one class. OVER-RIDE = ride over the parent's method with a
/// new one in a child.

/// ### Topic 4: Why you should always write @Override ###
/// `@Override` is an ANNOTATION: a label for the compiler. It says "I intend this method to override one from the
/// parent". It changes nothing at run time. Its job is to catch your typos. Without it, a tiny spelling slip does
/// not override anything; it silently creates a brand-new, unrelated method.

class Animal {
    void makeSound() {
        System.out.println("Some animal sound");
    }
}

class Dog extends Animal {
    void makesound() {                      // oops: a lowercase s, so a NEW method
        System.out.println("Woof woof");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal a = new Dog();
        a.makeSound();
    }
}

/// The program compiled and ran, but it printed the Animal sound, and the dog never barked. That is the dangerous
/// part: no error, only wrong behaviour. Now add `@Override` and look at what the compiler does.

class Animal {
    void makeSound() {
        System.out.println("Some animal sound");
    }
}

class Dog extends Animal {
    @Override
    void makesound() {
        System.out.println("Woof woof");
    }
}

public class Main {
    public static void main(String[] args) {
        new Dog().makesound();
    }
}
/// @error

/// "method does not override or implement a method from a supertype". The typo is caught instantly, before the
/// program runs.
///
/// TIP: Always write `@Override` when you override. It documents your intent for the reader, and the compiler
/// proves your intent is true.

/// ### Topic 5: Key rules of overriding ###
///   1. The method must have the same name and the same parameters.
///   2. The return type must be the same (or a more specific child type of it).
///   3. It only works with inheritance: there must be a parent to override.
///   4. The child's version cannot be less accessible than the parent's. A `public` method cannot be overridden
///      by a `private` one.
///   5. Use `@Override`, so the compiler can check rules 1 to 3 for you.
///   6. We cannot override: static methods, constructors, final methods and private methods.
///
/// Why those last four? A constructor belongs to its own class, so there is nothing to inherit. A private method
/// is invisible to the child. A static method belongs to the class, not an object, so the "which object is it?"
/// idea does not apply. And a `final` method was marked by its author as "no one may change this", which the
/// Final keyword lesson covers.

class Animal {
    public void speak() {
        System.out.println("generic noise");
    }
}

class Dog extends Animal {
    @Override
    void speak() {                // package-private is weaker than public
        System.out.println("Woof");
    }
}

public class Main {
    public static void main(String[] args) {
        new Dog().speak();
    }
}
/// @error

/// The error says the method is "attempting to assign weaker access privileges; was public". Rule 4 in action.
/// A child must never offer LESS than its parent promised, because code written for the parent would break.

/// ### Topic 6: Extending instead of replacing ###
/// Overriding does not have to throw the parent's behaviour away. Calling `super.method()` inside the new version
/// runs the parent's code first, and you can then add to it.

class Employee {
    void work() {
        System.out.println("Doing the daily tasks");
    }
}

class Manager extends Employee {
    @Override
    void work() {
        super.work();
        System.out.println("Also leading the team");
    }
}

public class Main {
    public static void main(String[] args) {
        new Manager().work();
    }
}

/// ### Topic 7: A parent reference, a child object ###
/// Here is a first taste of why overriding matters so much. A variable of the PARENT type is allowed to hold a
/// CHILD object, since a Car is a Vehicle. When you call the overridden method through that variable, Java runs the
/// version that belongs to the real object.

class Vehicle {
    public void drive() {
        System.out.println("The vehicle is moving...");
    }
}

class Car extends Vehicle {
    @Override
    public void drive() {
        System.out.println("The car is moving!");
    }
}

public class Main {
    public static void main(String[] args) {
        Vehicle v1 = new Vehicle();
        Vehicle v2 = new Car(); // Parent reference, child object

        v1.drive();
        v2.drive();
    }
}

/// `v2` is declared as a Vehicle, yet it printed the Car message. Java looks at the real object and not the
/// type of the variable. This is called RUNTIME POLYMORPHISM, and it is the subject of the next lesson.
///
/// REMEMBER: Overriding = same signature in a child, replacing the parent's version. Always add `@Override`. The
/// real object decides which version runs.

/// ============================================================================
/// YOUR TURN
///   1. Create `Shape` with `area()` returning 0, then `Circle` and `Square` that override it properly.
///   2. Misspell the name of an overriding method on purpose. Run it with and then without `@Override`. Compare.
///   3. Make a parent method `public`, and try to override it with no modifier. Read the error.
///   4. Write a child method that calls `super.method()` and adds one more line. What prints, in what order?
///   5. Explain to a friend, in two sentences, the difference between overloading and overriding.
/// ============================================================================
