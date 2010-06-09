<%@ page import="com.mp.domain.Recipe" %>
<div class="winter-week clearfix" style="width:406px;">
    <div class="winterButton"><strong>Week${weekIndex?.toInteger() + 1}</strong><p></p></div>
    <ul>
        <g:each in="${menuPlan.weeks[weekIndex?.toInteger()]}" var="week">
            <g:each in="${week.days}" var="day">
                <g:set var="items" value="${day.breakfast + day.lunch + day.dinner}"/>
                <g:each in="${items}" var="item" status="i">
                    <g:render template="shoppingListRecipe${item.instanceOf(Recipe)}" model="[item:item]"/>
                </g:each>
            </g:each>
        </g:each>
    </ul>
</div>
<div class="winterButton">
    <ul>
        <li id="groceries_${weekIndex}" class="grocery">
        </li>
        <li><span>
            <input type="button" name="addItemBtn_${weekIndex}" id="addItemBtn_${weekIndex}" value="Add Item"/></span>
            <label>

                <input name="addItemTxt_${weekIndex}" class="inpbox" id="addItemTxt_${weekIndex}" title="${g.message(code:'toolTip.recipe.unit')}">

                %{--<input type="text" class="inpbox" value="Auto complete grocery Items " size="40" name="addItemTxt_${weekIndex}" id="addItemTxt_${weekIndex}" />--}%
            </label>
        </li>
    </ul>
</div>

<script type="text/javascript">
    jQuery(document).ready(function(){
        jQuery('#addItemBtn_${weekIndex}').click(function(){
            var htmlString = '<p>'+jQuery('#addItemTxt_${weekIndex}').val()+'</p><input type="hidden" name="groceries${weekIndex}" value="'+jQuery('#addItemTxt_${weekIndex}').val()+'">'
            jQuery('#groceries_${weekIndex}').append(htmlString)
            return false;
        })
        $("#addItemTxt_${weekIndex}").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
           width: 300,
           multiple: false,
           matchContains: true
         })

    })
</script>