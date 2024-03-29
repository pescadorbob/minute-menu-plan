<%@ page import="com.mp.tools.UserTools; com.mp.domain.LoginCredential;" %>

<div id="right-panel">
    <div id="search">Quick Recipe Search</div>
    <div id="search-container">
        <div id="search-bottom" class="clearfix">
            <!--  start search-left -->
            <div id="search-left">
                <div id="search-input">
                    <g:formRemote
                            name="searchForm"
                            url="[controller:'menuPlan', action:'search']"
                            update="searchResult">
                        <input name="query" type="text" class="inp" value=""/>
                        <span id="searchParams" style="display:none;"></span>
                        <span style="display:none;"><input type="submit"/></span>
                        <g:hiddenField name="searchByDomainName" value="Recipe"/>
                        <input type="hidden" name="openInNewWindow" value="${openInNewWindow ? 'true' : 'false'}"/>
                    </g:formRemote>
                </div>
                <div id="youhave">
                    <h2>You've Selected</h2>
                    <div>
                        <ul id="categoryList" style="cursor:pointer;font-size:12px; padding-left:4px;">
                        </ul>
                    </div>
                    <table id="tableOption" style="border:0px;">
                        <tr id="domainTypeRow" style="display:none;">
                            <td width="100px;">
                                <img onclick="noDomainSpecified('domainType', 'Recipe')" id="domainTypeRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                            </td>
                            <td>
                                <span id="domainTypeDisplay" class="searchOptionTexts"></span>
                            </td>
                        </tr>
                        <tr id="caloriesStringRow" style="display:none;">
                            <td width="100px;">
                                <img onclick="removeSearchOption('caloriesString')" id="caloriesRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                                Calories:
                            </td>
                            <td>
                                <span id="nutrientsDisplay" class="searchOptionTexts"></span>
                            </td>
                        </tr>
                        <tr id="favouriteForUsersStringRow" style="display:none;">
                            <td width="100px;">
                                <img onclick="removeSearchOption('favouriteForUsersString')" id="favouriteForUsersStringRemove" src="${resource(dir: 'images', file: 'delete-icon.jpg')}" align="top" style="cursor:pointer;"/>
                                Favourites:
                            </td>
                            <td>
                                <span id="favouriteForUsersDisplay" class="searchOptionTexts"></span>
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

                </div>
                <div id="searchResult">

                    <g:render template="/menuPlan/searchResultMenuPlan" model="[itemList:itemList, itemTotal:itemTotal, query:query,openInNewWindow:openInNewWindow]"/>

                </div>
            </div>
            <!--  end search-left  start search-right-->
            <div id="search-right">
                <h2>Narrow Your Search</h2>

                <div id="country-cate">
                    <ul>
                        <li>
                            <g:each in="${categories}" var="category">
                                <span class="pointer" onclick="showSubCategoryDivMenuPlan('${category?.id}');">< ${category}</span><br/>
                                <div id="category${category?.id}" class="categoryMenuPlan">
                                    <a href="javascript:void(0);" onclick="jQuery(this).parent().hide();" style="float:right;">Close</a>
                                    <g:each in="${category.subCategories.toList().findAll{it.id in subCategories*.id}}" var="${subCategory}">
                                        <span class="pointer" onclick="submitSearchForm(this, 'subCategoriesString', '${subCategory}');
                                        jQuery(this).parent().hide();">${subCategory}</span><br/>
                                    </g:each>
                                </div>
                            </g:each>
                        </li>
                        <li>
                            <span id="domainProduct" class="pointer" onclick="defineSearchDomainType(this, 'domainType', 'Item');">Include Products</span><br/>
                        </li>
                        <li>
                            <g:if test="${UserTools.currentUser}">
                                <span id="favourites" class="pointer" onclick="submitSearchForm(this, 'favouriteForUsersString', '*${UserTools.currentUser.id}*');">Show favourites</span><br/>
                            </g:if>
                        </li>
                        <li>
                            Calories
                            <br/>
                            <span id="category0to500" class="pointer" onclick="submitSearchForm(this, 'caloriesString', '[00000000000000 TO 000000000000dw]');">0-500</span><br/>
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
            <!--  end search-right, left-panel start right-panel -->
        </div>
    </div>
</div>
<g:render template="/menuPlan/searchJquery"/>
