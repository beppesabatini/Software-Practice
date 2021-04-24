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
package je3.ch20.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 575-576. This servlet is
 * the server-side companion to the ErrorHandler.reportThrowable() utility
 * method developed earlier, in chapter 11 in this book. It responds to the HTTP
 * POST request initiated by that method.
 * <p/>
 * None of the examples in the servlet chapter, chapter 20, can be launched with
 * one click or with a Run Configuration. You must have access to a webserver,
 * and in that webserver you must install the WAR file made up of all the files
 * in this chapter. There is a build file to build that WAR file at the root of
 * this project, build.je3.ch20.servlet.war.xml.
 * <p>
 * With all that done, you should be able to send an error message from the
 * chapter 11 program, which the ErrorHandlerServlet in this file will print out
 * to its console and its log.
 */
public class ErrorHandlerServlet extends HttpServlet {

	private static final long serialVersionUID = -8608897010531776776L;

	// This servlet only supports HTTP POST requests.
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Note that ObjectInputStream deserializes by calling getReadObject();
		ObjectInputStream objectInputStream = new ObjectInputStream(request.getInputStream());
		try {
			Throwable throwable = (Throwable) objectInputStream.readObject();

			/**
			 * Exercise: save the throwable to a database, along with the current time, and
			 * the IP address from which it was reported.
			 * <p/>
			 * Our response will be displayed within an HTML document, but it is not a
			 * complete document itself. Declare it plain text, but it is okay to include
			 * HTML tags in it.
			 */
			response.setContentType("text/plain");
			PrintWriter printWriter = response.getWriter();
			/**
			 * The class name is displayed in (the obsolete) fixed-width
			 * <tt>Teletype Text</tt> to make it look more like code.
			 */
			String message = "";
			message += "Thanks for reporting your <tt>\"" + throwable.getClass().getName() + "\"</tt>.";
			message += "<br/>";
			message += "It has been filed and will be investigated.";
			printWriter.println(message);
		} catch (Exception exception) {
			/*
			 * Something went wrong deserializing the object; most likely someone tried to
			 * invoke the servlet manually and didn't provide correct data. We send an HTTP
			 * error because that is the easiest thing to do. Note, however that none of the
			 * HTTP error codes really describes this situation adequately.
			 */
			response.sendError(HttpServletResponse.SC_GONE, "Unable to deserialize throwable object");
		}
	}
}
