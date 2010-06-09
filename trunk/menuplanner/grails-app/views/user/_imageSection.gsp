<%@ page import="com.mp.domain.Permission; com.mp.domain.UserType" %>
<div id="leftpanel">
    <div id="photo">
        <img id='userImage' border='0' width='180' height="180" src="${g.createLink(controller: 'image', action: 'imageByPath', params: [imagePath: userCO?.selectUserImagePath, noImage: 'no-img.gif'])}"/>
    </div>
    <input type="hidden" name="selectUserImagePath" id="selectUserImagePath" value="${userCO?.selectUserImagePath}"/>
    <g:render template="/recipe/imageUpload" model="[selectorName:'selectUserImage']"/>
    <ul>
        <g:if test="${userCO?.joiningDate}"><li>Member since ${userCO?.joiningDate?.format('MMMM yyyy')}</li></g:if><li></li><li></li>
        <g:if test="${permission.hasPermission(permission: Permission.UPDATE_USER_ROLES)}">
            <g:each in="${UserType?.list()}" var="role">
                <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role.name}</li>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${UserType?.list()}" var="role">
                <g:if test="${role.name() in userCO?.roles}">
                    <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
                </g:if>
            </g:each>
        </g:else>
    </ul>
</div>
