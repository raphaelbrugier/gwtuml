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

import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.artifacts.LinkArtifact.LinkStyle;

/**
 * This class represent an uml message between two {@link UMLClass}es
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLMessage extends UMLLink {

	private String			name;

	private LinkAdornment	leftAdornment;
	private LinkAdornment	rightAdornment;
	private LinkStyle		linkStyle;

	/**
	 * Constructor of Message
	 * 
	 * @param messageKind
	 *            : The type of this message
	 */
	public UMLMessage(final LinkKind messageKind) {
		super(messageKind);
		this.linkStyle = messageKind.getDefaultLinkStyle();
		this.leftAdornment = messageKind.getDefaultLeftAdornment();
		this.rightAdornment = messageKind.getDefaultRightAdornment();
		this.name = "";
	}

	/**
	 * Getter for the left adornment
	 * 
	 * @return the left adornment
	 */
	public LinkAdornment getLeftAdornment() {
		return this.leftAdornment;
	}

	/**
	 * Getter for the link style
	 * 
	 * @return the link style
	 */
	public LinkStyle getLinkStyle() {
		return this.linkStyle;
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
	 * Getter for the right adornment
	 * 
	 * @return the right adornment
	 */
	public LinkAdornment getRightAdornment() {
		return this.rightAdornment;
	}

	/**
	 * Reverse the current message
	 */
	public void reverse() {
		final LinkAdornment tempAdornment = this.leftAdornment;
		this.leftAdornment = this.rightAdornment;
		this.rightAdornment = tempAdornment;
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
