package com.objetdirect.gwt.umlapi.client.analyser;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.Token;
/**
 * @author  florian
 */
public class TypeAnalyser extends SyntaxAnalyser {

	/**
	 * @uml.property  name="type"
	 */
	String type = "";
	/**
	 * @return
	 * @uml.property  name="type"
	 */
	public String getType() {
		return type;
	}
	@Override
	protected Token processToken(LexicalAnalyser lex, Token tk) {
		if (tk == null)
			tk = lex.getToken();
		switch (getStatus()) {
		case BEGIN:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalFlag.IDENTIFIER) {
				type += tk.getContent();
				setStatus(State.BEGIN_TYPE_PARAMETER);
				return null;
			} else
				throwSyntaxError(tk);
		case BEGIN_TYPE_PARAMETER:
			if (tk != null && tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals("<")) {
				TypeAnalyser ta = new TypeAnalyser();
				tk = ta.process(lex, null);
				type += "<" + ta.getType();
				setStatus(State.END_TYPE_PARAMETER);
				return tk;
			} else if (tk != null && tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals("[")) {
				type += "[";
				setStatus(State.CLOSE_BRACKET_EXPECTED);
				return null;
			} else {
				setStatus(State.FINISHED);
				return tk;
			}
		case END_TYPE_PARAMETER:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals(">")) {
				type += ">";
				setStatus(State.BEGIN_OPEN_BRACKET);
				return null;
			} else if (tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals(",")) {
				type += ", ";
				setStatus(State.TYPE_PARAMETER_EXPECTED);
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
				setStatus(State.END_TYPE_PARAMETER);
				return tk;
			}
		case BEGIN_OPEN_BRACKET:
			if (tk != null && tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals("[")) {
				type += "[";
				setStatus(State.CLOSE_BRACKET_EXPECTED);
				return null;
			} else {
				setStatus(State.FINISHED);
				return tk;
			}
		case CLOSE_BRACKET_EXPECTED:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals("]")) {
				type += "]";
				setStatus(State.FINISHED);
				return null;
			} else
				throwSyntaxError(tk);
		}
		throw new UMLDrawerException("Invalid syntax status : " + getStatus());
	}
}
