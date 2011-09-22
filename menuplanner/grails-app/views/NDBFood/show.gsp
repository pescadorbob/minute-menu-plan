
<%@ page import="com.mp.domain.ndb.NDBFood" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="menu" />
        <g:set var="entityName" value="${message(code: 'NDBFood.label', default: 'NDBFood')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
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
            <h3><g:message code="default.show.label" args="[entityName]" /></h3>
            </div>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.ndbNo.label" default="Ndb No" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "ndbNo")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.refuse_Pct.label" default="Refuse Pct" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "refuse_Pct")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.water.label" default="Water" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "water")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.energ_Kcal.label" default="Energ Kcal" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "energ_Kcal")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.protein.label" default="Protein" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "protein")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.lipid_Tot.label" default="Lipid Tot" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "lipid_Tot")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.ash.label" default="Ash" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "ash")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.carbohydrt.label" default="Carbohydrt" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "carbohydrt")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.fiber_TD.label" default="Fiber TD" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "fiber_TD")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.sugar_Tot.label" default="Sugar Tot" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "sugar_Tot")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.calcium.label" default="Calcium" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "calcium")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.iron.label" default="Iron" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "iron")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.magnesium.label" default="Magnesium" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "magnesium")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.phosphorus.label" default="Phosphorus" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "phosphorus")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.potassium.label" default="Potassium" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "potassium")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.sodium.label" default="Sodium" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "sodium")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.zinc.label" default="Zinc" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "zinc")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.copper.label" default="Copper" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "copper")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.manganese.label" default="Manganese" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "manganese")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.selenium.label" default="Selenium" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "selenium")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_C.label" default="Vit C" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_C")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.thiamin.label" default="Thiamin" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "thiamin")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.riboflavin.label" default="Riboflavin" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "riboflavin")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.niacin.label" default="Niacin" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "niacin")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.panto_acid.label" default="Pantoacid" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "panto_acid")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_B6.label" default="Vit B6" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_B6")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.folate_Tot.label" default="Folate Tot" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "folate_Tot")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.folic_acid.label" default="Folicacid" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "folic_acid")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.food_Folate.label" default="Food Folate" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "food_Folate")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.folate_DFE.label" default="Folate DFE" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "folate_DFE")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.choline_Tot.label" default="Choline Tot" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "choline_Tot")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_B12.label" default="Vit B12" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_B12")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_A_IU.label" default="Vit AIU" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_A_IU")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_A_RAE.label" default="Vit ARAE" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_A_RAE")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.retinol.label" default="Retinol" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "retinol")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.alpha_Carot.label" default="Alpha Carot" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "alpha_Carot")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.beta_Carot.label" default="Beta Carot" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "beta_Carot")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.beta_Crypt.label" default="Beta Crypt" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "beta_Crypt")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.lycopene.label" default="Lycopene" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "lycopene")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.lut_Zea.label" default="Lut Zea" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "lut_Zea")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_E.label" default="Vit E" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_E")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_D_mcg.label" default="Vit Dmcg" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_D_mcg")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_D_IU.label" default="Vit DIU" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_D_IU")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.vit_K.label" default="Vit K" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "vit_K")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.faSat.label" default="Fa Sat" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "faSat")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.faMono.label" default="Fa Mono" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "faMono")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.faPoly.label" default="Fa Poly" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "faPoly")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.cholestrl.label" default="Cholestrl" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "cholestrl")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.gmWt_1.label" default="Gm Wt1" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "gmWt_1")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.gmWt_Desc1.label" default="Gm Wt Desc1" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "gmWt_Desc1")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.gmWt_2.label" default="Gm Wt2" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "gmWt_2")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.gmWt_Desc2.label" default="Gm Wt Desc2" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "gmWt_Desc2")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.fileInfo.label" default="File Info" /></td>
                            
                            <td valign="top" class="value"><g:link controller="NDBFileInfo" action="show" id="${NDBFood?.fileInfo?.id}">${NDBFood?.fileInfo?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.fileVersion.label" default="File Version" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "fileVersion")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="NDBFood.shrt_Desc.label" default="Shrt Desc" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: NDBFood, field: "shrt_Desc")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${NDBFood?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </div></div></div>

    </body>
</html>
