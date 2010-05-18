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

    <g:each in="${userList}" var="user">
        <li class="alternatecolor">
            <ul>

                <li>
                    <a href="${createLink(action: 'show', controller: 'user', id: user?.id)}">${user?.name}</a>
                </li>
                <li class="email">${user?.email}</li>
                %{--<li>2 day ago</li>--}%
                %{--<li>Enabled</li>--}%
                %{--<li>0 Flags</li>--}%
                <li>To Be Implemented</li>
                <li id="status${user?.id}">${user?.status}</li>
                <li>To Be Implemented</li>
                <li><g:link controller="user" action="edit" params="[id:user?.id]">Edit</g:link></li>
                <li><g:remoteLink controller="user" action="changeStatus" onSuccess="invertStatus('${user?.id}');" name="changeStatus${user?.id}"
                        id="${user?.id}">${(user?.status == AccountStatus.ACTIVE) ? 'Deactivate' : 'Activate'}</g:remoteLink></li>
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
        if (text == 'Activate') {
            jQuery(link).text('Deactivate');
            jQuery('#status' + id).text('Active');
        } else {
            jQuery(link).text('Activate');
            jQuery('#status' + id).text('Inactive');
        }
    }
</script>

