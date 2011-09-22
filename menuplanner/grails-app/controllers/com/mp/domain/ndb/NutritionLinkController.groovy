package com.mp.domain.ndb

import com.mp.domain.ndb.ItemNutritionLink
import com.mp.domain.ndb.NDBFood
import com.mp.domain.ndb.NDBWeight
import org.compass.core.engine.SearchEngineQueryParseException
import com.mp.domain.*

class NutritionLinkController {

  def ndbService
  def searchableService
  
  static allowedMethods = [save: "POST", update: "POST"]

  def index = {
    redirect(action: "list", params: params)
  }

  def match = {errors ->
    println "Matching ingredient[${params.ingredient}] with [${params.nutrition}]"
    def isMP = params.ingredient?.indexOf('mp-')>=0
    def item
    def ingredient
    if(isMP){
      params.ingredient = params.ingredient[3..params.ingredient.length()-1]
      item = Item.get(params.int('ingredient'))//both the same intentionally
    } else {
      ingredient = RecipeIngredient.get(params.int('ingredient'))// both 'ingredient' intentionally

    }
    def nutrition = NDBWeight.get(params.int('nutrition'))
    def unit = Unit.get(params.int('unit'))
    def amount = params.int('quantity')
    def factor = params.float('factor')
    def preparationMethod = PreparationMethod.get(params.int('hiddenIngredientPreparationMethodNames'))
    if ((!ingredient && !item) || !nutrition  ) {
      flash.message = "Unable to make a match.  Be sure to select two radio buttons, one on each side. ${item?'If making a generic match (blue) choose a unit and (optional) preparation method too.':''} "
      params.remove("_action_match")
      params.remove("action")
      redirect(action: "list",params: params)
    }
    if( item){
      def link = new ItemNutritionLink(prep:preparationMethod,
              factor:factor,frum: new Date(),
              product:item,nutrition:nutrition,unit:unit)
      if (!link.hasErrors() && link.save(flush: true)) {
        def ingName = item?.toString()
        flash.message = "Matched Item [${unit}:${ingName}] with [${nutrition.weightFor.shrtDesc}-${nutrition?.msreDesc}]"
        params.remove("_action_match")
        params.remove("action")
        params.remove("nutritionalName")
        redirect(action: "list",params: params)
      } else {
        flash.message = "Unable to make a match.  Some error occurred!"
        if(link.hasErrors()){
          link.errors.each {
            flash.message += it
          }
        }
        println "Params:${params}"
        params.remove("_action_match")
        params.remove("action")
        params.NutritionLink = link

        redirect(action: "list",params: params)
      }
    } else  {
      unit = unit?unit:Unit.get(ingredient.quantity?.unit?.id)
      def link = new ItemNutritionLink(prep:preparationMethod?preparationMethod:PreparationMethod.get(ingredient.preparationMethod?.id),
              factor:factor,frum: new Date(),product:Item.get(ingredient.ingredient.id),nutrition:nutrition,unit:unit)
      if (!link.hasErrors() && link.save(flush: true)) {
        def ingName = ingredient?.ingredient?.toString()
        flash.message = "Matched ingredient [${unit}-${ingName}] with [${nutrition.weightFor.shrtDesc}-${nutrition?.msreDesc}]"
        params.remove("_action_match")
        params.remove("action")
        params.remove("nutritionalName")
        redirect(action: "list",params: params)
      } else {
        flash.message = "Unable to make a match.  Some error occurred!"
        if(link.hasErrors()){
          link.errors.each {
            flash.message += it
          }
        }
        println "Params:${params}"
        params.remove("_action_match")
        params.remove("action")
        redirect(action: "list", params: params)
      }
    }

  }
  def list = {params->
    filter(params)
  }
  def filter = {
    params.searchCount = params.searchCount?params.int('searchCount')+1:0
    params.standard = params.int('searchCount')==0?'on':params.standard
    params.unmatched = params.int('searchCount')==0?'on':params.unmatched
    params.maxIng = Math.min(params.maxIng ? params.int('maxIng') : 10, 100)
    params.offsetIng = params.offsetIng ? params.int('offsetIng') : 0
    params.maxFood = Math.min(params.maxFood ? params.int('maxFood') : 10, 100)
    params.offsetFood = params.offsetFood ? params.int('offsetFood') : 0
    if(params.pagingSource && params.pagingSource == 'ing'){
      params.maxIng = Math.min(params.max ? params.int('max') : 10, 100)
      params.offsetIng = params.offset ? params.int('offset') : 0
    } else if(params.pagingSource && params.pagingSource == 'food'){
      params.maxFood = Math.min(params.max ? params.int('max') : 10, 100)
      params.offsetFood = params.offset ? params.int('offset') : 0
    } 
    params.max = null
    params.offset = null
    def standardVolumetricUnits = Unit.findAllByMetricType(MetricType.METRIC)

    def ingredients
    println "unmatched:[${params.unmatched}]"
    println "Standard:[${params.standard}]"
    println "Recipe:[${params.recipe}]"
    println "UserProducts:[${params.userProducts}]"

    if(params.standard ){
      ingredients = getItems(params)
    } else {
       ingredients = getIngredients(params).ingredients
    }
    def searchResult
    def parseException
    try {
      if(!params.nutritionalName || params.nutritionalName.size()==0 || params.nutritionalName == 'null'){
        if(ingredients.ingredients.size()>0){
          def prodResults = ingredients.ingredients
          if(prodResults[0] instanceof Item){
            params.nutritionalName = prodResults[0].name
          } else if(prodResults[0] instanceof RecipeIngredient){
            params.nutritionalName = prodResults[0].ingredient.name
          }
        }
      }
      searchResult = getNutritionalValues(params)
    } catch (SearchEngineQueryParseException ex) {
      parseException = true
    }
    println "Nutritional Values size:[${searchResult?.total}]"
    println "ingredient:[${params.searchName}]"
    
    def quantity = params.quantity?params.quantity:"1"
    def factor = params.factor?params.factor:"1"
    def retParams = [nutritionalName: params.nutritionalName,
            aisle: params.aisle?params.aisle:null,
            quantity: quantity,
            factor: factor,
            searchName: params.searchName,
            parseException: parseException,
            recipe: params.recipe?params.recipe:null,
            standard: params.standard,
            unmatched: params.unmatched,
            userProducts: params.userProducts,
            ingredientTotal: ingredients.total,
            maxIng:params.maxIng,offsetIng:params.offsetIng,
            maxFood:params.maxFood,offsetFood:params.offsetFood]
    retParams.pagingParamsIng = retParams.clone()
    retParams.pagingParamsIng.pagingSource = "ing"
    retParams.pagingParamsFood = retParams.clone()
    retParams.preparationMethods=PreparationMethod.list()
    retParams.pagingParamsFood.pagingSource = "food"
    retParams.standardUnits = standardVolumetricUnits
    retParams.ingredientList = ingredients.ingredients
    retParams.searchResult = searchResult
    retParams.parseException = parseException
    if(params.NutritionLink) retParams.NutritionLink = params.NutritionLink
    retParams
  }
  def searchResults = {params ->
    def retParams = filter(params)
    render(template:'searchResults',model:retParams)
  }
  def getNutritionalValues = {params ->
    String name = params.nutritionalName
    println "Nutritional Name:[${name}]"

    def searchResult = null
    if (params.nutritionalName) {
        def fuzzyTerms = ""
        params.nutritionalName.split(' ').collect { it ->
          fuzzyTerms += " ${it}~"
        }
        def sparams = params.clone()
        sparams.offset = params.offsetFood
        sparams.max = params.maxFood
        searchResult = NDBFood.search(fuzzyTerms, sparams)
    }

    searchResult
  }
  def getIngredients = {params ->
    String name = params.searchName
    println "Aisle:[${params.int('aisle')}]"
    println "Name:[${name}]"
    println "Recipe:[${params.recipe}]"
    def alreadyMatchedIngredients = params.unmatched?ItemNutritionLink.list():[]
    println "AlreadyMatchIngredients:[${alreadyMatchedIngredients}]"
    def ingredientsClosure = {
        if (name && name.length()>0) {
          ilike('name', "%${name}%")
        }
        if (params.aisle && params.aisle != 'null') {
          suggestedAisle {
            eq('id', params.long('aisle'))
          }
        }
        if (alreadyMatchedIngredients) {
          not{
            and{
              alreadyMatchedIngredients.each { link->
                 and {
                   ingredient {
                     eq('id',link.product.id)
                   }
                   preparationMethod {
                     eq('id',link.prep.id)
                   }
                 }
              }
            }
          }
        }
        groupProperty('ingredient')
        order ('name')
    }

    def criteria1 = RecipeIngredient.createCriteria()
    ingredientsClosure.delegate = criteria1
    def ingredients = criteria1.list() {
      ingredient {
        ingredientsClosure()
      }
      if(params.recipe && params.recipe != 'null'){
        recipe {
          eq('id', params.long('recipe'))
        }
      }
      maxResults(params.maxIng)
      firstResult(params.offsetIng)
    }
    def countCriteria = RecipeIngredient.createCriteria()
    ingredientsClosure.delegate = countCriteria
    def total = countCriteria.count() {
      ingredient {
        ingredientsClosure()                           
      }
      if(params.recipe && params.recipe != 'null'){
        recipe {
          eq('id', params.long('recipe'))
        }
      }
    }
    [ingredients: ingredients, total: total]
  }
  def getItems = {params ->
    String name = params.searchName
    println "Aisle:[${params.int('aisle')}]"
    println "Name:[${name}]"
    def existingLinks = null
    def map = [:]

    def query = "from Item as mp where exists \
      (from RecipeIngredient as ri \
      left join ri.quantity as q \
      left join q.unit as recipeUnit where ri.ingredient = mp  "
    def pruneClause =" and not exists \
       (from ItemNutritionLink as link \
         left join link.unit link_unit \
         where link.product = ri.ingredient \
          and ( (link.prep is null  and ri.preparationMethod is null ) or link.prep = ri.preparationMethod )\
           and ((link_unit is null  and recipeUnit is null) or link_unit = q.unit \
               or (link_unit.isConvertible=1 and recipeUnit.isConvertible=1))      \
       )"
    if(params.unmatched) {
      query += pruneClause
    }
    if(name) query += " and ri.ingredient.name like '%${name}%' "
    if(params.aisle && params.aisle != 'null'){
      map.aisle = params.long('aisle')
      query += " and ri.ingredient.suggestedAisle.id=:aisle "
    }
    if(params.recipe && params.recipe != 'null') {
      map.recipe = params.long('recipe')
      query += " and ri.recipe.id=:recipe "
    }
    query += "  )"
    def ingredients = Item.executeQuery("select distinct mp " + query,map,[max:params.maxIng,offset:params.offsetIng])
    def total = Item.executeQuery("select count(distinct mp) " + query,map)[0]

    [ingredients: ingredients, total: total]
  }
  def delete = {
      def link = NutritionLink.get(params.id)
      if (link) {
          try {
              link.delete(flush: true)
              flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'NutritionLink.label', default: 'Nutrition Link'), params.id])}"
              redirect(action: "list",params: params)
          }
          catch (org.springframework.dao.DataIntegrityViolationException e) {
              flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'NutritionLink.label', default: 'NutritionLink'), params.id])}"
              redirect(action: "list", id: params.id)
          }
      }
      else {
          flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'NutritionLink.label', default: 'NutritionLink'), params.id])}"
          redirect(action: "list")
      }
  }

}
