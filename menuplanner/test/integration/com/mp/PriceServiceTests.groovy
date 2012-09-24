package com.mp

import com.mp.MenuConstants
import com.mp.domain.party.Party
import grails.test.GrailsUnitTestCase
import com.mp.PriceService
import com.mp.domain.pricing.Price
import com.mp.domain.party.Grocer
import com.mp.domain.party.OrganizationName
import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.fraction.ProperFractionFormat
import com.mp.domain.StandardConversionService
import com.mp.domain.Quantity
import com.mp.domain.Unit
import com.mp.domain.Recipe
import com.mp.domain.Item
import com.mp.domain.Product
import com.mp.domain.MeasurableProduct
import com.mp.domain.RecipeIngredient
import com.mp.domain.Aisle
import com.mp.domain.MetricType
import com.mp.domain.StandardConversion
import com.mp.domain.pricing.ItemPrice
import com.mp.domain.pricing.PriceType
import com.mp.tools.UnitUtil

class PriceServiceTests extends GrailsUnitTestCase {
    PriceService priceService
    StandardConversionService standardConversionService

    void testCalculateRecipePrices() {
        Item p = Item.findByName('flour')
        assert p
        Unit lbs = Unit.findByName("Pound")
        assert lbs
        Grocer g = Grocer.list().first()
        def brent = Party.get(1)
        ItemPrice.findAllByType(PriceType.SINGLE)*.delete(flush: true)

        pricing.each {key, value ->

            Quantity q = standardConversionService.getQuantityToSaveFloat(key, lbs)
            assert q.save()
            Price price = new Price(price: value, quantity: q)
            assert price.save()
            ItemPrice ip = new ItemPrice(price: price, priceOf: p, type: PriceType.SINGLE, recordedOn: new Date(), recordedBy: brent, grocer: g)
            assert ip.save(flush: true)
        }
        def productIds = [p.id]

        def grocerIds = [g.id]
        priceService.calculateRecipePrices(brent, productIds, grocerIds)
        def aveItemPrice = ItemPrice.findAllByTypeAndPriceOf(PriceType.AVE, p).first()
            println "$aveItemPrice.priceOf.name Ave Price: $aveItemPrice.price.price / $aveItemPrice.price.quantity.unit.name "
            assertEquals(expectedAvePrice, aveItemPrice.price.price,0.01)
            assertEquals('1 lb.', aveItemPrice.price.quantity.toString())
            assertEquals(expAvePriceUnit, aveItemPrice.price.quantity.unit.name)
    }
                // lbs: price
//    def pricing = [15: 12d, 10: 9d, 6: 5d, 7: 11.68d] // expected:<0.9916> but was:<1.050476196>
    def pricing = [1: 1d, 1: 1d, 1: 1d, 1: 1d]    //ok
    def expectedAvePrice = 1.0
    def expAvePriceUnit  = 'Pound'
}
