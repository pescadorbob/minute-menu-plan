<%@ page import="com.mp.domain.LoginCredential; com.mp.domain.PartyRoleType; com.mp.domain.Permission" %>
<g:if test="${loggedUser}">
    <li>
        <a href="#"><span>Hi! ${loggedUser.party.name}</span></a>
        <ul>
            <li>
                <g:link name="profileLinkTst" class="userProfileLinkFT" controller="user" action="show" id="${loggedUser?.party?.id}">Profile</g:link>
                <g:link name="userAccount" controller="account" action="showUserAccount" id="${loggedUser?.party?.id}">Account</g:link>
                <g:link name="addNewRecipeLink" class="addNewRecipeLinkF" controller="recipe" action="create">Add New Recipe</g:link>
                <g:if test="${(PartyRoleType.Director in LoginCredential.currentUser?.party?.roleTypes ) && (permission.hasPermission(permission: Permission.MANAGE_SUB_AFFILIATE))}">
                    <g:link class="addCoachFT" name="addNewCoach" controller="user" action="createCoach">Add Sub Director</g:link>
                </g:if>
            </li>
            <li><g:link class="logoutLink" controller="login" action="logout">Logout</g:link></li>
        </ul>
    </li>
</g:if>
<g:else>
    <li><a href="#">Hi! Guest</a></li>
</g:else>