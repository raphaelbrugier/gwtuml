package com.objetdirect.gwt.umlapi.client.analyser;

import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLClassMethod;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLParameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.UMLVisibility;

/**
 * A method syntax analyzer
 * 
 * @author Henri Darmet
 */
public class MethodSyntaxAnalyzer extends SyntaxAnalyzer {
    UMLClassMethod method = new UMLClassMethod(UMLVisibility.PUBLIC, null, null, null);
    List<UMLParameter> parameters = new ArrayList<UMLParameter>();

    /**
     * Getter for the {@link UMLClassMethod} 
     * 
     * @return The method
     */
    public UMLClassMethod getMethod() {
	return this.method;
    }

    void setParameters() {
	this.method.setParameters(this.parameters);
    }

    /* (non-Javadoc)
     * @see com.objetdirect.gwt.umlapi.client.analyser.SyntaxAnalyzer#processToken(com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer, com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyzer.Token)
     */
    @SuppressWarnings("fallthrough")
    @Override
    protected LexicalAnalyzer.Token processToken(final LexicalAnalyzer lex,
	    final LexicalAnalyzer.Token tk) {
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
		this.method.setVisibility(UMLVisibility.getVisibilityFromToken(token
			.getContent().charAt(0)));
		setStatus(State.IDENTIFIER_EXPECTED);
		return null;
	    }
	    this.method.setVisibility(UMLVisibility.PACKAGE);
	case IDENTIFIER_EXPECTED:
	    if (token == null) {
		throwUnexpectedEOF();
		return null;
	    }
	    if (token.getType() == LexicalFlag.IDENTIFIER) {
		this.method.setName(token.getContent());
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
	    final ParameterAnalyzer pa = new ParameterAnalyzer();
	    token = pa.process(lex, token);
	    this.parameters.add(pa.getParameter());
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

	    final ParameterAnalyzer parameterAnalyser = new ParameterAnalyzer();
	    token = parameterAnalyser.process(lex, token);
	    this.parameters.add(parameterAnalyser.getParameter());
	    setStatus(State.END_PARAMETER);
	    return token;

	case BEGIN_RETURN_TYPE:
	    setParameters();
	    if (token != null && token.getType() == LexicalFlag.SIGN
		    && token.getContent().equals(":")) {
		final TypeAnalyzer ta = new TypeAnalyzer();
		token = ta.process(lex, null);
		this.method.setReturnType(ta.getType());
		setStatus(State.FINISHED);
		return token;
	    }

	    setStatus(State.FINISHED);
	    return token;

	}
	throw new UMLDrawerException("Invalid method format : " + getStatus());
    }
}
