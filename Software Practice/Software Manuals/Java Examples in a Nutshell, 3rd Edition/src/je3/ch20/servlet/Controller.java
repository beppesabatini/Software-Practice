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
import java.sql.Connection;
//For the db-independent way to open a connection:
//import java.sql.DriverManager; 
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.sqlite.SQLiteConfig;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 591-595. This is the
 * Controller servlet for the ListManager web application. It must be configured
 * to be invoked in response to URLs ending with ".action". When it is invoked
 * this way, it uses the file name with which it is invoked, such as
 * "login.action", "edit.action", etc., to determine what to do. Each supported
 * action name has a corresponding method, which performs the requested action,
 * and returns an appropriate View object, in the form of a RequestDispatcher
 * wrapped around a JSP page. The servlet dispatches to the JSP page, which
 * generates an HTML document to display to the user.
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
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 2778784959400913227L;

	// Database connection:
	Connection connection;
	// Factory for managing User objects:
	UserFactory userFactory;

	/**
	 * This method is called when the servlet is first created. It reads its
	 * initialization parameters and uses them to connect to a database. It uses the
	 * database connection to create a UserFactory object.
	 */
	@Override
	public void init() throws ServletException {
		// Read initialization parameters from the web.xml deployment file.
		ServletConfig configuration = getServletConfig();
		String jdbcDriver = configuration.getInitParameter("jdbcDriver");
		String jdbcURL = configuration.getInitParameter("jdbcURL");
		// SQLite isn't looking at username and password for now.
		// String jdbcUser = configuration.getInitParameter("jdbcUser");
		// String jdbcPassword = configuration.getInitParameter("jdbcPassword");
		String tableName = configuration.getInitParameter("tableName");

		// Use those parameters to connect to the database.
		try {
			/*
			 * Load the driver class. It registers itself; we don't need to retain the
			 * returned Class.
			 */
			Class.forName(jdbcDriver);

			/*
			 * Connect to database. If the database server ever crashes, this Connection
			 * object will become invalid. The servlet will crash too, even if the database
			 * server has come back up.
			 */
			// This is the DB-independent way to connect:
			// connection = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPassword);
			// Obviously this is specific to SQLite:
			SQLiteConfig sqLiteConfig = new SQLiteConfig();
			connection = sqLiteConfig.createConnection(jdbcURL);

			// Use the DB connection to instantiate a UserFactory object.
			userFactory = new UserFactory(connection, tableName);
		} catch (Exception exception) {
			log("Can't connect to database", exception);
			throw new ServletException("Can't connect to database", exception);
		}

		/*
		 * Save an initialization parameter where our JSP pages can find it. They need
		 * this so they can display the name of the mailing list.
		 */
		ServletContext context = configuration.getServletContext();
		context.setAttribute("listname", configuration.getInitParameter("listname"));
	}

	/**
	 * If the servlet is destroyed, we need to release the database connection.
	 */
	@Override
	public void destroy() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace(System.err);
		}
	}

	/* Handle POST requests as if they were GET requests. */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
		doGet(request, resp);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		/*
		 * Look up the information we need to dispatch this request. We need to know the
		 * name under which we were invoked, and whether there is already a User object
		 * in the session.
		 */
		String name = request.getServletPath();
		User user = (User) request.getSession(true).getAttribute("user");

		// This will hold the page to which we dispatch for the response.
		RequestDispatcher nextPage;

		/*
		 * If no user is defined yet, go to the login page. Otherwise, dispatch to one
		 * of the methods below, based on the name by which we were invoked (see web.xml
		 * for the mapping). The page to display is the return value of the method to
		 * which we dispatch.
		 */
		try {
			if (name.endsWith("/login.action")) {
				nextPage = login(request, response);
			} else if (user == null) {
				nextPage = request.getRequestDispatcher("login.jsp");
			} else if (name.endsWith("/edit.action")) {
				nextPage = edit(request, response);
			} else if (name.endsWith("/unsubscribe.action")) {
				nextPage = unsubscribe(request, response);
			} else if (name.endsWith("/logout.action")) {
				nextPage = logout(request, response);
			} else {
				/*
				 * If we don't recognize the name under which we were invoked, send a HTTP 404
				 * error.
				 */
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found");
				return;
			}
		} catch (SQLException sqlException) {
			/*
			 * If anything goes wrong while processing the action, output an error page.
			 * This demonstrates how a servlet can produce its own output, instead of
			 * forwarding to a JSP page. We also use resp.sendError() here to send an error
			 * code.
			 */
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("<h1>Error</h1>");
			out.print("An unexpected error has occurred.<pre>");
			out.print(sqlException);
			out.print("</pre>Please contact the webmaster.");
			return;
		}
		/*
		 * Now send the nextPage as the response to the client. See the
		 * RequestDispatcher class.
		 */
		nextPage.forward(request, response);
	}

	/*
	 * This method handles "/login.action", which is either a request to subscribe a
	 * new user, or a request to log in an existing subscriber. A form that links to
	 * "/login.action" must define a parameter named "email" and a parameter named
	 * "password". If this is a subscription request for a new user the parameter
	 * "subscribe" must also be defined.
	 */
	RequestDispatcher login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// This action can dispatch to one of two pages.
		RequestDispatcher loginPage = request.getRequestDispatcher("login.jsp");
		RequestDispatcher editPage = request.getRequestDispatcher("edit.jsp");

		// Get parameters from the request.
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Make sure the e-mail address is not the empty string!
		if (email.length() == 0) {
			request.setAttribute("loginMessage", "You must specify an e-mail address");
			return loginPage;
		}

		/*
		 * Now try to subscribe or login. If all goes well, we'll end up with a User
		 * object.
		 */
		User user = null;
		try {
			/*
			 * This action is either for subscribing a new user, or for logging in an
			 * existing user. It depends on which submit button was pressed.
			 */
			if (request.getParameter("subscribe") != null) {
				// A new subscription:
				user = userFactory.insert(email, password);
			} else {
				// A login:
				user = userFactory.select(email, password);
			}
		}
		/*
		 * If anything goes wrong, we send the user back to the login page with an error
		 * message.
		 */
		catch (UserFactory.NoSuchUser e) {
			request.setAttribute("loginMessage", "Unknown e-mail address.");
			return loginPage;
		} catch (UserFactory.BadPassword e) {
			request.setAttribute("loginMessage", "Incorrect Password");
			return loginPage;
		} catch (UserFactory.UserAlreadyExists e) {
			request.setAttribute("loginMessage", email + " is already subscribed");
			return loginPage;
		}

		/*
		 * If we got here, the user is subscribed or logged in. Store the User object in
		 * the current session and move on to the edit page.
		 */
		HttpSession session = request.getSession(true);
		session.setAttribute("user", user);
		return editPage;
	}

	/*
	 * This method handles the URL "/edit.action". A form that links to this URL
	 * must define the parameters "html" and "digest," if the user wants HTML
	 * messages or digests.
	 */
	RequestDispatcher edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// Update the user's email delivery preferences based on request parameters.
		User user = (User) request.getSession().getAttribute("user");
		user.setPrefersHTML(request.getParameter("html") != null);
		user.setPrefersDigests(request.getParameter("digest") != null);
		// Ask the factory to save the new preferences to the database.
		userFactory.update(user);
		// ...and, re-display the edit page.
		return request.getRequestDispatcher("edit.jsp");
	}

	/*
	 * This method handles the URL "/unsubscribe.action". No parameters are
	 * necessary for this action.
	 */
	RequestDispatcher unsubscribe(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		// Get the User object from the session.
		User user = (User) request.getSession().getAttribute("user");
		// Note the e-mail address before destroying it.
		String email = user.getEmailAddress();
		// Delete the user from the database.
		userFactory.delete(user);
		// Terminate the session. First, log out:
		request.getSession().invalidate();
		// Now, display the login page again, with a "unsubscribed" message.
		request.setAttribute("loginMessage", email + " unsubscribed and logged out.");
		return request.getRequestDispatcher("login.jsp");
	}

	/*
	 * This method handles the URL "/logout.action". No parameters are necessary for
	 * this action.
	 */
	RequestDispatcher logout(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {
		/*
		 * Destroy the session object, and return to the login page with a "logged out"
		 * message for confirmation.
		 */
		User user = (User) request.getSession().getAttribute("user");
		request.setAttribute("loginMessage", user.getEmailAddress() + " logged out");
		// Delete the session:
		request.getSession().invalidate();
		return request.getRequestDispatcher("login.jsp");
	}
}
