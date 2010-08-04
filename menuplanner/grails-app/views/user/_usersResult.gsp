<%@ page import="com.mp.domain.*" %>
<ul>
    <li class="head">
        <ul>
            <li>Name</li>
            <li class="email">Email</li>
            <li>Last Login</li>
            <li>Enabled</li>
            <li>Flagged</li>
            <li>&nbsp;</li>
            <li>&nbsp;</li>
        </ul>
    </li>

    <g:each in="${parties}" var="party">
        <li class="alternatecolor">
            <ul>

                <li>
                    <a href="${createLink(action: 'show', controller: 'user', id: party?.id)}">${party?.name}</a>
                </li>
                <li class="email">${party?.email}</li>
                %{--<li>2 day ago</li>--}%
                <li>To Be Implemented</li>
                <li id="status${party?.id}">${party?.isEnabledString}</li>
                <li>${party?.inappropriateFlagsCount} Flags</li>
                <li><g:link controller="user" action="edit" params="[id:party?.id]">Edit</g:link></li>
                <li><g:remoteLink controller="user" action="changeStatus" onSuccess="invertStatus('${party?.id}');" name="changeStatus${party?.id}"
                        id="${party?.id}">${(party?.isEnabledString == 'Enabled') ? 'Disable' : 'Enable'}</g:remoteLink></li>
            </ul>
        </li>
    </g:each>

%{--<li>--}%
%{--<ul>--}%
%{--<li>Barbara Salt</li>--}%
%{--<li class="email">bsalt@gmail.com</li>--}%
%{--<li>2 day ago</li>--}%
%{--<li>Enabled</li>--}%
%{--<li>0 Flagged</li>--}%
%{--<li>&nbsp;<a href="#">Edit</a></li>--}%
%{--<li>&nbsp;<a href="#">Disable</a></li>--}%
%{--</ul>--}%
%{--</li>--}%
</ul>

<script type="text/javascript">
    function invertStatus(id) {
        var link = jQuery("a[name=changeStatus" + id + "]");
        var text = jQuery(link).text();
        if (text == 'Enable') {
            jQuery(link).text('Disable');
            jQuery('#status' + id).text('Enabled');
        } else {
            jQuery(link).text('Enable');
            jQuery('#status' + id).text('Disabled');
        }
    }
</script>

