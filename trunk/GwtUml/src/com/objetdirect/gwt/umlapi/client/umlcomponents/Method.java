package com.objetdirect.gwt.umlapi.client.umlcomponents;

public class Method extends ClassMember {

	String name;

	Parameter[] parameters;

	String returnType;

	boolean validated = true;

	public Method(String returnType, String name, Parameter[] parameters) {
		super();
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}

	public Parameter[] getParameters() {
		return parameters;
	}

	public String getReturnType() {
		return returnType;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParameters(Parameter[] parameters) {
		this.parameters = parameters;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	@Override
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

}
