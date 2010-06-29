package com.objetdirect.gwt.umlapi.client.umlcomponents.umlrelation;

import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClass;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLObject;

public class InstantiationRelation {

	private final UMLObject instance;

	private final UMLClass instanceOf;

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

}
