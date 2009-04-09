package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represent a Note uml component
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class Note extends UMLComponent {

    private String text;

    /**
     * Constructor of the Note
     *
     * @param text The text contained by the Note
     */
    public Note(final String text) {
	this.text = text;
    }

    /**
     * Getter for the text contained by the Note
     * @return The text contained by the Note
     */
    public String getText() {
	return this.text;
    }

    /**
     * Setter for the text contained by the Note
     * @param text The text contained by the Note
     */
    public void setText(final String text) {
	this.text = text;
    }

}
