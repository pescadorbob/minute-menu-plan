<%@ page import="com.mp.domain.*" %>
<%@ page import="com.mp.MenuConstants.*" %>
<html>
<head>
    <title>Recipe Book List</title>
    <meta name="layout" content="printRecipelayout"/>
    <style type="text/css">
    <
    g:if

    test
    =
    "
    ${
    printOneRecipePerPage
    }
    "
    >
    .break {
        page-break-after: always;
    }

    </
    g:if
    >
    body {
        color: #000000;
        background: #ffffff;
        font-family: "Times New Roman", Times, serif;
        font-size: 12pt;
    }

    a {
        text-decoration: underline;
        color: #0000ff;
    }
    </style>
    <g:javascript src="jquery.printElement.min.js"/>
    <rateable:resources/>
    <script type="text/javascript">
        window.onload = print()
    </script>
</head>
<body>
<g:each in="${recipes}" var="recipe">
    <div class="clearfix printMe break" id="content-wrapper">
        <div id="left-panel">
            <div id="right-head">
                <div id="leftpart">
                    <label id="recipeNameTst">${recipe?.name}</label>
                    <span id="spanRateable">
                    </span>
                </div>
                <div id="rightpart">
                </div>
            </div>
            <div class="top-shadow"><label></label></div>
            <div class="leftbox clearfix">
                <g:render template="/recipe/showRecipeDetails" model="[recipe: recipe]"/>
            </div>
            <div class="bottom-shadow"><label></label></div>
        </div>
        <div id="right-panel">
        </div>
    </div>
</g:each>
<script type="text/javascript">
    window.onload = preventRatingClickEvent;
    function preventRatingClickEvent() {
        $(".rating a").click(function(e) {
            e.preventDefault()
        })
    }
</script>
</body>
</html> 