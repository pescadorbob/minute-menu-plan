<%@ page import="com.mp.domain.PartyRoleType; com.mp.domain.LoginCredential; com.mp.domain.Permission; com.mp.domain.PartyRoleType" %>
<g:each in="${PartyRoleType?.list()}" var="role">
    <g:if test="${role?.name ==PartyRoleType.SuperAdmin.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_SUPER_ADMIN_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>
        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==PartyRoleType.Admin.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_ADMIN_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>

        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==PartyRoleType.Subscriber.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_SUBSCRIBER_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>

        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==PartyRoleType.Director.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_AFFILIATE_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>
        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==PartyRoleType.Coach.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_SUB_AFFILIATE_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>
        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
</g:each>