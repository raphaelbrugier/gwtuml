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

import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkKind;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkStyle;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink;

/**
 * This class represent an uml message between two {@link UMLClass}es
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLMessage extends UMLLink {

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private LinkAdornment leftAdornment;
	private LinkAdornment rightAdornment;
	private LinkStyle linkStyle;

	/** Default constructor ONLY for gwt-rpc serialization. */
	UMLMessage() {
	};

	/**
	 * Constructor of Message
	 * 
	 * @param messageKind
	 *            : The type of this message
	 */
	public UMLMessage(final LinkKind messageKind) {
		super(messageKind);
		linkStyle = messageKind.getDefaultLinkStyle();
		leftAdornment = messageKind.getDefaultLeftAdornment();
		rightAdornment = messageKind.getDefaultRightAdornment();
		name = "";
	}

	/**
	 * Getter for the left adornment
	 * 
	 * @return the left adornment
	 */
	public LinkAdornment getLeftAdornment() {
		return leftAdornment;
	}

	/**
	 * Getter for the link style
	 * 
	 * @return the link style
	 */
	public LinkStyle getLinkStyle() {
		return linkStyle;
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
	 * Getter for the right adornment
	 * 
	 * @return the right adornment
	 */
	public LinkAdornment getRightAdornment() {
		return rightAdornment;
	}

	/**
	 * Reverse the current message
	 */
	public void reverse() {
		final LinkAdornment tempAdornment = leftAdornment;
		leftAdornment = rightAdornment;
		rightAdornment = tempAdornment;
	}

	/**
	 * Setter for the left adornment
	 * 
	 * @param leftAdornment
	 *            the left adornment to set
	 */
	public void setLeftAdornment(final LinkAdornment leftAdornment) {
		this.leftAdornment = leftAdornment;
	}

	/**
	 * Setter for the link style
	 * 
	 * @param linkStyle
	 *            the link style to set
	 */
	public void setLinkStyle(final LinkStyle linkStyle) {
		this.linkStyle = linkStyle;
	}

	/**
	 * Setter for the name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Setter for the right adornment
	 * 
	 * @param rightAdornment
	 *            the right adornment to set
	 */
	public void setRightAdornment(final LinkAdornment rightAdornment) {
		this.rightAdornment = rightAdornment;
	}

}
