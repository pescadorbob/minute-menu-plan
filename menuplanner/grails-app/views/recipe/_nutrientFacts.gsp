<%@ page import="com.mp.domain.RecipeDifficulty" %>
<div class="clearfix" id=panelNutritionFacts>
   <div class="recipeSubhead" style="${hasErrors(bean: recipeCO, field: 'nutrientQuantities', 'color:red;')}">Nutrition&nbsp;Facts:
   <g:form name="calculateNutrition" action="calculateNutrition" >
     <g:hiddenField name="recipeId" value="${recipeCO?.id}"/>
     <g:actionSubmit name="submitCalcNut" value="Calculate Nutrition"/>
     </g:form></div>
    <div class="formElement">
        <div style="background-color:#ddd;">
            <table cellspacing="0px" cellpadding="0px" width="100%" class="menuplannerTab">
                <tr class="mnuTableHeader">
                    <td style="width:50px; text-align:left;"></td>
                    <td style="width:80px; text-align:left;"><strong>Amount</strong></td>
                    <td style="width:200px; text-align:left;"><strong>Nutrients per Serving</strong></td>
                    <td></td>
                </tr>
                <g:each in="${nutrients}" var="nutrient" status="i">
                    <tr class="nutrientRow${i%2}">
                        <td>
                            %{--IMP for Preview--}%
                            <input type="hidden" value="${nutrient?.id}" name="nutrientIds"/>
                            <input type="hidden" value="${nutrient?.name}" name="nutrientNames"/>
                            <input type="hidden" value="${nutrient?.preferredUnit?.symbol}" name="nutrientUnitSymbols"/>
                        </td>
                        <td>
                            <input id="txt${nutrient?.name}" class="inpboxSmall" type="text" name="nutrientQuantities" value="${((recipeCO) ? recipeCO?.nutrientQuantities[i] : '')}"/>
                        </td>
                        <td>
                            ${nutrient?.preferredUnit?.symbol}  ${nutrient?.name}
                        </td>
                        <td></td>
                    </tr>
                </g:each>

            </table>
        </div>
    </div>
</div>
