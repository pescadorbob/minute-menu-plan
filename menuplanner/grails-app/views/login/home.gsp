<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Minute Menu Plan</title>
  <facebook:facebookConnectJavascript/>
</head>
<body>
<div id="content-wrapper" class="clearfix">
    <div id="left-panel-product">
        <div class="top-shadow">
            <label>&nbsp;</label>
        </div>
        <div class="leftbox clearfix">
            <div id="country-cateLogin">
                <ul>
                    <li>Create a meal plan in minutes</li>
                    <li>Choose from 1000s of recipes</li>
                    <li>Rate the recipes</li>
                    <li>Add your own recipes</li>
                    <li>Print your shopping list</li>
                    <li>Print your own recipes</li>
                    <li>Automatically calculate more or less mouth to feed</li>
                    <li>Print recipe by day or separately</li>
                    <li>Share recipe with your friends</li>
                    <li>Save $100s dollars by only buying recipe you need</li>
                    <li>Save time in kitchen</li>
                    <li>60-day money back</li>
                </ul>
            </div>
            <div id="category">
                <ul><li></li>
                    <li class="border"><strong>Recipes Categories Include:</strong></li>
                    <li>Appetizers | Beverages | Breads | Cakes | Candies|
                    Casserts |Eggs | Fish |
                    Itallian | Main Dishes Meats |Mexican</li>
                    <li>Appetizers | Beverages | Breads | Cakes | Candies|
                    Casserts |Eggs | Fish |
                    Itallian | Main Dishes Meats |Mexican</li>
                    <li>Appetizers | Beverages | Breads | Cakes | Candies|
                    Casserts |Eggs | Fish |
                    Itallian | Main Dishes Meats |Mexican</li>
                </ul>
            </div>
        </div>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
    <div id="maiddle-panel">
        <div class="top-shadow">
            <label>&nbsp;</label>
        </div>
        <div class="leftbox clearfix">
            <div id="content">
                <p>Use It Today - Plan and Print Immediately After Purchasing</p>
                <p>"Learn how this Minute Menu Plan will interest picky kids, relieve stressed moms, and make meals enjoyable again!"</p>
                <h2>"Who Else Wants To Make Every Dinner This Week A Relaxed, Enjoyable Event That Your Family Will Talk About For Weeks - With Almost Zero Effort, 1/2 The Expense Or The Dreaded, "What's For Dinner?" Question?</h2>
                <div class="clear clearfix">
                    <g:link controller="user" class="createUserLinkFT"  action="createUser"><div class="orderbtn"><h3>Order Now</h3> $5/month</div></g:link>
                    <div id="video-box"><img src="${resource(dir: 'images', file: 'video.png')}"/></div>
                    <g:link controller="user" action="createUser"><div class="orderbtn"><h3>Order Now</h3> $5/month</div></g:link>
                </div>
                <p>More and More Text Here...</p>
                <p>We just keep on telling them how good this product is, and displaying the 'Order Now' Button</p>
            </div>
        </div>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
    <div id="right-panel-landing">
        <div class="top-shadow">
            <label>&nbsp;</label>
        </div>
        <div class="leftbox clearfix">
            <div id="login">
                <g:form name="loginForm">
                    <g:hiddenField name="targetUri" value="${params.targetUri}"/>
                    <ul>
                        <li>Username : (Email Address)<div class="search-input">
                            <input name="email" type="text" class="inp  ${hasErrors(bean: loginCO, field: 'email', 'loginExc')}" value="${loginCO?.email}"/></div>
                            <g:hasErrors bean="${loginCO}" field="email">
                                <div id="displayEmailError" class="loginError">
                                    <g:renderErrors bean="${loginCO}" field="email"/>
                                </div>
                            </g:hasErrors>
                        </li>
                        <li>Password :<div class="search-input">
                            <input name="password" type="password" class="inp  ${hasErrors(bean: loginCO, field: 'password', 'loginExc')}" value=""/></div>
                            <g:hasErrors bean="${loginCO}" field="password">
                                <div id="displayPasswordError" class="loginError">
                                    <g:renderErrors bean="${loginCO}" field="password"/>
                                </div>
                            </g:hasErrors>

                            <g:if test="${flash.message}">
                                <div id="display_WrongPassword_DisabledUser_Error" class="loginError">
                                    ${flash.message}
                                </div>
                            </g:if>
                        </li>
                        <li>
                            <g:actionSubmit controller="login" action="login" value="Login" class="userLoginLink"/>
                        </li>
                        <li><g:link controller="login" action="forgotPassword">forgot password or username?</g:link></li>
                        <span style="color:#007AD8">Or login using Facebook</span> 
                      <fb:login-button ></fb:login-button>
                        <li class="border"><h2>TESTIMONIAL</h2></li>
                        <li>Minute Menu Plan removed the stress from my evenings and stopped the kids from asking whats for dinner.  We love to plan our meals together, and it takes only a few minutes.  Thanks Minute Menu Plan!
                            <label>--Donna, Hamburg, NY</label></li>
                        <li>Minute Menu Plan removed the stress from my evenings and stopped the kids from asking whats for dinner.  We love to plan our meals together, and it takes only a few minutes.  Thanks Minute Menu Plan!
                            <label>--Donna, Hamburg, NY</label></li>
                        <li>Minute Menu Plan removed the stress from my evenings and stopped the kids from asking whats for dinner.  We love to plan our meals together, and it takes only a few minutes.  Thanks Minute Menu Plan!
                            <label>--Donna, Hamburg, NY</label></li>
                    </ul>
                </g:form>
            </div>
        </div>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
</div>
</body>
</html>
