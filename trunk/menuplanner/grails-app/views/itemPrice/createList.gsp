<%@ page import="com.mp.domain.*" %>
<%@ page import="com.mp.domain.pricing.ItemPrice" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="menu"/>
  <g:set var="entityName" value="${message(code: 'itemPrice.label', default: 'ItemPrice')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="body">
        <h3>Just Went Shopping?  Log Receipts Here!</h3>
        <p>Logging receipts here gives you and the community a better idea how to plan Menus in the future
        by calculating meal prices from real personal and regional prices of items on the menu!</p>
        <g:if test="${flash.message}">
          <div class="message">${flash.message}</div>
        </g:if>
        <div class="list">
          <g:form method="post" >
          <div class="clearfix" id=panelIngredients>
              <div class="formElement">
                <div class="prop">
                  <label for="grocerId">Grocer:</label><g:select name="grocerId" from="${com.mp.domain.party.Grocer.list()}"
                          optionKey="id" value="${com.mp.domain.party.Grocer.list()[0]?.id}" />
                </div>
                  <div class="showIngredientsHere">
                      <table id="unitTable" style="display:none;">
                          <g:each in="${metricUnits}">
                              <tr>
                                  <td>${it?.name}</td>
                                  <td>${it?.symbol}</td>
                              </tr>
                          </g:each>
                      </table>
                      <table id="tableIngredients" cellspacing="0px" cellpadding="0px" class="menuplannerTab receipt-header">
                          <tr id="tableIngredientsHeader" class="mnuTableHeader">
                            <td width="105"><strong>Ingredient</strong></td>
                            <td width="57" align="center"><strong>Qty.</strong></td>
                            <td width="107"><strong>Measurement</strong></td>
                            <td width="68" align="center"><strong>Price Paid</strong></td>
                          </tr>
                      </table>
                  </div>
                  <ul class="ingredients" id="ingredientGrid">
                      <g:render template="groceryItemRow" model="[display:'none']"/>
                        <g:each in="${(1..2)}" var="i">
                            <g:render template="groceryItemRow"/>
                        </g:each>
                  </ul>
              </div>
          </div>
          <div class="buttons">
              <span class="button"><g:actionSubmit class="save" action="saveList" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
              <span class="button"><g:actionSubmit class="cancel" action="cancel" value="${message(code: 'default.button.cancel.label', default: 'Cancel')}" /></span>
          </div>
          </g:form>


          <script type="text/javascript">
              var itemsJson = {
                  <g:each in="${Item.list()}" var="itemVar">
                  '${itemVar?.name?.replaceAll("'","\\\\'")}':['${itemVar?.suggestedAisle?.name?.replaceAll("'","\\\\'")}','${itemVar?.suggestedAisle?.id}'],
                  </g:each>
              }

              var metricUnits = []
              <g:each in="${metricUnits}" var="metricUnit">
              metricUnits.push(['${metricUnit}','${metricUnit.id}'])
              </g:each>
              //    metricUnits.push('Other...')

              //    var unitPopupCaller;

              function resetUnitAutocomplete() {
                  $(".iUnit").unautocomplete().autocomplete(metricUnits, {
                      matchContains: true,
                      minChars: 0,
                      max:0,
                      mustMatch:true
                  }).result(function(event, data, formatted) {
                      var unitId = data[1];
                      var currentUnit = jQuery(this).val()
                      $(this).next().val(unitId);
                      if (jQuery('#unitTable td:contains(' + currentUnit + ')')) {
                          if (jQuery('#unitTable td:contains(' + currentUnit + ')').eq(0).text() == currentUnit) {
                              var unitSymbol = jQuery('#unitTable td:contains(' + currentUnit + ')').eq(0).next().html()
                              $(this).next().next().val(unitSymbol)
                          }
                      }
                  })
              }
              function resetIngredients() {

                  resetUnitAutocomplete()
                  $(".iProduct").unautocomplete().autocomplete("${createLink(action: 'getMatchingProducts', controller: 'recipe')}", {
                      width : 300,
                      minChars: 3,
                      selectFirst: false
                  }).result(function(event, data, formatted) {
                      $(this).val(data[0]);
                      $(this).next().val(data[1])
                  })
                  showNewLineOnLastFocus();
                  bindEventUpDownIngredientArrow()
              }


              $(function() {
                  resetIngredients();
              })

              $("#ingredientGrid>li:eq(1) input:visible[value='']").tooltip({events: {
                  input: "focus,blur"
              },
                  effect:'slide'
              }).dynamic({ bottom: { direction: 'down', bounce: true } })

              $("#ingredientGrid>li:eq(1) input:visible[value='']").keydown(function() {
                  $(this).unbind('focus')
                  $(".tooltip").hide();
              })

          </script>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>
