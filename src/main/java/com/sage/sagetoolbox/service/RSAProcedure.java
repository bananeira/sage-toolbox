package com.sage.sagetoolbox.service;

import com.sage.sagetoolbox.model.RSAWithKeyOutput;
import com.sage.sagetoolbox.model.RSAWithPrimesOutput;

import java.util.List;

public class RSAProcedure {
    public static RSAWithPrimesOutput rsaWithPrimes(int primeP, int primeQ, int encryptKey) {
        final RSAWithPrimesOutput rsaOutput = new RSAWithPrimesOutput();

        if (primeP <= 0) {
            rsaOutput.exception = exceptionList(9);
            return rsaOutput;
        }

        if (!PrimeFactorization.isPrime(primeP)) {
            rsaOutput.exception = exceptionList(4);
            return rsaOutput;
        }

        if (primeQ <= 0) {
            rsaOutput.exception = exceptionList(10);
            return rsaOutput;
        }

        if (!PrimeFactorization.isPrime(primeQ)) {
            rsaOutput.exception = exceptionList(5);
            return rsaOutput;
        }

        if (encryptKey <= 0) {
            rsaOutput.exception = exceptionList(8);
            return rsaOutput;
        }

        if (encryptKey > EulerTotientFunction.findEulersTotient(List.of(primeP, primeQ))) {
            rsaOutput.exception = exceptionList(1);
            return rsaOutput;
        }

        rsaOutput.eulerTotient = EulerTotientFunction.findEulersTotient(List.of(primeP, primeQ));

        rsaOutput.totientComponents = ExtendedEulerTotientFunction.findEulersTotient(List.of(primeP, primeQ));
        rsaOutput.divisorFormatList = EuclideanAlgorithm.findGCD(encryptKey, rsaOutput.eulerTotient);

        if (rsaOutput.divisorFormatList.size() > 1
                && rsaOutput.divisorFormatList.get(rsaOutput.divisorFormatList.size() - 2).get(3) != 1) {
            rsaOutput.exception = exceptionList(7);
            return rsaOutput;
        } else if (rsaOutput.divisorFormatList.get(rsaOutput.divisorFormatList.size() - 1).get(2) != 1) {
            rsaOutput.exception = exceptionList(7);
            return rsaOutput;
        }

        rsaOutput.extendedGCDList = EuclideanAlgorithm.extendGCD(rsaOutput.divisorFormatList);

        return rsaOutput;
    }

    public static RSAWithKeyOutput rsaWithKey(int encryptKey, int N) {
        final RSAWithKeyOutput rsaOutput = new RSAWithKeyOutput();

        if (encryptKey <= 0) {
            rsaOutput.exception = exceptionList(8);
            return rsaOutput;
        }

        if (N <= 0) {
            rsaOutput.exception = exceptionList(11);
            return rsaOutput;
        }

        if (encryptKey >= N) {
            rsaOutput.exception = exceptionList(6);
            return rsaOutput;
        }

        rsaOutput.primeFactors = PrimeFactorization.getPrimeFactorsNumber(N);

        rsaOutput.eulerTotient = EulerTotientFunction.findEulersTotient(rsaOutput.primeFactors);

        if (rsaOutput.primeFactors.size() != 2) {
            rsaOutput.exception = exceptionList(2);
            return rsaOutput;
        }

        if (encryptKey >= rsaOutput.eulerTotient) {
            rsaOutput.exception = exceptionList(1);
            return rsaOutput;
        }

        rsaOutput.totientComponents = ExtendedEulerTotientFunction.findEulersTotient(rsaOutput.primeFactors);
        rsaOutput.divisorFormatList = EuclideanAlgorithm.findGCD(encryptKey, rsaOutput.eulerTotient);


        if (rsaOutput.divisorFormatList.size() > 1
                && rsaOutput.divisorFormatList.get(rsaOutput.divisorFormatList.size() - 2).get(3) != 1) {
            rsaOutput.exception = exceptionList(3);
            return rsaOutput;
        } else if (rsaOutput.divisorFormatList.get(rsaOutput.divisorFormatList.size() - 1).get(2) != 1) {
            rsaOutput.exception = exceptionList(3);
            return rsaOutput;
        }

        rsaOutput.extendedGCDList = EuclideanAlgorithm.extendGCD(rsaOutput.divisorFormatList);

        return rsaOutput;
    }

    public static Integer exceptionList(Integer exception) {
        List<Integer> exceptions = List.of(
                0, // no error
                1, // the encryptKey is bigger than or equal to phi(N)
                2, // N does not consist of exactly 2 prime factors
                3, // the GCD of given numbers is not 1, euclidean algorithm is not extended
                4, // p is not a prime or smaller than 1
                5, // q is not a prime or smaller than 1
                6, // the encryptKey is bigger than or equal to N or smaller than 1
                7, // GCD of e and phi(N) is not 1
                8, // e is out of range
                9, // p is out of range
                10, // q is out of range
                11, // N is out of range
                12, // e is too big
                13, // p is too big
                14, // q is too big
                15, // N is too big
                16, // min is too big or too small
                17, // max is too big or too small
                18, // min is bigger than max
                19 // no primes in given interval
        );

        return exceptions.get(exception);
    }
}