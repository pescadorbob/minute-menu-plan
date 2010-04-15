<div class="browse-recipes-container-content2" style="height:440px;">
    <div class="browse-recipes-search-input">
        <input name="q" type="text" value=""/>
        <span style="display:none;"><input type="submit"/></span>
    </div>
    <span class="header2">
        You’ve Selected
    </span>
    <div class="close-container">

        <table id="tableOption" style="border:0px;">
            <tr id="nutrientsOption" style="display:none;">
                <td width="80px;">
                    <img id="nutrientsRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Calories:
                </td>
                <td>
                    <span id="nutrientsDisplay" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="difficultyOption" style="display:none;">
                <td width="80px;">
                    <img id="difficultyRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Difficulty:
                </td>
                <td>
                    <span id="difficultyDisplay" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="totalTimeOption" style="display:none;">
                <td width="80px;">
                    <img id="totalTimeRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
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
        <g:select name="qSelect" from="${categoryList}" onchange="submitSearchFormBySelect()"/>
        <div class="narrow-text2">
            <p>
                <span class="narrow-text-header">
                    Calories
                </span>
                <br/>
                <span id="category0to500" style="cursor:pointer;" onclick="submitSearchForm('nutrients', '[0 TO 500]');">0-500</span><br/>
                <span id="category501to1000" style="cursor:pointer;" onclick="submitSearchForm('nutrients', '[501 TO 1000]');">501-1000</span><br/>
                <span id="category1001to" style="cursor:pointer;" onclick="submitSearchForm('nutrients', '[1001 TO *]');">1000+</span>
            </p>
            <p>
                <span class="narrow-text-header">
                    Difficulty
                </span>
                <br/>
                <span id="difficultyEasy" style="cursor:pointer;" onclick="submitSearchForm('difficulty', 'EASY');">Easy</span><br/>
                <span id="difficultyMedium" style="cursor:pointer;" onclick="submitSearchForm('difficulty', 'MEDIUM');">Medium</span><br/>
                <span id="difficultyHard" style="cursor:pointer;" onclick="submitSearchForm('difficulty', 'HARD');">Hard</span>
            </p>
            <p>
                <span class="narrow-text-header">
                    Total Time
                </span>
                <br/>
                <span id="totalTime0to30" style="cursor:pointer;" onclick="submitSearchForm('cookingTime_value', '([1 TO 9] OR [11 TO 30])');">0-30 min.</span><br/>
                <span id="totalTime31to60" style="cursor:pointer;" onclick="submitSearchForm('cookingTime_value', '[31 TO 60]');">31-60 min.</span><br/>
                <span id="totalTime61to120" style="cursor:pointer;" onclick="submitSearchForm('cookingTime_value', '([61 TO 99] OR [100 TO 120])');">1-2 hrs.</span><br/>
                <span id="totalTime121to" style="cursor:pointer;" onclick="submitSearchForm('cookingTime_value', '[121 TO 180]');">2+ hrs.</span>
            </p>
        </div>
    </div>
    <div class="clr"></div>
</div>
<div id="searchParams">

</div>

<script type="text/javascript">
    jQuery(document).ready(function() {
        jQuery('#nutrientsRemove').click(function() {
            jQuery('#nutrientsOption').hide()
            jQuery('[value^=nutrients]').remove();
        })
        jQuery('#difficultyRemove').click(function() {
            jQuery('#difficultyOption').hide()
            jQuery('[value^=difficulty]').remove();
        })
        jQuery('#totalTimeRemove').click(function() {
            jQuery('#totalTimeOption').hide()
            jQuery('[value^=totalTime]').remove();
        })
    })
    function submitSearchForm(fieldName, fieldValue) {
        jQuery('[value^=' + fieldName + ']').remove();
        var html = '<input type="hidden" name="q" value="' + fieldName + ':' + fieldValue + '" />';

        jQuery('#searchParams').append(html);
        document.getElementById('searchForm').onsubmit();
        return false;
    }
    function submitSearchFormBySelect() {
        jQuery('[value^=category]').remove();
        var html = '<input type="hidden" name="q" value="category:"' + jQuery('[name=qSelect] :selected').text() + '" />';
        jQuery('#searchParams').append(html);
        document.getElementById('searchForm').onsubmit();
        return false;
    }
</script>