package com.objetdirect.gwt.umlapi.client.analyser;
import com.objetdirect.gwt.umlapi.client.UMLDrawerException;
/**
 * @author  florian
 */
public class LexicalAnalyser {
	/**
	 * @author  florian
	 */
	public static class Token {
		/**
		 * @uml.property  name="content"
		 */
		String content;
		/**
		 * @uml.property  name="type"
		 */
		int type;
		public Token(int type, String content) {
			this.type = type;
			this.content = content;
		}
		/**
		 * @return
		 * @uml.property  name="content"
		 */
		public String getContent() {
			return content;
		}
		/**
		 * @return
		 * @uml.property  name="type"
		 */
		public int getType() {
			return type;
		}
	}
	public static final int CHAR = 12;
	public static final int FLOAT = 17;
	public static final int IDENTIFIER = 1;
	public static final int INTEGER = 16;
	public static final int SIGN = 15;
	public static final int SIGN_CONTINUED = 16;
	public static final int STRING = 10;
	static final int CHAR_DEFINED = 14;
	static final int DECIMAL = 5;
	static final int DOT_OR_DECIMAL = 4;
	static final int ESCAPED_CHAR = 13;
	static final int ESCAPED_STRING = 11;
	static final int EXPONENT = 9;
	static final int NUMERIC = 2;
	static final int SIGN_OR_NUMERIC = 3;
	static final int SIGNED_EXPONENT = 7;
	static final int START_DECIMAL = 6;
	static final int START_EXPONENT = 8;
	static final int UNDEFINED = 0;
	int ptr;
	int status = UNDEFINED;
	String text;
	/**
	 * @uml.property  name="token"
	 */
	StringBuffer token = new StringBuffer();
	public LexicalAnalyser(String text) {
		this.text = text;
		this.ptr = 0;
	}
	/**
	 * @return
	 * @uml.property  name="token"
	 */
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
	Token consume(int status, char c) {
		this.token.append(c);
		this.ptr++;
		String content = this.token.toString();
		this.token = new StringBuffer();
		this.status = UNDEFINED;
		return new Token(status, content);
	}
	Token ignore() {
		this.ptr++;
		return null;
	}
	Token inject(int status) {
		String content = this.token.toString();
		this.token = new StringBuffer();
		this.status = UNDEFINED;
		return new Token(status, content);
	}
	Token process(int status, char c) {
		this.token.append(c);
		this.ptr++;
		this.status = status;
		return null;
	}
	Token processEOF() {
		switch (status) {
		case IDENTIFIER:
			return inject(IDENTIFIER);
		case SIGN_OR_NUMERIC:
			return inject(SIGN);
		case DOT_OR_DECIMAL:
			return inject(SIGN);
		case NUMERIC:
			return inject(INTEGER);
		case DECIMAL:
			return inject(FLOAT);
		case EXPONENT:
			return inject(FLOAT);
		case SIGN_CONTINUED:
			return inject(SIGN);
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
				return process(IDENTIFIER, c);
			else if (c == '#' || c == '(' || c == ')' || c == ',' || c == '{'
					|| c == '}' || c == ':' || c == '[' || c == ']')
				return consume(SIGN, c);
			else if (c == '<' || c == '>')
				return process(SIGN_CONTINUED, c);
			else if (c == '+' || c == '-')
				return process(SIGN_OR_NUMERIC, c);
			else if (c == '.')
				return process(DOT_OR_DECIMAL, c);
			else if (c == '\'')
				return process(CHAR, c);
			else if (c == '"')
				return process(STRING, c);
			else if (c >= '0' && c <= '9')
				return process(NUMERIC, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case IDENTIFIER:
			if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_'
					|| c >= '0' && c <= '9')
				return process(IDENTIFIER, c);
			else
				return inject(IDENTIFIER);
		case SIGN_OR_NUMERIC:
			if (c >= '0' && c <= '9')
				return process(NUMERIC, c);
			else if (c == '.')
				return process(DECIMAL, c);
			else
				return inject(SIGN);
		case SIGN_CONTINUED:
			if (c == '=')
				return consume(SIGN, c);
			else
				return inject(SIGN);
		case DOT_OR_DECIMAL:
			if (c >= '0' && c <= '9')
				return process(START_DECIMAL, c);
			else
				return inject(SIGN);
		case STRING:
			if (c == '\\')
				return process(ESCAPED_STRING, c);
			else if (c == '"')
				return consume(STRING, c);
		case ESCAPED_STRING:
			return process(STRING, c);
		case CHAR:
			if (c == '\\')
				return process(ESCAPED_CHAR, c);
			else if (c != '\'')
				return process(CHAR_DEFINED, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case ESCAPED_CHAR:
			return process(CHAR_DEFINED, c);
		case CHAR_DEFINED:
			if (c == '\'')
				return consume(CHAR, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case NUMERIC:
			if (c >= '0' && c <= '9')
				return process(NUMERIC, c);
			else if (c == '.')
				return process(DECIMAL, c);
			else if (c == 'e' || c == 'E')
				return process(SIGNED_EXPONENT, c);
			else
				return consume(INTEGER, c);
		case START_DECIMAL:
			if (c >= '0' && c <= '9')
				return process(DECIMAL, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case DECIMAL:
			if (c >= '0' && c <= '9')
				return process(DECIMAL, c);
			else if (c == 'e' || c == 'E')
				return process(SIGNED_EXPONENT, c);
			else
				return inject(FLOAT);
		case SIGNED_EXPONENT:
			if (c == '+' || c == '-')
				return process(START_EXPONENT, c);
			else if (c >= '0' && c <= '9')
				return process(EXPONENT, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case START_EXPONENT:
			if (c >= '0' && c <= '9')
				return process(EXPONENT, c);
			throw new UMLDrawerException("Invalid character : " + c);
		case EXPONENT:
			if (c >= '0' && c <= '9')
				return process(EXPONENT, c);
			else
				return inject(FLOAT);
		}
		throw new UMLDrawerException("Invalid status : " + status);
	}
}
