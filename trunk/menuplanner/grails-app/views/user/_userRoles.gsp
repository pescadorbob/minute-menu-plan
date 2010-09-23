<%@ page import="com.mp.domain.LoginCredential; com.mp.domain.Permission; com.mp.domain.UserType" %>
<g:each in="${UserType?.list()}" var="role">
    <g:if test="${role?.name ==UserType.SuperAdmin.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_SUPER_ADMIN_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>
        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==UserType.Admin.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_ADMIN_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>

        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==UserType.Subscriber.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_SUBSCRIBER_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>

        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==UserType.Affiliate.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_AFFILIATE_ROLE,party:LoginCredential?.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>
        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==UserType.SubAffiliate.name }">
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