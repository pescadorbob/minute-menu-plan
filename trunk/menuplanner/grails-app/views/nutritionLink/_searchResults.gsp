<%@ page import="com.mp.domain.Unit" %>
<%
  def deleteParams = [:]
    params.each {key,value->
    if(key.indexOf('_')!=0) deleteParams[key] = value
  }
%>
<g:form name="matchForm">
    <g:actionSubmit id="btnSubmit" action="match" name="submit" value="Match-it"/>
    <g:each in="${pagingParamsIng}" var="hidPar">
      <g:if test="${hidPar.key != 'quantity' && hidPar.key != 'factor' && hidPar.key != 'preparationMethods'}">
        <g:hiddenField name="${hidPar.key}" value="${hidPar.value}"/>
      </g:if>
    </g:each>
    Quantity:<g:textField name="quantity" value="${quantity.toString()}" size="4"/>
    <g:select name="unit" noSelection="${['null':'Select Standard Unit...']}"
            from="${standardUnits}"
            value="${unit}"
            optionKey="id"/>
    <input class="inpbox iPreparationMethod" name="hiddenIngredientPreparationMethodNames" value="${hiddenIngredientPreparationMethodNames?.encodeAsHTML()}" style="width:90px;"/>
    <input type="hidden" value="${hiddenIngredientPreparationMethodNames}" name="ingredientPreparationMethodIds"/>
    Factor :<g:textField name="factor" value="${factor.toString()}" size="4" />(E.g.MMP 6 oz can - NDB 12 oz can = .5)

    <div>
      <div class="left-side list">
        <g:each in="${ingredientList}" status="i" var="ingredient">
          <%
            if (!standard) {
          %>
          <div class="food-name"><g:radio name="ingredient" value="${ingredient.id}"/>
          ${ingredient.ingredient.name}
          <g:if test="${i<1}"><em>${i}(select unit and/or preparation method to create mapping)</em></g:if></div>
          <mp:usedProducts unmatched="${params.unmatched}" in="${result.id}" var="ing1" status="j">
            <div class="result">
              <g:radio name="ingredient" value="${ing1.id}"/>

            </div>
          </mp:usedProducts>


            <td>${ingredient.preparationMethod?.name}</td>
            <td>${ingredient.recipe?.name}</td>
            <td><g:radio name="ingredient" value="${ingredient.id}"/></td>

          <%
            } else {
          %>
          <div class="food-name"><g:radio name="ingredient" value="mp-${ingredient.id}"/>
          <b>${ingredient.name}</b>
            <g:if test="${i==0}"><em> (select unit and/or preparation method to create mapping)</em></g:if></div>
          <mp:usedProducts unmatched="${params.unmatched}" in="${ingredient.id}" var="recipeIngredient" status="j">
            <div class="result">
              <g:radio name="ingredient" value="${recipeIngredient.id}"/>
              ${recipeIngredient} <em>${recipeIngredient.recipe.name}</em>
              <g:if test="${!params.unmatched}">
                <g:each in="${recipeIngredient?.ingredient?.links}" var="${link}">
                  <g:link id="${link.id}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"
                          action="delete" class="delete" title="del ${link.unit?.name}-${link.nutrition?.gmWgt}g (${link.nutrition?.amount} ${link.nutrition?.msreDesc})"
                    params="${deleteParams}">                    
                    link </g:link>
                </g:each>
              </g:if>
            </div>
          </mp:usedProducts>
          <% } %>
        </g:each>
      <div class="paginateButtons">
        <g:paginate action="list" total="${ingredientTotal}" max="${maxIng}" offset="${offsetIng}" params="${pagingParamsIng}"/>
      </div>
    </div>
      <div class="left-side list">
        <g:set var="haveQuery" value="${params.nutritionalName?.trim()}"/>
        <g:set var="haveResults" value="${searchResult?.results}"/>
        <div class="title">
          <span>
            <g:if test="${haveQuery && haveResults}">
              Showing <strong>${searchResult.offset + 1}</strong> - <strong>${searchResult.results.size() + searchResult.offset}</strong> of <strong>${searchResult.total}</strong>
              results for <strong>${params.nutritionalName}</strong>
            </g:if>
            <g:else>
              &nbsp;
            </g:else>
          </span>
        </div>
        <g:if test="${haveQuery && !haveResults && !parseException}">
          <p>Nothing matched your query - <strong>${params.nutritionalName}</strong></p>
            <p>Perhaps the ndb hasn't been loaded.  Click <g:link controller="nutritionLink" action="loadNDB">here</g:link> to load it now.</p>
          <g:if test="${!searchResult?.suggestedQuery}">
            <p>Suggestions:</p>
            <ul>
              <li>Try a suggested query: <g:link controller="searchable" action="index" params="[q: params.q, suggestQuery: true]">Search again with the <strong>suggestQuery</strong> option</g:link><br/>
                <em>Note: Suggestions are only available when classes are mapped with <strong>spellCheck</strong> options, either at the class or property level.<br/>
                  The simplest way to do this is add <strong>spellCheck "include"</strong> to the domain class searchable mapping closure.<br/>
                  See the plugin/Compass documentation Mapping sections for details.</em>
              </li>
            </ul>
          </g:if>
        </g:if>

        <g:if test="${searchResult?.suggestedQuery}">
          <p>Did you mean <g:link controller="searchable" action="index" params="[q: searchResult.suggestedQuery]">${StringQueryUtils.highlightTermDiffs(params.q.trim(), searchResult.suggestedQuery)}</g:link>?</p>
        </g:if>

        <g:if test="${haveResults}">
          <div class="results">
            <g:each var="result" in="${searchResult.results}" status="index">
              <g:set var="desc" value="${result.shrtDesc}"/>
              <g:if test="${desc.size() > 120}"><g:set var="desc" value="${desc[0..120] + '...'}"/></g:if>
              <div class="food-name">${desc}</div>
              <mp:weights in="${result.id}" var="nutrition" status="windex">
                <div class="result">
                  <g:radio name="nutrition" value="${nutrition.id}" id="nutrition_${nutrition.id}"/>
                  <label for="nutrition_${nutrition.id}">${nutrition.gmWgt} g per ${nutrition.amount} ${nutrition.msreDesc}</label>
                </div>
              </mp:weights>
            </g:each>
          </div>
          <div>
            <div class="paginateButtons">
              <g:if test="${haveResults && searchResult}">
                <g:set var="totalPages" value="${Math.ceil(searchResult.total / searchResult.max)}"/>
                <g:if test="${totalPages == 1}"><span class="currentStep">1</span></g:if>
                <g:else>
                  <g:paginate action="list" params="${pagingParamsFood}" total="${searchResult.total}" max="${maxFood}" offset="${offsetFood}" />
                  %{--<g:paginate controller="searchable" action="index" params="[q: params.q]" total="${searchResult.total}" prev="&lt; previous" next="next &gt;"/>--}%
                </g:else>
              </g:if>
            </div>
          </div>
        </g:if>

      </div>
    </div>
  </g:form>
  <script type="text/javascript">

      var preparationMethods = []
      <g:each in="${preparationMethods}" var="preparationMethod">
      preparationMethods.push(['${preparationMethod?.name?.replaceAll("'","\\\\'")}','${preparationMethod.id}'])
      </g:each>

      function resetPreparationMethods() {

          $(".iPreparationMethod").unautocomplete().autocomplete(preparationMethods, {
              matchContains: true,
              selectFirst: false,
              minChars: 0,
              max:0,
              mustMatch:false
          }).result(function(event, data, formatted) {
              $(this).next().val(data[0])
          })
      }


      $(function() {
          resetPreparationMethods();
      })

      var measurementUnits = {}
      <g:each var="result" in="${searchResult?.results}" status="index">
        <mp:weights in="${result.id}" var="nutrition" status="windex">
            measurementUnits['${nutrition.id}']='${nutrition?.msreDesc}'
        </mp:weights>
      </g:each>

      var units = {}
      <g:each var="unit" in="${Unit.list()}" status="index">
         units['name-${unit.id}'] = '${unit.name}'
         units['small-${unit.id}'] = '${unit.small?.length()>0?unit.small:'empty'}'
        </g:each>

      var ingredientsWithUnits = {}
              <g:each in="${ingredientList}" status="i" var="ingredient">
                <%
                  if (!standard) {
                %>
                <mp:usedProducts in="${result.id}" var="ing1" status="j">
                  <g:if test="${ing1?.quantity?.unit}">
                    ingredientsWithUnits['${ing1.id}'] = '${ing1.name}'
                  </g:if>
                </mp:usedProducts>
                <%
                  } else {
                %>
      <mp:usedProducts in="${ingredient.id}" var="recipeIngredient" status="j">
        <g:if test="${recipeIngredient?.quantity?.unit}">
          ingredientsWithUnits['${recipeIngredient.id}'] = '${recipeIngredient.ingredient.name}'
        </g:if>
      </mp:usedProducts>

                <% } %>
              </g:each>

      registerNutritionLinks = function(){
        $.each($('input[name=nutrition]'),function(i,nutRadio){
           nutRadio.onclick = function(){
             $.each($('select[name=unit]')[0].options, function(j,unitOption){
               // is the checked ingredient with units?
               var doesIngredientHaveUnit = false
               $.each($('input[name=ingredient]'),function(k,ingRadio){
                 if(ingRadio.checked){
                   if(ingredientsWithUnits[ingRadio.value]!=null){
                     doesIngredientHaveUnit = true
                   }
                 }
               })
               if(doesIngredientHaveUnit &&
                       unitOption.value!='null' &&
                  (measurementUnits[nutRadio.value].toUpperCase().indexOf(units['name-'+unitOption.value].toUpperCase())>=0 ||
                  measurementUnits[nutRadio.value].toUpperCase().indexOf(units['small-'+unitOption.value].toUpperCase())>=0)){
                 unitOption.selected = true;
               } else {
                 unitOption.selected = false;
               }
             })
           }
        })
      }
      $(function () {
        registerNutritionLinks();
      })

  </script>
