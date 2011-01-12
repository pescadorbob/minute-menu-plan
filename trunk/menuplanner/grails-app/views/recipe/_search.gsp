<%@ page import="com.mp.tools.UserTools; com.mp.domain.LoginCredential;" %>
<div id="left-panel-product">
    <div class="top-shadow">
        <label>&nbsp;</label>
    </div>
    <div class="leftbox clearfix">
        <div id="search-input">
            <input name="query" type="text" class="inp" value=""/>
        </div>
        <h2>You have Selected</h2>
        <div>
            <ul id="categoryList" style="cursor:pointer;font-size:12px; padding-left:4px;">
            </ul>
        </div>
        <table id="tableOption" style="border:0px; font-size:12px;">
            <tr id="favouriteForUsersStringRow" style="display:none;">
                <td width="100px;">
                    <img onclick="removeSearchOption('favouriteForUsersString')" id="favouriteForUsersStringRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Show:
                </td>
                <td>
                    <span id="nutrientsDisplay" class="searchOptionTexts"></span>
                </td>
            </tr>
            <tr id="caloriesStringRow" style="display:none;">
                <td width="100px;">
                    <img onclick="removeSearchOption('caloriesString')" id="caloriesRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Calories:
                </td>
                <td>
                    <span id="caloriesDisplay" class="searchOptionTexts"></span>
                </td>
            </tr>
            <tr id="difficultyRow" style="display:none;">
                <td width="100px;">
                    <img onclick="removeSearchOption('difficulty')" id="difficultyRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Difficulty:
                </td>
                <td>
                    <span id="difficultyDisplay" class="searchOptionTexts"></span>
                </td>
            </tr>
            <tr id="prepTimeValueRow" style="display:none;">
                <td width="100px;">
                    <img onclick="removeSearchOption('prepTimeValue')" id="prepTimeRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Prep Time:
                </td>
                <td>
                    <span id="prepTimeDisplay" class="searchOptionTexts"></span>
                </td>
            </tr>
            <tr id="cookingTimeValueRow" style="display:none;">
                <td width="100px;">
                    <img onclick="removeSearchOption('cookingTimeValue')" id="cookingTimeRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Cook Time:
                </td>
                <td>
                    <span id="cookingTimeDisplay" class="searchOptionTexts"></span>
                </td>
            </tr>
            <tr id="totalTimeValueRow" style="display:none;">
                <td width="100px;">
                    <img onclick="removeSearchOption('totalTimeValue')" id="totalTimeRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                    Total Time:
                </td>
                <td>
                    <span id="totalTimeDisplay" class="searchOptionTexts"></span>
                </td>
            </tr>
        </table>
        <br/>
        <h3>Narrow Your Search</h3>
        <div id="country-cate">
            <ul>
                <li>
                    <g:each in="${categories}" var="category">
                        <span class="pointer" onclick="showSubCategoryDiv('${category?.id}');">${category} &gt;</span><br/>
                        <div id="category${category?.id}" class="categoryMenu">
                            <a href="javascript:void(0);" onclick="jQuery(this).parent().hide();" style="float:right;">Close</a>
                            <g:each in="${category.subCategories.findAll{it.id in subCategories*.id}}" var="${subCategory}">
                                <span class="pointer" onclick="submitSearchForm(this, 'subCategoriesString', '${subCategory}');
                                jQuery(this).parent().hide();">${subCategory}</span><br/>
                            </g:each>
                        </div>
                    </g:each>
                </li>
                <li>
                    <span id="favouriteForUsersString" class="pointer" onclick="submitSearchForm(this, 'favouriteForUsersString', '*${UserTools.currentUser?.id}*');">Favourites</span>
                </li>
                <li>
                    Calories
                    <br/>
                    <span class="pointer" id="category0to500" onclick="submitSearchForm(this, 'caloriesString', '[00000000000000 TO 000000000000dw]');">0-500</span><br/>
                    <span id="category501to1000" class="pointer" onclick="submitSearchForm(this, 'caloriesString', '[000000000000dx TO 000000000000rs]');">501-1000</span><br/>
                    <span id="category1001to" class="pointer" onclick="submitSearchForm(this, 'caloriesString', '[000000000000rt TO *]');">1000+</span>
                </li>
                <li>
                    Difficulty
                    <br/>
                    <span id="difficultyEasy" class="pointer" onclick="submitSearchForm(this, 'difficulty', 'EASY');">Easy</span><br/>
                    <span id="difficultyMedium" class="pointer" onclick="submitSearchForm(this, 'difficulty', 'MEDIUM');">Medium</span><br/>
                    <span id="difficultyHard" class="pointer" onclick="submitSearchForm(this, 'difficulty', 'HARD');">Hard</span>
                </li>
                <li>
                    Prep Time
                    <br/>
                    <span id="prepTime0to30" class="pointer" onclick="submitSearchForm(this, 'prepTimeValue', '[00000000000000 TO 0000000000000u]');">0-30 min.</span><br/>
                    <span id="prepTime31to60" class="pointer" onclick="submitSearchForm(this, 'prepTimeValue', '[0000000000000u TO 0000000000001o]');">31-60 min.</span><br/>
                    <span id="prepTime61to120" class="pointer" onclick="submitSearchForm(this, 'prepTimeValue', '[0000000000001o TO 0000000000003c]');">1-2 hrs.</span><br/>
                    <span id="prepTime121to" class="pointer" onclick="submitSearchForm(this, 'prepTimeValue', '[0000000000003d TO *]');">2+ hrs.</span>
                </li>
                <li>
                    Cook Time
                    <br/>
                    <span id="cookTime0to30" class="pointer" onclick="submitSearchForm(this, 'cookingTimeValue', '[00000000000000 TO 0000000000000u]');">0-30 min.</span><br/>
                    <span id="cookTime31to60" class="pointer" onclick="submitSearchForm(this, 'cookingTimeValue', '[0000000000000v TO 0000000000001o]');">31-60 min.</span><br/>
                    <span id="cookTime61to120" class="pointer" onclick="submitSearchForm(this, 'cookingTimeValue', '[0000000000001o TO 0000000000003c]');">1-2 hrs.</span><br/>
                    <span id="cookTime121to" class="pointer" onclick="submitSearchForm(this, 'cookingTimeValue', '[0000000000003d TO *]');">2+ hrs.</span>
                </li>
                <li>
                    Total Time
                    <br/>
                    <span id="totalTime0to30" class="pointer" onclick="submitSearchForm(this, 'totalTimeValue', '[00000000000000 TO 0000000000000u]');">0-30 min.</span><br/>
                    <span id="totalTime31to60" class="pointer" onclick="submitSearchForm(this, 'totalTimeValue', '[0000000000000u TO 0000000000001o]');">31-60 min.</span><br/>
                    <span id="totalTime61to120" class="pointer" onclick="submitSearchForm(this, 'totalTimeValue', '[0000000000001o TO 0000000000003c]');">1-2 hrs.</span><br/>
                    <span id="totalTime121to" class="pointer" onclick="submitSearchForm(this, 'totalTimeValue', '[0000000000003d TO *]');">2+ hrs.</span>
                </li>
            </ul>
        </div>
    </div>
    <div class="bottom-shadow">
        <label>&nbsp;</label>
    </div>
</div>
<g:render template="/menuPlan/searchJquery"/>
