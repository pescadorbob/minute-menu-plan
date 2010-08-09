<%@ page import="grails.util.Environment; grails.util.GrailsUtil" %>
<html>
<head>
    <meta name="layout" content="menu"/>
    <title>Minute Menu Plan</title>
</head>
<body>
<g:if test="${!(GrailsUtil.environment in ['test'])}">
    <facebook:facebookConnectJavascript/>
  </g:if>
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
                    <li>Automatically calculate more or less mouths to feed</li>
                    <li>Print recipes by day or separately</li>
                    <li>Share recipes with your friends</li>
                    <li>Save 100s of dollars by only buying ingredients you need</li>
                    <li>Save time in kitchen</li>
                    <li>60-day money back guaranty</li>
                </ul>
            </div>
            <div id="category">
                <ul><li></li>
                    <li class="border"><strong>Recipes Categories Include:</strong></li>
                    <li class="border"><strong>Cooking Techniques</strong></li>
                    <li>Baking | Blanching
                    | Boiling
                    | more...</li>
                    <li><strong>Recipe Types</strong></li>
                    <li>Appetizers
                    | Beverages
                    | Bread
                    | Breakfast
                    | more...</li>
                    <li><strong>Basic Food Groups</strong></li>
                    <li>Dairy Products & Eggs | more...
                    </li>

                    <li><strong>National and Ethnic Cuisines</strong></li>

                    <li><strong>Special Diets</strong></li>

                    <li><strong>Nutrition</strong></li>

                    <li><strong>Ingredients</strong></li>
                    <li><strong>More...</strong></li>

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
                    <div class="orderbtn"><h3>Order Now</h3> in beta</div>
                    <div id="video-box"><img src="${resource(dir: 'images', file: 'video.png')}"/></div>
                    <div class="orderbtn"><h3>Order Now</h3> in beta</div>
                </div>
                <h2>Find recipes from tons of categories including...</h2>
                <div>
                    <ul><li></li>
                        <li><strong>Cooking Techniques</strong></li>
                        <li>Baking | Blanching
                        | Boiling
                        | Braising
                        | Broiling
                        | Canning
                        | Creaming
                        | Deep-fry
                        | Fermenting
                        | Freezing
                        | Frying
                        | Grilling
                        | Outdoor Cooking
                        | Pickling
                        | Poaching
                        | Pressure Cooking
                        | Roasting
                        | Salting
                        | Sauteing
                        | Scalding
                        | Simmering
                        | Smoking
                        | Steaming
                        | Stir-frying
                        | Tempering</li>
                        <li><strong>Recipe Types</strong></li>
                        <li>Appetizers
                        | Beverages
                        | Bread
                        | Breakfast
                        | Confections
                        | Desserts
                        | Meats
                        | Pasta
                        | Rice
                        | Salads
                        | Sandwiches
                        | Sauces
                        | Seafood
                        | Side Dishes
                        | Soups
                        | Stews
                        | Stocks
                        | Sushi
                        | Vegan
                        | Vegetarian
                        | Easy Dinners
                        | Camping Recipes
                        | Holiday Recipes
                        | Recipes In Metric Measurements</li>
                        <li><strong>Basic Food Groups</strong></li>
                        <li>
                            | Dairy Products & Eggs
                            | Cereals & Grains
                            | Fruit
                            | Meat & Poultry
                            | Fats & Oils
                            | Nuts & Seeds
                            | Seafood
                            | Spices & Herbs
                            | Sweeteners
                            | Vegetables</li>

                        <li><strong>National and Ethnic Cuisines</strong></li>
                        <li>
                            | African Cuisines
                            | British Cuisines
                            | Caribbean Cuisines
                            | East Asian Cuisines
                            | European Cuisines
                            | Mediterranean Cuisines
                            | Middle Eastern Cuisines
                            | Pacific Cuisines
                            | North American Cuisines
                            | South American Cuisines
                            | South Asian Cuisines
                            | South-East Asian Cuisines</li>
                        <li><strong>Special Diets</strong></li>
                        <li>
                            Low-carb (Atkins, Zone, etc.)
                            | Gluten-Free
                            | Healthful Eating
                            | Halal
                            | High Protein
                            | Kosher
                            | Low-Calorie Diet
                            | Low-GI Diet (Diabetic)
                            | Vegan
                            | Vegetarian</li>
                        <li><strong>Nutrition</strong></li>
                        <li>
                            Antioxidants
                            | Calories
                            | Carbohydrates
                            | Fats
                            | Fiber
                            | Minerals
                            | Organic
                            | Protein
                            | Vitamins</li>
                        <li><strong>Ingredients</strong></li>
                        <li>
                            Baking Soda
                            | Beans & Other Legumes
                            | Bread
                            | Grain
                            | Chiles
                            | Chocolate
                            | Coffee
                            | Eggs
                            | Flour
                            | Herbs
                            | Jams & Jellies
                            | Milk
                            | Pasta
                            | Peppers
                            | Potatoes
                            | Rice
                            | Salt
                            | Spices
                            | Sugar</li>

                    </ul>
                </div>
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
                        <g:if test="${!(GrailsUtil.environment in ['test'])}">
                            <fb:login-button onlogin="loginToMenuPlanner()" autologoutlink="true">Connect</fb:login-button>
                        </g:if>
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
<g:if test="${!(GrailsUtil.environment in ['test'])}">
    %{--<facebook:facebookConnectJavascript/>--}%
    <script type="text/javascript">

      <g:if test="${!(params.fbLogout)}">
      setTimeout("loginToMenuPlanner()",2000);
      </g:if>
//      FB.ensure_init(function(){
//         alert("hello")
//      });
        function getUid() {
            return FB.Connect.get_loggedInUser()
        }

        function logoutFB() {
            FB.Connect.logout()
        }

//        function loginToMenuPlanner() {
//            var facebookUid = getUid()
            %{--self.location.href = "${createLink(controller:'login',action:'index')}?facebookUid=" + facebookUid--}%
//        }

        <g:if test="${params.fbLogout}">
        setTimeout("logoutFB()",2000);
        </g:if>
        
        function loginToMenuPlanner(){
          FB.Facebook.get_sessionState().waitUntilReady(function(session){
            if(session){
              self.location.href="${createLink(controller:'login',action:'index')}?facebookUid=" + getUid()
            }
          });
        }
    </script>
</g:if>
</body>
</html>
