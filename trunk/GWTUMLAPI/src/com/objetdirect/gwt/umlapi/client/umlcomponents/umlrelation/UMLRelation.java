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
package com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation;

import static com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLLink.LinkKind.ASSOCIATION_RELATION;

import com.objetdirect.gwt.umlapi.client.exceptions.UMLException;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

/**
 * This class represent an uml relation between two {@link UMLClass}es
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 * @contributor Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class UMLRelation extends UMLLink {

	private String name;

	private String leftCardinality;
	private String leftConstraint;
	private String leftRole;
	private String leftStereotype;
	private LinkAdornment leftAdornment;
	private UMLClass leftTarget;

	private String rightCardinality;
	private String rightConstraint;
	private String rightRole;
	private String rightStereotype;
	private LinkAdornment rightAdornment;
	private UMLClass rightTarget;

	private LinkStyle linkStyle;

	/**
	 * Factory for the simplest kind of association between two classes.
	 * 
	 * @param owner
	 * @param target
	 * @param rightRole
	 * @return the relation created.
	 */
	public static UMLRelation createAssociation(UMLClass owner, UMLClass target, String rightRole) {
		UMLRelation association = new UMLRelation(ASSOCIATION_RELATION);
		association.setLeftTarget(owner);
		association.setRightTarget(target);
		association.setRightRole(rightRole);
		return association;
	}
	
	/**
	 * Default constructor ONLY for gwt-rpc serialization
	 */
	UMLRelation() {
	}

	/**
	 * Constructor of Relation
	 * 
	 * @param relationKind
	 *            : The type of this relation
	 */
	public UMLRelation(final LinkKind relationKind) {
		super(relationKind);
		linkStyle = relationKind.getDefaultLinkStyle();
		name = "";

		leftCardinality = relationKind.getDefaultLeftCardinality();
		leftConstraint = "";
		leftRole = "";
		leftStereotype = "";
		leftAdornment = relationKind.getDefaultLeftAdornment();

		rightCardinality = relationKind.getDefaultRightCardinality();
		rightConstraint = "";
		rightRole = "";
		rightStereotype = "";
		rightAdornment = relationKind.getDefaultRightAdornment();
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
	 * Getter for the leftCardinality
	 * 
	 * @return the leftCardinality
	 */
	public String getLeftCardinality() {
		return leftCardinality;
	}

	/**
	 * Getter for the leftConstraint
	 * 
	 * @return the leftConstraint
	 */
	public String getLeftConstraint() {
		return leftConstraint;
	}

	/**
	 * Getter for the leftRole
	 * 
	 * @return the leftRole
	 */
	public String getLeftRole() {
		return leftRole;
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
	 * Getter for the rightCardinality
	 * 
	 * @return the rightCardinality
	 */
	public String getRightCardinality() {
		return rightCardinality;
	}

	/**
	 * Getter for the rightConstraint
	 * 
	 * @return the rightConstraint
	 */
	public String getRightConstraint() {
		return rightConstraint;
	}

	/**
	 * Getter for the rightRole
	 * 
	 * @return the rightRole
	 */
	public String getRightRole() {
		return rightRole;
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
	 * @param leftStereotype
	 *            the leftStereotype to set
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

	/**
	 * @param rightStereotype
	 *            the rightStereotype to set
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
	 * @param leftTarget
	 *            the leftTarget to set
	 */
	public void setLeftTarget(UMLClass leftTarget) {
		this.leftTarget = leftTarget;
	}

	/**
	 * @return the rightTarget
	 */
	public UMLClass getRightTarget() {
		return rightTarget;
	}

	/**
	 * @param rightTarget
	 *            the rightTarget to set
	 */
	public void setRightTarget(UMLClass rightTarget) {
		this.rightTarget = rightTarget;
	}

	/**
	 * Reverse the current relation
	 */
	public void reverse() {
		final LinkAdornment tempAdornment = leftAdornment;
		final String tempCardinality = leftCardinality;
		final String tempConstraint = leftConstraint;
		final String tempRole = leftRole;
		final String tempStereotype = leftStereotype;
		final UMLClass tempClass = leftTarget;

		leftAdornment = rightAdornment;
		leftCardinality = rightCardinality;
		leftConstraint = rightConstraint;
		leftRole = rightRole;
		leftStereotype = rightStereotype;
		leftTarget = rightTarget;

		rightAdornment = tempAdornment;
		rightCardinality = tempCardinality;
		rightConstraint = tempConstraint;
		rightRole = tempRole;
		rightStereotype = tempStereotype;
		rightTarget = tempClass;
	}

	/**
	 * @return true if the relation is a bidirectionnal relation between two classes
	 */
	public boolean isBidirectional() {
		if (!linkKind.equals(LinkKind.ASSOCIATION_RELATION)) {
			return false;
		}

		if (leftRole.isEmpty() || rightRole.isEmpty()) {
			return false;
		}

		if (!(leftAdornment.equals(LinkAdornment.NONE) && rightAdornment.equals(LinkAdornment.NONE))) {
			return false;
		}

		return true;
	}

	/**
	 * On a relation between two classes, one of the classes is the owner of the relation.
	 * 
	 * @return the owner of the relation.
	 */
	public UMLClass getOwner() {
		if (this.isBidirectional()) {
			if (leftStereotype.equalsIgnoreCase("<<owner>>")) {
				return leftTarget;
			} else if (rightStereotype.equalsIgnoreCase("<<owner>>")) {
				return rightTarget;
			} else {
				throw new UMLException("A bidirectional relation must have an owner defined.");
			}
		}

		if (leftAdornment.equals(LinkAdornment.WIRE_ARROW)) {
			return rightTarget;
		} else if (rightAdornment.equals(LinkAdornment.WIRE_ARROW)) {
			return leftTarget;
		} else {
			throw new UMLException("An association must have an arrow on one side to define the owner.");
		}
	}

	/**
	 * On a relation between two classes, one of the classes is the target of the relation.
	 * 
	 * @return the target of the relation.
	 */
	public UMLClass getTarget() {
		if (leftTarget.equals(getOwner())) {
			return rightTarget;
		}

		return leftTarget;
	}

	/**
	 * @return True if the left side is the owner of the relation
	 */
	public boolean isLeftOwner() {
		if (getOwner().equals(leftTarget)) {
			return true;
		}

		return false;
	}

	/**
	 * @return True if the right side is the owner of the relation.
	 */
	public boolean isRightOwner() {
		return !isLeftOwner();
	}

	/**
	 * @return true if the relation is a composition.
	 */
	public boolean isAComposition() {
		if (leftAdornment.equals(LinkAdornment.INVERTED_SOLID_DIAMOND) || rightAdornment.equals(LinkAdornment.INVERTED_SOLID_DIAMOND)) {
			return true;
		}

		return false;
	}

	/**
	 * @return true if the relation is a OneToOne relation.
	 */
	public boolean isOneToOne() {
		if (leftCardinality.equalsIgnoreCase("1")) {
			// Simple one To One
			if (rightCardinality.isEmpty()) {
				return true;
			} else if (rightCardinality.equalsIgnoreCase("1")) {
				return true;
				// one to many
			} else {
				return false;
			}
		}

		if (rightCardinality.equalsIgnoreCase("1")) {
			// Simple one To One
			if (leftCardinality.isEmpty()) {
				return true;
			}
			return false;
		}

		return false;
	}

	/**
	 * @return true if the relation is a OneToMany relation
	 */
	public boolean isOneToMany() {
		if (isLeftOwner()) {
			if ((leftCardinality.isEmpty() || leftCardinality.equalsIgnoreCase("1")) && rightCardinality.equalsIgnoreCase("*")) {
				return true;
			}
		}
		// Right owner
		else {
			if ((rightCardinality.isEmpty() || rightCardinality.equalsIgnoreCase("1")) && leftCardinality.equalsIgnoreCase("*")) {
				return true;
			}
		}

		return false;
	}

	public boolean isManyToOne() {
		if (isLeftOwner()) {
			if (leftCardinality.equalsIgnoreCase("*") && rightCardinality.equalsIgnoreCase("1")) {
				return true;
			}
		}
		// Right owner
		else {
			if (rightCardinality.equalsIgnoreCase("*") && leftCardinality.equalsIgnoreCase("1")) {
				return true;
			}
		}

		return false;
	}

	public boolean isManyToMany() {
		if (leftCardinality.equalsIgnoreCase("*") && rightCardinality.equalsIgnoreCase("*")) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		String leftTargetName = leftTarget == null ? "null" : leftTarget.getName();
		String rightTargetName = rightTarget == null ? "null" : rightTarget.getName();

		return "Name = " + name + "\n" + "LinkStyle = " + linkStyle + "\n" + 
				"Left :\n" + "\tCardinality = " + leftCardinality + "\n" + "\tConstraint = "
				+ leftConstraint + "\n" + "\tRole = " + leftRole + "\n" + "\tAdornment = " + leftAdornment + "\n" + "\tUMLClass = " + leftTargetName + "\n"
				+ "\n" + 
				"Right :\n" + "\tCardinality = " + rightCardinality + "\n" + "\tConstraint = " + rightConstraint + "\n" + "\tRole = " + rightRole
				+ "\n" + "\tAdornment = " + rightAdornment + "\n" + "\tUMLClass = " + rightTargetName + "\n";
	}
}
