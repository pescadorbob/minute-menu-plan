<g:if test="${loggedUser}">
    <li>
        <a href="#"><span>Hi! ${loggedUser.party.name}</span></a>
        <ul>
            <li>
                <g:link name="profileLinkTst" class="userProfileLinkFT" controller="user" action="show" id="${loggedUser?.party?.id}">Profile</g:link>
                <g:link name="addNewRecipeLink" class="addNewRecipeLinkF" controller="recipe" action="create">Add New Recipe</g:link>
            </li>
            <li><g:link class="logoutLink" controller="login" action="logout">Logout</g:link></li>
        </ul>
    </li>
</g:if>                                      