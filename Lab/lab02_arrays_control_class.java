/*
 * Lab: Lab 2: Arrays, control statements, class and object
 */

/// ============================================================================
/// WHAT YOU WILL PRACTISE
///   - Decisions with if, else if and switch
///   - Loops: for, while, nested loops
///   - Arrays: reading, searching, finding largest, sorting
///   - Writing your first class and creating objects
/// ============================================================================


/// ### Task 1: Grade calculator ###
/// Read marks (0 to 100) and print the grade: 80 or more is A, 70 to 79 is B, 60 to 69 is C, 40 to 59 is D, below 40 is F.
/// Print "Invalid" for anything outside 0 to 100.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Marks: ");
        int marks = scan.nextInt();

        if (marks < 0 || marks > 100) {
            System.out.println("Invalid");
        } else if (marks >= 80) {
            System.out.println("Grade A");
        } else if (marks >= 70) {
            System.out.println("Grade B");
        } else if (marks >= 60) {
            System.out.println("Grade C");
        } else if (marks >= 40) {
            System.out.println("Grade D");
        } else {
            System.out.println("Grade F");
        }
        scan.close();
    }
}
/// @input 74
/// @input 130

/// ### Task 2: Prime numbers ###
/// Print all the prime numbers from 2 to 50. A number is prime if no number from 2 up to its square root divides it.

public class Main {
    public static void main(String[] args) {
        for (int n = 2; n <= 50; n++) {
            boolean prime = true;
            for (int d = 2; d * d <= n; d++) {
                if (n % d == 0) {
                    prime = false;
                    break;
                }
            }
            if (prime) {
                System.out.print(n + " ");
            }
        }
        System.out.println();
    }
}

/// ### Task 3: Factorial and Fibonacci ###
/// Print the factorial of 6, then the first 10 Fibonacci numbers (each number is the sum of the two before it).

public class Main {
    public static void main(String[] args) {
        long factorial = 1;
        for (int i = 2; i <= 6; i++) {
            factorial *= i;
        }
        System.out.println("6! = " + factorial);

        int a = 0, b = 1;
        for (int i = 0; i < 10; i++) {
            System.out.print(a + " ");
            int next = a + b;
            a = b;
            b = next;
        }
        System.out.println();
    }
}

/// ### Task 4: A pyramid ###
/// Print a pyramid of height 4:
///
///        *
///       ***
///      *****
///     *******

public class Main {
    public static void main(String[] args) {
        int height = 4;
        for (int row = 1; row <= height; row++) {
            for (int space = 1; space <= height - row; space++) {
                System.out.print(" ");
            }
            for (int star = 1; star <= 2 * row - 1; star++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}

/// ### Task 5: Statistics of an array ###
/// Read 5 numbers into an array. Print the largest, the smallest, the sum and the average.

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] numbers = new int[5];

        for (int i = 0; i < numbers.length; i++) {
            System.out.print("Number " + (i + 1) + ": ");
            numbers[i] = scan.nextInt();
        }

        int max = numbers[0];
        int min = numbers[0];
        int sum = 0;
        for (int n : numbers) {
            if (n > max) max = n;
            if (n < min) min = n;
            sum += n;
        }

        System.out.println("Largest: " + max);
        System.out.println("Smallest: " + min);
        System.out.println("Sum: " + sum);
        System.out.println("Average: " + (double) sum / numbers.length);
        scan.close();
    }
}
/// @input 12 | 5 | 40 | 7 | 21

/// ### Task 6: Search and reverse ###
/// Given an array, report whether a value is in it and at which index, then print the array reversed.

public class Main {
    public static void main(String[] args) {
        int[] data = {4, 8, 15, 16, 23, 42};
        int target = 16;

        int found = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                found = i;
                break;
            }
        }
        System.out.println(found >= 0 ? target + " found at index " + found : target + " not found");

        for (int i = data.length - 1; i >= 0; i--) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }
}

/// ### Task 7: Sort an array (bubble sort) ###
/// Sort an array in ascending order by repeatedly swapping neighbours that are out of order.

public class Main {
    public static void main(String[] args) {
        int[] a = {64, 25, 12, 22, 11};

        for (int pass = 0; pass < a.length - 1; pass++) {
            for (int i = 0; i < a.length - 1 - pass; i++) {
                if (a[i] > a[i + 1]) {
                    int temp = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = temp;
                }
            }
        }

        System.out.println(java.util.Arrays.toString(a));
    }
}

/// ### Task 8: A 2D array ###
/// Create a 3 by 3 matrix, print it, and print the sum of the main diagonal.

public class Main {
    public static void main(String[] args) {
        int[][] m = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        int diagonal = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
            diagonal += m[i][i];
        }
        System.out.println("Diagonal sum: " + diagonal);
    }
}

/// ### Task 9: Your first class: Student ###
/// Write a class `Student` with a name, an ID and marks in three subjects. Add a method `average()` and a method
/// `display()`. In `main`, create two students and show their details.

class Student {
    String name;
    int id;
    int[] marks = new int[3];

    Student(String name, int id, int m1, int m2, int m3) {
        this.name = name;
        this.id = id;
        marks[0] = m1;
        marks[1] = m2;
        marks[2] = m3;
    }

    double average() {
        return (marks[0] + marks[1] + marks[2]) / 3.0;
    }

    void display() {
        System.out.printf("%s (ID %d): average %.1f%n", name, id, average());
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("Rafi", 101, 80, 75, 90);
        Student s2 = new Student("Mim", 102, 60, 85, 70);

        s1.display();
        s2.display();
    }
}

/// ### Task 10: An array of objects ###
/// Keep three students in an array and print the name of the one with the highest average.

class Pupil {
    String name;
    double average;

    Pupil(String name, double average) {
        this.name = name;
        this.average = average;
    }
}

public class Main {
    public static void main(String[] args) {
        Pupil[] class1 = {
            new Pupil("Rafi", 81.7),
            new Pupil("Mim", 71.7),
            new Pupil("Anik", 90.2)
        };

        Pupil best = class1[0];
        for (Pupil p : class1) {
            if (p.average > best.average) {
                best = p;
            }
        }
        System.out.println("Top student: " + best.name + " (" + best.average + ")");
    }
}

/// ### Check yourself ###
///   - Why does the prime check stop at `d * d <= n`?
///   - What is the difference between `break` and `continue`?
///   - Why is an array of objects initially full of `null`s?

/// ### Try next ###
///   1. Count the vowels in a word.
///   2. Print the multiplication tables from 1 to 10 in a grid.
///   3. Find the second largest number in an array.
///   4. Add a method to Student that returns the highest of the three marks.
