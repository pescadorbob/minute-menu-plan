<%@ page import="com.mp.domain.*" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Minute Menu Plan</title>
    <meta name="layout" content="menuPlanner"/>
    <script type="text/javascript" src="${resource(dir: 'js', file: 'createRecipe.js')}"></script>
</head>
<body>

<div class="month-menu">
    <div class="add-recipe-rgt1">
        <div id='divResult' style="padding:30px;float:top;">
            <g:if test="${result}">
                <g:render template='result' model='[result:result]'/>
            </g:if>
        </div>
        <div style="border:1px solid #ddd; padding:30px;float:top;">
            Populate recipe from detail provided in Excel File:<br/><br/>
            <g:form name="myUpload" controller="util" action="readXls" method="post" enctype="multipart/form-data">
                <strong>
                    SELECT EXCEL FILE: <input type="file" name="selectXls"/>
                </strong>
                <br/>
                <br/>
                <input type="submit" name="submit" value="Submit">
            </g:form>
        </div>
    </div>
</div>
</body>
</html>