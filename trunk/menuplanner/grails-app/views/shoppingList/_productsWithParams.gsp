<tr class="addProduct">
    <td width="30px;">
        <img class="btnCross" src="${resource(dir: 'images', file: 'crossImg.png')}" hspace="2" vspace="2"/>
    </td>
    <td class="groceryText">${product}</td>
    <td class="groceryTextBox">
        <input type="text" name="products${weekIndex}" value="${product}" style="display:none;"/>
    </td>
    <td>
        <span class="linkEdit">Edit</span>
    </td>
    <td></td>
</tr>
