/*
 * Working with Objects: Passing and Returning Object
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Passing Objects as Arguments
///   - Returning an Object from a Method
///   - Using Objects as Parameters
///   - Copy Constructor + Constructor Overloading
/// ============================================================================


/// ### Topic 1: Passing Objects as Arguments ###
/// In Java, objects are passed by reference — technically by value of the reference (memory
/// address).
/// This means when you pass an object to a method:
///    - You're passing a copy of the reference, not the object itself.
///    - The method can modify the object's internal state, but not the reference itself.
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


/// ### Topic 2: Returning an Object from a Method ###
/// A method can return an object just like it returns a primitive value. Often used in factory
/// methods, result methods, etc.
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


/// ### Topic 3: Using Objects as Parameters ###
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

class UsingObjectsAsParametersExample {
    public static void main(String args[]) {
        Test2 ob1 = new Test2(100, 22);
        Test2 ob2 = new Test2(100, 22);
        Test2 ob3 = new Test2(-1, -1);

        System.out.println("ob1 == ob2: " + ob1.equals(ob2));
        System.out.println("ob1 == ob3: " + ob1.equals(ob3));
    }
}


/// ### Topic 4: Copy Constructor + Constructor Overloading ###
/// --- Concept: -----
///
/// Java allows us to pass objects as parameters to constructors, just like with methods.
/// This is used to copy the values from one object to another — it's called a copy constructor.
/// Also, this example shows constructor overloading — where a class has multiple constructors with
/// different parameter lists.
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
