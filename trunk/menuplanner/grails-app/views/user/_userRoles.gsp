<%@ page import="com.mp.domain.party.Coach;com.mp.tools.UserTools;com.mp.domain.PartyRoleType;com.mp.domain.LoginCredential;com.mp.domain.Permission" %>
<g:each in="${PartyRoleType?.list()}" var="role">
    <g:if test="${role?.name ==PartyRoleType.SuperAdmin.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_SUPER_ADMIN_ROLE,party:UserTools.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>
        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==PartyRoleType.Admin.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_ADMIN_ROLE,party:UserTools.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>

        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==PartyRoleType.Subscriber.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_SUBSCRIBER_ROLE,party:UserTools.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>

        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
      <div id="coachesList" style="display:none">
          <span style="margin-left:10px;"><g:select  style="width:130px;" class="inpbox ${hasErrors(bean: userCO, field: 'coachDbId', 'errors')}" name="coachDbId" from="${Coach?.list()}" value="${userCO?.coachDbId}" optionKey="id" noSelection='["":"Select Coach"]'/></span>
      </div>

    </g:if>
    <g:if test="${role?.name ==PartyRoleType.Director.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_AFFILIATE_ROLE,party:UserTools.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>
        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
    <g:if test="${role?.name ==PartyRoleType.Coach.name }">
        <g:if test="${(permission.hasPermission(permission: Permission.CAN_ASSIGN_SUB_AFFILIATE_ROLE,party:UserTools.currentUser?.party))}">
            <li><input id="chk_${role.name()}" type="checkbox" name="roles" ${(role.name() in userCO?.roles) ? 'checked="checked"' : ''} value="${role.name()}"/>${role}</li>
        </g:if>
        <g:else>
            <g:if test="${role.name() in userCO?.roles}">
                <li><input id="chk_${role.name()}" type="hidden" name="roles" value="${role.name()}"/></li>
            </g:if>
        </g:else>
    </g:if>
</g:each>
