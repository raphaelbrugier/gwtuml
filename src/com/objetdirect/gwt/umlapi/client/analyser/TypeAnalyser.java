package com.objetdirect.gwt.umlapi.client.analyser;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.Token;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class TypeAnalyser extends SyntaxAnalyser {

    String type = "";

    public String getType() {
	return type;
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
		type += token.getContent();
		setStatus(State.BEGIN_TYPE_PARAMETER);
		return null;
	    }
	    throwSyntaxError(token);
	    return null;
	case BEGIN_TYPE_PARAMETER:
	    if (token != null && token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals("<")) {
		final TypeAnalyser ta = new TypeAnalyser();
		token = ta.process(lex, null);
		type += "<" + ta.getType();
		setStatus(State.END_TYPE_PARAMETER);
		return token;
	    } else if (token != null && token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals("[")) {
		type += "[";
		setStatus(State.CLOSE_BRACKET_EXPECTED);
		return null;
	    } else {
		setStatus(State.FINISHED);
		return token;
	    }
	case END_TYPE_PARAMETER:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    } else if (token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals(">")) {
		type += ">";
		setStatus(State.BEGIN_OPEN_BRACKET);
		return null;
	    } else if (token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals(",")) {
		type += ", ";
		setStatus(State.TYPE_PARAMETER_EXPECTED);
		return null;
	    }
	    throwSyntaxError(token);
	    return null;
	case TYPE_PARAMETER_EXPECTED:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    }

	    final TypeAnalyser ta = new TypeAnalyser();
	    token = ta.process(lex, token);
	    type += ta.getType();
	    setStatus(State.END_TYPE_PARAMETER);
	    return token;

	case BEGIN_OPEN_BRACKET:
	    if (token != null && token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals("[")) {
		type += "[";
		setStatus(State.CLOSE_BRACKET_EXPECTED);
		return null;
	    }
	    setStatus(State.FINISHED);
	    return token;

	case CLOSE_BRACKET_EXPECTED:
	    if (token == null) {
		throwUnexpectedEOF();
	    } else if (token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals("]")) {
		type += "]";
		setStatus(State.FINISHED);
		return null;
	    } else {
		throwSyntaxError(token);
	    }
	}
	throw new UMLDrawerException("Invalid syntax status : " + getStatus());
    }
}
