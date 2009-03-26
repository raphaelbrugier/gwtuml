package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represent a parameter of a method
 * @author  fmounier
 * @author  hdarmet
 */
public class Parameter {

	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="type"
	 */
	private String type;
	
	/**
	 * Constructor of the parameter
	 * @param type Type of the parameter
	 * @param name Name of the parameter
	 */
	public Parameter(String type, String name) {
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
	 * Format a string from parameter name and type
	 * @return the UML formatted string for parameter name and type
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
