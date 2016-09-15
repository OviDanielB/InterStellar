<%@ page import="View.LoginBean" %><%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 16/08/16
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    LoginBean loginBean;

    // checks if user had logged in before, else send to login page
    if( (loginBean = (LoginBean) session.getAttribute("loginBean")) == null){
        %>
            <jsp:forward page="index.jsp" />
        <%
    }
%>
<html>
<head>
    <title>Title</title>
    <jsp:include page="htmlParts/cssHead.html" />

</head>
<body>
<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <a href="#" class="brand-logo center yellow-text">InterStellar</a>
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


<div class="parallax-container">
    <div class="parallax"><img src="http://cdn.wonderfulengineering.com/wp-content/uploads/2014/06/galaxy-wallpapers-10.jpg"></div>

</div>


<div class="section white">
    <div class="row container">
        <h2 class="header">Galaxy Search</h2>
        <p class="grey-text text-darken-3 lighten-3">Search a galaxy by its name and get its position, its distance with the relative redshift,luminosity and metallicity with relative errors.
        The search can be made for galaxies in a certain radius from a position in space.</p>
        <a href="galaxySearch.jsp" class="waves-effect waves-light btn-large green"><i class="material-icons left">place</i>Go To Galaxy Search</a>
    </div>
</div>

<div class="parallax-container">
    <div class="parallax"><img src="https://aos.iacpublishinglabs.com/question/aq/1400px-788px/star-constellation-pisces_7ef64f822dc1659b.jpg?domain=cx.aos.ask.com">
    </div>
</div>

<div class="section white">
    <div class="row container">
        <h2 class="header">Flux Statistics</h2>
        <p class="grey-text text-darken-3 lighten-3">Search a flux related to a specific atom and a specific galaxy and see if it's an upperlimit or not.
        You can also calculate the ratio between two selected fluxes for a specific galaxy. Statistic operations (Average,Median,Standard Deviation and Median Absolute Deviation)
         can be applied to specific flux ratios for a specific spectral category and optionally a specified aperture.</p>
        <a href="fluxSearch.jsp" class="waves-effect waves-light btn-large blue"><i class="material-icons left">place</i>Go To Flux Statistics</a>
    </div>
</div>

<div class="parallax-container">
    <div class="parallax"><img src="http://www.wallpaperawesome.com/wallpapers-awesome/wallpapers-planets-stars-galaxies-nebulae-sci-fi-awesome/wallpaper-yellow-nebula.jpg">
    </div>
</div>

<%
    // admin only features
    if(loginBean.getAdmin()){

%>

<div class="section white">
    <div class="row container">
        <h2 class="header">User Registration (Admin only)</h2>
        <p class="grey-text text-darken-3 lighten-3">Administrators only have the feature to register new users to have access to the application.
        They need user information like full name and email. They must choose a unique userID and password for the new user</p>
        <a class="waves-effect waves-light btn-large pink" href="UserRegistration.jsp"><i class="material-icons left">people</i>Register New User</a>

    </div>
</div>

<div class="parallax-container">
    <div class="parallax"><img src="http://img.wallpaperfolder.com/f/56B26A768C3C/space-nebula-blue-and-red.jpg">
    </div>
</div>

<div class="section white">
    <div class="row container">
        <h2 class="header">Data Import (Admin Only)</h2>
        <p class="grey-text text-darken-3 lighten-3">Import new data in CSV file format. All values will be upgraded with the new ones.The files can contain galaxy information and related measurements like luminosity, metallicity and fluxes (with various apertures).   </p>
        <a class="waves-effect waves-light btn-large yellow" href="ImportCSVFile.jsp"><i class="material-icons left">attach_file</i>Go To File Import</a>
    </div>
</div>

<%
    }
%>

<jsp:include page="htmlParts/scriptInclude.html" />
<script src="JavaScript/home.js"></script>
</body>
</html>
