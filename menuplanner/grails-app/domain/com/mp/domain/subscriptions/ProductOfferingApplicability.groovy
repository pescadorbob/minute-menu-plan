package com.mp.domain.subscriptions

class ProductOfferingApplicability {

    ProductOffering availableFor
    String applicableFrom //purchaseDate
    String applicableFromDescription
    String applicableThru //purchaseDate + 1m
    String applicableThruDescription //purchaseDate + 1m
    static belongsTo = [availableFor: ProductOffering]

    static constraints = {
    }
}
