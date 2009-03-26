package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * @author  florian
 */
public class Note {

	/**
	 * @uml.property  name="text"
	 */
	private String text;

	public Note(String text) {
		this.text = text;
	}

	/**
	 * @return
	 * @uml.property  name="text"
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 * @uml.property  name="text"
	 */
	public void setText(String text) {
		this.text = text;
	}
	
}
