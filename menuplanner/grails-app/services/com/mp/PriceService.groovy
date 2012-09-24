package com.mp

import com.mp.domain.MenuPlan
import com.mp.domain.Week
import com.mp.domain.party.Grocer
import com.mp.domain.pricing.ItemPrice
import com.mp.domain.Item
import com.mp.domain.pricing.Price
import com.mp.domain.pricing.PriceType
import com.mp.tools.UserTools
import com.mp.domain.Quantity
import com.mp.domain.Unit
import com.mp.domain.pricing.ReceiptCO
import com.mp.domain.Product
import com.mp.domain.Recipe
import com.mp.domain.RecipeIngredient

import com.mp.domain.party.Party
import com.mp.domain.StandardConversionService
import com.mp.tools.UnitUtil
import com.mp.domain.pricing.IngredientPrice

/**
 * User: Brent Fisher
 * Date: Mar 10, 2012
 * Time: 11:06:48 AM
 */

public class PriceService {
  def standardConversionService
  def unitService

  def calculateRecipePrices(Party recParty, productIds, grocerIds) {
    def now = new Date()
    def grocers = [:]
    ItemPrice.withTransaction {
      ItemPrice.findAllByType(PriceType.AVE)*.delete(flush: true)
      ItemPrice.findAllByType(PriceType.HIGH)*.delete(flush: true)
      ItemPrice.findAllByType(PriceType.LOW)*.delete(flush: true)
    }
    Product.withTransaction {
      if (log.isDebugEnabled()) {
        log.debug "Pricing out ${productIds.size()} products and ${grocerIds.size()} grocers"
      }
      productIds.each {productId ->
        grocerIds.each {gId ->
          def c = ItemPrice.createCriteria()
          def itemPrices = c {
            eq("type", PriceType.SINGLE)
            grocer {
              eq("id", gId)
            }
            priceOf {
              eq("id", productId)
            }
          }
          if (itemPrices?.size() > 0) {
            Item p = itemPrices.last().priceOf
            Grocer grocer = itemPrices.last().grocer
            grocers[grocer.id] = grocer

            BigDecimal avePriceDollarPerMilliliter = itemPrices.collect { ip ->
                ip.price.price / ip.price.quantity.value
                // this quantity is always milliliter, so the number is pretty small.
                // small errors in rounding could be problematic.
            }.sum()/itemPrices.size()

            Unit preferredGroceryUnit = Unit.findByName(p.mainShoppingListUnit)

              Quantity preferredUnitOfProduct = standardConversionService
                      .getQuantityToSaveFloat(1,preferredGroceryUnit)
              preferredUnitOfProduct.s()
              Price avePrice = new Price(
                      price: avePriceDollarPerMilliliter*preferredUnitOfProduct.value,
                      quantity: preferredUnitOfProduct)
            avePrice.s()

            ItemPrice aveIPrice = new ItemPrice(recordedOn: now, priceOf: p, type: PriceType.AVE,
                    recordedBy: recParty, grocer: grocer, price: avePrice)
            aveIPrice.s()

          }
        }
      }
    }
    def recipeIds = []
    Recipe.withTransaction {
      // now that all of the product prices are known calculate the recipe prices?
      // per grocer, and for all grocers
      Quantity oneEach = standardConversionService.getQuantityToSaveFloat(1.0d, Unit.findByName("Each"))
      oneEach.s()
      def recipes = []
      productIds.each {pid ->
        recipes.addAll Recipe.executeQuery("select ri.recipe from RecipeIngredient ri where ri.ingredient.id = ?", [pid])
      }
      recipes.each {recipe1 ->
        Recipe recipe = recipe1
        recipeIds << recipe.id
        grocerIds.each {gId ->
          BigDecimal grocerTotalPrice = 0.0;
          recipe.ingredients.each {ri ->
            RecipeIngredient recipeIngredient = ri
            def c = ItemPrice.createCriteria()
            def aveItemPrices = c {
              eq("type", PriceType.AVE)
              grocer {
                eq("id", gId)
              }
              priceOf {
                eq("id", ri.ingredient.id)
              }
            }


            if (aveItemPrices && aveItemPrices.size() > 0) {
              ItemPrice aveItemPrice = aveItemPrices.last()
              // now take the price (oz) by the quantity of the ingredient (in oz)
              BigDecimal ingPriceValue

              if (recipeIngredient.quantity){
                  ingPriceValue = aveItemPrice.price.price *
                          StandardConversionService.getQuantityValueAsFloat(
                                  UnitUtil.convert(recipeIngredient.quantity,recipeIngredient.preparationMethod,recipeIngredient.ingredient, aveItemPrice.price.quantity.unit))
              }
              else ingPriceValue = aveItemPrice.price.price

              grocerTotalPrice += ingPriceValue
                Price ingPrice = new Price(price: ingPriceValue,quantity: recipeIngredient.quantity)
                if(ingPrice.save()){
                    IngredientPrice ip = new IngredientPrice(recordedOn:now,priceOf:recipeIngredient,
                            price: ingPrice, type: PriceType.SINGLE, recordedBy: recParty )
                    ip.save()
                } else {
                    println "Ingredient missing the quantity for ${recipeIngredient.recipe.name} + ${recipeIngredient.ingredient.name}"
                }
            }
          }
          Price price = new Price(price: grocerTotalPrice, quantity: oneEach)
          price.s()
          ItemPrice grcTotalPrice = new ItemPrice(recordedBy: recParty, recordedOn: now, priceOf: recipe, price: price, type: PriceType.AVE, grocer: Grocer.get(gId))
          grcTotalPrice.s()
        }
      }
    }
  }

