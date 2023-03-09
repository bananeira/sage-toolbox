package com.sage.sagetoolbox.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sage.sagetoolbox.service.EuclideanAlgorithm.fastGCD;
import static java.lang.Math.abs;

public class GenerateRSAValues {
    public static List<Integer> generateKeySet(int min, int max) {
        List<Integer> keySet = new ArrayList<>();
        int p = generateRandomPrime(min, max);
        int q = generateRandomPrime(min, max);

        if (p == -1 || q == -1) {
            return keySet;
        }

        keySet.add(p * q);
        keySet.add(generateRandomE(keySet.get(0)));

        return keySet;
    }

    public static List<Integer> generatePrimesSet(int min, int max) {
        List<Integer> primesSet = new ArrayList<>();
        primesSet.add(generateRandomPrime(min, max));
        primesSet.add(generateRandomPrime(min, max));

        if (primesSet.get(0) == -1 || primesSet.get(1) == -1) {
            return primesSet;
        }

        primesSet.add(generateRandomE(primesSet.get(0) * primesSet.get(1)));

        return primesSet;
    }

    public static int generateRandomPrime(int min, int max) {
        List<Integer> primeList = generateRandomPrimeList(min, max);
        Random random = new Random();

        return primeList.size() > 0 ? primeList.get(random.nextInt(primeList.size())) : -1;
    }

    public static List<Integer> generateRandomPrimeList(int min, int max) {
        return IntStream.rangeClosed(min, max)
                .filter(PrimeFactorization::isPrime).boxed()
                .collect(Collectors.toList());
    }

    public static int generateRandomE(int N) {
        List<Integer> randomEList = generateRandomEList(N);
        Random random = new Random();

        return randomEList.get(abs(random.nextInt()) % randomEList.size());
    }

    public static List<Integer> generateRandomEList(int N) {
        int eulerTotient = EulerTotientFunction.findEulersTotient(PrimeFactorization.getPrimeFactorsNumber(N));
        int min = 2;

        return IntStream.rangeClosed(min, eulerTotient)
                .filter(x -> (fastGCD(x, eulerTotient) == 1)).boxed()
                .collect(Collectors.toList());
    }

    public static int generateRandomN(int max) {
        List<Integer> randomNList = generateRandomNList(1, max);
        Random random = new Random();

        return randomNList.get(random.nextInt(randomNList.size()));
    }

    public static List<Integer> generateRandomNList(int min, int max) {
        return IntStream.rangeClosed(min, max)
                .filter(x -> (PrimeFactorization.getPrimeFactorsNumber(x)).size() == 2).boxed()
                .collect(Collectors.toList());
    }
}
