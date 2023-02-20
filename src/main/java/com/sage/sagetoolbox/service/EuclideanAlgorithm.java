package com.sage.sagetoolbox.service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class EuclideanAlgorithm {
    public static int fastGCD(int number1, int number2) {
        if (number2 == 0) {
            return number1;
        }

        return fastGCD(number2, number1 % number2);
    }

    public static List<List<Integer>> findGCD(int numberA, int numberB) {
        final List<List<Integer>> divisorFormatList = new ArrayList<>();

        int buffer = min(numberA, numberB) == numberA ? numberA : numberB;
        numberA = max(numberA, numberB);
        numberB = buffer;

        findGCDRecursive(numberA, numberB, divisorFormatList);
        return divisorFormatList;
    }

    public static void findGCDRecursive(int numberA, int numberB, List<List<Integer>> divisorFormatList) {
        //we define numbers in the format n = dq + r ( (0) divisor = (1) dividend * (2) quotient + (3) rest)
        List<Integer> divisorFormat = new ArrayList<>();

        if (numberB >= 1) {
            divisorFormat.add(numberA);
            divisorFormat.add((int) floor((numberA / numberB)));
            divisorFormat.add(numberB);
            divisorFormat.add(numberA % numberB);

            divisorFormatList.add(divisorFormat);

            findGCDRecursive(numberB, numberA % numberB, divisorFormatList);
        }
    }

    public static List<List<Integer>> extendGCD(List<List<Integer>> divisorFormatList) {
        final List<List<Integer>> extendedGCDList = new ArrayList<>();

        if (divisorFormatList.size() == 1 && divisorFormatList.get(0).get(2) == 1) {
            extendGCDRecursive(
                    1,
                    divisorFormatList.get(0).get(0),
                    - divisorFormatList.get(0).get(1),
                    divisorFormatList.get(0).get(2),
                    0,
                    divisorFormatList,
                    extendedGCDList
            );
        } else if (divisorFormatList.get(divisorFormatList.size() - 2).get(3) == 1) {
            extendGCDRecursive(
                    1,
                    divisorFormatList.get(divisorFormatList.size() - 2).get(0),
                    - divisorFormatList.get(divisorFormatList.size() - 2).get(1),
                    divisorFormatList.get(divisorFormatList.size() - 2).get(2),
                    divisorFormatList.size() - 2,
                    divisorFormatList,
                    extendedGCDList
            );
        }

        return extendedGCDList;
    }

    public static void extendGCDRecursive(int s, int a, int t, int b, int currentPositionalIndex, List<List<Integer>> divisorFormatList, List<List<Integer>> extendedGCDList) {
        List<Integer> toInverseFormat = new ArrayList<>();
        if (divisorFormatList.size() > 2) {
            if ((a != divisorFormatList.get(0).get(0) || a != divisorFormatList.get(0).get(2))
                    && (b != divisorFormatList.get(0).get(0) || b != divisorFormatList.get(0).get(2))
                    && currentPositionalIndex > 0) {

                if (currentPositionalIndex == divisorFormatList.size() - 1
                        || currentPositionalIndex == divisorFormatList.size() - 2) {
                    getDefaultInverseFormat(s, a, t, b, toInverseFormat);

                    extendedGCDList.add(List.copyOf(toInverseFormat));
                }

                --currentPositionalIndex;

                toInverseFormat = new ArrayList<>();
                toInverseFormat.add(1);

                if (a < b) {
                    toInverseFormat.add(t);
                    toInverseFormat.add(b);
                    toInverseFormat.add(s);
                    insertEquals(currentPositionalIndex, toInverseFormat, divisorFormatList);
                } else {
                    toInverseFormat.add(s);
                    toInverseFormat.add(a);
                    toInverseFormat.add(t);
                    insertEquals(currentPositionalIndex, toInverseFormat, divisorFormatList);
                }

                extendedGCDList.add(List.copyOf(toInverseFormat));

                toInverseFormat.set(4, toInverseFormat.get(3) * toInverseFormat.get(4));
                toInverseFormat.set(6, toInverseFormat.get(3) * toInverseFormat.get(6));
                toInverseFormat.remove(3);

                extendedGCDList.add(List.copyOf(toInverseFormat));

                if (toInverseFormat.get(2).equals(toInverseFormat.get(4))) {
                    collectFactors(toInverseFormat, 3);
                } else {
                    collectFactors(toInverseFormat, 5);
                }

                extendedGCDList.add(List.copyOf(toInverseFormat));

                extendGCDRecursive(
                        toInverseFormat.get(1),
                        toInverseFormat.get(2),
                        toInverseFormat.get(3),
                        toInverseFormat.get(4),
                        currentPositionalIndex,
                        divisorFormatList,
                        extendedGCDList
                );
            }
        } else {
            getDefaultInverseFormat(s, a, t, b, toInverseFormat);

            extendedGCDList.add(List.copyOf(toInverseFormat));
        }

        // we want the form r = s * a + t * b
        // while we have the form n = d * q + r <=> r = 1 * n - d * q, see comment above

        // we continue the recursion until both a and b equal both top layer integers we checked the GCD of

    }

    private static void collectFactors(List<Integer> toInverseFormat, int atIndex) {
        toInverseFormat.set(1, toInverseFormat.get(atIndex) + toInverseFormat.get(1));
        toInverseFormat.remove(atIndex);
        toInverseFormat.remove(atIndex);
    }

    private static void insertEquals(int currentPositionalIndex, List<Integer> toInverseFormat, List<List<Integer>> divisorFormatList) {
        toInverseFormat.add(1);
        toInverseFormat.add(divisorFormatList.get(currentPositionalIndex).get(0));
        toInverseFormat.add(- divisorFormatList.get(currentPositionalIndex).get(1));
        toInverseFormat.add(divisorFormatList.get(currentPositionalIndex).get(2));
    }

    private static void getDefaultInverseFormat(int s, int a, int t, int b, List<Integer> toInverseFormat) {
        toInverseFormat.add(1);
        toInverseFormat.add(s);
        toInverseFormat.add(a);
        toInverseFormat.add(t);
        toInverseFormat.add(b);
    }
}
