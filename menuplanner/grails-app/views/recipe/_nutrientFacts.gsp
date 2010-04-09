<div id="panelNutritionFacts" class="left-container2" style="display:none;">
    <div class="min-height-container">
        <ul class="ingredients">
            <li>
                <div style="background-color:#ddd; padding:5px;">
                    <table style="border:0px;">
                        <tr>
                            <td style="width:50px; text-align:left;"></td>
                            <td style="width:53px; text-align:left;"><strong>Amount</strong></td>
                            <td style="width:150px; text-align:left;"><strong>Nutrients per Serving</strong></td>
                            <td></td>
                        </tr>
                    </table>
                </div>
            </li>
            <li>
                <g:each in="${nutrients}" var="nutrient" status="i">

                    <div class="addrecipe${(i % 2 == 0) ? '' : '2'}">
                        <input type="hidden" value="${nutrient?.id}" name="nutrientIds"/>
                        <input class="input2" type="text" name="nutrientQuantities" id="text1" value="${((recipeCO) ? recipeCO.nutrientQuantities[i] : '')}"/>
                        ${nutrient?.preferredUnit?.symbol}  ${nutrient?.name}
                        %{--IMP for Preview--}%
                        <input type="hidden" value="${nutrient?.name}" name="nutrientNames"/>
                        <input type="hidden" value="${nutrient?.preferredUnit?.symbol}" name="nutrientUnitSymbols"/>
                    </div>

                </g:each>
            </li>
        </ul>
    </div>
    <div style="clear:both;">
        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
    </div>
</div>
