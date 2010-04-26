<div class="browse-recipes-container-content2">
    <div class="browse-recipes-search-input">
        <input name="q" type="text" value=""/>
        <span style="display:none;"><input type="submit"/></span>
    </div>
    <span class="header2">
        You’ve Selected
    </span>
    <div class="close-container">

        <table id="tableOption" style="border:0px;">
            <tr id="categoriesStringRow" style="display:none;">
                <td width="80px;"></td>
                <td>
                    <span id="categoriesStringDisplay" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="caloriesRow" style="display:none;">
                <td width="80px;">
                    <img onclick="removeSearchOption('calories')" id="caloriesRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Calories:
                </td>
                <td>
                    <span id="nutrientsDisplay" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="difficultyRow" style="display:none;">
                <td width="80px;">
                    <img onclick="removeSearchOption('difficulty')" id="difficultyRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Difficulty:
                </td>
                <td>
                    <span id="difficultyDisplay" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="prepTimeValueRow" style="display:none;">
                <td width="80px;">
                    <img onclick="removeSearchOption('prepTimeValue')" id="prepTimeRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Prep Time:
                </td>
                <td>
                    <span id="prepTimeDisplay" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="cookingTimeValueRow" style="display:none;">
                <td width="80px;">
                    <img onclick="removeSearchOption('cookingTimeValue')" id="cookingTimeRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Cook Time:
                </td>
                <td>
                    <span id="cookingTimeDisplay" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="totalTimeValueRow" style="display:none;">
                <td width="80px;">
                    <img onclick="removeSearchOption('totalTimeValue')" id="totalTimeRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Total Time:
                </td>
                <td>
                    <span id="totalTimeDisplay" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>

        </table>
        <div class="clr"></div>
    </div>

    <div class="narrow-container2">
        Narrow Your Search
    </div>
    <div>
        <g:select name="qSelect" from="${categoryList}" onchange="submitSearchFormBySelect()" noSelection="[' ': '(Select One)']"/>
        <div class="narrow-text2">
            <p>
                <span class="narrow-text-header">
                    Calories
                </span>
                <br/>
                <span id="category0to500" style="cursor:pointer;" onclick="submitSearchForm(this, 'calories', '[00000000000000 TO 000000000000dw]');">0-500</span><br/>
                <span id="category501to1000" style="cursor:pointer;" onclick="submitSearchForm(this, 'calories', '[000000000000dx TO 000000000000rs]');">501-1000</span><br/>
                <span id="category1001to" style="cursor:pointer;" onclick="submitSearchForm(this, 'calories', '[000000000000rt TO *]');">1000+</span>
            </p>
            <p>
                <span class="narrow-text-header">
                    Difficulty
                </span>
                <br/>
                <span id="difficultyEasy" style="cursor:pointer;" onclick="submitSearchForm(this, 'difficulty', 'EASY');">Easy</span><br/>
                <span id="difficultyMedium" style="cursor:pointer;" onclick="submitSearchForm(this, 'difficulty', 'MEDIUM');">Medium</span><br/>
                <span id="difficultyHard" style="cursor:pointer;" onclick="submitSearchForm(this, 'difficulty', 'HARD');">Hard</span>
            </p>
            <p>
                <span class="narrow-text-header">
                    Prep Time
                </span>
                <br/>
                <span id="prepTime0to30" style="cursor:pointer;" onclick="submitSearchForm(this, 'prepTimeValue', '[00000000000000 TO 0000000000000u]');">0-30 min.</span><br/>
                <span id="prepTime31to60" style="cursor:pointer;" onclick="submitSearchForm(this, 'prepTimeValue', '[0000000000000u TO 0000000000001o]');">31-60 min.</span><br/>
                <span id="prepTime61to120" style="cursor:pointer;" onclick="submitSearchForm(this, 'prepTimeValue', '[0000000000001o TO 0000000000003c]');">1-2 hrs.</span><br/>
                <span id="prepTime121to" style="cursor:pointer;" onclick="submitSearchForm(this, 'prepTimeValue', '[0000000000003d TO *]');">2+ hrs.</span>
            </p>
            <p>
                <span class="narrow-text-header">
                    Cook Time
                </span>
                <br/>
                <span id="cookTime0to30" style="cursor:pointer;" onclick="submitSearchForm(this, 'cookingTimeValue', '[00000000000000 TO 0000000000000u]');">0-30 min.</span><br/>
                <span id="cookTime31to60" style="cursor:pointer;" onclick="submitSearchForm(this, 'cookingTimeValue', '[0000000000000v TO 0000000000001o]');">31-60 min.</span><br/>
                <span id="cookTime61to120" style="cursor:pointer;" onclick="submitSearchForm(this, 'cookingTimeValue', '[0000000000001o TO 0000000000003c]');">1-2 hrs.</span><br/>
                <span id="cookTime121to" style="cursor:pointer;" onclick="submitSearchForm(this, 'cookingTimeValue', '[0000000000003d TO *]');">2+ hrs.</span>
            </p>
            <p>
                <span class="narrow-text-header">
                    Total Time
                </span>
                <br/>
                <span id="totalTime0to30" style="cursor:pointer;" onclick="submitSearchForm(this, 'totalTimeValue', '[00000000000000 TO 0000000000000u]');">0-30 min.</span><br/>
                <span id="totalTime31to60" style="cursor:pointer;" onclick="submitSearchForm(this, 'totalTimeValue', '[0000000000000u TO 0000000000001o]');">31-60 min.</span><br/>
                <span id="totalTime61to120" style="cursor:pointer;" onclick="submitSearchForm(this, 'totalTimeValue', '[0000000000001o TO 0000000000003c]');">1-2 hrs.</span><br/>
                <span id="totalTime121to" style="cursor:pointer;" onclick="submitSearchForm(this, 'totalTimeValue', '[0000000000003d TO *]');">2+ hrs.</span>
            </p>
        </div>
    </div>
    <div class="clr"></div>
</div>
<div id="searchParams">

</div>

<script type="text/javascript">

    function removeSearchOption(fieldName) {
        jQuery('#' + fieldName + 'Row').hide();
        jQuery('[value^=' + fieldName + ']').remove();
        document.getElementById('searchForm').onsubmit();
    }

    function submitSearchForm(element, fieldName, fieldValue) {
        jQuery('[value^=' + fieldName + ']').remove();
        var html = '<input type="hidden" name="q" value="' + fieldName + ':' + fieldValue + '" />';
        jQuery('#searchParams').append(html);
        jQuery('#' + fieldName + 'Row').show()
        if (element) {
            jQuery('#' + fieldName + 'Row td:eq(1)').html(jQuery(element).text())
        } else {
        }
        document.getElementById('searchForm').onsubmit();
        return false;
    }
    function submitSearchFormBySelect() {
        var fieldValue = '*' + jQuery('[name=qSelect] :selected').text() + '*';
        if (fieldValue == '*(Select One)*') {
            fieldValue = '*';
        }
        submitSearchForm(null, 'categoriesString', fieldValue);
    }
</script>