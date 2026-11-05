/*
 * Java Basics: Arrays
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What an array is, and why we need one
///   - How to create arrays, read and change items, and use the index (which starts at 0)
///   - Reading array values from the user and looping over them
///   - The out-of-bounds mistake
///   - Two-dimensional arrays (tables), including rows of different lengths
/// ============================================================================


/// ### Topic 1: Why arrays? ###
/// Say you want to store the marks of 30 students. Creating 30 separate variables, `mark1`, `mark2`, ...,
/// `mark30`, is clumsy, and you could not loop over them. An ARRAY stores many values of the SAME type under
/// one name, in a numbered row.
///
/// ANALOGY: An array is a row of lockers, each with a number on the door. The whole row has one name ("marks"),
/// and you open a particular locker by its number. Each locker holds one value, and all of them hold the same
/// kind of thing.
///
/// Two facts define an array:
///
///   - Its SIZE is fixed when you create it. You cannot make it longer afterwards.
///   - Positions are numbered from 0. The first item is at index 0, the second at index 1, and an array of 5
///     items ends at index 4.
///
/// WHY: Why start from 0? An index is really "how far from the start". The first item is zero steps away. It
/// feels odd for a week, and then it becomes second nature, and most programming languages do the same.

/// ### Topic 2: Creating and reading an array ###
/// There are two ways to create one. The first lists the values directly. Write the type, then `[]`, then the
/// name.

public class Main {
    public static void main(String[] args) {
        int[] n = {10, 20, 30, 40, 50};

        for (int i = 0; i < n.length; i++) {
            System.out.println("Element at index " + i + ": " + n[i]);
        }
    }
}

/// Each part of what you just saw:
///
///   - `int[] n` declares an array variable that holds ints.
///   - `{10, 20, 30, 40, 50}` is the list of starting values.
///   - `n[i]` reads the item at position i. The number in the square brackets is the INDEX.
///   - `n.length` is how many items there are (with no brackets, because it is not a method). Here it is 5.
///
/// So the loop runs with i = 0, 1, 2, 3, 4, and stops when i reaches 5. That is why the condition is `i <
/// n.length` and never `<=`. The last valid index is always `length - 1`.
///
/// You can also change an item by assigning to its position: `n[2] = 99;`.

public class Main {
    public static void main(String[] args) {
        String[] fruits = {"Apple", "Mango", "Banana"};

        System.out.println(fruits[0]);
        fruits[1] = "Orange";               // replace the second item
        System.out.println(fruits[1]);
        System.out.println("Count: " + fruits.length);
    }
}

/// ### Topic 3: Creating an empty array with new ###
/// If you do not know the values yet, create an array of a given size with `new`. Java fills it with defaults:
/// 0 for numbers, `false` for booleans and `null` (meaning "nothing yet") for Strings and other objects.

public class Main {
    public static void main(String[] args) {
        int[] scores = new int[4];             // four ints, all 0
        String[] names = new String[2];        // two Strings, both null

        System.out.println(scores[0]);
        System.out.println(names[0]);

        scores[0] = 70;
        scores[3] = 95;
        System.out.println(scores[0] + " " + scores[1] + " " + scores[2] + " " + scores[3]);
    }
}

/// ### Topic 4: Reading values from the user ###
/// Arrays and loops belong together. The usual pattern is a `for` loop that visits each position, and `Scanner`
/// to fill it. The second loop, a for-each, prints what we collected.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] n = new int[3];

        for (int i = 0; i < n.length; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            n[i] = scan.nextInt();
        }

        System.out.println("N :");
        // for-each loop
        for (int x : n) {
            System.out.println(x);
        }
        scan.close();
    }
}
/// @input 85 | 90 | 78

/// We asked for "number i + 1" because people count from 1, while the array counts from 0. The brackets around
/// `i + 1` make sure it is added as a number and not joined as text.
///
/// A typical task is to calculate something across the array, such as the total or the largest value.

public class Main {
    public static void main(String[] args) {
        int[] marks = {72, 95, 60, 88};

        int sum = 0;
        int max = marks[0];                 // start with the first item
        for (int m : marks) {
            sum += m;
            if (m > max) {
                max = m;
            }
        }

        System.out.println("Total: " + sum);
        System.out.println("Average: " + (double) sum / marks.length);
        System.out.println("Highest: " + max);
    }
}

/// ### Topic 5: The out-of-bounds mistake ###
/// What if you ask for an item that does not exist, such as index 5 in an array of 5? Java checks every access
/// and stops the program with an exception called `ArrayIndexOutOfBoundsException`.

public class Main {
    public static void main(String[] args) {
        int[] n = {10, 20, 30};
        System.out.println(n[0]);
        System.out.println(n[3]);
    }
}
/// @error

/// The message tells you the index you tried (3) and the length (3). It is the same off-by-one idea as in loops:
/// the valid indexes of a 3-item array are 0, 1 and 2. This is the most frequent runtime error you will meet,
/// so learn to recognise it at a glance.
///
/// TIP: The Arrays class (in `java.util`) has helpers such as `Arrays.toString(arr)` to print a whole array, and
/// `Arrays.sort(arr)` to sort it. Try them once you are comfortable with the basics.

/// ### Topic 6: Two-dimensional arrays ###
/// An array can hold other arrays. A 2D array is a table with rows and columns, and you pick a cell with two
/// indexes: `matrix[row][column]`.
///
/// ANALOGY: A cinema. Seat B4 means row B, seat 4. Two numbers are enough to find any single seat.

public class Main {
    public static void main(String[] args) {
        int[][] matrix = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 }
        };

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/// `matrix.length` is the number of rows (3). `matrix[i].length` is the number of columns in row i (also 3). The
/// outer loop walks the rows, the inner loop walks the cells of one row, and `println()` ends each line. This is
/// the nested-loop pattern from the Loops lesson at work.
///
/// Here is the same thing, but the user fills the table. Note `printf`, which prints text with placeholders: `%d`
/// is replaced by the next number you give it.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] matrix = new int[3][3];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("matrix[%d][%d]: ", i+1, j+1);
                matrix[i][j] = sc.nextInt();
            }
        }

        System.out.println("Matrix: ");

        for(int[] arr : matrix){
            for(int num : arr){
                System.out.print(num + " ");
            }
            System.out.println();
        }
        sc.close();
    }
}
/// @input 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9

/// The printing part uses two for-each loops: the outer one gives you each ROW (itself an `int[]`), and the inner
/// one gives each number in that row.

/// ### Topic 7: Rows of different lengths (jagged arrays) ###
/// Each row of a 2D array is its own array, so rows do not have to be the same length. Create the outer array
/// with only the row count, `new int[row][]`, and give every row its own size later.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Number of rows : ");
        int row = scan.nextInt();

        int[][] a = new int[row][];

        for (int i = 0; i < row; i++) {
            System.out.printf("Number of columns for row %d : ", i + 1);
            int col = scan.nextInt();
            a[i] = new int[col];
            for (int j = 0; j < col; j++) {
                System.out.printf("Enter element a[%d][%d] : ", i + 1, j + 1);
                a[i][j] = scan.nextInt();
            }
        }

        for (int rows[] : a) {
            for (int cols : rows) {
                System.out.print(cols + " ");
            }
            System.out.println();
        }
        scan.close();
    }
}
/// @input 2 | 2 | 1 | 2 | 3 | 3 | 4 | 5

/// REMEMBER: An array has a fixed size. If you need something that can grow and shrink, Java has a more flexible
/// tool called `ArrayList`, which comes later in the Collections lesson.

/// ============================================================================
/// YOUR TURN
///   1. Make an array of five numbers and print them in reverse order.
///   2. Read 5 numbers from the user and print the smallest.
///   3. Count how many items of an array are even.
///   4. Swap the first and last items of an array (you will need a temporary variable).
///   5. Search an array for a number the user types and print its index, or "not found".
///   6. Build a 3 x 3 table where cell [i][j] holds i * j, and print it.
///   7. Print the sum of each row and the sum of the diagonal of a 3 x 3 matrix.
/// ============================================================================
