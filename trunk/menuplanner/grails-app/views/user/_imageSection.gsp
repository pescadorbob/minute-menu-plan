<%@ page import="com.mp.domain.Affiliate; com.mp.domain.Permission; com.mp.domain.UserType" %>
<div id="leftpanel">
    <g:if test="${party?.subscriber}">
        <div class="scaleImageSizeUpperDiv" style="height:180px;width:180px;float:right;border:none;">
            <div id="photo" class="scaleImageSize" style="width:180px; height:180px;text-align:center;">
                <img id='userImage' border='0' src="${g.createLink(controller: 'image', action: 'imageByPath', params: [imagePath: userCO?.selectUserImagePath, noImage: 'no-img.gif'])}"/>
            </div>
        </div>
        <input type="hidden" name="selectUserImagePath" id="selectUserImagePath" value="${userCO?.selectUserImagePath}"/>
        <g:render template="/recipe/imageUpload" model="[selectorName:'selectUserImage']"/>
    </g:if>
    <ul>
        <g:if test="${userCO?.joiningDate}"><li>Member since ${userCO?.joiningDate?.format('MMMM yyyy')}</li></g:if><li></li><li></li>

        <g:render template="/user/userRoles" model="[userCO:userCO,party:party]"/>

        <div id="affiliatesList" style="display:none">
            <span style="margin-left:10px;"><g:select class="inpbox ${hasErrors(bean: userCO, field: 'affiliateId', 'errors')}" name="affiliateId" from="${Affiliate?.list()}" value="${userCO?.affiliateId}" optionKey="id" noSelection='["":"Select One"]'/></span>
        </div>
        <g:if test="${params.action != 'create'}">
            <li><h3>Contributed Recipes</h3></li>
            <g:render template="/user/contributedRecipes" model="[party:party]"/>
            <li><h3>Favorites</h3></li>
            <g:render template="/user/favoriteRecipes" model="[party:party]"/>
        </g:if>
    </ul>
</div>
