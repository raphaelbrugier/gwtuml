package com.objetdirect.gwt.umlapi.client.umlcomponents;

public class Attribute extends ClassMember {

	String name;

	String type;

	boolean validated = true;

	public Attribute(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
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
