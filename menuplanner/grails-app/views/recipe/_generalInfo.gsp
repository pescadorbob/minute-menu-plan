<%@ page import="com.mp.domain.RecipeDifficulty" %>
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
                    <li><label>Description</label></li>
                    <li style="height:140px;" >
                        <span>
                        <tinyMce:renderEditor type="simple" style="width:295px"  id="description" name="description" >${recipeCO?.description}</tinyMce:renderEditor>
                    </span></li>
                    <li style="height:100px;"><label>Categories</label>
                        <span>
                            <g:each in="${(0..2)}" var="index">
                                <p style="padding-bottom:10px;">
                                <select class="inpbox auto-resize" id="category_${index}" name="subCategoryIds" style="width:190px;">
                                    <optgroup label="--"><option value="">(Select One)</option></optgroup>
                                    <g:each in="${categories}" var="category">
                                        <optgroup label="${category}">
                                            <g:each in="${category.subCategories}" var="subCategory">
                                                <g:if test="${(recipeCO?.subCategoryIds) && recipeCO?.subCategoryIds?.toList()[index]==subCategory?.id}">
                                                    <option selected="true" value="${subCategory?.id}">${subCategory}</option>
                                                </g:if>
                                                <g:else>
                                                    <option rel="${category?.id}" value="${subCategory?.id}">${subCategory}</option>
                                                </g:else>
                                            </g:each>
                                        </optgroup>
                                    </g:each>
                                </select>
                                </p>
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
                            <g:textField class="inpboxSmall ${hasErrors(bean:recipeCO,field:'makesServing', 'errors')}"
                                    name="makesServing" value="${recipeCO?.makesServing}"/>
                            Servings
                        </span>
                    </li>
                    <li>
                        <label>Average Cost</label>
                        <span>
                            <g:textField class="inpboxSmall ${hasErrors(bean:recipeCO,field:'cost', 'errors')}"
                                    name="cost" value="${recipeCO?.cost}"/>
                            <br/><g:checkBox name="shareWithCommunity" value="${recipeCO?.shareWithCommunity}"/> Share with community
                        </span>
                    </li>
                </ul>
            </li>
            <li class="browsebox">
                <g:render template="/recipe/imageUpload" model="[selectorName:'selectRecipeImage']"/>
            </li>
            <li>
                <div class="scaleImageSizeUpperDiv" style="height:200px; width:200px; float:right; border:none;">
                    <div id="myImageDiv" class="scaleImageSize" style="height:200px; width:200px; float:right;">
                         <img id="recipeImage"  border="0" src="${g.createLink(controller: 'image', action: 'imageByPath', params: [imagePath: recipeCO?.selectRecipeImagePath, noImage: 'no-img.gif'])}"/>
                    </div>
                </div>
                <input type="hidden" name="selectRecipeImagePath" id="selectRecipeImagePath" value="${recipeCO?.selectRecipeImagePath}"/>
            </li>
        </ul>
    </div>

