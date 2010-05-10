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
                    <input class="inpbox" id="serveWithItems1" name="serveWithItems"
                            value="${(recipeCO?.serveWithItems)? recipeCO?.serveWithItems?.toList()?.first() : null}"/><br/><br/>
                    <input class="inpbox" id="serveWithItems2" name="serveWithItems"
                            value="${(recipeCO?.serveWithItems?.size() > 1)? recipeCO?.serveWithItems?.toList()[1] : null}"/><br/><br/>
                    <input class="inpbox" id="serveWithItems3" name="serveWithItems"
                            value="${(recipeCO?.serveWithItems?.size() > 2)? recipeCO?.serveWithItems?.toList()[2] : null}"/>
                        %{--<mp:tagInput name="serveWithItems" controller="recipe" action="getMatchingItems"/>--}%
                    </span>

                </span>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    $("#serveWithItems1").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
            width: 300,
            multiple: false,
            matchContains: true,
        });

    $("#serveWithItems1").result(function(event, data, formatted) {
        jQuery(this).val(data[0]);
    })

    $("#serveWithItems2").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
            width: 300,
            multiple: false,
            matchContains: true,
        });

    $("#serveWithItems2").result(function(event, data, formatted) {
        jQuery(this).val(data[0]);
    })

    $("#serveWithItems3").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
            width: 300,
            multiple: false,
            matchContains: true,
        });

    $("#serveWithItems3").result(function(event, data, formatted) {
        jQuery(this).val(data[0]);
    })

</script>
