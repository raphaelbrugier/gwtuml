package com.objetdirect.gwt.umlapi.client.analyser;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.Token;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Visibility;

/**
 * @author Florian Mounier (mounier-dot-florian.at.gmail'dot'com)
 */
public class MethodSyntaxAnalyser extends SyntaxAnalyser {
    Method method = new Method(Visibility.PUBLIC, null, null, null);
    List<Parameter> parameters = new ArrayList<Parameter>();

    public Method getMethod() {
	return method;
    }

    void setParameters() {
	method.setParameters(parameters);
    }

    @SuppressWarnings("fallthrough")
    @Override
    protected LexicalAnalyser.Token processToken(final LexicalAnalyser lex,
	    final LexicalAnalyser.Token tk) {
	Token token = tk;
	if (token == null) {
	    token = lex.getToken();
	}
	switch (getStatus()) {
	case BEGIN:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    }
	    if (token.getType() == LexicalFlag.VISIBILITY) {
		method.setVisibility(Visibility.getVisibilityFromToken(token
			.getContent().charAt(0)));
		setStatus(State.IDENTIFIER_EXPECTED);
		return null;
	    }
	    method.setVisibility(Visibility.PACKAGE);
	case IDENTIFIER_EXPECTED:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    }
	    if (token.getType() == LexicalFlag.IDENTIFIER) {
		method.setName(token.getContent());
		setStatus(State.OPEN_PARENTHESIS_EXPECTED);
		return null;
	    }
	    throwSyntaxError(token);
	    return null;
	case OPEN_PARENTHESIS_EXPECTED:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    }
	    if (token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals("(")) {
		setStatus(State.BEGIN_PARAMETER);
		return null;
	    }
	    throwSyntaxError(token);
	    return null;
	case BEGIN_PARAMETER:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    }
	    if (token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals(")")) {
		setStatus(State.BEGIN_RETURN_TYPE);
		return null;
	    }
	    final ParameterAnalyser pa = new ParameterAnalyser();
	    token = pa.process(lex, token);
	    parameters.add(pa.getParameter());
	    setStatus(State.END_PARAMETER);
	    return token;

	case END_PARAMETER:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    }
	    if (token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals(")")) {
		setStatus(State.BEGIN_RETURN_TYPE);
		return null;
	    }
	    if (token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals(",")) {
		setStatus(State.PARAMETER_EXPECTED);
		return null;
	    }
	    throwSyntaxError(token);
	    return null;
	case PARAMETER_EXPECTED:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    }

	    final ParameterAnalyser parameterAnalyser = new ParameterAnalyser();
	    token = parameterAnalyser.process(lex, token);
	    parameters.add(parameterAnalyser.getParameter());
	    setStatus(State.END_PARAMETER);
	    return token;

	case BEGIN_RETURN_TYPE:
	    setParameters();
	    if (token != null && token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals(":")) {
		final TypeAnalyser ta = new TypeAnalyser();
		token = ta.process(lex, null);
		method.setReturnType(ta.getType());
		setStatus(State.FINISHED);
		return token;
	    }

	    setStatus(State.FINISHED);
	    return token;

	}
	throw new UMLDrawerException("Invalid syntax status : " + getStatus());
    }
}
