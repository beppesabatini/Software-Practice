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

import java.io.File;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 556-557. Transforms an
 * input document to an output document using an XSLT style sheet.
 * <p/>
 * Usage: java XSLTransform input styleSheet output
 * <p/>
 * We have not been able to test this program yet, because we have no access to
 * the input data described in the manual. XSL is actually a much larger subject
 * than can be introduced in a small example such as this one.
 */
public class XSLTransform {
	public static void main(String[] args) throws TransformerException {
		/*
		 * Set up streams for input, style sheet, and output. These do not have to come
		 * from or go to files. We can also use the javax.xml.transform.{dom,sax}
		 * packages, which use DOM trees and streams of SAX events as sources and sinks
		 * for documents and style sheets.
		 */
		StreamSource input = new StreamSource(new File(args[0]));
		StreamSource stylesheet = new StreamSource(new File(args[1]));
		StreamResult output = new StreamResult(new File(args[2]));

		/*
		 * Get a factory object, create a Transformer from it, and transform the input
		 * document to the output document.
		 */
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(stylesheet);
		transformer.transform(input, output);
	}
}
