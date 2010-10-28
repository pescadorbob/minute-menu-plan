<%@ page import="com.mp.domain.LoginCredential; com.mp.domain.UserType; com.mp.domain.Permission" %>
<g:if test="${loggedUser}">
    <li>
        <a href="#"><span>Hi! ${loggedUser.party.name}</span></a>
        <ul>
            <li>
                <g:link name="profileLinkTst" class="userProfileLinkFT" controller="user" action="show" id="${loggedUser?.party?.id}">Profile</g:link>
                <g:link name="addNewRecipeLink" class="addNewRecipeLinkF" controller="recipe" action="create">Add New Recipe</g:link>
                <g:if test="${(UserType.Affiliate in LoginCredential.currentUser?.party?.roleTypes ) && (permission.hasPermission(permission: Permission.MANAGE_SUB_AFFILIATE))}">
                    <g:link class="addSubAffiliateFT" name="addNewSubAffiliate" controller="user" action="createSubAffiliate">Add Sub Affiliate</g:link>
                </g:if>
            </li>
            <li><g:link class="logoutLink" controller="login" action="logout">Logout</g:link></li>
        </ul>
    </li>
</g:if>
<g:else>
    <li><g:link controller="user" action="createFreeUser">Hi! Guest</g:link></li>
</g:else>