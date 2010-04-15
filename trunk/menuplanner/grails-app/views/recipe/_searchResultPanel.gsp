<div>
    <img src="${resource(dir: 'images', file: 'add-rgt-img4.jpg')}" width="770"/>
</div>
<div class="browse-recipes-container-content1">
    <div class="min-height-container2">
        <g:each in="${(recipeList)}" var="recipe" status="i">
            <div class="youhave-selected-container2 recipeThumb">
                <div class="browse-recipes-content-thmb">
                    <a href="">
                        <div class="selected-data">
                            <a href="${createLink(controller: 'recipe', action: 'show', id: recipe?.id)}">
                                ${recipe?.name}
                            </a>
                            <br/>
                            <div style="height:85px; width:85px;">
                                <g:if test="${recipe.image}">
                                    <img height="80" width="80" src="${createLink(controller: 'recipe', action: 'showImage', id: recipe?.id)}"/>
                                </g:if>
                            </div>
                            %{--<img src="${resource(dir: 'images', file: 'img1.jpg')}"/>--}%
                        </div>

                        <div class="selected-data2">
                            <div class="star-container">
                                <g:render template="/recipe/rateing"/>
                            </div>
                            <em>
                                ${recipe?.totalTime}<br/>
                                ${recipe?.difficulty}</em><br/>
                            %{--Round Cut Beef<br/>--}%
                            %{--Broccoli<br/>--}%
                            %{--Onions...--}%
                        </div>
                        <div class="clr"></div>
                    </a>
                </div>
            </div>
        </g:each>
        <div class="clr"></div>
        %{--<div class="paginateButtons" style="margin:5px; border:0px;">--}%
            %{--<g:paginate total="${recipeTotal}" max="15" offset="${params.offset?:0}" params="[q:params.q]"/>--}%
        %{--</div>--}%
    </div>
    <div>
        <img src="${resource(dir: 'images', file: 'left-container-img1.jpg')}" width="770" align="left">
    </div>
</div>
