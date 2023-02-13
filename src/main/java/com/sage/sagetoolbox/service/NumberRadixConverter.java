package com.sage.sagetoolbox.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

@Service
public class NumberRadixConverter {
    public static List<Integer> convertToRadix(List<Integer> input, int fromBasis, int toBasis, int sign) {
        //calculates length of new number via log_toBasis(fromBasis)
        int lengthOfConvertedNumber = (int) (ceil(log(fromBasis) / log(toBasis) * input.size()));

        List<Integer> convertedList = new ArrayList<>();
        BigInteger decimal = BigInteger.valueOf(0);

        //converts number to decimal by iterating backwards through digits while increasing
        //exponent per iteration. starting with fromBasis^0
        for (int i = 0; i < input.size(); i++) {
            decimal = decimal.add(BigInteger.valueOf((int) (input.get(input.size() - 1 - i) * pow(fromBasis, i))));
        }

        //adds each remainder of decimal mod toBasis and divides decimal by toBasis while decimal is bigger than 0
        while (BigInteger.valueOf(0).compareTo(decimal) < 0) {
            convertedList.add(0, decimal.mod(BigInteger.valueOf(toBasis)).intValue());
            decimal = decimal.divide(BigInteger.valueOf(toBasis));
        }

        //sign extend to match length of new number
        while (convertedList.size() < lengthOfConvertedNumber) {
            convertedList.add(0, sign);
        }

        return convertedList;
    }
}