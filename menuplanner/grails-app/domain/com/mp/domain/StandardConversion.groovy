package com.mp.domain

import org.apache.commons.math.fraction.Fraction
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import static com.mp.MenuConstants.*

class StandardConversion {
    Unit sourceUnit
    Unit targetUnit
    BigDecimal conversionFactor

    static mapping = {
        sourceUnit fetch: 'join'
        targetUnit fetch: 'join'
        conversionFactor(sqlType: 'numeric(20, 10)', fetch: 'join')
        cache true
    }
}