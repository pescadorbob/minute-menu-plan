<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Minute Menu Plan</title>
    <meta name="layout" content="menu"/>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <form name="formCreateRecipe" action="${createLink(action:'testIngredientParserSubmit')}" >
        <textarea rows="20" cols="100" name="ingredients">${params.ingredients}</textarea>
        <input type="submit" value="Submit" /> 
    </form>
    <g:if test="${ingredientItemVOs}">
    <div style="clear:both">
        <table border="1">
            <tr>
                <td>
                    Amount
                </td>
                <td>
                    Measure
                </td>
                <td>
                    Ingredient
                </td>
                <td>
                    Preparation Method
                </td>
                <td>
                    Aisle
                </td>
            </tr>
            <g:each in="${ingredientItemVOs}" var="ingredientItemVo">
                <tr>
                    <td>
                        ${ingredientItemVo.amount}
                    </td>
                    <td>
                        ${ingredientItemVo.measure}
                    </td>
                    <td>
                        ${ingredientItemVo.ingredient}
                    </td>
                    <td>
                        ${ingredientItemVo.preparationMethod}
                    </td>
                    <td>
                        ${ingredientItemVo.aisle}
                    </td>
                </tr>
            </g:each>
        </table>
    </div>
    </g:if>
    
</div>
%{--var txt=jQuery('textarea').val()--}%
%{--txt=txt.replace(/[^\n,\w,\-,\\,\/,\$,\&,\d]\s*/g," ")--}%
%{--jQuery('textarea').val(txt);--}%
%{----}%
%{--jQuery('textarea')[0].selectionStart=jQuery('textarea').val().indexOf('Louisiana')--}%
%{--jQuery('textarea')[0].selectionEnd=jQuery('textarea').val().indexOf('Louisiana')+'Louisiana'.length--}%
%{--jQuery('textarea')[0].setSelectionRange(jQuery('textarea').val().indexOf('Louisiana'),jQuery('textarea').val().indexOf('Louisiana')+'Louisiana'.length)--}%
</body>
</html>