package com.sage.sagetoolbox.service;

public enum ComplementBuilderErrorTypes {
    NO_VALUE_INPUTS("please enter correct values (radix: number, length: number, inputString: String)"),
    RADIX_OUT_OF_LEGAL_INTERVAL("radix out of legal interval (2-16)"),
    INVALID_LENGTH("increment length to match length of String or reduce length of String to match given size"),
    INVALID_RADIX("choose String that matches radix"),
    COMPLEMENT_TOO_LARGE("complement too large to be displayed by length, adjust input or length accordingly");

    private String message;
    ComplementBuilderErrorTypes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}