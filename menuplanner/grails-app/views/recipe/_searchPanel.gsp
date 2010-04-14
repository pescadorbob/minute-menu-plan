<div class="browse-recipes-container-content2" style="height:440px;">
    <div class="browse-recipes-search-input">
        <input name="q" type="text" value="${params.q}"/>
        <span style="display:none;"><input type="submit"/></span>
    </div>
    <span class="header2">
        You’ve Selected
    </span>
    <div class="close-container">

        <table id="tableOption" style="border:0px;">
            <tr id="caloriesOption" style="display:none;">
                <td width="80px;">
                    <img id="removeOptionCalories" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Calories:
                </td>
                <td>
                    <span id="displayCalories" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="difficultyOption" style="display:none;">
                <td width="80px;">
                    <img id="removeOptionDifficulty" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Difficulty:
                </td>
                <td>
                    <span id="displayDifficulty" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>
            <tr id="totalTimeOption" style="display:none;">
                <td width="80px;">
                    <img id="removeOptionTotalTime" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Total Time:
                </td>
                <td>
                    <span id="displayTotalTime" style="float:left; padding-left:5px;"></span>
                </td>
            </tr>

        </table>
        <div class="clr"></div>
    </div>

    <div class="narrow-container2">
        Narrow Your Search
    </div>
    <div>
        <g:select name="selectCategory" from="${categoryList}"/>
        <div class="narrow-text2">
            <p>
                <span class="narrow-text-header">
                    Calories
                </span>
                <br/>
                <span id="category0to500" style="cursor:pointer;">0-500</span><br/>
                <span id="category501to1000" style="cursor:pointer;">501-1000</span><br/>
                <span id="category1001to" style="cursor:pointer;">1000+</span>
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
                <span id="totalTime0to30" style="cursor:pointer;">0-30 min.</span><br/>
                <span id="totalTime31to60" style="cursor:pointer;">31-60 min.</span><br/>
                <span id="totalTime61to120" style="cursor:pointer;">1-2 hrs.</span>
                <span id="totalTime121to" style="cursor:pointer;">2+ hrs.</span>
            </p>
        </div>
    </div>
    <div class="clr"></div>
</div>
<div id="searchParams">

</div>

<script type="text/javascript">
    function submitSearchForm(fieldName, fieldValue){
        jQuery('[name='+fieldName+']').remove();
        var html = '<input type="hidden" name="'+fieldName+'" value="'+fieldValue+'" />';
        jQuery('#searchParams').append(html);
        document.getElementById('searchForm').onsubmit();return false;
    }

</script>