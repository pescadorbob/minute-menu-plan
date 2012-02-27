<%@ page import="com.mp.domain.MenuPlan" %>
<li class="user-recipes">
<ul onclick="location.href = '${createLink(action: 'show', controller: 'user', id: party?.id)}'">
<input type="hidden" name="menuItemId" value="${party?.id}"/>
<li style="top:0px;" class="topcorner">
    <p:image src='top-rounded.png' width="215" border="0"/></li>
  <li><h3 class="recipeName"><p>${party}</p></h3></li>

<li>
    <ul>
        <li style="padding:4px;"><div class="frame-corner">&nbsp;</div>
            <mp:image class="imgbor" size="200" id="${party?.subscriber?.image?.id}"/></li>
        <li>
            <ul>
              <li>Since ${party?.joiningDate?.format('MMM yyyy')}</li>
              <li class="difficultyBlue" style="background-color:#eee!important;color:#000;">
                <g:if test="${party?.lastLogin}">
                  Seen
                <prettytime:display date="${party?.lastLogin}" />
                  </g:if>
                  <g:else>Never Seen</g:else>
              </li>
              <li>${party?.subscriber?.city ?: ''}</li>
              <li>${MenuPlan.findAllByOwner(party)?.size()} menu plans</li>
              <li>${party?.subscriber?.mouthsToFeed} mouths</li>
              <g:if test="${party?.coach}">
                <li><pty:clientCount party="${party}" /> clients</li>
                </g:if>
              
            </ul>
        </li>
    </ul>
</li>
<li style=" bottom:0px;" class="topcorner"><p:image src='bottom-rounded.png' width="215" border="0" height="4"/></li>
</ul>
</li>

%{--<g:link controller="user" action="show" id="${party?.id}">--}%
  %{--<mp:image size="200" height="40" id="${party?.subscriber?.image?.id}" alt="${party?.subscriber}"  title="${party?.subscriber}"/>--}%
%{--</g:link>--}%
