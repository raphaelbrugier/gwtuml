package com.objetdirect.gwt.umlapi.client;

/**
 * This class defines specific application exception
 * 
 * @author hdarmet
 * @author fmounier
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
