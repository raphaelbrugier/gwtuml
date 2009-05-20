/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;

/**
 * This class represents an object uml component
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class UMLObject extends UMLNode  {
    private String objectName;
    private String instanceName;
    private String stereotype;
    private ArrayList<UMLObjectAttribute> attributes;
    /**
     * Constructor of an {@link UMLObject}
     *
     * @param objectInstance The name of this instance
     * @param objectName The name of this object
     */
    public UMLObject(String objectInstance, String objectName) {
	super();
	this.instanceName = objectInstance;
	this.objectName = objectName;
    }
    /**
     * Parse a stereotype from a {@link String}
     * 
     * @param stereotypeToParse The string containing a stereotype
     * 
     * @return The new parsed stereotype or an empty one if there was a problem
     */
    public static String parseStereotype(String stereotypeToParse) {
	if(stereotypeToParse.equals("")) return "";
	final LexicalAnalyzer lex = new LexicalAnalyzer(stereotypeToParse);
	try {
	    LexicalAnalyzer.Token tk = lex.getToken();
	    if (tk == null || tk.getType() != LexicalFlag.IDENTIFIER) {
		throw new UMLDrawerException(
			"Invalid object stereotype : " + stereotypeToParse + " doesn't repect uml conventions");
	    }
	    return tk.getContent();
	} catch (final UMLDrawerException e) {
	    Log.error(e.getMessage());
	}
	return "";
    }
    /**
     * Parse a name from a {@link String}
     * 
     * @param nameToParse The string containing a name
     * 
     * @return The new parsed name or an empty one if there was a problem
     */
    public static List<String> parseName(String nameToParse) {
	if(nameToParse.equals("")) return Arrays.asList("", "");
	final LexicalAnalyzer lex = new LexicalAnalyzer(nameToParse);
	String instance = "";
	String name = "";
	try {

	    LexicalAnalyzer.Token tk = lex.getToken();
	    if (tk != null && tk.getType() == LexicalFlag.IDENTIFIER) {
		
	    instance = tk.getContent();
	    tk = lex.getToken();
	    }
	    if (tk != null) {
		if (tk.getType() != LexicalFlag.SIGN
			|| !tk.getContent().equals(":")) {
		    throw new UMLDrawerException(
			    "Invalid object name format : " + nameToParse + " doesn't match 'instance : name'");
		}
		tk = lex.getToken();
		if (tk != null && tk.getType() == LexicalFlag.IDENTIFIER) {
		   name = tk.getContent();
		}
	    }
	    if(name.equals("") && !instance.equals("")) {
		name = instance;
		instance = "";
	    }

	} catch (final UMLDrawerException e) {
	    Log.error(e.getMessage());
	}
	return Arrays.asList(instance, name);
    }
    /**
     * Getter for the object name
     *
     * @return the object name
     */
    public final String getObjectName() {
	return this.objectName;
    }
    /**
     * Getter for the object instance name
     *
     * @return the object instance name
     */
    public final String getInstanceName() {
	return this.instanceName;
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
    public final ArrayList<UMLObjectAttribute> getObjectAttributes() {
	return this.attributes;
    }
    /**
     * Setter for the object name
     *
     * @param objectName the object name to set
     */
    public final void setObjectName(String objectName) {
	this.objectName = objectName;
    }
    /**
     * Setter for the object instance name
     *
     * @param instanceName the object instance name to set
     */
    public final void setInstanceName(String instanceName) {
	this.instanceName = instanceName;
    }
    /**
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
    public final void setObjectAttributes(ArrayList<UMLObjectAttribute> attributes) {
	this.attributes = attributes;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.instanceName + ":" + this.objectName;
    }
}
