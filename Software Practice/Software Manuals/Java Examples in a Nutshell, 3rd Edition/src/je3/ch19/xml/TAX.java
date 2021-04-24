/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch19.xml;

import java.io.IOException;
import java.io.Reader;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import je3.ch02.classes.Tokenizer;
import je3.ch02.classes.CharSequenceTokenizer;
import je3.ch03.io.ReaderTokenizer;
import je3.ch06.nio.ChannelTokenizer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 558-568. This class, whose
 * name is an acronym for "Trivial API for XML", is a container for a simple
 * Parser class for parsing XML and its related Token, TokenType and
 * ParseException classes and constants.
 * 
 * TAX.Parser is a simple, lightweight pull-parser that is useful for a variety
 * of simple XML parsing tasks. Note, however, that it is more of a tokenizer
 * than a true parser and that the grammar it parses is not actually XML, but a
 * simplified subset of XML. The parser has (at least) these limitations:
 * 
 * <pre>
 *   It does not enforce well-formedness. For example, it does not require
 *      tags to be properly nested.
 *   It is not a validating parser, and does not read external DTDs.
 *   It does not parse the internal subset of the DOCTYPE tag, and cannot
 *      recognize any entities defined there.
 *   It is not name-space aware.
 *   It does not handle entity or character references in attribute values,
 *      not even pre-defined entities such as quotation marks (&quot;).
 *   It strips all whitespace from the start and end of document text, which,
 *      while useful for many documents, is not generally correct.
 *   It makes no attempt to do error recovery. The results of calling next()
 *      after a ParseException is thrown are undefined.
 *   It does not provide enough detail to reconstruct the source document.
 * </pre>
 * 
 * TAX.Parser always replaces entity references with their values, or throws a
 * Tax.ParseException if no replacement value is known. The parser coalesces
 * adjacent text and entities into a single TEXT token. CDATA sections are also
 * returned as TEXT tokens, but are not coalesced.
 * <p/>
 * To test this TAX file, try launching ListServlets2, which has a Run
 * Configuration defined (named the standard "ListServlets2"). You will need to
 * customize the Run Configuration with information about your local file
 * system.
 * 
 */
public class TAX {

	public static enum TokenType {

		/* <GUTENTAG> An opening tag */
		TAG(true),
		/* </GUTENTAG> A closing tag */
		ENDTAG(true),
		/* */
		TEXT(true),
		/* <!-- A comment --> */
		COMMENT(false),
		/* Processing Instruction */
		PI(false),
		/* Document Type */
		DOCTYPE(false),
		/* XML Declaration */
		XMLDECL(false);

		TokenType(boolean isToBeReturned) {
			this.isToBeReturned = isToBeReturned;
		}

		private boolean isToBeReturned;

		public void setIsToBeReturned(boolean value) {
			this.isToBeReturned = value;
		}

		public boolean getIsToBeReturned() {
			return this.isToBeReturned;
		}
	}

	/*
	 * Token objects are the return value of the Parser.next() method. They provide
	 * details about what was parsed and where.
	 */
	public static class Token {
		// One of the constants above:
		private TokenType type;
		// The complete text, minus delimiters for other types:
		private String text;

		// The position of the start of the token:
		private int line, column;
		// The name/value map for TAG and XMLDECL, null otherwise.
		private Map<String, String> attributes;
		// This is true for XMLDECL and TAGs ending with "/>".
		private boolean empty;

		// We use this constructor for TAG and XMLDECL tokens.
		Token(TokenType tokenType, String string, int line, int column, Map<String, String> attributes, boolean empty) {
			this(tokenType, string, line, column);
			this.attributes = attributes;
			this.empty = empty;
		}

		// We use this constructor for other token types.
		Token(TokenType type, String text, int line, int column) {
			this.type = type;
			this.text = text;
			this.line = line;
			this.column = column;
		}

		// These are property accessor methods.
		public TokenType getType() {
			return type;
		}

		public String getText() {
			return text;
		}

		public int getLine() {
			return line;
		}

		public int getColumn() {
			return column;
		}

		public Map<String, String> getAttributes() {
			return attributes;
		}

		public boolean getEmpty() {
			return empty;
		}

		public boolean isEmpty() {
			return empty;
		}
	}

