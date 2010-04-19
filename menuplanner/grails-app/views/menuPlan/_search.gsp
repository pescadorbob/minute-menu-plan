<div id="right-panel">
    <div id="search">Quick Recipe Search</div>
    <div id="search-container">
        <div id="search-bottom" class="clearfix">
            <!--  start search-left -->
            <div id="search-left">
                <div id="search-input">
                    <input type="text" class="inp" value="Beef" onFocus="if (this.value == 'Beef')this.value = '';" onblur="if (this.value == '')this.value = 'Beef';"/>

                </div>
                <div id="youhave">
                    <h2>You've Selected</h2>
                    1000+ Calories, Medium Difficulty,
                    31-60 Minutes Total Time
                </div>
                <div id="ratingbox">

                    <ul>
                        <li>
                            <div class="ratingbox-left"><h3>Beef&nbsp;with&nbsp;Broccoli</h3>
                                <img src="${resource(dir: 'images', file: 'vegetarian.gif')}" class="imgbor"/></div>
                            <div class="ratingbox-right">
                                <div class="star-container">
                                    <img src="${resource(dir: 'images', file: 'star.gif')}" width="14" height="14"/>
                                    <img src="${resource(dir: 'images', file: 'star.gif')}" width="14" height="14"/>
                                    <img src="${resource(dir: 'images', file: 'star.gif')}" width="14" height="14"/>
                                    <img src="${resource(dir: 'images', file: 'star-half.gif')}" width="14" height="14"/>
                                    <img src="${resource(dir: 'images', file: 'star-full.gif')}" width="14" height="14"/>
                                </div>

                                30 Min.<br/>
                                Easy<br/>
                                Round Cut Beef<br/>
                                Broccoli<br/>
                                Onions...</div>

                        </li>
                        <li>
                            <div class="ratingbox-left"><h3>Beef&nbsp;with&nbsp;Broccoli</h3>
                                <img src="${resource(dir: 'images', file: 'vegetarian.gif')}" class="imgbor"/></div>
                            <div class="ratingbox-right">
                                <div class="star-container"><img src="images/star.gif" width="14" height="14"/><img src="images/star.gif" width="14" height="14"/><img src="images/star.gif" width="14" height="14"/><img src="images/star-half.gif" alt="" width="14" height="14"/> <img src="images/star-full.gif" alt="" width="14" height="14"/></div>

                                30 Min.<br/>
                                Easy<br/>
                                Round Cut Beef<br/>
                                Broccoli<br/>
                                Onions...</div>

                        </li>
                        <li>
                            <div class="ratingbox-left"><h3>Beef&nbsp;with&nbsp;Broccoli</h3>
                                <img src="${resource(dir: 'images', file: 'vegetarian.gif')}" class="imgbor"/></div>
                            <div class="ratingbox-right">
                                <div class="star-container"><img src="images/star.gif" width="14" height="14"/><img src="images/star.gif" width="14" height="14"/><img src="images/star.gif" width="14" height="14"/><img src="images/star-half.gif" alt="" width="14" height="14"/> <img src="images/star-full.gif" alt="" width="14" height="14"/></div>

                                30 Min.<br/>
                                Easy<br/>
                                Round Cut Beef<br/>
                                Broccoli<br/>
                                Onions...</div>

                        </li><li>
                        <div class="ratingbox-left"><h3>Beef&nbsp;with&nbsp;Broccoli</h3>
                            <img src="${resource(dir: 'images', file: 'vegetarian.gif')}" class="imgbor"/></div>
                        <div class="ratingbox-right">
                            <div class="star-container"><img src="images/star.gif" width="14" height="14"/><img src="images/star.gif" width="14" height="14"/><img src="images/star.gif" width="14" height="14"/><img src="images/star-half.gif" alt="" width="14" height="14"/> <img src="images/star-full.gif" alt="" width="14" height="14"/></div>

                            30 Min.<br/>
                            Easy<br/>
                            Round Cut Beef<br/>
                            Broccoli<br/>
                            Onions...</div>

                    </li>
                    </ul>
                </div>

                <!-- paging -->
                <div id="pagination">
                    <ul>
                        <li><img src="${resource(dir: 'images', file: 'first.gif')}" class="arrowbor"/></li>
                        <li><img src="${resource(dir: 'images', file: 'prev.gif')}" class="arrowbor"/></li><li><a href="_search.gsp#">1</a></li>
                        <li><a href="_search.gsp#">2</a></li>
                        <li><a href="_search.gsp#">3</a></li>
                        <li><a href="_search.gsp#">4</a></li>
                        <li><a href="_search.gsp#">5</a></li>
                        <li><a href="_search.gsp#"><img src="${resource(dir: 'images', file: 'next.gif')}" border="0" class="arrowbor"/></a></li>
                        <li><a href="_search.gsp#"><img src="${resource(dir: 'images', file: 'last.gif')}" border="0" class="arrowbor"/></a></li>
                    </ul>
                </div>
                <!--end paging-->

            </div>
            <!--  end search-left  start search-right-->
            <div id="search-right"><h2>Narrow
            Your Search</h2>

                <div id="country-cate">
                    <ul>
                        <li><img src="${resource(dir: 'images', file: 'italian.gif')}"/></li>
                        <li>Calories
                            <a href="E">0-500</a>
                            <a href="_search.gsp#"><strong>501-1000</strong></a>
                            <a href="_search.gsp#">1000+</a></li>
                        <li>Difficulty
                            <a href="E">Easy</a>
                            <a href="_search.gsp#"><strong>Medium</strong></a>
                            <a href="_search.gsp#">Difficult</a></li>
                        <li>Total Time
                            <a href="E">0-30 min.</a>
                            <a href="_search.gsp#"><strong>31-60 min.</strong></a>
                            <a href="_search.gsp#">1-2 hrs.</a>
                            <a href="_search.gsp#">2+ hrs.</a></li>
                    </ul>
                </div>

            </div>
            <!--  end search-right, left-panel start right-panel -->
        </div>
    </div>
</div>
