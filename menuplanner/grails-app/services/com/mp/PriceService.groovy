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
import com.mp.domain.Metric

/**
 * User: Brent Fisher
 * Date: Mar 10, 2012
 * Time: 11:06:48 AM
 */

public class PriceService {
  def standardConversionService
  
  def calculateRecipePrices(recParty){
    ItemPrice.findAllByType(PriceType.AVE)*.delete()
    ItemPrice.findAllByType(PriceType.HIGH)*.delete()
    ItemPrice.findAllByType(PriceType.LOW)*.delete()
    Unit oz = Unit.findByName("Ounce")
    Quantity normalizedQuantity = standardConversionService.getQuantityToSaveFloat(1.0f, oz)
    normalizedQuantity.s()
    def now = new Date()
    Product.list().each { product1 ->
      Grocer.list().each { grocer1 ->
        def c = ItemPrice.createCriteria()
        def itemPrices = c {
            eq("type",PriceType.SINGLE)
            grocer {
              eq("id",grocer1.id)
            }
            priceOf {
              eq("id",product1.id)
            }          
        }
        if(itemPrices && itemPrices.size()>0){
          def prices = itemPrices.collect {it.price.price / it.price.quantity.value}

          Price avePrice = new Price(price: prices.sum() / prices.size(), quantity: normalizedQuantity)
          avePrice.s()
          ItemPrice aveIPrice = new ItemPrice(recordedOn:now,priceOf:product1,type:PriceType.AVE,
             recordedBy:recParty,grocer:grocer1,price:avePrice)
          aveIPrice.s()
          Price maxPrice = new Price(price: prices.max(), quantity: normalizedQuantity)
          maxPrice.s()
          ItemPrice aveMaxPrice = new ItemPrice(recordedOn:now,priceOf:product1,type:PriceType.AVE,
             recordedBy:recParty,grocer:grocer1,price:maxPrice)
          aveMaxPrice.s()
          Price minPrice = new Price(price: prices.min(), quantity: normalizedQuantity)
          minPrice.s()
          ItemPrice aveMinPrice = new ItemPrice(recordedOn:now,priceOf:product1,type:PriceType.AVE,
             recordedBy:recParty,grocer:grocer1,price:minPrice)
          aveMinPrice.s()
          
        }
      }
    }
    // now that all of the product prices are known calculate the recipe prices?
    // per grocer, and for all grocers
    Quantity oneEach = standardConversionService.getQuantityToSaveFloat(1.0f,Unit.findByName("Each"))
    oneEach.s()
    Recipe.list().each { it ->
       Recipe recipe = it
      Grocer.list().each { grocer1 ->
        BigDecimal grocerTotalPrice = 0.0;
        recipe.ingredients.each { ri ->
          RecipeIngredient recipeIngredient = ri
          def c = ItemPrice.createCriteria()
           def aveItemPrices = c {
            eq("type",PriceType.AVE)
            grocer {
              eq("id",grocer1.id)
            }
            priceOf {
              eq("id",ri.ingredient.id)
            }
          }


          if(aveItemPrices && aveItemPrices.size()>0){
            ItemPrice aveItemPrice = aveItemPrices.last()
             // now take the price (oz) by the quantity of the ingredient (in oz)
             BigDecimal ingPrice

              if(recipeIngredient.quantity) ingPrice = aveItemPrice.price.price * recipeIngredient.quantity.value
              else ingPrice = aveItemPrice.price.price
            
            grocerTotalPrice += ingPrice
          }
        }
        Price price = new Price(price:grocerTotalPrice,quantity:oneEach)
        price.s()
        ItemPrice grcTotalPrice = new ItemPrice(recordedBy:recParty,recordedOn:now,priceOf:recipe,price:price,type:PriceType.AVE,grocer:grocer1)
        grcTotalPrice.s()
      }
    }

  }
  def recordReceipt(ReceiptCO receipt) {
    Grocer grocer = Grocer.get(receipt.grocerId)
    Long itemCount = 0;
    def now = new Date()
    def enteredPrices = receipt.itemProductIds.eachWithIndex {productId, index->
      if(productId?.length()>0){
        itemCount ++
        def price = new Price(price:receipt.itemPrices[index].toBigDecimal(),
                        quantity:new Quantity(value:receipt.itemQuantities[index]?.toFloat(),
                                              unit:Unit.get(receipt.itemUnitIds[index]?.toLong())).s()).s()
        ItemPrice itemPrice = new ItemPrice(recordedOn: now,
                priceOf: Item.get(productId?.toLong()),
                price: price,
                type: PriceType.SINGLE,
                recordedBy:UserTools.currentUser.party,
                grocer:grocer).s()
      }
    }
    return itemCount
  }

  def calculateMenuPlanCosts(MenuPlan menuPlan) {

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
            meal.cost += item.avePrice ? item.avePrice.price : 0
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

}