	// Exceptions of this type are thrown for syntax errors or unknown entities.
	public static class ParseException extends Exception {

		private static final long serialVersionUID = 8202493642932716514L;

		public ParseException(String message) {
			super(message);
		}

		static ParseException expected(Token token, String expected) {
			String message = "Expected " + expected + " at line " + token.getLine() + ", column " + token.getColumn();
			return new ParseException(message);
		}
	}

	/**
	 * This is the parser class. It relies internally on a Tokenizer. The public
	 * constructors allow you to parse XML from a CharSequence, a Reader, or a
	 * Channel. By default, it will return tokens of type TAG, ENDTAG, and TEXT, and
	 * will ignore all others. You can change this behavior by passing token type
	 * constants to returnTokens() or ignoreTokens(). By default the parser will
	 * replace character entities and the pre-defined entities &amp;amp; ('&'),
	 * &amp;lt; ('<'), &amp;gt; ('>'), &amp;quot; ('"'), and &amp;apos; ('); with
	 * their values. You can define new entity name/replacement pairs by calling
	 * defineEntity(). These configuration methods all return the Parser objects so
	 * calls can be chained. After configuring your Parser, call the next() method
	 * repeatedly until it returns null.
	 */
	public static class Parser {
		// Used to break up the input:
		Tokenizer tokenizer;
		// Map the entity name to its replacement:
		Map<String, String> entityMap;
		// Should we return tokens of these types?
		boolean[] returnTokenType = new boolean[8];

		public Parser(CharSequence text) {
			this(new CharSequenceTokenizer(text));
		}

		public Parser(Reader in) {
			this(new ReaderTokenizer(in));
		}

		public Parser(ReadableByteChannel in, Charset encoding) {
			this(new ChannelTokenizer(in, encoding));
		}

		Parser(Tokenizer tokenizer) {
			this.tokenizer = tokenizer;
			// Always tokenize the spaces:
			tokenizer.tokenizeSpaces(true);
			// Track the line number and the column number:
			tokenizer.trackPosition(true);
			/*
			 * We don't always want the tokenizer to tokenize words, but when we do, this is
			 * how we want the words formed.
			 */
			tokenizer.wordRecognizer(new Tokenizer.WordRecognizer() {
				public boolean isWordStart(char c) {
					return Character.isLetter(c) || c == '_' || c == ':';
				}

				public boolean isWordPart(char c, char first) {
					if (Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.' || c == ':') {
						return true;
					}
					int type = Character.getType(c);
					if (type == Character.COMBINING_SPACING_MARK) {
						return true;
					}
					if (type == Character.ENCLOSING_MARK) {
						return true;
					}
					if (type == Character.NON_SPACING_MARK) {
						return true;
					}
					if (type == Character.MODIFIER_LETTER) {
						return true;
					}
					return false;
				}
			});

			// Set pre-defined entities
			entityMap = new HashMap<String, String>();
			entityMap.put("lt", "<");
			entityMap.put("gt", ">");
			entityMap.put("amp", "&");
			entityMap.put("quot", "\"");
			entityMap.put("apos", "'");
		}

		public Parser returnTokens(TokenType tokenType) {
			tokenType.setIsToBeReturned(true);
			return this;
		}

		public Parser ignoreTokens(TokenType tokenType) {
			tokenType.setIsToBeReturned(false);
			return this;
		}

		/*
		 * Define a mapping from entity name to entity replacement. Note that the entity
		 * name should not include the & or ; delimiters.
		 */
		public Parser defineEntity(String name, String replacement) {
			entityMap.put(name, replacement);
			return this;
		}

		// This utility method is for reporting parsing errors.
		void syntax(String message) throws ParseException {
			throw new ParseException(message + " at " + tokenizer.tokenLine() + ":" + tokenizer.tokenColumn());
		}

