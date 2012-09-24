package com.mp.domain

import static com.mp.MenuConstants.*
import com.mp.tools.UserTools
import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.util.MathUtils

class StandardConversionService {

  // every unit has a conversion to target mL

  public static Quantity getQuantityToSave(String amountFraction, Unit displayUnit, Float density = 1.0f, List<StandardConversion> standardConversions = []) {
    Float amount
    if (amountFraction) {
      if (amountFraction.contains('/')) {
        amount = new Fraction(amountFraction)?.floatValue()
      } else {
        amount = amountFraction?.toFloat()
      }
    }
    getQuantityToSaveFloat(amount, displayUnit, density, standardConversions)
  }

  public static Quantity getQuantityToSaveFloat(BigDecimal amount, Unit displayUnit, BigDecimal density = 1.0d, List<StandardConversion> standardConversions = []) {
    Quantity result
    if (null != amount) {
      result = new Quantity()
      result.value = amount
    }
    if (displayUnit && amount && result) {
      def milliliterUnit = Unit.findByName('Milliliter')
      StandardConversion standardConversion = (standardConversions) ?
        standardConversions.find {it.sourceUnit.id == displayUnit.id} :
      // because most units are stored as milliliters, and because units such as each, large, small
      // etc. also has a dozen conversion factor, this part must find the milliliter conversion to
      // store the quantity value.
      // if however, the convert to milliliter isn't found, then the value is just stored AS IS
        StandardConversion.findBySourceUnitAndTargetUnit(displayUnit, milliliterUnit)
      BigDecimal conversionFactor = standardConversion ? standardConversion.conversionFactor : 1.0d
      if (displayUnit.isWeightUnit) {
        result.value = ((amount * conversionFactor) / density)
      } else {
        result.value = amount * conversionFactor
      }
      result.savedUnit = standardConversion ? standardConversion?.targetUnit : displayUnit
      result.unit = displayUnit
    }
    return result
  }

  boolean transactional = true
  def conversions = []

  public static Float getQuantityValueAsFloat(Quantity sourceQuantity, BigDecimal density = 1.0d, List<StandardConversion> standardConversions = []) {
    Float result;
    Float floatValue = sourceQuantity?.value
    Unit unit = sourceQuantity?.unit
    if (!unit) {
      result = floatValue ? floatValue : null
    } else {
      if (floatValue != null) {
        StandardConversion standardConversion
        standardConversion = (standardConversions) ? standardConversions.find {it.sourceUnit.id == unit.id} : StandardConversion.findBySourceUnitAndTargetUnit(unit,Unit.findByName('Milliliter'))
        Float conversionFactor = (standardConversion) ? standardConversion.conversionFactor : 1.0f
        if (unit.isWeightUnit) {
          floatValue = (floatValue * density) / conversionFactor
        }
        else {
          floatValue = (floatValue / conversionFactor)
        }
          result = floatValue
      }
    }
    result
  }

  public static String getQuantityValueString_old(Quantity sourceQuantity, Float density = 1.0f, List<StandardConversion> standardConversions = []) {
    String result = ''
    Float floatValue = sourceQuantity?.value
    Unit unit = sourceQuantity?.unit
    if (!unit) {
      result = floatValue ? new Fraction(floatValue).myFormatUsingProperFractionFormat() : ''
    } else {
      if (floatValue != null) {
        StandardConversion standardConversion
        standardConversion = (standardConversions) ? standardConversions.find {it.sourceUnit.id == unit.id} : StandardConversion.findBySourceUnit(unit)
        Float conversionFactor = (standardConversion) ? standardConversion.conversionFactor : 1.0f
        if (unit.isWeightUnit) {
          floatValue = (floatValue * density) / conversionFactor
        }
        else {
          floatValue = (floatValue / conversionFactor)
        }
        if (unit.belongsToUsaSystem()) {
          result = new Fraction(floatValue).myFormatUsingProperFractionFormat()
        } else {
          result = floatValue ? floatValue?.toString() : ''
        }
      }
    }
    return result
  }

}
