<div class="clearfix" id=panelServeWith >
   <div class="recipeSubhead">Serve With:</div>
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
                    <span id="itemToBeAdded">
                        <input class="inpbox" id="serveWithItems1" name="serveWithItems"
                                value="${(recipeCO?.serveWithItems) ? recipeCO?.serveWithItems?.toList()?.first() : null}"/><br/><br/>
                        <input class="inpbox" id="serveWithItems2" name="serveWithItems"
                                value="${(recipeCO?.serveWithItems?.size() > 1) ? recipeCO?.serveWithItems?.toList()[1] : null}"/><br/><br/>
                        <input class="inpbox" id="serveWithItems3" name="serveWithItems"
                                value="${(recipeCO?.serveWithItems?.size() > 2) ? recipeCO?.serveWithItems?.toList()[2] : null}"/>
                    </span>

                </span>
            </li>
        </ul>
    </div>
</div>

<script type="text/javascript">
    $("#serveWithItems1").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
        width: 300,
        minChars: 3,
        multiple: false,
        selectFirst: false
    });

    $("#serveWithItems1").result(function(event, data, formatted) {
        jQuery(this).val(data[0]);
    })

    $("#serveWithItems2").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
        width: 300,
        minChars: 3,
        multiple: false,
        selectFirst: false
    });

    $("#serveWithItems2").result(function(event, data, formatted) {
        jQuery(this).val(data[0]);
    })

    $("#serveWithItems3").autocomplete("${createLink(action: 'getMatchingItems', controller: 'recipe')}", {
        width: 300,
        minChars: 3,
        multiple: false,
        selectFirst: false
    });

    $("#serveWithItems3").result(function(event, data, formatted) {
        jQuery(this).val(data[0]);
    })

</script>
