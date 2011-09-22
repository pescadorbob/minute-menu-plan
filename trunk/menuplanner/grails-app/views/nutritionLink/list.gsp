<%@ page import="org.springframework.util.ClassUtils;  com.mp.domain.Recipe; com.mp.domain.ndb.NutritionLink; com.mp.domain.Aisle; com.mp.domain.Unit" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="menu"/>
  <g:set var="entityName" value="${message(code: 'NDBProductMapping.label', default: 'Nutritional linking to ingredients')}"/>
  <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>
<body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="headbox">
        <h3><g:message code="default.list.label" args="[entityName]"/></h3>
      </div>

      <div class="body">
        <g:if test="${flash.message}">
          <div class="flashMessage">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${NutritionLink}">
          <div class="errors">
            <g:renderErrors bean="${NutritionLink}" as="list"/>
          </div>
        </g:hasErrors>
        <div class="filter">
          <g:form name="searchForm">
            <g:hiddenField name="searchCount" value="${params.searchCount}"/>
          %{--<g:formRemote name="searchResults" on404="alert('Search Results not found')" update="search-results"--}%
          %{--url="${[action:'searchResults']}">--}%
            <table><tbody>
            <tr><td><h3>Ingredients Filter</h3></td></tr>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="searchName">Ingredient Name:</label>
              </td>
              <td valign="top" class="value ">
                <input name="searchName" type="text" class="inpbox" value="${searchName}"/>
              </td>

            </tr>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="aisle">Aisle:</label>
              </td>
              <td valign="top" class="value ">
                <g:select name="aisle" noSelection="${['null':'Select Aisle...']}"
                        from="${Aisle.list()}"
                        value="${aisle}"
                        optionKey="id"/>
              </td>
            </tr>
            <tr>
              <td valign="top" class="name">
                <label for="aisle">Recipe:</label>
              </td>
              <td valign="top" class="value ">
                <g:select name="recipe" noSelection="${['null':'Select Recipe...']}"
                        from="${Recipe.list()}"
                        value="${recipe}"
                        optionKey="id"/>
              </td>
            </tr>
            
            <tr><td valign="top" class="name">
              <label for="userProducts">Show user products</label></td>
              <td>
                <g:checkBox name="userProducts" value="${userProducts}"/>
              </td></tr>
            <tr><td valign="top" class="name">
              <label for="unmatched">Show only unmatched products</label></td>
              <td>
                <g:checkBox name="unmatched" value="${unmatched}"/>
              </td></tr>            
            <tr>
              <td valign="top" class="name">
                <label for="standard">Match Standard Products (Not Recipe Ingredients)</label></td><td>
              <g:checkBox name="standard" value="${standard}"/>
            </td>
            </tr>
            <tr><td><h3>NDB Food Filter</h3></td></tr>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="nutritionalName">Nutritional Value Name:</label>
              </td>
              <td valign="top" class="value ">
                <input name="nutritionalName" type="text" class="inpbox" value="${nutritionalName}"/>
              </td>

            </tr>
            <tr>
              <td valign="top" class="name">
                <label for="fileVersion">NDB File Version:</label>
              </td>
              <td valign="top" class="value ">
                <input name="fileVersion" type="text" value="${fileVersion}"/>
              </td>

            </tr>
            <tr><td>
              <g:actionSubmit id="btnSubmit" action="list" name="submit" value="Filter"/>
            </td></tr>
            </tbody></table>
          </g:form>
        </div>
        <div id="search-results">

        <g:render template="/nutritionLink/searchResults"
                model="[maxIng:maxIng,offsetIng:offsetIng,pagingParamsIng:pagingParamsIng,ingredientTotal:ingredientTotal,maxFood:maxFood,offsetFood:offsetFood,pagingParamsFood:pagingParamsFood,parseException:parseException,haveResults:haveResults,ingredientList: ingredientList,params:params,searchResult:searchResult,haveQuery:haveQuery]"/>
          </div>
      </div></div>
  </div>
</div>

</body>
</html>

