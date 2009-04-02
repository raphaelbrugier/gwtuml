package com.objetdirect.gwt.umlapi.client.analyser;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
/**
 * @author  florian
 */
public abstract class SyntaxAnalyser {
	public enum State { 
		BEGIN, BEGIN_TYPE, FINISHED, BEGIN_PARAMETER,
		BEGIN_RETURN_TYPE, END_PARAMETER, 
		OPEN_PARENTHESIS_EXPECTED, PARAMETER_EXPECTED,
		BEGIN_OPEN_BRACKET, BEGIN_TYPE_PARAMETER, CLOSE_BRACKET_EXPECTED,
		END_TYPE_PARAMETER, TYPE_PARAMETER_EXPECTED}
	 
	private State status = State.BEGIN;
	public LexicalAnalyser.Token process(LexicalAnalyser lex,
			LexicalAnalyser.Token tk) {
		while (getStatus() != State.FINISHED) {
			tk = processToken(lex, tk);
		}
		return tk;
	}
State getStatus() {
		return status;
	}
 
	void setStatus(State status) {
		this.status = status;
	}
	protected abstract LexicalAnalyser.Token processToken(LexicalAnalyser lex,
			LexicalAnalyser.Token tk);
	protected void throwSyntaxError(LexicalAnalyser.Token tk) {
		throw new UMLDrawerException("Syntax error : " + tk);
	}
	protected void throwUnexpectedEOF() {
		throw new UMLDrawerException("Unexpected EOF");
	}
}
