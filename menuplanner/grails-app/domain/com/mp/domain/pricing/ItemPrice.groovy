package com.mp.domain.pricing

import com.mp.domain.Item
import com.mp.domain.party.Party
import com.mp.domain.party.Grocer


public class ItemPrice {
  Date recordedOn
  Item priceOf
  Price price
  PriceType type
  Party recordedBy
  Grocer grocer
}
