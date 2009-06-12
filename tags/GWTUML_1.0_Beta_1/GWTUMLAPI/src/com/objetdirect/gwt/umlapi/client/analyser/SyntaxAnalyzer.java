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
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token;

/**
 * @author Henri Darmet
 */
public abstract class SyntaxAnalyzer {
	/**
	 * This enumeration lists all the possible syntax states
	 * 
	 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
	 */
	public enum State {
		/**
	 * 
	 */
		BEGIN,
		/**
	 * 
	 */
		BEGIN_OPEN_BRACKET,
		/**
	 * 
	 */
		BEGIN_PARAMETER,
		/**
	 * 
	 */
		/**
	 * 
	 */
		BEGIN_RETURN_TYPE,
		/**
	 * 
	 */
		BEGIN_TYPE,
		/**
	 * 
	 */
		BEGIN_TYPE_PARAMETER,
		/**
	 * 
	 */
		CLOSE_BRACKET_EXPECTED,
		/**
	 * 
	 */
		END_PARAMETER,
		/**
	 * 
	 */
		END_TYPE_PARAMETER,
		/**
	 * 
	 */
		FINISHED,
		/**
	 * 
	 */
		IDENTIFIER_EXPECTED,
		/**
	 * 
	 */
		OPEN_PARENTHESIS_EXPECTED,
		/**
	 * 
	 */
		PARAMETER_EXPECTED,
		/**
	 * 
	 */
		TYPE_PARAMETER_EXPECTED;
	}

	private State	status	= State.BEGIN;

	/**
	 * Process the token
	 * 
	 * @param lex
	 *            The {@link LexicalAnalyzer}
	 * @param tk
	 *            The {@link Token}
	 * 
	 * @return The processed {@link Token}
	 */
	public LexicalAnalyzer.Token process(final LexicalAnalyzer lex, final LexicalAnalyzer.Token tk) {
		Token token = tk;
		while (this.getStatus() != State.FINISHED) {
			token = this.processToken(lex, token);
		}
		return token;
	}

	State getStatus() {
		return this.status;
	}

	void setStatus(final State status) {
		this.status = status;
	}

	protected abstract LexicalAnalyzer.Token processToken(LexicalAnalyzer lex, LexicalAnalyzer.Token tk);

	protected void throwSyntaxError(final LexicalAnalyzer.Token tk) {
		throw new GWTUMLAPIException("Syntax error at : " + tk.getContent() + " in state : " + this.status);
	}

	protected void throwUnexpectedEOF() {
		throw new GWTUMLAPIException("Syntax error in state " + this.status);
	}
}
