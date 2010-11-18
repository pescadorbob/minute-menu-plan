<%@ page import="com.mp.domain.RecipeDifficulty" %>
<div class="clearfix" id=panelCookingSteps >
   <div class="recipeSubhead" style="${hasErrors(bean: recipeCO, field: 'directions', 'color:red;')}">Cooking Steps</div>
    <div class="formElement">
        <div class="showDirectionsHere">
            <table id="tableDirections" cellspacing="0px" cellpadding="0px" width="100%" class="menuplannerTab">
                <tr id="tableDirectionsHeader" class="mnuTableHeader">
                    <td width="24" height="30" valign="middle">&nbsp;</td>
                    <td width="24">&nbsp;</td>
                    <td width="30">&nbsp;</td>
                    <td><strong>Step Text</strong></td>
                </tr>
            </table>
        </div>
        <ul class="ingredients">
            <li class="liForToolBoxes">
                <span id="AddDirectionToolBox" class="toolBoxes">
                    <span id="directionToBeAdded">
                        <tinyMce:renderEditor type="simple" id="directions" name="directions" style="width:500px" >${recipeCO?.directions?.join(' ')}</tinyMce:renderEditor>
                </span>
                </span>
            </li>
        </ul>
    </div>
</div>
