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

import java.io.FileReader;
import java.io.IOException;
import je3.ch19.xml.TAX.TokenType;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 558. Parse a web.xml file
 * using the TAX pull parser, and print out the servlet name-to-class and
 * name-to-url mappings.
 */
public class ListServlets2 {
	public static void main(String[] args) throws IOException, TAX.ParseException {
		String inputXMLFile = args[0];
		inputXMLFile = inputXMLFile.replace("%20", " ");

		// Create a TAX.Parser instance to parse the specified file.
		TAX.Parser parser = new TAX.Parser(new FileReader(inputXMLFile));
		/*
		 * By default, the parser returns TAG, TEXT and ENDTAG tokens and skips others.
		 * Configure it to skip ENDTAG tokens, too.
		 */
		parser.ignoreTokens(TokenType.ENDTAG);

		// Now loop through all tokens until the end of the file.
		for (TAX.Token token = parser.next(); token != null; token = parser.next()) {
			// If it is not a tag, we're not interested.
			if (token.getType() != TokenType.TAG) {
				continue;
			}

			// If we found a <servlet> tag:
			if (token.getText().equals("servlet")) {
				// Require the <servlet-name> tag next:
				parser.expect("servlet-name");
				// Require a text token next:
				token = parser.expect(TokenType.TEXT);
				// Get text from the token:
				String name = token.getText();
				// Require the <servlet-class> tag next:
				parser.expect("servlet-class");
				// Require a text token:
				token = parser.expect(TokenType.TEXT);
				// Output the class-name-to-class mapping:
				System.out.println("Servlet " + name + " implemented by " + token.getText());
			} else if (token.getText().equals("servlet-mapping")) {
				// Now we do the same thing for the <servlet-mapping> tags.
				parser.expect("servlet-name");
				String name = parser.expect(TokenType.TEXT).getText();
				parser.expect("url-pattern");
				String mapping = parser.expect(TokenType.TEXT).getText();
				System.out.println("Servlet " + name + " mapped to " + mapping);
			}
		}
	}
}