  def recordReceipt(ReceiptCO receipt) {
    Grocer grocer = Grocer.get(receipt.grocerId)
    Long itemCount = 0;
    def now = new Date()
    def enteredPrices = receipt.itemProductIds.eachWithIndex {productId, index ->
      if (productId?.length() > 0) {
        itemCount++
        def price = new Price(price: receipt.itemPrices[index].toBigDecimal(),
                quantity: new Quantity(value: receipt.itemQuantities[index]?.toFloat(),
                        unit: Unit.get(receipt.itemUnitIds[index]?.toLong())).s()).s()
        ItemPrice itemPrice = new ItemPrice(recordedOn: now,
                priceOf: Item.get(productId?.toLong()),
                price: price,
                type: PriceType.SINGLE,
                recordedBy: UserTools.currentUser.party,
                grocer: grocer).s()
      }
    }
    return itemCount
  }

    def getAvePrices(MenuPlan menuPlan)
    {
        def avePricesIds = []
        menuPlan.weeks.each {it ->
            Week week = it;
            week.days.each {day ->
                day.meals.each {meal ->
                    meal.items.each { item ->
                        avePricesIds << item.id
                    }
                }
            }
        }
        def c = ItemPrice.createCriteria()
        def aveItemPrices = c {
         priceOf {
             'in'('id',avePricesIds)
         }
        }
        def aveItemPriceMap = [:]
        aveItemPrices.each {
            aveItemPriceMap[it.priceOf] = it
        }
        aveItemPriceMap
    }
  def calculateMenuPlanCosts(MenuPlan menuPlan,avePrice) {

    menuPlan.weeks.each {it ->
      Week week = it;
      week.days.each {day ->
        day.meals.each {meal ->
          meal.cost = 0
          meal.s()
        }
        day.cost = 0
        day.s()
      }
      week.cost = 0
      week.s()
    }
    menuPlan.cost = 0
    menuPlan.s()

    menuPlan.weeks.each {week ->
      week.days.each {day ->
        day.meals.each {meal ->
          meal.items.each {item ->
              if(avePrice[item]?.price?.price) {
                  meal.cost += avePrice[item]?.price?.price
              }
          }
          meal.s()
          println "Meal total:${meal.cost}"
          day.cost += meal.cost
        }
        println "Day total:${day.cost}"
        day.s()
        week.cost += day.cost
      }
      println "Weekly total total:${week.cost}"
      week.s()
      menuPlan.cost += week.cost
    }
    menuPlan.s()
    println "Menu Plan total:${menuPlan.cost}"
  }

    def calculateRecipeCosts(List<Recipe> recipes) {
        def c = ItemPrice.createCriteria()
        def itemPrices = c {
            priceOf {
                'in'('id',recipes*.id)
            }
        }
        def avePrices = [:]
        itemPrices.each {
            avePrices[it.priceOf]=it
        }
        avePrices
        
    }
}