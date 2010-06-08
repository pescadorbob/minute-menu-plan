<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Create MenuPlan</title>
    <rateable:resources/>
    <g:javascript library="ui.core"/>
    <g:javascript library="ui.sortable"/>
    <g:javascript src="menuPlan.js"/>
    <script type="text/javascript">
        setCrossImagePath("${resource(dir:'images',file:'delete.jpg')}")
    </script>
</head>

<body>

<div id="content-wrapper" class="clearfix">
<div id="left-panel">
  <g:form action="saveAndUpdate" name="editMenuPlanForm">
    <g:render template="/menuPlan/actions"/>
    <g:render template="/menuPlan/quickFills"/>
        <g:render template="weeklyMeals"/>
        <div id="button">
            <ul>
                <li><g:submitButton class="button" name="create" value="Create"/></li>
                <li><g:actionSubmit class="button" action="create" value="Cancel"/></li>
            </ul>
        </div>
        </div>
    </g:form>
    <g:render template="/menuPlan/search"/>
</div>
<mp:firstTimeUser>
<div class="tooltip" id="draggingBaloonTip" style="display: none; visibility: visible; position: absolute; top: 155px; left: 650px;">
  To create a menu plan: Click on the search item from right and drag it into the empty boxes on the left side. As soon as you release the click the item will automatically gets added to the plan.
</div>
<script type="text/javascript">
  $(function(){
    $('#draggingBaloonTip').fadeIn()
    setTimeout(function(){
      $('#draggingBaloonTip').fadeOut()
    },20000)
  })
  </mp:firstTimeUser>
</script>
</body>
</html>
