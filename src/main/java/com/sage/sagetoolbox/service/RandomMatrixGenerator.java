package com.sage.sagetoolbox.service;

import java.math.BigInteger;
import java.util.*;

import static com.sage.sagetoolbox.service.GaussMatrix.getMatrix;

public class RandomMatrixGenerator {
    public static List<String> generateRandomMatrix(int max, boolean useFractions, int m, int n, int numOfFreeVars) throws Exception {
        Random random = new Random();
        Set<Integer> freeVars = new TreeSet<>();
        List<Integer> notFreeVarsList = new ArrayList<>();
        List<String> elementList = new ArrayList<>();
        boolean removeNotFreeVar = false;
        int amountOfRandomOperations = random.nextInt(30) + 15;
        int addsAtBeginning = 12;
        int swapsLeft = 4;

        if (numOfFreeVars > n || numOfFreeVars < -1) {
            return null;
        }

        if (numOfFreeVars == -1) {
            numOfFreeVars = random.nextInt(n);
        }

        if (max < 0 || max > 100) {
            return null;
        }

        for (int i = 0; i < n; i++) {
            notFreeVarsList.add(i);
        }

        while (freeVars.size() < numOfFreeVars) {
            int nextInt = random.nextInt(n);
            if (notFreeVarsList.contains(nextInt) && !freeVars.contains(nextInt)) {
                notFreeVarsList.remove((Integer) nextInt);
                freeVars.add(nextInt);
            }
        }

        for (int i = 0; i < m; i++) {
            if (removeNotFreeVar) {
                notFreeVarsList.remove(0);
                removeNotFreeVar = false;
            }

            for (int j = 0; j <= n; j++) {
                if (notFreeVarsList.size() > 0 && j == notFreeVarsList.get(0)) {
                    elementList.add("1");
                    removeNotFreeVar = true;
                } else if (notFreeVarsList.size() > 0 && j > notFreeVarsList.get(0)) {
                    elementList.add(generateRandomFractionAsString(max, useFractions));
                } else {
                    elementList.add("0");
                }
            }
        }

        GaussMatrix.setMatrix(m, n + 1, elementList);

        for (int i = amountOfRandomOperations; i >= 0; i--) {
            int randomOperation = random.nextInt(3);

            if (addsAtBeginning > 0 && randomOperation != 1) {
                randomOperation = 1;
            }

            while (swapsLeft == 0 && randomOperation == 0) {
                randomOperation = random.nextInt(3);
            }

            switch (randomOperation) {
                case 0 -> {
                    int swapFrom = random.nextInt(m);
                    int swapTo = random.nextInt(m);

                    GaussMatrix.swap(swapFrom, swapTo);

                    swapsLeft--;
                }
                case 1 -> {
                    int addFrom = getRandomFilledRow(m);
                    int addTo = random.nextInt(m);

                    while (addTo == addFrom && m > 1) {
                        addTo = random.nextInt(m);
                    }

                    Fraction addFactor = generateRandomFraction(max, useFractions);
                    int maxAttempts = 15;
                    int newMax = max;

                    if (m > 1) GaussMatrix.add(addFactor, addFrom, addTo);

                    while (GaussMatrix.matrixExceedingBarrier(max) && maxAttempts > 0 && m > 1) {
                        addFactor.multiply(BigInteger.valueOf(-1));
                        GaussMatrix.add(addFactor, addFrom, addTo);

                        if (maxAttempts > 1) {
                            newMax = (int) Math.ceil((double) newMax / 10);
                            addFactor = generateRandomFraction(newMax, useFractions);
                            GaussMatrix.add(addFactor, addFrom, addTo);
                        }

                        maxAttempts--;
                    }

                    if (addsAtBeginning > 0) {
                        addsAtBeginning--;
                    }
                }
                case 2 -> {
                    int multTo = getRandomFilledRow(m);
                    Fraction multFactor = generateRandomFraction(max, useFractions);
                    int maxAttempts = 15;
                    int newMax = max;

                    GaussMatrix.multiply(multTo, multFactor);

                    while (GaussMatrix.matrixExceedingBarrier(max) && maxAttempts > 0) {
                        multFactor.invert();
                        GaussMatrix.multiply(multTo, multFactor);

                        if (maxAttempts > 1) {
                            newMax = (int) Math.ceil((double) newMax / 10);
                            multFactor = generateRandomFraction(newMax, useFractions);
                            GaussMatrix.multiply(multTo, multFactor);
                        }

                        maxAttempts--;
                    }
                }
            }
        }

        Fraction[][] matrix = getMatrix();

        return getElementList(matrix);
    }

    private static List<String> getElementList(Fraction[][] matrix) {
        List<String> elementList = new ArrayList<>();

        for (Fraction[] fractions : matrix) {
            for (Fraction fraction : fractions) {
                elementList.add(String.valueOf(fraction));
            }
        }

        return elementList;
    }

    private static String generateRandomFractionAsString(int max, boolean useFractions) {
        return generateRandomFraction(max, useFractions).toString();
    }

    private static Fraction generateRandomFraction(int max, boolean useFractions) {
        Random random = new Random();

        BigInteger newNum = BigInteger.valueOf(random.nextInt(2 * max + 1) - max);
        BigInteger newDen = useFractions ? BigInteger.valueOf(random.nextInt(max) + 1) : BigInteger.ONE;

        while (Objects.equals(newNum, BigInteger.ZERO)) {
            newNum = BigInteger.valueOf(random.nextInt(2 * max + 1) - max);
        }

        return new Fraction(
                newNum,
                newDen
        );
    }

    private static int getRandomFilledRow(int m) {
        Random random = new Random();
        int randomRow = random.nextInt(m);

        while (!GaussMatrix.checkLineDifferentFromZero(randomRow)
                && GaussMatrix.matrixNotEmpty()) {
            randomRow = random.nextInt(m);
        }

        return randomRow;
    }
}
