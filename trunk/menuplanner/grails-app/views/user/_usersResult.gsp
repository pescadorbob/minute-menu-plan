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
                        <a href="${createLink(action: 'show', controller:'user', id: user?.id)}">${user?.name}</a>
                    </li>
                    <li class="email">${user?.email}</li>
                    %{--<li>2 day ago</li>--}%
                    %{--<li>Enabled</li>--}%
                    %{--<li>0 Flags</li>--}%
                    <li>To Be Implemented</li>
                    <li>To Be Implemented</li>
                    <li>To Be Implemented</li>
                    <li>&nbsp;<a href="#">Edit</a></li>

                    <li>&nbsp;<a href="#">Disable</a></li>
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
