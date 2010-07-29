/*
 * This file is part of the GWTUML project and was written by Brugier Raphael <raphael-dot-brugier.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct Contact: gwtuml@googlegroups.com
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
 * This enumeration list all return types
 * 
 * @author Raphael Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public enum UMLType {
	STRING("String"),

	INT("int"),

	INTEGER("Integer"),

	LONG("long"),

	WRAPPED_LONG("Long"),

	BYTE("byte"),

	WRAPPED_BYTE("Byte"),

	SHORT("short"),

	WRAPPED_SHORT("Short"),

	BOOLEAN("boolean"),

	WRAPPED_BOOLEAN("Boolean"),

	CHAR("char"),

	CHARACTER("Character"),

	FLOAT("float"),

	WRAPPED_FLOAT("Float"),

	DOUBLE("double"),

	WRAPPED_DOUBLE("Double"),
	
	DATE("Date"),

	CUSTOM("custom");

	private String type;

	/**
	 * Return the type corresponding to the given string
	 * 
	 * @param type
	 * @return
	 */
	public static UMLType getUMLTypeFromString(String type) {

		for (UMLType umlType : UMLType.values()) {
			if (umlType.type.equals(type)) {
				return umlType;
			}
		}

		return CUSTOM;
	}

	/** Default constructor ONLY for gwt-rpc serialization. */
	private UMLType() {
	}

	private UMLType(String type) {
		this.type = type;
	}
}
