package com.sage.sagetoolbox.service;

import com.sage.sagetoolbox.model.GaussAlgorithmData;
import com.sage.sagetoolbox.model.GaussAlgorithmOutput;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static com.sage.sagetoolbox.service.SolveEquationSystem.getFractions;

public class GaussAlgorithm {
    private static Fraction[][] matrix;

    public static GaussAlgorithmOutput matrixToLineLevelForm(int m, int n, List<String> list) throws Exception {
        GaussAlgorithmOutput output = new GaussAlgorithmOutput();
        GaussAlgorithmData gaussAlgorithmData = new GaussAlgorithmData();

        GaussMatrix.setMatrix(m, n, list);
        matrix = GaussMatrix.getMatrix();

        gaussAlgorithmData.matrixHistory.add(returnDeepCopyOfMatrix());

        while (!GaussMatrix.checkForLineLevelForm()) {
            gaussAlgorithmData.operationsOnPass.add(0);

            gaussAlgorithmData.firstColumnDifferentFromZero = columnDifferentFromZero(gaussAlgorithmData);
            gaussAlgorithmData.currentFirstInColumn = matrix[gaussAlgorithmData.ignoreRowsAboveNeq][gaussAlgorithmData.firstColumnDifferentFromZero];

            ensureFirstInColumnDifferentFromZero(gaussAlgorithmData);
            makeFirstElementOne(gaussAlgorithmData);
            eliminateElementsBelowFirst(gaussAlgorithmData);

            if (gaussAlgorithmData.ignoreRowsAboveNeq < matrix.length - 1) {
                gaussAlgorithmData.ignoreRowsAboveNeq++;
            }

            gaussAlgorithmData.matrixHistory.add(returnDeepCopyOfMatrix());
            gaussAlgorithmData.pass++;
        }

        output.matrixHistory = gaussAlgorithmData.matrixHistory;
        output.matrixOperations = gaussAlgorithmData.visualizedOperations;
        output.operationsOnPass = gaussAlgorithmData.operationsOnPass;
        output.eqSysTranformationHistory = SolveEquationSystem.solveSystem(GaussMatrix.getMatrix());
        output.containsIllegalEquation = GaussMatrix.resultsInIllegalEquation();
        output.foundVariables = SolveEquationSystem.foundVariables;

        return output;
    }

    private static int columnDifferentFromZero(GaussAlgorithmData gaussAlgorithmData) {
        for (int j = gaussAlgorithmData.firstColumnDifferentFromZero; j < matrix[gaussAlgorithmData.firstColumnDifferentFromZero].length; j++) {
            if (GaussMatrix.checkColumnDifferentFromZero(j, gaussAlgorithmData.ignoreRowsAboveNeq)) {
                gaussAlgorithmData.firstColumnDifferentFromZero = j;
                return gaussAlgorithmData.firstColumnDifferentFromZero;
            }
        }

        return gaussAlgorithmData.firstColumnDifferentFromZero;
    }

    private static void ensureFirstInColumnDifferentFromZero(GaussAlgorithmData gaussAlgorithmData) {
        if (Objects.equals(gaussAlgorithmData.currentFirstInColumn.getNum(), BigInteger.ZERO)) {
            for (int i = gaussAlgorithmData.ignoreRowsAboveNeq + 1; i < matrix.length; i++) {
                if (!Objects.equals(matrix[i][gaussAlgorithmData.firstColumnDifferentFromZero].getNum(), BigInteger.ZERO)) {
                    GaussMatrix.swap(i, gaussAlgorithmData.ignoreRowsAboveNeq);
                    gaussAlgorithmData.currentFirstInColumn = matrix[gaussAlgorithmData.ignoreRowsAboveNeq][gaussAlgorithmData.firstColumnDifferentFromZero];

                    gaussAlgorithmData.visualizedOperations.add(gaussAlgorithmData.pass + "::swap::" + gaussAlgorithmData.ignoreRowsAboveNeq + "::" + i);
                    gaussAlgorithmData.operationsOnPass.set(gaussAlgorithmData.pass,
                            gaussAlgorithmData.operationsOnPass.get(gaussAlgorithmData.pass) + 1);
                }
            }
        }
    }

    private static void makeFirstElementOne(GaussAlgorithmData gaussAlgorithmData) {
        if (!((Objects.equals(gaussAlgorithmData.currentFirstInColumn.getNum(), BigInteger.ONE)
                || Objects.equals(gaussAlgorithmData.currentFirstInColumn.getNum(), BigInteger.ZERO))
                && Objects.equals(gaussAlgorithmData.currentFirstInColumn.getDen(), BigInteger.ONE))) {
            Fraction factor;

            factor = new Fraction(gaussAlgorithmData.currentFirstInColumn.getDen(), gaussAlgorithmData.currentFirstInColumn.getNum());
            GaussMatrix.multiply(gaussAlgorithmData.ignoreRowsAboveNeq, factor);

            gaussAlgorithmData.visualizedOperations.add(gaussAlgorithmData.pass + "::mult::" + gaussAlgorithmData.ignoreRowsAboveNeq + "::" + factor);
            gaussAlgorithmData.operationsOnPass.set(gaussAlgorithmData.pass,
                    gaussAlgorithmData.operationsOnPass.get(gaussAlgorithmData.pass) + 1);
        }
    }

    private static void eliminateElementsBelowFirst(GaussAlgorithmData gaussAlgorithmData) {
        for (int i = gaussAlgorithmData.ignoreRowsAboveNeq + 1; i < matrix.length; i++) {
            if (!(Objects.equals(matrix[i][gaussAlgorithmData.firstColumnDifferentFromZero].getNum(), BigInteger.ZERO)
                    && Objects.equals(matrix[i][gaussAlgorithmData.firstColumnDifferentFromZero].getDen(), BigInteger.ONE))) {
                Fraction factor = new Fraction(matrix[i][gaussAlgorithmData.firstColumnDifferentFromZero].getNum().multiply(BigInteger.valueOf(-1)),
                        matrix[i][gaussAlgorithmData.firstColumnDifferentFromZero].getDen());
                GaussMatrix.add(factor, gaussAlgorithmData.ignoreRowsAboveNeq, i);
                gaussAlgorithmData.visualizedOperations.add(gaussAlgorithmData.pass + "::add::" + i + "::" + gaussAlgorithmData.ignoreRowsAboveNeq + "::" + factor);
                gaussAlgorithmData.operationsOnPass.set(gaussAlgorithmData.pass,
                        gaussAlgorithmData.operationsOnPass.get(gaussAlgorithmData.pass) + 1);
            }
        }
    }

    private static Fraction[][] returnDeepCopyOfMatrix() {
        return getFractions(matrix);
    }
}
