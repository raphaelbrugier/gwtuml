/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

import java.util.ArrayList;

/**
 * This class represents a class uml component
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class Class extends UMLComponent  {
    private String name;
    private String stereotype;
    private ArrayList<Attribute> attributes;
    private ArrayList<Method> methods;
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
    public final ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }
    /**
     * Getter for the methods
     *
     * @return the methods
     */
    public final ArrayList<Method> getMethods() {
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
    public final void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }
    /**
     * Setter for the methods
     *
     * @param methods the methods to set
     */
    public final void setMethods(ArrayList<Method> methods) {
        this.methods = methods;
    }
}
