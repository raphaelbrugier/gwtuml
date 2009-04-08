package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represent a parameter of a method
 * 
 * @author fmounier
 * @author hdarmet
 */
public class Parameter {

    private String name;

    private String type;

    /**
     * Constructor of the parameter
     * 
     * @param type
     *            Type of the parameter
     * @param name
     *            Name of the parameter
     */
    public Parameter(final String type, final String name) {
	this.type = type;
	this.name = name;
    }

    /**
     * Getter for the name
     * 
     * @return the name
     * 
     */
    public String getName() {
	return this.name;
    }

    /**
     * Getter for the type
     * 
     * @return the type
     * 
     */
    public String getType() {
	return this.type;
    }

    /**
     * Setter for the name
     * 
     * @param name
     *            to be set
     * 
     */
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * Setter for the type
     * 
     * @param type
     *            to be set
     * 
     */
    public void setType(final String type) {
	this.type = type;
    }

    /**
     * Format a string from parameter name and type
     * 
     * @return the UML formatted string for parameter name and type
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	final StringBuffer f = new StringBuffer();
	f.append(this.name);
	if (this.type != null) {
	    f.append(" : ");
	    f.append(this.type);
	}
	return f.toString();
    }
}
