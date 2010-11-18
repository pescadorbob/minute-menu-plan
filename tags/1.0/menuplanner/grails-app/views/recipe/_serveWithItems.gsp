<%@ page import="com.mp.domain.Recipe" %>
<span id="showServeWithTst">Serve With:
<g:each in="${items}" var="item" status="i">
    <g:if test="${item?.instanceOf(Recipe)}">
        <g:link class="recipeServeWithFT" action="show" controller="recipe" id="${item?.id}">${item}</g:link>
        <g:if test="${i < items?.size()-1}">,</g:if>
    </g:if>
    <g:else>
        ${item}<g:if test="${i < items?.size()-1}">,</g:if>
    </g:else>
</g:each>
</span>
                    