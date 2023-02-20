package com.sage.sagetoolbox.service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class PrimeFactorization {
    public static List<Integer> getPrimeFactorsNumber(int number) {
        int divisor = 2; //smallest prime number
        int maxDivisor = (int) sqrt(number);
        List<Integer> primeFactors = new ArrayList<>();

        while (divisor <= maxDivisor) {
            if (number % divisor == 0) {
                primeFactors.add(divisor);
                number /= divisor;
                maxDivisor = (int) sqrt(number);
            } else {
                ++divisor;
                // increment divisor by one. this works, because every number that is not prime
                // will be factorizable. note that we work our way from bottom to top, starting with the smallest prime
            }
        }
        primeFactors.add(number);

        return primeFactors;
    }

    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }

        return true;
    }
}
