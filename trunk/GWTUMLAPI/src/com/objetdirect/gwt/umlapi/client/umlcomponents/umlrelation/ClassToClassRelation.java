package com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;

public abstract class ClassToClassRelation implements Relation {

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
}
