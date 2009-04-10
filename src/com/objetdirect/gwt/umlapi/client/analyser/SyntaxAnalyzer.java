package com.objetdirect.gwt.umlapi.client.analyser;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
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

    private State status = State.BEGIN;

    /**
     * Process the token
     * @param lex The {@link LexicalAnalyzer}
     * @param tk The {@link Token}
     * 
     * @return The processed {@link Token}
     */
    public LexicalAnalyzer.Token process(final LexicalAnalyzer lex,
	    final LexicalAnalyzer.Token tk) {
	Token token = tk;
	while (getStatus() != State.FINISHED) {
	    token = processToken(lex, token);
	}
	return token;
    }

    State getStatus() {
	return this.status;
    }

    void setStatus(final State status) {
	this.status = status;
    }

    protected abstract LexicalAnalyzer.Token processToken(LexicalAnalyzer lex,
	    LexicalAnalyzer.Token tk);

    protected void throwSyntaxError(final LexicalAnalyzer.Token tk) {
	throw new UMLDrawerException("Syntax error at : " + tk.getContent()
		+ " in state : " + this.status);
    }

    protected void throwUnexpectedEOF() {
	throw new UMLDrawerException("Syntax error in state " + this.status);
    }
}
