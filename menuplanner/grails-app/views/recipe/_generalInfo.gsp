<%@ page import="com.mp.domain.RecipeDifficulty" %>
<div class="tabPanel leftbox clearfix" id=panelGeneralInfo>
    <table id="categoryTable" style="display:none;">
        <g:each in="${categories}">
            <tr>
                <td>${it?.id}</td>
                <td>${it?.name}</td>
            </tr>
        </g:each>
    </table>
    <div class="formElement">
        <ul>
            <li>
                <ul>
                    <li><label>Name</label>
                        <span><g:textField class="inpbox ${hasErrors(bean:recipeCO,field:'name', 'errors')}" name="name" value="${recipeCO?.name}"/></span></li>
                    <li><label>Categories</label>
                        <span>
                           <g:each in="${(0..2)}" var="index">
                            <select class="inpbox" name="subCategoryIds" style="width:190px;" class="auto-resize">
                                <optgroup label="--"><option value="">(Select One)</option></optgroup>
                                <g:each in="${categories}" var="category">
                                    <optgroup label="${category}">
                                        <g:each in="${category.subCategories}" var="subCategory">
                                            <g:if test="${(recipeCO?.subCategoryIds) && recipeCO?.subCategoryIds?.toList()[index]==subCategory?.id}">
                                                <option selected="true" value="${subCategory?.id}">${subCategory}</option>
                                            </g:if>
                                            <g:else>
                                                <option value="${subCategory?.id}">${subCategory}</option>
                                            </g:else>
                                        </g:each>
                                    </optgroup>
                                </g:each>
                            </select>
                            <br/>
                            <br/>
                           </g:each>
                        </span></li>
                    <li><label>Prep Time</label>
                        <span>
                            <g:textField class="inpboxSmall ${hasErrors(bean:recipeCO,field:'preparationTime', 'errors')}" name="preparationTime" value="${recipeCO?.preparationTime}"/>
                            <g:select class="inpbox" name="preparationUnitId" from="${timeUnits}" optionKey="id" value="${recipeCO?.preparationUnitId}"/>
                        </span></li>
                    <li><label>Cook Time</label>
                        <span>
                            <g:textField class="inpboxSmall ${hasErrors(bean:recipeCO,field:'cookTime', 'errors')}" name="cookTime" value="${recipeCO?.cookTime}"/>
                            <g:select class="inpbox" name="cookUnitId" from="${timeUnits}" optionKey="id" value="${recipeCO?.cookUnitId}"/>
                        </span></li>
                    <li class="add-recipe-form-input-radio">
                        <label>Difficulty</label>
                        <span>
                            <g:radioGroup name="difficulty" values="${RecipeDifficulty.list()*.name()}" value="${recipeCO? (recipeCO?.difficulty): RecipeDifficulty.EASY.name()}" labels="${RecipeDifficulty.list()*.name}">
                                ${it.radio} <g:message code="${it.label}"/>
                            </g:radioGroup>
                        </span></li>
                    <li>
                        <label>Makes</label>
                        <span>
                            <g:textField class="inpboxSmall ${hasErrors(bean:recipeCO,field:'makesServing', 'errors')}" name="makesServing" value="${recipeCO?.makesServing}"/>
                            Servings <br/><g:checkBox name="shareWithCommunity" value="${recipeCO?.shareWithCommunity}"/> Share with community
                        </span>
                    </li>
                </ul>
            </li>
            <li class="browsebox">
                <g:render template="/recipe/imageUpload" model="[selectorName:'selectRecipeImage']"/>
            </li>
            <li>
                <div id="myImageDiv" style="text-align:right;">
                    <img id="recipeImage" border="0" width="200" height="200" src="${g.createLink(controller: 'image', action: 'imageByPath', params: [imagePath: recipeCO?.selectRecipeImagePath, noImage: 'no-img.gif'])}"/>
                </div>
                <input type="hidden" name="selectRecipeImagePath" id="selectRecipeImagePath" value="${recipeCO?.selectRecipeImagePath}"/>
            </li>
        </ul>
    </div>
</div>
