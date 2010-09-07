/*
 * This file is part of the Gwt-Generator project and was written by Raphaël Brugier <raphael dot brugier at gmail dot com > for Objet Direct
 * <http://wwww.objetdirect.com>
 * 
 * Copyright © 2010 Objet Direct
 * 
 * Gwt-Generator is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Gwt-Generator is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Gwt-Generator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.objetdirect.gwt.umlapi.client.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.objetdirect.gwt.umlapi.client.umlCanvas.ObjectDiagram;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.ObjectRelation;
import com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation.UMLRelation;

/**
 * This class has the responsability to calculate all the relations that
 * the object can reach.
 * It is the difference between the relations described in the model and the other objects
 * the object has already been related.
 * 
 * @author Raphaël Brugier <raphael dot brugier at gmail dot com>
 */
public class ObjectRelationsCalculator {

	private final ObjectDiagram objectDiagram;
	private final UMLObject umlObject;

	/**
	 * List all the classes that can be reach from the umlObject and their cardinality;
	 */
	private Map<UMLClass, Integer> allPossibleRelations;

	/**
	 * List all the classes that can be reach from the umlObject and their cardinality;
	 */
	private Map<UMLClass, Integer> allCurrentRelations;

	/**
	 * List of all the classes that can be attached with the current object.
	 */
	private List<UMLClass> possibleClasses;

	/**
	 * @param umlCanvasObjectDiagram
	 * @param objectArtifact
	 */
	public ObjectRelationsCalculator(ObjectDiagram objectDiagram, UMLObject umlObject) {
		this.objectDiagram = objectDiagram;
		this.umlObject = umlObject;
		listAllPossibleRelation();
		listAllObjectRelations();
		diffBetweenPossibleAndCurrent();
	}

	/**
	 * List all the possible relation with the object
	 */
	private void listAllPossibleRelation() {
		allPossibleRelations = new HashMap<UMLClass, Integer>();
		String className = umlObject.getClassName();
		for (UMLRelation classRelation : objectDiagram.getClassRelations()) {
			if (classRelation.getOwner().getName().equals(className)) {
				String cardinality = "*";
				if (classRelation.isLeftOwner()) {
					cardinality = classRelation.getRightCardinality();
				} else {
					cardinality = classRelation.getLeftCardinality();
				}
				allPossibleRelations.put(classRelation.getTarget(), cardinalityToInteger(cardinality));
			}
		}
	}

	/**
	 * Transform a cardinality stored as a string into an Integer.
	 * The cardinality n or * is transformed in -1
	 * @param cardinality
	 * @return the cardinality as an integer
	 */
	private Integer cardinalityToInteger(String cardinality) {
		if (cardinality == null || cardinality.equalsIgnoreCase("")) {
			return Integer.MAX_VALUE;
		}

		if (cardinality.equalsIgnoreCase("n") || cardinality.equalsIgnoreCase("*")) {
			return Integer.MAX_VALUE;
		}
		return Integer.valueOf(cardinality);
	}


	/**
	 * List all the relations the object currently has.
	 */
	private void listAllObjectRelations() {
		allCurrentRelations = new HashMap<UMLClass, Integer>();
		for(ObjectRelation objectRelation  : objectDiagram.getObjectRelations()) {
			if (objectRelation.getLeftObject().equals(umlObject)) {
				UMLClass umlClass = objectRelation.getRightObject().getInstantiatedClass();
				addOrIncrementNumberOfCurrentRelations(umlClass);
			}
		}
	}

	/**
	 * @param umlClass
	 */
	private void addOrIncrementNumberOfCurrentRelations(UMLClass umlClass) {
		int numberOfRelations = 1;
		if (allCurrentRelations.containsKey(umlClass)) {
			numberOfRelations = allCurrentRelations.get(umlClass);
			numberOfRelations++;
		}
		allCurrentRelations.put(umlClass, numberOfRelations);
	}


	/**
	 * Process a difference between the possible relations in the model and the current relations.
	 * The result is stored in the list : possibleClasses
	 * It contains all the classes that the object can now reached.
	 */
	private void diffBetweenPossibleAndCurrent() {
		possibleClasses = new ArrayList<UMLClass>();
		for (UMLClass classFromModel : allPossibleRelations.keySet()) {
			if (! allCurrentRelations.containsKey(classFromModel)) {
				possibleClasses.add(classFromModel);
			} else {
				int cardinality = allPossibleRelations.get(classFromModel);
				int currentNumber = allCurrentRelations.get(classFromModel);
				if (cardinality - currentNumber > 0 ) {
					possibleClasses.add(classFromModel);
				}
			}
		}
	}

	/**
	 * @return A list of all the classes that the object can be related with.
	 */
	public List<UMLClass> getPossibleClasses() {
		return possibleClasses;
	}
}
