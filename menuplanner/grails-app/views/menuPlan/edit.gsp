<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
  <meta name="layout" content="menu"/>
  <title>Edit ${menuPlan.name}</title>
  <g:javascript library="ui.core"/>
  <g:javascript library="ui.sortable"/>
  <g:javascript src="menuPlan.js"/>
  <rateable:resources/>
  <script type="text/javascript">
    setCrossImagePath("${resource(dir:'images',file:'delete.jpg')}")
  </script>

</head>

<body>
<div id="content-wrapper" class="clearfix">
<div id="left-panel">
  <g:form action="saveAndUpdate" name="editMenuPlanForm">
    <g:render template="/menuPlan/actions"/>
    <g:render template="/quickFill/quickFills"/>
    <input type="hidden" name="id" value="${menuPlan.id}"/>
    <g:render template="weeklyMeals"/>
    <div id="button">
      <ul>
        <li><g:submitButton class="button" name="update" value="Update"/></li>
        <li><g:actionSubmit class="button" action="show" value="Cancel"/></li>
      </ul>
    </div>
    </div>
  </g:form>
  <g:render template="/menuPlan/search"/>
</div>
</body>
</html>
