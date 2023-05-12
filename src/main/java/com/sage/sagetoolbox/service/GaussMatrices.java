package com.sage.sagetoolbox.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GaussMatrices {
    private static int[][][] matrix;

    public static void setMatrix(int m, int n, List<Integer> elements) {
        matrix = new int[m][n][2];

        int elementPos = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (elementPos < elements.size()) {
                    matrix[i][j][0] = elements.get(elementPos);
                }

                matrix[i][j][1] = 1;

                elementPos++;
            }
        }
    }

    public static int[][][] getMatrix() {
        return matrix;
    }

    public static void mult(int row, int[] factor) {
        for (int i = 0; i < matrix[row].length; i++) {
            matrix[row][i][0] *= factor[0];
            matrix[row][i][1] *= factor[1];

            matrix[row][i] = shortenFracsMax(matrix[row][i]);
        }
    }

    public static void swap(int fromRow, int toRow) {
        Collections.swap(Arrays.asList(matrix), fromRow, toRow);
    }

    public static void add(int[] multiple, int fromRow, int toRow) {
        int[][] temp = deepCopy(matrix[fromRow]);

        for (int i = 0; i < temp.length; i++) {
            temp[i][0] *= multiple[0];
            temp[i][1] *= multiple[1];
        }

        for (int i = 0; i < matrix[toRow].length; i++) {
            temp[i][0] *= matrix[toRow][i][1];

            matrix[toRow][i][0] *= temp[i][1];
            matrix[toRow][i][1] *= temp[i][1];

            matrix[toRow][i][0] += temp[i][0];

            matrix[toRow][i] = shortenFracsMax(matrix[toRow][i]);
        }
    }

    private static int[][] deepCopy(int[][] original) {
        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    private static int[] shortenFracsMax(int[] fraction) {
        int[] toShortened = Arrays.copyOf(fraction, fraction.length);
        int divisor = EuclideanAlgorithm.fastGCD(toShortened[0], toShortened[1]);

        return divisor != 1 ? Arrays.stream(toShortened).map(elem -> elem / divisor).toArray() : fraction;
    }
}
