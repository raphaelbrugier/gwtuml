/*
 * This file is part of the Gwt-Uml project and was written by Raphaël Brugier <raphael dot brugier at gmail dot com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct
 * 
 * Gwt-Uml is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Gwt-Uml is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Gwt-Generator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This enum lists all types of uml diagram that the application can draw
 * 
 * @author Raphaël Brugier <raphael dot brugier at gmail dot com>
 */
public enum DiagramType {
	/**
	 * For a class diagram
	 */
	CLASS(0, "class"),
	/**
	 * For an object diagram
	 */
	OBJECT(1, "object"),
	/**
	 * For a sequence diagram
	 */
	SEQUENCE(3, "sequence");

	private String name;
	private int index;

	private DiagramType(final int index, final String name) {
		this.name = name;
		this.index = index;
	}

	public static DiagramType fromIndex(int index) {
		for (DiagramType d : DiagramType.values()) {
			if (d.index == index) {
				return d;
			}
		}
		return CLASS;
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
}