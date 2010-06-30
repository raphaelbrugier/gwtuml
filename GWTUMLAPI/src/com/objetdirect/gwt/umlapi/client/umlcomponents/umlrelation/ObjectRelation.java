/*
 * This file is part of the GWTUML project and was written by Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com) for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright Â© 2010 Objet Direct Contact: gwtuml@googlegroups.com
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

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;

/**
 * Represents a relation between two objects in the metamodel.
 * 
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public class ObjectRelation {

	private final UMLObject leftObject;
	private String leftRole;
	private boolean leftNavigable;

	private final UMLObject rightObject;
	private String rightRole;
	private boolean rightNavigable;

	/**
	 * @param leftObject
	 * @param rightObject
	 */
	public ObjectRelation(UMLObject leftObject, UMLObject rightObject) {
		this.leftObject = leftObject;
		this.rightObject = rightObject;

		leftRole = "";
		leftNavigable = false;

		rightRole = "role";
		rightNavigable = true;
	}

	/**
	 * @return the leftRole
	 */
	public String getLeftRole() {
		return leftRole;
	}

	/**
	 * @param leftRole
	 *            the leftRole to set
	 */
	public void setLeftRole(String leftRole) {
		this.leftRole = leftRole;
	}

	/**
	 * @return the rightRole
	 */
	public String getRightRole() {
		return rightRole;
	}

	/**
	 * @param rightRole
	 *            the rightRole to set
	 */
	public void setRightRole(String rightRole) {
		this.rightRole = rightRole;
	}

	/**
	 * @return the leftObject
	 */
	public UMLObject getLeftObject() {
		return leftObject;
	}

	/**
	 * @return the leftNavigable
	 */
	public boolean isLeftNavigable() {
		return leftNavigable;
	}

	/**
	 * @return the rightObject
	 */
	public UMLObject getRightObject() {
		return rightObject;
	}

	/**
	 * @return the rightNavigable
	 */
	public boolean isRightNavigable() {
		return rightNavigable;
	}

	/**
	 * @param leftNavigable
	 *            the leftNavigable to set
	 */
	public void setLeftNavigable(boolean leftNavigable) {
		this.leftNavigable = leftNavigable;
	}

	/**
	 * @param rightNavigable
	 *            the rightNavigable to set
	 */
	public void setRightNavigable(boolean rightNavigable) {
		this.rightNavigable = rightNavigable;
	}
}
