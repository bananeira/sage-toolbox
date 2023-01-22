package com.sage.sagetoolbox.service;

import com.sage.sagetoolbox.model.Output;

import java.util.ArrayList;
import java.util.List;

public class ComplementBuilderNew {
    List<Character> hexSequencePreset = List.of(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    );

    List<Integer> hexSequenceInIntegers = List.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    );

    public List<Integer> formMinusOneComplement(List<Integer> numRepresentation, int radix) {
        return null;
    }

    public List<Integer> formOneComplement(List<Integer> numRepresentation, int radix) {
        return null;
    }

    private List<Integer> getPlusOneComplement(List<Integer> integerList, int radix) {
        while ()

        return ;
    }

    private Integer getNextIndexOverRadix(Integer index, int radix) {
        return (hexSequenceInIntegers.get(index) + 1 <= radix)
                ? hexSequenceInIntegers.get(index) + 1
                : hexSequenceInIntegers.get(0);
    }

    private List<Integer> getInvertedIndexList(List<Integer> integerList, int radix) {
        List<Integer> indexList = new ArrayList<>(List.copyOf(integerList));

        indexList.replaceAll(index -> getInvertedIndexOverRadix(index, radix));

        return indexList;
    }

    private Integer getInvertedIndexOverRadix(Integer index, int radix) {
        return hexSequenceInIntegers.get(radix - index) % radix;
    }

    private List<Character> buildCharListOfNumRepresentation(List<Integer> integerList) {
        List<Character> charListOfNumRepresentation = new ArrayList<>();

        for (Integer integer : integerList) {
            charListOfNumRepresentation.add(hexSequencePreset.get(integer));
        }

        return charListOfNumRepresentation;
    }

    private List<Character> buildSequenceOfPresetInRadix(int radix) {
        List<Character> sequenceOfPresetInRadix = new ArrayList<>();

        for (int i = 0; i < radix; i++) {
            sequenceOfPresetInRadix.add(hexSequencePreset.get(i));
        }

        return sequenceOfPresetInRadix;
    }

    private Output filterIllegalArguments(/*args*/) {
        return null;
    }
}