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

/**
 * User: Brent Fisher
 * Date: Mar 10, 2012
 * Time: 11:06:48 AM
 */

public class PriceService {
  def calculateRecipePrices(){
    ItemPrice.findAllByType(PriceType.AVE)*.delete()
    ItemPrice.findAllByType(PriceType.HIGH)*.delete()
    ItemPrice.findAllByType(PriceType.LOW)*.delete()
    def now = new Date()
    Product.list().each { product ->
      Grocer.list().each { grocer ->
        def avePrice = 0.0;
        def prices = ItemPrice.findAllByTypeAndByGrocerAndByPriceOf(PriceType.SINGLE,grocer,product)
        def count = prices?.size()
        prices.each { itemPrice ->
          
        }
        ItemPrice ip = new ItemPrice(recordedOn:now,priceOf:product,type:PriceType.AVE,
        recordedBy:UserTools.currentUser.party,grocer:grocer,price:avePrice)
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