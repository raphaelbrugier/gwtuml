package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * @author florian
 */
public class Note {

    private String text;

    public Note(final String text) {
	this.text = text;
    }

    public String getText() {
	return this.text;
    }

    public void setText(final String text) {
	this.text = text;
    }

}
