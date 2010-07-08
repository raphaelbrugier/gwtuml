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

import java.io.Serializable;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;

/**
 * Represents a relation between two objects in the metamodel.
 * 
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class ObjectRelation implements Serializable {

	private UMLObject leftObject;
	private String leftRole;
	private boolean leftNavigable;

	private UMLObject rightObject;
	private String rightRole;
	private boolean rightNavigable;

	/**
	 * Default constructor only for gwt-rpc serialization.
	 */
	ObjectRelation() {}
	
	/**
	 * @param owner by default the owner is the left object
	 * @param target by default the target is the right object
	 */
	public ObjectRelation(UMLObject owner, UMLObject target) {
		this.leftObject = owner;
		this.rightObject = target;

		leftRole = "";
		leftNavigable = false;

		rightRole = "role";
		rightNavigable = true;
	}

	/**
	 * @return the leftRole, default is empty string
	 */
	public String getLeftRole() {
		return leftRole;
	}

	/**
	 * @param leftRole
	 *            the leftRole to set
	 */
	public ObjectRelation setLeftRole(String leftRole) {
		this.leftRole = leftRole;
		return this;
	}

	/**
	 * @return the right role, default = "role"
	 */
	public String getRightRole() {
		return rightRole;
	}

	/**
	 * @param rightRole
	 *            the rightRole to set
	 */
	public ObjectRelation setRightRole(String rightRole) {
		this.rightRole = rightRole;
		return this;
	}

	/**
	 * @return the leftObject
	 */
	public UMLObject getLeftObject() {
		return leftObject;
	}

	/**
	 * @return the leftNavigable, default is false.
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
	 * @return the rightNavigable, default is true;
	 */
	public boolean isRightNavigable() {
		return rightNavigable;
	}

	/**
	 * @param leftNavigable
	 *            the leftNavigable to set, default is false.
	 */
	public ObjectRelation setLeftNavigable(boolean leftNavigable) {
		this.leftNavigable = leftNavigable;
		return this;
	}

	/**
	 * @param rightNavigable
	 *            the rightNavigable to set, default is true.
	 */
	public ObjectRelation setRightNavigable(boolean rightNavigable) {
		this.rightNavigable = rightNavigable;
		return this;
	}
	
	@Override
	public String toString() {
		return "Object relation between  : \n" +
				"\t left object named : " + leftObject.getInstanceName() + ", instance of class : " + leftObject.getClassName() + " " +
				"\t\t left role = " + leftRole + "   ,  leftNavigable = " + leftNavigable +				
				"\t right object named : " + rightObject.getInstanceName() + ", instance of class : " + rightObject.getClassName() +
				"\t\t right role = " + rightRole + "   ,  rightNavigable = " + rightNavigable;				
	}
}
