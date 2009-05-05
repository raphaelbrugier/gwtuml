package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represent an attribute in a class
 * 
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLObjectAttribute extends UMLClassAttribute {

    private String stringInstance;
    private Number numberInstance;
    /**
     * Constructor of the attribute
     * 
     * @param visibility
     * @param type
     *            Type of the attribute
     * @param name
     *            Name of the attribute
     * @param instance Instance string of the attribute 
     */
    public UMLObjectAttribute(final UMLVisibility visibility, final String type,
	    final String name, final String instance) {
	super(visibility,  type, name);
	this.setInstance(instance);	
    }
    
    /**
     * Constructor of the attribute
     * 
     * @param visibility
     * @param type
     *            Type of the attribute
     * @param name
     *            Name of the attribute
     * @param instance Instance {@link Number}r of the attribute
     */
    public UMLObjectAttribute(final UMLVisibility visibility, final String type,
	    final String name, final Number instance) {
	super(visibility,  type, name);
	this.setInstance(instance);	
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
	if(this.stringInstance != null) {
	    f.append(" = \"");
	    f.append(this.stringInstance);
	    f.append("\"");
	} else if(this.numberInstance != null) {
	    f.append(" = ");
	    f.append(this.numberInstance);
	}
	return f.toString();
    }

    /**
     * Set the instance with a {@link String}
     * 
     * @param stringInstance The {@link String} value of this attribute instance
     */
    public void setInstance(String stringInstance) {
	this.stringInstance = stringInstance;
    }

    /**
     * Set the instance with a {@link Number}
     * 
     * @param numberInstance The {@link Number} value of this attribute instance
     */
    public void setInstance(Number numberInstance) {
	this.numberInstance = numberInstance;
    }

    /**
     * Return the {@link String} of this instance
     * If the instance is a {@link Number} toString is applied
     * 
     * @return The instance {@link String} or "" if no instance is defined 
     */
    public String getInstance() {
	if(this.stringInstance != null) return this.stringInstance;
	if(this.numberInstance != null) return this.numberInstance.toString();
	return "";
    }

}
