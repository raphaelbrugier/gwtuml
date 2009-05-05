/**
 * 
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represents an UML diagram
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 *
 */
public class UMLDiagram {

    /**
     * This enum lists all types of uml diagram that the application can draw   
     * 
     * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
     *
     */
    public enum Type {
	/**
	 * For a class diagram
	 */
	CLASS("class", true, false),
	/**
	 * For an object diagram
	 */
	OBJECT("object", false, true),
	/**
	 * For a class and object diagram
	 */
	HYBRID("class and object", true, true);
	
	private boolean isClassType;
	private boolean isObjectType;
	private String name;


	private Type(String name, boolean isClassType, boolean isObjectType) {
	    this.name = name;
	    this.isClassType = isClassType;
	    this.isObjectType = isObjectType;
	}

	/**
	 * Getter for the name
	 *
	 * @return the name
	 */
	public String getName() {
	    return this.name;
	}

	/**
	 * This method allows to know if a diagram can draw class diagram type objects
	 *
	 * @return True if the diagram can draw class diagram objects
	 */
	public boolean isClassType() {
	    return this.isClassType;
	}

	/**
	 * This method allows to know if a diagram can draw object diagram type objects
	 *
	 * @return True if the diagram can draw object diagram objects
	 */
	public boolean isObjectType() {
	    return this.isObjectType;
	}	
    }
    private Type type;
    
    /**
     * Constructor of {@link UMLDiagram}
     *
     * @param type The {@link Type} of the uml diagram
     */
    public UMLDiagram(Type type) {
	this.type = type;
    }

    /**
     * Getter for the {@link Type}
     *
     * @return the {@link Type}
     */
    public Type getType() {
        return this.type;
    }
}
