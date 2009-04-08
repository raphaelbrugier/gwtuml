package com.objetdirect.gwt.umlapi.client.umlcomponents;

public enum Visibility {
    PACKAGE('~'), PRIVATE('-'), PROTECTED('#'), PUBLIC('+');

    /**
     * This function convert a visibility char (+, -, #, ~) to a Visibility
     * @param token
     * @return the Visibility, if the char is any other character returns the PACKAGE visibility 
     */
    public static Visibility getVisibilityFromToken(final char token) {
	switch (token) {
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

    private char token;

    /**
     * @param token
     */
    private Visibility(final char token) {
	this.token = token;
    }

    @Override
    public String toString() {
	return "" + this.token;
    }

}
