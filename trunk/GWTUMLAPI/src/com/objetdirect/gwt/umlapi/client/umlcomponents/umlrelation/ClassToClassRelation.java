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

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

/**
 * Represents a relation between two classes in the metamodel.
 * 
 * THIS CLASS IS NOT USED FOR NOW This class was supposed to be part of a big refactoring around how the relations
 * metamodel are managed by the corresponding artifacts. It's left here for now until we'll continue the refactoring.
 * 
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
public abstract class ClassToClassRelation {

	private final UMLClass leftTarget;

	private final UMLClass rightTarget;

	/**
	 * @param leftTarget
	 * @param rightTarget
	 */
	public ClassToClassRelation(UMLClass leftTarget, UMLClass rightTarget) {
		this.leftTarget = leftTarget;
		this.rightTarget = rightTarget;
	}

	/**
	 * @return the leftTarget
	 */
	public UMLClass getLeftTarget() {
		return leftTarget;
	}

	/**
	 * @return the rightTarget
	 */
	public UMLClass getRightTarget() {
		return rightTarget;
	}
}
