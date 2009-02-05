package com.objetdirect.gwt.umlapi.client.umlcomponents;

import java.util.List;

/**
 * This class represent a method in a class
 * @author hdarmet
 * @author fmounier
 *
 */
public class Method extends ClassMember {

	private String name;
	private List<Parameter>  parameters;
	private String returnType;
	private boolean validated = true;

	/**
	 * Constructor of the method
	 * @param returnType The return type of the method
	 * @param name The name of the method
	 * @param parameters The parameters list of this method
	 */
	public Method(String returnType, String name, List<Parameter> parameters) {
		super();
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
	}
	
	/**
	 * Getter for the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Getter for the parameters list
	 * @return the parameters list
	 */
	public List<Parameter>  getParameters() {
		return parameters;
	}
	
	/**
	 * Getter for the return type
	 * @return the return type
	 */
	public String getReturnType() {
		return returnType;
	}
	
	/**
	 * Get the validated state of the attribute
	 * @return true if validated, false otherwise
	 */
	public boolean isValidated() {
		return validated;
	}

	/**
	 * Setter for the name
	 * @param name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for the parameters list
	 * @param parameters list to be set
	 */
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Setter for the return type
	 * @param return type to be set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	/**
	 * Set the validation state
	 * @param boolean for validated validation state 
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	
	/**
	 * Format a string from method name, parameters and return type
	 * @return the UML formatted string for method name, parameters and return type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer f = new StringBuffer();
		f.append(this.name);
		f.append("(");
		if (parameters != null && parameters.size() > 0) {
			boolean first = true;
			for (Parameter parameter : parameters) {
				if (!first)
					f.append(", ");
				else
					first = false;
				f.append(parameter.toString());	
			}
		}
		f.append(")");
		if (this.returnType != null) {
			f.append(" : ");
			f.append(this.returnType);
		}
		return f.toString();
	}

}
