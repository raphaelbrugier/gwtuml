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

import java.io.Serializable;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.exceptions.GWTUMLAPIException;

/**
 * This class represent an attribute in a class
 * 
 * use the method parseAttribute(String chain) to parse a valid chain and construct a new UMLObjectAttribute A valid
 * chain is attributeName = "value"
 * 
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLObjectAttribute implements Serializable {

	/**
	 * Parse an attribute from a {@link String}
	 * 
	 * @param attributeToParse
	 *            The string containing an {@link UMLObjectAttribute} obtained with
	 *            {@link UMLObjectAttribute#toString()}
	 * 
	 * @return The new parsed {@link UMLObjectAttribute} or an empty one if there was a problem
	 */
	public static UMLObjectAttribute parseAttribute(final String attributeToParse) {

		final LexicalAnalyzer lex = new LexicalAnalyzer(attributeToParse);
		String name = "attributeName";
		String value = "value";
		try {

			LexicalAnalyzer.Token tk = lex.getToken();

			if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
				throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : attributeName = \"value\"");
			}
			name = tk.getContent();
			tk = lex.getToken();

			if (tk != null) {
				if ((tk.getType() != LexicalFlag.SIGN) || !tk.getContent().equals("=")) {
					throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : attributeName = \"value\"");
				}
				tk = lex.getToken();
				if ((tk == null) || ((tk.getType() != LexicalFlag.STRING) && (tk.getType() != LexicalFlag.INTEGER))) {
					throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : attributeName = \"value\"");
				}
				value = tk.getContent().replaceAll("\"", ""); // get the content and remove the quotes

			}
		} catch (final GWTUMLAPIException e) {
			Log.error(e.getMessage());
		}
		return new UMLObjectAttribute(name, value);
	}


	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	private String attributeName;

	private String stringValue;


	/** Default constructor ONLY for gwt-rpc serialization. */
	UMLObjectAttribute() {
	}

	/**
	 * Constructor of the attribute. The constructor is private, use the method parseAttribute(String chain) to parse a
	 * valid chain and construct a new UMLObjectAttribute A valid chain is attributeName = "value"
	 * 
	 * @param attributeName
	 *            Name of the attribute
	 * @param stringValue
	 *            Instance string of the attribute
	 */
	private UMLObjectAttribute(final String attributeName, final String stringValue) {
		this.attributeName = attributeName;
		this.stringValue = stringValue;
	}

	/**
	 * Return the {@link String} of this instance If the instance is a {@link Number} toString is applied
	 * 
	 * @return The instance {@link String} or "" if no instance is defined
	 */
	public String getValue() {
		return stringValue == null ? "" : stringValue;
	}

	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * @param attributeName
	 *            the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @param stringValue
	 *            the stringValue to set
	 */
	public void setValue(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * @return a formatted String to be serialized in a url.
	 */
	public String toUrl() {
		return attributeName + " = \"" + getValue() + "\"";
	}

	/**
	 * Format a string from attribute name and type
	 * 
	 * @return the UML formatted string for attribute name and type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toUrl();
	}
}
