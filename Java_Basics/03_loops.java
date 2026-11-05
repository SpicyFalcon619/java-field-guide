/*
 * Java Basics: Loops
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What are Loops?
///   - Basic Example
///   - For Loop
///   - While Loop
///   - Do-While Loop
///   - For-Each Loop (Enhanced For Loop)
/// ============================================================================


/// ### Topic 1: What are Loops? ###
/// Loops are used to repeat a block of code multiple times.
/// There are 4 types of loops in Java:
///    - `for` loop
///    - `while` loop
///    - `do-while` loop
///    - `for-each` loop


/// ### Topic 2: Basic Example ###
public class Main {
    public static void main(String[] args) {
        // For loop
        for (int i = 1; i <= 5; i++) {
            System.out.println("For loop: " + i);
        }

        // While loop
        int j = 1;
        while (j <= 5) {
            System.out.println("While loop: " + j);
            j++;
        }

        // Do-while loop
        int k = 1;
        do {
            System.out.println("Do-while loop: " + k);
            k++;
        } while (k <= 5);
        
        // For-each loop
        int[] marks = {85, 90, 78};

        for (int mark : marks) {
            System.out.println("Mark: " + mark);
        }
    }
}


/// ### Topic 3: For Loop ###
/// Used when we know how many times to repeat.
///
/// --- Syntax: -----
///
///     for (initialization; condition; update) {
///         // code to execute
///     }
///
/// --- Basic Example -----

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            System.out.println("For loop: " + i);
        }
    }
}

/// --- Key Points -----
///    - Runs only if the condition is `true`.
///    - Initialization → Condition check → Execution → Update → Repeat


/// ### Topic 4: While Loop ###
/// Use when the number of repetitions is not known beforehand.
///
/// --- Syntax: -----
///
///     while (condition) {
///         // code to execute
///     }
///
/// --- Basic Example -----

public class Main {
    public static void main(String[] args) {
        int j = 1;
        while (j <= 5) {
            System.out.println("While loop: " + j);
            j++;
        }
    }
}

/// --- Key Points -----
///    - Condition is checked before executing the block.
///    - Might not run at all if the condition is false from the start.


/// ### Topic 5: Do-While Loop ###
/// Use when you want the code to run at least once, even if the condition is false.
///
/// --- Syntax: -----
///
///     do {
///         // code to execute
///     } while (condition);
///
/// --- Basic Example -----

public class Main {
    public static void main(String[] args) {
        int k = 1;
        do {
            System.out.println("Do-while loop: " + k);
            k++;
        } while (k <= 5);
    }
}

/// --- Key Points -----
///    - Code runs at least once.
///    - Condition is checked after the loop body runs.


/// ### Topic 6: For-Each Loop (Enhanced For Loop) ###
/// Use to traverse arrays or collections easily.
///
/// --- Syntax: -----
///
///     for (dataType variable : arrayOrCollection) {
///         // code
///     }
///
/// --- Basic Example -----

public class Main {
    public static void main(String[] args) {
        int[] marks = {85, 90, 78};

        for (int mark : marks) {
            System.out.println("Mark: " + mark);
        }
    }
}

/// --- Limitation Example (Modification doesn’t work) -----

public class Main {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};

        for (int num : nums) {
            num = num * 2; // Doesn't affect the original array
        }

        for (int x : nums) {
            System.out.print(x + " ");
        }
    }
}

/// --- Key Points -----
///    - Best for traversing (reading values)
///    - Cannot modify array elements
///    - No access to index
