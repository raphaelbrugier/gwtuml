package com.objetdirect.gwt.umlapi.client.analyser;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;

/**
 * @author  florian
 */
public abstract class SyntaxAnalyser {

	public static final int BEGIN = 0;
	public static final int FINISHED = -1;

	/**
	 * @uml.property  name="status"
	 */
	private int status = BEGIN;

	public LexicalAnalyser.Token process(LexicalAnalyser lex,
			LexicalAnalyser.Token tk) {
		while (getStatus() != FINISHED) {
			tk = processToken(lex, tk);
		}
		return tk;
	}

	/**
	 * @return
	 * @uml.property  name="status"
	 */
	int getStatus() {
		return status;
	}

	/**
	 * @param status
	 * @uml.property  name="status"
	 */
	void setStatus(int status) {
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