		/*
		 * This method returns the next XML token of input or null if there is no more
		 * input to parse.
		 */
		public Token next() throws ParseException, IOException {
			Token token = null;

			// Otherwise, loop until we find a token we want to return:
			for (;;) {
				/*
				 * Invariant: we keep the tokenizer on the first unparsed token. This means we
				 * start our methods by calling tokenType() to examine what we're currently on,
				 * not by calling next(). But we end by calling next() to consume the stuff
				 * we've already seen.
				 */
				int tokenType = tokenizer.tokenType();

				// If we're at the tokenizer's start state, then read a token.
				if (tokenType == Tokenizer.BOF) {
					tokenType = tokenizer.next();
				}

				// If there is no more input, return null
				if (tokenType == Tokenizer.EOF) {
					return null;
				}

				/*
				 * Skip any space. This is not technically correct: we don't know if this is
				 * ignorable whitespace or not. But in practice, most clients will want to
				 * ignore it.
				 */
				if (tokenType == Tokenizer.SPACE) {
					tokenizer.next();
					continue;
				}

				/*
				 * If the token is a open angle bracket, then this is mark-up (tags). Otherwise,
				 * it is text.
				 */
				if (tokenType == '<') {
					token = parseMarkup();
				} else {
					token = parseText();
				}

				/*
				 * If the token we've parsed is one of the kind to be returned, then return it.
				 * Otherwise, continue looping for a new token.
				 */
				if (token.type.getIsToBeReturned() == true) {
					return token;
				}
			}
		}

		/*
		 * Read the next token and return it if it is a TAG with the specified tag name.
		 * Otherwise throw a ParseException.
		 */
		public Token expect(String tagName) throws ParseException, IOException {
			Token token = next();
			if (token == null || token.getType() != TokenType.TAG || token.getText().equals(tagName) == false) {
				throw ParseException.expected(token, "<" + tagName + ">");
			}
			return token;
		}

		/*
		 * Read and return the next token, if it is of the specified type. Otherwise
		 * throw a ParseException.
		 */
		public Token expect(TokenType tokenType) throws ParseException, IOException {
			Token token = next();
			if (token == null || token.getType() != tokenType) {
				throw ParseException.expected(token, tokenType.toString());
			}
			return token;
		}

		/*
		 * This method is called with a current token of '<' to parse various forms of
		 * XML mark-up (tags).
		 */
		Token parseMarkup() throws ParseException, IOException {
			assert tokenizer.tokenType() == '<' : tokenizer.tokenType();
			try {
				// Turn on word tokenizing. It is turned off in the "finally" clause.
				tokenizer.tokenizeWords(true);
				int tokenType = tokenizer.next();
				if (tokenType == '?') {
					// The mark-up is a PI (Processing Instruction) or XMLDECL.
					tokenType = tokenizer.next();
					if (tokenType != Tokenizer.WORD) {
						syntax("XMLDECL or PI expected");
					}
					if (tokenizer.tokenText().equals("xml")) {
						Token token = new Token(TokenType.XMLDECL, tokenizer.tokenText(), tokenizer.tokenLine(),
								tokenizer.tokenColumn() - 2, parseAttributes(), true);

						if (tokenizer.tokenType() != '?') {
							syntax("'?' expected");
						}
						if (tokenizer.next() != '>') {
							syntax("'>' expected");
						}
						return token;
					} else {
						Token token = new Token(TokenType.PI, null, tokenizer.tokenLine(), tokenizer.tokenColumn() - 2);
						// Read to the end of the Processing Instruction (PI).
						tokenizer.scan("?>", true, true, false, true);
						token.text = tokenizer.tokenText();
						return token;
					}
				}

				if (tokenType == '!') {
					// Mark-up is DOCTYPE, CDATA, or a Comment.
					tokenType = tokenizer.next();
					if (tokenType == Tokenizer.WORD && tokenizer.tokenText().equals("DOCTYPE")) {
						return parseDoctype();
					} else if (tokenType == '[') {
						if (tokenizer.next() == Tokenizer.WORD && tokenizer.tokenText().equals("CDATA")
								&& tokenizer.next() == '[') {
							Token token = new Token(TokenType.TEXT, null, tokenizer.tokenLine(),
									tokenizer.tokenColumn() - 8);
							tokenizer.scan("]]>", true, false, false, true);
							token.text = tokenizer.tokenText();
							return token;
						} else {
							syntax("CDATA expected");
						}
					} else if (tokenType == '-' && tokenizer.next() == '-') {
						// This is a COMMENT token.
						Token token = new Token(TokenType.COMMENT, null, tokenizer.tokenLine(),
								tokenizer.tokenColumn() - 4);
						tokenizer.scan("-->", true, false, false, true);
						token.text = tokenizer.tokenText();
						return token;
					} else {
						syntax("DOCTYPE, CDATA, or Comment expected");
					}
				}

				if (tokenType == '/') {
					// The mark-up is an element end tag.
					tokenType = tokenizer.next();
					if (tokenType == Tokenizer.WORD) {
						Token token = new Token(TokenType.ENDTAG, tokenizer.tokenText(), tokenizer.tokenLine(),
								tokenizer.tokenColumn() - 2);

						tokenType = tokenizer.next();
						if (tokenType == Tokenizer.SPACE) {
							tokenType = tokenizer.next();
						}
						if (tokenType != '>') {
							syntax("Expected '>'");
						}
						return token;
					} else {
						syntax("ENDTAG expected.");
					}
				}

				if (tokenType == Tokenizer.WORD) {
					// The mark-up is an element start tag.
					String tokenText = tokenizer.tokenText();
					int tokenLine = tokenizer.tokenLine();
					int tokenColumn = tokenizer.tokenColumn() - 1;
					Map<String, String> attributes = parseAttributes();
					boolean isTokenEmpty = tokenizer.tokenType() == '/';
					Token token = new Token(TokenType.TAG, tokenText, tokenLine, tokenColumn, attributes, isTokenEmpty);

					if (tokenizer.tokenType() == '/') {
						tokenizer.next();
					}
					if (tokenizer.tokenType() != '>') {
						syntax("'>' expected");
					}
					return token;
				}

				// If none of the above matched, this is a syntax error.
				syntax("Invalid character following '<'");

				/*
				 * The compiler doesn't realize that syntax() never returns, so it requires a
				 * return statement here.
				 */
				return null;
			} finally {
				// Restore the tokenizer state.
				tokenizer.tokenizeWords(false);
				// Get the next token ready.
				tokenizer.next();
			}
		}

