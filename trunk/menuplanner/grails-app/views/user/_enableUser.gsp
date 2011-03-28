<%@ page import="com.mp.domain.Permission" %>
<label>
    <g:if test="${(party?.id && party?.superAdmin) && permission.hasPermission(permission: Permission.MANAGE_SUPER_ADMIN,party:currentUser)}">
        <input id="chk_Enable" name="isEnabled" ${(userCO) ? ((userCO?.isEnabled) ? 'checked="checked"' : '') : 'checked="checked"'}type="checkbox" value="true"/>
        <strong>Account enabled</strong>
    </g:if>
    <g:elseif test="${(party?.id && party?.administrator) && permission.hasPermission(permission: Permission.MANAGE_ADMIN,party:currentUser) && (party?.id != currentUser?.id)}">
        <input id="chk_Enable" name="isEnabled" ${(userCO) ? ((userCO?.isEnabled) ? 'checked="checked"' : '') : 'checked="checked"'}type="checkbox" value="true"/>
        <strong>Account enabled</strong>
    </g:elseif>
    <g:elseif test="${(party?.id && party?.subscriber) && permission.hasPermission(permission: Permission.MANAGE_SUBSCRIBER,party:currentUser) && (party?.id != currentUser?.id)}">
        <input id="chk_Enable" name="isEnabled" ${(userCO) ? ((userCO?.isEnabled) ? 'checked="checked"' : '') : 'checked="checked"'}type="checkbox" value="true"/>
        <strong>Account enabled</strong>
    </g:elseif>
    <g:elseif test="${(party?.id && party?.director) && permission.hasPermission(permission: Permission.MANAGE_AFFILIATE,party:currentUser) && (party?.id != currentUser?.id)}">
        <input id="chk_Enable" name="isEnabled" ${(userCO) ? ((userCO?.isEnabled) ? 'checked="checked"' : '') : 'checked="checked"'}type="checkbox" value="true"/>
        <strong>Account enabled</strong>
    </g:elseif>
    <g:elseif test="${(party?.id && party?.coach) && permission.hasPermission(permission: Permission.MANAGE_SUB_AFFILIATE,party:currentUser) && (party?.id != currentUser?.id)}">
        <input id="chk_Enable" name="isEnabled" ${(userCO) ? ((userCO?.isEnabled) ? 'checked="checked"' : '') : 'checked="checked"'}type="checkbox" value="true"/>
        <strong>Account enabled</strong>
    </g:elseif>
    <g:elseif test="${permission.hasPermission(permission: Permission.ENABLE_DISABLE_USER,party:currentUser)}">
        <input id="chk_Enable" name="isEnabled" ${(userCO) ? ((userCO?.isEnabled) ? 'checked="checked"' : '') : 'checked="checked"'}type="checkbox" value="true"/>
        <strong>Account enabled</strong>
    </g:elseif>
    <g:else>
        <input name="isEnabled" value="${userCO?.isEnabled}" type="hidden" value="true"/>
    </g:else>
</label>