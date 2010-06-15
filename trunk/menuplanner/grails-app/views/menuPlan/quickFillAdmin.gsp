<%@ page import="com.mp.domain.QuickFill" %>
<html>
<head>
  <meta name="layout" content="menu"/>
  <title>Admin: Quick Fill</title>
</head>
<body>
<div id="content-wrapper" class="clearfix">
  <div id="left-panel">
    <!--  start left-panel -->
    <div class="headbox">
      <h3>Quick Fill Admin</h3>
    </div>
    <div class="top-shadow">
      <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
      <div id="country-cate">Quick Fill Admin :<input type="button" value="Add New"/>
      </div>
      <g:render template="quickFillList"/>
      <div id="box-right">
        <div id="usersave"><strong>Name :</strong>
          <input type="text" class="inpbox" value="${quickFill.name}"/>
          &nbsp;
          <input type="button" value="Save"/>
        </div>
        <div class="box-rating">
          <ul>
            <g:each in="${quickFill.mealItems}" var="meal">
              <li>
                <h2>${meal.type}</h2>
              </li>
              <li class="bgcolor clearfix">
                <g:each in="${meal?.items}" var="item">
                  <div>${item.name}<a href="quickFillAdmin.gsp#">Remove</a></div>
                </g:each>
              </li>
            </g:each>
          </ul>
        </div>
        <!-- paging -->
        <!--end paging-->
      </div>
    </div>
    <div class="bottom-shadow">
      <label>&nbsp;</label>
    </div>
  </div>
  <!--  end left-panel start right-panel -->

  <g:render template="/menuPlan/search" model='[categoryList:categoryList, itemTotal:itemTotal]'/>

</div>
</body>
</html>
