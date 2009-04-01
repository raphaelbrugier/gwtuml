package com.objetdirect.gwt.umlapi.client.umlcomponents;

public enum Visibility {
    PUBLIC('+'),
    PROTECTED('#'),
    PRIVATE('-'),
    PACKAGE('~');

    private char token;

    private Visibility(char token) {
        this.token = token;
    }

    public static Visibility getVisibilityFromToken(char token) {
        switch(token) {
        case '+':
            return PUBLIC;
        case '#':
            return PROTECTED;
        case '-':
            return PRIVATE;
        case '~':
        default:
            return PACKAGE;
        }
    }
    public String toString() {
        return "" + token;
    }

}
