package com.mp

import com.mp.domain.*
import static com.mp.MenuConstants.*

class MasterDataBootStrapService {

    boolean transactional = true

    public void populateSystemOfUnits() {
        SystemOfUnit systemOfUnitsUsa = new SystemOfUnit(systemName: SYSTEM_OF_UNIT_USA, standardizationBody: SYSTEM_OF_UNIT_USA_STANDARDIZATION_BODY).s()
        SystemOfUnit systemOfUnitsMetric = new SystemOfUnit(systemName: SYSTEM_OF_UNIT_METRIC, standardizationBody: SYSTEM_OF_UNIT_METRIC_STANDARDIZATION_BODY).s()
    }

    public void populateTimeUnits() {

        SystemOfUnit systemOfUnitsUsa = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
        SystemOfUnit systemOfUnitsMetric = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_METRIC)

        Time timeHour = new Time(name: TIME_UNIT_HOURS, symbol: TIME_UNIT_HOURS_SYMBOL, definition: "This is definition for hours", metricType: MetricType.TIME)
        timeHour.addToSystemOfUnits(systemOfUnitsMetric)
        timeHour.addToSystemOfUnits(systemOfUnitsUsa)
        timeHour.s()

        Time timeMinutes = new Time(name: TIME_UNIT_MINUTES, symbol: TIME_UNIT_MINUTES_SYMBOL, definition: "This is definition for minuts", metricType: MetricType.TIME)
        timeMinutes.addToSystemOfUnits(systemOfUnitsMetric)
        timeMinutes.addToSystemOfUnits(systemOfUnitsUsa)
        timeMinutes.s()
    }

    public void populateUnitsAndStandardConversions() {

        SystemOfUnit systemOfUnitsUsa = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
        SystemOfUnit systemOfUnitsMetric = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_METRIC)

        Unit drop = new Unit(name: UNIT_DROP, symbol: UNIT_DROP_SYMBOL, definition: "This is definition for drop", metricType: MetricType.METRIC)
        drop.addToSystemOfUnits(systemOfUnitsUsa)
        drop.s()

        Unit milliLitres = new Unit(name: UNIT_MILLI_LITRE, symbol: UNIT_MILLI_LITRE_SYMBOL, definition: "This is definition for millilitre", metricType: MetricType.METRIC)
        milliLitres.addToSystemOfUnits(systemOfUnitsUsa)
        milliLitres.s()
        Unit teaspoon = new Unit(name: UNIT_TEA_SPOON, symbol: UNIT_TEA_SPOON_SYMBOL, definition: "This is definition for Teaspoon", metricType: MetricType.METRIC)
        teaspoon.addToSystemOfUnits(systemOfUnitsUsa)
        teaspoon.s()
        Unit tableSpoon = new Unit(name: UNIT_TABLE_SPOON, symbol: UNIT_TABLE_SPOON_SYMBOL, definition: "This is definition for Tablespoon", metricType: MetricType.METRIC)
        tableSpoon.addToSystemOfUnits(systemOfUnitsUsa)
        tableSpoon.s()
        Unit fluidOunce = new Unit(name: UNIT_FLUID_OUNCE, symbol: UNIT_FLUID_OUNCE_SYMBOL, definition: "This is definition for Fluid ounce", metricType: MetricType.METRIC)
        fluidOunce.addToSystemOfUnits(systemOfUnitsUsa)
        fluidOunce.s()
        Unit jigger = new Unit(name: UNIT_JIGGER, symbol: UNIT_JIGGER_SYMBOL, definition: "This is definition for jigger", metricType: MetricType.METRIC)
        jigger.addToSystemOfUnits(systemOfUnitsUsa)
        jigger.s()
        Unit cup = new Unit(name: UNIT_CUP, symbol: UNIT_CUP_SYMBOL, definition: "This is definition for Cup", metricType: MetricType.METRIC)
        cup.addToSystemOfUnits(systemOfUnitsUsa)
        cup.s()
        Unit pint = new Unit(name: UNIT_PINT, symbol: UNIT_PINT_SYMBOL, definition: "This is definition for Pint", metricType: MetricType.METRIC)
        pint.addToSystemOfUnits(systemOfUnitsUsa)
        pint.s()
        Unit fifth = new Unit(name: UNIT_FIFTH, symbol: UNIT_FLUID_OUNCE_SYMBOL, definition: "This is definition for fifth", metricType: MetricType.METRIC)
        fifth.addToSystemOfUnits(systemOfUnitsUsa)
        fifth.s()
        Unit quart = new Unit(name: UNIT_QUART, symbol: UNIT_QUART_SYMBOL, definition: "This is definition for Quart", metricType: MetricType.METRIC)
        quart.addToSystemOfUnits(systemOfUnitsUsa)
        quart.s()
        Unit gallon = new Unit(name: UNIT_GALLON, symbol: UNIT_GALLON_SYMBOL, definition: "This is definition for Gallon", metricType: MetricType.METRIC)
        gallon.addToSystemOfUnits(systemOfUnitsUsa)
        gallon.s()

        new StandardConversion(sourceUnit: milliLitres, targetUnit: drop, conversionFactor: UNIT_DROP_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: teaspoon, conversionFactor: UNIT_TEA_SPOON_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: tableSpoon, conversionFactor: UNIT_TABLE_SPOON_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: fluidOunce, conversionFactor: UNIT_FLUID_OUNCE_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: jigger, conversionFactor: UNIT_JIGGER_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: cup, conversionFactor: UNIT_CUP_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: pint, conversionFactor: UNIT_PINT_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: fifth, conversionFactor: UNIT_FIFTH_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: quart, conversionFactor: UNIT_QUART_CONVERSION_FACTOR).s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: gallon, conversionFactor: UNIT_GALLON_CONVERSION_FACTOR).s()
    }

    public void populateNutrients() {

        Unit nutrientUnit = Unit.findByName(UNIT_MILLI_LITRE)

        new Nutrient(name: NUTRIENT_CALORIES, preferredUnit: nutrientUnit).s()
        new Nutrient(name: NUTRIENT_TOTAL_FAT, preferredUnit: nutrientUnit).s()
        new Nutrient(name: NUTRIENT_SATURATED_FAT, preferredUnit: nutrientUnit).s()
        new Nutrient(name: NUTRIENT_CHOLESTEROL, preferredUnit: nutrientUnit).s()
        new Nutrient(name: NUTRIENT_SODIUM, preferredUnit: nutrientUnit).s()
        new Nutrient(name: NUTRIENT_CARBOHYDRATES, preferredUnit: nutrientUnit).s()
        new Nutrient(name: NUTRIENT_FIBER, preferredUnit: nutrientUnit).s()
        new Nutrient(name: NUTRIENT_PROTEIN, preferredUnit: nutrientUnit).s()
    }
}