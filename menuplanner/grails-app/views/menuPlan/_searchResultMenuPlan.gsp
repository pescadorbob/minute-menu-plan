<%@ page import="com.mp.domain.Recipe" %>
<div class="ratingbox">
    <ul class="resultContainer">
        <g:each in="${itemList}" var="item" status="index">
            <li class="clearfix draggableItem" id="draggableSearchItem_${index + 1}">
                <ul>
                    <input type="hidden" name="menuItemId" value="${item.id}"/>
                    <g:if test="${item?.instanceOf(Recipe)}">
                        <li><h3 class="recipeName"><a href="${createLink(action: 'show', controller: 'recipe', id: item?.id)}">${item.name}</a></h3></li>
                    </g:if>
                    <g:else>
                        <li><h3 class="recipeName">${item.name}</h3></li>
                    </g:else>
                    <li>
                        <ul>
                            <g:if test="${item.instanceOf(Recipe)}">
                                <li><img class="imgbor" src="${createLink(controller: 'image', action: 'image', id: item?.image?.id)}"/></li>
                                <li>
                                    <ul>
                                        <li><g:render template="/rating/rating"/></li>
                                        <g:if test="${item?.totalTime}">
                                            <li>${item?.totalTime}</li>
                                        </g:if>
                                        <g:if test="${item?.difficulty}">
                                            <li>${item?.difficulty}</li>
                                        </g:if>
                                        <g:each in="${item?.ingredients?.ingredient}" var="product" status="i">
                                            <g:if test="${(i<3)}"><li>${product}</li></g:if>
                                        </g:each>
                                    </ul>
                                </li>
                            </g:if>
                            <g:else>
                                <li><img class="imgbor" src="${createLink(controller: 'image', action: 'imageByPath', params: [imagePath: '', noImage: 'no-img.gif'])}"/></li>
                            </g:else>

                        </ul>
                    </li>
                </ul>
            </li>
        </g:each>
    </ul>
</div>

<div class="paginateButtons">
    <util:remotePaginate
            controller="menuPlan"
            action="search"
            total="${itemTotal}"
            params="[query: query]"
            max="4"
            offset="${params.offset}"
            update="searchResult"
            maxsteps="5"/>
</div>
