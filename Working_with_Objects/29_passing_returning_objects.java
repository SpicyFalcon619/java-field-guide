/*
 * Working with Objects: Passing and returning objects
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Passing an object to a method, and what the method can do with it
///   - Returning an object from a method, including a brand-new one
///   - Using objects as parameters to compare or combine them
///   - Putting these together with constructor overloading, with a Box example
/// ============================================================================


/// ### Topic 1: Passing objects as arguments ###
/// You saw in the last lessons that an object passed to a method arrives as a copy of the reference (the address). Put
/// plainly:
///    - The method is not given a copy of the object. It is given a way to reach the SAME object.
///    - It can modify the object's internal state, and the caller will see it.
///    - It cannot change which object the caller's variable points to.
///
/// --- Example -----

class Student11 {
    String name;

    Student11(String n) {
        name = n;
    }

    void changeName(Student11 s) {
        s.name = "Changed Name";
    }
}

public class Main {
    public static void main(String[] args) {
        Student11 st = new Student11("Noman");
        System.out.println("Before: " + st.name);
        st.changeName(st);
        System.out.println("After: " + st.name);
    }
}

/// The object was handed to its own method here, which is allowed (an object can be passed to its own method), and
/// the method renamed the one real student.

/// ### Topic 2: Returning an object from a method ###
/// A method can return an object just like it returns a number. This is often used in "factory" methods (which build
/// and hand back a new object) and in methods that produce a result as an object.
///
/// --- Example -----

class Adder {
    int a;

    Adder(int num) {
        a = num;
    }

    Adder addTen() {
        Adder result = new Adder(a + 10);
        return result;
    }
}

public class Main {
    public static void main(String[] args) {
        Adder ob1 = new Adder(5);
        Adder ob2 = ob1.addTen();

        System.out.println("ob1.a = " + ob1.a); // 5
        System.out.println("ob2.a = " + ob2.a); // 15
    }
}

/// `addTen()` did not change the object it was called on. It built a NEW Adder holding 15 and returned it, so `ob1`
/// still holds 5. Compare that with returning `this`, from the `this` lesson, which hands back the same object.
///
/// WHY: Returning a new object instead of changing the old one keeps the original safe. Java does this all the time:
/// `"hello".toUpperCase()` returns a new String and leaves the original alone.

/// ### Topic 3: Using objects as parameters ###
/// --- Concept: -----
///
/// We can pass objects as parameters to methods. This allows us to:
///    - Compare two objects.
///    - Copy data from one object to another.
///    - Perform operations involving multiple objects.
///
/// --- Example -----

class Test2 {
    int a, b;

    Test2(int i, int j) {
        a = i;
        b = j;
    }

    boolean equals(Test2 o) {
        if (o.a == a && o.b == b) return true;
        else return false;
    }
}

public class Main {
    public static void main(String[] args) {
        Test2 ob1 = new Test2(100, 22);
        Test2 ob2 = new Test2(100, 22);
        Test2 ob3 = new Test2(-1, -1);

        System.out.println("ob1 == ob2: " + ob1.equals(ob2));
        System.out.println("ob1 == ob3: " + ob1.equals(ob3));
    }
}

/// The method receives a second Test2 (`o`) and compares its fields with its own. `ob1` and `ob2` are different
/// objects, but they hold the same values, so our `equals` says true.
///
/// MISTAKE: The `==` operator on objects asks "are these the SAME object?", not "do they hold the same values?". Two
/// objects built separately are never `==`, whatever they contain. That is why classes provide an `equals` method
/// to compare content, as you saw with Strings.

public class Main {
    public static void main(String[] args) {
        Test2 ob1 = new Test2(100, 22);
        Test2 ob2 = new Test2(100, 22);
        System.out.println(ob1 == ob2);        // same object?
        System.out.println(ob1.equals(ob2));   // same values?
    }
}

class Test2 {
    int a, b;

    Test2(int i, int j) {
        a = i;
        b = j;
    }

    boolean equals(Test2 o) {
        return o.a == a && o.b == b;
    }
}

/// ### Topic 4: Copy constructor and constructor overloading together ###
/// --- Concept: -----
///
/// Java allows us to pass objects as parameters to constructors, just like with methods. This is used to copy the
/// values from one object to another, which is the copy constructor from the earlier lesson. This example also shows
/// constructor overloading, where a class has several constructors with different parameter lists.
///
/// --- Example -----

class Box {
    double width;
    double height;
    double depth;

    // Copy constructor: takes another Box object as parameter
    Box(Box ob) {
        width = ob.width;
        height = ob.height;
        depth = ob.depth;
    }

    // Constructor with all three dimensions
    Box(double w, double h, double d) {
        width = w;
        height = h;
        depth = d;
    }

    // Constructor with no dimensions (default box)
    Box() {
        width = -1;
        height = -1;
        depth = -1;
    }

    // Constructor for cube (all sides equal)
    Box(double len) {
        width = height = depth = len;
    }

    // Method to compute and return volume
    double volume() {
        return width * height * depth;
    }
}

public class Main {
    public static void main(String[] args) {
        // Create boxes using various constructors
        Box mybox1 = new Box(10, 20, 15);    // box with all dimensions
        Box mybox2 = new Box();              // default box
        Box mycube = new Box(7);             // cube with side length 7

        // Create a copy of mybox1 using the copy constructor
        Box myclone = new Box(mybox1);

        double vol;

        // Volume of mybox1
        vol = mybox1.volume();
        System.out.println("Volume of mybox1 is " + vol);

        // Volume of mybox2 (default values: -1)
        vol = mybox2.volume();
        System.out.println("Volume of mybox2 is " + vol);

        // Volume of mycube
        vol = mycube.volume();
        System.out.println("Volume of cube is " + vol);

        // Volume of myclone (same as mybox1)
        vol = myclone.volume();
        System.out.println("Volume of clone is " + vol);
    }
}

/// Four boxes, built four different ways. Java chose each constructor from the arguments: three numbers, none, one
/// number, or a Box. The "default box" of -1 shows a made-up marker value; a real program would more likely reject
/// it, as in the Encapsulation lesson.

/// ### Topic 5: Objects in arrays and lists ###
/// Because objects are passed and stored as references, a method can reach every object in an array, and changing
/// them changes the originals.

class Player {
    String name;
    int score;

    Player(String name, int score) {
        this.name = name;
        this.score = score;
    }
}

public class Main {
    static void bonus(Player[] team) {
        for (Player p : team) {
            p.score += 10;
        }
    }

    static Player best(Player[] team) {
        Player top = team[0];
        for (Player p : team) {
            if (p.score > top.score) top = p;
        }
        return top;                  // returns the very same object, not a copy
    }

    public static void main(String[] args) {
        Player[] team = { new Player("Rafi", 40), new Player("Mim", 55), new Player("Anik", 48) };

        bonus(team);
        Player star = best(team);

        System.out.println(star.name + " leads with " + star.score);
        star.score = 0;
        System.out.println(team[1].name + " now has " + team[1].score);
    }
}

/// `best` returned the very same Player that is inside the array (not a copy), so zeroing `star.score` changed the
/// array's own element.
///
/// REMEMBER: Passing an object shares it; returning an object hands out a reference, to a new one if the method
/// built it, or to an existing one if it returns something it was given. Compare content with `equals`, never `==`.

/// ============================================================================
/// YOUR TURN
///   1. Write `Rectangle` with `width` and `height`, and a method `Rectangle bigger(Rectangle other)` that returns
///      whichever has the larger area.
///   2. Write `Point add(Point other)` that returns a NEW Point with the sum, without changing either original.
///   3. Write `boolean sameAs(Student other)` comparing name and age. Test it on two separately built students.
///   4. Write a method that takes an array of `Product` objects and raises every price by 10 percent. Print the
///      array afterwards.
///   5. Explain why `new Box(1,2,3) == new Box(1,2,3)` is false.
/// ============================================================================
