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
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;

/**
 * @author Henri Darmet
 */
public class ParameterAnalyzer extends SyntaxAnalyzer {

	UMLParameter	param	= new UMLParameter(null, null);

	/**
	 * Getter for the {@link UMLParameter}
	 * 
	 * @return the parameter
	 */
	public UMLParameter getParameter() {
		return this.param;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.objetdirect.gwt.umlapi.client.analyser.SyntaxAnalyzer#processToken(com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer,
	 * com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token)
	 */
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
					this.param.setName(token.getContent());
					this.setStatus(State.BEGIN_TYPE);
					return null;
				}
				this.throwSyntaxError(token);
				return null;
			case BEGIN_TYPE:
				if ((token != null) && (token.getType() == LexicalFlag.SIGN) && token.getContent().equals(":")) {
					final TypeAnalyzer ta = new TypeAnalyzer();
					token = ta.process(lex, null);
					this.param.setType(ta.getType());
					this.setStatus(State.FINISHED);
					return token;
				}
				this.setStatus(State.FINISHED);
				return token;

		}
		throw new GWTUMLAPIException("Invalid syntax status : " + this.getStatus());
	}
}
