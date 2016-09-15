<%@ page import="View.LoginBean" %>
<%@ page import="View.GalaxyResultBean" %>
<%@ page import="java.util.List" %>
<%@ page import="View.GalaxyQueryBean" %>
<%@ page import="java.io.File" %>
<%@ page import="Controller.CSVController" %><%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 12/09/16
  Time: 14:23
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

    GalaxyQueryBean queryBean = null;
    List<GalaxyResultBean> resultBeanList = null;

    String operation = request.getParameter("operation");
    if(operation != null){
        queryBean = new GalaxyQueryBean();
        queryBean.setOperation(operation);
        queryBean.setRedshiftOpSign(request.getParameter("opSign"));
        queryBean.setMany(request.getParameter("many"));
        queryBean.setRedshift(request.getParameter("redshift"));

        resultBeanList = queryBean.performQuery();

    }
%>
<html>
<head>
    <title>Galaxy Redshift Search</title>
    <jsp:include page="htmlParts/cssHead.html" />
    <link rel="stylesheet" href="CSS/galaxySearchCSS.css" type="text/css">
</head>
<body background="http://wfiles.brothersoft.com/r/red_swirl-1400x1050.jpg">
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
    <blockquote>
        <br />
        <h5 class="white-text">Search n GALAXIES with <b>redshift</b> value less/equal or greater/equal than a specified value. </h5>
        <br />
    </blockquote>

<div class="row">
    <form action="galaxyRedshiftSearch.jsp" class="white-text col s12" method="post">

        <div class="row">
            <h6 class="yellow-text">Redshift</h6>
        </div>
        <div class="row">
            <div class="input-field col s4">
                <select  name="opSign">
                    <option value="<=" selected> <= (less/equl) </option>
                    <option value=">="> >= (greater/equal) </option>
                </select>
                <label>Operation Sign</label>
            </div>
            <div class="input-field col s4">
                <input id="redshift" type="number" min="0" step="any" class="validate" name="redshift" required>
                <label for="redshift">Redshift Value</label>
            </div>

        </div>


        <h6 class="yellow-text">Number of Galaxies</h6>

        <p class="range-field">
            <input type="range" id="many" name="many" min="0" max="100" />
        </p>

        <button class="btn-floating btn-large green waves-effect waves-light right tooltipped"
                data-position="left" data-delay="50" data-tooltip="Search" type="submit" name="operation" value="REDSHIFT" id="searchButton">
            <i class="material-icons right">send</i>
        </button>
    </form>
</div>

    <%
        if(resultBeanList != null){
            %>
    <blockquote>
        <h6 class="white-text">Query Result : first <%= queryBean.getMany()%> GALAXIES with <b>REDSHIFT</b> <%=queryBean.getRedshiftOpSign() + " " + queryBean.getRedshift() + " ordered by redshift value"%> </h6>
    </blockquote>
    <ul class="collapsible popout col l6 offset-l3" data-collapsible="accordion">
    <%
            int i = 1;
            for(GalaxyResultBean bean : resultBeanList){
    %>
    <li>
        <div class="collapsible-header green-text"><i class="material-icons">whatshot</i> <b><%=i + ". " + bean.getName()%></b></div>
        <div class="collapsible-body white blue-text">
            <table>
                <thead>
                    <tr>
                        <th>Property</th>
                        <th>Value</th>
                    </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Name</td>
                    <td><%= bean.getName()%></td>
                </tr>
                <tr>
                    <td>Redshift</td>
                    <td><%=bean.getRedshift()%></td>
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
</div>

    <jsp:include page="htmlParts/scriptInclude.html" />
    <script>
        $(document).ready(function() {
            $('select').material_select();
        });
    </script>
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
