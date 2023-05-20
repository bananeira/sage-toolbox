package com.sage.sagetoolbox.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SolveEquationSystem {
    public static List<Integer> foundVariables;
    public static List<Fraction[][]> solveSystem(Fraction[][] augmentedMatrix) {
        foundVariables = new ArrayList<>();
        List<Fraction[][]> transformationHistory = new ArrayList<>();
        Fraction[][] expandedAugmentedSystem = new Fraction[augmentedMatrix.length][(augmentedMatrix[0].length * 2) - 1];

        for (int i = 0; i < expandedAugmentedSystem.length; i++) {
            for (int j = 0; j < expandedAugmentedSystem[i].length; j++) {
                if (j < augmentedMatrix[i].length) {
                    expandedAugmentedSystem[augmentedMatrix.length - 1 - i][j] =
                            new Fraction(augmentedMatrix[i][j].getNum(), augmentedMatrix[i][j].getDen());
                } else {
                    expandedAugmentedSystem[i][j] = new Fraction();
                }
            }
        }

        transformationHistory.add(returnDeepCopyOfMatrix(expandedAugmentedSystem));

        for (int i = 0; i < augmentedMatrix.length; i++) {
            for (int j = 0; j < augmentedMatrix[i].length - 1; j++) {
                if (j != GaussMatrix.getPosOfLeadingOne(augmentedMatrix.length - 1 - i)) {
                    Fraction summand = expandedAugmentedSystem[i][j];
                    summand.multiply(BigInteger.valueOf(-1));
                    expandedAugmentedSystem[i][j] = new Fraction();
                    expandedAugmentedSystem[i][j + augmentedMatrix[i].length].add(summand);
                }
            }
        }

        transformationHistory.add(returnDeepCopyOfMatrix(expandedAugmentedSystem));

        for (int i = 0; i < expandedAugmentedSystem.length; i++) {
            for (int j = augmentedMatrix[i].length; j < expandedAugmentedSystem[i].length; j++) {
                if (rowIsZero(expandedAugmentedSystem, i) || (i - 1) < 0) {
                    break;
                }

                substituteRow(augmentedMatrix, expandedAugmentedSystem, i, j);
            }
        }
        transformationHistory.add(returnDeepCopyOfMatrix(expandedAugmentedSystem));

        for (int i = 0; i < expandedAugmentedSystem.length; i++) {
            foundVariables.add(getPosOfLeadingOne(expandedAugmentedSystem, i));
        }

        return transformationHistory;
    }

    private static void substituteRow(Fraction[][] augmentedMatrix, Fraction[][] expandedAugmentedSystem, int i, int j) {
        for (int k = 0; k < i; k++) {
            int previousLeadingOnePosEquiv = getPosOfLeadingOne(expandedAugmentedSystem, k) + augmentedMatrix[i].length;
            if (previousLeadingOnePosEquiv == j) {
                Fraction before = new Fraction(expandedAugmentedSystem[i][j]);
                for (int l = augmentedMatrix[i].length - 1; l < expandedAugmentedSystem[i].length; l++) {
                    Fraction scalingFraction = new Fraction(expandedAugmentedSystem[i][j].getNum(), expandedAugmentedSystem[i][j].getDen());
                    scalingFraction.multiply(expandedAugmentedSystem[k][l]);
                    expandedAugmentedSystem[i][l].add(scalingFraction);
                }
                expandedAugmentedSystem[i][j].subtract(before);
            }
        }
    }

    public static boolean rowIsZero(Fraction[][] augmentedMatrix, int row) {
        for (int j = 0; j < augmentedMatrix[row].length; j++) {
            if (Objects.equals(augmentedMatrix[row][j].getNum(), BigInteger.ZERO)) return false;
        }

        return true;
    }

    private static int getPosOfLeadingOne(Fraction[][] matrix, int row) {
        for (int j = 0; j < matrix[row].length; j++) {
            if (Objects.equals(matrix[row][j].getDen(), BigInteger.ONE)
                    && Objects.equals(matrix[row][j].getNum(), BigInteger.ONE)) return j;
        }

        return -1;
    }

    private static Fraction[][] returnDeepCopyOfMatrix(Fraction[][] matrix) {
        return getFractions(matrix);
    }

    static Fraction[][] getFractions(Fraction[][] matrix) {
        Fraction[][] newMatrix = new Fraction[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                newMatrix[i][j] = new Fraction(matrix[i][j]);
            }
        }

        return newMatrix;
    }
}
