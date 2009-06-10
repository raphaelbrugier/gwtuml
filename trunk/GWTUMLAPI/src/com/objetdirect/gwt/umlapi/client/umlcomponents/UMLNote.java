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
 * This class represent a Note uml component
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLNote extends UMLComponent {

	private String	text;

	/**
	 * Constructor of the Note
	 * 
	 * @param text
	 *            The text contained by the Note
	 */
	public UMLNote(final String text) {
		super();
		this.text = text;
	}

	/**
	 * Getter for the text contained by the Note
	 * 
	 * @return The text contained by the Note
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Setter for the text contained by the Note
	 * 
	 * @param text
	 *            The text contained by the Note
	 */
	public void setText(final String text) {
		this.text = text;
	}

}
