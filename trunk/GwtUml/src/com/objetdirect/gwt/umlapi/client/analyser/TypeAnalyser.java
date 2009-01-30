package com.objetdirect.gwt.umlapi.client.analyser;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.Token;

public class TypeAnalyser extends SyntaxAnalyser {

	public static final int BEGIN_TYPE_PARAMETER = 1;
	public static final int END_TYPE_PARAMETER = 2;
	public static final int TYPE_PARAMETER_EXPECTED = 3;
	public static final int BEGIN_OPEN_BRACKET = 4;
	public static final int CLOSE_BRACKET_EXPECTED = 5;

	protected Token processToken(LexicalAnalyser lex, Token tk) {
		if (tk == null)
			tk = lex.getToken();
		switch (getStatus()) {
		case BEGIN:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalAnalyser.IDENTIFIER) {
				type += tk.getContent();
				setStatus(BEGIN_TYPE_PARAMETER);
				return null;
			} else
				throwSyntaxError(tk);
		case BEGIN_TYPE_PARAMETER:
			if (tk != null && tk.getType() == LexicalAnalyser.SIGN
					&& tk.getContent().equals("<")) {
				TypeAnalyser ta = new TypeAnalyser();
				tk = ta.process(lex, null);
				type += "<" + ta.getType();
				setStatus(END_TYPE_PARAMETER);
				return tk;
			} else if (tk != null && tk.getType() == LexicalAnalyser.SIGN
					&& tk.getContent().equals("[")) {
				type += "[";
				setStatus(CLOSE_BRACKET_EXPECTED);
				return null;
			} else {
				setStatus(FINISHED);
				return tk;
			}
		case END_TYPE_PARAMETER:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalAnalyser.SIGN
					&& tk.getContent().equals(">")) {
				type += ">";
				setStatus(BEGIN_OPEN_BRACKET);
				return null;
			} else if (tk.getType() == LexicalAnalyser.SIGN
					&& tk.getContent().equals(",")) {
				type += ", ";
				setStatus(TYPE_PARAMETER_EXPECTED);
				return null;
			} else
				throwSyntaxError(tk);
		case TYPE_PARAMETER_EXPECTED:
			if (tk == null)
				throwUnexpectedEOF();
			else {
				TypeAnalyser ta = new TypeAnalyser();
				tk = ta.process(lex, tk);
				type += ta.getType();
				setStatus(END_TYPE_PARAMETER);
				return tk;
			}

		case BEGIN_OPEN_BRACKET:
			if (tk != null && tk.getType() == LexicalAnalyser.SIGN
					&& tk.getContent().equals("[")) {
				type += "[";
				setStatus(CLOSE_BRACKET_EXPECTED);
				return null;
			} else {
				setStatus(FINISHED);
				return tk;
			}
		case CLOSE_BRACKET_EXPECTED:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalAnalyser.SIGN
					&& tk.getContent().equals("]")) {
				type += "]";
				setStatus(FINISHED);
				return null;
			} else
				throwSyntaxError(tk);
		}
		throw new UMLDrawerException("Invalid syntax status : " + getStatus());
	}

	public String getType() {
		return type;
	}

	String type = "";
}
