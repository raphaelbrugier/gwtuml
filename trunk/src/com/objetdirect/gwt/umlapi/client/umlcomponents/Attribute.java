package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represent an attribute in a class
 * 
 * @author hdarmet
 * @author fmounier
 */
public class Attribute {

    private String name;

    private String type;

    private boolean validated = true;
    private Visibility visibility;

    /**
     * Constructor of the attribute
     * 
     * @param visibility
     * @param type
     *            Type of the attribute
     * @param name
     *            Name of the attribute
     */
    public Attribute(final Visibility visibility, final String type,
	    final String name) {
	super();
	this.visibility = visibility;
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
     * Getter for the visibility
     * @return the visibility
     */
    public Visibility getVisibility() {
	return this.visibility;
    }

    /**
     * Get the validated state of the attribute
     * 
     * @return true if validated, false otherwise
     * 
     */
    public boolean isValidated() {
	return this.validated;
    }

    /**
     * Setter for the name
     * 
     * @param name to be set
     * 
     */
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * Setter for the type
     * 
     * @param type  to be set
     * 
     */
    public void setType(final String type) {
	this.type = type;
    }

    /**
     * Set the validation state
     * 
     * @param validated boolean for validated validation state
     * 
     */
    public void setValidated(final boolean validated) {
	this.validated = validated;
    }

    /**
     * Set the visibility of the Attribute
     * @see Visibility
     * @param visibility
     */
    public void setVisibility(final Visibility visibility) {
	this.visibility = visibility;
    }

    /**
     * Format a string from attribute name and type
     * 
     * @return the UML formatted string for attribute name and type
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	final StringBuffer f = new StringBuffer();
	f.append(this.visibility);
	f.append(this.name);
	if (this.type != null) {
	    f.append(" : ");
	    f.append(this.type);
	}
	return f.toString();
    }
}
