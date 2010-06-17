<%@ page import="com.mp.domain.QuickFill" %>
<div id="box-left">
    <div id="userlist">
        <ul>
            <li class="head">
                <ul>
                    <li>Name</li>
                    <li>&nbsp;</li>
                </ul>
            </li>
            <g:each in="${quickFillList}" var="quickFill" status="i">
                <li class="${(i % 2 == 0) ? 'alternatecolor ' : ''}clearfix">
                    <ul>
                        <li>${quickFill.name}</li>
                        <li>&nbsp;<g:link controller="quickFill" action="quickFillAdmin" id="${quickFill.id}">Edit</g:link></li>
                    </ul>
                </li>
            </g:each>
        </ul>
    </div>

    <div class="paginateButtons">
        <g:paginate controller="quickFill" action="quickFillAdmin" total="${quickFillTotal}"/>
    </div>
</div>
