package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class Note extends UMLComponent {

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
