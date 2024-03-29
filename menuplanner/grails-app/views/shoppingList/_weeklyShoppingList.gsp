<%@ page import="com.mp.domain.party.Grocer; com.mp.domain.Aisle" %>
<div class="clearfix">
    <div class="winter-week" id="shoppingWeek${weeklyShoppingList.weekIndex}">
        <br/>
        <g:if test="${!shoppingList?.isWeeklyShoppingList}">
            <div class="winterButton"><h3><strong>Complete List</strong></h3></div>
          <div class="list-header receipt-header" >Enter the price and quantity purchased from receipts.
                    This helps others and yourself understand the cost of meals on the Minute Menu Plan </div>
          <div>
            <g:select name="grocerId" from="${Grocer.list()}" optionKey="id" value=""  />            
          </div>
        </g:if>
        <g:else>
            <div class="winterButton"><h3><strong>Week ${weeklyShoppingList.weekIndex + 1}</strong></h3></div>
        </g:else>
        <g:each in="${weeklyShoppingList.aisles + null}" var="aisle">
            <strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(aisle ? aisle : 'Others')}</strong>
            <span id="products_${weeklyShoppingList.weekIndex}" class="grocery">
                <table id="ProductTable_${weeklyShoppingList.weekIndex}" style="width:100%" >
                  <tr><th></th><th>                                        
                  </th></tr>
                  <tr><th></th><th class="receipt">
                    <div class="list-header list-description-head" >Description</div>
                    <div class="list-header receipt" style="width:58px;">Quantity</div>
                    <div class="list-header receipt" style="width:108px;">Unit</div>
                    <div class="list-header receipt" style="width:194px;">Ingredient</div>
                    <div class="list-header receipt" style="width:50px;">Price</div>
                  </th>   </tr>
                    <g:each in="${weeklyShoppingList.getProductsByAisle(aisle)}" var="product" status="i">
                        <g:render template="/shoppingList/productsWithParams"
                                model="[product:product, weekIndex:weeklyShoppingList.weekIndex, aisle: aisle]"/>
                    </g:each>
                </table>
            </span>

            <div class="winterButton">
                <span id="groceries_${weeklyShoppingList.weekIndex}" class="grocery grocery_${(aisle?.id ?: 'null')}">
                    <table id="groceryTable_${weeklyShoppingList.weekIndex}" style="width:100%" class="week${weeklyShoppingList.weekIndex}_groceries${(aisle?.id)}">
                        <g:if test="${weeklyShoppingList.getGroceriesByAisle(aisle)}">
                            <g:each in="${weeklyShoppingList.getGroceriesByAisle(aisle)}" var="grocery" status="i">
                                <g:render template="/shoppingList/groceryWithParams" model="[grocery:grocery, weekIndex:weeklyShoppingList.weekIndex, aisle: aisle]"/>
                            </g:each>
                        </g:if>
                        <g:else>
                            <tr class="addGrocery">&nbsp;</tr>
                        </g:else>
                    </table>
                </span>
            </div>
        </g:each>
    </div>
    <g:set var="weekIndex" value="${weeklyShoppingList.weekIndex}"/>
    %{--<div class="winter-week">--}%
        %{--<div class="addProductItem">--}%
            %{--<ul>--}%
                %{--<li><h3>Add items to Shopping List</h3></li>--}%
                %{--<li><g:select name="aisleList_${weekIndex}" from="${Aisle.getAislesForCurrentUser()}" optionKey="id" optionValue="${it?.name}" noSelection="['':'-Select Aisle-']"/></li>--}%
                %{--<li><input name="addItemTxt_${weekIndex}" class="inpbox" id="addItemTxt_${weekIndex}" title="${g.message(code: 'toolTip.recipe.unit')}"></li>--}%
                %{--<li><input type="button" name="addItemBtn_${weekIndex}" id="addItemBtn_${weekIndex}" value="Add Item"/></li>--}%
            %{--</ul></div>--}%
    %{--</div>--}%
</div>
<script type="text/javascript">
    var htmlString = jQuery('#groceryTemplateRow>tbody').html()
    jQuery(document).ready(function() {
        bindEvents()
        jQuery('#addItemBtn_${weekIndex}').click(function() {
            if (jQuery('#addItemTxt_${weekIndex}').val() != "") {
                var grocery = jQuery('#addItemTxt_${weekIndex}').val();
                var aisleIndex = jQuery("#aisleList_${weekIndex}").val();
                aisleIndex = aisleIndex ? aisleIndex : '0';
                var groceryTB = '<input type="text"  name="week${weekIndex}.groceries.' + aisleIndex + '"value="' + grocery + '">'
                if ($(".week${weekIndex}_groceries" + $("#aisleList_${weekIndex}").val()).length == 0) {
                    var groceryHtmlString = '<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + $('option[value=' + $("#aisleList_${weekIndex}").val() + ']', $("#aisleList_${weekIndex}")).text() + '</strong><div class="winterButton"> <span class="grocery grocery_' + $("#aisleList_${weekIndex}").val() + '" id="groceries_${weekIndex}"><table class="week${weekIndex}_groceries' + $("#aisleList_${weekIndex}").val() + '" style="width: 100%;" id="groceryTable_0"><tbody><tr class="addGrocery"/></tbody></table></span></div>'
                    $("#shoppingWeek${weekIndex}").append(groceryHtmlString)
                }
                var newHtmlSting = $(htmlString).clone()
                $(".groceryText", $(newHtmlSting)).html(grocery)
                $(".groceryTextBox", $(newHtmlSting)).html(groceryTB)
                //                $('input[type="text"]',$(newHtmlSting)).hide()
                $(newHtmlSting).addClass('addGrocery')
                $(".week${weekIndex}_groceries" + $("#aisleList_${weekIndex}").val()).append(newHtmlSting)
                var groceryFieldName= 'week${weekIndex}.groceries.' + aisleIndex;
                $('#addItemTxt_${weekIndex}').val('');
                bindEvents()
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
            //            jQuery('.linkEdit').click(function() {
            //                jQuery(('.groceryTextBox input[type="text"]'), jQuery(this).parent().parent())
            //                        .show().focus().unbind()
            //                        .blur(function() {
            //                    jQuery(('.groceryText'), jQuery(this).hide().parent().parent()).show().html(jQuery(this).val())
            //                })
            //                        .keydown(function(e) {
            //                    if (e.keyCode == 13) {
            //                        jQuery(this).blur()
            //                        return false;
            //                    }
            //                })
            //                jQuery(('.groceryText'), jQuery(this).parent().parent()).hide();
            //            })
        }
    })
</script>