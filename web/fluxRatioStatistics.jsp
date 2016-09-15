<%@ page import="View.LoginBean" %>
<%@ page import="View.FluxQueryBean" %>
<%@ page import="Model.Atom" %>
<%@ page import="View.FluxResultBean" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 14/09/16
  Time: 10:59
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
        String stat = request.getParameter("statistics");
        queryBean = new FluxQueryBean();
        queryBean.setOperation(stat);
        queryBean.setAperture(request.getParameter("aperture"));
        queryBean.setCategory(request.getParameter("category"));

        System.out.println(queryBean.getOperation());


        String num = request.getParameter("num");
        String den = request.getParameter("den");

        // set line fluxes requested in Bean
        for(Atom a : Atom.values()){
            if(a.toString().contains(num)){
                queryBean.setFluxNum(a);
            } else if(a.toString().contains(den)){
                queryBean.setFluxDen(a);
            }

        }

        result = queryBean.performQuery();

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
        <h5 class="white-text">Get statistic operations (average,median,standard deviation and absolute standard deviation) of flux ratio in a specific spectral category.</h5>
        <br />
    </blockquote>
    <br />

    <div class="row">
        <form action="fluxRatioStatistics.jsp" class="col s6 white-text offset-s3" method="post">
            <div class="row">
                <div class="input-field">
                    <i class="material-icons prefix">cloud_queue</i>
                    <input id="name" name="category" type="text" class="validate" required>
                    <label for="name">Spectral Category</label>
                </div>
            </div>

            <h6 class="white-text">Statistics Operation</h6>

            <div class="row">
                <input class="with-gap" name="statistics" value="AVG" type="radio" id="AVG"  checked/>
                <label for="AVG" class="white-text">Average</label>
                <input class="with-gap" name="statistics" value="MEDIAN" type="radio" id="MEDIAN"  />
                <label for="MEDIAN" class="white-text">Median</label>
                <input class="with-gap" name="statistics" value="STD_DEV" type="radio" id="STD_DEV"  />
                <label for="STD_DEV" class="white-text">Standard Deviation</label>
                <input class="with-gap" name="statistics" value="MED_ABS_DEV" type="radio" id="MED_ABS_DEV"  />
                <label for="MED_ABS_DEV" class="white-text">Median Absolute Deviation</label>
            </div>


            <div class="row">
                <div class="input-field col s6 offset-s3">
                    <select name="num">
                        <option value="OIII52" selected>OIII52</option>
                        <option value="NIII57">NIII57</option>
                        <option value="OI63">OI63</option>
                        <option value="OIII88">OIII88</option>
                        <option value="NII122">NII122</option>
                        <option value="OI145">OI145</option>
                        <option value="CII158">CII158</option>
                        <option value="SIV10">SIV10</option>
                        <option value="NeII12">NeII12</option>
                        <option value="NeIII15">NeIII15</option>
                        <option value="SIII18">SIII18</option>
                        <option value="SIII33">SIII33</option>
                        <option value="SII34">SII34</option>

                    </select>
                    <label>Numerator</label>
                </div>
                <div class="input-field col s6 offset-s3">
                    <select name="den">
                        <option value="OIII52" >OIII52</option>
                        <option value="NIII57" selected>NIII57</option>
                        <option value="OI63">OI63</option>
                        <option value="OIII88">OIII88</option>
                        <option value="NII122">NII122</option>
                        <option value="OI145">OI145</option>
                        <option value="CII158">CII158</option>
                        <option value="SIV10">SIV10</option>
                        <option value="NeII12">NeII12</option>
                        <option value="NeIII15">NeIII15</option>
                        <option value="SIII18">SIII18</option>
                        <option value="SIII33">SIII33</option>
                        <option value="SII34">SII34</option>
                    </select>
                    <label>Denominator</label>
                </div>
            </div>

            <div class="row">
                <div class="input-field col s6 offset-s3">
                    <select name="aperture">
                        <option value="NONE" selected>NONE</option>
                        <option value="LOW">3x3</option>
                        <option value="CENTRAL">Central (c)</option>
                        <option value="HIGH">5x5</option>
                    </select>
                    <label>Aperture (optional)</label>
                </div>
            </div>



            <button class="btn-floating btn-large green waves-effect waves-light right tooltipped"
                    data-position="left" data-delay="50" data-tooltip="Search" type="submit" name="operation" value="STATISTICS" id="searchButton">
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
                if(result.size() == 1 ){
                    // no correct result
                    if(result.get(0).getFluxValue() != 0){



        %>
        <li>
            <div class="collapsible-header green-text"><i class="material-icons">filter_drama</i> <b><%=i + ". Spectral Category : " + bean.getGalaxyName() + " " + bean.getStatisticsOperation() + " on " + bean.getAtom() + " / " + bean.getDenAtom()
                    + " flux ratio with aperture : " + bean.getAperture()%></b></div>
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
                        <td> Spectral Classification </td>
                        <td> <%= bean.getGalaxyName()%></td>
                    </tr>
                    <tr>
                        <td> Statistic Operation </td>
                        <td><%=bean.getStatisticsOperation() %></td>
                    </tr>
                    <tr>
                        <td>Flux Numerator</td>
                        <td><%=bean.getAtom()%></td>
                    </tr>
                    <tr>
                        <td>Flux Denominator</td>
                        <td><%= bean.getDenAtom()%></td>
                    </tr>
                    <tr>
                        <td> Operation Value</td>
                        <td> <%= bean.getFluxValue()%></td>
                    </tr>
                    <tr>
                        <td> Aperture </td>
                        <td><%= bean.getAperture()%></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </li>
        <%
                        }

                    }
                }
            }
        %>
    </ul>
    <br />
    <br />
    <br />

</div>


<jsp:include page="htmlParts/scriptInclude.html" />
<script>
    $(document).ready(function() {
        $('select').material_select();
        $('.collapsible').collapsible({
            accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
        });
    });
</script>
<%
    if(result != null){
        if(result.size() == 0){
%>
<script>
    Materialize.toast("No Result",5000,'rounded red');
</script>
<%
        } else if(result.size() == 1){
            if(result.get(0).getFluxValue() == 0){
                %>

<script>
    Materialize.toast("No Result",5000,'rounded red');
</script>
<%
            }
        }
    }
%>
</body>
</html>