		Token parseDoctype() throws IOException {
			assert (tokenizer.tokenType() == Tokenizer.WORD && tokenizer.tokenText().equals("DOCTYPE"));

			int line = tokenizer.tokenLine();
			int column = tokenizer.tokenColumn();
			StringBuffer stringBuffer = new StringBuffer();

			int tokenType = tokenizer.next();
			while (tokenType != '>' && tokenType != '[' && tokenType != Tokenizer.EOF) {
				stringBuffer.append(tokenizer.tokenText());
				tokenType = tokenizer.next();
			}

			if (tokenType == '[') {
				// If there is an internal subset, scan for its end.
				tokenizer.scan("]>", true, true, false, true);
				stringBuffer.append(tokenizer.tokenText());
				stringBuffer.append(']');
			}

			return new Token(TokenType.DOCTYPE, stringBuffer.toString(), line, column);
		}

		/*
		 * Parse a sequence of name=value attributes, where value is always quoted in
		 * single or double quotes, and return them as a Map. When this method is
		 * called, the tokenizer is looking at the element name, not at the first token
		 * to parse. This is used when parsing element start tags and XMLDECLs.
		 */
		Map<String, String> parseAttributes() throws ParseException, IOException {
			try {
				/*
				 * Adjust tokenizer to recognize quotes. Defaults are restored in finally clause
				 * below
				 */
				tokenizer.quotes("'\"", "'\"");
				// Consume the element name:
				int tokenType = tokenizer.next();

				// Skip optional space:
				if (tokenType == Tokenizer.SPACE) {
					tokenType = tokenizer.next();
				}

				// This is a special case for elements with no attributes.
				if (tokenType != Tokenizer.WORD) {
					@SuppressWarnings("unchecked")
					Map<String, String> emptyMap = Collections.EMPTY_MAP;
					return (emptyMap);
				}

				// Where we'll store our attributes.
				Map<String, String> attributeNameToValue = new HashMap<String, String>();

				while (tokenType == Tokenizer.WORD) {
					// Get the attribute name:
					String name = tokenizer.tokenText();
					// The next token must be '=':
					if (tokenizer.next() != '=') {
						syntax("'=' expected");
					}
					tokenType = tokenizer.next();
					// The next token must be a quoted string:
					if (tokenType != '"' && tokenType != '\'') {
						syntax("quoted attribute value expected");
					}
					/*
					 * Map attribute name to attribute value. The tokenizer strips the quotes for
					 * us. Note that we do not handle entity references here.
					 */
					attributeNameToValue.put(name, tokenizer.tokenText());
					// Consume the value and skip an optional space after it.
					tokenType = tokenizer.next();
					if (tokenType == Tokenizer.SPACE) {
						tokenType = tokenizer.next();
					}
				}
				return attributeNameToValue;
			}
			// Always turn off quote tokenizing:
			finally {
				tokenizer.quotes("", "");
			}
		}

