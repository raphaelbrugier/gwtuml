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

import java.io.Serializable;
import java.util.List;

import com.allen_sauer.gwt.log.client.Log;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer;
import com.objetdirect.gwt.umlapi.client.analyser.MethodSyntaxAnalyzer;
import com.objetdirect.gwt.umlapi.client.exceptions.GWTUMLAPIException;

/**
 * This class represent a method in a class
 * 
 * @author Henri Darmet
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class UMLClassMethod implements Serializable {

	/**
	 * Parse a method from a {@link String}
	 * 
	 * @param methodToParse
	 *            The string containing an {@link UMLClassMethod} obtained with {@link UMLClassMethod#toString()}
	 * 
	 * @return The new parsed {@link UMLClassMethod} or an empty one if there was a problem
	 */
	public static UMLClassMethod parseMethod(final String methodToParse) {
		final LexicalAnalyzer lex = new LexicalAnalyzer(methodToParse);

		try {
			final MethodSyntaxAnalyzer methodSyntaxAnalyser = new MethodSyntaxAnalyzer();
			methodSyntaxAnalyser.process(lex, null);
			return methodSyntaxAnalyser.getMethod();

		} catch (final GWTUMLAPIException e) {
			Log.error(e.getMessage());

		}
		return null;
	}

	/**
	 * /!\ Don't forget to increment the serialVersionUID if you change any of the fields above /!\
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private List<UMLParameter> parameters;

	private String returnType;

	private UMLVisibility visibility;

	/**
	 * Default constructor only for gwt-rpc serialization
	 */
	public UMLClassMethod() {
		this(UMLVisibility.PUBLIC, "", "", null);
	}

	/**
	 * Constructor of the method
	 * 
	 * @param visibility
	 * @param returnType
	 *            The return type of the method
	 * @param name
	 *            The name of the method
	 * @param parameters
	 *            The parameters list of this method
	 */
	public UMLClassMethod(final UMLVisibility visibility, final String returnType, final String name, final List<UMLParameter> parameters) {
		this.visibility = visibility;
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
	}

	/**
	 * Getter for the name
	 * 
	 * @return the name
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for the parameters list
	 * 
	 * @return the parameters list
	 * 
	 */
	public List<UMLParameter> getParameters() {
		return parameters;
	}

	/**
	 * Getter for the return type
	 * 
	 * @return the return type
	 * 
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * Getter of the visibility of this method
	 * 
	 * @return The visibility of this method
	 */
	public UMLVisibility getVisibility() {
		return visibility;
	}

	/**
	 * Setter for the name
	 * 
	 * @param name
	 *            to be set
	 * 
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Setter for the parameters list
	 * 
	 * @param parameters
	 *            list to be set
	 * 
	 */
	public void setParameters(final List<UMLParameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Setter for the return type
	 * 
	 * @param returnType
	 *            return type to be set
	 * 
	 */
	public void setReturnType(final String returnType) {
		this.returnType = returnType;
	}

	/**
	 * Setter for the visibility of the method
	 * 
	 * @param visibility
	 *            The {@link UMLVisibility} of this method
	 */
	public void setVisibility(final UMLVisibility visibility) {
		this.visibility = visibility;
	}

	/**
	 * Format a string from method name, parameters and return type
	 * 
	 * @return the UML formatted string for method name, parameters and return type
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder f = new StringBuilder();
		f.append(visibility);
		f.append(name);
		f.append("(");
		if ((parameters != null) && (parameters.size() > 0)) {
			boolean first = true;
			for (final UMLParameter parameter : parameters) {
				if (!first) {
					f.append(", ");
				} else {
					first = false;
				}
				f.append(parameter.toString());
			}
		}
		f.append(")");
		if (returnType != null) {
			f.append(" : ");
			f.append(returnType);
		}
		return f.toString();
	}

}
