package com.mp.domain

import javax.servlet.http.Cookie
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.tools.UserTools
import com.mp.email.Tag
import com.mp.domain.pricing.Price
import com.mp.domain.pricing.ItemPrice
import com.mp.domain.pricing.PriceType
import com.mp.domain.party.Grocer

class ShoppingListController {

  def standardConversionService
  def shoppingListService
  def asynchronousMailService
  def userService

  def index = { }

  def generateShoppingList = {
    MenuPlan menuPlan = MenuPlan.get(params?.id?.toLong())
    LoginCredential user = UserTools.currentUser
    PrintShoppingListCO pslCO = new PrintShoppingListCO()
    pslCO.name = menuPlan?.name + '-Shopping List'
    pslCO.menuPlanId = params?.id
    Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
    pslCO.servings = mouthsToFeed
    List<MenuPlan> menuPlans = user?.party?.menuPlans as List
    render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
  }

  def delete = {
    ShoppingList shoppingList = ShoppingList.get(params.shoppingListId)
    if (shoppingList) {
      try {
        flash.message = message(code: 'shoppingList.deleted.success')
        ShoppingList.withTransaction {
          shoppingList.delete(flush: true)
        }
        redirect(uri: '/')
        return
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = message(code: 'shoppingList.deleted.unsuccess')
        redirect(action: "show", id: params.id)
        return
      }
    }
    else {
      flash.message = "No such Shopping List exists."
      redirect(uri: '/')
      return
    }
  }

