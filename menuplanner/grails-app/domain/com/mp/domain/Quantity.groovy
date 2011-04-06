package com.mp.domain

import static com.mp.MenuConstants.*
import org.apache.commons.math.fraction.Fraction

class Quantity {

  Float value
  Unit unit
  Unit savedUnit

  String getSmall() {
    String amount
    String result
    amount = StandardConversion.getQuantityValueString(this)
    result = "${amount ? amount : ''}${unit ? (unit?.small) : ''}"
    return result.trim()

  }

  String toString() {
    String amount
    String result
    amount = StandardConversion.getQuantityValueString(this)
    result = "${amount ? amount : ''}${unit ? (' ' + unit?.symbol) : ''}"
    return result.trim()
  }

  static transients = ['small']

  static constraints = {
    value(nullable: true, blank: true)
    unit(nullable: true, blank: true)
    savedUnit(nullable: true, blank: true)
  }

  static mapping = {
    value(sqlType: 'numeric(25, 10)')
    columns {
      unit fetch: 'join'
      savedUnit fetch: 'join'
    }
    cache true
  }

  public static Quantity addTime(Quantity q1, Quantity q2) {
    Quantity sum = new Quantity()
    Float totalMinutes = 0.0f
    totalMinutes += (q1?.value) ? q1?.value : 0.0f
    totalMinutes += (q2?.value) ? q2?.value : 0.0f
    sum.unit = q1.savedUnit
    sum.value = totalMinutes
    return sum
  }

//    //TODO; Implement this
//
//    public static Quantity multiply(Quantity quantity1, Quantity quantity2) {
//        List<StandardConversion> standardConversions = StandardConversion.list([cache: true])
//        return add(quantity1, quantity2, standardConversions)
//    }

  public static Quantity add(Quantity quantity1, Quantity quantity2, List<StandardConversion> standardConversions = []) {
    if (!quantity1) { return quantity2 }
    if (!quantity2) { return quantity1 }
    String usVal1 = (quantity1.value) ? (StandardConversion.getQuantityValueString(quantity1, 1.0f, standardConversions)) : ''
    Unit displayUnit1 = quantity1.unit
    String usVal2 = (quantity2.value) ? (StandardConversion.getQuantityValueString(quantity2, 1.0f, standardConversions)) : ''
    Unit displayUnit2 = quantity2.unit
    Quantity resultantQuantity
    usVal1 = usVal1 ? usVal1.replaceAll(',', '') : ''
    usVal2 = usVal2 ? usVal2.replaceAll(',', '') : ''
    Quantity q1 = StandardConversion.getQuantityToSave(usVal1, displayUnit1, 1.0f, standardConversions)
    Quantity q2 = StandardConversion.getQuantityToSave(usVal2, displayUnit2, 1.0f, standardConversions)
    if (q1 != null && (q1?.savedUnit == q2?.savedUnit)) {
      resultantQuantity = new Quantity()
      Unit displayUnit = q1?.unit
      if (displayUnit) {
        if (standardConversions.find {it.sourceUnit == q1?.unit}?.conversionFactor > standardConversions.find {it.sourceUnit == q2?.unit}?.conversionFactor) {
          displayUnit = q2?.unit
        }
      }
      if (q1.value && q2.value) {
        resultantQuantity?.value = q1?.value + q2.value
      } else {
        resultantQuantity?.value = (q1.value) ? q1.value : q2.value
      }
      resultantQuantity?.savedUnit = q1?.savedUnit
      resultantQuantity?.unit = displayUnit
    }
    return resultantQuantity
  }

  public String toReadableTimeString() {
    Unit minutes = Unit.findByName(TIME_UNIT_MINUTES)
    Unit hours = Unit.findByName(TIME_UNIT_HOURS)

    if (value >= 60f) {
      Integer integerValue = value.toInteger()
      String readableString = (value / 60).toInteger() + " ${hours.symbol}" + ((integerValue % 60) ? " ${(integerValue % 60)} ${minutes.symbol}" : '')
      return readableString
    }
    return this.toString()
  }

  public String toBiggestUnitString(Float density) {
    if (!unit || !unit.isConvertible) {return this.toString()}
    String finalString = ''
    if (!value) {return finalString}
    Float valueWithDensity = ((unit?.isWeightUnit) ? (value * density) : value).toFloat()
    StandardConversion standardConversion = StandardConversion.createCriteria().get {
      eq('targetUnit', savedUnit)
      le('conversionFactor', valueWithDensity)
      sourceUnit {
        eq('isWeightUnit', unit.isWeightUnit)
        eq('isConvertible', true)
        systemOfUnits {
          'in'('id', unit.systemOfUnits*.id)
        }
      }
      order("conversionFactor", "desc")
      maxResults(1)
      cache true
    }
    if (standardConversion) {
      Fraction f3 = new Fraction((valueWithDensity / standardConversion.conversionFactor.toFloat()))
      finalString = (f3.myFormatUsingProperFractionFormat() + " " + standardConversion.sourceUnit?.symbol)
    } else {
      finalString = toString()
    }
    return finalString
  }
}
