<%@ page import="com.mp.domain.PartyRoleType; com.mp.tools.UserTools; org.codehaus.groovy.grails.commons.ConfigurationHolder" %>
<div id="viewmenu">

    <ul>
        <g:render template="${params.action}SubNavbarButtons"/>
    %{--<li><a href="#" class="btn">Weekly</a></li>--}%
    %{--<li class="noseprator"><a href="#">Shopping List</a></li>--}%
        <g:if test="${params.action!='create'}">
            <li class="noseprator1">
                <a href="#">
                    <img src="${resource(dir: 'images', file: 'actions.gif')}" border="0"/>
                    <ul>
                        <li id="printMonthlyMenuPlanBtn"><a href="">Print Monthly Menu Plan</a></li>
                        %{--<li><a href="">Print Weekly Menu Plan</a></li>--}%
                        <li id="printMenuPlanRecipesBtn"><a href="">Print Menu Plan Recipes</a></li>
                        <g:if test="${UserTools.currentUser && ((UserTools.currentUser.party.roles*.type in [PartyRoleType.Admin, PartyRoleType.SuperAdmin])|| (UserTools.currentUser.party == menuPlan.owner))}">
                            <li>
                                <g:link controller="shoppingList" class="createShoppingListLinkFT" action="generateShoppingList" id="${menuPlan?.id}">Create Shopping List</g:link>
                            </li>
                        </g:if>
                        <li>
                            <g:if test="${(party?.coach)}">
                                <mp:shareThis coachId="${party?.uniqueId}" shareUrl="${createLink(controller:'menuPlan',action:'show',id:menuPlan.id,absolute:true)}"/>
                            </g:if>
                            <g:else>
                                <mp:shareThis shareUrl="${createLink(controller:'menuPlan',action:'show',id:menuPlan.id,absolute:true)}"/>
                            </g:else>
                        </li>
                        %{--<li><a href="">Delete Menu Plan</a></li>--}%
                    </ul>
                </a>
            </li>
        </g:if>
    </ul>
    <script type="text/javascript">
        jQuery.each(jQuery('#viewmenu>ul>li.noseprator1'), function() {
            jQuery(this).mouseover(function() {
                jQuery(this).addClass("sfhover")
            })
            jQuery(this).mouseout(function() {
                jQuery(this).removeClass("sfhover")
            })
        });
    </script>
</div>
