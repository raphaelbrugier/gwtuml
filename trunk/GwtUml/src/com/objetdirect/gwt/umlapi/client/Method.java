package com.objetdirect.gwt.umlapi.client;

public class Method extends ClassMember {

	public Parameter[] getParameters() {
		return parameters;
	}

	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
	}

	public Method(String returnType, String name, Parameter[] parameters) {
		super();
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String toString() {
		StringBuffer f = new StringBuffer();
		f.append(this.name);
		f.append("(");
		if (parameters != null && parameters.length > 0) {
			boolean first = true;
			for (int i = 0; i < parameters.length; i++) {
				if (!first)
					f.append(", ");
				else
					first = false;
				f.append(parameters[i].toString());
			}
		}
		f.append(")");
		if (this.returnType != null) {
			f.append(" : ");
			f.append(this.returnType);
		}
		return f.toString();
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	boolean validated = true;
	Parameter[] parameters;
	String returnType;
	String name;

}
