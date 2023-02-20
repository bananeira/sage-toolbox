package com.sage.sagetoolbox.service;

import com.sage.sagetoolbox.model.Output;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ComplementBuilder {
    private static final Output output = new Output();

    static List<Character> hexSequencePreset = List.of(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    );

    static List<Integer> hexSequenceInIntegers = List.of(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    );

    public static Output formatOutput(String inputString,
                                      int radix,
                                      int length,
                                      boolean getMinusOneComplement,
                                      boolean interpretAsBinary) {
        Output filterIllegalArguments = filterIllegalArguments(inputString, radix, length);

        if (filterIllegalArguments != null) {
            return filterIllegalArguments;
        }

        if (getMinusOneComplement) {
            List<Character> complement = formMinusOneComplement(inputString, radix, length, interpretAsBinary);
            output.text = "out: " + convertCharListToString(complement);
            output.status = "success";

            return output;
        }

        List<Character> complement = formOneComplement(inputString, radix, length, interpretAsBinary);
        output.text = "out: " + convertCharListToString(complement);
        output.status = "success";

        return output;
    }

    public static String convertCharListToString(List<Character> characterList) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char character : characterList) {
            stringBuilder.append(character);
        }

        return String.valueOf(stringBuilder);
    }

    public static List<Character> formMinusOneComplement(String numRepresentationString,
                                                         int radix,
                                                         int length,
                                                         boolean interpretAsBinary) {
        List<Integer> numRepresentationList = receiveRepresentationAsString(numRepresentationString);
        numRepresentationList = interpretAsBinary && radix != 2
                ? getInvertedIndexList(NumberRadixConverter.convertToRadix(
                        numRepresentationList,
                        radix,
                        2,
                        0),
                2)
                : getInvertedIndexList(numRepresentationList, radix);
        numRepresentationList = applyLengthFormat(radix, length, interpretAsBinary, numRepresentationList);
        numRepresentationList = interpretAsBinary && radix != 2
                ? NumberRadixConverter.convertToRadix(
                numRepresentationList,
                2,
                radix,
                1)
                : numRepresentationList;

        return buildCharListOfNumRepresentation(numRepresentationList);
    }

    public static List<Character> formOneComplement(String numRepresentationString,
                                                    int radix,
                                                    int length,
                                                    boolean interpretAsBinary) {
        List<Integer> numRepresentationList = receiveRepresentationAsString(numRepresentationString);
        numRepresentationList = interpretAsBinary && radix != 2
                ? getInvertedIndexList(NumberRadixConverter.convertToRadix(
                        numRepresentationList,
                        radix,
                        2,
                        0),
                2)
                : getInvertedIndexList(numRepresentationList, radix);
        numRepresentationList = interpretAsBinary && radix != 2
                ? getPlusOneComplement(numRepresentationList, 2)
                : getPlusOneComplement(numRepresentationList, radix);
        numRepresentationList = applyLengthFormat(radix, length, interpretAsBinary, numRepresentationList);
        numRepresentationList = interpretAsBinary && radix != 2
                ? NumberRadixConverter.convertToRadix(
                numRepresentationList,
                2,
                radix,
                1)
                : numRepresentationList;

        return buildCharListOfNumRepresentation(numRepresentationList);
    }

    private static List<Integer> applyLengthFormat(
            int radix,
            int length,
            boolean interpretAsBinary,
            List<Integer> numRepresentationList) {
        numRepresentationList = (length > numRepresentationList.size() && interpretAsBinary)
                ? extendToLength(numRepresentationList, 2, length)
                : (length > numRepresentationList.size())
                ? extendToLength(numRepresentationList, radix, length)
                : numRepresentationList;
        return numRepresentationList;
    }

    public static List<Integer> receiveRepresentationAsString(String numberRepresentation) {
        numberRepresentation = numberRepresentation.toUpperCase();
        List<Character> numberRepresentationAsCharList = new ArrayList<>();

        for (Character character : numberRepresentation.toCharArray()) {
            numberRepresentationAsCharList.add(character);
        }

        List<Integer> numberRepresentationFromCharList = new ArrayList<>();

        for (Character character : numberRepresentationAsCharList) {
            numberRepresentationFromCharList.add(hexSequencePreset.indexOf(character));
        }

        return numberRepresentationFromCharList;
    }

    private static List<Integer> getPlusOneComplement(List<Integer> integerList, int radix) {
        List<Integer> indexList = new ArrayList<>(List.copyOf(integerList));

        for (int i = indexList.size() - 1; i >= 0; i--) {
            if (Objects.equals(indexList.get(i), hexSequenceInIntegers.get(radix - 1))) {
                indexList.set(i, 0);
            } else {
                indexList.set(i, indexList.get(i) + 1);
                return indexList;
            }
        }

        return indexList;
    }

    private static List<Integer> getInvertedIndexList(List<Integer> integerList, int radix) {
        List<Integer> indexList = new ArrayList<>(List.copyOf(integerList));

        indexList.replaceAll(index -> getInvertedIndexOverRadix(index, radix));

        return indexList;
    }

    private static Integer getInvertedIndexOverRadix(Integer index, int radix) {
        return hexSequenceInIntegers.get((radix - index - 1) % (radix));
    }

    public static List<Character> buildCharListOfNumRepresentation(List<Integer> integerList) {
        List<Character> charListOfNumRepresentation = new ArrayList<>();

        for (Integer integer : integerList) {
            charListOfNumRepresentation.add(hexSequencePreset.get(integer));
        }

        return charListOfNumRepresentation;
    }

    private static List<Integer> extendToLength(List<Integer> input, int radix, int length) {
        List<Integer> extendedIndexList = new ArrayList<>(input);

        while (extendedIndexList.size() < length) {
            extendedIndexList.add(0, hexSequenceInIntegers.get(radix - 1));
        }

        return extendedIndexList;
    }

    private static Output filterIllegalArguments(String input, int radix, int length) {
        if (input == null || radix == 0) {
            output.text = "error: " + ComplementBuilderErrorTypes.NO_VALUE_INPUTS.getMessage();
            output.status = "error";

            return output;
        }

        if (radix < 2 || radix > 16) {
            output.text = "error: " + ComplementBuilderErrorTypes.RADIX_OUT_OF_LEGAL_INTERVAL.getMessage();
            output.status = "error";

            return output;
        }

        if (length > 512) {
            output.text = "error: " + ComplementBuilderErrorTypes.LENGTH_TOO_LARGE.getMessage();
            output.status = "error";

            return output;
        }

        if (length < input.length() && length != 0) {
            output.text = "error: " + ComplementBuilderErrorTypes.INVALID_LENGTH.getMessage();
            output.status = "error";

            return output;
        }

        String pattern = radix >= 11
                ? "[0-9A-" + hexSequencePreset.get(radix - 1).toString().toUpperCase()
                + "a-" + hexSequencePreset.get(radix - 1).toString().toLowerCase() + "]*"
                : "[0-" + hexSequencePreset.get(radix - 1).toString() + "]*";

        if (!input.matches(pattern)) {
            output.text = "error: " + ComplementBuilderErrorTypes.INVALID_RADIX.getMessage();
            output.status = "error";

            return output;
        }

        return null;
    }
}
