package com.mp.domain

import static com.mp.MenuConstants.*
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import org.apache.commons.math.fraction.Fraction

class Quantity {

    Float value
    Unit unit
    Unit savedUnit

    String toString() {
        String amount
        String result
        amount = StandardConversion.getQuantityValueString(this)
        result = "${amount ? amount : ''}${unit ?(' '+ unit?.symbol) : ''}"
        return result.trim()
    }

    static constraints = {
        value(nullable: true, blank: true)
        unit(nullable: true, blank: true)
        savedUnit(nullable: true, blank: true)
    }

//TODO: Make it generic unit conversion method add(q1, q2).

    public static Quantity addTime(Quantity q1, Quantity q2) {
        Quantity sum = new Quantity()
        Float totalMinutes = 0.0
        if (q1?.value) {
            totalMinutes += q1?.value
        }
        if (q2?.value) {
            totalMinutes += q2?.value
        }
        sum.unit = q1.savedUnit
        sum.value = totalMinutes
        return sum
    }

    public static Quantity add(Quantity quantity1, Quantity quantity2){
      Quantity quantity
      return quantity

    }

    public Quantity plus(Quantity quantity){
        String usVal1 =(this.value)?(StandardConversion.getQuantityValueString(this)):''
        Unit displayUnit1=this.unit
        String usVal2 = (quantity.value)?(StandardConversion.getQuantityValueString(quantity)):''
        Unit displayUnit2=quantity.unit
        Quantity resultantQuantity = new Quantity()
        Quantity q1 = StandardConversion.getQuantityToSave(usVal1, displayUnit1)
        Quantity q2 = StandardConversion.getQuantityToSave(usVal2, displayUnit2)
        if (q1.toString() && !q2.toString()) { return q1 }
        if (!q1.toString() && q2.toString()) { return q2 }
        if (q1?.savedUnit == q2?.savedUnit) {
            Unit displayUnit = q1?.unit
            if (displayUnit) {
                if (StandardConversion.findBySourceUnit(q1?.unit)?.conversionFactor > StandardConversion.findBySourceUnit(q2?.unit)?.conversionFactor) {
                    displayUnit = q2?.unit
                }
            }
            if(q1?.value && q2?.value){
                resultantQuantity?.value = q1?.value + q2?.value
            }else if(q1?.value){
                resultantQuantity?.value = q1?.value
            }else if(q2?.value){
                resultantQuantity?.value = q2?.value
            }
            resultantQuantity?.savedUnit = q1?.savedUnit
            resultantQuantity?.unit = displayUnit
        }
        return resultantQuantity
    }
}