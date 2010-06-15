<%@ page import="com.mp.domain.Recipe" %>
<div class="clearfix">
    <div class="winter-week">
        <div class="winterButton"><strong>Week${weekIndex?.toInteger() + 1}</strong><p></p></div>
        <ul>
            <g:each in="${productListForWeek}" var="prodList" status="i">
                <li class="${(i % 2 == 1) ? 'alternatecolor' : ''}">
                    <ul>
                        <li class="first_clumon">
                            <input name="" type="checkbox" value=""/>
                        </li>
                        <li class="email">${prodList.key}</li>
                        <li>${prodList.value}</li>
                    </ul>
                </li>
            </g:each>
        </ul>
        <div class="winterButton"><ul><li id="groceries_${weekIndex}" class="grocery">
            <g:each in="${groceryListForWeek}" var="grocery" status="i">
                <p>${grocery}</p>
            </g:each>
        </li></ul></div>
    </div>
    <div class="winter-week">
        <div class="winterButton" style="margin-top:52px;margin-left:64px">
            <input type="button" name="addItemBtn_${weekIndex}" id="addItemBtn_${weekIndex}" value="Add Item"/>
            <input name="addItemTxt_${weekIndex}" class="inpbox" id="addItemTxt_${weekIndex}" title="${g.message(code: 'toolTip.recipe.unit')}">
        </div>
    </div>
</div>
<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('#addItemBtn_${weekIndex}').click(function() {
            if (jQuery('#addItemTxt_${weekIndex}').val() != "") {
                var htmlString = '<p>' + jQuery('#addItemTxt_${weekIndex}').val() + '</p><input type="hidden" name="groceries${weekIndex}" value="' + jQuery('#addItemTxt_${weekIndex}').val() + '">'
                jQuery('#groceries_${weekIndex}').append(htmlString)
                jQuery('#addItemTxt_${weekIndex}').val('')
            }
            return false;
        })
        $("#addItemTxt_${weekIndex}").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
            width: 300,
            multiple: false,
            matchContains: true
        })
    })
</script>