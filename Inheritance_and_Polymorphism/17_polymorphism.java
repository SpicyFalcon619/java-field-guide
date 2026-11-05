/*
 * Inheritance and Polymorphism: Polymorphism
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What is Polymorphism?
///   - Runtime Polymorphism using Method Overriding
///   - Compile-time Polymorphism using Method Overloading
///   - Upcasting
///   - Downcasting
///   - `instanceof` Operator
/// ============================================================================


/// ### Topic 1: What is Polymorphism? ###
/// "Poly" = many, "morph" = forms
/// Polymorphism = “one thing, many forms”
/// In Java, it means:
///
/// NOTE: The same method call can behave differently depending on the object type.
///
/// There are two types:
///    1. Compile-time polymorphism → Method Overloading
///    1. Runtime polymorphism → Method Overriding + Inheritance


/// ### Topic 2: Runtime Polymorphism using Method Overriding ###
/// Setup:
///    - Parent class has a method.
///    - Child class overrides it (same name, return type, parameters).
///    - But method call depends on actual object type, not reference type.
///
/// --- Code -----


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


/// ### Topic 3: Compile-time Polymorphism using Method Overloading ###
/// Rules for Method Overloading:
///    - Must have different parameter list.
///    - Can’t overload based on just return type.
///    - Allowed to change number/type/order of parameters.
///
/// --- Basic Overloading -----

public class Main {

    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double add(double a, double b) {
        return a + b;
    }

    public static void main(String[] args) {
        Main calc = new Main();

        System.out.println("add(2, 3): " + calc.add(2, 3));
        System.out.println("add(2, 3, 4): " + calc.add(2, 3, 4));
        System.out.println("add(2.5, 3.5): " + calc.add(2.5, 3.5));
    }
}

/// --- Overloading with different parameter order -----
///
///     void show(String s, int n) {
///     
///     }
///     void show(int n, String s) {
///     
///     }
///
/// --- Type Conversion (Don’t touch it now) -----
///
/// When Java automatically converts one data type to another in method calls — it’s called type
/// promotion or type conversion.
///
/// --- Type Promotion from `int` to `long` -----
///
/// --- If match is available: -----

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

/// --- If match is not available: -----

public class Main {
    void show(long a) {
        System.out.println("long");
    }

    public static void main(String[] args) {
        Main t = new Main();
        t.show(100);
    }
}

/// --- Multiple overloads — Java chooses closest match: -----

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

/// --- Autoboxing (with overloads) (Don’t touch it now) -----

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


/// ### Topic 4: Upcasting ###
/// Upcasting means assigning a subclass object to a superclass reference.
/// It’s done automatically and allows runtime polymorphism.
///
/// --- Syntax: -----
///
///     Animal a = new Dog();
///
///    - `Dog` is a subclass of `Animal`.
///    - Even though `a` is of type `Animal`, it will call `Dog`'s overridden methods.
///
/// --- Example -----


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
        // a.wagTail(); //  Not allowed — reference type is Animal
    }
}


/// ### Topic 5: Downcasting ###
/// Downcasting = converting a superclass reference back to a subclass reference.
///    - Must be done manually using a cast.
///    - Risky: can throw a `ClassCastException` if the object is not actually of that subclass.
///
/// --- Syntax: -----
///
///     Dog d = (Dog) a; // a must actually refer to a Dog object
///
/// --- Example -----


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

        d.sound();          // Works — overridden
				d.wagTail();        // Now accessible after downcasting
	      ((Dog8)a).wagTail(); // Downcasting w/o creating new object
    }
}


/// ### Topic 6: `instanceof` Operator ###
/// We use this to safely check object type before downcasting.
///
/// --- Syntax: -----
///
///     if (a instanceof Dog) {
///         Dog d = (Dog) a;
///     }
///
/// --- Example -----


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
