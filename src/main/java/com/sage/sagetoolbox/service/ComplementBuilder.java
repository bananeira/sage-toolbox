package com.sage.sagetoolbox.service;

import com.sage.sagetoolbox.model.Output;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplementBuilder {
    private final Output output = new Output();

    List<Character> highestValueInRadix = List.of(
            '%', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    );

    char firstValueAfterOverflow = '0';

    public Output buildBComplement(String inputString, int basis, int size) {
        ComplementBuilderErrorTypes illegalParameterInputMessage = checkForIllegalParameterInputs(inputString, basis, size);

        if ( illegalParameterInputMessage != null ) {
            output.text = illegalParameterInputMessage.getMessage();
            output.status = "error";

            return output;
        }

        String bMinusOneComplement = stringToBMinusOneComplement(inputString, basis);
        String bComplement = addOneComplement(bMinusOneComplement, basis, size);

        if ( bComplement.length() < size ) {
            output.text =  "SUCCESS: Your complement is " + extendStringToCorrectSize(bComplement, basis, size) + ".";
            output.status = "success";
        } else {
            output.text = "SUCCESS: Your complement is " + bComplement + ".";
            output.status = "success";
        }

        return output;
    }

    private ComplementBuilderErrorTypes checkForIllegalParameterInputs(String inputString, int radix, int length) {
        if ( inputString == null || length == 0 || radix == 0) {
            return ComplementBuilderErrorTypes.NO_VALUE_INPUTS;
        }

        if ( radix < 2 || radix > 16 ) {
            return ComplementBuilderErrorTypes.RADIX_OUT_OF_LEGAL_INTERVAL;
        }

        if ( length <= 0 || inputString.length() > length ) {
            return ComplementBuilderErrorTypes.INVALID_LENGTH;
        }

        if ( checkForIllegalCharactersForRadix(inputString, radix, length) ) {
            return ComplementBuilderErrorTypes.INVALID_RADIX;
        }

        return null;
    }

    private boolean checkForIllegalCharactersForRadix(String inputString, int basis, int size) {
        String pattern = basis >= 11 ?
                "[0-9A-" + highestValueInRadix.get(basis) + "a-" + highestValueInRadix.get(basis).toString().toLowerCase() + "]{0," + size + "}"
                : "[0-" + highestValueInRadix.get(basis) + "]{0," + size + "}";
        return !inputString.matches(pattern);
    }

    public String stringToBMinusOneComplement(String inputString, int basis) {
        StringBuilder bComplementString = new StringBuilder();

        for (char c : inputString.toCharArray()) {
            bComplementString.append(getInversionOfValueInRadix(c, basis));
        }

        return bComplementString.toString();
    }

    private String addOneComplement(String inputString, int basis, int size) {
        char[] inputStringCharacters = inputString.toCharArray();

        int index = inputString.length() - 1;
        while (inputStringCharacters[index] == highestValueInRadix.get(basis) ) {
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

    private String handleLargerComplementThanInputString(String inputString, int size) {
        StringBuilder str = new StringBuilder(inputString);
        str.insert(0, toNextValue(firstValueAfterOverflow));

        if (str.length() > size) {
            return null;
        }

        return String.valueOf(str);
    }

    private char toNextValue(char inputChar) {
        int nextIndex = highestValueInRadix.indexOf(inputChar) + 1;

        return highestValueInRadix.get(nextIndex);
    }

    private List<Character> buildCompleteSequenceOfRadix(int basis) {
        String hexSequencePreset = "0123456789ABCDEF";
        String sequenceFromBasis = hexSequencePreset.substring(0, basis);

        return sequenceFromBasis.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }

    public char getInversionOfValueInRadix(char inputChar, int basis) {
        List<Character> sequenceOfBasis = buildCompleteSequenceOfRadix(basis);
        int indexOfChar = sequenceOfBasis.indexOf(inputChar);

        return sequenceOfBasis.get(( basis - indexOfChar - 1 ) % basis);
    }

    private String extendStringToCorrectSize(String inputString, int basis, int size) {
        StringBuilder currentString = new StringBuilder(inputString);

        int currentStringSize = currentString.length();
        while ( currentStringSize < size ) {
            currentString.insert(0, highestValueInRadix.get(basis));
            currentStringSize++;
        }

        return String.valueOf(currentString);
    }
}
