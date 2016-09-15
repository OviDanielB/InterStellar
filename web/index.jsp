
<%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 10/08/16
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%-- Si dichiara la variabile loginBean e istanzia un oggetto LoginBean --%>
<jsp:useBean id="loginBean" scope="session"
             class="View.LoginBean" />

<%--  Setta automaticamente tutti gli attributi dell'oggetto loginBean --%>
<jsp:setProperty name="loginBean" property="userID" />
<jsp:setProperty name="loginBean" property="password" />


<%
  if (request.getParameter("login") != null) {
    /* if login successful*/
    if (loginBean.validate()) {
      // carry across season user
      session.setAttribute("loginBean",loginBean);

%>
  <!-- redirect to new page is login succeded -->
  <jsp:forward page="Home.jsp"/>
<%
    }
    // if user has requested a logout from anywhere
  } else if(request.getParameter("logout") != null){
      loginBean = null;
    }
%>

<html>
  <head>
    <title>InterStellar Login</title>
    <jsp:include page="htmlParts/cssHead.html"/>
    <link rel="stylesheet" href="CSS/index.css" type="text/css" >
  </head>
  <body>

  <nav>
    <div class="nav-wrapper indigo">
      <a href="#" class="brand-logo center yellow-text">InterStellar</a>
    </div>
  </nav>
  <div class="container">
    <br />
    <br />
    <br />
    <form method="post" action="index.jsp" class="form_padding">


      <h1 class="cyan-text">Login</h1> <br />
      <div class="cyan-text">Username </div>  <input class="white-text"  type="text" name="userID"> <br />
      <div class="cyan-text">Password </div> <input class="white-text" type = "password" name="password"> <br />
      <p>
        <button class="btn yellow right" type="submit" name="login" id="login" value="login">Submit
          <i class="material-icons right">send</i>
        </button>

      </p>
    </form>

  </div>
  <jsp:include page="htmlParts/scriptInclude.html"/>
  <script src="JavaScript/index.js"></script>
  <%
    // wrong username or password
    if(request.getParameter("login") != null){
      %>
    <script>
      Materialize.toast("Wrong Username or Password.Try Again",5000,'rounded red left');
    </script>
  <%
    }
  %>
  </body>
</html>