package com.sage.sagetoolbox.service;

import java.math.BigInteger;
import java.util.*;

import static java.lang.Math.abs;

public class GaussMatrix {
    private static Fraction[][] matrix;

    public static void setMatrix(int m, int n, List<String> elements) throws Exception {
        matrix = new Fraction[abs(m)][abs(n)];

        int elementPos = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = new Fraction();

                if (elementPos < elements.size()) {
                    try {
                        String[] unformattedFraction = elements.get(elementPos).split("/",2);

                        if (unformattedFraction.length == 2 && !new BigInteger(unformattedFraction[1]).equals(BigInteger.ZERO)) {
                            matrix[i][j].setNum(new BigInteger(unformattedFraction[0]));
                            matrix[i][j].setDen(new BigInteger(unformattedFraction[1]));

                        } else {
                            matrix[i][j].setNum(new BigInteger(elements.get(elementPos)));
                        }
                    } catch (NumberFormatException numberFormatException) {
                        throw new Exception("Fractions may only contain mathematical Integers, separators '/' between Integers and may not divide by zero (not '/0').");
                    }
                } else {
                    matrix[i][j] = new Fraction();
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
            matrix[row][i].multiply(factor);
        }
    }

    public static void swap(int fromRow, int toRow) {
        Collections.swap(Arrays.asList(matrix), fromRow, toRow);
    }

    public static void add(Fraction factor, int fromRow, int toRow) {
        Fraction[] temp = Arrays.copyOf(matrix[fromRow], matrix[fromRow].length);

        for (int j = 0; j < matrix[fromRow].length; j++) {
            temp[j] = new Fraction(matrix[fromRow][j].getNum(), matrix[fromRow][j].getDen());

            temp[j].setNum(matrix[fromRow][j].getNum());
            temp[j].setDen(matrix[fromRow][j].getDen());
        }

        for (Fraction fraction : temp) {
            fraction.multiply(factor);
        }

        for (int i = 0; i < matrix[toRow].length; i++) {
            matrix[toRow][i].add(temp[i]);
        }
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
        }

        return true;
    }

    public static boolean checkLineDifferentFromZero(int line) {
        boolean lineIsNotZeroes = false;
        for (int j = 0; j < matrix[line].length; j++) {
            if (!Objects.equals(matrix[line][j].getNum(), BigInteger.ZERO)) {
                lineIsNotZeroes = true;
                break;
            }
        }

        return lineIsNotZeroes;
    }

    public static boolean checkColumnDifferentFromZero(int column) {
        boolean columnIsNotZeroes = false;
        for (Fraction[] fractions : matrix) {
            if (!Objects.equals(fractions[column].getNum(), BigInteger.ZERO)) {
                columnIsNotZeroes = true;
                break;
            }
        }

        return columnIsNotZeroes;
    }

    public static boolean checkColumnDifferentFromZero(int column, int ignoreRowsAboveNeq) {
        boolean columnIsNotZeroes = false;
        for (int i = ignoreRowsAboveNeq; i < matrix.length; i++) {
            if (!Objects.equals(matrix[i][column].getNum(), BigInteger.ZERO)) {
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
                if ((!Objects.equals(fractions[j].getNum(), BigInteger.ONE)
                        && !Objects.equals(fractions[j].getNum(), BigInteger.ZERO)
                        || !Objects.equals(fractions[j].getDen(), BigInteger.ONE))) {
                    return false;
                }

                if (Objects.equals(fractions[j].getNum(), BigInteger.ONE)
                        && Objects.equals(fractions[j].getDen(), BigInteger.ONE)
                        && j > furthest) {
                    furthest = j;
                    break;
                } else if (Objects.equals(fractions[j].getNum(), BigInteger.ONE)
                        && Objects.equals(fractions[j].getDen(), BigInteger.ONE)
                        && j <= furthest) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int getPosOfLeadingOne(int row) {
        for (int j = 0; j < matrix[row].length; j++) {
            if (Objects.equals(matrix[row][j].getDen(), BigInteger.ONE)
                    && Objects.equals(matrix[row][j].getNum(), BigInteger.ONE)) return j;
        }

        return -1;
    }

    public static boolean resultsInIllegalEquation() {
        boolean rowFound = false;
        int row = -1;

        for (int i = matrix.length - 1; i >= 0; i--) {
            if (rowFound) break;

            for (int j = 0; j < matrix[i].length; j++) {
                if (!Objects.equals(matrix[i][j].getNum(), BigInteger.ZERO)) {
                    rowFound = true;
                    row = i;
                    break;
                }
            }
        }

        if (row != -1 && checkColumnDifferentFromZero(matrix[row].length - 1)) {
            for (int j = 0; j < matrix[row].length - 1; j++) {
                if (!Objects.equals(matrix[row][j].getNum(), BigInteger.ZERO)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public static boolean matrixExceedingBarrier(int barrier) {
        for (Fraction[] rows : matrix) {
            for (Fraction fraction : rows) {
                if (fraction.getDen().compareTo(BigInteger.valueOf(barrier)) > 0
                        || fraction.getNum().compareTo(BigInteger.valueOf(barrier)) > 0
                        || fraction.getNum().compareTo(BigInteger.valueOf(-barrier)) < 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean matrixNotEmpty() {
        for (Fraction[] rows : matrix) {
            for (Fraction fraction : rows) {
                if (fraction.getNum().compareTo(BigInteger.ZERO) != 0) {
                    return true;
                }
            }
        }

        return false;
    }
}

