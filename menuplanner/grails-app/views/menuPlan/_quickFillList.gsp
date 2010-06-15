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
      <g:each in="${QuickFill.list()}" var="quickFill" status="i">
        <li class="${(i % 2 == 0) ? 'alternatecolor ' : ''}clearfix">
          <ul>
            <li>${quickFill.name}</li>
            <li>&nbsp;<g:link controller="menuPlan" action="quickFillAdmin" id="${quickFill.id}">Edit</g:link></li>
          </ul>
        </li>
      </g:each>
    </ul>
  </div>
  <div id="pagination">
    <ul>
      <li><img src="${resource(dir: 'images', file: 'first.gif')}" class="arrowbor"/></li>
      <li><img src="${resource(dir: 'images', file: 'prev.gif')}" class="arrowbor"/></li>
      <li><a href="quickFillAdmin.gsp#">1</a></li>
      <li><a href="quickFillAdmin.gsp#">2</a></li>
      <li><a href="quickFillAdmin.gsp#">3</a></li>
      <li><a href="quickFillAdmin.gsp#">4</a></li>
      <li><a href="quickFillAdmin.gsp#">5</a></li>
      <li><a href="quickFillAdmin.gsp#"><img src="${resource(dir: 'images', file: 'next.gif')}" border="0" class="arrowbor"/></a></li>
      <li><a href="quickFillAdmin.gsp#"><img src="${resource(dir: 'images', file: 'last.gif')}" border="0" class="arrowbor"/></a></li>
    </ul>
  </div>
</div>
