<div class="add-recipe-rgt-con">
    <div class="add-recipe-header">
        <div class="add-recipe-header1">
            <img src="${resource(dir: 'images', file: 'add-recipe-header-img1.jpg')}"/>
        </div>
        <div class="add-recipe-header3">Recipe Preview</div>
        <div class="add-recipe-header1">
            <img src="${resource(dir: 'images', file: 'add-recipe-header-img2.jpg')}"/>
        </div>
    </div>
    <div class="clr"></div>
    <div class="add-recipe-rgt1">
        <div>
            <img src="${resource(dir: 'images', file: 'add-rgt-img1.jpg')}"/>
        </div>
        <div class="add-recipe-rgt2">

               <div class="timePreview">
                    <span id="displayPrepTime">
                        %{--Prep - 30 min--}%
                    </span>
                    <span id="displayCookTime" >
                        %{--Cook - 30 min--}%
                    </span>
                </div>

            <div class="add-rgt-hdr">
                <img src="${resource(dir: 'images', file: 'add-rgt-hdr1.jpg')}" align="left"/>
                <div class="add-rgt-hdr1">
                    <span id="displayName">
                        %{--Beef Broccoil--}%
                    </span>
                </div>
                <img src="${resource(dir: 'images', file: 'add-rgt-hdr3.jpg')}" align="left"/>

                <div class="rating">Rating
                    <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                    <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                    <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                    <img src="${resource(dir: 'images', file: 'star1.jpg')}"/>
                </div>

            </div>

            <img id="showPreviewRecipeImage" src="${resource(dir: 'images', file: '')}" align="right" width="148" height="130"/>
            <p>
                <span id="displayDifficulty"></span>
            </p>
            <p>
                <span id="displayMakeServing"></span>
            </p>
            <br>
            <p>
                <span id="displayIngredients" style="font-weight:bold;">
                    %{--<strong>--}%
                        %{--1 lb. chicken, partially frozen--}%
                        %{--<br/>--}%
                        %{--3 lg. carrots, sliced diagonally--}%
                        %{--<br/>--}%
                        %{--2 c. string beans, fresh or--}%
                        %{--<br/>--}%
                        %{--frozen--}%
                        %{--<br/>--}%
                        %{--3/4 c. chicken broth--}%
                        %{--<br/>--}%
                        %{--1 can mushrooms--}%
                    %{--</strong>--}%
                </span>
            </p>
            <p>&nbsp;</p>
            <p class="clr">
            </p>
            <span id="displayDirections">
                %{--Slice meat diagonally with grain into thin strips.--}%
                %{--Pour terikate sauce over and marinate while preparing vegetables.--}%
                %{--<p>&nbsp;</p>--}%
                %{--<p>&nbsp;</p>--}%
                %{--<p>--}%
                    %{--Place oil in pan, stir fry beans until tender, add carrots, onions and then put meat in a little at a time. Stir meat and vegetables together. Add mushrooms and beef broth. Stir until hot. Serve over rice.--}%
                %{--</p>--}%

            </span>
            <p>&nbsp;</p>
            <p>
                <span id="displayCategory">
                </span>

                %{--Categories: <br/>--}%
                %{--<g:each in="${recipe?.items}" status="i" var="item">--}%
                    %{--<a href="#" class="serve-link">${item}</a><g:if test="${( i < (recipe?.items?.size()-1 ))}">,</g:if>--}%
                %{--</g:each>--}%


            <br><br>
            <p class="serve-link">
                <span id="displayServeWith">
                %{--Serve With--}%
                %{--<a class="serve-link" href="#">Rice</a>, <a class="serve-link" href="#">Won Tons</a>, <a class="serve-link" href="#">Egg Rolls</a>, <a class="serve-link" href="#">Egg Drop Soup</a></p>--}%
                </span>
            <p>&nbsp;</p><p>&nbsp;</p>

            <p>
                By - <span class="blue-text">Cheryl - Bakersfield, CA
            </span></p><p>&nbsp;</p><p>&nbsp;</p>

            <p class="font-10">
                <span id="showNutrients">
                    %{--Nutritional Facts per serving:--}%
                    %{--204 cal., 7g total fat, 4 mg chol. 533 mg sodium, 33 g. carbo., 2 g fiber, 6 g. pro.--}%
                </span>
            </p>

        </div>
        <div>
            <img src="${resource(dir: 'images', file: 'add-rgt-img3.jpg')}"/>
        </div>
    </div>

</div>
