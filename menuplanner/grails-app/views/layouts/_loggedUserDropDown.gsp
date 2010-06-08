<g:if test="${loggedUser}">
    <li>
        <a href="#"><span>Hi! ${loggedUser.name}</span></a>
        <ul>
            <li>
                <g:link controller="user" action="show" id="${loggedUser?.id}">Profile</g:link>
                <g:link name="addNewRecipeLink" controller="recipe" action="create">Add New Recipe</g:link>
            </li>
            <li><g:link class="logoutLink" controller="login" action="logout">Logout</g:link></li>
        </ul>
    </li>
</g:if>                                      