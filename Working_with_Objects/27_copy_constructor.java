/*
 * Working with Objects: Copy constructor
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why `b = a` does not copy an object
///   - What a copy constructor is, and how to write one
///   - Shallow copy versus deep copy, and the trap in between
/// ============================================================================


/// ### Topic 1: Why `b = a` does not copy anything ###
/// You learned in the Classes and Objects lesson that an object variable holds an address. So writing
/// `Student s2 = s1;` copies the ADDRESS, not the student. Both variables then point at the same one object, and a
/// change through either is visible through both.

class Student {
    String name;
    int age;

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Noman", 35);
        Student s2 = s1;           // not a copy: one student, two names for it
        s2.name = "Tortilla";

        System.out.println(s1.name);
        System.out.println(s2.name);
    }
}

/// Renaming `s2` renamed `s1` too, because there was only ever one student. When you really want a second,
/// independent object with the same values, you have to create it yourself. That is what a copy constructor does.

/// ### Topic 2: What is a copy constructor? ###
/// A copy constructor is a constructor that creates a NEW object by copying the fields from another object of the
/// same class. It takes one parameter: an object of its own class.
///
/// NOTE: Unlike C++, Java does not provide one by default. We define it ourselves when we need it.

/// ### Topic 3: Example ###

class Student9 {
    String name;
    int age;

    // Normal constructor
    Student9(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Copy constructor
    Student9(Student9 s) {
        this.name = s.name;
        this.age = s.age;
    }

    void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

public class Main {
    public static void main(String[] args) {
        Student9 s1 = new Student9("Noman", 35);
        Student9 s2 = new Student9(s1); // Copy constructor called

        s2.name = "Tortilla";            // changing the copy...

        s1.display();                    // ...leaves the original alone
        s2.display();
    }
}

/// Now there are two separate objects, each with its own `name` and `age`. Changing the copy leaves the original
/// alone, which is exactly what `s2 = s1` could not give us.
///
/// Use case: a copy constructor is useful when you want to duplicate an object with the same values, for example to
/// try a change on a copy first, or to give someone a snapshot that they cannot use to alter your original.
///
/// TIP: A copy constructor is just an overloaded constructor (see the Constructors and Method Overloading lessons).
/// Java chooses it because you passed one Student and not a String and an int.

/// ### Topic 4: A trap: shallow copy ###
/// The copy above worked because `name` is a String (immutable) and `age` is a primitive. But what if a field is
/// itself an OBJECT? Copying that field with `=` copies only its address, so the original and the copy end up SHARING
/// that inner object. This is called a SHALLOW COPY.

class Address {
    String city;

    Address(String city) {
        this.city = city;
    }
}

class Person {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    Person(Person other) {
        this.name = other.name;
        this.address = other.address;       // copies only the address (the arrow), not the Address object
    }
}

public class Main {
    public static void main(String[] args) {
        Person original = new Person("Rafi", new Address("Dhaka"));
        Person copy = new Person(original);

        copy.address.city = "Leeds";

        System.out.println(original.address.city);
        System.out.println(copy.address.city);
    }
}

/// The original moved to Leeds as well. The two Persons are separate objects, but they point at the same Address.
///
/// ANALOGY: Photocopying a page of a notebook gives you a new page. But if the page only says "see the locker", both
/// pages send you to the same locker.

/// ### Topic 5: The fix: deep copy ###
/// A DEEP COPY copies the inner objects as well, so nothing is shared. Give each class a copy constructor and let
/// the outer one call the inner one.

class Address {
    String city;

    Address(String city) {
        this.city = city;
    }

    Address(Address other) {
        this.city = other.city;
    }
}

class Person {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    Person(Person other) {
        this.name = other.name;
        this.address = new Address(other.address);     // a new Address, too
    }
}

public class Main {
    public static void main(String[] args) {
        Person original = new Person("Rafi", new Address("Dhaka"));
        Person copy = new Person(original);

        copy.address.city = "Leeds";

        System.out.println(original.address.city);
        System.out.println(copy.address.city);
    }
}

/// Now the copy has its own Address, and moving it does not move the original.
///
/// WHY: Shallow versus deep is a choice. Sometimes sharing is exactly what you want (many students sharing one
/// School object). The point is to know which one you are getting, so it is never a surprise.
///
/// REMEMBER: `a = b` shares an object. A copy constructor makes a new one. If the object holds other objects, copy
/// those as well to get a deep copy.

/// ============================================================================
/// YOUR TURN
///   1. Write a class `Book` with `title` and `price`, and a copy constructor. Copy a book, change the copy's price,
///      and print both.
///   2. Show the "one object, two names" problem with `Book b2 = b1;`, then explain why a copy constructor fixes it.
///   3. Give `Person` a `String[] hobbies` array. Write a copy constructor that copies the array too, using
///      `Arrays.copyOf`. Prove that changing the copy's hobbies leaves the original alone.
///   4. For each field of this class, say whether copying it with `=` is safe: `int`, `String`, `Address`,
///      `int[]`.
/// ============================================================================
