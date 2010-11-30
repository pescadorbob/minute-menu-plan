<%@ page import="com.mp.tools.UserTools; com.mp.domain.LoginCredential" %>
<a class="fbconnect_login_button FBConnectButton FBConnectButton_Medium"
        onclick="window.open('${applicationUrl}', 'myWindow', 'width=1000, height=500, top=150, left=150');
        return false;" href="#">
    <span class="FBConnectButton_Text">
        <fb:intl>Connect with Facebook</fb:intl>
    </span>
</a>
<g:if test="${UserTools.currentUser}">
    <span id="mpFacebookConnectSuccess" style="display:none;">Your account has been connected with Facebook!
    <g:render template="/facebook/disconnectFromFacebook" model="[party:UserTools.currentUser.party]"/>
</g:if>
</span>
<script type="text/javascript">
    function facebookConnectSuccess() {
        $(".FBConnectButton").hide();
        $("#mpFacebookConnectSuccess").show();
    }
</script>
<facebook:facebookConnectJavascript/>
