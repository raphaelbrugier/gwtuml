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

	/**
	 * Parse a stereotype from a {@link String}
	 * 
	 * @param stereotypeToParse
	 *            The string containing a stereotype
	 * 
	 * @return The new parsed stereotype or an empty one if there was a problem
	 */
	public static String parseStereotype(final String stereotypeToParse) {
		if (stereotypeToParse.equals("")) {
			return "";
		}
		final LexicalAnalyzer lex = new LexicalAnalyzer(stereotypeToParse);
		try {
			final LexicalAnalyzer.Token tk = lex.getToken();
			if ((tk == null) || (tk.getType() != LexicalFlag.IDENTIFIER)) {
				throw new GWTUMLAPIException("Invalid object stereotype : " + stereotypeToParse + " doesn't repect uml conventions");
			}
			return tk.getContent();
		} catch (final GWTUMLAPIException e) {
			Log.error(e.getMessage());
		}
		return "";
	}

	private String className;
	private String instanceName;
	private String stereotype;
	private List<UMLObjectAttribute> attributes;

	/** Default constructor ONLY for gwt-rpc serialization. */
	UMLObject() {
	}

	/**
	 * Constructor of an {@link UMLObject}
	 * 
	 * @param objectInstance
	 *            The name of this instance
	 * @param className
	 *            The name of this object
	 */
	public UMLObject(final String instanceName, final String className, String stereotype) {
		super();
		this.instanceName = instanceName;
		this.className = className;
		this.stereotype = stereotype;
		attributes = new ArrayList<UMLObjectAttribute>();
	}

	/**
	 * Getter for the object instance name
	 * 
	 * @return the object instance name
	 */
	public final String getInstanceName() {
		return instanceName;
	}

	/**
	 * Getter for the attributes
	 * 
	 * @return the attributes
	 */
	public final List<UMLObjectAttribute> getObjectAttributes() {
		return attributes;
	}

	/**
	 * Getter for the object name
	 * 
	 * @return the object name
	 */
	public final String getClassName() {
		return className;
	}

	/**
	 * Getter for the stereotype
	 * 
	 * @return the stereotype
	 */
	public final String getStereotype() {
		return stereotype;
	}

	/**
	 * Setter for the object instance name
	 * 
	 * @param instanceName
	 *            the object instance name to set
	 */
	public final void setInstanceName(final String instanceName) {
		this.instanceName = instanceName;
	}

	/**
	 * Setter for the object name
	 * 
	 * @param objectName
	 *            the object name to set
	 */
	public final void setClassName(final String objectName) {
		className = objectName;
	}

	/**
	 * /** Setter for the stereotype
	 * 
	 * @param stereotype
	 *            the stereotype to set
	 */
	public final void setStereotype(final String stereotype) {
		this.stereotype = stereotype;
	}

	/**
	 * @return A formatted string with the object name (if any) and the instance name.
	 */
	public String getFormattedName() {
		return instanceName + ":" + className;
	}

	public String getValueOfAttribute(String attributeName) {
		for (UMLObjectAttribute attribute : attributes) {
			System.out.println("attribute = " + attribute + "    attributeName = " + attributeName);
			if (attribute.getAttributeName().equals(attributeName)) {
				return attribute.getValue();
			}
		}
		throw new RuntimeException("No attribute correspond to " + attributeName + " in the instantiated object.");
	}

	public UMLObject addAttributeValuePair(String attributeName, String attributeValue) {
		getObjectAttributes().add(UMLObjectAttribute.parseAttribute(attributeName + " = \"" + attributeValue + "\""));
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return instanceName + ":" + className;
	}
}
