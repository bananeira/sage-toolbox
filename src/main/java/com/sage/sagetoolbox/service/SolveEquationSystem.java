package com.sage.sagetoolbox.service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Objects;

public class SolveEquationSystem {
    public static void solveSystem(Fraction[][] augmentedMatrix) {
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

        System.out.println(Arrays.deepToString(expandedAugmentedSystem));
    }
}
