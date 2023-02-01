package com.sage.sagetoolbox.service;

public enum ComplementBuilderErrorTypes {
    NO_VALUE_INPUTS("please enter correct values (radix: number, length: number, inputString: String)"),
    RADIX_OUT_OF_LEGAL_INTERVAL("radix out of legal interval (2-16)"),
    INVALID_LENGTH("increment length to match length of String or reduce length of String to match given size"),
    LENGTH_TOO_LARGE("surpassed maximum length (512 characters), please reduce length of input"),
    INVALID_RADIX("choose a String that matches radix");

    private final String message;
    ComplementBuilderErrorTypes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}