package com.mp.tools

import com.mp.domain.ndb.ItemNutritionLink
import com.mp.domain.*

/**
 */

public class UnitUtil {
  /**
   * converts a quantity of a recipe ingredient to a different unit.
   * both units must be convertible.
   * I.e. there must be a Standard Conversion, otherwise, the same quantity will be
   * returned. Both units must be convertible, else the same unit is passed back.
   */
  static def Quantity convert(Quantity fromQ, PreparationMethod fromPrep,
                              Item fromProduct, Unit targetUnit) {
    if (!fromQ?.unit || !fromQ?.unit.isConvertible) {return fromQ}

    ItemNutritionLink link
    if (fromPrep) {
      link = ItemNutritionLink.findByProductAndByPrep(fromProduct, fromPrep)
    } else {
      link = ItemNutritionLink.findByProduct(fromProduct)
    }
    // if there is no nutrition link, then the conversion can't be done.  in this case
    // the from is returned as a fallback
    if (!link?.unit) {
      return tryDirectConversion(targetUnit, fromQ)
    } else {

      def milliliterUnit = Unit.findByName('Milliliter')

//      StandardConversion convTomL =
//      StandardConversion.findByTargetUnitAndSourceUnit(milliliterUnit, link.unit)

      StandardConversion mLconvToFromUnit =
         StandardConversion.findByTargetUnitAndSourceUnit(milliliterUnit, fromQ.unit)

      // since there is a a nutrition link then take the weight in grams of the linked measurement.
      // If From: 275 T; To:Kilograms
      // E.g. link.unit = Cups; amount = 4; weightInGramsOfUnit = 512; 4 Cups = 512 grams
      // fromQ.value = 275
      // fromQ.unit = Tablespoons;
      // targetUnit = grams
      // fromQ is in mL since all quantities are stored by mL
      // old
      // (fromQ) * (gmWgt/amount link.unit) * (link.unit conversion to mL) * (mL conversion to from unit)
      // (275 T) * (512g/4c               ) * (c/mL                      ) * (mL/T                      )
      // new
      // (fromQ)        * (gmWgt/amount link.unit) * (link.unit conversion to mL)
      // (15*(275 T)mL) * (512g/4c               ) * (240mL/1c                  )
      float fromQInGrams
      // gotta figure out the saved value vs. the displayed value.
      // fromQ.value is in mL
      if (fromQ?.value && link?.nutrition?.gmWgt &&
              mLconvToFromUnit?.conversionFactor && link?.nutrition?.amount ) {
        fromQInGrams =
          fromQ.value * (link.nutrition.gmWgt * (1/mLconvToFromUnit.conversionFactor)) /
                (link.nutrition.amount)
      } else if (fromQ && link?.nutrition.gmWgt && link?.unit.id == fromQ.savedUnit.id) {
        // if there was no conversion but the from unit is the same as the link unit
        // then grams can be calculated.
        fromQInGrams = fromQ.value * link.nutrition.gmWgt
      } else {
        println("Unable to calculate fromQInGrams")
        return tryDirectConversion(targetUnit, fromQ)
      }
      def targetQuantity = convertGramsToTargetUnit(fromQInGrams, targetUnit)
      if (targetQuantity){
        return targetQuantity
      }else{
        return tryDirectConversion(targetUnit, fromQ)
      }
    }
  }

  private static def tryDirectConversion(Unit targetUnit, Quantity fromQ) {
    StandardConversion directConversion =
      StandardConversion.findByTargetUnitAndSourceUnit(targetUnit, fromQ.unit)
    if (directConversion) {
      float targetValue = fromQ.value * directConversion.conversionFactor
      Quantity targetQuantity = new Quantity(value: targetValue, savedUnit:targetUnit, unit: targetUnit)
      targetQuantity.save()
      return targetQuantity
    } else {
      StandardConversion inverseConversion =
        StandardConversion.findByTargetUnitAndSourceUnit(fromQ.unit, targetUnit)
      if (inverseConversion) {
        float targetValue = fromQ.value * (1 / inverseConversion.conversionFactor)
        Quantity targetQuantity = new Quantity(value: targetValue,savedUnit:targetUnit, unit: targetUnit)
        targetQuantity.save()
        return targetQuantity
      }
    }

    return fromQ
  }

  static convertGramsToTargetUnit(float fromQInGrams, Unit targetUnit) {
    def milliliterUnit = Unit.findByName('Milliliter')
    def gramsUnit = Unit.findByName('Grams')


    StandardConversion convTomL =
    StandardConversion.findByTargetUnitAndSourceUnit(milliliterUnit, gramsUnit)

    StandardConversion mLconvToFromUnit =
    StandardConversion.findByTargetUnitAndSourceUnit(milliliterUnit, targetUnit)

    float targetQAmount
    if (gramsUnit && targetUnit && convTomL && mLconvToFromUnit) {
      targetQAmount = (fromQInGrams
              * (1 / mLconvToFromUnit.conversionFactor) * convTomL.conversionFactor)
      return StandardConversionService.getQuantityToSaveFloat(targetQAmount, targetUnit)
    } else {
      println("Unable to calculate Target Unit ${fromQInGrams} ${targetUnit}")
    }

  }

  /**
   * takes a value like 0.14 pounds and converts it to ounces based on value of the fraction of the
   * unit being less than 1/4 of the unit.  I.e. it makes more sense to say 1 ounce of such and such, than 1/16 of a pound,
   * but 1/4 pound seems totally reasonable.
   */
  static Quantity normalizeQuantity(Quantity quantity) {
    Quantity result = quantity
    Float quantityValue = StandardConversionService.getQuantityValueAsFloat(quantity)
    Quantity normalize =downConvert(quantityValue,quantity.unit)
    if(normalize?.unit == quantity?.unit){
      normalize = upConvert(quantityValue,quantity.unit)
      if(normalize?.unit != quantity.unit){
        result = normalize
      }

    } else {
      result = normalize
    }
    result
  }

  public static Quantity downConvert(float v, Unit unit) {
    if(v < 1.0/8.0){
      // do the conversion down
      UnitGroup group = UnitGroup.getGroup(unit)
      if(!group) return StandardConversionService.getQuantityToSaveFloat(v,unit)
      Unit down = group.down(unit)
      if(!down) return StandardConversionService.getQuantityToSaveFloat(v,unit)
      StandardConversion downConvert = StandardConversion.findBySourceUnitAndTargetUnit(unit,down)
      if(downConvert){
        float newQuantityValue = v * downConvert.conversionFactor
        return UnitUtil.downConvert(newQuantityValue,down)
      } else {
        return StandardConversionService.getQuantityToSaveFloat(v,unit)
      }
    } else {
      return StandardConversionService.getQuantityToSaveFloat(v,unit)
    }

  }

  public static Quantity upConvert(float v, Unit unit) {
    if(v > 1000.0){
      // do the conversion up
      UnitGroup group = UnitGroup.getGroup(unit)
      if(!group) return StandardConversionService.getQuantityToSaveFloat(v,unit)
      Unit up = group.up(unit)
      if(!up) return StandardConversionService.getQuantityToSaveFloat(v,unit)
      StandardConversion upConvert = StandardConversion.findBySourceUnitAndTargetUnit(up,unit)
      if(upConvert){
        float newQuantityValue = v / upConvert.conversionFactor
        return upConvert(newQuantityValue,up)
      } else {
        return StandardConversionService.getQuantityToSaveFloat(v,unit)
      }
    } else {
      return StandardConversionService.getQuantityToSaveFloat(v,unit)
    }
  }
}