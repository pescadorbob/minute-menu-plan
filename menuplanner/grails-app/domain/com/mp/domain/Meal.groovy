package com.mp.domain

import com.mp.domain.pricing.Price

class Meal {

    MealType type
    List<Item> items = []
    BigDecimal cost
    static belongsTo = [Day, QuickFill]
    static hasMany = [items: Item]

    static mapping = {
        items fetch: 'join'
    }

    static constraints = {
      cost(nullable:true)
    }

}
