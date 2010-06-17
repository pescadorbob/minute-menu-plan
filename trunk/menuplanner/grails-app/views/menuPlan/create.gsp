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
        <g:render template="/quickFill/quickFills"/>
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
        <g:message code="toolTip.menuPlan.firstTimeUser"/>
    </div>
    <script type="text/javascript">
        $(function() {
            $('#draggingBaloonTip').fadeIn()
            setTimeout(function() {
                $('#draggingBaloonTip').fadeOut()
            }, 20000)
        })
    </script>
</mp:firstTimeUser>
<script type="text/javascript">
    $('#menuPlanNameTextbox').each(function() {

        this.value = $(this).attr('title');
        $(this).css('color', '#cdcdcd')

        $(this).focus(function() {
            if (this.value == $(this).attr('title')) {
                this.value = '';
                $(this).css('color', '');
            }
        });

        $(this).blur(function() {
            if (this.value == '') {
                this.value = $(this).attr('title');
                $(this).css('color', '#cdcdcd');
            }
        });
    })
</script>
</body>
</html>
