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
 * This class represent an uml relation between two {@link UMLClass}es
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLRelation extends UMLLink {

	private String			leftCardinality;
	private String			leftConstraint;
	private String			leftRole;
	private String			name;
	private String			rightCardinality;
	private String			rightConstraint;
	private String			rightRole;
	private LinkAdornment	leftAdornment;
	private LinkAdornment	rightAdornment;
	private LinkStyle		linkStyle;

	/**
	 * Constructor of Relation
	 * 
	 * @param relationKind
	 *            : The type of this relation
	 */
	public UMLRelation(final LinkKind relationKind) {
		super(relationKind);
		this.linkStyle = relationKind.getDefaultLinkStyle();
		this.leftAdornment = relationKind.getDefaultLeftAdornment();
		this.rightAdornment = relationKind.getDefaultRightAdornment();
		this.leftCardinality = relationKind.getDefaultLeftCardinality();
		this.rightCardinality = relationKind.getDefaultRightCardinality();
		this.leftConstraint = "";
		this.rightConstraint = "";
		this.leftRole = "";
		this.rightRole = "";
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
	 * Getter for the leftCardinality
	 * 
	 * @return the leftCardinality
	 */
	public String getLeftCardinality() {
		return this.leftCardinality;
	}

	/**
	 * Getter for the leftConstraint
	 * 
	 * @return the leftConstraint
	 */
	public String getLeftConstraint() {
		return this.leftConstraint;
	}

	/**
	 * Getter for the leftRole
	 * 
	 * @return the leftRole
	 */
	public String getLeftRole() {
		return this.leftRole;
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
	 * Getter for the rightCardinality
	 * 
	 * @return the rightCardinality
	 */
	public String getRightCardinality() {
		return this.rightCardinality;
	}

	/**
	 * Getter for the rightConstraint
	 * 
	 * @return the rightConstraint
	 */
	public String getRightConstraint() {
		return this.rightConstraint;
	}

	/**
	 * Getter for the rightRole
	 * 
	 * @return the rightRole
	 */
	public String getRightRole() {
		return this.rightRole;
	}

	/**
	 * Reverse the current relation
	 */
	public void reverse() {
		final LinkAdornment tempAdornment = this.leftAdornment;
		final String tempCardinality = this.leftCardinality;
		final String tempConstraint = this.leftConstraint;
		final String tempRole = this.leftRole;
		this.leftAdornment = this.rightAdornment;
		this.leftCardinality = this.rightCardinality;
		this.leftConstraint = this.rightConstraint;
		this.leftRole = this.rightRole;
		this.rightAdornment = tempAdornment;
		this.rightCardinality = tempCardinality;
		this.rightConstraint = tempConstraint;
		this.rightRole = tempRole;
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
	 * Setter for the leftCardinality
	 * 
	 * @param leftCardinality
	 *            the leftCardinality to set
	 */
	public void setLeftCardinality(final String leftCardinality) {
		this.leftCardinality = leftCardinality;
	}

	/**
	 * Setter for the leftConstraint
	 * 
	 * @param leftConstraint
	 *            the leftConstraint to set
	 */
	public void setLeftConstraint(final String leftConstraint) {
		this.leftConstraint = leftConstraint;
	}

	/**
	 * Setter for the leftRole
	 * 
	 * @param leftRole
	 *            the leftRole to set
	 */
	public void setLeftRole(final String leftRole) {
		this.leftRole = leftRole;
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

	/**
	 * Setter for the rightCardinality
	 * 
	 * @param rightCardinality
	 *            the rightCardinality to set
	 */
	public void setRightCardinality(final String rightCardinality) {
		this.rightCardinality = rightCardinality;
	}

	/**
	 * Setter for the rightConstraint
	 * 
	 * @param rightConstraint
	 *            the rightConstraint to set
	 */
	public void setRightConstraint(final String rightConstraint) {
		this.rightConstraint = rightConstraint;
	}

	/**
	 * Setter for the rightRole
	 * 
	 * @param rightRole
	 *            the rightRole to set
	 */
	public void setRightRole(final String rightRole) {
		this.rightRole = rightRole;
	}

}
