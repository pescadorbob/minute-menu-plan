<%@ page import="com.mp.domain.party.Director; com.mp.domain.party.Director; com.mp.domain.Permission; com.mp.domain.PartyRoleType" %>
<div id="leftpanel">
    <pty:hasRole role="${PartyRoleType.Subscriber}" bean="${party}"> <h3>HELLO WORLD ${PartyRoleType.Subscriber}</h3> </pty:hasRole>
    <pty:hasRole role="${PartyRoleType.Subscriber}" bean="${party}">
        <div class="scaleImageSizeUpperDiv" style="height:180px;width:180px;float:right;border:none;">
            <div id="photo" class="scaleImageSize" style="width:180px; height:180px;text-align:center;">
                <img id='userImage' border='0' src="${g.createLink(controller: 'image', action: 'imageByPath', params: [imagePath: userCO?.selectUserImagePath, noImage: 'no-img.gif'])}"/>
            </div>
        </div>
        <input type="hidden" name="selectUserImagePath" id="selectUserImagePath" value="${userCO?.selectUserImagePath}"/>
        <g:render template="/recipe/imageUpload" model="[selectorName:'selectUserImage']"/>
    </pty:hasRole>
    <ul>
        <g:if test="${userCO?.joiningDate}"><li>Member since ${userCO?.joiningDate?.format('MMMM yyyy')}</li></g:if><li></li><li></li>

        <g:render template="/user/userRoles" model="[userCO:userCO,party:party]"/>

        <div id="directorsList" style="display:none">
            <span style="margin-left:10px;"><g:select  style="width:130px;" class="inpbox ${hasErrors(bean: userCO, field: 'directorId', 'errors')}" name="directorId" from="${Director?.list()}" value="${userCO?.directorId}" optionKey="id" noSelection='["":"Select Director"]'/></span>
        </div>
        <g:if test="${params.action != 'create'}">
            <li><h3>Contributed Recipes</h3></li>
            <g:render template="/user/contributedRecipes" model="[party:party]"/>
            <li><h3>Favorites</h3></li>
            <g:render template="/user/favoriteRecipes" model="[party:party]"/>
        </g:if>
    </ul>
</div>
