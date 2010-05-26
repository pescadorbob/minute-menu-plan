<%@ page import="com.mp.domain.SystemOfUnit" %>

<div class="unitAddPopup" id="unitAddPopup" style="background-color:#f2f2f2;min-height:200px;border:1px solid #EB7527;left:19%;padding:10px; position:fixed;top:33%; display:none;">
  <form name="saveNewUnit" action="${createLink(controller: 'unit', action: 'saveNewUnit')}">

    <table cellspacing="0px" cellpadding="4px" width="100%" style="font-size:11px;font-weight:normal;">
      <tr>
        <td width="160" colspan="2" height="20" style="background-color:#ddd; text-align:center;"><strong>Add New Unit</strong></td>

      </tr>
      <tr>
        <td width="160">New Unit Name</td>
        <td><input type="text" class="inpbox" name="unitName" id="unitName"></td>
      </tr><tr>
      <td width="160">Symbol</td>
      <td><input type="text" class="inpbox" name="unitSymbol" id="unitSymbol"></td>
    </tr><tr>
      <td width="160">Conversion Factor</td>
      <td><input type="text" class="inpbox" name="conversionFactor" id="conversionFactor"></td>
    </tr><tr>
      <td width="160">Ingredient</td>
      <td><g:select class="inpbox" noSelection="['':'(No Unit)']" name="unitId" from="${metricUnits}" optionKey="id" style="width:105px;"/></td>
    </tr>
      <tr>
        <td width="160">Unit System</td>
        <td><g:select class="inpbox" noSelection="['':'(No System)']" name="systemOfUnit" from="${SystemOfUnit.list()}" optionKey="id" optionValue="systemName" style="width:105px;"/></td>
      </tr>
      <tr>
        <td width="160" style="text-align:right;"><g:actionSubmit class="button" controller="unit" action="saveNewUnit" name="unitCreate" id="unitCreate" value="Create"/> &nbsp; </td>
        <td><input type="button" class="button" name="unitCancel" id="unitCancel" value="Cancel" onclick="$('#unitAddPopup').hide();
        return false;"/></td>
      </tr>

    </table>
  </form>
  <script type="text/javascript">
    $("#unitCreate").click(function() {
      $.post("${createLink(controller:'unit',action:'saveNewUnit')}", {"unitName":$("#unitName").val(),
        "unitSymbol":$("#unitSymbol").val(), "conversionFactor":$("#conversionFactor").val(),
        "unitId":$("#unitId").val(), "systemOfUnit":$("#systemOfUnit").val()}, function(data) {
        if(data.toString()!="error"){
          metricUnits.push($("#unitName").val())
          $("#optionIngredientUnitIds").append("<option value='" + data.toString() + "'>"+ $("#unitName").val() +"</option>")
          $("#combobox_optionIngredientUnitIds").unautocomplete()
          $("#combobox_optionIngredientUnitIds").autocomplete(metricUnits, {
            matchContains: true,
            minChars: 0,
            max:0,
            mustMatch:true
          });
          $("#combobox_optionIngredientUnitIds").val($("#unitName").val())
          $("#optionIngredientUnitIds").children().each(function(){
          if($(this).text()==$("#unitName").val()){
            $(this).attr('selected','selected')
            $("#unitAddPopup").hide()
          }
        })
          return false;
        }
      })
      return false;
    })
  </script>
</div>