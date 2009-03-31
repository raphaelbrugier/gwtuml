package com.objetdirect.gwt.umlapi.client.analyser;
import java.util.ArrayList;
import java.util.List;

import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
import com.objetdirect.gwt.umlapi.client.analyser.LexicalAnalyser.LexicalFlag;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Method;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Parameter;
import com.objetdirect.gwt.umlapi.client.umlcomponents.Visibility;
/**
 * @author  florian
 */
public class MethodSyntaxAnalyser extends SyntaxAnalyser {
	/**
	 * @uml.property  name="method"
	 * @uml.associationEnd  
	 */
	Method method = new Method(Visibility.PUBLIC, null, null, null);
	List<Parameter> parameters = new ArrayList<Parameter>();
	/**
	 * @return
	 * @uml.property  name="method"
	 */
	public Method getMethod() {
		return method;
	}
	void setParameters() {
	    method.setParameters(parameters);
	}
	@Override
	protected LexicalAnalyser.Token processToken(LexicalAnalyser lex,
			LexicalAnalyser.Token tk) {
		if (tk == null)
			tk = lex.getToken();
		switch (getStatus()) {
		case BEGIN:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalFlag.IDENTIFIER) {
				method.setName(tk.getContent());
				setStatus(State.OPEN_PARENTHESIS_EXPECTED);
				return null;
			} else
				throwSyntaxError(tk);
		case OPEN_PARENTHESIS_EXPECTED:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals("(")) {
				setStatus(State.BEGIN_PARAMETER);
				return null;
			} else
				throwSyntaxError(tk);
		case BEGIN_PARAMETER:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals(")")) {
				setStatus(State.BEGIN_RETURN_TYPE);
				return null;
			} else {
				ParameterAnalyser pa = new ParameterAnalyser();
				tk = pa.process(lex, tk);
				parameters.add(pa.getParameter());
				setStatus(State.END_PARAMETER);
				return tk;
			}
		case END_PARAMETER:
			if (tk == null)
				throwUnexpectedEOF();
			else if (tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals(")")) {
				setStatus(State.BEGIN_RETURN_TYPE);
				return null;
			} else if (tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals(",")) {
				setStatus(State.PARAMETER_EXPECTED);
				return null;
			} else
				throwSyntaxError(tk);
		case PARAMETER_EXPECTED:
			if (tk == null)
				throwUnexpectedEOF();
			else {
				ParameterAnalyser pa = new ParameterAnalyser();
				tk = pa.process(lex, tk);
				parameters.add(pa.getParameter());
				setStatus(State.END_PARAMETER);
				return tk;
			}
		case BEGIN_RETURN_TYPE:
			setParameters();
			if (tk != null && tk.getType() == LexicalFlag.SIGN
					&& tk.getContent().equals(":")) {
				TypeAnalyser ta = new TypeAnalyser();
				tk = ta.process(lex, null);
				method.setReturnType(ta.getType());
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
