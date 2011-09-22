
<%@ page import="com.mp.domain.ndb.NDBFood" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'NDBFood.label', default: 'NDBFood')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
<div id="container">
  <div id="wrapper" class="clearfix">
    <div id="content-wrapper" class="clearfix">
      <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <div class="headbox">
              <h3><g:message code="default.edit.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${NDBFood}">
            <div class="errors">
                <g:renderErrors bean="${NDBFood}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${NDBFood?.id}" />
                <g:hiddenField name="version" value="${NDBFood?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ndbNo"><g:message code="NDBFood.ndbNo.label" default="Ndb No" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'ndbNo', 'errors')}">
                                    <g:textField name="ndbNo" value="${NDBFood?.ndbNo}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="refuse_Pct"><g:message code="NDBFood.refuse_Pct.label" default="Refuse Pct" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'refuse_Pct', 'errors')}">
                                    <g:textField name="refuse_Pct" value="${fieldValue(bean: NDBFood, field: 'refuse_Pct')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="water"><g:message code="NDBFood.water.label" default="Water" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'water', 'errors')}">
                                    <g:textField name="water" value="${fieldValue(bean: NDBFood, field: 'water')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="energ_Kcal"><g:message code="NDBFood.energ_Kcal.label" default="Energ Kcal" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'energ_Kcal', 'errors')}">
                                    <g:textField name="energ_Kcal" value="${fieldValue(bean: NDBFood, field: 'energ_Kcal')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="protein"><g:message code="NDBFood.protein.label" default="Protein" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'protein', 'errors')}">
                                    <g:textField name="protein" value="${fieldValue(bean: NDBFood, field: 'protein')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lipid_Tot"><g:message code="NDBFood.lipid_Tot.label" default="Lipid Tot" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'lipid_Tot', 'errors')}">
                                    <g:textField name="lipid_Tot" value="${fieldValue(bean: NDBFood, field: 'lipid_Tot')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="ash"><g:message code="NDBFood.ash.label" default="Ash" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'ash', 'errors')}">
                                    <g:textField name="ash" value="${fieldValue(bean: NDBFood, field: 'ash')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="carbohydrt"><g:message code="NDBFood.carbohydrt.label" default="Carbohydrt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'carbohydrt', 'errors')}">
                                    <g:textField name="carbohydrt" value="${fieldValue(bean: NDBFood, field: 'carbohydrt')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fiber_TD"><g:message code="NDBFood.fiber_TD.label" default="Fiber TD" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'fiber_TD', 'errors')}">
                                    <g:textField name="fiber_TD" value="${fieldValue(bean: NDBFood, field: 'fiber_TD')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sugar_Tot"><g:message code="NDBFood.sugar_Tot.label" default="Sugar Tot" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'sugar_Tot', 'errors')}">
                                    <g:textField name="sugar_Tot" value="${fieldValue(bean: NDBFood, field: 'sugar_Tot')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="calcium"><g:message code="NDBFood.calcium.label" default="Calcium" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'calcium', 'errors')}">
                                    <g:textField name="calcium" value="${fieldValue(bean: NDBFood, field: 'calcium')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="iron"><g:message code="NDBFood.iron.label" default="Iron" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'iron', 'errors')}">
                                    <g:textField name="iron" value="${fieldValue(bean: NDBFood, field: 'iron')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="magnesium"><g:message code="NDBFood.magnesium.label" default="Magnesium" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'magnesium', 'errors')}">
                                    <g:textField name="magnesium" value="${fieldValue(bean: NDBFood, field: 'magnesium')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="phosphorus"><g:message code="NDBFood.phosphorus.label" default="Phosphorus" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'phosphorus', 'errors')}">
                                    <g:textField name="phosphorus" value="${fieldValue(bean: NDBFood, field: 'phosphorus')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="potassium"><g:message code="NDBFood.potassium.label" default="Potassium" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'potassium', 'errors')}">
                                    <g:textField name="potassium" value="${fieldValue(bean: NDBFood, field: 'potassium')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="sodium"><g:message code="NDBFood.sodium.label" default="Sodium" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'sodium', 'errors')}">
                                    <g:textField name="sodium" value="${fieldValue(bean: NDBFood, field: 'sodium')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="zinc"><g:message code="NDBFood.zinc.label" default="Zinc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'zinc', 'errors')}">
                                    <g:textField name="zinc" value="${fieldValue(bean: NDBFood, field: 'zinc')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="copper"><g:message code="NDBFood.copper.label" default="Copper" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'copper', 'errors')}">
                                    <g:textField name="copper" value="${fieldValue(bean: NDBFood, field: 'copper')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="manganese"><g:message code="NDBFood.manganese.label" default="Manganese" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'manganese', 'errors')}">
                                    <g:textField name="manganese" value="${fieldValue(bean: NDBFood, field: 'manganese')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="selenium"><g:message code="NDBFood.selenium.label" default="Selenium" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'selenium', 'errors')}">
                                    <g:textField name="selenium" value="${fieldValue(bean: NDBFood, field: 'selenium')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_C"><g:message code="NDBFood.vit_C.label" default="Vit C" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_C', 'errors')}">
                                    <g:textField name="vit_C" value="${fieldValue(bean: NDBFood, field: 'vit_C')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="thiamin"><g:message code="NDBFood.thiamin.label" default="Thiamin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'thiamin', 'errors')}">
                                    <g:textField name="thiamin" value="${fieldValue(bean: NDBFood, field: 'thiamin')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="riboflavin"><g:message code="NDBFood.riboflavin.label" default="Riboflavin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'riboflavin', 'errors')}">
                                    <g:textField name="riboflavin" value="${fieldValue(bean: NDBFood, field: 'riboflavin')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="niacin"><g:message code="NDBFood.niacin.label" default="Niacin" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'niacin', 'errors')}">
                                    <g:textField name="niacin" value="${fieldValue(bean: NDBFood, field: 'niacin')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="panto_acid"><g:message code="NDBFood.panto_acid.label" default="Pantoacid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'panto_acid', 'errors')}">
                                    <g:textField name="panto_acid" value="${fieldValue(bean: NDBFood, field: 'panto_acid')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_B6"><g:message code="NDBFood.vit_B6.label" default="Vit B6" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_B6', 'errors')}">
                                    <g:textField name="vit_B6" value="${fieldValue(bean: NDBFood, field: 'vit_B6')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="folate_Tot"><g:message code="NDBFood.folate_Tot.label" default="Folate Tot" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'folate_Tot', 'errors')}">
                                    <g:textField name="folate_Tot" value="${fieldValue(bean: NDBFood, field: 'folate_Tot')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="folic_acid"><g:message code="NDBFood.folic_acid.label" default="Folicacid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'folic_acid', 'errors')}">
                                    <g:textField name="folic_acid" value="${fieldValue(bean: NDBFood, field: 'folic_acid')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="food_Folate"><g:message code="NDBFood.food_Folate.label" default="Food Folate" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'food_Folate', 'errors')}">
                                    <g:textField name="food_Folate" value="${fieldValue(bean: NDBFood, field: 'food_Folate')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="folate_DFE"><g:message code="NDBFood.folate_DFE.label" default="Folate DFE" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'folate_DFE', 'errors')}">
                                    <g:textField name="folate_DFE" value="${fieldValue(bean: NDBFood, field: 'folate_DFE')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="choline_Tot"><g:message code="NDBFood.choline_Tot.label" default="Choline Tot" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'choline_Tot', 'errors')}">
                                    <g:textField name="choline_Tot" value="${fieldValue(bean: NDBFood, field: 'choline_Tot')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_B12"><g:message code="NDBFood.vit_B12.label" default="Vit B12" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_B12', 'errors')}">
                                    <g:textField name="vit_B12" value="${fieldValue(bean: NDBFood, field: 'vit_B12')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_A_IU"><g:message code="NDBFood.vit_A_IU.label" default="Vit AIU" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_A_IU', 'errors')}">
                                    <g:textField name="vit_A_IU" value="${fieldValue(bean: NDBFood, field: 'vit_A_IU')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_A_RAE"><g:message code="NDBFood.vit_A_RAE.label" default="Vit ARAE" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_A_RAE', 'errors')}">
                                    <g:textField name="vit_A_RAE" value="${fieldValue(bean: NDBFood, field: 'vit_A_RAE')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="retinol"><g:message code="NDBFood.retinol.label" default="Retinol" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'retinol', 'errors')}">
                                    <g:textField name="retinol" value="${fieldValue(bean: NDBFood, field: 'retinol')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="alpha_Carot"><g:message code="NDBFood.alpha_Carot.label" default="Alpha Carot" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'alpha_Carot', 'errors')}">
                                    <g:textField name="alpha_Carot" value="${fieldValue(bean: NDBFood, field: 'alpha_Carot')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="beta_Carot"><g:message code="NDBFood.beta_Carot.label" default="Beta Carot" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'beta_Carot', 'errors')}">
                                    <g:textField name="beta_Carot" value="${fieldValue(bean: NDBFood, field: 'beta_Carot')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="beta_Crypt"><g:message code="NDBFood.beta_Crypt.label" default="Beta Crypt" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'beta_Crypt', 'errors')}">
                                    <g:textField name="beta_Crypt" value="${fieldValue(bean: NDBFood, field: 'beta_Crypt')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lycopene"><g:message code="NDBFood.lycopene.label" default="Lycopene" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'lycopene', 'errors')}">
                                    <g:textField name="lycopene" value="${fieldValue(bean: NDBFood, field: 'lycopene')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="lut_Zea"><g:message code="NDBFood.lut_Zea.label" default="Lut Zea" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'lut_Zea', 'errors')}">
                                    <g:textField name="lut_Zea" value="${fieldValue(bean: NDBFood, field: 'lut_Zea')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_E"><g:message code="NDBFood.vit_E.label" default="Vit E" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_E', 'errors')}">
                                    <g:textField name="vit_E" value="${fieldValue(bean: NDBFood, field: 'vit_E')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_D_mcg"><g:message code="NDBFood.vit_D_mcg.label" default="Vit Dmcg" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_D_mcg', 'errors')}">
                                    <g:textField name="vit_D_mcg" value="${fieldValue(bean: NDBFood, field: 'vit_D_mcg')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_D_IU"><g:message code="NDBFood.vit_D_IU.label" default="Vit DIU" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_D_IU', 'errors')}">
                                    <g:textField name="vit_D_IU" value="${fieldValue(bean: NDBFood, field: 'vit_D_IU')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="vit_K"><g:message code="NDBFood.vit_K.label" default="Vit K" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'vit_K', 'errors')}">
                                    <g:textField name="vit_K" value="${fieldValue(bean: NDBFood, field: 'vit_K')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="faSat"><g:message code="NDBFood.faSat.label" default="Fa Sat" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'faSat', 'errors')}">
                                    <g:textField name="faSat" value="${fieldValue(bean: NDBFood, field: 'faSat')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="faMono"><g:message code="NDBFood.faMono.label" default="Fa Mono" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'faMono', 'errors')}">
                                    <g:textField name="faMono" value="${fieldValue(bean: NDBFood, field: 'faMono')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="faPoly"><g:message code="NDBFood.faPoly.label" default="Fa Poly" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'faPoly', 'errors')}">
                                    <g:textField name="faPoly" value="${fieldValue(bean: NDBFood, field: 'faPoly')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="cholestrl"><g:message code="NDBFood.cholestrl.label" default="Cholestrl" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'cholestrl', 'errors')}">
                                    <g:textField name="cholestrl" value="${fieldValue(bean: NDBFood, field: 'cholestrl')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gmWt_1"><g:message code="NDBFood.gmWt_1.label" default="Gm Wt1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'gmWt_1', 'errors')}">
                                    <g:textField name="gmWt_1" value="${fieldValue(bean: NDBFood, field: 'gmWt_1')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gmWt_Desc1"><g:message code="NDBFood.gmWt_Desc1.label" default="Gm Wt Desc1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'gmWt_Desc1', 'errors')}">
                                    <g:textField name="gmWt_Desc1" value="${NDBFood?.gmWt_Desc1}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gmWt_2"><g:message code="NDBFood.gmWt_2.label" default="Gm Wt2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'gmWt_2', 'errors')}">
                                    <g:textField name="gmWt_2" value="${fieldValue(bean: NDBFood, field: 'gmWt_2')}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="gmWt_Desc2"><g:message code="NDBFood.gmWt_Desc2.label" default="Gm Wt Desc2" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'gmWt_Desc2', 'errors')}">
                                    <g:textField name="gmWt_Desc2" value="${NDBFood?.gmWt_Desc2}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fileInfo"><g:message code="NDBFood.fileInfo.label" default="File Info" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'fileInfo', 'errors')}">
                                    <g:select name="fileInfo.id" from="${com.mp.domain.ndb.NDBFileInfo.list()}" optionKey="id" value="${NDBFood?.fileInfo?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="fileVersion"><g:message code="NDBFood.fileVersion.label" default="File Version" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'fileVersion', 'errors')}">
                                    <g:textField name="fileVersion" value="${NDBFood?.fileVersion}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="shrt_Desc"><g:message code="NDBFood.shrt_Desc.label" default="Shrt Desc" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: NDBFood, field: 'shrt_Desc', 'errors')}">
                                    <g:textField name="shrt_Desc" value="${NDBFood?.shrt_Desc}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
        </div>
        </div>
        </div>
    </body>
</html>