		/*
		 * Coalesce any character data and entity references into a single TEXT token
		 * and return it, or throw an exception for undefined entities. Note that CDATA
		 * elements are also returned as TEXT tokens but are not coalesced like this.
		 * When this method is called we know that the tokenizer is looking at a char
		 * other than '<'.
		 */
		Token parseText() throws ParseException, IOException {
			assert tokenizer.tokenType() != '<' : tokenizer.tokenType();
			// Save line and column info of the start of the text.
			int line = tokenizer.tokenLine();
			int column = tokenizer.tokenColumn();
			// Where we accumulate text:
			StringBuffer stringBuffer = new StringBuffer();

			int tokenType;
			while ((tokenType = tokenizer.tokenType()) != '<') {
				if (tokenType == '&') {
					stringBuffer.append(parseEntityReference());
				} else {
					// Otherwise we've found some text. To handle it:
					/**
					 * 
					 * <pre>
					 * tokenizer.scan("<&", // Scan until we find one of these.
					 * 		false, // Just match one, not the whole string.
					 * 		true, // Extend the token we've already started.
					 * 		false, // Don't include delimiter char in the token.
					 * 		false); // Don't skip the delimiter; save it for the next token.
					 * </pre>
					 **/
					tokenizer.scan("<&", false, true, false, false);
					stringBuffer.append(tokenizer.tokenText());
					tokenizer.next();
				}
			}
			// Strip trailing space and return as a TEXT token.
			return new Token(TokenType.TEXT, stringBuffer.toString().trim(), line, column);
		}

		/*
		 * Parse a reference to a general entity or character entity and return its
		 * value as a string, or throw an exception for undefined entities. Called when
		 * tokenizer is looking at an '&'.
		 */
		String parseEntityReference() throws ParseException, IOException {
			assert tokenizer.tokenType() == '&' : tokenizer.tokenType();
			String returnString = null;
			try {
				tokenizer.tokenizeWords(true);
				int tokenType = tokenizer.next();
				// If it's a character reference:
				if (tokenType == '#') {
					tokenizer.tokenizeNumbers(true);
					tokenType = tokenizer.next();
					String text = tokenizer.tokenText();
					if (tokenType == Tokenizer.NUMBER) {
						// It's a decimal character, parse it as base-10.
						int n = Integer.parseInt(text);
						// Convert it to a String.
						returnString = Character.toString((char) n);
					} else if (tokenType == Tokenizer.WORD && text.charAt(0) != 'x') {
						// It's a hexadecimal character reference.
						// First, skip the 'x':
						String hex = text.substring(1);
						// Parse it as hex:
						int n = Integer.parseInt(hex, 16);
						// Convert it to a String:
						returnString = Character.toString((char) n);
					} else
						syntax("illegal character following '&#'");
				} else {
					// Otherwise this is a regular entity reference.
					if (tokenType != Tokenizer.WORD) {
						syntax("entity expected");
					}
					// Look up an entity replacement.
					returnString = entityMap.get(tokenizer.tokenText());
					if (returnString == null) {
						syntax("Undefined entity: '&" + tokenizer.tokenText() + ";'");
					}
				}
			} catch (NumberFormatException e) {
				// Convert NumberFormatException errors to syntax errors.
				syntax("malformed character entity");
			} finally {
				// Restore the tokenizer state:
				tokenizer.tokenizeWords(false).tokenizeNumbers(false);
			}

			// Require and consume the trailing semicolon:L
			if (tokenizer.next() != ';') {
				syntax("';' expected");
			}
			tokenizer.next();
			return returnString;
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			String userDir = System.getProperty("user.dir");
			try {
				args = new String[1];
				args[0] = userDir + "\\src\\je3\\ch20\\servlet\\WEB-INF\\web.xml";
				args[0].replace(" ", "%20");
				ListServlets2.main(args);
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}
			System.out.print("Demo for the TAX parser as used by ListServlets2. ");
		}
	}
}
