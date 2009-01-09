package com.objetdirect.gwt.umlapi.client;

public class Attribute extends ClassMember {
	
	public Attribute(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean isValidated() {
		return validated;
	}
	
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	
	public String toString() {
		StringBuffer f = new StringBuffer();
		f.append(this.name);
		if (this.type!=null) {
			f.append(" : " );
			f.append(this.type);
		}
		return f.toString();
	}
	
	String type;
	String name;
	boolean validated = true;
}
