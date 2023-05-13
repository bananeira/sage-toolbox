package com.sage.sagetoolbox.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GaussMatrix {
    private static Fraction[][] matrix;

    public static void setMatrix(int m, int n, List<Integer> elements) {
        matrix = new Fraction[m][n];

        int elementPos = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (elementPos < elements.size()) {
                    matrix[i][j] = new Fraction();
                    matrix[i][j].setNum(elements.get(elementPos));
                } else {
                    matrix[i][j] = new Fraction(0, 1);
                }

                elementPos++;
            }
        }
    }

    public static Fraction[][] getMatrix() {
        return matrix;
    }

    public static void multiply(int row, Fraction factor) {
        for (int i = 0; i < matrix[row].length; i++) {
            matrix[row][i].setNum(matrix[row][i].getNum() * factor.getNum());
            matrix[row][i].setDen(matrix[row][i].getDen() * factor.getDen());

            matrix[row][i] = shortenFractionsMax(matrix[row][i]);
        }
    }

    public static void swap(int fromRow, int toRow) {
        Collections.swap(Arrays.asList(matrix), fromRow, toRow);
    }

    public static void add(Fraction multiple, int fromRow, int toRow) {
        Fraction[] temp = new Fraction[matrix[fromRow].length];

        for (int j = 0; j < matrix[fromRow].length; j++) {
            temp[j].setNum(matrix[fromRow][j].getNum());
            temp[j].setDen(matrix[fromRow][j].getDen());
        }

        for (Fraction fraction : temp) {
            fraction.setNum(fraction.getNum() * multiple.getNum());
            fraction.setDen(fraction.getDen() * multiple.getDen());
        }

        for (int i = 0; i < matrix[toRow].length; i++) {
            temp[i].setNum(temp[i].getNum() * matrix[toRow][i].getDen());

            matrix[toRow][i].setNum(matrix[toRow][i].getNum() * temp[i].getDen());
            matrix[toRow][i].setDen(matrix[toRow][i].getDen() * temp[i].getDen());

            matrix[toRow][i].setNum(matrix[toRow][i].getNum() * temp[i].getNum());

            matrix[toRow][i] = shortenFractionsMax(matrix[toRow][i]);
        }
    }

    private static int[][] deepCopy(int[][] original) {
        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    private static Fraction shortenFractionsMax(Fraction fraction) {
        Fraction toShortened = new Fraction(fraction.getNum(), fraction.getDen());
        int divisor = EuclideanAlgorithm.fastGCD(toShortened.getNum(), toShortened.getDen());

        if (divisor == 1) return fraction;

        toShortened.setNum(toShortened.getNum() / divisor);
        toShortened.setDen(toShortened.getDen() / divisor);

        return toShortened;
    }

    public static boolean checkForLineLevelForm() {
        matrix = GaussMatrix.getMatrix();
        /* (i) All rows containing only zeros are at the bottom of the matrix. */
        /* (ii) If a line does not contain only zeros,
         * then the leftmost, is a one (the leading one of this line). */
        /* (iii ->+ (ii)) For each two different lines that do not contain only zeros,
         * the leading one of the upper line is further to the left than the leading one of the lower line. */
        boolean criteria1 = checkZeroesOnBottom();
        boolean criteria2 = checkLeadingOnes();

        return criteria1 && criteria2;
    }

    private static boolean checkZeroesOnBottom() {
        boolean bottomDifferentFromZero = false;

        for (int i = 0; i < matrix.length; i++) {
            if (!checkLineDifferentFromZero(i)) {
                bottomDifferentFromZero = true;
            }

            if (bottomDifferentFromZero && checkLineDifferentFromZero(i)) {
                return false;
            }

            /*if (!bottomDifferentFromZero && i == matrix.length - 1) {
                return true;
            }*/
        }

        return true;
    }

    public static boolean checkLineDifferentFromZero(int line) {
        boolean lineIsNotZeroes = false;
        for (int j = 0; j < matrix[line].length; j++) {
            if (matrix[line][j].getNum() != 0) {
                lineIsNotZeroes = true;
                break;
            }
        }

        return lineIsNotZeroes;
    }

    public static boolean checkColumnDifferentFromZero(int column) {
        boolean columnIsNotZeroes = false;
        for (Fraction[] fractions : matrix) {
            if (fractions[column].getNum() != 0) {
                columnIsNotZeroes = true;
                break;
            }
        }

        return columnIsNotZeroes;
    }

    public static boolean checkColumnDifferentFromZero(int column, int ignoreRowsAboveEq) {
        boolean columnIsNotZeroes = false;
        for (int i = ignoreRowsAboveEq; i < matrix.length; i++) {
            if (matrix[i][column].getNum() != 0) {
                columnIsNotZeroes = true;
                break;
            }
        }

        return columnIsNotZeroes;
    }

    private static boolean checkLeadingOnes() {
        int furthest = -1;

        for (Fraction[] fractions : matrix) {
            for (int j = 0; j < fractions.length; j++) {
                if ((fractions[j].getNum() != 1
                        && fractions[j].getNum() != 0
                        || fractions[j].getDen() != 1)) {
                    return false;
                }

                if (fractions[j].getNum() == 1
                        && fractions[j].getDen() == 1
                        && j > furthest) {
                    furthest = j;
                    break;
                } else if (fractions[j].getNum() == 1
                        && fractions[j].getDen() == 1
                        && j <= furthest) {
                    return false;
                }
            }
        }

        return true;
    }
}

