<%@ page import="com.mp.domain.ElementLocation" %>
<li>
    <a href="#" class="menuplan"><span>Menu&nbsp;Plans</span></a>
    <ul><li><nobr>
      <theme:gadget location="${ElementLocation.MENU}" contextRule="Featured Menus"/>
        <g:each in="${menuPlans}" var="menuPlan">
            <g:link controller="menuPlan" action="show" id="${menuPlan.id}">${menuPlan.name}</g:link>
        </g:each>
    </nobr></li>
        <li><nobr>
            <g:if test="${((params.controller =='menuPlan') && (params.action in ['show','saveAndUpdate']))}">
                <g:link controller="menuPlan" action="create" params="[menuPlanId: menuPlan?.id]">Copy Menu Plan</g:link>
            </g:if>
            <g:link controller="menuPlan" action="create">Add New Menu Plan</g:link>
        </nobr></li>
    </ul>
</li>
