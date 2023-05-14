package com.sage.sagetoolbox.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GaussAlgorithm {
    private static Fraction[][] matrix;
    private static int firstColumnDifferentFromZero;
    private static int ignoreRowsAboveNeq = 0;
    private static Fraction currentFirstInColumn;
    private static final List<String> visualizedOperations = new ArrayList<>();
    private static final List<Integer> operationsOnPass = new ArrayList<>();
    private static int pass = 0;

    public static void matrixToLineLevelForm(int n, int m, List<Integer> list) {
        GaussMatrix.setMatrix(n, m, list);
        matrix = GaussMatrix.getMatrix();

        while (!GaussMatrix.checkForLineLevelForm()) {
            operationsOnPass.add(0);
            firstColumnDifferentFromZero = columnDifferentFromZero(firstColumnDifferentFromZero);
            currentFirstInColumn = matrix[ignoreRowsAboveNeq][firstColumnDifferentFromZero];
            ensureFirstInColumnDifferentFromZero();
            makeFirstElementOne();
            eliminateElementsBelowFirst();
            if (ignoreRowsAboveNeq < matrix.length - 1) {
                ignoreRowsAboveNeq++;
            }
            pass++;
        }
    }

    private static int columnDifferentFromZero(int startColumn) {
        for (int j = firstColumnDifferentFromZero; j < matrix[firstColumnDifferentFromZero].length; j++) {
            if (GaussMatrix.checkColumnDifferentFromZero(j, ignoreRowsAboveNeq)) {
                startColumn = j;
                return startColumn;
            }
        }

        return startColumn;
    }

    private static void ensureFirstInColumnDifferentFromZero() {
        if (Objects.equals(currentFirstInColumn.getNum(), BigInteger.valueOf(0))) {
            for (int i = ignoreRowsAboveNeq + 1; i < matrix.length; i++) {
                if (!Objects.equals(matrix[i][firstColumnDifferentFromZero].getNum(), BigInteger.valueOf(0))) {
                    GaussMatrix.swap(i, ignoreRowsAboveNeq);
                    currentFirstInColumn = matrix[ignoreRowsAboveNeq][firstColumnDifferentFromZero];

                    visualizedOperations.add(pass + "::swap::" + ignoreRowsAboveNeq + "::" + i);
                    operationsOnPass.set(pass,
                            operationsOnPass.get(pass) + 1);
                }
            }
        }
    }

    private static void makeFirstElementOne() {
        if (!((Objects.equals(currentFirstInColumn.getNum(), BigInteger.valueOf(1))
                || Objects.equals(currentFirstInColumn.getNum(), BigInteger.valueOf(0)))
                && Objects.equals(currentFirstInColumn.getDen(), BigInteger.valueOf(1)))) {
            Fraction factor;

            factor = new Fraction(currentFirstInColumn.getDen(), currentFirstInColumn.getNum());
            GaussMatrix.multiply(ignoreRowsAboveNeq, factor);

            visualizedOperations.add(pass + "::mult::" + factor + "::" + ignoreRowsAboveNeq);
            operationsOnPass.set(pass,
                    operationsOnPass.get(pass) + 1);
        }
    }

    private static void eliminateElementsBelowFirst() {
        for (int i = ignoreRowsAboveNeq + 1; i < matrix.length; i++) {
            if (!(Objects.equals(matrix[i][firstColumnDifferentFromZero].getNum(), BigInteger.valueOf(0))
                    && Objects.equals(matrix[i][firstColumnDifferentFromZero].getDen(), BigInteger.valueOf(1)))) {
                Fraction factor = new Fraction(matrix[i][firstColumnDifferentFromZero].getNum().multiply(BigInteger.valueOf(-1)),
                        matrix[i][firstColumnDifferentFromZero].getDen());
                GaussMatrix.add(factor, ignoreRowsAboveNeq, i);
                visualizedOperations.add(pass + "::add::" + factor + "::" + ignoreRowsAboveNeq + "::" + i);
                operationsOnPass.set(pass,
                        operationsOnPass.get(pass) + 1);
            }
        }
    }
}
