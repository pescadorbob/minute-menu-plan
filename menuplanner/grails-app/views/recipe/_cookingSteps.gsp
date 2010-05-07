<%@ page import="com.mp.domain.RecipeDifficulty" %>
<div class="tabPanel leftbox clearfix" id=panelCookingSteps style="display:none;">
    <div class="formElement">
        <div class="showDirectionsHere">
            <table id="tableDirections" cellspacing="0px" cellpadding="0px" width="100%" class="menuplannerTab">
                <tr id="tableDirectionsHeader" class="mnuTableHeader">
                    <td width="24" height="30" valign="middle">&nbsp;</td>
                    <td width="24">&nbsp;</td>
                    <td width="30">&nbsp;</td>
                    <td><strong>Step Text</strong></td>
                    <g:each in="${recipeCO?.directions}">
                        <g:render template="/recipe/directionRowWithParams" model="[direction:it]"/>
                    </g:each>
                </tr>
            </table>
        </div>
        <ul class="ingredients">
            <li class="liForToolBoxes">
                <span id="AddDirectionToolBox" class="toolBoxes">
                    <img class="imagePointer" id="btnAddDirection" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0"/>
                    <span id="directionToBeAdded" style="display:block; float:left;padding-left:10px;">
                        <g:textArea class="inpbox" id="optionDirections" name="optionDirections" value="" rows="5" style="width:420px;"/>
                    </span>
                </span>
            </li>
        </ul>
    </div>
</div>
