package com.mp.domain.pricing

import com.mp.domain.RecipeIngredient
import com.mp.domain.party.Party

public class IngredientPrice {
    Date recordedOn
    RecipeIngredient priceOf
    Price price
    PriceType type
    Party recordedBy

}
