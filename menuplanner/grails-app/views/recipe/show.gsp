<%@ page import="com.mp.domain.*" %>
<%@ page import="com.mp.MenuConstants.*" %>
<html>
<head>
    <title>${recipe?.name}</title>
    <meta name="layout" content="menu"/>
</head>
<body>
<div class="clearfix" id="content-wrapper">
    <!--  start left-panel -->
    <div id="left-panel">
        <!--  start left-panel -->
        <div id="right-head">
            <div id="leftpart">
                <label style="">${recipe?.name}</label>
                <g:render template="/rating/rating"/>
                <g:if test="${recipe?.preparationTime}">Prep - ${recipe?.preparationTime}</g:if>
                <g:if test="${recipe?.cookingTime}">Cook - ${recipe?.cookingTime}</g:if>
            </div>
            <div id="rightpart">
                <span>
                    <img src="${resource(dir: 'images', file: 'edit.gif')}"/>
                    <g:link action="edit" id="${recipe?.id}">Edit</g:link>
                </span>
                <span><img src="${resource(dir: 'images', file: 'printer.gif')}"/><a href="#">Print</a></span>
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
                                        <li>
                                            <g:each in="${recipe?.directions}" var="direction">
                                                <span>${direction}</span>
                                            </g:each>
                                        </li>
                                    </g:if>
                                    <g:if test="${recipe?.categories}">
                                        <li>
                                            <span>Categories: ${recipe?.categories?.join(", ")}</span>
                                        </li>
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
                                </ul>
                            </li>
                            <li style="text-align: right;">
                                <g:render template="/rating/rating"/><span>Rating</span><br/>
                                <a href="#"><strong>test link</strong></a></li>
                        </ul>
                    </div>
                </div>
                <div class="bottom-shadow"><label></label></div>
            </div>
            <!--  end left-panel start right-panel -->
            <div id="right-panel">

            </div>
            <!--  end right-panel -->
        </div>
</body>
</html> 