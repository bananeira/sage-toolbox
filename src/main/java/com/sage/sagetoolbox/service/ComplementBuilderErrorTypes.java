package com.sage.sagetoolbox.service;

public enum ComplementBuilderErrorTypes {
    NO_VALUE_INPUTS("ERROR: Enter values (radix: number, length: number, inputString: String)"),
    RADIX_OUT_OF_LEGAL_INTERVAL("ERROR: Basis out of legal interval (2-16)"),
    INVALID_LENGTH("ERROR: Reduce size of String to match given size or increment size to match length of String or give size higher than 0"),
    INVALID_RADIX("ERROR: Choose string that matches basis"),
    COMPLEMENT_TOO_LARGE("ERROR: Complement too large to be displayed by size, adjust input or size");

    private String message;
    ComplementBuilderErrorTypes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}