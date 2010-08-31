<ul onclick="location.href = '${createLink(action: 'show', controller: 'recipe', id: item?.id)}'">
    <input type="hidden" name="menuItemId" value="${item.id}"/>
    <li style="top:0px;" class="topcorner"><img width="215" border="0" src="${resource(dir: 'images', file: 'top-rounded.png')}"/></li>
    <li><h3 class="recipeName"><a href="${createLink(action: 'show', controller: 'recipe', id: item?.id)}">${item.name}</a></h3></li>
    <li><ul>
        <li  style="padding:4px;"><div class="frame-corner">&nbsp;</div>
            <mp:image class="imgbor" size="100" id="${item?.image?.id}"/>
        <li>
            <ul>
                <li><rateable:ratings bean='${item}' active="false"/></li>
                <g:if test="${item?.totalTime}"><li>${item.totalTime.toReadableTimeString()}</li></g:if>
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
  <li style=" bottom:0px;" class="topcorner"><img  width="215"  border="0" height="4" src="${resource(dir: 'images', file: 'bottom-rounded.png')}"/></li>
</ul>