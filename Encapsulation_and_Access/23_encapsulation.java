/*
 * Encapsulation and Access: Encapsulation
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What encapsulation is: hiding the data and guarding it with methods
///   - Getters and setters, and why they are better than public fields
///   - Validation: how a class protects itself from bad values
///   - Read-only fields, and the benefits of all of this
/// ============================================================================


/// ### Topic 1: What is encapsulation? ###
/// ENCAPSULATION is wrapping the data (the variables) and the code that works on it (the methods) into a single
/// unit, a class, and then hiding the data inside. Outsiders get a few public methods for access instead of the
/// raw data itself.
///
/// The word comes from "capsule". A medicine capsule keeps the ingredients inside a shell. You do not need to know
/// what the powder is made of: you just swallow the capsule.
///
/// ANALOGY: A TV remote. You press Volume Up and the volume rises. You do not open the TV and twist a wire. The
/// buttons are the public methods; the electronics are private. Because the makers kept the inside hidden, they
/// are free to redesign it in the next model, and your remote keeps working.
///
/// Encapsulation is built from two ideas you already know:
///
///   1. Make the fields `private` (the Access Control lesson).
///   2. Provide public methods to read and change them: GETTERS and SETTERS.

/// ### Topic 2: The problem, seen first ###
/// With public fields, nothing prevents nonsense. A class cannot defend itself.

class Student {
    public String name;
    public int age;
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
        s.name = "";
        s.age = -500;                // legal, and absurd

        System.out.println("Name: [" + s.name + "], age: " + s.age);
    }
}

/// The program is perfectly happy with a student who is minus 500 years old. Nothing is wrong as far as Java can
/// tell. The class needs a way to say no.

/// ### Topic 3: Basic example of encapsulation ###
/// The class below makes both fields `private` and offers methods. Read the comments, then look at how `Main` uses
/// them.
///
/// A GETTER is a method that returns a field's value, usually named `getX`. A SETTER stores a new value, usually
/// named `setX`. Because every change now passes through a method, the method can check it, and the `setAge`
/// below refuses an unreasonable age.

class Person2 {
    // Private attributes
    private String name;
    private int age;

    // Constructor to initialize the object
    public Person2(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getter method for 'name'
    public String getName() {
        return name;
    }

    // Setter method for 'name'
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for 'age'
    public int getAge() {
        return age;
    }

    // Setter method for 'age' with validation
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        } else {
            System.out.println("Invalid age!");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Person2 p1 = new Person2("Noman", 35);

        System.out.println(p1.getName());
        System.out.println(p1.getAge());

        p1.setName("Tortilla");
        p1.setAge(100);

        System.out.println(p1.getName());
        System.out.println(p1.getAge());

        // Attempting to set an invalid age
        p1.setAge(200);
        System.out.println(p1.getAge());
    }
}

/// Notes on the example:
///
///   - `name` and `age` are private. Code outside Person2 cannot touch them directly.
///   - The public methods `getName()`, `getAge()`, `setName()` and `setAge()` are the controlled doorway.
///   - The age of 200 was rejected, and the old value of 100 stayed. The class protected its own data.
///
/// IMPORTANT: Notice that the constructor does not validate the age, so `new Person2("X", -5)` would still slip
/// through. A careful class validates in the constructor too. A neat way is to have the constructor call the
/// setter, so the checking lives in one place.

/// ### Topic 4: A realistic example: a bank account ###
/// A bank account shows why encapsulation matters. The balance must never be set directly. It may only change
/// through deposits and withdrawals, each with its own rules.

class BankAccount {
    private double balance;

    public BankAccount(double startingBalance) {
        if (startingBalance >= 0) {
            balance = startingBalance;
        }
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount);
        } else {
            System.out.println("Deposit must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient funds.");
        } else if (amount <= 0) {
            System.out.println("Withdrawal must be positive.");
        } else {
            balance -= amount;
            System.out.println("Withdrew " + amount);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount(500);

        acc.deposit(200);
        acc.withdraw(1000);       // refused
        acc.withdraw(150);
        acc.deposit(-50);         // refused

        System.out.println("Balance: " + acc.getBalance());
    }
}

/// There is no `setBalance` at all. The only way to change the balance is through `deposit` and `withdraw`, which
/// enforce the rules. Money cannot appear from nowhere or go negative. If the field were public, one careless line
/// could break that promise.
///
/// WHY: Notice the design choice. We gave `balance` a getter but NO setter. That makes it READ-ONLY from outside.
/// Not every private field needs both. Provide only what outsiders really need.

/// ### Topic 5: Trying to cheat ###
/// Let us confirm that the protection really works.

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount(500);
        acc.balance = 1000000;
        System.out.println(acc.getBalance());
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double startingBalance) {
        balance = startingBalance;
    }

    public double getBalance() {
        return balance;
    }
}
/// @error

/// The compiler stops the attempt: "balance has private access in BankAccount".

/// ### Topic 6: Benefits of encapsulation ###
///   - DATA PROTECTION: private fields cannot be set to invalid values behind the class's back.
///   - CONTROL: setters can validate, convert or log changes. You decide the rules in one place.
///   - FLEXIBILITY: the inside of the class can change without breaking the code that uses it. Perhaps tomorrow
///     the balance is stored in cents as an `int`. Everyone using `getBalance()` still works, because the public
///     methods, the INTERFACE of the class, stayed the same.
///   - MAINTAINABILITY: when something goes wrong with a value, you only need to look at the few methods that
///     can change it, and not the entire program.
///
/// JARGON: Hiding the details inside a class and showing only what is necessary is also called INFORMATION HIDING.
/// It is closely related to ABSTRACTION, a later lesson: the user of a class sees WHAT it does, not HOW.
///
/// TIP: Your IDE can write getters and setters for you. In IntelliJ it is Alt+Insert; in VS Code, right-click and
/// choose "Source Action", then "Generate Getters and Setters". You still decide which fields deserve them.
///
/// REMEMBER: Fields private, behaviour public. Validate in setters and constructors. Offer only the access that is
/// needed: a getter alone makes a field read-only.

/// ============================================================================
/// YOUR TURN
///   1. Write a `Student` class with private `name` and `marks`, and a setter that rejects marks outside 0 to 100.
///   2. Make a `Product` with a price that can be read and changed, but never to a negative value.
///   3. Extend BankAccount with a `transfer(BankAccount other, double amount)` method.
///   4. Make a class with a field that has a getter but no setter, and explain why that might be useful.
///   5. Change the constructor of `Person2` so it calls `setAge`, so that even a bad starting age is checked.
/// ============================================================================
