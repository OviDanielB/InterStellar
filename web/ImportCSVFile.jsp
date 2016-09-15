<%@ page import="View.LoginBean" %>
<%--
  Created by IntelliJ IDEA.
  User: ovidiudanielbarba
  Date: 26/08/16
  Time: 12:59
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
    <title>Import CSV Data File</title>
    <jsp:include page="htmlParts/cssHead.html" />
    <link rel="stylesheet" type="text/css" href="CSS/importCSVFile.css">
</head>
<body>


<div class="navbar-fixed">
    <nav>
        <div class="nav-wrapper indigo">
            <a href="Home.jsp" class="brand-logo center yellow-text">InterStellar</a>
            <ul id="nav-mobile" class="left hide-on-med-and-down">
                <li><a href="galaxySearch.jsp"><i class="small material-icons left">place</i>Galaxies</a></li>
                <li class="active"><a href="#"><i class="small material-icons left">attach_file</i>Import File</a></li>
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
    <br/>
    <blockquote class="white-text">
        <h4>Import a new file containing updated information.</h4>
    </blockquote>
    <br />
    <br />
    <p class="yellow-text">
        The file must be of <b>CSV</b> type and the name should be the following :
    </p>

    <div class="row">
        <div class="chip green white-text">MRTable3_sample.csv</div>
        <div class="chip green white-text">MRTable4_flux.csv</div>
        <div class="chip green white-text">MRTable6_cont.csv</div>
        <div class="chip green white-text">MRTable8_irs.csv</div>
        <div class="chip green white-text">MRTable11_C_3x3_5x5_flux.csv</div>
    </div>
    <br />



    <form action="UploadServlet" class="col m6 offset-m3" enctype="multipart/form-data" method="post">
        <div class="file-field input-field">
            <div class="btn yellow red-text">
                <span>File</span>
                <input type="file" name="file">
            </div>
            <div class="file-path-wrapper">
                <input class="file-path validate white-text" type="text" name="path" required>
            </div>
        </div>
        <div class="progress blue" id="progress">
            <div class="indeterminate"></div>
        </div>
        <button class="btn-floating btn-large red waves-effect waves-light right tooltipped"
                data-position="left" data-delay="50" data-tooltip="Import File" type="submit" name="fileUpload" id="uploadButton">
            <i class="material-icons right">file_upload</i>
        </button>
    </form>


</div>

<jsp:include page="htmlParts/scriptInclude.html" />
<%
    String upload = (String) request.getAttribute("upload");
    if(upload != null){
        if(upload.equals("OK")){
%>
<script>
    Materialize.toast("File Uploaded Successfully!",'rounded green')
</script>
<%
        } else if(upload.equals("FAILED")){
%>
<script>
    Materialize.toast("File NOT Uploaded Successfully!",'rounded red')
</script>

<%
        }
    }
%>
<script>
    $(document).ready(function () {
        $("#progress").hide();
        $("#uploadButton").click(function () {
            $("#progress").show();
        });
    });
</script>
</body>
</html>
