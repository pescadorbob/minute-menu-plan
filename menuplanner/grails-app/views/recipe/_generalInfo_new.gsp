<%@ page import="com.mp.domain.RecipeDifficulty" %>
<div class="tabPanel leftbox clearfix" id=panelGeneralInfo>
    <div class="formElement">
        <ul>
            <li>
                <ul>
                    <li>
                        <label>Name</label>
                        <span>
                            <g:textField class="inpbox ${hasErrors(bean:recipeCO,field:'name', 'errors')}" name="name" value="${recipeCO?.name}"/>
                        </span>
                    </li>
                    <li>
                        <label>Categories</label>
                        <span>
                            <mp:tagInput name="categoryIds" class="textareaInput" controller="recipe" action="getMatchingCategories"
                                    prePopulated="${categories}"/>
                            %{--<input name="" type="text" class="inpbox"/>--}%
                        </span>
                    </li>
                    <li>
                        <label>Prep Time</label>
                        <span>
                            <g:textField class="inpbox ${hasErrors(bean:recipeCO,field:'preparationTime', 'errors')}" name="preparationTime" value="${recipeCO?.preparationTime}" style="width:50px;"/>
                            <g:select class="inpbox" name="preparationUnitId" from="${timeUnits}" optionKey="id" value="${recipeCO?.preparationUnitId}"/>
                        </span>
                    </li>
                    <li>
                        <label>Cook Time</label>
                        <span>
                            <g:textField class="inpbox ${hasErrors(bean:recipeCO,field:'cookTime', 'errors')}" name="cookTime" value="${recipeCO?.cookTime}" style="width:50px;"/>
                            <g:select class="inpbox" name="cookUnitId" from="${timeUnits}" optionKey="id" value="${recipeCO?.cookUnitId}"/>
                        </span>
                    </li>

                    <li class="add-recipe-form-input-radio">
                        <label>Difficulty</label>
                        <span>
                            <g:radioGroup name="difficulty" values="${RecipeDifficulty.list()*.name()}" value="${recipeCO? (recipeCO?.difficulty): RecipeDifficulty.EASY.name()}" labels="${RecipeDifficulty.list()*.name}">
                                ${it.radio} <g:message code="${it.label}"/>
                            </g:radioGroup>
                        </span>
                    </li>
                    <li>
                        <label>Makes</label>
                        <span>
                            <g:textField class="inpbox ${hasErrors(bean:recipeCO,field:'makesServing', 'errors')}" name="makesServing" value="${recipeCO?.makesServing}" style="width:50px;"/>
                            Servings <br/>
                            <g:checkBox name="shareWithCommunity" value="${recipeCO?.shareWithCommunity}"/>
                            Share with community
                        </span>
                    </li>
                </ul>
            </li>
            <li class="browsebox">
               <table width="85%" align="right">
                   <tr>
                       <td>Image</td>
                       <td>
                           <input id="selectRecipeImage" name="selectRecipeImage" class="input3" type="file"/>
                       </td>
                       <td>
                           %{--<img src="${resource(dir: 'images', file: 'browse-img.gif')}" align="absmiddle"/>--}% 
                           <img id="removeRecipeImage" src="${resource(dir: 'images', file: '].gif')}" align="absmiddle" alt="Remove" style="cursor:pointer"/>
                       </td>
                   </tr>
               </table>
               </li>
            <li>
                <div id="myImageDiv">
                    <g:if test="${recipeCO?.selectRecipeImagePath}">
                        <img id='recipeImage' border='0' width='195' height="171" src="${g.createLink(controller: 'recipe', action: 'showImage', params: [selectRecipeImagePath: recipeCO?.selectRecipeImagePath])}"/>
                    </g:if>
                    <g:elseif test="${recipeCO?.id}">
                        <img id='recipeImage' border='0' width='195' height="171" src="${g.createLink(controller: 'recipe', action: 'showImage', id: recipeCO?.id)}"/>
                    </g:elseif>
                </div>
                <input type="hidden" name="selectRecipeImagePath" id="selectRecipeImagePath" value="${recipeCO?.selectRecipeImagePath}"/>
            </li>
        </ul>
    </div>
</div>