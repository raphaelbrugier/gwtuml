/*
 * This file is part of the GWTUML project and was written by Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com) for Objet Direct
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

import java.io.Serializable;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;

/**
 * Represents an instantiation relation in the metamodel.
 * 
 * @author Raphaël Brugier (raphael-dot-brugier.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class InstantiationRelation implements Serializable {

	private UMLObject instance;

	private UMLClass instanceOf;

	/**
	 * Default constructor only for gwt-rpc serialiation.
	 * DO NOT USE
	 */
	@SuppressWarnings("unused")
	private InstantiationRelation() {
	}
	
	/**
	 * @param instance
	 * @param instanceOf
	 */
	public InstantiationRelation(UMLObject instance, UMLClass instanceOf) {
		super();
		this.instance = instance;
		this.instanceOf = instanceOf;
	}

	/**
	 * @return the instance
	 */
	public UMLObject getInstance() {
		return instance;
	}

	/**
	 * @return the instanceOf
	 */
	public UMLClass getInstanceOf() {
		return instanceOf;
	}
	
	@Override
	public String toString() {
		return "Instantation link between object named : " + instance.getInstanceName() + "  , instance of class : " + instanceOf.getName();
	}

}
