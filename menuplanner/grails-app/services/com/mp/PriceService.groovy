package com.mp

import com.mp.domain.MenuPlan
import com.mp.domain.Week

/**
 * User: Brent Fisher
 * Date: Mar 10, 2012
 * Time: 11:06:48 AM
 */

public class PriceService {
    def calculateMenuPlanCosts(MenuPlan menuPlan){

      menuPlan.weeks.each { it ->
        Week week = it;
        week.days.each { day ->
          day.meals.each { meal ->
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

      menuPlan.weeks.each { week ->
        week.days.each { day ->
          day.meals.each { meal ->
             meal.items.each { item ->
              meal.cost += item.avePrice?item.avePrice.price:0  
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