<%@ page import="com.mp.domain.MealType" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Edit ${menuPlan.name}</title>
    <p:javascript src='menuPlanJquery'/>
    <rateable:resources/>
    <p:css name='custom-ratings'/>
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
                <li><g:actionSubmit class="button" action="show" value="Cancel" id="cancel"/></li>
            </ul>
        </div>
        </div>
    </g:form>
    <g:render template="/menuPlan/search"/>
    <script type="text/javascript">
        $("#update").click(function() {
            if ($("#menuPlanNameTextbox").val() == "") {
                alert("Please enter Menu plan name")
                return false;
            }
        });
        jQuery.each(jQuery('a'), function() {
            $(this).attr('target', '_blank');
        });
    </script>
</div>
</body>
</html>
