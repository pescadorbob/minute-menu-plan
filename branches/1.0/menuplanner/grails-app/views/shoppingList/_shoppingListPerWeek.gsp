<%@ page import="com.mp.domain.Recipe" %>
<div class="clearfix">
    <div class="winter-week">
        <div class="winterButton"><strong>Week ${weekIndex?.toInteger() + 1}</strong><p></p></div>
        %{--<ul>--}%
            %{--<g:each in="${productListForWeek}" var="prodList" status="i">--}%
                %{--<li class="${(i % 2 == 1) ? 'alternatecolor' : ''}">--}%
                    %{--<ul>--}%
                        %{--<li class="first_clumon">--}%
                            %{--<input name="" type="checkbox" value=""/>--}%
                        %{--</li>--}%
                        %{--<li class="email">${prodList.key}</li>--}%
                        %{--<li>${prodList.value}</li>--}%
                    %{--</ul>--}%
                %{--</li>--}%
            %{--</g:each>--}%
        %{--</ul>--}%
        <span id="products_${weekIndex}" class="grocery">
                <table id="ProductTable_${weekIndex}" style="width:100%">
                    <g:if test="${productListForWeek}">
                        <g:each in="${productListForWeek}" var="product" status="i">
                            <g:render template="/shoppingList/productsWithParams" model="[product:product, weekIndex:weekIndex]"/>
                        </g:each>
                    </g:if>
                </table>
            </span>

        <div class="winterButton">
            <span id="groceries_${weekIndex}" class="grocery">
                <table id="groceryTable_${weekIndex}" style="width:100%">
                    <g:if test="${groceryListForWeek}">
                        <g:each in="${groceryListForWeek}" var="grocery" status="i">
                            <g:render template="/shoppingList/groceryWithParams" model="[grocery:grocery, weekIndex:weekIndex]"/>
                        </g:each>
                    </g:if>
                    <g:else>
                        <tr class="addGrocery">&nbsp;</tr>
                    </g:else>
                </table>
            </span>
        </div>
    </div>
    <div class="winter-week">
        <div class="winterButton" style="margin-top:52px;margin-left:64px">
            <input type="button" name="addItemBtn_${weekIndex}" id="addItemBtn_${weekIndex}" value="Add Item"/>
            <input name="addItemTxt_${weekIndex}" class="inpbox" id="addItemTxt_${weekIndex}" title="${g.message(code: 'toolTip.recipe.unit')}">
        </div>
    </div>
</div>
<script type="text/javascript">
    var htmlString = jQuery('#groceryTemplateRow>tbody').html()
    jQuery(document).ready(function() {
        bindEvents()
        jQuery('#addItemBtn_${weekIndex}').click(function() {
            if (jQuery('#addItemTxt_${weekIndex}').val() != "") {
                var grocery = jQuery('#addItemTxt_${weekIndex}').val()
                var groceryTB = '<input type="text" name="groceries${weekIndex}" value="' + jQuery('#addItemTxt_${weekIndex}').val() + '">'

                jQuery('#groceryTable_${weekIndex} tbody').append(htmlString)
                jQuery('#groceryTable_${weekIndex} .addGroceryNew .groceryText').html(grocery)
                jQuery('#groceryTable_${weekIndex} .addGroceryNew .groceryTextBox').html(groceryTB);

                jQuery('#groceryTable_${weekIndex} .addGroceryNew .groceryTextBox input[type="text"]').hide()

                jQuery('#groceryTable_${weekIndex} .addGroceryNew').attr('class', 'addGrocery')
                bindEvents()
                jQuery('#addItemTxt_${weekIndex}').val('')
            }
            return false;
        })

        $("#addItemTxt_${weekIndex}").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
            width: 300,
            multiple: false,
            matchContains: true
        })
        function bindEvents() {
            jQuery('.btnCross').click(function() {
                jQuery(this).parents('tr').remove();
            })
            jQuery('.linkEdit').click(function() {
                jQuery(('.groceryTextBox input[type="text"]'), jQuery(this).parent().parent())
                        .show().focus().unbind()
                        .blur(function() {
                    jQuery(('.groceryText'), jQuery(this).hide().parent().parent()).show().html(jQuery(this).val())
                })
                        .keydown(function(e) {
                    if (e.keyCode == 13) {
                        jQuery(this).blur()
                        return false;
                    }
                })
                jQuery(('.groceryText'), jQuery(this).parent().parent()).hide();
            })
        }
    })
</script>