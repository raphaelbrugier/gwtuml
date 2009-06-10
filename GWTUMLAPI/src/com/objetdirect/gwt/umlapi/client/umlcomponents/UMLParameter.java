/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2009 Objet Direct Contact: gwtuml@googlegroups.com
 * 
 * GWTUML is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * GWTUML is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with GWTUML. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.umlcomponents;

/**
 * This class represent a parameter of a method
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @author Henri Darmet
 */
public class UMLParameter {

	private String	name;

	private String	type;

	/**
	 * Constructor of the parameter
	 * 
	 * @param type
	 *            Type of the parameter
	 * @param name
	 *            Name of the parameter
	 */
	public UMLParameter(final String type, final String name) {
		super();
		this.type = type;
		this.name = name;
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter for the type
	 * 
	 * @return the type
	 * 
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Setter for the name
	 * 
	 * @param name
	 *            to be set
	 * 
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Setter for the type
	 * 
	 * @param type
	 *            to be set
	 * 
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * Format a string from parameter name and type
	 * 
	 * @return the UML formatted string for parameter name and type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder f = new StringBuilder();
		f.append(this.name);
		if (this.type != null) {
			f.append(" : ");
			f.append(this.type);
		}
		return f.toString();
	}
}
