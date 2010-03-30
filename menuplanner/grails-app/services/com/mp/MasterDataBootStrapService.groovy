package com.mp

import com.mp.domain.*
import static com.mp.MenuConstants.*

class MasterDataBootStrapService {

    boolean transactional = true

    //TODO: Bootstrap Units for Nutrients
    public void populateNutrients() {
        new Nutrient(name: NUTRIENT_CALORIES).s()
        new Nutrient(name: NUTRIENT_TOTAL_FAT).s()
        new Nutrient(name: NUTRIENT_SATURATED_FAT).s()
        new Nutrient(name: NUTRIENT_CHOLESTEROL).s()
        new Nutrient(name: NUTRIENT_SODIUM).s()
        new Nutrient(name: NUTRIENT_CARBOHYDRATES).s()
        new Nutrient(name: NUTRIENT_FIBER).s()
        new Nutrient(name: NUTRIENT_PROTEIN).s()
    }

    public void populateTimeUnits() {
        Time timeHour = new Time(name: TIME_UNIT_HOURS, symbol: TIME_UNIT_HOURS_SYMBOL, definition: "This is definition for hours", metricType: MetricType.TIME).s()
        Time timeMinutes = new Time(name: TIME_UNIT_MINUTES, symbol: TIME_UNIT_MINUTES_SYMBOL, definition: "This is definition for minuts", metricType: MetricType.TIME).s()
        new StandardConversion(sourceUnit: timeHour, targetUnit: timeMinutes, conversionFactor: 60).s()
    }

    public void populateUnitsAndStandardConversions() {
        Unit milliLitres = new Unit(name: UNIT_MILLI_LITRE, symbol: UNIT_MILLI_LITRE_SYMBOL, definition: "This is definition for millilitre", metricType: MetricType.METRIC).s()
        Unit teaspoon = new Unit(name: UNIT_TEA_SPOON, symbol: UNIT_TEA_SPOON_SYMBOL, definition: "This is definition for Teaspoon", metricType: MetricType.METRIC).s()
        Unit dessertSpoon = new Unit(name: UNIT_DESSERT_SPOON, symbol: UNIT_DESSERT_SPOON_SYMBOL, definition: "This is definition for Dessertspoon", metricType: MetricType.METRIC).s()
        Unit tableSpoon = new Unit(name: UNIT_TABLE_SPOON, symbol: UNIT_TABLE_SPOON_SYMBOL, definition: "This is definition for Tablespoon", metricType: MetricType.METRIC).s()
        Unit fluidOunce = new Unit(name: UNIT_FLUID_OUNCE, symbol: UNIT_FLUID_OUNCE_SYMBOL, definition: "This is definition for Fluid ounce", metricType: MetricType.METRIC).s()
        Unit cup = new Unit(name: UNIT_CUP, symbol: UNIT_CUP_SYMBOL, definition: "This is definition for Cup", metricType: MetricType.METRIC).s()
        Unit pint = new Unit(name: UNIT_PINT, symbol: UNIT_PINT_SYMBOL, definition: "This is definition for Pint", metricType: MetricType.METRIC).s()
        Unit quart = new Unit(name: UNIT_QUART, symbol: UNIT_QUART_SYMBOL, definition: "This is definition for Quart", metricType: MetricType.METRIC).s()
        Unit gallon = new Unit(name: UNIT_GALLON, symbol: UNIT_GALLON_SYMBOL, definition: "This is definition for Gallon", metricType: MetricType.METRIC).s()

        //TODO: Move conversion factor values to MenuConstants
        new StandardConversion(sourceUnit: milliLitres, targetUnit: teaspoon, conversionFactor: 4.93).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: tableSpoon, conversionFactor: 14.79).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: fluidOunce, conversionFactor: 29.57).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: cup, conversionFactor: 236.59).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: pint, conversionFactor: 473.18).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: quart, conversionFactor: 946.35).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: gallon, conversionFactor: 3785.41).s()

    }

}