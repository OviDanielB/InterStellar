<%@ page import="View.LoginBean" %>
<%@ page import="View.GalaxyQueryBean" %>
<%@ page import="java.util.List" %>
<%@ page import="View.GalaxyResultBean" %>
<%@ page import="Controller.CSVController" %>
<%@ page import="java.io.File" %><%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 10/09/16
  Time: 14:16
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

    GalaxyQueryBean query = null;
    List<GalaxyResultBean> resultBeanList = null;

    //galaxy by NAME query
    String operation = request.getParameter("operation");
    if( operation != null){
        query = new GalaxyQueryBean();
        query.setOperation(operation);
        query.setGalaxyName(request.getParameter("galaxyName"));

        resultBeanList = query.performQuery();
    }
%>


<html>
<head>
    <title>Galaxy Name Search</title>
    <jsp:include page="htmlParts/cssHead.html" />
    <link rel="stylesheet" href="CSS/galaxySearchCSS.css" type="text/css">
</head>
<body background="http://www.thisiscolossal.com/wp-content/uploads/2016/08/DanielKordan_02.jpg">

<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <a href="Home.jsp" class="brand-logo center yellow-text">InterStellar</a>
            <ul id="nav-mobile" class="left hide-on-med-and-down">
                <li class="active"><a href="galaxySearch.jsp"><i class="small material-icons left">place</i>Galaxies</a></li>
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
        <br />
        <h5 class="white-text">Search a GALAXY by name</h5>
        <br />
    </blockquote>
    <br />

    <div class="row">
        <form action="galaxyNameSearch.jsp" class="col s6 white-text offset-s3" method="post">
            <div class="input-field">
                <i class="material-icons prefix">cloud_queue</i>
                <input id="name" name="galaxyName" type="text" class="validate" required>
                <label for="name">Galaxy Name</label>
            </div>

            <button class="btn-floating btn-large green waves-effect waves-light right tooltipped"
                    data-position="left" data-delay="50" data-tooltip="Search" type="submit" name="operation" value="NAME" id="searchButton">
                <i class="material-icons right">send</i>
            </button>
        </form>
    </div>

    <ul class="collapsible popout col l6 offset-l3" data-collapsible="accordion">
    <%
        int i = 1;
        if(resultBeanList != null){
            for (GalaxyResultBean bean : resultBeanList){
    %>
        <li>
            <div class="collapsible-header green-text"><i class="material-icons">filter_drama</i> <b><%=i + ". " + bean.getName()%></b></div>
            <div class="collapsible-body white blue-text">
                <table>
                    <thead>
                    <tr>
                        <th data-field="property">Property</th>
                        <th data-field="value">Value</th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <td>Name</td>
                        <td><%=bean.getName()%></td>
                    </tr>
                    <tr>
                        <td>Position (HH MM SS, +/- DD MM SS)</td>
                        <td><%="("+bean.getAscH()+" "+bean.getAscM()+" "+bean.getAscS() +" , " + bean.getDecSign()+" "+
                        bean.getDecD() + " " + bean.getDecM() + " " + bean.getDecS() + ")"%></td>
                    </tr>
                    <tr>
                        <td>Distance </td>
                        <td><%
                            if(bean.getDistance() == -1f){
                                out.print("NOT DEFINED");
                            }else {
                                out.print(bean.getDistance());
                            }%></td>
                    </tr>
                    <tr>
                        <td>RedShift</td>
                        <td><%
                            if(bean.getRedshift() == -1f){
                                out.print("NOT DEFINED");
                            }else {
                                out.print(bean.getRedshift());
                            }%></td>
                    </tr>
                    <tr>
                        <td>Luminosity (Average from NeV14,Nev24 and OIV25)</td>
                        <td><%=bean.getLumAvg()%></td>
                    </tr>
                    <tr>
                        <td>Metallicity</td>
                        <td><%
                            if(bean.getMetallicity() == -1f){
                                out.print("NOT DEFINED");
                            }else {
                                out.print(bean.getMetallicity());
                            }%></td>
                    </tr>
                    <tr>
                        <td>Metallicity Error</td>
                        <td><%
                            if(bean.getMetallicityError() == -1f){
                                out.print("NOT DEFINED");
                            }else {
                                out.print(bean.getMetallicityError());
                            }%></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </li>


        <%
                i++;
            }
        }
    %>
    </ul>
    <br />
    <br />
    <br />
</div>
<jsp:include page="htmlParts/scriptInclude.html" />
<%
    if(resultBeanList != null){
        if(resultBeanList.size() == 0){
%>
<script>
    Materialize.toast("No Result",5000,'rounded red');
</script>
<%
        }
    }
%>
</body>
</html>
