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
            gaussAlgorithmData.currentFirstInColumn
                    = matrix[gaussAlgorithmData.ignoreRowsAboveNeq][gaussAlgorithmData.firstColumnDifferentFromZero];

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

    private static int columnDifferentFromZero(GaussAlgorithmData gaussData) {
        for (int j = gaussData.firstColumnDifferentFromZero; j < matrix[0].length; j++) {
            if (GaussMatrix.checkColumnDifferentFromZero(j, gaussData.ignoreRowsAboveNeq)) {
                gaussData.firstColumnDifferentFromZero = j;
                return gaussData.firstColumnDifferentFromZero;
            }
        }

        return gaussData.firstColumnDifferentFromZero;
    }

    private static void ensureFirstInColumnDifferentFromZero(GaussAlgorithmData gaussData) {
        if (Objects.equals(gaussData.currentFirstInColumn.getNum(), BigInteger.ZERO)) {
            for (int i = gaussData.ignoreRowsAboveNeq + 1; i < matrix.length; i++) {
                if (!Objects.equals(matrix[i][gaussData.firstColumnDifferentFromZero].getNum(), BigInteger.ZERO)) {
                    GaussMatrix.swap(i, gaussData.ignoreRowsAboveNeq);
                    gaussData.currentFirstInColumn =
                            matrix[gaussData.ignoreRowsAboveNeq][gaussData.firstColumnDifferentFromZero];

                    gaussData.visualizedOperations
                            .add(gaussData.pass + "::swap::" + gaussData.ignoreRowsAboveNeq + "::" + i);
                    gaussData.operationsOnPass
                            .set(gaussData.pass,
                            gaussData.operationsOnPass.
                                    get(gaussData.pass) + 1);
                }
            }
        }
    }

    private static void makeFirstElementOne(GaussAlgorithmData gaussData) {
        if (!((Objects.equals(gaussData.currentFirstInColumn.getNum(), BigInteger.ONE)
                || Objects.equals(gaussData.currentFirstInColumn.getNum(), BigInteger.ZERO))
                && Objects.equals(gaussData.currentFirstInColumn.getDen(), BigInteger.ONE))) {
            Fraction factor;

            factor = new Fraction(gaussData.currentFirstInColumn.getDen(),
                    gaussData.currentFirstInColumn.getNum());
            GaussMatrix.multiply(gaussData.ignoreRowsAboveNeq, factor);

            gaussData.visualizedOperations.add(gaussData.pass + "::mult::"
                    + gaussData.ignoreRowsAboveNeq + "::" + factor);
            gaussData.operationsOnPass.set(gaussData.pass,
                    gaussData.operationsOnPass.get(gaussData.pass) + 1);
        }
    }

    private static void eliminateElementsBelowFirst(GaussAlgorithmData gaussData) {
        for (int i = gaussData.ignoreRowsAboveNeq + 1; i < matrix.length; i++) {
            if (!(Objects
                    .equals(matrix[i][gaussData.firstColumnDifferentFromZero].getNum(), BigInteger.ZERO)
                    && Objects
                    .equals(matrix[i][gaussData.firstColumnDifferentFromZero].getDen(), BigInteger.ONE))) {
                Fraction factor =
                        new Fraction(matrix[i][gaussData.firstColumnDifferentFromZero]
                                .getNum()
                                .multiply(BigInteger.valueOf(-1)),
                        matrix[i][gaussData.firstColumnDifferentFromZero].getDen());
                GaussMatrix.add(factor, gaussData.ignoreRowsAboveNeq, i);
                gaussData.visualizedOperations.add(
                        gaussData.pass + "::add::" + i + "::"
                        + gaussData.ignoreRowsAboveNeq + "::"
                                + factor
                );
                gaussData.operationsOnPass.set(gaussData.pass,
                        gaussData.operationsOnPass.get(gaussData.pass) + 1);
            }
        }
    }

    private static Fraction[][] returnDeepCopyOfMatrix() {
        return getFractions(matrix);
    }
}
