package com.mp.domain

import static com.mp.MenuConstants.*
import java.text.FieldPosition
import org.apache.commons.math.fraction.ProperFractionFormat
import org.apache.commons.math.fraction.Fraction

class Quantity {

    BigDecimal value
    Unit unit
    Unit savedUnit

    static transients = ['fractionValue']

    String toString() {
        if(unit.metricType==MetricType.TIME){
            if(unit?.name==TIME_UNIT_HOURS){
                value=value/60
            }
            return "${value ?: ''} ${unit?.symbol ?: ''}"
        }else if(unit.belongsToUsaSystem()){

            String truncatedValue = ''
            truncatedValue = value?.toString()

            //TODO: Converted value should be displayed to the user
            //truncatedValue = StandardConversion.getUsaString(value, unit)

            return "${truncatedValue} ${unit?.symbol ?: ''}"
        }
    }

    String getFractionValue() {
        if (value) {
            Fraction fraction = new Fraction(value)
            return fraction.myFormatUsingProperFractionFormat()
        } else {
            return ''
        }
    }

    static constraints = {
        value(nullable: true, blank: true)
        unit(nullable: true, blank: true)
        savedUnit(nullable: true, blank: true)
    }

//TODO: Make it generic unit conversion method add(q1, q2).

    public static Quantity addTime(Quantity q1, Quantity q2) {
        Quantity sum = new Quantity()
        sum.value = 0.00
        //Buggy code: Quick fix for hibernate expception during bootstrap
        //sum.unit = Unit.findByName(TIME_UNIT_MINUTES)
        sum.unit = q1.unit
        sum.value = sum.value + ((q1.unit.id == sum.unit.id) ? q1.value : (q1.value * 60))
        sum.value = sum.value + ((q2.unit.id == sum.unit.id) ? q2.value : (q2.value * 60))
        return sum
    }
}