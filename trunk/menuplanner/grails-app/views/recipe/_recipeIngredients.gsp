<%@ page import="com.mp.domain.Permission" %>
<g:each in="${ingredients}" var="ingredient">
    <div class="ingredient">
    <span class="ingredient-details"><strong>${ingredient}</strong></span>
    <g:if test="${permission.hasPermission(permission: Permission.MANAGE_SUPER_ADMIN)}">
        <g:if test="${prices[ingredient]}">
            <span class="ingredient-price">
                <pricing:price ingredient="${ingredient}" itemPrice="${prices[ingredient]}"/>
            </span>
        </g:if>
    </g:if>
    </div>
</g:each>