  def createOriginal = {PrintShoppingListCO pslCO ->
      def listErrors = []
      pslCO.areServingsRequired = false
    pslCO.areWeeksRequired = (params.shoppingList == "GENERATE_WITH_WEEKS")
    if (pslCO.validate()) {
      ShoppingList shoppingList
      Boolean weeklyList
      switch (params?.shoppingList) {
        case "GENERATE_WITH_WEEKS":
          weeklyList = true
          shoppingList = shoppingListService.createShoppingList(pslCO, weeklyList,listErrors)
          break;
        case "GENERATE_WITHOUT_WEEKS":
          weeklyList = false
          shoppingList = shoppingListService.createShoppingList(pslCO, weeklyList,listErrors)
          break;
      }
      render(view: 'create', model: [shoppingList: shoppingList, weeklyList: weeklyList])
    } else {
      pslCO.errors.allErrors.each {
        println it
      }
      LoginCredential user = UserTools.currentUser
      List<MenuPlan> menuPlans = user?.party?.menuPlans as List
      Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
      render(view: 'generateShoppingList', model: [listErrors:listErrors,pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
    }
  }

  def createScaled = {PrintShoppingListCO pslCO ->
      def listErrors = []
      pslCO.areServingsRequired = true
    pslCO.areWeeksRequired = (params.shoppingList == "GENERATE_WITH_WEEKS")
    if (pslCO.validate()) {
      Integer servings = pslCO.servings?.toInteger()
      ShoppingList shoppingList
      Boolean weeklyList
      switch (params?.shoppingList) {
        case "GENERATE_WITH_WEEKS":
          weeklyList = true
          shoppingList = shoppingListService.createShoppingList(pslCO, weeklyList,listErrors)
          break;
        case "GENERATE_WITHOUT_WEEKS":
          weeklyList = false
          shoppingList = shoppingListService.createShoppingList(pslCO, weeklyList,listErrors)
          break;
      }
      render(view: 'create', model: [listErrors:listErrors,shoppingList: shoppingList, weeklyList: weeklyList, servings: servings])
    } else {
      pslCO.errors.allErrors.each {
        println it
      }
      LoginCredential user = UserTools.currentUser
      List<MenuPlan> menuPlans = user?.party?.menuPlans as List
      Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
      render(view: 'generateShoppingList', model: [listErrors:listErrors,pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
    }
  }

  def show = {
    ShoppingList shoppingList = ShoppingList.get(params.id)
    render(view: 'show', model: [shoppingList: shoppingList, party: UserTools.currentUser?.party])
  }

  def save = {
    List<String> weekList = params.weekList?.tokenize("[], ")
    String isWeeklyListString = params?.isWeeklyShoppingList?.toString()
    MenuPlan menuPlan = MenuPlan.get(params?.menuPlanId?.toLong())
    ShoppingList shoppingList = new ShoppingList()
    shoppingList.menuPlan = menuPlan
    shoppingList.name = params?.shoppingListName
    shoppingList.servings = params?.servings?.toInteger()
    shoppingList.isWeeklyShoppingList = (isWeeklyListString.contains('true')) ? true : false
    shoppingList.party = UserTools.currentUser?.party
    shoppingList.s()
    weekList.each {String index ->
      WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, weekIndex: index.toInteger())

      List<ShoppingIngredient> products = []
      List<ShoppingIngredient> groceries = []

      Map groceriesByWeek = params.findAll {it.key.contains("week${index}.groceries")}
      groceriesByWeek.each {key, value ->
        String aisleId = key.tokenize(".")?.last()
        Aisle aisle
        if (aisleId) {
          aisle = Aisle.get(aisleId?.toLong())
        }

        List<String> groceryNames = [value].flatten()
        groceryNames?.each {
          ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
          shoppingIngredient.name = it
          shoppingIngredient.aisle = aisle
          groceries.add(shoppingIngredient.s())
        }
      }

      Map productsByWeek = params.findAll {it.key.contains("week${index}.products")}
      productsByWeek.each {key, value ->
        String aisleId = key.tokenize(".")?.last()
        Aisle aisle
        if (aisleId) {
          aisle = Aisle.get(aisleId?.toLong())
        }
        List<String> productNames = [value].flatten()
        productNames?.each {
          ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
          shoppingIngredient.name = it
          shoppingIngredient.aisle = aisle
          products.add(shoppingIngredient.s())
        }
      }
      recordReceipts(params)
      weeklyShoppingList.products = products
      weeklyShoppingList.groceries = groceries
      shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
      weeklyShoppingList.s()
    }
    flash.message = "Shopping List ${shoppingList?.name} has been created successfully"
    redirect(controller: 'shoppingList', action: 'show', id: shoppingList?.id)
  }

  def cancelDetailShoppingList = {
    redirect(controller: 'shoppingList', action: 'generateShoppingList', id: params?.menuPlanId)
  }

  def edit = {
    ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
    if (shoppingList.menuPlan) {
      LoginCredential user = UserTools.currentUser
      PrintShoppingListCO pslCO = new PrintShoppingListCO()
      pslCO.name = shoppingList?.name
      pslCO.menuPlanId = shoppingList?.menuPlan?.id
      pslCO.servings = shoppingList?.servings
      pslCO.isWeeklyShoppingList = shoppingList?.isWeeklyShoppingList
      pslCO.weeks = shoppingList?.weeklyShoppingLists*.weekIndex*.toString()
      List<MenuPlan> menuPlans = user?.party?.menuPlans as List
      Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
      render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, shoppingListId: shoppingList?.id, isWeeklyList: shoppingList?.isWeeklyShoppingList, servings: mouthsToFeed, shoppingList: shoppingList])
    } else {
      render(view: 'create', model: [shoppingList: shoppingList, shoppingListId: shoppingList?.id, isWeeklyList: shoppingList?.isWeeklyShoppingList])
    }
  }

  def modifyShoppingListOriginal = {PrintShoppingListCO pslCO ->
    pslCO.areServingsRequired = false
    pslCO.areWeeksRequired = (params.shoppingList == "GENERATE_WITH_WEEKS")
    if (pslCO.validate()) {
      ShoppingList shoppingListNew
      ShoppingList shoppingListOld = ShoppingList.get(params?.shoppingListId?.toLong())
      Boolean isWeeklyList
      switch (params?.shoppingList) {
        case "GENERATE_WITH_WEEKS":
          isWeeklyList = true
          shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld, isWeeklyList)
          break;
        case "GENERATE_WITHOUT_WEEKS":
          isWeeklyList = false
          shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld, isWeeklyList)
          break;
      }
      render(view: 'create', model: [shoppingList: shoppingListNew, shoppingListId: shoppingListOld?.id, isWeeklyList: isWeeklyList,
              metricUnits: Unit.sortedMetricUnits,  preparationMethods: PreparationMethod.list()])
    }
    else {
      pslCO.errors.allErrors.each {
        println it
      }
      LoginCredential user = UserTools.currentUser
      List<MenuPlan> menuPlans = user?.party?.menuPlans as List
      Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
      render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
    }
  }

  def modifyShoppingListScaled = {PrintShoppingListCO pslCO ->
    pslCO.areServingsRequired = true
    pslCO.areWeeksRequired = (params.shoppingList == "GENERATE_WITH_WEEKS")
    if (pslCO.validate()) {
      ShoppingList shoppingListNew
      ShoppingList shoppingListOld = ShoppingList.get(params?.shoppingListId?.toLong())
      Boolean isWeeklyList
      switch (params?.shoppingList) {
        case "GENERATE_WITH_WEEKS":
          isWeeklyList = true
          shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld, isWeeklyList)
          break;
        case "GENERATE_WITHOUT_WEEKS":
          isWeeklyList = false
          shoppingListNew = shoppingListService.modifyShoppingList(pslCO, shoppingListOld, isWeeklyList)
          break;
      }
      shoppingListNew.servings = params?.servings?.toInteger()
      render(view: 'create', model: [shoppingList: shoppingListNew, shoppingListId: shoppingListOld?.id,
              metricUnits: Unit.sortedMetricUnits,  preparationMethods: PreparationMethod.list()])
    }
    else {
      pslCO.errors.allErrors.each {
        println it
      }
      LoginCredential user = UserTools.currentUser
      List<MenuPlan> menuPlans = user?.party?.menuPlans as List
      Integer mouthsToFeed = user?.party?.subscriber?.mouthsToFeed
      render(view: 'generateShoppingList', model: [pslCO: pslCO, menuPlans: menuPlans, servings: mouthsToFeed])
    }
  }

  def update = {
    List<String> weekList = params.weekList?.tokenize("[], ")
    String isWeeklyListString = params?.isWeeklyShoppingList?.toString()
    ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
    shoppingList.servings = (params?.servings) ? params?.servings?.toInteger() : shoppingList?.servings
    shoppingList.isWeeklyShoppingList = (isWeeklyListString.contains('true')) ? true : false
    List<WeeklyShoppingList> weeklyShoppingLists = shoppingList.weeklyShoppingLists
    shoppingList.weeklyShoppingLists = []
    weeklyShoppingLists.each {WeeklyShoppingList weeklyShoppingList ->
      weeklyShoppingList.delete(flush: true)
    }

    weekList.each {String index ->
      WeeklyShoppingList weeklyShoppingList = new WeeklyShoppingList(shoppingList: shoppingList, weekIndex: index.toInteger())

      List<ShoppingIngredient> products = []
      List<ShoppingIngredient> groceries = []
      Map groceriesByWeek = params.findAll {it.key.contains("week${index}.groceries")}
      groceriesByWeek.each {key, value ->
        String aisleId = key.tokenize(".")?.last()
        Aisle aisle
        if (aisleId) {
          aisle = Aisle.get(aisleId?.toLong())
        }

        List<String> groceryNames = [value].flatten()
        groceryNames?.each {
          ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
          shoppingIngredient.name = it
          shoppingIngredient.aisle = aisle
          groceries.add(shoppingIngredient.s())
        }
      }
      Map productsByWeek = params.findAll {it.key.contains("week${index}.products")}
      productsByWeek.each {key, value ->
        String aisleId = key.tokenize(".")?.last()
        Aisle aisle
        if (aisleId) {
          aisle = Aisle.get(aisleId?.toLong())
        }
        List<String> productNames = [value].flatten()
        productNames?.each {
          ShoppingIngredient shoppingIngredient = new ShoppingIngredient()
          shoppingIngredient.name = it
          shoppingIngredient.aisle = aisle
          products.add(shoppingIngredient.s())
        }
      }
      recordReceipts params
      weeklyShoppingList.products = products
      weeklyShoppingList.groceries = groceries

      shoppingList.addToWeeklyShoppingLists(weeklyShoppingList)
      weeklyShoppingList.s()
    }
    flash.message = "Your changes have been saved successfully to ${shoppingList?.name}"
    redirect(controller: 'shoppingList', action: 'show', id: shoppingList?.id)
  }

  def recordReceipts(params) {
    String[] itemQuantities = params.get("itemQuantities")
    String[] itemUnitIds = params.get("itemUnitIds")
    String[] itemPrices = params.get("itemPrices")
    String[] itemProductNames = params.get("itemProductNames")
    itemProductNames.eachWithIndex {name, pIndex ->
      Item product = Item.findByName(name)
      Unit u = Unit.get(itemUnitIds[pIndex])
      if (u) {
        Quantity q = standardConversionService.getQuantityToSave(itemQuantities[pIndex], u)
        q.s()
        if (product && itemPrices[pIndex].isBigDecimal()) {
          Price p = new Price(price: itemPrices[pIndex].toBigDecimal(), quantity: q)
          p.s()
          if (p) {
            ItemPrice ip = new ItemPrice(recordedOn: new Date(), priceOf: product,
                    price: p, type: PriceType.SINGLE, recordedBy: UserTools.currentUser?.party,
                    grocer: Grocer.get(params.get("grocerId")))
            ip.s()
          }
        }
      }
    }

  }

  def printerFriendlyShoppingList = {
    ShoppingList shoppingList = ShoppingList.get(params.id)
    render(view: 'printerFriendlyShoppingList', model: [size: params.size, shoppingList: shoppingList])
  }

  def emailShoppingList = {
    ShoppingList shoppingList = ShoppingList.get(params?.shoppingListId?.toLong())
    String emailAddress = params?.emailId
    asynchronousMailService.sendAsynchronousMail {
      to emailAddress
      subject "${Tag.shoppingList} Your Shopping List : ${shoppingList.name}"
      html g.render(template: '/shoppingList/emailShoppingList', model: [tag: Tag.shoppingList, shoppingList: shoppingList])
    }
    render "Email sent to ${emailAddress}"
  }

}
