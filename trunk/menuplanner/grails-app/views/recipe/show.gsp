<%@ page import="com.mp.domain.*" %>
<%@ page import="com.mp.MenuConstants.*" %>
<html>
<head>
    <title>${recipe?.name}</title>
    <meta name="layout" content="menu"/>
    <g:javascript src="jquery.printElement.min.js"/>
    <script type="text/javascript">
        jQuery(function() {
            jQuery("#printButton").click(function() {
                printElem({ pageTitle: '${recipe?.name}' });
                return false;
            });

        });
        function printElem(options) {
            jQuery(".printMe:first").printElement(options);
        }
    </script>
</head>
<body>
<div class="clearfix printMe" id="content-wrapper">
    <!--  start left-panel -->
    <div id="left-panel">
    <!--  start left-panel -->

        <g:if test="${flash.message}">
            <div class="flashMessage">${flash.message}</div>
        </g:if>


        <div id="right-head">
            <div id="leftpart">
                <label>${recipe?.name}</label>
                <g:render template="/rating/rating"/>
                <g:if test="${recipe?.preparationTime}">Prep - ${recipe?.preparationTime}</g:if>
                <g:if test="${recipe?.cookingTime}">Cook - ${recipe?.cookingTime}</g:if>
            </div>
            <div id="rightpart">
                <span>
                    <img src="${resource(dir: 'images', file: 'edit.gif')}"/>
                    <g:link action="edit" id="${recipe?.id}">Edit</g:link>
                </span>
                <span><img src="${resource(dir: 'images', file: 'printer.gif')}"/><a href="#" id="printButton">Print</a></span>
            </div>

        </div>
        <div class="top-shadow"><label></label></div>
        <div class="leftbox clearfix">
            <div id="contectElement">
                <ul>
                    <li>
                        <ul>
                            <li>
                                <g:if test="${recipe?.difficulty}">
                                    <span>Difficulty Level: ${recipe?.difficulty}</span>
                                </g:if>
                                <g:if test="${recipe?.servings}">
                                    <span>Servings: ${recipe?.servings}</span>
                                </g:if>
                            </li>
                            <g:if test="${recipe?.ingredients}">
                                <li>
                                    <g:each in="${recipe?.ingredients}" var="ingredient">
                                        <span><strong>${ingredient}</strong></span>
                                    </g:each>
                                </li>
                            </g:if>
                            <g:if test="${recipe?.directions}">
                                <li>${recipe?.directions*.toString().join(" ")}</li>
                            </g:if>
                            <g:if test="${recipe?.categories}">
                                <li>Categories: ${recipe?.categories?.join(", ")}</li>
                            </g:if>
                            <g:if test="${recipe?.nutrients}">
                                <li><span>Nutritional Facts per serving:</span>
                                    <g:each in="${recipe?.nutrients}" var="nutrient">
                                        <span>${nutrient}</span>
                                    </g:each>
                                </li>
                            </g:if>
                            <g:if test="${recipe?.items}">
                                <li><span>Serve With: ${recipe?.items?.join(", ")}</span></li>
                            </g:if>
                            <li>
                            </li>
                        </ul>
                    </li>
                    <li style="text-align: right;">
                        <mp:recipeImage id="${recipe?.image?.id}" height="160" width="160" noImage="no-img.gif"/><br/>
                        <g:link controller="user" action="alterFavorite" name="changeFavorite" id="${recipe?.id}">
                            <span id="showFavorite" style="text-align:right;">
                                <mp:showFavorite recipeId="${recipe?.id}" userId="${session?.loggedUserId}"/>
                            </span>
                        </g:link>
                    </li>
                </ul>
                <div class="content">
                    <strong>Comments :</strong><br/>
                    
                    <comments:each bean="${recipe}">
                        <span class="clearfix">
                            %{--<g:render template='/recipe/rating'/>--}%
                            ${comment?.body} - Posted by <g:link controller="user" action="show" id="${comment?.poster?.id}">${comment?.poster}</g:link>
                            %{--<g:link controller="user" action="reportComment" id="${comment?.poster?.id}"> &nbsp;&nbsp;&nbsp; report this </g:link>--}%
                            <br/>
                        </span>
                    </comments:each><br/> 
                    <g:uploadForm name="addCommentForm">
                        <g:textArea class="inpbox" name="comment" rows="5" cols="50"/>
                        <g:hiddenField name="recipeId" value="${recipe?.id}"/>
                        <g:actionSubmit controller="recipe" action="addComment" value="Add Comment"/>
                    </g:uploadForm>
                </div>
            </div>
        </div>
        <div class="bottom-shadow"><label></label></div>
    </div>
    <div id="right-panel">
    </div>

</div>
</body>
</html> 