package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represent an attribute in a class
 * @author  hdarmet
 * @author  fmounier
 */
public class Attribute extends ClassMember {

	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="type"
	 */
	private String type;
	/**
	 * @uml.property  name="validated"
	 */
	private boolean validated = true;

	/**
	 * Constructor of the attribute
	 * @param type Type of the attribute
	 * @param name Name of the attribute
	 */
	public Attribute(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	/**
	 * Getter for the name
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for the type
	 * @return  the type
	 * @uml.property  name="type"
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get the validated state of the attribute
	 * @return  true if validated, false otherwise
	 * @uml.property  name="validated"
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * Setter for the name
	 * @param name  to be set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter for the type
	 * @param type  to be set
	 * @uml.property  name="type"
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
	/**
	 * Set the validation state
	 * @param  boolean for validated validation state
	 * @uml.property  name="validated"
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	
	/**
	 * Format a string from attribute name and type
	 * @return the UML formatted string for attribute name and type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer f = new StringBuffer();
		f.append(this.name);
		if (this.type != null) {
			f.append(" : ");
			f.append(this.type);
		}
		return f.toString();
	}
}
