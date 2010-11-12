<%@ page import="com.mp.domain.*" %>
<%@ page import="com.mp.MenuConstants.*" %>
<html>
<head>
    <title>${menuPlanName}</title>
    <meta name="layout" content="printRecipelayout"/>
    <style type="text/css">
    <g:if test="${printOneRecipePerPage}">
    .break {
        page-break-after: always;
    }
    </g:if>
    body {
        color: #000000;
        background: #ffffff;
        font-family: Arial,Helvetica,sans-serif;
        font-size: 17pt;
    }
    #contectElement ul li ,
    #right-head label {
      font-size: 17px;
    }
    #contectElement ul li#rightLiElements {
      float: right;
    }
    #left-panel {
      width: auto;
    }
    a {
        text-decoration: none;
        color: #0000ff;
    }
    #header{
      height:auto;
      width:auto;
    }
    #content-wrapper{
      width:auto;
      padding-top: 0px;
      padding-bottom: 23px;
    }


    h1 {
      margin: 15px;
    }
      #content-wrapper {
        background: url("");
      }
      #footer {
        background: url(""); 
      }
    </style>
    <g:javascript src="jquery.printElement.min.js"/>
    <rateable:resources/>
    <script type="text/javascript">
        window.onload = print()
    </script>
</head>
<body>
<div><h1>${menuPlanName}</h1></div>
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
                <g:render template="/recipe/showRecipeDetails" model="[recipe: recipe,customServings:customServings,isPrintable:isPrintable,imageSize:640,printRecipe:true]"/>
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