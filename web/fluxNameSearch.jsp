<%@ page import="View.LoginBean" %>
<%@ page import="View.FluxQueryBean" %>
<%@ page import="Model.Atom" %>
<%@ page import="java.util.List" %>
<%@ page import="View.FluxResultBean" %>
<%@ page import="Controller.CSVController" %>
<%@ page import="java.io.File" %><%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 13/09/16
  Time: 12:48
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

    FluxQueryBean queryBean = null;
    List<FluxResultBean> result = null;
    String operation = request.getParameter("operation");
    if(operation != null){
        queryBean = new FluxQueryBean();
        queryBean.setOperation(operation);
        queryBean.setGalaxyName(request.getParameter("galaxyName"));

        // set line fluxes requested in Bean
        for(Atom a : Atom.values()){
            String atomRequeste = request.getParameter(a.toString());
            if(atomRequeste != null){
                if(atomRequeste.contains("on")){
                    queryBean.addAtom(a);
                }
            }
        }

        result = queryBean.performQuery();

    }
%>
<html>
<head>
    <title>Search Line Flux By Galaxy</title>
    <jsp:include page="htmlParts/cssHead.html" />
</head>
<body background="http://img.wallpaperfolder.com/f/56B26A768C3C/space-nebula-blue-and-red.jpg">
<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <a href="Home.jsp" class="brand-logo center yellow-text">InterStellar</a>
            <ul id="nav-mobile" class="left hide-on-med-and-down">
                <li ><a href="galaxySearch.jsp"><i class="small material-icons left">place</i>Galaxies</a></li>
                <li><a href="ImportCSVFile.jsp"><i class="small material-icons left">attach_file</i>Import File</a></li>
                <li class="active"><a href="fluxSearch.jsp"><i class="small material-icons left">insert_chart</i>Statistics</a></li>
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
        <h5 class="white-text">Search a line flux given a specific galaxy</h5>
        <br />
    </blockquote>
    <br />


    <div class="row">
        <form action="fluxNameSearch.jsp" class="col s6 white-text offset-s3" method="post">
            <div class="row">
            <div class="input-field">
                <i class="material-icons prefix">cloud_queue</i>
                <input id="name" name="galaxyName" type="text" class="validate" required>
                <label for="name">Galaxy Name</label>
            </div>
            </div>


                <div class="input-field">
                    <input type="checkbox" id="NeV14" name="NeV14" />
                    <label for="NeV14" class="white-text">NeV 14.3</label>
                </div>
                <div class="input-field">
                    <input type="checkbox" id="NeV24" name="NeV24" />
                    <label for="NeV24" class="white-text">NeV 24.3</label>
                </div>
            <div class="input-field">
                <input type="checkbox" id="OIV25" name="OIV25" />
                <label for="OIV25" class="white-text">OIV 25.9</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="OIII52" name="OIII52" />
                <label for="OIII52" class="white-text">OIII 52</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="NIII57" name="NIII57" />
                <label for="NIII57" class="white-text">NIII 57</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="OI63" name="OI63" />
                <label for="OI63" class="white-text">OI 63</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="OIII88" name="OIII88" />
                <label for="OIII88" class="white-text">OIII 88</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="NII122" name="NII122" />
                <label for="NII122" class="white-text">NII 122</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="OI145" name="OI145" />
                <label for="OI145" class="white-text">OI 145</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="CII158" name="CII158" />
                <label for="CII158" class="white-text">CII 158</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="SIV10" name="SIV10" />
                <label for="SIV10" class="white-text">SIV 10.5</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="NeII12" name="NeII12" />
                <label for="NeII12" class="white-text">NeII 12.8</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="NeIII15" name="NeIII15" />
                <label for="NeIII15" class="white-text">NeIII 15.6</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="SIII18" name="SIII18" />
                <label for="SIII18" class="white-text">SIII 18.7</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="SIII33" name="SIII33" />
                <label for="SIII33" class="white-text">SIII 33.5</label>
            </div>
            <div class="input-field">
                <input type="checkbox" id="SII34" name="SII34" />
                <label for="SII34" class="white-text">SII 34.8</label>
            </div>

            <button class="btn-floating btn-large green waves-effect waves-light right tooltipped"
                    data-position="left" data-delay="50" data-tooltip="Search" type="submit" name="operation" value="NAME" id="searchButton">
                <i class="material-icons right">send</i>
            </button>
        </form>
    </div>

    <%
        if(result != null){
            %>
    <ul class="collapsible popout col l6 offset-l3" data-collapsible="accordion">
    <%
            int i = 1;
            for(FluxResultBean bean : result){
    %>
        <li>
            <div class="collapsible-header green-text"><i class="material-icons">filter_drama</i> <b><%=i + ". " + bean.getGalaxyName() + " flux " + bean.getAtom()%></b></div>
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
                        <td> Galaxy Name </td>
                        <td> <%= bean.getGalaxyName()%></td>
                    </tr>
                    <tr>
                        <td> Flux Type</td>
                        <td> LINE</td>
                    </tr>
                    <tr>
                        <td> Flux Atom </td>
                        <td> <%= bean.getAtom()%></td>
                    </tr>
                    <tr>
                        <td> Flux Value </td>
                        <td> <% if(bean.getFluxValue() == -1){
                            out.print("NOT DEFINED");
                        } else {
                            out.print(bean.getFluxValue());
                        }%></td>
                    </tr>
                    <tr>
                        <td> Flux Error </td>
                        <td> <% if(bean.getError() == -1f){
                            out.print("NOT DEFINED");
                        } else {
                            out.print(bean.getError());
                        }%></td>
                    </tr>
                    <tr>
                        <td> Upper Limit (Yes / No)</td>
                        <td> <% if(bean.isUpperLimit()){
                            out.print("Yes");
                        } else {
                            out.print("No");
                        }%></td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </li>
    <%
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
    if(result != null){
        if(result.size() == 0){
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
