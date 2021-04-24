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
import java.io.PrintWriter;

// HTTP-specific servlet functionality:
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 573-574. This simple
 * servlet greets the user. It looks in the request and session objects in an
 * attempt to greet the user by name.
 * <p/>
 * None of the examples in the servlet chapter, chapter 20, can be launched with
 * one click or with a Run Configuration. You must have access to a web server,
 * and in that web server you must install the WAR file made up of all the files
 * in this chapter. There is a build file to build that WAR file at the root of
 * this project, build.je3.ch20.servlet.war.xml. When all that is done, there is
 * a URL for the entry point to the WAR file:
 * <p/>
 * http://localhost:8080/FlanaganServlet1.0/
 * <p/>
 * That is a page of links which directly or indirectly test most of the servlet
 * chapter examples.
 */
public class HelloNet extends HttpServlet {

	private static final long serialVersionUID = 3135876248361664709L;

	// This method is invoked when the servlet is the subject of an HTTP GET.
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// See if the username is specified in the request.
		String name = request.getParameter("username");

		/*
		 * If not, look in the session object. The web server or servlet container
		 * performs session tracking automatically for the servlet, and associates a
		 * HttpSession object with each session.
		 */
		if (name == null) {
			name = (String) request.getSession().getAttribute("username");
		}

		// If the username is not found in either place, use a default name.
		if (name == null) {
			name = "World";
		}

		/*
		 * Specify the type of output we produce. If this servlet is included from
		 * within another servlet or JSP page, this setting will be ignored.
		 */
		response.setContentType("text/html");

		// Get a stream that we can write the output to.
		PrintWriter printWriter = response.getWriter();

		// ...and, finally, do our output.
		printWriter.println("Hello " + name + "!");
	}

	/*
	 * This method is invoked when the servlet is the subject of an HTTP POST. It
	 * calls the doGet() method so that this servlet works correctly with either
	 * type of request.
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}
