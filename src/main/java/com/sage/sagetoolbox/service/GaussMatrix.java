package com.sage.sagetoolbox.service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;

public class GaussMatrix {
    private static Fraction[][] matrix;

    public static void setMatrix(int m, int n, List<Integer> elements) {
        matrix = new Fraction[abs(m)][abs(n)];

        int elementPos = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (elementPos < elements.size()) {
                    matrix[i][j] = new Fraction();
                    matrix[i][j].setNum(BigInteger.valueOf(elements.get(elementPos)));
                } else {
                    matrix[i][j] = new Fraction(BigInteger.valueOf(0), BigInteger.valueOf(1));
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
            matrix[row][i].setNum(matrix[row][i].getNum().multiply(factor.getNum()));
            matrix[row][i].setDen(matrix[row][i].getDen().multiply(factor.getDen()));

            matrix[row][i].shortenFractionsMax();
        }
    }

    public static void swap(int fromRow, int toRow) {
        Collections.swap(Arrays.asList(matrix), fromRow, toRow);
    }

    public static void add(Fraction multiple, int fromRow, int toRow) {
        Fraction[] temp = new Fraction[matrix[fromRow].length];

        for (int j = 0; j < matrix[fromRow].length; j++) {
            temp[j] = new Fraction(matrix[fromRow][j].getNum(), matrix[fromRow][j].getDen());

            temp[j].setNum(matrix[fromRow][j].getNum());
            temp[j].setDen(matrix[fromRow][j].getDen());
        }

        for (Fraction fraction : temp) {
            fraction.setNum(fraction.getNum().multiply(multiple.getNum()));
            fraction.setDen(fraction.getDen().multiply(multiple.getDen()));
        }

        for (int i = 0; i < matrix[toRow].length; i++) {
            temp[i].setNum(temp[i].getNum().multiply(matrix[toRow][i].getDen()));

            matrix[toRow][i].setNum(matrix[toRow][i].getNum().multiply(temp[i].getDen()));
            matrix[toRow][i].setDen(matrix[toRow][i].getDen().multiply(temp[i].getDen()));

            matrix[toRow][i].setNum(matrix[toRow][i].getNum().add(temp[i].getNum()));

            matrix[toRow][i].shortenFractionsMax();
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

            /*if (!bottomDifferentFromZero && i == matrix.length - 1) {
                return true;
            }*/
        }

        return true;
    }

    public static boolean checkLineDifferentFromZero(int line) {
        boolean lineIsNotZeroes = false;
        for (int j = 0; j < matrix[line].length; j++) {
            if (!Objects.equals(matrix[line][j].getNum(), BigInteger.valueOf(0))) {
                lineIsNotZeroes = true;
                break;
            }
        }

        return lineIsNotZeroes;
    }

    public static boolean checkColumnDifferentFromZero(int column) {
        boolean columnIsNotZeroes = false;
        for (Fraction[] fractions : matrix) {
            if (!Objects.equals(fractions[column].getNum(), BigInteger.valueOf(0))) {
                columnIsNotZeroes = true;
                break;
            }
        }

        return columnIsNotZeroes;
    }

    public static boolean checkColumnDifferentFromZero(int column, int ignoreRowsAboveNeq) {
        boolean columnIsNotZeroes = false;
        for (int i = ignoreRowsAboveNeq; i < matrix.length; i++) {
            if (!Objects.equals(matrix[i][column].getNum(), BigInteger.valueOf(0))) {
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
                if ((!Objects.equals(fractions[j].getNum(), BigInteger.valueOf(1))
                        && !Objects.equals(fractions[j].getNum(), BigInteger.valueOf(0))
                        || !Objects.equals(fractions[j].getDen(), BigInteger.valueOf(1)))) {
                    return false;
                }

                if (Objects.equals(fractions[j].getNum(), BigInteger.valueOf(1))
                        && Objects.equals(fractions[j].getDen(), BigInteger.valueOf(1))
                        && j > furthest) {
                    furthest = j;
                    break;
                } else if (Objects.equals(fractions[j].getNum(), BigInteger.valueOf(1))
                        && Objects.equals(fractions[j].getDen(), BigInteger.valueOf(1))
                        && j <= furthest) {
                    return false;
                }
            }
        }

        return true;
    }
}

