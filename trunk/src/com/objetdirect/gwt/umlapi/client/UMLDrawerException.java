package com.objetdirect.gwt.umlapi.client;

/**
 * This class defines specific application exception
 * 
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLDrawerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor of the exception
     * 
     * @param msg
     *            The String exception
     */
    public UMLDrawerException(final String msg) {
	super(msg);
    }
}
