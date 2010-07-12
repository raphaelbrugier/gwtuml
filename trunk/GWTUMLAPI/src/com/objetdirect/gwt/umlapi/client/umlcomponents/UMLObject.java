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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.exceptions.GWTUMLAPIException;

/**
 * This class represents an object uml component
 * 
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
@SuppressWarnings("serial")
public class UMLObject extends UMLNode {
	/**
	 * Parse a name from a {@link String}
	 * 
	 * @param nameToParse
	 *            The string containing a name
	 * 
	 * @return The list of the new parsed name and instance or an empty one if there was a problem
	 */
	public static List<String> parseName(final String nameToParse) {
		if (nameToParse.equals("")) {
			return Arrays.asList("", "");
		}
		final LexicalAnalyzer lex = new LexicalAnalyzer(nameToParse);
		String instance = "";
		String name = "";
		try {

			LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk != null) && (tk.getType() == LexicalFlag.IDENTIFIER)) {

				instance = tk.getContent();
				tk = lex.getToken();
			}
			if (tk != null) {
				if ((tk.getType() != LexicalFlag.SIGN) || !tk.getContent().equals(":")) {
					throw new GWTUMLAPIException("Invalid object name format : " + nameToParse + " doesn't match 'instance : name'");
				}
				tk = lex.getToken();
				if ((tk != null) && (tk.getType() == LexicalFlag.IDENTIFIER)) {
					name = tk.getContent();
				}
			}
			if (name.equals("") && !instance.equals("")) {
				name = instance;
				instance = "";
			}

		} catch (final GWTUMLAPIException e) {
			Log.error(e.getMessage());
		}
		return Arrays.asList(instance, name);
	}

	private UMLClass instantiatedClass;

	private String instanceName;
	private List<UMLObjectAttribute> attributes;

	/**
	 * Default constructor.
	 */
	public UMLObject() {
		instanceName = "";
		attributes = new ArrayList<UMLObjectAttribute>();
	}

	public UMLObject(final String instanceName, final UMLClass instantiatedClass) {
		this.instanceName = instanceName;
		this.instantiatedClass = instantiatedClass;
		attributes = new ArrayList<UMLObjectAttribute>();
	}

	/**
	 * Getter for the object instance name
	 * 
	 * @return the object instance name
	 */
	public String getInstanceName() {
		return instanceName;
	}

	/**
	 * Getter for the attributes
	 * 
	 * @return the attributes
	 */
	public List<UMLObjectAttribute> getObjectAttributes() {
		return attributes;
	}

	/**
	 * Setter for the object instance name
	 * 
	 * @param instanceName
	 *            the object instance name to set
	 */
	public void setInstanceName(final String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * @return the instantiatedClass
	 */
	public UMLClass getInstantiatedClass() {
		return instantiatedClass;
	}

	/**
	 * @param instantiatedClass
	 *            the instantiatedClass to set
	 */
	public void setInstantiatedClass(UMLClass instantiatedClass) {
		this.instantiatedClass = instantiatedClass;
	}

	/**
	 * @return A formatted string with the object name (if any) and the instance name.
	 */
	public String getFormattedName() {
		String className;
		if (instantiatedClass == null) {
			className = "Choose the instantiated class";
		} else {
			className = instantiatedClass.getName();
		}
		return instanceName + ":" + className;
	}

	public String getValueOfAttribute(String attributeName) {
		for (UMLObjectAttribute attribute : attributes) {
			if (attribute.getAttributeName().equals(attributeName)) {
				return attribute.getValue();
			}
		}
		return null;
	}

	public UMLObject addAttributeValuePair(String attributeName, String attributeValue) {
		getObjectAttributes().add(UMLObjectAttribute.parseAttribute(attributeName + " = \"" + attributeValue + "\""));
		return this;
	}

	@Override
	public String toString() {
		return getFormattedName();
	}

	public String getClassName() {
		return instantiatedClass.getName();
	}
}
