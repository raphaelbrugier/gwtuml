package com.objetdirect.gwt.umlapi.client.analyser;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
/**
 * @author  henri, florian
 */
public class LexicalAnalyser {
	/**
	 * @author  henri, florian
	 */
	public static class Token {
		/**
		 * 
		 */
		String content;
		/**
		 * 
		 */
		LexicalFlag type;
		public Token(LexicalFlag type, String content) {
			this.type = type;
			this.content = content;
		}
		/**
		 * @return
		 * 
		 */
		public String getContent() {
			return content;
		}
		/**
		 * @return
		 * 
		 */
		public LexicalFlag getType() {
			return type;
		}
	}
	public enum LexicalFlag {
		CHAR, FLOAT, IDENTIFIER, INTEGER,  SIGN,  SIGN_CONTINUED, STRING,  CHAR_DEFINED,
		DECIMAL, DOT_OR_DECIMAL, ESCAPED_CHAR, ESCAPED_STRING, EXPONENT, NUMERIC,
		SIGN_OR_NUMERIC, SIGNED_EXPONENT, START_DECIMAL, START_EXPONENT , UNDEFINED;
	}
	int ptr;
	LexicalFlag status = LexicalFlag.UNDEFINED;
	String text;
	 
	StringBuffer token = new StringBuffer();
	public LexicalAnalyser(String text) {
		this.text = text;
		this.ptr = 0;
	}
public Token getToken() {
		Token token = null;
		while (token == null) {
			if (this.ptr >= text.length()) {
				if (this.token.length() > 0) {
					token = processEOF();
					if (token != null)
						return token;
					else
						throw new UMLDrawerException("Unexpected EOF");
				} else
					return null;
			}
			token = processNextChar();
		}
		return token;
	}
	Token consume(LexicalFlag status, char c) {
		this.token.append(c);
		this.ptr++;
		String content = this.token.toString();
		this.token = new StringBuffer();
		this.status = LexicalFlag.UNDEFINED;
		return new Token(status, content);
	}
	Token ignore() {
		this.ptr++;
		return null;
	}
	Token inject(LexicalFlag status) {
		String content = this.token.toString();
		this.token = new StringBuffer();
		this.status = LexicalFlag.UNDEFINED;
		return new Token(status, content);
	}
	Token process(LexicalFlag status, char c) {
		this.token.append(c);
		this.ptr++;
		this.status = status;
		return null;
	}
	Token processEOF() {
		switch (status) {
		case IDENTIFIER:
			return inject(LexicalFlag.IDENTIFIER);
		case SIGN_OR_NUMERIC:
			return inject(LexicalFlag.SIGN);
		case DOT_OR_DECIMAL:
			return inject(LexicalFlag.SIGN);
		case NUMERIC:
			return inject(LexicalFlag.INTEGER);
		case DECIMAL:
			return inject(LexicalFlag.FLOAT);
		case EXPONENT:
			return inject(LexicalFlag.FLOAT);
		case SIGN_CONTINUED:
			return inject(LexicalFlag.SIGN);
		}
		return null;
	}
	Token processNextChar() {
		char c = text.charAt(this.ptr);
		switch (status) {
		case UNDEFINED:
			if (c == ' ')
				return ignore();
			else if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_')
				return process(LexicalFlag.IDENTIFIER, c);
			else if (c == '#' || c == '(' || c == ')' || c == ',' || c == '{'
				|| c == '}' || c == ':' || c == '[' || c == ']')
				return consume(LexicalFlag.SIGN, c);
			else if (c == '<' || c == '>')
				return process(LexicalFlag.SIGN_CONTINUED, c);
			else if (c == '+' || c == '-')
				return process(LexicalFlag.SIGN_OR_NUMERIC, c);
			else if (c == '.')
				return process(LexicalFlag.DOT_OR_DECIMAL, c);
			else if (c == '\'')
				return process(LexicalFlag.CHAR, c);
			else if (c == '"')
				return process(LexicalFlag.STRING, c);
			else if (c >= '0' && c <= '9')
				return process(LexicalFlag.NUMERIC, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case IDENTIFIER:
			if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_'
				|| c >= '0' && c <= '9')
				return process(LexicalFlag.IDENTIFIER, c);
			else
				return inject(LexicalFlag.IDENTIFIER);
		case SIGN_OR_NUMERIC:
			if (c >= '0' && c <= '9')
				return process(LexicalFlag.NUMERIC, c);
			else if (c == '.')
				return process(LexicalFlag.DECIMAL, c);
			else
				return inject(LexicalFlag.SIGN);
		case SIGN_CONTINUED:
			if (c == '=')
				return consume(LexicalFlag.SIGN, c);
			else
				return inject(LexicalFlag.SIGN);
		case DOT_OR_DECIMAL:
			if (c >= '0' && c <= '9')
				return process(LexicalFlag.START_DECIMAL, c);
			else
				return inject(LexicalFlag.SIGN);
		case STRING:
			if (c == '\\')
				return process(LexicalFlag.ESCAPED_STRING, c);
			else if (c == '"')
				return consume(LexicalFlag.STRING, c);
		case ESCAPED_STRING:
			return process(LexicalFlag.STRING, c);
		case CHAR:
			if (c == '\\')
				return process(LexicalFlag.ESCAPED_CHAR, c);
			else if (c != '\'')
				return process(LexicalFlag.CHAR_DEFINED, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case ESCAPED_CHAR:
			return process(LexicalFlag.CHAR_DEFINED, c);
		case CHAR_DEFINED:
			if (c == '\'')
				return consume(LexicalFlag.CHAR, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case NUMERIC:
			if (c >= '0' && c <= '9')
				return process(LexicalFlag.NUMERIC, c);
			else if (c == '.')
				return process(LexicalFlag.DECIMAL, c);
			else if (c == 'e' || c == 'E')
				return process(LexicalFlag.SIGNED_EXPONENT, c);
			else
				return consume(LexicalFlag.INTEGER, c);
		case START_DECIMAL:
			if (c >= '0' && c <= '9')
				return process(LexicalFlag.DECIMAL, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case DECIMAL:
			if (c >= '0' && c <= '9')
				return process(LexicalFlag.DECIMAL, c);
			else if (c == 'e' || c == 'E')
				return process(LexicalFlag.SIGNED_EXPONENT, c);
			else
				return inject(LexicalFlag.FLOAT);
		case SIGNED_EXPONENT:
			if (c == '+' || c == '-')
				return process(LexicalFlag.START_EXPONENT, c);
			else if (c >= '0' && c <= '9')
				return process(LexicalFlag.EXPONENT, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case START_EXPONENT:
			if (c >= '0' && c <= '9')
				return process(LexicalFlag.EXPONENT, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case EXPONENT:
			if (c >= '0' && c <= '9')
				return process(LexicalFlag.EXPONENT, c);
			else
				return inject(LexicalFlag.FLOAT);
		}
		throw new UMLDrawerException("Invalid status : " + status);
	}
}
