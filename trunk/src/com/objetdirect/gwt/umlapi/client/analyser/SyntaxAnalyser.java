package com.objetdirect.gwt.umlapi.client.analyser;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.Token;

/**
 * @author florian
 */
public abstract class SyntaxAnalyser {
    public enum State {
	BEGIN, BEGIN_OPEN_BRACKET, BEGIN_PARAMETER, BEGIN_RETURN_TYPE, BEGIN_TYPE, BEGIN_TYPE_PARAMETER, CLOSE_BRACKET_EXPECTED, END_PARAMETER, END_TYPE_PARAMETER, FINISHED, IDENTIFIER_EXPECTED, OPEN_PARENTHESIS_EXPECTED, PARAMETER_EXPECTED, TYPE_PARAMETER_EXPECTED
    }

    private State status = State.BEGIN;

    State getStatus() {
	return this.status;
    }

    public LexicalAnalyser.Token process(final LexicalAnalyser lex,
	    final LexicalAnalyser.Token tk) {
	Token token = tk;
	while (getStatus() != State.FINISHED) {
	    token = processToken(lex, tk);
	}
	return token;
    }

    protected abstract LexicalAnalyser.Token processToken(LexicalAnalyser lex,
	    LexicalAnalyser.Token tk);

    void setStatus(final State status) {
	this.status = status;
    }

    protected void throwSyntaxError(final LexicalAnalyser.Token tk) {
	throw new UMLDrawerException("Syntax error : " + tk);
    }

    protected void throwUnexpectedEOF() {
	throw new UMLDrawerException("Unexpected EOF");
    }
}
