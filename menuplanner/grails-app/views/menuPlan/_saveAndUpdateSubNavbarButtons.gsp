<%@ page import="com.mp.domain.PartyRoleType; com.mp.tools.UserTools" %>
<g:if test="${UserTools.currentUser && ((UserTools.currentUser.party.roles*.type in [PartyRoleType.Admin, PartyRoleType.SuperAdmin])|| (UserTools.currentUser.party == menuPlan.owner))}">
    <li><g:link action="edit" id="${menuPlan.id}" name="editMenuPlan" class="btn">Edit</g:link></li>
</g:if>
