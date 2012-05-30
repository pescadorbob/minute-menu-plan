package com.mp.domain

import com.mp.domain.pricing.Price

class ShoppingIngredient {

  String name
  Aisle aisle
  Item ingredient
  Quantity quantity
  Price predictedPrice

  String toString() {
    return name
  }

  static constraints = {
    aisle(nullable: true, blank: true)
    ingredient(nullable: true)
    predictedPrice(nullable: true)
    quantity(nullable: true)
  }

}
