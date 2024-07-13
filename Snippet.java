import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


public class Snippet {
     public static void main(String[] args) {
       //  int n = 50;
       //  String fileName = "data.csv";
       //  double[][] A = readMatrixFromCSV(fileName, n);
       //  double[][] A_inv = findMatrixInverse(A, n);
            int n = 20;
        double[][] A = generateRandomMatrix(n, n);
        double[][] A_inv = findMatrixInverse(A, n);
        printMatrix(A, n);
       // checks if the Matrix is Non-Singular or if it is Square
       //it should be Non-singualr and a Square Matrix
        if (A_inv != null) {
            System.out.println("Inverse of the given matrix:");
            printMatrix(A_inv, n);
        } else {
            System.out.println("Matrix is singular or not square.");
        }
    }


    // Reads and loads the matrix from the given CSV dataset
    public static double[][] readMatrixFromCSV(String fileName, int n) {
        double[][] matrix = new double[n][n];

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 0; col < n; col++) {
                    matrix[row][col] = Double.parseDouble(values[col]);
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matrix;
    }

    // Generates a Rondom matrix 

    public static double[][] generateRandomMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        Random rand = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextDouble() *10;
            }
        }

        return matrix;
    }

    public static void printMatrix(double[][] matrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    //FInds a Inverse of a matric A using LUdecomposition , forwardSubstitution and Backwardsubsitutuion methods
    public static double[][] findMatrixInverse(double[][] A, int n) {
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];

        if (!LUdecomposition(A, L, U, n)) {
            return null;
        }

        double[][] A_inv = new double[n][n];
        for (int i = 0; i < n; i++) {
            double[] B = new double[n];
            B[i] = 1;

            double[] Y = forwardSubstitution(L, B, n);
            double[] X = backwardSubstitution(U, Y, n);

            for (int j = 0; j < n; j++) {
                A_inv[j][i] = X[j];
            }
        }

        return A_inv;
    }

    public static boolean LUdecomposition(double[][] A, double[][] L, double[][] U, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j < i) {
                    L[j][i] = 0;
                } else {
                    L[j][i] = A[j][i];
                    for (int k = 0; k < i; k++) {
                        L[j][i] = L[j][i] - L[j][k] * U[k][i];
                    }
                }
            }

            for (int j = 0; j < n; j++) {
                if (j < i) {
                    U[i][j] = 0;
                } else if (i == j) {
                    U[i][j] = 1;
                } else {
                    U[i][j] = A[i][j] / L[i][i];
                    for (int k = 0; k < i; k++) {
                        U[i][j] = U[i][j] - ((L[i][k] * U[k][j]) / L[i][i]);
                    }
                }
            }
        }

        // Check if the matrix is singular
        for (int i = 0; i < n; i++) {
            if (L[i][i] == 0) {
                return false;
            }
        }

        return true;
    }

    public static double[] forwardSubstitution(double[][] L, double[] B, int n) {
        double[] Y = new double[n];
        for (int i = 0; i < n; i++) {
            Y[i] = B[i] / L[i][i];
            for (int j = 0; j < i; j++) {
                Y[i] -= Y[j] * L[i][j] / L[i][i];
            }
        }
        return Y;
    }

    public static double[] backwardSubstitution(double[][] U, double[] Y, int n) {
        double[] X = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            X[i] = Y[i];
            for (int j = i + 1; j < n; j++) {
                X[i] -= X[j] * U[i][j];
            }
        }
        return X;
    }
}

            		