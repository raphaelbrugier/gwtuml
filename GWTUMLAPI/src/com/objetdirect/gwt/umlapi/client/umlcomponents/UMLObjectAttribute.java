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

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;

/**
 * This class represent an attribute in a class
 * 
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLObjectAttribute extends UMLClassAttribute {

	/**
	 * Parse an attribute from a {@link String}
	 * 
	 * @param attributeToParse
	 *            The string containing an {@link UMLObjectAttribute} obtained with {@link UMLObjectAttribute#toString()}
	 * 
	 * @return The new parsed {@link UMLObjectAttribute} or an empty one if there was a problem
	 */
	public static UMLObjectAttribute parseAttribute(final String attributeToParse) {

		final LexicalAnalyzer lex = new LexicalAnalyzer(attributeToParse);
		String type = "";
		String name = "";
		String instance = "";
		UMLVisibility visibility = UMLVisibility.PACKAGE;
		try {

			LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk != null) && (tk.getType() != LexicalFlag.VISIBILITY)) {
				visibility = UMLVisibility.PACKAGE;
			} else if (tk != null) {
				visibility = UMLVisibility.getVisibilityFromToken(tk.getContent().charAt(0));
				tk = lex.getToken();
			}
			if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
				throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : type = instance'");
			}
			name = tk.getContent();
			tk = lex.getToken();
			if (tk != null) {
				if ((tk.getType() == LexicalFlag.SIGN) && tk.getContent().equals(":")) {

					tk = lex.getToken();
					if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
						throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : type = instance'");
					}
					type = tk.getContent();
					tk = lex.getToken();
				}
			}

			if (tk != null) {
				if ((tk.getType() != LexicalFlag.SIGN) || !tk.getContent().equals("=")) {
					throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : type = instance'");
				}
				tk = lex.getToken();
				if ((tk == null) || ((tk.getType() != LexicalFlag.STRING) && (tk.getType() != LexicalFlag.INTEGER))) {
					throw new GWTUMLAPIException("Invalid attribute format : " + attributeToParse + " doesn't match 'identifier : type = instance'");
				}
				instance = tk.getContent();
			}
		} catch (final GWTUMLAPIException e) {
			Log.error(e.getMessage());
		}
		return new UMLObjectAttribute(visibility, type, name, instance);
	}

	private String	stringInstance;

	private Number	numberInstance;

	/**
	 * Constructor of the attribute
	 * 
	 * @param visibility
	 * @param type
	 *            Type of the attribute
	 * @param name
	 *            Name of the attribute
	 * @param instance
	 *            Instance {@link Number}r of the attribute
	 */
	public UMLObjectAttribute(final UMLVisibility visibility, final String type, final String name, final Number instance) {
		super(visibility, type, name);
		this.setInstance(instance);
	}

	/**
	 * Constructor of the attribute
	 * 
	 * @param visibility
	 * @param type
	 *            Type of the attribute
	 * @param name
	 *            Name of the attribute
	 * @param instance
	 *            Instance string of the attribute
	 */
	public UMLObjectAttribute(final UMLVisibility visibility, final String type, final String name, final String instance) {
		super(visibility, type, name);
		this.setInstance(instance);
	}

	/**
	 * Return the {@link String} of this instance If the instance is a {@link Number} toString is applied
	 * 
	 * @return The instance {@link String} or "" if no instance is defined
	 */
	public String getInstance() {
		if (this.stringInstance != null) {
			return this.stringInstance;
		}
		if (this.numberInstance != null) {
			return this.numberInstance.toString();
		}
		return "";
	}

	/**
	 * Set the instance with a {@link Number}
	 * 
	 * @param numberInstance
	 *            The {@link Number} value of this attribute instance
	 */
	public void setInstance(final Number numberInstance) {
		this.numberInstance = numberInstance;
	}

	/**
	 * Set the instance with a {@link String}
	 * 
	 * @param stringInstance
	 *            The {@link String} value of this attribute instance
	 */
	public void setInstance(final String stringInstance) {
		this.stringInstance = stringInstance;
	}

	/**
	 * Format a string from attribute name and type
	 * 
	 * @return the UML formatted string for attribute name and type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder f = new StringBuilder();
		f.append(this.visibility);
		f.append(this.name);
		if ((this.type != null) && !this.type.equals("")) {
			f.append(" : ");
			f.append(this.type);
		}
		if (this.stringInstance != null) {
			f.append(" = ");
			f.append(this.stringInstance);
		} else if (this.numberInstance != null) {
			f.append(" = ");
			f.append(this.numberInstance);
		}
		return f.toString();
	}

}
