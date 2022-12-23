package com.sage.sagetoolbox.tool;

import java.util.List;
import java.util.stream.Collectors;

public class ComplementBuilder {
    static List<Character> highestValueInBasis = List.of(
            '%', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    );

    static char firstValueAfterOverflow = '0';

    public static String buildBComplement(String inputString, int basis, int size) {
        ComplementBuilderErrorTypes illegalParameterInputMessage = checkForIllegalParameterInputs(inputString, basis, size);

        if ( illegalParameterInputMessage != null ) {
            return illegalParameterInputMessage.getMessage();
        }

        String bMinusOneComplement = stringToBMinusOneComplement(inputString, basis);
        String bComplement = addOneComplement(bMinusOneComplement, basis, size);

        if ( bComplement.length() < size ) {
            return extendStringToCorrectSize(bComplement, basis, size);
        }

        return bComplement;
    }

    private static ComplementBuilderErrorTypes checkForIllegalParameterInputs(String inputString, int basis, int size) {
        if ( inputString == null ) {
            return ComplementBuilderErrorTypes.NO_STRING_INPUT;
        }

        if ( basis < 2 || basis > 16 ) {
            return ComplementBuilderErrorTypes.BASIS_OUT_OF_LEGAL_INTERVAL;
        }

        if ( size <= 0 || inputString.length() > size ) {
            return ComplementBuilderErrorTypes.INVALID_SIZE;
        }

        if ( checkForIllegalCharactersForBasis(inputString, basis, size) ) {
            return ComplementBuilderErrorTypes.INVALID_BASIS;
        }

        return null;
    }

    private static boolean checkForIllegalCharactersForBasis(String inputString, int basis, int size) {
        String pattern = basis >= 11 ?
                "[0-9A-" + highestValueInBasis.get(basis) + "a-" + highestValueInBasis.get(basis).toString().toLowerCase() + "]{0," + size + "}"
                : "[0-" + highestValueInBasis.get(basis) + "]{0," + size + "}";
        return !inputString.matches(pattern);
    }

    public static String stringToBMinusOneComplement(String inputString, int basis) {
        StringBuilder bComplementString = new StringBuilder();

        for (char c : inputString.toCharArray()) {
            bComplementString.append(getInversionOfValueInBasis(c, basis));
        }

        return bComplementString.toString();
    }

    private static String addOneComplement(String inputString, int basis, int size) {
        char[] inputStringCharacters = inputString.toCharArray();

        int index = inputString.length() - 1;
        while (inputStringCharacters[index] == highestValueInBasis.get(basis) ) {
            inputStringCharacters[index] = firstValueAfterOverflow;
            index--;

            if (index < 0) {
                String largerComplementThanInputString = handleLargerComplementThanInputString(String.valueOf(inputStringCharacters), size);

                return largerComplementThanInputString != null
                        ? largerComplementThanInputString
                        : ComplementBuilderErrorTypes.COMPLEMENT_TOO_LARGE.getMessage();
            }
        }
        inputStringCharacters[index] = toNextValue(inputStringCharacters[index]);

        return String.valueOf(inputStringCharacters);
    }

    private static String handleLargerComplementThanInputString(String inputString, int size) {
        StringBuilder str = new StringBuilder(inputString);
        str.insert(0, toNextValue(firstValueAfterOverflow));

        if (str.length() > size) {
            return null;
        }

        return String.valueOf(str);
    }

    private static char toNextValue(char inputChar) {
        int nextIndex = highestValueInBasis.indexOf(inputChar) + 1;

        return highestValueInBasis.get(nextIndex);
    }

    private static List<Character> buildCompleteSequenceOfBasis(int basis) {
        String hexSequencePreset = "0123456789ABCDEF";
        String sequenceFromBasis = hexSequencePreset.substring(0, basis);

        return sequenceFromBasis.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }

    public static char getInversionOfValueInBasis(char inputChar, int basis) {
        List<Character> sequenceOfBasis = buildCompleteSequenceOfBasis(basis);
        int indexOfChar = sequenceOfBasis.indexOf(inputChar);

        return sequenceOfBasis.get(( basis - indexOfChar - 1 ) % basis);
    }

    private static String extendStringToCorrectSize(String inputString, int basis, int size) {
        StringBuilder currentString = new StringBuilder(inputString);

        int currentStringSize = currentString.length();
        while ( currentStringSize < size ) {
            currentString.insert(0, highestValueInBasis.get(basis));
            currentStringSize++;
        }

        return String.valueOf(currentString);
    }
}
