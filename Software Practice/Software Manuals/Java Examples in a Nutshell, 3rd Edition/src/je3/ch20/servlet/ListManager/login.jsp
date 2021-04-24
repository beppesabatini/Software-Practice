<%-- ------------------------------------------------------------------------
  - From Java Examples in a Nutshell, 3rd Edition, pp. 596-597. 

  - login.jsp: Displays a login form for the ListManager web application. 
  - 
  - Inputs: The page expects the name of the mailing list in
  - ${applicationScope.listname} (the "listname" attribute of ServletContext).
  - it also looks for an optional error or status message in
  - ${requestScope.loginMessage} (the "loginMessage" attribute of the
  - ServletRequest
  - 
  - Outputs: When submitted, the form POSTs to "login.action", and defines
  - parameters named "email" and "password". If the user submits with the
  - Subscribe button it defines a "subscribe" parameter. Otherwise, if the
  - user submits with the Login button, it defines a "login" parameter.
  ----------------------------------------------------------------------  --%>
<%-- Custom tag directory in the war file:--%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
  <head>
    <title>ListManager: Login</title>
  </head>
  <body>
    <div align="center">
      <%-- Get list title from ServletContext attribute --%>
      <h1>List Manager: ${applicationScope.listname}</h1>

      <%-- Use the custom "box" tag to highlight the login form --%>
      <tags:box title="Login" bgcolor="#ccf" border="3" margin="70" padding="20">
        <%-- Get the login message from a request attribute, and highlight it with a tag. --%>
        <tags:attention>${requestScope.loginMessage}</tags:attention>
        <%-- The rest of the file is standard HTML. --%>
        <p/>
        Please enter an e-mail address and password to subscribe to this
        mailing list, or to log in and change your delivery preferences.
        <form action='login.action' method="post">
          <table>
            <%-- First row: email address --%>
            <tr> 
              <td align='right'>Email-address:</td>
              <td>
                <input name='email'>
              </td>
            </tr>
            <%-- Second row: password --%>
            <tr>
              <td align='right'>Password:</td>
              <td>
                <input type='password' name='password'>
              </td>
            </tr>
            <%-- Third row: buttons --%>
            <tr>
              <td align='center' colspan=2>
                <input type=submit name="subscribe" value='Subscribe' />
                <input type=submit name="login" value='Login' />
              </td>
            </tr>
          </table>
        </form>
        <%-- Demonstrate the <jsp:include> tag, by including servlet output on this page page --%>
        <%-- The Counter servlet maintains a HashTable with number-of-views per page. --%>
        <%-- This call tells Counter to look up the number of visits for login.jsp, the current page. --%>
        This page has been accessed
        <jsp:include page="/Counter">
          <jsp:param name="counter" value="login.jsp"/>
        </jsp:include> times.
      </tags:box>
    </div>
  </body>
</html>
