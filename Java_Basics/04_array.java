/*
 * Java Basics: Array
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - One-dimensional Array
///   - Array Input
///   - 2D Array (Matrix)
/// ============================================================================


/// ### Topic 1: One-dimensional Array ###
public class Main {
    public static void main(String[] args) {
        int[] n = {10, 20, 30, 40, 50};

        for (int i = 0; i < n.length; i++) {
            System.out.println("Element at index " + i + ": " + n[i]);
        }
    }
}


/// ### Topic 2: Array Input ###
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


/// ### Topic 3: 2D Array (Matrix) ###
/// --- Basic -----

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

/// --- Symmatric 2D Array (w/For-each Loop) -----

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

/// --- Asymmatric 2D Array (w/For-each Loop) -----

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
