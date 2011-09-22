<div class="nutri-div nutrition">

                        <h3>Nutritional Information :</h3>
                        <p id="nutritional-information">
                            <b>Amount Per Serving</b>&nbsp; Calories:
                            <span style="float: none;" ><mp:facts nutrition="${nutrition}" fact="energKcal" /></span>
                            | Total Fat:
                            <span style="float: none;"><mp:facts nutrition="${nutrition}" fact="faSat,faMono,faPoly" />g</span>
                            | Cholesterol:
                            <span style="float: none;"><mp:facts nutrition="${nutrition}" fact="cholestrl" />mg</span>
                          </span><span class="usdaAttribute" style="float: right;"> powered by the USDA National Nutrient Database for Standard Reference </span>
                          <span class="less-more" style="float: left"><a href="#" onclick="showNutritionFacts();return false;">more </a></span>
                            </p>

                        <div style="display:none;" id="nutri-info">
                            <span class="less-more"><a href="#" onclick="hideNutritionFacts();return false;">less</a></span>
                            <div class="title">
                                Nutritional Information</div>
                            <div class="rectitle">
                                ${recipe?.name}</div>
                            <p>
                                Servings Per Recipe:
                                ${customServings ? customServings : recipe?.servings}</p>
                            <p>
                                <b>Amount Per Serving</b></p>
                            <p>
                                Calories:
                                <span class="calories"><mp:facts nutrition="${nutrition}" fact="energKcal" /></span></p>
                            <ul>
                                <li><b>Total Fat:&nbsp;</b>
                                    <span class="fat"><mp:facts nutrition="${nutrition}" fact="faSat,faMono,faPoly" />g</span></li>
                                <li><b>Cholesterol:&nbsp;</b>
                                    <span class="cholesterol"><mp:facts nutrition="${nutrition}" fact="cholestrl" />mg</span></li>
                                <li><b>Sodium:&nbsp;</b>
                                    <span class="sodium"><mp:facts nutrition="${nutrition}" fact="sodium" />mg</span></li>
                                <li><b>Total Carbs:&nbsp;</b>
                                    <span class="totalcarbs"><mp:facts nutrition="${nutrition}" fact="carbohydrt" />g</span></li>
                                <li>&nbsp;&nbsp;&nbsp;&nbsp;<b>Dietary Fiber:&nbsp;</b>
                                    <span class="dietaryfiber"><mp:facts nutrition="${nutrition}" fact="fiberTD" />g</span></li>
                                <li><b>Protein:&nbsp;</b>
                                    <span class="protein"><mp:facts nutrition="${nutrition}" fact="protein" />g</span></li>
                            </ul>
                            <p >
                            <a target="_new" rel="nofollow" href="http://www.nal.usda.gov/fnic/foodcomp/search/">
                              <p:image src='usdaicon80_70.jpg' hspace="2" vspace="2" style="float: right;" title="powered by the USDA National Nutrient Database for Standard Reference "/>
                              </a></p><span class="usdaAttribute" style="float: left; width:160px;">powered by the USDA National Nutrient Database for Standard Reference</span></div>

            </div>