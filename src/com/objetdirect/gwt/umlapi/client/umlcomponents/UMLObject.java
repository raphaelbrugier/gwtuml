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
public class UMLObject extends UMLNode  {
    private String className;
    private String instanceName;
    private String stereotype;
    private ArrayList<UMLObjectAttribute> attributes;

    /**
     * Getter for the class name
     *
     * @return the class name
     */
    public final String getClassName() {
        return this.className;
    }
    /**
     * Getter for the class name
     *
     * @return the class name
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
     * Setter for the class name
     *
     * @param className the class name to set
     */
    public final void setClassName(String className) {
        this.className = className;
    }
    /**
     * Setter for the instance name
     *
     * @param instanceName the instance name to set
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
}
