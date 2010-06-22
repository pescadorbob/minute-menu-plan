<tr class="addProduct">
    <td width="30px;" valign="top">
        <img class="btnCross" src="${resource(dir: 'images', file: 'crossImg.png')}" hspace="2" vspace="2"/>
    </td>
    <td width="250" class="groceryText">${product}</td>
    <td width="250" class="groceryTextBox">
        <g:if test="${aisle}">
            <input type="text" name="week${weekIndex}.products.${aisle.id}" value="${product}" style="display:none;"/>
        </g:if>
        <g:else>
            <input type="text" name="week${weekIndex}.products.0" value="${product}" style="display:none;"/>
        </g:else>
    </td>
    <td>
        <span class="linkEdit">Edit</span>
    </td>
    <td></td>
</tr>
