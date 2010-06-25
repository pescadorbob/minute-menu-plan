<%@ page import="com.mp.domain.*" %>
<div class="clearfix">
    <div class="winter-week" id="shoppingWeek${weeklyShoppingList.weekIndex}">
        <div class="winterButton"><h3><strong>Week ${weeklyShoppingList.weekIndex + 1}</strong></h3></div>
        <g:each in="${weeklyShoppingList.aisles + null}" var="aisle">
            <strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(aisle ? aisle : 'Others')}</strong>
        <span id="products_${weeklyShoppingList.weekIndex}" class="grocery">
            <table id="ProductTable_${weeklyShoppingList.weekIndex}" style="width:100%">
                <g:each in="${weeklyShoppingList.getProductsByAisle(aisle)}" var="product" status="i">
                    <g:render template="/shoppingList/productsWithParams" model="[product:product, weekIndex:weeklyShoppingList.weekIndex, aisle: aisle]"/>
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
    <div class="winter-week">
    <div class="winterButton" style="margin-top:52px;margin-left:64px;">
      <g:select name="aisleList_${weekIndex}" from="${Aisle.list()}" optionKey="id" optionValue="${it?.name}" noSelection="['':'-Select Aisle-']"/>
      <input name="addItemTxt_${weekIndex}" class="inpbox" id="addItemTxt_${weekIndex}" title="${g.message(code: 'toolTip.recipe.unit')}">
      <input type="button" name="addItemBtn_${weekIndex}" id="addItemBtn_${weekIndex}" value="Add Item"/>
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
                var groceryTB = '<input type="text" name="week${weekIndex}.groceries.'+$("#aisleList_${weekIndex}").val() +'"value="' + jQuery('#addItemTxt_${weekIndex}').val() + '">'
              if($(".week${weekIndex}_groceries"+$("#aisleList_${weekIndex}").val()).length==0){
                var groceryHtmlString='<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+ $('option[value='+$("#aisleList_${weekIndex}").val()+']',$("#aisleList_${weekIndex}")).text() +'</strong><div class="winterButton"> <span class="grocery grocery_'+$("#aisleList_${weekIndex}").val()+'" id="groceries_${weekIndex}"><table class="week${weekIndex}_groceries'+ $("#aisleList_${weekIndex}").val() +'" style="width: 100%;" id="groceryTable_0"><tbody><tr class="addGrocery"/></tbody></table></span></div>'
                $("#shoppingWeek${weekIndex}").append(groceryHtmlString)
              }
              var newHtmlSting=$(htmlString).clone()
                $(".groceryText",$(newHtmlSting)).html(grocery)
                $(".groceryTextBox",$(newHtmlSting)).html(groceryTB)
//                $('input[type="text"]',$(newHtmlSting)).hide()
                $(newHtmlSting).addClass('addGrocery')
                $(".week${weekIndex}_groceries"+$("#aisleList_${weekIndex}").val()).append(newHtmlSting)
                $('#addItemTxt_${weekIndex}').val('')
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