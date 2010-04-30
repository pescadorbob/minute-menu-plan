<div class="tabPanel leftbox clearfix" id=panelServeWith style="display:none;">
    <div class="formElement">
        <div>
            <table class="menuplannerTab" cellspacing="0px" cellpadding="0px" width="100%">
                <tr class="mnuTableHeader">
                    <td style="width:75px; text-align:left;"></td>
                    <td style="width:150px; text-align:left;"><strong>Recipe/Food Name</strong></td>
                    <td></td>
                </tr>
            </table>
        </div>
        <ul class="ingredients">
            <li class="liForToolBoxes">
                <span id="AddItemToolBox" class="toolBoxes" style="width:545px;">
                    %{--<img id="btnAddItem" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>--}%
                    <span id="itemToBeAdded" style="display:block; float:left;padding-left:42px;">
                        <mp:tagInput name="serveWithItems" controller="recipe" action="getMatchingItems"/>
                    </span>

                </span>
            </li>
        </ul>
    </div>
</div>
