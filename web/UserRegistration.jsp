<%@ page import="View.LoginBean" %>
<%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 24/08/16
  Time: 20:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    LoginBean loginBean ;
    boolean registrationDone = false;

    if( (loginBean = (LoginBean) session.getAttribute("loginBean")) == null ){
%>
    <jsp:forward page="index.jsp" />
<%
    }
        if(request.getParameter("register") != null){
%>
            <jsp:useBean id="registrationBean" class="View.UserRegistrationBean" scope="request" />
            <jsp:setProperty name="registrationBean" property="*" />
<%

            if(registrationBean.validate())
                registrationDone = true;
        }

%>
<html>
<head>
    <title>InterStellar : User Registration (Admin only)</title>
    <jsp:include page="htmlParts/cssHead.html"/>
    <link rel="stylesheet" type="text/css" href="CSS/userRegistration.css">
</head>
<body>
<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <a href="Home.jsp" class="brand-logo center yellow-text">InterStellar</a>
            <ul id="nav-mobile" class="left hide-on-med-and-down">
                <li><a href="galaxySearch.jsp"><i class="small material-icons left">place</i>Galaxies</a></li>
                <li><a href="ImportCSVFile.jsp"><i class="small material-icons left">attach_file</i>Import File</a></li>
                <li><a href="fluxSearch.jsp"><i class="small material-icons left">insert_chart</i>Statistics</a></li>
            </ul>
            <ul class="right hide-on-med-and-down">

                <li id="accBtn"><a href="#"><i class="small material-icons left">perm_identity</i> <%= loginBean.getUserID()%> </a> </li>
                <li>
                    <form action="index.jsp">

                        <button class="btn-floating btn-large red center" type="submit" name="logout" id="logout" value="logout">
                            <i class="tiny material-icons center">lock_outline</i>
                        </button>

                    </form>
                </li>
            </ul>
        </div>
    </nav>
</div>

<div class="container">
    <br />
    <blockquote>
        <h3 class="white-text">Register a new User</h3>
    </blockquote>
    <br />

    <div class="row">
        <form class="col s6 white-text offset-s3" action="UserRegistration.jsp">
            <div class="row">
                <div class="input-field col s6">
                    <i class="material-icons prefix">account_circle</i>
                    <input id="firstName" name="firstName" type="text" class="validate" required>
                    <label for="firstName">First Name</label>
                </div>
                <div class="input-field col s6">
                    <i class="material-icons prefix">account_circle</i>
                    <input id="lastName" name="lastName" type="text" class="validate" required>
                    <label for="lastName">Last Name</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <i class="material-icons prefix">person_add</i>
                    <input  id="userID" name="userID" type="text" class="validate" required>
                    <label for="userID">UserID</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <i class="material-icons prefix">visibility_off</i>
                    <input id="password" name="password" type="password" class="validate" required>
                    <label for="password">Password</label>
                </div>
            </div>
            <div class="row">
                <div class="input-field col s12">
                    <i class="material-icons prefix">mail_outline</i>
                    <input id="email" name="email" type="email" class="validate" required>
                    <label for="email">Email</label>
                </div>
            </div>
            <button class="btn-floating btn-large red waves-effect waves-light right tooltipped"
                    data-position="left" data-delay="50" data-tooltip="Add User" type="submit" name="register" value="register" >
                <i class="material-icons right">add</i>
            </button>
        </form>
    </div>

</div>

<jsp:include page="htmlParts/scriptInclude.html" />
<%
    // the user has completed the form and submitted
    if(request.getParameter("register") != null){

        if(registrationDone){
            %>
            <script >
                var $toastContent = $('<span>User registred correctly!</span>');
                Materialize.toast($toastContent, 5000);
            </script>
<%
        }else {
            %>

<script >
    var $toastContent = $('<span>User NOT registred correctly!</span>');
    Materialize.toast($toastContent, 5000);
</script>
<%
        }
    }
%>
</body>
</html>
