package com.sage.sagetoolbox.tool;

public enum ComplementBuilderErrorTypes {
    NO_STRING_INPUT("Enter correct String"),
    BASIS_OUT_OF_LEGAL_INTERVAL("Basis out of legal interval (2-16)"),
    INVALID_SIZE("Reduce size of String to match given size or increment size to match length of String or give size higher than 0"),
    INVALID_BASIS("Choose string that matches basis"),
    COMPLEMENT_TOO_LARGE("Complement too large to be displayed by size, adjust input or size");

    private String message;
    ComplementBuilderErrorTypes(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}