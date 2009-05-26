/**
 * 
 */
/*
 *    This file is part of the GWTUML project
 *    and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> 
 *    for Objet Direct <http://wwww.objetdirect.com>
 *    
 *    Copyright © 2009 Objet Direct
 *    Contact: gwtuml@googlegroups.com
 *    
 *    GWTUML is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    GWTUML is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
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
	CLASS("class", true, false, 0),
	/**
	 * For an object diagram
	 */
	OBJECT("object", false, true, 1),
	/**
	 * For a class and object diagram
	 */
	HYBRID("class and object", true, true, 2);
	
	private boolean isClassType;
	private boolean isObjectType;
	private String name;
	private Integer index;

	/**
	 * Getter for the index
	 *
	 * @return the index
	 */
	public Integer getIndex() {
	    return this.index;
	}

	private Type(String name, boolean isClassType, boolean isObjectType, Integer index) {
	    this.name = name;
	    this.isClassType = isClassType;
	    this.isObjectType = isObjectType;
	    this.index = index;
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
	
	/**
	 * This method allows to know if a diagram can draw class diagram and object diagram type objects
	 *
	 * @return True if the diagram can draw class diagram and object diagram objects
	 */
	public boolean isHybridType() {
	    return this.isClassType && this.isObjectType;
	}
	
	/**
	 * Return a the UMLDiagram type that corresponds to the index
	 * 
	 * @param uMLDiagramIndex the index corresponding to the diagram to retrieve
	 * @return The {@link UMLDiagram.Type} corresponding to the index
	 */
	public static UMLDiagram.Type getUMLDiagramFromIndex(final int uMLDiagramIndex) {
	    for (final UMLDiagram.Type type : UMLDiagram.Type.values()) {
		if (type.index == uMLDiagramIndex) {
		    return type;
		}
	    }
	    return UMLDiagram.Type.HYBRID;
	}
    }
    private Type type;
    
    /**
     * Constructor of {@link UMLDiagram}
     *
     * @param type The {@link Type} of the uml diagram
     */
    public UMLDiagram(Type type) {
	super();
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