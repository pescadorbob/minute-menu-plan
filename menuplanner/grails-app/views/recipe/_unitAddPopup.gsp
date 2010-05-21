
<div class="unitAddPopup" id="unitAddPopup" style="background-color:white;height:200px;border:1px solid black;left:26%;position:fixed;top:33%;display:none">
  <table cellspacing="0px" cellpadding="0px" width="100%">
  <tr>
    <td width="24">&nbsp;</td>
    <td width="24">&nbsp;</td>
    <td width="30">&nbsp;</td>
    <td width="117"><strong>Amount</strong></td>
    <td width="203"><strong>Unit</strong></td>
    <td><strong>Ingredient</strong></td>
  </tr>
</table>
  <input type="text" class="inpbox">
  <input type="text" class="inpbox">
  <g:select class="inpbox" id='popupOptionIngredientUnitIds' noSelection="['':'(No Unit)']" name="popupOptionIngredientUnitIds" from="${metricUnits}" optionKey="id" style="width:105px;"/>
</div>