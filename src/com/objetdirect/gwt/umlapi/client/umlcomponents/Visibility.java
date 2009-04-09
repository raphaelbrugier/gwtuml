package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This enumeration lists all the visibility defined in uml
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public enum Visibility {
    /**
     * Package visibility (~) 
     */
    PACKAGE('~'),
    /**
     * Private visibility (-)
     */
    PRIVATE('-'),
    /**
     * Protected visibility (#)
     */
    PROTECTED('#'),
    /**
     * Public visibility (+)
     */
    PUBLIC('+');

    /**
     * This function convert a visibility char (+, -, #, ~) to a Visibility
     * 
     * @param token
     * @return the Visibility, if the char is any other character returns the
     *         PACKAGE visibility
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

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
	return "" + this.token;
    }

}
