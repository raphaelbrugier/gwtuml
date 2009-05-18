/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import java.util.ArrayList;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;

/**
 * This class represents a class uml component
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class UMLClass extends UMLNode  {
    private String name;
    
    /**
     * Constructor of {@link UMLClass}
     *
     * @param name
     */
    public UMLClass(String name) {
	super();
	this.name = name;
    }
    private String stereotype;
    private ArrayList<UMLClassAttribute> attributes;
    private ArrayList<UMLClassMethod> methods;

    /**
     * Parse a name or a stereotype from a {@link String}
     * 
     * @param stringToParse The string containing a name or a stereotype
     * 
     * @return The new parsed name or stereotype or an empty one if there was a problem
     */
    public static String parseNameOrStereotype(String stringToParse) {
	final LexicalAnalyzer lex = new LexicalAnalyzer(stringToParse);
	try {
	    LexicalAnalyzer.Token tk = lex.getToken();
	    if (tk == null || tk.getType() != LexicalFlag.IDENTIFIER) {
		throw new UMLDrawerException(
		"Invalid class name/stereotype : " + stringToParse + " doesn't repect uml conventions");
	    }
	    return tk.getContent();
	} catch (final UMLDrawerException e) {
	    Log.error(e.getMessage());
	}
	return "";
    }
    /**
     * Getter for the name
     *
     * @return the name
     */
    public final String getName() {
	return this.name;
    }
    /**
     * Getter for the stereotype
     *
     * @return the stereotype
     */
    public final String getStereotype() {
	return this.stereotype;
    }
    /**
     * Getter for the attributes
     *
     * @return the attributes
     */
    public final ArrayList<UMLClassAttribute> getAttributes() {
	return this.attributes;
    }
    /**
     * Getter for the methods
     *
     * @return the methods
     */
    public final ArrayList<UMLClassMethod> getMethods() {
	return this.methods;
    }
    /**
     * Setter for the name
     *
     * @param name the name to set
     */
    public final void setName(String name) {
	this.name = name;
    }
    /**
     * Setter for the stereotype
     *
     * @param stereotype the stereotype to set
     */
    public final void setStereotype(String stereotype) {
	this.stereotype = stereotype;
    }
    /**
     * Setter for the attributes
     *
     * @param attributes the attributes to set
     */
    public final void setAttributes(ArrayList<UMLClassAttribute> attributes) {
	this.attributes = attributes;
    }
    /**
     * Setter for the methods
     *
     * @param methods the methods to set
     */
    public final void setMethods(ArrayList<UMLClassMethod> methods) {
	this.methods = methods;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.name;
    }
}