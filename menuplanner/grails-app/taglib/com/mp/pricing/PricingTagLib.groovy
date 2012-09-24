package com.mp.pricing

import com.mp.domain.Permission
import com.mp.domain.Recipe
import com.mp.domain.access.PermissionLevel
import com.mp.domain.access.SecurityRole
import com.mp.domain.party.Party
import com.mp.tools.CurrencyUtils
import com.mp.tools.UnitUtil
import com.mp.domain.pricing.ItemPrice
import com.mp.domain.RecipeIngredient
import com.mp.domain.pricing.IngredientPrice
import org.hibernate.Criteria

class PricingTagLib {

    static namespace = "pricing"

    def price = {attrs, body ->
        ItemPrice itemPrice = attrs['itemPrice']
        RecipeIngredient ingredient = attrs['ingredient']
        def output = ""
        if (ingredient) {
            def c = IngredientPrice.createCriteria()
            def prices = c {
                priceOf {
                    eq('id',ingredient.id)
                }
            }
            def ip
            if (prices?.size()>0){
               ip = prices.first()
            }
            if (ip?.price?.quantity?.unit) {
                output = """\$${CurrencyUtils.getRoundedAmount(ip.price.price)} for ${UnitUtil.getQuantityValueString(ip.price.quantity)}
        ${ip.price.quantity.unit.symbol}
        at """
            }
        }
        output += """ \$${CurrencyUtils.getRoundedAmount(itemPrice.price.price)} for
        ${UnitUtil.getQuantityValueString(itemPrice.price.quantity)}
${fieldValue(bean: itemPrice, field: "price.quantity.unit.symbol")} """
        out << output
    }

}
