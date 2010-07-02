<ul onclick="location.href = '${createLink(action: 'show', controller: 'recipe', id: item?.id)}'">
    <input type="hidden" name="menuItemId" value="${item.id}"/>
    <li><h3 class="recipeName"><a href="${createLink(action: 'show', controller: 'recipe', id: item?.id)}">${item.name}</a></h3></li>
    <li><ul>
        <li><div class="frame-corner">&nbsp;</div>
            <mp:recipeImage class="imgbor" id="${item?.image?.id}" noImage="no-img.gif"/>
        <li>
            <ul>
                <li><rateable:ratings bean='${item}' active="false"/></li>
                <g:if test="${item?.totalTime}"><li>${item?.totalTime}</li></g:if>
                <g:if test="${item?.difficulty}">
                    <li class="difficultyBlue" style="background-color:#eee!important;color:#000;">${item?.difficulty}</li>
                </g:if>
                <g:each in="${item?.ingredients?.ingredient}" var="product" status="i">
                    <g:if test="${(i<3)}"><li>${product}</li></g:if>
                </g:each>
            </ul>
        </li>
    </ul>
    </li>
</ul>