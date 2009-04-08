package com.objetdirect.gwt.umlapi.client.analyser;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.Token;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;

/**
 * @author florian
 */
public class ParameterAnalyser extends SyntaxAnalyser {

    Parameter param = new Parameter(null, null);

    public Parameter getParameter() {
	return this.param;
    }

    @Override
    protected Token processToken(final LexicalAnalyser lex, final Token tk) {
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
		final TypeAnalyser ta = new TypeAnalyser();
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
