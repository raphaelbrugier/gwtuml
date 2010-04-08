/*
 * This file is part of the GWTUML project and was written by Mounier Florian <mounier-dot-florian.at.gmail'dot'com> for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright Â© 2009 Objet Direct Contact: gwtuml@googlegroups.com
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
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkAdornment;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.LinkStyle;

/**
 * This class represent an uml relation between two {@link UMLClass}es
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @contributor Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class UMLRelation extends UMLLink {

	private String			name;

	private String			leftCardinality;
	private String			leftConstraint;
	private String			leftRole;
	private String			leftStereotype;
	private LinkAdornment	leftAdornment;
	private UMLClass		leftTarget;

	private String			rightCardinality;
	private String			rightConstraint;
	private String			rightRole;
	private String			rightStereotype;
	private LinkAdornment	rightAdornment;
	private UMLClass		rightTarget;
	
	private LinkStyle		linkStyle;


	/**
	 * Default constructor for gwt-rpc serialization
	 */
	public UMLRelation () {
	}
	
	/**
	 * Constructor of Relation
	 * 
	 * @param relationKind
	 *            : The type of this relation
	 */
	public UMLRelation(final LinkKind relationKind) {
		super(relationKind);
		this.linkStyle = relationKind.getDefaultLinkStyle();
		this.name = "";

		this.leftCardinality = relationKind.getDefaultLeftCardinality();
		this.leftConstraint = "";
		this.leftRole = "";
		this.leftStereotype = "";
		this.leftAdornment = relationKind.getDefaultLeftAdornment();
		
		this.rightCardinality = relationKind.getDefaultRightCardinality();
		this.rightConstraint = "";
		this.rightRole = "";
		this.rightStereotype = "";
		this.rightAdornment = relationKind.getDefaultRightAdornment();
		
//		Log.debug("UMLRelation::UMLRelation()\n" + this);
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
	 * @return the leftStereotype
	 */
	public String getLeftStereotype() {
		return leftStereotype;
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
	 * @return the rightStereotype
	 */
	public String getRightStereotype() {
		return rightStereotype;
	}

	/**
	 * Setter for the left adornment
	 * 
	 * @param leftAdornment
	 *            the left adornment to set
	 */
	public void setLeftAdornment(final LinkAdornment leftAdornment) {
		this.leftAdornment = leftAdornment;
//		Log.debug("UMLRelation::setLeftAdornment \n" + this);
	}

	/**
	 * Setter for the leftCardinality
	 * 
	 * @param leftCardinality
	 *            the leftCardinality to set
	 */
	public void setLeftCardinality(final String leftCardinality) {
		this.leftCardinality = leftCardinality;
//		Log.debug("UMLRelation::setLeftCardinality() \n" + this);
	}

	/**
	 * Setter for the leftConstraint
	 * 
	 * @param leftConstraint
	 *            the leftConstraint to set
	 */
	public void setLeftConstraint(final String leftConstraint) {
		this.leftConstraint = leftConstraint;
//		Log.debug("UMLRelation::setLeftConstraint \n" + this);
	}

	/**
	 * Setter for the leftRole
	 * 
	 * @param leftRole
	 *            the leftRole to set
	 */
	public void setLeftRole(final String leftRole) {
		this.leftRole = leftRole;
//		Log.debug("UMLRelation::setLeftRole \n" + this);
	}

	/**
	 * @param leftStereotype the leftStereotype to set
	 */
	public void setLeftStereotype(String leftStereotype) {
		this.leftStereotype = leftStereotype;
	}
	
	/**
	 * Setter for the link style
	 * 
	 * @param linkStyle
	 *            the link style to set
	 */
	public void setLinkStyle(final LinkStyle linkStyle) {
		this.linkStyle = linkStyle;
//		Log.debug("UMLRelation::setLinkStyle \n" + this);
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
//		Log.debug("UMLRelation::setRightAdornment \n" + this);
	}

	/**
	 * Setter for the rightCardinality
	 * 
	 * @param rightCardinality
	 *            the rightCardinality to set
	 */
	public void setRightCardinality(final String rightCardinality) {
		this.rightCardinality = rightCardinality;
//		Log.debug("UMLRelation::setRightCardinality() \n" + this);
	}

	/**
	 * Setter for the rightConstraint
	 * 
	 * @param rightConstraint
	 *            the rightConstraint to set
	 */
	public void setRightConstraint(final String rightConstraint) {
		this.rightConstraint = rightConstraint;
//		Log.debug("UMLRelation::setRightConstraint \n" + this);
	}

	/**
	 * Setter for the rightRole
	 * 
	 * @param rightRole
	 *            the rightRole to set
	 */
	public void setRightRole(final String rightRole) {
		this.rightRole = rightRole;
//		Log.debug("UMLRelation::setRightRole \n" + this);
	}

	/**
	 * @param rightStereotype the rightStereotype to set
	 */
	public void setRightStereotype(String rightStereotype) {
		this.rightStereotype = rightStereotype;
	}

	/**
	 * @return the leftTarget
	 */
	public UMLClass getLeftTarget() {
		return leftTarget;
	}

	/**
	 * @param leftTarget the leftTarget to set
	 */
	public void setLeftTarget(UMLClass leftTarget) {
		this.leftTarget = leftTarget;
//		Log.debug("UMLRelation::setLeftTarget \n" + this);
	}

	/**
	 * @return the rightTarget
	 */
	public UMLClass getRightTarget() {
		return rightTarget;
	}

	/**
	 * @param rightTarget the rightTarget to set
	 */
	public void setRightTarget(UMLClass rightTarget) {
		this.rightTarget = rightTarget;
//		Log.debug("UMLRelation::setRightTarget \n" + this);
	}
	

	/**
	 * Reverse the current relation
	 */
	public void reverse() {
		final LinkAdornment tempAdornment = this.leftAdornment;
		final String tempCardinality = this.leftCardinality;
		final String tempConstraint = this.leftConstraint;
		final String tempRole = this.leftRole;
		final String tempStereotype = this.leftStereotype;
		final UMLClass tempClass = this.leftTarget;
		
		this.leftAdornment = this.rightAdornment;
		this.leftCardinality = this.rightCardinality;
		this.leftConstraint = this.rightConstraint;
		this.leftRole = this.rightRole;
		this.leftStereotype = this.rightStereotype;
		this.leftTarget = this.rightTarget;
		
		this.rightAdornment = tempAdornment;
		this.rightCardinality = tempCardinality;
		this.rightConstraint = tempConstraint;
		this.rightRole = tempRole;
		this.rightStereotype = tempStereotype;
		this.rightTarget = tempClass;
		
//		Log.debug("UMLRelation::reverse()\n" + this);
	}
	
	
	/**
	 * @return true if the relation is a bidirectionnal relation between two classes
	 */
	public boolean isBidirectional() {
		if ( ! linkKind.equals(LinkKind.ASSOCIATION_RELATION))
			return false;
		
		if (leftCardinality.isEmpty() || rightCardinality.isEmpty())
			return false;
		
		if (leftRole.isEmpty() || rightRole.isEmpty())
			return false;
		
		if ( ! leftStereotype.contentEquals("<<owner>>") && ! rightStereotype.contentEquals("<<owner>>"))
			return false;
		
		if ( !(leftAdornment.equals(LinkAdornment.NONE) && rightAdornment.equals(LinkAdornment.NONE)))  
			return false;
		
		return true;
	}

	
	/**
	 * On a relation between two classes, one of the classes is the owner of the relation.
	 * @return the owner of the relation.
	 */
	public UMLClass getOwner() {
		if (this.isBidirectional()) {
			if (leftStereotype.equalsIgnoreCase("<<owner>>"))
				return leftTarget;
			else if (rightStereotype.equalsIgnoreCase("<<owner>>"))
				return rightTarget;
			else
				throw new GWTUMLAPIException("A bidirectional relation must have an owner defined.");
		} 
		
		if (leftAdornment.equals(LinkAdornment.WIRE_ARROW))
			return rightTarget;
		else if (rightAdornment.equals(LinkAdornment.WIRE_ARROW))
			return leftTarget;
		else
			throw new GWTUMLAPIException("An association must have an arrow on one side to define the owner.");
	}
	
	
	/** 
	 * On a relation between two classes, one of the classes is the target of the relation.
	 * @return the target of the relation.
	 */
	public UMLClass getTarget() {
		if (leftTarget.equals(getOwner()))
			return rightTarget;
		else 
			return leftTarget;
	}
	
	
	/**
	 * @return True if the left side is the owner of the relation
	 */
	public boolean isLeftOwner () {
		if (getOwner().equals(leftTarget))
			return true;
		else
			return false;
	}
	
	/**
	 * @return True if the right side is the owner of the relation.
	 */
	public boolean isRightOwner() {
		return ! isLeftOwner();
	}

	
	/**
	 * @return true if the relation is a composition.
	 */
	public boolean isAComposition() {
		if (leftAdornment.equals(LinkAdornment.INVERTED_SOLID_DIAMOND) || rightAdornment.equals(LinkAdornment.INVERTED_SOLID_DIAMOND))
			return true;
			
		return false;
	}
	
	
	/**
	 * Return if the relation is a one to one relation.
	 * @return true if the relation is a one to one relation.
	 */
	public boolean isOneToOne () {
		if (leftCardinality.equalsIgnoreCase("1")) {
			// Simple one To One
			if (rightCardinality.isEmpty())
				return true;
			//Bidirectional
			else if (rightCardinality.equalsIgnoreCase("1"))
				return true;
			// one to many
			else 
				return false;
		}
		
		if (rightCardinality.equalsIgnoreCase("1")) {
			// Simple one To One
			if (leftCardinality.isEmpty())
				
				return true;
			//Bidirectional
			else if (leftCardinality.equalsIgnoreCase("1"))
				return true;
			// one to many
			else 
				return false;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		String leftTargetName = leftTarget==null ? "null" : leftTarget.getName();
		String rightTargetName = rightTarget==null ? "null" : rightTarget.getName();
		
		return 
			"Name = " + name + "\n" +
			"LinkStyle = " + linkStyle +
			"Left :\n" +
				"\tCardinality = " + leftCardinality + "\n" +
				"\tConstraint = " + leftConstraint + "\n" +
				"\tRole = " + leftRole + "\n" +
				"\tAdornment = " + leftAdornment + "\n" +
				"\tUMLClass = " + leftTargetName + "\n" +
				"\n" +
			"Right :\n" +
				"\tCardinality = " + rightCardinality + "\n" +
				"\tConstraint = " + rightConstraint + "\n" +
				"\tRole = " + rightRole + "\n" +
				"\tAdornment = " + rightAdornment + "\n" +
				"\tUMLClass = " + rightTargetName + "\n"
			;
	}
}
