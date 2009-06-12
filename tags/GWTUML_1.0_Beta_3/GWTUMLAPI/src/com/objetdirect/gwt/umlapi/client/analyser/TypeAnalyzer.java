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

import com.objetdirect.gwt.umlapi.client.GWTUMLAPIException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token;

/**
 * @author Henri Darmet
 */
public class TypeAnalyzer extends SyntaxAnalyzer {

	String	type	= "";

	/**
	 * Getter for the type
	 * 
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	@Override
	protected Token processToken(final LexicalAnalyzer lex, final Token tk) {
		Token token = tk;

		if (token == null) {
			token = lex.getToken();
		}
		switch (this.getStatus()) {
			case BEGIN:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				} else if (token.getType() == LexicalFlag.IDENTIFIER) {
					this.type += token.getContent();
					this.setStatus(State.BEGIN_TYPE_PARAMETER);
					return null;
				}
				this.throwSyntaxError(token);
				return null;
			case BEGIN_TYPE_PARAMETER:
				if ((token != null) && (token.getType() == LexicalFlag.SIGN) && token.getContent().equals("<")) {
					final TypeAnalyzer ta = new TypeAnalyzer();
					token = ta.process(lex, null);
					this.type += "<" + ta.getType();
					this.setStatus(State.END_TYPE_PARAMETER);
					return token;
				} else if ((token != null) && (token.getType() == LexicalFlag.SIGN) && token.getContent().equals("[")) {
					this.type += "[";
					this.setStatus(State.CLOSE_BRACKET_EXPECTED);
					return null;
				} else {
					this.setStatus(State.FINISHED);
					return token;
				}
			case END_TYPE_PARAMETER:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				} else if ((token.getType() == LexicalFlag.SIGN) && token.getContent().equals(">")) {
					this.type += ">";
					this.setStatus(State.BEGIN_OPEN_BRACKET);
					return null;
				} else if ((token.getType() == LexicalFlag.SIGN) && token.getContent().equals(",")) {
					this.type += ", ";
					this.setStatus(State.TYPE_PARAMETER_EXPECTED);
					return null;
				}
				this.throwSyntaxError(token);
				return null;
			case TYPE_PARAMETER_EXPECTED:
				if (token == null) {
					this.throwUnexpectedEOF();
					return null;
				}

				final TypeAnalyzer ta = new TypeAnalyzer();
				token = ta.process(lex, token);
				this.type += ta.getType();
				this.setStatus(State.END_TYPE_PARAMETER);
				return token;

			case BEGIN_OPEN_BRACKET:
				if ((token != null) && (token.getType() == LexicalFlag.SIGN) && token.getContent().equals("[")) {
					this.type += "[";
					this.setStatus(State.CLOSE_BRACKET_EXPECTED);
					return null;
				}
				this.setStatus(State.FINISHED);
				return token;

			case CLOSE_BRACKET_EXPECTED:
				if (token == null) {
					this.throwUnexpectedEOF();
				} else if ((token.getType() == LexicalFlag.SIGN) && token.getContent().equals("]")) {
					this.type += "]";
					this.setStatus(State.FINISHED);
					return null;
				} else {
					this.throwSyntaxError(token);
				}
		}
		throw new GWTUMLAPIException("Invalid syntax status : " + this.getStatus());
	}
}
