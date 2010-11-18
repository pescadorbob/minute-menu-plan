<tr class="addGrocery">
    <td width="30px;" valign="top">
        <img class="btnCross" src="${resource(dir: 'images', file: 'crossImg.png')}" hspace="2" vspace="2"/>
    </td>
    %{--<td width="250" class="groceryText">${grocery}</td>--}%
    <td width="250" class="groceryTextBox">
        <g:if test="${aisle}">
            <input type="text" name="week${weekIndex}.groceries.${aisle.id}" value="${grocery}"/>
        </g:if>
        <g:else>
            <input type="text" name="week${weekIndex}.groceries.0" value="${grocery}"/>
        </g:else>
    </td>
    %{--<td>--}%
        %{--<span class="linkEdit">Edit</span>--}%
    %{--</td>--}%
    <td></td>
</tr>
