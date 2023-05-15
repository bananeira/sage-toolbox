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

    public static void matrixToLineLevelForm(int m, int n, List<Integer> list) {
        GaussMatrix.setMatrix(m, n, list);
        matrix = GaussMatrix.getMatrix();

        System.out.println("starting with: " + Arrays.deepToString(GaussMatrix.getMatrix()));
        System.out.println();

        while (!GaussMatrix.checkForLineLevelForm()) {
            operationsOnPass.add(0);
            firstColumnDifferentFromZero = columnDifferentFromZero(firstColumnDifferentFromZero);
            currentFirstInColumn = matrix[ignoreRowsAboveNeq][firstColumnDifferentFromZero];

            System.out.println(Arrays.deepToString(GaussMatrix.getMatrix()));
            System.out.print("#" + operationsOnPass.get(pass));
            System.out.print(" on pass: " + pass);
            if (visualizedOperations.size() != 0) {
                System.out.println(", op: " + visualizedOperations.subList(visualizedOperations.size() - operationsOnPass.get(pass), visualizedOperations.size()));
            } else {
                System.out.println(", no ops yet");
            }
            System.out.println();

            ensureFirstInColumnDifferentFromZero();

            System.out.println(Arrays.deepToString(GaussMatrix.getMatrix()));
            System.out.print("#" + operationsOnPass.get(pass));
            System.out.print(" on pass: " + pass);
            if (visualizedOperations.size() != 0) {
                System.out.println(", op: " + visualizedOperations.subList(visualizedOperations.size() - operationsOnPass.get(pass), visualizedOperations.size()));
            } else {
                System.out.println(", no ops yet");
            }
            System.out.println();

            makeFirstElementOne();

            System.out.println(Arrays.deepToString(GaussMatrix.getMatrix()));
            System.out.print("#" + operationsOnPass.get(pass));
            System.out.print(" on pass: " + pass);
            if (visualizedOperations.size() != 0) {
                System.out.println(", op: " + visualizedOperations.subList(visualizedOperations.size() - operationsOnPass.get(pass), visualizedOperations.size()));
            } else {
                System.out.println(", no ops yet");
            }
            System.out.println();

            eliminateElementsBelowFirst();

            System.out.println(Arrays.deepToString(GaussMatrix.getMatrix()));
            System.out.print("#" + operationsOnPass.get(pass));
            System.out.print(" on pass: " + pass);
            if (visualizedOperations.size() != 0) {
                System.out.println(", op: " + visualizedOperations.subList(visualizedOperations.size() - operationsOnPass.get(pass), visualizedOperations.size()));
            } else {
                System.out.println(", no ops yet");
            }
            System.out.println();
            System.out.println("linelevelform? " + GaussMatrix.checkForLineLevelForm());
            System.out.println();
            System.out.println("sum of operations: " + operationsOnPass);
            System.out.println();

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
        if (Objects.equals(currentFirstInColumn.getNum(), BigInteger.ZERO)) {
            for (int i = ignoreRowsAboveNeq + 1; i < matrix.length; i++) {
                if (!Objects.equals(matrix[i][firstColumnDifferentFromZero].getNum(), BigInteger.ZERO)) {
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
        if (!((Objects.equals(currentFirstInColumn.getNum(), BigInteger.ONE)
                || Objects.equals(currentFirstInColumn.getNum(), BigInteger.ZERO))
                && Objects.equals(currentFirstInColumn.getDen(), BigInteger.ONE))) {
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
            if (!(Objects.equals(matrix[i][firstColumnDifferentFromZero].getNum(), BigInteger.ZERO)
                    && Objects.equals(matrix[i][firstColumnDifferentFromZero].getDen(), BigInteger.ONE))) {
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
