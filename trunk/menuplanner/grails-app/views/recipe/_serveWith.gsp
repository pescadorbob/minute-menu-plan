<div id="panelServeWith" class="left-container2" style="display:none;">

    <div class="min-height-container">

        <ul class="ingredients">
            <li>
                <div>
                    <table class="mnuTableHeader" cellspacing="0px" cellpadding="0px">
                        <tr>
                            <td style="width:50px; text-align:left;"></td>
                            <td style="width:150px; text-align:left;"><strong>Recipe/Food Name</strong></td>
                            <td></td>
                        </tr>
                    </table>
                </div>
            </li>

            <li>
                <span id="ItemsAdded">

                    <!-- Show Items Here -->
                </span>
            </li>

            <li class="clr">
                <span id="AddItemToolBox" style="float:left; margin-top:10px; padding-left:30px;padding-top:10px;padding-bottom:10px; width:545px; border:1px solid #ddd;">
                    %{--<img id="btnAddItem" src="${resource(dir: 'images', file: 'plus-add.jpg')}" hspace="4" align="left" border="0" style="cursor:pointer; margin:0px;"/>--}%
                    <span id="itemToBeAdded" style="display:block; float:left;padding-left:42px;">
                        <mp:tagInput name="serveWithItems" controller="recipe" action="getMatchingItems"/>
                    </span>

                </span>
            </li>
        </ul>

    </div>
    <div style="clear:both;">
        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}"/>
    </div>
</div>
