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
package com.objetdirect.gwt.umlapi.client.analyser;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

/**
 * A method syntax analyzer
 * 
 * @author Henri Darmet
 */
public class MethodSyntaxAnalyzer extends SyntaxAnalyzer {
	UMLClassMethod		method		= new UMLClassMethod(UMLVisibility.PUBLIC, null, null, null);
	List<UMLParameter>	parameters	= new ArrayList<UMLParameter>();

	/**
	 * Getter for the {@link UMLClassMethod}
	 * 
	 * @return The method
	 */
	public UMLClassMethod getMethod() {
		return this.method;
	}

	void setParameters() {
		this.method.setParameters(this.parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.analyser.SyntaxAnalyzer#processToken(com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer,
	 * com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token)
	 */
	@SuppressWarnings("fallthrough")
	@Override
	protected LexicalAnalyzer.Token processToken(final LexicalAnalyzer lex, final LexicalAnalyzer.Token tk) {
		Token token = tk;
		if (token == null) {
			token = lex.getToken();
		}
		switch (this.getStatus()) {
			case BEGIN:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				}
				if (token.getType() == LexicalFlag.VISIBILITY) {
					this.method.setVisibility(UMLVisibility.getVisibilityFromToken(token.getContent().charAt(0)));
					this.setStatus(State.IDENTIFIER_EXPECTED);
					return null;
				}
				this.method.setVisibility(UMLVisibility.PACKAGE);
			case IDENTIFIER_EXPECTED:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				}
				if (token.getType() == LexicalFlag.IDENTIFIER) {
					this.method.setName(token.getContent());
					this.setStatus(State.OPEN_PARENTHESIS_EXPECTED);
					return null;
				}
				this.throwSyntaxError(token);
				return null;
			case OPEN_PARENTHESIS_EXPECTED:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				}
				if ((token.getType() == LexicalFlag.SIGN) && token.getContent().equals("(")) {
					this.setStatus(State.BEGIN_PARAMETER);
					return null;
				}
				this.throwSyntaxError(token);
				return null;
			case BEGIN_PARAMETER:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				}
				if ((token.getType() == LexicalFlag.SIGN) && token.getContent().equals(")")) {
					this.setStatus(State.BEGIN_RETURN_TYPE);
					return null;
				}
				final ParameterAnalyzer pa = new ParameterAnalyzer();
				token = pa.process(lex, token);
				this.parameters.add(pa.getParameter());
				this.setStatus(State.END_PARAMETER);
				return token;

			case END_PARAMETER:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				}
				if ((token.getType() == LexicalFlag.SIGN) && token.getContent().equals(")")) {
					this.setStatus(State.BEGIN_RETURN_TYPE);
					return null;
				}
				if ((token.getType() == LexicalFlag.SIGN) && token.getContent().equals(",")) {
					this.setStatus(State.PARAMETER_EXPECTED);
					return null;
				}
				this.throwSyntaxError(token);
				return null;
			case PARAMETER_EXPECTED:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				}

				final ParameterAnalyzer parameterAnalyser = new ParameterAnalyzer();
				token = parameterAnalyser.process(lex, token);
				this.parameters.add(parameterAnalyser.getParameter());
				this.setStatus(State.END_PARAMETER);
				return token;

			case BEGIN_RETURN_TYPE:
				this.setParameters();
				if ((token != null) && (token.getType() == LexicalFlag.SIGN) && token.getContent().equals(":")) {
					final TypeAnalyzer ta = new TypeAnalyzer();
					token = ta.process(lex, null);
					this.method.setReturnType(ta.getType());
					this.setStatus(State.FINISHED);
					return token;
				}

				this.setStatus(State.FINISHED);
				return token;

		}
		throw new GWTUMLAPIException("Invalid method format : " + this.getStatus());
	}
}
