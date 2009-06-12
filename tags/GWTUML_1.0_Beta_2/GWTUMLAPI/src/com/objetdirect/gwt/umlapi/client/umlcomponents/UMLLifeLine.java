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

import java.util.Arrays;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;

/**
 * This class represent a LifeLine uml component
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLLifeLine extends UMLComponent {

	/**
	 * Parse a name from a {@link String}
	 * 
	 * @param nameToParse
	 *            The string containing a name
	 * 
	 * @return The list of the new parsed name and instance or an empty one if there was a problem
	 */
	public static List<String> parseName(final String nameToParse) {
		if (nameToParse.equals("")) {
			return Arrays.asList("", "");
		}
		final LexicalAnalyzer lex = new LexicalAnalyzer(nameToParse);
		String instance = "";
		String name = "";
		try {

			LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk != null) && (tk.getType() == LexicalFlag.IDENTIFIER)) {

				instance = tk.getContent();
				tk = lex.getToken();
			}
			if (tk != null) {
				if ((tk.getType() != LexicalFlag.SIGN) || !tk.getContent().equals(":")) {
					throw new GWTUMLAPIException("Invalid life line name format : " + nameToParse + " doesn't match 'instance : name'");
				}
				tk = lex.getToken();
				if ((tk != null) && (tk.getType() == LexicalFlag.IDENTIFIER)) {
					name = tk.getContent();
				}
			}
			if (name.equals("") && !instance.equals("")) {
				name = instance;
				instance = "";
			}

		} catch (final GWTUMLAPIException e) {
			Log.error(e.getMessage());
		}
		return Arrays.asList(instance, name);
	}

	private String	name;

	private String	instance;

	/**
	 * Constructor of the LifeLine
	 * 
	 * @param name
	 *            The name of the LifeLine
	 * @param instance
	 *            The instance of the LifeLine
	 */
	public UMLLifeLine(final String name, final String instance) {
		super();
		this.name = name;
		this.instance = instance;
	}

	/**
	 * Getter for the instance of the LifeLine
	 * 
	 * @return The instance of the LifeLine
	 */
	public String getInstance() {
		return this.instance;
	}

	/**
	 * Getter for the name of the LifeLine
	 * 
	 * @return The name of the LifeLine
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter for the instance of the LifeLine
	 * 
	 * @param instance
	 *            The instance of the LifeLine
	 */
	public void setInstance(final String instance) {
		this.instance = instance;
	}

	/**
	 * Setter for the name of the LifeLine
	 * 
	 * @param name
	 *            The name of the LifeLine
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.instance + ":" + this.name;
	}
}
