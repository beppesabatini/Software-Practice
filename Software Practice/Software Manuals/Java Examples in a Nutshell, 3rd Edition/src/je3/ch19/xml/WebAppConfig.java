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

// For reading the input file:
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

// JAXP classes for parsing:
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

// For transforming a DOM tree to an XML file:
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

// W3C DOM classes for traversing the document:
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

// SAX classes used for error handling by JAXP:
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 554-556. A WebAppConfig
 * object is a wrapper around a DOM tree for a web.xml file. The methods of the
 * class use the DOM API to work with the tree in various ways.
 * <p/>
 * Usage: WebAppConfig web.xml org.w3c.dom.Document
 * <p/>
 * The one-click Demo method will work here together with a checked-in Run
 * Configuration ("WebAppConfig"). To make the Run Configuration work, you will
 * need to define Configuration variables describing your local file system.
 */
public class WebAppConfig {
	/** The main method creates and demonstrates a WebAppConfig object. */
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException,
			TransformerConfigurationException, TransformerException {
		/*
		 * Create a new WebAppConfig object that represents the web.xml file specified
		 * by the first command-line argument.
		 */
		String webXmlFilepath = args[0];
		webXmlFilepath = webXmlFilepath.replace("%20", " ");
		WebAppConfig config = new WebAppConfig(new File(webXmlFilepath));
		/*
		 * Query the tree for the class name associated with the specified servlet name.
		 */
		System.out.println("Class for servlet " + args[1] + " is " + config.getServletClass(args[1]));
		// ...and, write out an XML version of the DOM tree to standard out.
		config.output(new PrintWriter(System.out));
	}

	// This field holds the parsed DOM tree.
	private Document document;

	/**
	 * This constructor method is passed an XML file. It uses the JAXP API to obtain
	 * a DOM parser, and to parse the file into a DOM Document object, which is used
	 * by the remaining methods of the class.
	 */
	public WebAppConfig(File configfile) throws IOException, SAXException, ParserConfigurationException {
		// Get a JAXP parser factory object:
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// Tell the factory what kind of parser we want:
		dbf.setValidating(false);
		// Use the factory to get a JAXP parser object:
		DocumentBuilder parser = dbf.newDocumentBuilder();

		/*
		 * Tell the parser how to handle errors. Note that in the JAXP API, DOM parsers
		 * rely on the SAX API for error handling
		 */
		parser.setErrorHandler(new ErrorHandler() {
			@Override
			public void warning(SAXParseException saxParseException) {
				System.err.println("WARNING: " + saxParseException.getMessage());
			}

			@Override
			public void error(SAXParseException saxParseException) {
				System.err.println("ERROR: " + saxParseException.getMessage());
			}

			@Override
			public void fatalError(SAXParseException saxParseException) throws SAXException {
				System.err.println("FATAL: " + saxParseException.getMessage());
				// Re-throw the error:
				throw saxParseException;
			}
		});

		/*
		 * Finally, use the JAXP parser to parse the file. This call returns A Document
		 * object. Now that we have this object, the rest of this class uses the DOM API
		 * to work with it; JAXP is no longer required.
		 */
		document = parser.parse(configfile);
	}

	/**
	 * This method looks for specific Element nodes in the DOM tree in order to
	 * figure out the class name associated with the specified servlet name.
	 */
	public String getServletClass(String servletName) {
		// Find all <servlet> elements and loop through them.
		NodeList servletNodes = document.getElementsByTagName("servlet");
		int numberServlets = servletNodes.getLength();
		for (int i = 0; i < numberServlets; i++) {
			Element servletTag = (Element) servletNodes.item(i);
			// Get the first <servlet-name> tag within the <servlet> tag:
			Element nameTag = (Element) servletTag.getElementsByTagName("servlet-name").item(0);
			if (nameTag == null) {
				continue;
			}

			/*
			 * The <servlet-name> tag should have a single child of type Text. Get that
			 * child, and extract its text. Use trim() to strip whitespace from the
			 * beginning and end of it.
			 */
			String name = ((Text) nameTag.getFirstChild()).getData().trim();

			// If this <servlet-name> tag has the right name:
			if (servletName.equals(name)) {
				// Get the matching <servlet-class> tag:
				Element classTag = (Element) servletTag.getElementsByTagName("servlet-class").item(0);
				if (classTag != null) {
					// Extract the tag's text as above, and return it.
					Text classTagContent = (Text) classTag.getFirstChild();
					return classTagContent.getNodeValue().trim();
				}
			}
		}

		// If we get here, no matching servlet name was found.
		return null;
	}

	/**
	 * This method adds a new name-to-class mapping in in the form of a &lt;servlet>
	 * sub-tree to the document.
	 */
	public void addServlet(String servletName, String className) {
		// Create the <servlet> tag:
		Element newNode = document.createElement("servlet");
		// Create the <servlet-name> and <servlet-class> tags:
		Element nameNode = document.createElement("servlet-name");
		Element classNode = document.createElement("servlet-class");
		// Add the name and class name text to those tags:
		nameNode.appendChild(document.createTextNode(servletName));
		classNode.appendChild(document.createTextNode(className));
		// ...and, add those tags to the servlet tag.
		newNode.appendChild(nameNode);
		newNode.appendChild(classNode);

		/*
		 * Now that we've created the new sub-tree, figure out where to put it. This
		 * code looks for another servlet tag and inserts the new one right before it.
		 * Note that this code will fail if the document does not already contain at
		 * least one &lt;servlet> tag.
		 */
		NodeList servletnodes = document.getElementsByTagName("servlet");
		Element firstServlet = (Element) servletnodes.item(0);

		// Insert the new node before the first servlet node.
		firstServlet.getParentNode().insertBefore(newNode, firstServlet);
	}

	/**
	 * Output the DOM tree to the specified stream as an XML document. See the
	 * XMLDocumentWriter example for the details.
	 */
	public void output(PrintWriter printWriter) throws TransformerConfigurationException, TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();

		transformer.transform(new DOMSource(document), new StreamResult(printWriter));
	}
}
