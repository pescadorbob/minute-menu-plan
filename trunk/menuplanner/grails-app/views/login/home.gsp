<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Minute Menu Plan</title>
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
                    <li>- Create a meal plan in minutes</li>
                    <li>- Choose from 1000s of recipes</li>
                    <li>- Rate the recipes</li>
                    <li>- Add your own recipes</li>
                    <li>- Print your shopping list</li>
                    <li>- Print your own recipes</li>
                    <li>- Automatically calculate more or less mouth to feed</li>
                    <li>- Print recipe by day or separately</li>
                    <li>- Share recipe with your friends</li>
                    <li>- Save $100s dollars by only buying recipe you need</li>
                    <li>- Save time in kitchen</li>
                    <li>- 60-day money back</li>
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
                <p>Hello. You have found an example page for Suckerfish Dropdowns.</p>
                <p>Under the hood you will find some nice structured HTML, a smattering of CSS and a teensy bit of JavaScript (that's just 12 lines of it). It's lightweight, it's accessible, it's cross-compatible.</p>
                <h2>"Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit..."
                "There is no one who loves pain itself, who seeks after it and wants to</h2>
                <div class="clear clearfix">
                    <g:link controller="user" action="create"><div class="orderbtn"><h3>Order Now</h3> $5/month</div></g:link>
                    <div id="video-box"><img src="${resource(dir: 'images', file: 'video.png')}"/></div>
                    <g:link controller="user" action="create"><div class="orderbtn"><h3>Order Now</h3> $5/month</div></g:link>
                </div>
                <p>Hello. You have found an example page for Suckerfish Dropdowns.</p>
                <p>Under the hood you will find some nice structured HTML, a smattering of CSS and a teensy bit of JavaScript (that's just 12 lines of it). It's lightweight, it's accessible, it's cross-compatible.
                .. Demo Hemburg NY</p>
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
                <g:uploadForm name="loginForm">
                    <g:hiddenField name="targetUri" value="${params.targetUri}"/>
                    <ul>
                        <li>Username : (Email Address)<div class="search-input">
                            <input name="email" type="text" class="inp  ${hasErrors(bean: loginCO, field: 'email', 'loginExc')}" value="${loginCO?.email}"/></div>
                            <g:hasErrors bean="${loginCO}" field="email" >
                                <div class="loginError">
                                    <g:renderErrors bean="${loginCO}" field="email"/>
                                </div>
                            </g:hasErrors>
                        </li>
                        <li>Password :<div class="search-input">
                            <input name="password" type="password" class="inp  ${hasErrors(bean: loginCO, field: 'password', 'loginExc')}" value=""/></div>
                            <g:hasErrors bean="${loginCO}"  field="password">
                                <div class="loginError">
                                    <g:renderErrors bean="${loginCO}" field="password"/>
                                </div>
                            </g:hasErrors>

                            <g:if test="${flash.message}">
                                <div class="loginError">
                                    ${flash.message}
                                </div>
                            </g:if>

                        </li>
                        <li>
                            <g:actionSubmit name="login" controller="login" action="login" value="Login" class=""/>
                        </li>
                        <li><a href="#">forgot password or username?</a></li>
                        <li class="border"><h2>TESTIMONIAL</h2></li>
                        <li>Hello. You have found an example page for Suckerfish Dropdowns. Under the hood you will find some nice structured HTML, a smattering of CSS and a teensy bit of JavaScript (that's just 12 lines of it). It's lightweight, it's accessible, it's cross-compatible.
                            <label>.. Demo Hemburg NY</label>
                        </li>
                        <li>Hello. You have found an example page for Suckerfish Dropdowns. Under the hood you will find some nice structured HTML, a smattering of CSS and a teensy bit of JavaScript (that's just 12 lines of it). It's lightweight, it's accessible, it's cross-compatible.
                            <label>.. Demo Hemburg NY</label>
                        </li>
                        <li>Hello. You have found an example page for Suckerfish Dropdowns. Under the hood you will find some nice structured HTML, a smattering of CSS and a teensy bit of JavaScript (that's just 12 lines of it). It's lightweight, it's accessible, it's cross-compatible.
                            <label>.. Demo Hemburg NY</label>
                        </li>
                    </ul>
                </g:uploadForm>
            </div>
        </div>
        <div class="bottom-shadow">
            <label>&nbsp;</label>
        </div>
    </div>
</div>
</body>
</html>
