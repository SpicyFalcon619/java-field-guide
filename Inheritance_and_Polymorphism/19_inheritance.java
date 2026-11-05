/*
 * Inheritance and Polymorphism: Inheritance
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What inheritance is, and the problem of repeated code it solves
///   - The `extends` keyword, parent and child classes, and the "is-a" test
///   - The `super` keyword: reaching the parent's methods, variables and constructor
///   - What happens, and in what order, when a child object is created
///   - The types of inheritance, why Java has no multiple inheritance of classes, and has-a versus is-a
/// ============================================================================


/// ### Topic 1: The problem of repeated code ###
/// Imagine you are writing a pet-shop program. A Dog can eat, sleep and bark. A Cat can eat, sleep and meow. A
/// Cow can eat, sleep and moo. If you write each class on its own, you type `eat()` and `sleep()` three times.
/// Fix a bug in `eat()` and you must remember to fix it in three places.
///
/// INHERITANCE solves this. You write the shared parts once in a general class, and let the specific classes
/// REUSE them. The general class is the PARENT (also called the superclass or base class). A class that reuses it
/// is a CHILD (also the subclass or derived class).
///
/// ANALOGY: A child inherits eye colour and family traits from their parents without anyone writing them down for
/// the child. In the same way, a Dog class inherits `eat()` from Animal for free, and then adds its own extras,
/// such as `bark()`.
///
/// NOTE: Inheritance helps us reuse code, keeps things organised, and makes polymorphism possible (the next
/// lessons). Of the three, reuse is the first reason people notice.

/// ### Topic 2: The `extends` keyword ###
/// The `extends` keyword creates a child from a parent:
///
///     class Child extends Parent { ... }
///
/// Read it as "Child is a kind of Parent", and it inherits everything accessible from the parent. Here is a
/// working example.

// Parent class
class Animal4 {
    public void eat() {
        System.out.println("This animal eats food");
    }
}

// Child class
class Dog3 extends Animal4 {
    public void bark() {
        System.out.println("The dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog3 d = new Dog3();
        d.eat();
        d.bark();
    }
}

/// `Dog3` never defines `eat()`, yet `d.eat()` works. It was inherited from `Animal4`. `bark()` belongs to Dog3
/// alone, so an Animal4 object would not have it.
///
/// --- The is-a test -----
///
/// Before you use `extends`, say the sentence out loud: "A Dog IS AN Animal". It sounds right, so inheritance fits.
/// "A Car IS AN Engine" sounds wrong, and so does "an Engine IS A Car".
///
/// MISTAKE: Do not use inheritance just because two classes share some code. If the sentence "B is a kind of A" is
/// false, you do not want `extends`. A car HAS an engine, which is a different relationship, covered at the end of
/// this lesson.

/// ### Topic 3: What gets inherited, and what does not ###
/// A child inherits the parent's fields and methods, but a few things deserve attention.
///
///   - A `private` member of the parent exists inside the child's objects, but the child's own code cannot touch
///     it directly. (Access control is the topic of a later lesson.)
///   - Constructors are NOT inherited, because a constructor's name is the name of its class.
///   - A child can add new fields and methods, and can replace inherited methods (overriding, the next lesson).

class Animal {
    private String secret = "hidden";
    String name = "Animal";
}

class Dog extends Animal {
    void show() {
        System.out.println(name);
        System.out.println(secret);
    }
}

public class Main {
    public static void main(String[] args) {
        new Dog().show();
    }
}
/// @error

/// The compiler says `secret` has private access in Animal. A parent can protect certain details even from its own
/// children. The Access Control lesson explains the four levels, including `protected`, which is the version made
/// for children.

/// ### Topic 4: The `super` keyword ###
/// Inside a child, `super` means "the parent part of me". Compare it with `this`, which means "this object": `this`
/// looks at the object's own class first, while `super` reaches up into the parent. You use it for three things:
///
///   - Calling a parent METHOD that the child has replaced.
///   - Reaching a parent VARIABLE that the child has hidden with one of the same name.
///   - Calling the parent CONSTRUCTOR.
///
/// --- Using `super` to call a parent method -----
/// The child below has its own `sound()`, but it first asks the parent to do its part, then adds more.

class Animal2 {
    void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog2 extends Animal2 {
    void sound() {
        super.sound(); // Call parent method
        System.out.println("Dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog2 d = new Dog2();
        d.sound();
    }
}

/// --- Using `super` to access a parent variable -----
/// If the parent and the child both declare a field called `type`, the child's version hides the parent's. Writing
/// `super.type` reaches the hidden one.

class Animal3 {
    String type = "Animal";
}

class Cat extends Animal3 {
    String type = "Cat";

    void printType() {
        System.out.println("Child type: " + type);
        System.out.println("Parent type: " + super.type);
    }
}

public class Main {
    public static void main(String[] args) {
        Cat c = new Cat();
        c.printType();
    }
}

/// TIP: Hiding fields with the same name is confusing and best avoided. It is shown here so that you can read it
/// when you meet it, not as something to copy.

/// ### Topic 5: Creating a child object: the parent comes first ###
/// When you create a Student, which is a kind of Person, Java must build the Person part before the Student
/// part, because the child is built on top of the parent. So the parent's constructor always runs FIRST.
///
/// `super()` is how a child's constructor calls the parent's constructor. It must be the first line.
///
/// --- Using `super()` to call the parent constructor -----

class Person {
    Person() {
        System.out.println("Person constructor called");
    }
}

class Student6 extends Person {
    Student6() {
        super();
        System.out.println("Student constructor called");
    }
}

public class Main {
    public static void main(String[] args) {
        Student6 s = new Student6();
    }
}

/// WHY: If you leave `super()` out, Java silently adds it for you, as an invisible first line. That is why the
/// example above would print exactly the same with or without it. The order is always parent first, then child.
///
/// That invisible call is the source of a classic puzzle. What if the parent has no constructor without
/// parameters? Then the automatic `super()` has nothing to call.

class Person {
    String name;

    Person(String name) {
        this.name = name;
    }
}

class Student extends Person {
    Student() {
        System.out.println("Student created");
    }
}

public class Main {
    public static void main(String[] args) {
        new Student();
    }
}
/// @error

/// The error says Person has no constructor that takes no arguments. The fix is for the child to call the one that
/// does exist, passing what it needs:

class Person {
    String name;

    Person(String name) {
        this.name = name;
    }
}

class Student extends Person {
    int roll;

    Student(String name, int roll) {
        super(name);             // pass the name up to Person
        this.roll = roll;
    }

    void show() {
        System.out.println(name + " has roll " + roll);
    }
}

public class Main {
    public static void main(String[] args) {
        new Student("Rafi", 7).show();
    }
}

/// REMEMBER: `super(...)` calls the parent constructor and must be the first statement. Like `this(...)`, you
/// cannot use both in one constructor.

/// ### Topic 6: Types of inheritance ###
/// Classes can be arranged in a few shapes. They have names, and you will meet them in exams and interviews.
///
///     Type           Example
///     Single         Dog extends Animal
///     Multilevel     Puppy extends Dog extends Animal
///     Hierarchical   Cat, Dog, Cow all extend Animal
///     Multiple       one class with two parents (Java allows this only with interfaces)
///
/// --- Hierarchical inheritance -----
/// Several children share one parent. It looks like a tree: one parent, many children. All of them get the common
/// features, and each adds its own.

class Animal5 {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Dog4 extends Animal5 {
    void bark() {
        System.out.println("The dog barks.");
    }
}

class Cat2 extends Animal5 {
    void meow() {
        System.out.println("The cat meows.");
    }
}

public class Main {
    public static void main(String[] args) {
        Dog4 d = new Dog4();
        d.eat();
        d.bark();

        Cat2 c = new Cat2();
        c.eat();
        c.meow();
    }
}

/// --- Diagram -----
///
///            Animal
///           /   |    \
///        Dog   Cat   Cow
///
/// --- Multilevel inheritance -----
/// A class is derived from a class that is itself derived from another. Think grandparent, parent, child: the
/// child inherits from both.

class Animal6 {
    void eat() {
        System.out.println("This animal eats food.");
    }
}

class Dog5 extends Animal6 {
    void bark() {
        System.out.println("The dog barks.");
    }
}

class Puppy extends Dog5 {
    void weep() {
        System.out.println("The puppy weeps.");
    }
}

public class Main {
    public static void main(String[] args) {
        Puppy p = new Puppy();
        p.eat();   // Inherited from Animal
        p.bark();  // Inherited from Dog
        p.weep();  // Defined in Puppy
    }
}

/// --- Diagram -----
///
///     Animal
///        ↓
///     Dog
///        ↓
///     Puppy
///
/// --- Multiple inheritance, and the diamond problem -----
/// Multiple inheritance means one class has more than one parent. Java does NOT allow a class to extend two
/// classes. The reason is the DIAMOND PROBLEM: suppose two parents both define `sound()` differently. Which
/// version should the child inherit? There is no good answer, and the confusion causes bugs, so Java forbids it.
///
/// Java's solution is the INTERFACE, a list of method promises with no code to inherit. A class may implement as
/// many interfaces as it likes, since two promises cannot conflict the way two pieces of code can. (Interfaces
/// get their own lesson later in this guide.)

interface A {
    void methodA();
}

interface B {
    void methodB();
}

class C implements A, B {
    public void methodA() {
        System.out.println("Method A from Interface A");
    }

    public void methodB() {
        System.out.println("Method B from Interface B");
    }
}

public class Main {
    public static void main(String[] args) {
        C obj = new C();
        obj.methodA();
        obj.methodB();
    }
}

/// --- Diagram -----
///
///      Interface A     Interface B
///              \          /
///               \        /
///                \      /
///                Class C

/// ### Topic 7: Every class has a secret parent ###
/// If you do not write `extends`, your class automatically extends a class called `Object`, which sits at the top of
/// every Java class tree. That is why every object, whatever its class, already has methods such as `toString()`
/// and `equals()` without you writing them.

class Dog {
}

public class Main {
    public static void main(String[] args) {
        Dog d = new Dog();
        System.out.println(d instanceof Object);
        System.out.println(d.getClass().getSimpleName());
        System.out.println(d.equals(d));
    }
}

/// `Dog` has no code at all, and still answers `equals` and `getClass`. Those came from `Object`. Overriding
/// `toString()` from `Object` is how you make an object print as something readable, and you will do exactly
/// that in a later lesson.

/// ### Topic 8: Is-a versus has-a ###
/// Inheritance models "is-a". But many relationships are "has-a": a Car HAS an Engine, a Student HAS an Address.
/// For those, you do not extend. You simply keep an object of the other class in a field. This is called
/// COMPOSITION.

class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Car {
    Engine engine = new Engine();     // a Car HAS an Engine

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}

public class Main {
    public static void main(String[] args) {
        new Car().drive();
    }
}

/// TIP: When unsure, ask the two questions. "Is a B a kind of A?" gives inheritance. "Does B have an A?" gives a
/// field. Mixing them up makes code hard to change later.

/// ============================================================================
/// YOUR TURN
///   1. Create a parent `Vehicle` with `speed` and a `move()` method, and children `Bike` and `Truck` each with one
///      extra method. Call both inherited and own methods.
///   2. Make a three-level chain: `Person` to `Employee` to `Manager`, and print a message from a constructor at
///      every level. Predict the order before you run it.
///   3. Give the parent only a constructor with a parameter and see the error from the child. Then fix it with
///      `super(...)`.
///   4. Use `super.method()` in a child to extend, rather than replace, a parent method.
///   5. For each pair, say "is-a" or "has-a": Circle and Shape, House and Door, Teacher and Person, Library and
///      Book.
/// ============================================================================
