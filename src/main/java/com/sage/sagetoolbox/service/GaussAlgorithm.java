package com.sage.sagetoolbox.service;

import java.util.List;

public class GaussAlgorithm {
    private static int[][][] matrix;
    public void matrixToLineLevelForm(int n, int m, List<Integer> list) {
        GaussMatrices.setMatrix(n, m, list);


    }

    private static boolean checkForLineLevelForm() {
        matrix = GaussMatrices.getMatrix();
        /* (i) All rows containing only zeros are at the bottom of the matrix. */
        /* (i) All rows containing only zeros are at the bottom of the matrix. */
        /* (i) All rows containing only zeros are at the bottom of the matrix. */
        boolean criteria1 = checkZeroesOnBottom(matrix);
        boolean criteria2 = false;
        boolean criteria3 = false;

        return criteria1 && criteria2 && criteria3;
    }

    private static boolean checkZeroesOnBottom(int[][][] matrix) {
        boolean bottomDifferentFromZero = false;

        for (int i = 0; i < matrix.length; i++) {
            if (!checkLineDifferentFromZero(i)) {
                bottomDifferentFromZero = true;
            }

            if (bottomDifferentFromZero && checkLineDifferentFromZero(i)) {
                return false;
            }

            if (!bottomDifferentFromZero && i == matrix.length - 1) {
                return true;
            }
        }

        return bottomDifferentFromZero;
    }

    private static boolean checkLineDifferentFromZero(int line) {
        boolean lineIsNotZeroes = false;
        for (int j = 0; j < matrix[line].length; j++) {

            if (matrix[line][j][0] != 0) {
                lineIsNotZeroes = true;
            }
        }

        return lineIsNotZeroes;
    }
}
