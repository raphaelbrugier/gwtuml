package com.objetdirect.gwt.umlapi.client.analyser;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;

/**
 * @author Henri Darmet
 */
public class ParameterAnalyzer extends SyntaxAnalyzer {

    Parameter param = new Parameter(null, null);

    /**
     * Getter for the {@link Parameter}
     *
     * @return the parameter
     */
    public Parameter getParameter() {
	return this.param;
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.analyser.SyntaxAnalyzer#processToken(com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer, com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token)
     */
    @Override
    protected Token processToken(final LexicalAnalyzer lex, final Token tk) {
	Token token = tk;
	if (token == null) {
	    token = lex.getToken();
	}
	switch (getStatus()) {
	case BEGIN:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    } else if (token.getType() == LexicalFlag.IDENTIFIER) {
		this.param.setName(token.getContent());
		setStatus(State.BEGIN_TYPE);
		return null;
	    }
	    throwSyntaxError(token);
	    return null;
	case BEGIN_TYPE:
	    if (token != null && token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals(":")) {
		final TypeAnalyzer ta = new TypeAnalyzer();
		token = ta.process(lex, null);
		this.param.setType(ta.getType());
		setStatus(State.FINISHED);
		return token;
	    }
	    setStatus(State.FINISHED);
	    return token;

	}
	throw new UMLDrawerException("Invalid syntax status : " + getStatus());
    }
}