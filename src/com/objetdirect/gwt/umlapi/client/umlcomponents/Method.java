package com.objetdirect.gwt.umlapi.client.umlcomponents;

import java.util.List;

/**
 * This class represent a method in a class
 * 
 * @author hdarmet
 * @author fmounier
 */
public class Method {

    private String name;

    private List<Parameter> parameters;

    private String returnType;

    private boolean validated = true;

    private Visibility visibility;

    /**
     * Constructor of the method
     * 
     * @param visibility
     * @param returnType
     *            The return type of the method
     * @param name
     *            The name of the method
     * @param parameters
     *            The parameters list of this method
     */
    public Method(final Visibility visibility, final String returnType,
	    final String name, final List<Parameter> parameters) {
	super();
	this.visibility = visibility;
	this.returnType = returnType;
	this.name = name;
	this.parameters = parameters;
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
     * Getter for the parameters list
     * 
     * @return the parameters list
     * 
     */
    public List<Parameter> getParameters() {
	return this.parameters;
    }

    /**
     * Getter for the return type
     * 
     * @return the return type
     * 
     */
    public String getReturnType() {
	return this.returnType;
    }

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
     * @param name
     *            to be set
     * 
     */
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * Setter for the parameters list
     * 
     * @param parameters
     *            list to be set
     * 
     */
    public void setParameters(final List<Parameter> parameters) {
	this.parameters = parameters;
    }

    /**
     * Setter for the return type
     * 
     * @param returnType return type to be set
     * 
     */
    public void setReturnType(final String returnType) {
	this.returnType = returnType;
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

    public void setVisibility(final Visibility visibility) {
	this.visibility = visibility;
    }

    /**
     * Format a string from method name, parameters and return type
     * 
     * @return the UML formatted string for method name, parameters and return
     *         type
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	final StringBuffer f = new StringBuffer();
	f.append(this.visibility);
	f.append(this.name);
	f.append("(");
	if (this.parameters != null && this.parameters.size() > 0) {
	    boolean first = true;
	    for (final Parameter parameter : this.parameters) {
		if (!first) {
		    f.append(", ");
		} else {
		    first = false;
		}
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
