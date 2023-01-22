package com.sage.sagetoolbox.service;

import com.sage.sagetoolbox.model.Output;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplementBuilder {
    private Output output = new Output();

    List<Character> highestValueInRadix = List.of(
            '%', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    );

    char firstValueAfterOverflow = '0';

    public Output buildBComplement(String inputString, int radix, int length, boolean getMinusOneComplement) {
        ComplementBuilderErrorTypes illegalParameterInputMessage = checkForIllegalParameterInputs(inputString, radix, length);

        if ( illegalParameterInputMessage != null ) {
            output.text = "failed: " + illegalParameterInputMessage.getMessage();
            output.status = "error";

            return output;
        }

        String bMinusOneComplement = stringToBMinusOneComplement(inputString, radix);
        String bComplement = addOneComplement(bMinusOneComplement, radix);

        output = getMinusOneComplement
                ? formatComplementOutput(bMinusOneComplement, radix, length)
                : formatComplementOutput(bComplement, radix, length);

        return output;
    }

    private Output formatComplementOutput(String complement, int radix, int length) {
        if (length == 0 || complement.length() >= length) {
            output.text = "out: " + "following complement has been found: " + complement;
        }
        else {
            output.text = "out: " + "following complement has been found: " + extendStringToSize(complement, radix, length);
        }
        output.status = "success";

        return output;
    }

    private ComplementBuilderErrorTypes checkForIllegalParameterInputs(String inputString, int radix, int length) {
        if ( inputString == null || radix == 0) {
            return ComplementBuilderErrorTypes.NO_VALUE_INPUTS;
        }

        if ( radix < 2 || radix > 16 ) {
            return ComplementBuilderErrorTypes.RADIX_OUT_OF_LEGAL_INTERVAL;
        }

        if ( length < 0 || (inputString.length() > length && length > 0)) {
            return ComplementBuilderErrorTypes.INVALID_LENGTH;
        }

        if ( checkForIllegalCharactersForRadix(inputString, radix) ) {
            return ComplementBuilderErrorTypes.INVALID_RADIX;
        }

        return null;
    }

    private boolean checkForIllegalCharactersForRadix(String inputString, int radix) {
        String pattern = radix >= 11 ?
                "[0-9A-" + highestValueInRadix.get(radix) + "a-" + highestValueInRadix.get(radix).toString().toLowerCase() + "]*"
                : "[0-" + highestValueInRadix.get(radix) + "]*";
        return !inputString.matches(pattern);
    }

    public String stringToBMinusOneComplement(String inputString, int radix) {
        StringBuilder bComplementString = new StringBuilder();

        for (char c : inputString.toCharArray()) {
            bComplementString.append(getInversionOfValueInRadix(c, radix));
        }

        return bComplementString.toString();
    }

    private String addOneComplement(String inputString, int radix) {
        char[] inputStringCharacters = inputString.toCharArray();

        int index = inputString.length() - 1;
        while (inputStringCharacters[index] == highestValueInRadix.get(radix) && index > 0) {
            inputStringCharacters[index] = firstValueAfterOverflow;
            index--;

            /*
            if (index < 0) {
                String largerComplementThanInputString = handleLargerComplementThanInputString(String.valueOf(inputStringCharacters), length);


                return largerComplementThanInputString != null
                        ? largerComplementThanInputString
                        : ComplementBuilderErrorTypes.COMPLEMENT_TOO_LARGE.getMessage();
            }
             */
        }
        inputStringCharacters[index] = toNextValue(inputStringCharacters[index]);

        return String.valueOf(inputStringCharacters);
    }

    /* Das ist aus rein praktischer Hinsicht falsch.
    Das Komplement erlebt genau dann einen Overflow, der im Weiteren nicht beachtet werden soll.


    private String handleLargerComplementThanInputString(String inputString, int radix) {
        StringBuilder str = new StringBuilder(inputString);
        str.insert(0, toNextValue(firstValueAfterOverflow));

        if (str.length() > radix) {
            return null;
        }

        return String.valueOf(str);
    }
     */

    private char toNextValue(char inputChar) {
        int observeIndexInResidueClass = highestValueInRadix.indexOf(inputChar) + 1 == highestValueInRadix. ?




                (highestValueInRadix.indexOf(inputChar) == highestValueInRadix.size() - 1)
                ? highestValueInRadix.get(1)
                : highestValueInRadix.indexOf(inputChar) + 1;

        return highestValueInRadix.get(observeIndexInResidueClass);
    }

    private List<Character> buildCompleteSequenceOfRadix(int radix) {
        String hexSequencePreset = "0123456789ABCDEF";
        String sequenceFromBasis = hexSequencePreset.substring(0, radix);

        return sequenceFromBasis.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }

    private char getInversionOfValueInRadix(char inputChar, int radix) {
        List<Character> sequenceOfBasis = buildCompleteSequenceOfRadix(radix);
        int indexOfChar = sequenceOfBasis.indexOf(inputChar);

        return sequenceOfBasis.get(( radix - indexOfChar - 1 ) % radix);
    }

    private String extendStringToSize(String inputString, int radix, int length) {
        StringBuilder currentString = new StringBuilder(inputString);

        int currentStringSize = currentString.length();
        while ( currentStringSize < length ) {
            currentString.insert(0, highestValueInRadix.get(radix));
            currentStringSize++;
        }

        return String.valueOf(currentString);
    }
}
