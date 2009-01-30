package com.objetdirect.gwt.umlapi.client.analyser;

import com.objetdirect.gwt.umlapi.client.Parameter;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.Token;

public class ParameterAnalyser extends SyntaxAnalyser {

	public static final int BEGIN_TYPE = 1;

	protected Token processToken(LexicalAnalyser lex, Token tk) {
		if (tk == null)
			tk = lex.getToken();
		switch (getStatus()) {
		case BEGIN:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalAnalyser.IDENTIFIER) {
				param.setName(tk.getContent());
				setStatus(BEGIN_TYPE);
				return null;
			} else
				throwSyntaxError(tk);
		case BEGIN_TYPE:
			if (tk != null && tk.getType() == LexicalAnalyser.SIGN
					&& tk.getContent().equals(":")) {
				TypeAnalyser ta = new TypeAnalyser();
				tk = ta.process(lex, null);
				param.setType(ta.getType());
				setStatus(FINISHED);
				return tk;
			} else {
				setStatus(FINISHED);
				return tk;
			}
		}
		throw new UMLDrawerException("Invalid syntax status : " + getStatus());
	}

	public Parameter getParameter() {
		return param;
	}

	Parameter param = new Parameter(null, null);
}
