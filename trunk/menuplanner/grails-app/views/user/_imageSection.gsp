<%@ page import="com.mp.domain.Permission; com.mp.domain.UserType" %>
<div id="leftpanel">
    <div id="photo">
      <img id='userImage' border='0' width='180' height="180" src="${g.createLink(controller: 'image', action: 'imageByPath', params: [imagePath: userCO?.selectUserImagePath, noImage:'no-img.gif'])}"/>
    </div>
    <input type="hidden" name="selectUserImagePath" id="selectUserImagePath" value="${userCO?.selectUserImagePath}"/>

    <g:render template="/recipe/imageUpload" model="[selectorName:'selectUserImage']"/>

    <ul>
    <g:if test="${userCO?.joiningDate}">
        <li>Member since ${userCO?.joiningDate?.format('MMMM yyyy')}</li>
    </g:if>

    <li></li>
    <li></li>

    <g:each in="${UserType?.list()}" var="role">
        <li>
            <input id="chk_${role.name()}" type="checkbox" name="roles" ${userCO?.roles?.contains(role.name()) ? 'checked="checked"':''} value="${role.name()}"/>${role.name}
        </li>
    </g:each>

    %{--<li>--}%
    %{--<h3>Contributed Recipes</h3>--}%
    %{--</li>--}%
    %{--<li><a href="#">Beef & broccoll</a></li>--}%
    %{--<li><a href="#">Lamb Curry</a></li>--}%
    %{--<li><a href="#">Turkey Pie</a></li>--}%
    %{--<li>--}%
    %{--<h3>Favorites</h3>--}%
    %{--</li>--}%
    %{--<li><a href="#">Beef & broccoll</a> <a href="#">remove</a></li>--}%
    %{--<li><a href="#">Lamb Curry</a> <a href="#">remove</a></li>--}%
    %{--<li><a href="#">Turkey Pie</a> <a href="#">remove</a></li>--}%
    </ul>
</div>
