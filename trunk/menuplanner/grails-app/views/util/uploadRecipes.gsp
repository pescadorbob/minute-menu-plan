<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Minute Menu Plan</title>
    <meta name="layout" content="menu"/>
</head>
<body>

<div id="content-wrapper" class="clearfix">
    <div id="left-panel">
        <div style="border:1px solid #ddd; padding:30px;">
            Upload Excel File for Populating Recipes:<br/><br/>
            <g:form name="myUpload" controller="util" action="populateRecipes" method="post" enctype="multipart/form-data">
                <strong>
                    <input type="file" name="recipeFile"/>
                </strong>
                <br/><br/>
                <input type="submit" name="submit" value="Submit">
            </g:form>
        </div>
    </div>
</div>
</body>
</html>