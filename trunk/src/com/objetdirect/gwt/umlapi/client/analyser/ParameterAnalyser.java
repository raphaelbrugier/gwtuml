package com.objetdirect.gwt.umlapi.client.analyser;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.Token;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
/**
 * @author  florian
 */
public class ParameterAnalyser extends SyntaxAnalyser {

	Parameter param = new Parameter(null, null);
	public Parameter getParameter() {
		return param;
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
				param.setName(tk.getContent());
				setStatus(State.BEGIN_TYPE);
				return null;
			} else
				throwSyntaxError(tk);
		case BEGIN_TYPE:
			if (tk != null && tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals(":")) {
				TypeAnalyser ta = new TypeAnalyser();
				tk = ta.process(lex, null);
				param.setType(ta.getType());
				setStatus(State.FINISHED);
				return tk;
			} else {
				setStatus(State.FINISHED);
				return tk;
			}
		}
		throw new UMLDrawerException("Invalid syntax status : " + getStatus());
	}
}
