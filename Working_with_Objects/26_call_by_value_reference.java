/*
 * Working with Objects: Call by value and call by reference
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What happens to a variable when you pass it to a method
///   - Call by value for primitives: the method gets a copy
///   - Call by value for objects: the method gets a copy of the address
///   - What a method can change, and what it can never change
///   - Why a method that swaps two variables does not work
/// ============================================================================


/// ### Topic 1: Key idea ###
/// When you call a method and give it a variable, what does the method actually receive? Does it get the variable
/// itself, or something else? The answer decides whether the method can change your data, and getting it wrong is
/// one of the most common sources of surprise for beginners.
///
/// NOTE: Java only uses CALL BY VALUE. Always. The method receives a COPY of whatever you pass. It looks different
/// for primitives and for objects, though, because the "value" being copied is different. That is the whole story,
/// and the rest of this lesson makes it concrete.
///
/// ANALOGY: You want a friend to read your notes.
///   - You hand over a PHOTOCOPY of the page. Your friend may scribble on it, and your page stays untouched. That is
///     what happens to a primitive.
///   - You give your friend the ADDRESS of your locker, where the notes are kept. Your friend can go and rewrite
///     the notes in your locker, and you will see the change. But if they tear up their piece of paper with the
///     address, your locker is not affected. That is what happens to an object.
///
/// JARGON: Some languages (C++, for example) can pass a variable by REFERENCE, meaning the method works on the
/// original variable itself. Java cannot. You will still hear people say "objects are passed by reference". It is a
/// common shortcut, and this lesson shows exactly what is true.

/// ### Topic 2: Call by value with primitives ###
/// When you pass a primitive (`int`, `double`, `char`, `boolean`...) to a method, a copy of the value is passed.
///
/// NOTE: Changes made inside the method do not affect the original variable.
///
/// --- Example: -----

public class Main {
    static void changeValue(int x) {
        x = 100;
    }

    public static void main(String[] args) {
        int a = 50;
        changeValue(a);
        System.out.println("Value of a: " + a);
    }
}

/// Even though `x` was changed inside `changeValue`, `a` remains the same. Inside the method, `x` is a separate
/// box that started with a copy of a's value. When the method ends, `x` disappears and `a` was never touched. (This is
/// the same idea as the Methods lesson, where each variable lives only in its own method.)

/// ### Topic 3: Call by value with objects ###
/// A variable that holds an object does not hold the object itself. It holds a REFERENCE, which is the object's
/// address in memory (as you saw in the Classes and Objects lesson). So when you pass such a variable, the value
/// that gets copied is that address.
///
/// That means: a copy of the reference is passed to the method. Both the original variable and the parameter point
/// to the SAME object, but the reference itself is a copy.
///
/// NOTE: We can change the object's fields inside the method, and everyone sees the change, because there is only one
/// object. But we cannot reassign the original reference, because the method only has a copy of it.
///
/// --- Example: Modifying object fields -----

class Student7 {
    String name;
}

public class Main {
    static void updateStudent(Student7 s) {
        s.name = "Noman";
    }

    public static void main(String[] args) {
        Student7 s1 = new Student7();
        s1.name = "Tortilla";

        updateStudent(s1);

        System.out.println(s1.name);
    }
}

/// The method modified the fields of the object, and the change was reflected outside. Both `s1` and `s` pointed
/// at the one Student, so renaming it through `s` renamed it for everybody.
///
/// --- Example: Trying to reassign the object -----

class Student8 {
    String name;
}

public class Main {
    static void changeReference(Student8 s) {
        s = new Student8();  // this new object is not seen by main()
        s.name = "Noman";
    }

    public static void main(String[] args) {
        Student8 s1 = new Student8();
        s1.name = "Tortilla";

        changeReference(s1);

        System.out.println(s1.name);
    }
}

/// Here, `s` was given a brand-new object, but that does not affect `s1` in `main()`, because only a copy of the
/// reference was passed. Picture it in two steps:
///
///     1. s1 -----> [ Tortilla ]          s also points at the same Tortilla object
///     2. s  -----> [ Noman ]             s now points at a NEW object. s1 still points at Tortilla.
///
/// Reassigning `s` moved only the copy of the address. The original `s1` is exactly where it was.

/// ### Topic 4: The swap that does not swap ###
/// A classic test of this understanding is a method that swaps two variables. It cannot work, because the method
/// only ever swaps its own copies.

public class Main {
    static void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
        System.out.println("inside swap: a = " + a + ", b = " + b);
    }

    public static void main(String[] args) {
        int x = 1;
        int y = 2;
        swap(x, y);
        System.out.println("in main:     x = " + x + ", y = " + y);
    }
}

/// Inside the method the values really were swapped, but main's `x` and `y` are untouched. The same would be true
/// for two Student variables. What DOES work is swapping the contents of the objects, because the method can reach
/// the one real object through its copy of the address:

class Box {
    int value;

    Box(int value) {
        this.value = value;
    }
}

public class Main {
    static void swapContents(Box p, Box q) {
        int temp = p.value;
        p.value = q.value;
        q.value = temp;
    }

    public static void main(String[] args) {
        Box first = new Box(1);
        Box second = new Box(2);

        swapContents(first, second);

        System.out.println("first = " + first.value + ", second = " + second.value);
    }
}

/// ### Topic 5: Arrays and Strings ###
/// Two types you use every day follow the same rule, with a twist.
///
///   - An ARRAY is an object. A method that receives an array can change its elements, and the caller sees it.
///   - A String is an object too, but it is IMMUTABLE (you cannot change its characters), so a method can never
///     change the caller's String. It can only build a new one.

public class Main {
    static void doubleAll(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = numbers[i] * 2;
        }
    }

    static void shout(String text) {
        text = text.toUpperCase();      // makes a NEW String; the caller's is untouched
    }

    public static void main(String[] args) {
        int[] data = {1, 2, 3};
        doubleAll(data);
        System.out.println(data[0] + " " + data[1] + " " + data[2]);

        String word = "hello";
        shout(word);
        System.out.println(word);
    }
}

/// --- Summary -----
///
///     What you pass          What the method gets            Can it change your variable?   Can it change the object?
///     int, double, char...   a copy of the value             no                             (no object)
///     an object, an array    a copy of the address           no (it cannot repoint yours)   yes, through the address
///     a String               a copy of the address           no                             no (Strings never change)
///
/// REMEMBER: Java always copies what you pass. For an object, what is copied is the address, so the method can
/// change the one shared object but can never change which object your variable refers to.

/// ============================================================================
/// YOUR TURN
///   1. Write `void addTen(int n)` that adds 10 to n, call it, and print the original. Explain the output.
///   2. Write `void rename(Student s)` that changes the name, and another `void replace(Student s)` that does
///      `s = new Student()`. Call both on the same student and describe what changed.
///   3. Write a method that doubles every element of an array, and call it. Why is the array changed?
///   4. Draw (on paper) the two variables and the one object for Topic 3's second example, step by step.
///   5. Predict the output before running: a method `static void reset(int[] a) { a = new int[3]; }` called on an
///      array of 5 numbers. Then run it.
/// ============================================================================
