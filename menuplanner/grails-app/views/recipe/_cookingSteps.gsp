<div id="panelCookingSteps" class="left-container2" style="display:none;">
    <div class="min-height-container">
        <ul class="ingredients">
            <li>
                <div class="showDirectionsHere">
                    <table id="tableDirections" cellspacing="0px" cellpadding="0px">
                        <tr id="tableDirectionsHeader" class="mnuTableHeader">
                            <td width="24">&nbsp;</td>
                            <td width="24">&nbsp;</td>
                            <td width="30">&nbsp;</td>
                            %{--<td style="width:40px; text-align:left;"><strong>Image</strong></td>--}%
                            <td><strong>Step Text</strong></td>
                            <g:each in="${recipeCO?.directions}">
                                <g:render template="directionRowWithParams" model="[direction:it]"/>
                            </g:each>
                        </tr>
                    </table>
                </div>
            </li>
            <li class="clr">
                <span id="AddDirectionToolBox" style="float:left; margin-top:10px; padding-left:35px;padding-top:10px;padding-bottom:10px;border:1px solid #ddd;width:485px;">
                    <img id="btnAddDirection" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>
                    <span id="directionToBeAdded" style="display:block; float:left;padding-left:10px;">
                        <g:textArea class="inputTextArea" id="optionDirections" name="optionDirections" value="" rows="5"/>
                    </span>
                </span>
            </li>
        </ul>
    </div>
    <div style="clear:both;">
        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
    </div>
</div>
