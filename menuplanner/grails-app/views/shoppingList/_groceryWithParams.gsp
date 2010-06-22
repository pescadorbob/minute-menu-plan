<tr class="addGrocery">
   <td width="30px;" valign="top">
        <img class="btnCross" src="${resource(dir: 'images', file: 'crossImg.png')}" hspace="2" vspace="2"/>
    </td>
    <td width="250" class="groceryText">${grocery}</td>
    <td width="250" class="groceryTextBox">
        <input type="text" name="groceries${weekIndex}" value="${grocery}" style="display:none;"/>
    </td>
    <td>
        <span class="linkEdit">Edit</span>
    </td>
    <td></td>
</tr>
