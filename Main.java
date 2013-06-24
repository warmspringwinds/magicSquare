package com.company;

import java.util.Scanner;

public class Main
{
    public  static void printIntMatrix(int[][] matrix)
    {
        System.out.print("\n");

        for (int row = 0; row < matrix.length; row++)
        {
            for (int column = 0; column < matrix[row].length; column++)
            {
                System.out.print(matrix[row][column]);
                System.out.print(" ");
            }

            System.out.print("\n");
        }
    }

    public static void createMagicSquare(int[][] matrix, int n)
    {
        if (n%2 == 0)
        {
            if (n%4 == 0)
            {
                Main.doublyEvenOrderMagicSquare(matrix, n);
            }
            else
            {
                Main.singlyEvenOrderMagicSquare(matrix, n);
            }
        }
        else
        {
            Main.oddSizeMagicSquare(matrix, n);
        }
    }

    public static void doublyEvenOrderMagicSquare(int[][] matrix, int n)
    {
        int [][] I = new int[n][n];
        int [][] J = new int[n][n];

        int index = 1;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                I[i][j] = ((i+1)%4)/2;
                J[j][i] = ((i+1)%4)/2;
                matrix[i][j] = index;
                index++;
            }
        }

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (I[i][j] == J[i][j])
                {
                    matrix[i][j] = n*n + 1 - matrix[i][j];
                }
            }
        }
    }

    public static void oddSizeMagicSquare(int[][] matrix, int n)
    {
        int amountOfSteps = n*n;
        int currentRow = 0;
        int currentColumn = n/2;

        matrix[0][currentColumn] = 1;

        for (int step = 2; step <= amountOfSteps; step++)
        {
            int possibleNextRow = currentRow - 1;
            int possibleNextColumn = currentColumn + 1;

            if (possibleNextRow < 0)
            {
                possibleNextRow = n-1;
            }

            if (possibleNextColumn >= n)
            {
                possibleNextColumn = 0;
            }

            if ( matrix[possibleNextRow][possibleNextColumn] == 0 )
            {
                currentRow = possibleNextRow;
                currentColumn = possibleNextColumn;
                matrix[currentRow][currentColumn] = step;
            }
            else
            {
                currentRow = currentRow + 1;
                matrix[currentRow][currentColumn] = step;
            }
        }
    }

    public static void singlyEvenOrderMagicSquare(int[][] matrix, int n)
    {
        int subMatrixOrder = n/2;
        //n = 4*k + 2
        int k = n/4;

        //There is no 2x2 magic square
        if (n == 2)
        {
            return;
        }

        //Fill up A sub matrix
        Main.oddSizeMagicSquare(matrix, subMatrixOrder);

        for (int i = 0; i < subMatrixOrder; i++)
        {
            for (int j = 0; j < subMatrixOrder; j++)
            {
                //Fill up B sub matrix
                matrix[i + subMatrixOrder][j + subMatrixOrder] = matrix[i][j] + subMatrixOrder*subMatrixOrder;
                //Fill up C sub matrix
                matrix[i][j + subMatrixOrder] = matrix[i][j] + 2*subMatrixOrder*subMatrixOrder;
                //Fill up D sub matrix
                matrix[i + subMatrixOrder][j] = matrix[i][j] + 3*subMatrixOrder*subMatrixOrder;
            }
        }

        //Exchange the leftmost n columns in sub-square A with the corresponding columns of sub-square D
        for (int i = 0; i < k; i++)
        {
            for (int j = 0; j < subMatrixOrder; j++)
            {
                int swapVar = matrix[j][i];
                matrix[j][i] = matrix[subMatrixOrder + j][i];
                matrix[subMatrixOrder + j][i] = swapVar;
            }
        }

        //Exchange the rightmost n-1 columns in sub-square C with the corresponding columns of sub-square B
        for (int i = 0; i < k-1; i++)
        {
            for (int j = 0; j < subMatrixOrder; j++)
            {
                int swapVar = matrix[j][n-1-i];
                matrix[j][i] = matrix[subMatrixOrder + j][n-1-i];
                matrix[subMatrixOrder + j][n-1-i] = swapVar;
            }
        }

        //Exchange the middle cell of the leftmost column of sub-square A with the corresponding cell of sub-square D
        int middle = subMatrixOrder/2;
        int swapVar = matrix[middle][0];
        matrix[middle][0] = matrix[subMatrixOrder + middle][0];
        matrix[subMatrixOrder + middle][0] = swapVar;

        //Exchange the central cell in sub-square A with the corresponding cell of sub-square D
        swapVar = matrix[middle][middle];
        matrix[middle][middle] = matrix[subMatrixOrder + middle][middle];
        matrix[subMatrixOrder + middle][middle] = swapVar;
    }

    public static void main(String[] args)
    {
      System.out.print("Enter size of magic square: \n");

        Scanner user_input = new Scanner( System.in );

        String tmp;
        tmp = user_input.next();

        int n = Integer.parseInt(tmp);

        if (n == 2)
        {
            System.out.print("There is no magic square of this size.\n");
            return;
        }

        int [][] matrix = new int[n][n];
        Main.createMagicSquare(matrix, n);
        Main.printIntMatrix(matrix);
    }
}
