<%@ page import="View.LoginBean" %><%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 13/09/16
  Time: 12:36
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
    <title>Flux Search</title>
    <jsp:include page="htmlParts/cssHead.html" />
</head>
<body background="http://img.wallpaperfolder.com/f/56B26A768C3C/space-nebula-blue-and-red.jpg">
<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <a href="Home.jsp" class="brand-logo center yellow-text">InterStellar</a>
            <ul id="nav-mobile" class="left hide-on-med-and-down">
                <li><a href="galaxySearch.jsp"><i class="small material-icons left">place</i>Galaxies</a></li>
                <li><a href="ImportCSVFile.jsp"><i class="small material-icons left">attach_file</i>Import File</a></li>
                <li class="active"><a href=""><i class="small material-icons left">insert_chart</i>Statistics</a></li>
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
            <div class="collapsible-header"><i class="material-icons">filter_drama</i>Search Line Flux By Galaxy</div>
            <div class="collapsible-body">

                <p class="white-text">Search line flux by galaxy name. For each galaxy selected spectral lines fluxes with relative errors and upper-limit. </p>
                <a href="fluxNameSearch.jsp" class="waves-effect waves-light btn yellow right">Search</a>
                <br />
                <br />
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">place</i>Flux Ratio</div>
            <div class="collapsible-body">
                <p class="white-text">Given a specific galaxy, show the ratio between 2 selected fluxes.Also shows if the result is an upper-limit or a lower-limit </p>
                <a href="fluxRatioSearch.jsp" class="waves-effect waves-light btn green right">Search</a>
                <br />
                <br />
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">whatshot</i>Flux Ratio Statistic</div>
            <div class="collapsible-body">
                <p class="white-text">For every spectral group, you can get the average, standard deviation, median and mean absolute deviation of the ratio between 2 selected fluxes and optionally an aperture size. </p>
                <a href="fluxRatioStatistics.jsp" class="waves-effect waves-light btn red right">Search</a>
                <br />
                <br />
            </div>
        </li>
        <li>
            <div class="collapsible-header"><i class="material-icons">place</i>Line on Continuous Flux Ratio</div>
            <div class="collapsible-body">
                <p class="white-text">Given a specific galaxy, show the ratio between a selected line flux and its continuous value. </p>
                <a href="fluxRatioContinuous.jsp" class="waves-effect waves-light btn green right">Search</a>
                <br />
                <br />
            </div>
        </li>
    </ul>
</div>


<jsp:include page="htmlParts/scriptInclude.html" />

</body>
</html>
