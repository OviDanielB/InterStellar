<%@ page import="View.LoginBean" %><%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 10/09/16
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LoginBean loginBean;
    if((loginBean = (LoginBean) session.getAttribute("loginBean")) == null){
%>
<jsp:forward page="index.jsp" />
<%
    }
%>
<html>
<head>
    <title>Galaxy Search</title>
    <jsp:include page="htmlParts/cssHead.html" />
</head>
<body background="http://www.thisiscolossal.com/wp-content/uploads/2016/08/DanielKordan_02.jpg">

<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <a href="Home.jsp" class="brand-logo center yellow-text">InterStellar</a>
            <ul id="nav-mobile" class="left hide-on-med-and-down">
                <li class="active"><a href="#"><i class="small material-icons left">place</i>Galaxies</a></li>
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
    <br />
    <br />
    <ul class="collapsible popup" data-collapsible="accordion">
        <li>
            <div class="collapsible-header"><i class="material-icons">filter_drama</i>Search By Name</div>
            <div class="collapsible-body">

                <p class="white-text">Search Galaxy by name. For each galaxy shows position, distance with relative redshift, luminosity and metallicity. </p>
                <a href="galaxyNameSearch.jsp" class="waves-effect waves-light btn yellow right">Search</a>
                <br />
                <br />
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">place</i>Search By Space Position</div>
            <div class="collapsible-body">
                <p class="white-text">Search Galaxies by space position and radius. For each galaxy shows position, distance with relative redshift, luminosity and metallicity. </p>
                <a href="galaxyRangeSearch.jsp" class="waves-effect waves-light btn green right">Search</a>
                <br />
                <br />
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">whatshot</i>Search By Redshift Value</div>
            <div class="collapsible-body">
                <p class="white-text">Search Galaxy by name. For each galaxy shows position, distance with relative redshift, luminosity and metallicity. </p>
                <a   href="galaxyRedshiftSearch.jsp" class="waves-effect waves-light btn red right">Search</a>
                <br />
                <br />
            </div>
        </li>
    </ul>
</div>


<jsp:include page="htmlParts/scriptInclude.html" />
</body>
</html>
