package com.mp.domain

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

        Time timeMinutes = new Time(name: TIME_UNIT_MINUTES, symbol: TIME_UNIT_MINUTES_SYMBOL, definition: "This is definition for minuts", metricType: MetricType.TIME)
        timeMinutes.addToSystemOfUnits(systemOfUnitsMetric)
        timeMinutes.addToSystemOfUnits(systemOfUnitsUsa)
        timeMinutes.s()
        new StandardConversion(sourceUnit: timeMinutes, targetUnit: timeMinutes, conversionFactor: TIME_UNIT_MINUTES_TO_MINUTES_CONVERSION_FACTOR).s()

        Time timeHour = new Time(name: TIME_UNIT_HOURS, symbol: TIME_UNIT_HOURS_SYMBOL, definition: "This is definition for hours", metricType: MetricType.TIME)
        timeHour.addToSystemOfUnits(systemOfUnitsMetric)
        timeHour.addToSystemOfUnits(systemOfUnitsUsa)
        timeHour.s()
        new StandardConversion(sourceUnit: timeHour, targetUnit: timeMinutes, conversionFactor: TIME_UNIT_HOURS_TO_MINUTES_CONVERSION_FACTOR).s()

    }

    public void populateUnitsAndStandardConversions() {

        /* POPULATEING METRIC UNITS: */
        SystemOfUnit systemOfUnitsMetric = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_METRIC)

        Unit milliLitres = new Unit(name: UNIT_MILLI_LITRE, symbol: UNIT_MILLI_LITRE_SYMBOL, definition: "This is definition for millilitre", metricType: MetricType.METRIC)
        milliLitres.addToSystemOfUnits(systemOfUnitsMetric)
        milliLitres.s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: milliLitres, conversionFactor: UNIT_MILLI_LITRE_CONVERSION_FACTOR).s()

        Unit milliGram = new Unit(name: UNIT_MILLI_GRAM, symbol: UNIT_MILLI_GRAM_SYMBOL, definition: "This is definition for milligram", metricType: MetricType.METRIC)
        milliGram.addToSystemOfUnits(systemOfUnitsMetric)
        milliGram.s()
        new StandardConversion(sourceUnit: milliGram, targetUnit: milliGram, conversionFactor: UNIT_MILLI_LITRE_CONVERSION_FACTOR).s()

        Unit gram = new Unit(name: UNIT_GRAM, symbol: UNIT_GRAM_SYMBOL, definition: "This is definition for gram", metricType: MetricType.METRIC)
        gram.addToSystemOfUnits(systemOfUnitsMetric)
        gram.s()
        new StandardConversion(sourceUnit: gram, targetUnit: milliGram, conversionFactor: UNIT_GRAM_CONVERSION_FACTOR).s()

        Unit kiloGram = new Unit(name: UNIT_KILO_GRAM, symbol: UNIT_KILO_GRAM_SYMBOL, definition: "This is definition for kilogram", metricType: MetricType.METRIC)
        kiloGram.addToSystemOfUnits(systemOfUnitsMetric)
        kiloGram.s()
        new StandardConversion(sourceUnit: kiloGram, targetUnit: milliGram, conversionFactor: UNIT_KILO_GRAM_CONVERSION_FACTOR).s()

        /* POPULATEING USA UNITS: */
        SystemOfUnit systemOfUnitsUsa = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)

        Unit drop = new Unit(name: UNIT_DROP, symbol: UNIT_DROP_SYMBOL, definition: "This is definition for drop", metricType: MetricType.METRIC)
        drop.addToSystemOfUnits(systemOfUnitsUsa)
        drop.s()
        new StandardConversion(sourceUnit: drop, targetUnit: milliLitres, conversionFactor: UNIT_DROP_CONVERSION_FACTOR).s()

        Unit teaspoon = new Unit(name: UNIT_TEA_SPOON, symbol: UNIT_TEA_SPOON_SYMBOL, definition: "This is definition for Teaspoon", metricType: MetricType.METRIC)
        teaspoon.addToSystemOfUnits(systemOfUnitsUsa)
        teaspoon.s()
        new StandardConversion(sourceUnit: teaspoon, targetUnit: milliLitres, conversionFactor: UNIT_TEA_SPOON_CONVERSION_FACTOR).s()

        Unit tableSpoon = new Unit(name: UNIT_TABLE_SPOON, symbol: UNIT_TABLE_SPOON_SYMBOL, definition: "This is definition for Tablespoon", metricType: MetricType.METRIC)
        tableSpoon.addToSystemOfUnits(systemOfUnitsUsa)
        tableSpoon.s()
        new StandardConversion(sourceUnit: tableSpoon, targetUnit: milliLitres, conversionFactor: UNIT_TABLE_SPOON_CONVERSION_FACTOR).s()

        Unit fluidOunce = new Unit(name: UNIT_FLUID_OUNCE, symbol: UNIT_FLUID_OUNCE_SYMBOL, definition: "This is definition for Fluid ounce", metricType: MetricType.METRIC)
        fluidOunce.addToSystemOfUnits(systemOfUnitsUsa)
        fluidOunce.s()
        new StandardConversion(sourceUnit: fluidOunce, targetUnit: milliLitres, conversionFactor: UNIT_FLUID_OUNCE_CONVERSION_FACTOR).s()

        Unit jigger = new Unit(name: UNIT_JIGGER, symbol: UNIT_JIGGER_SYMBOL, definition: "This is definition for jigger", metricType: MetricType.METRIC)
        jigger.addToSystemOfUnits(systemOfUnitsUsa)
        jigger.s()
        new StandardConversion(sourceUnit: jigger, targetUnit: milliLitres, conversionFactor: UNIT_JIGGER_CONVERSION_FACTOR).s()

        Unit cup = new Unit(name: UNIT_CUP, symbol: UNIT_CUP_SYMBOL, definition: "This is definition for Cup", metricType: MetricType.METRIC)
        cup.addToSystemOfUnits(systemOfUnitsUsa)
        cup.s()
        new StandardConversion(sourceUnit: cup, targetUnit: milliLitres, conversionFactor: UNIT_CUP_CONVERSION_FACTOR).s()

        Unit pint = new Unit(name: UNIT_PINT, symbol: UNIT_PINT_SYMBOL, definition: "This is definition for Pint", metricType: MetricType.METRIC)
        pint.addToSystemOfUnits(systemOfUnitsUsa)
        pint.s()
        new StandardConversion(sourceUnit: pint, targetUnit: milliLitres, conversionFactor: UNIT_PINT_CONVERSION_FACTOR).s()

        Unit fifth = new Unit(name: UNIT_FIFTH, symbol: UNIT_FLUID_OUNCE_SYMBOL, definition: "This is definition for fifth", metricType: MetricType.METRIC)
        fifth.addToSystemOfUnits(systemOfUnitsUsa)
        fifth.s()
        new StandardConversion(sourceUnit: fifth, targetUnit: milliLitres, conversionFactor: UNIT_FIFTH_CONVERSION_FACTOR).s()

        Unit quart = new Unit(name: UNIT_QUART, symbol: UNIT_QUART_SYMBOL, definition: "This is definition for Quart", metricType: MetricType.METRIC)
        quart.addToSystemOfUnits(systemOfUnitsUsa)
        quart.s()
        new StandardConversion(sourceUnit: quart, targetUnit: milliLitres, conversionFactor: UNIT_QUART_CONVERSION_FACTOR).s()

        Unit gallon = new Unit(name: UNIT_GALLON, symbol: UNIT_GALLON_SYMBOL, definition: "This is definition for Gallon", metricType: MetricType.METRIC)
        gallon.addToSystemOfUnits(systemOfUnitsUsa)
        gallon.s()
        new StandardConversion(sourceUnit: gallon, targetUnit: milliLitres, conversionFactor: UNIT_GALLON_CONVERSION_FACTOR).s()

        Unit pound = new Unit(name: UNIT_POUND, symbol: UNIT_POUND_SYMBOL, definition: "This is definition for Pound", metricType: MetricType.METRIC)
        pound.addToSystemOfUnits(systemOfUnitsUsa)
        pound.s()
        new StandardConversion(sourceUnit: pound, targetUnit: milliGram, conversionFactor: UNIT_POUND_CONVERSION_FACTOR).s()

        Unit can = new Unit(name: UNIT_CAN, symbol: UNIT_CAN_SYMBOL, definition: "This is definition for Can", metricType: MetricType.METRIC)
        can.addToSystemOfUnits(systemOfUnitsUsa)
        can.s()
        new StandardConversion(sourceUnit: can, targetUnit: milliGram, conversionFactor: UNIT_CAN_CONVERSION_FACTOR).s()

        Unit can_8 = new Unit(name: UNIT_8_CAN, symbol: UNIT_8_CAN_SYMBOL, definition: "This is definition for 8 Can", metricType: MetricType.METRIC)
        can_8.addToSystemOfUnits(systemOfUnitsUsa)
        can_8.s()
        new StandardConversion(sourceUnit: can_8, targetUnit: milliGram, conversionFactor: UNIT_8_CAN_CONVERSION_FACTOR).s()

        Unit ounce_10_3_4 = new Unit(name: UNIT_10_3_4_OUNCE_CAN, symbol: UNIT_10_3_4_OUNCE_CAN_SYMBOL, definition: "This is definition for 10 3/4 ounce can", metricType: MetricType.METRIC)
        ounce_10_3_4.addToSystemOfUnits(systemOfUnitsUsa)
        ounce_10_3_4.s()
        new StandardConversion(sourceUnit: ounce_10_3_4, targetUnit: milliGram, conversionFactor: UNIT_10_3_4_OUNCE_CAN_CONVERSION_FACTOR).s()

        Unit ounce_6 = new Unit(name: UNIT_6_OUNCE_CAN, symbol: UNIT_6_OUNCE_CAN_SYMBOL, definition: "This is definition for 6 ounce can", metricType: MetricType.METRIC)
        ounce_6.addToSystemOfUnits(systemOfUnitsUsa)
        ounce_6.s()
        new StandardConversion(sourceUnit: ounce_6, targetUnit: milliGram, conversionFactor: UNIT_6_OUNCE_CAN_CONVERSION_FACTOR).s()

        Unit ounce_8 = new Unit(name: UNIT_8_OUNCE_CAN, symbol: UNIT_8_OUNCE_CAN_SYMBOL, definition: "This is definition for 8 ounce can", metricType: MetricType.METRIC)
        ounce_8.addToSystemOfUnits(systemOfUnitsUsa)
        ounce_8.s()
        new StandardConversion(sourceUnit: ounce_8, targetUnit: milliGram, conversionFactor: UNIT_8_OUNCE_CAN_CONVERSION_FACTOR).s()

        Unit ounce_10 = new Unit(name: UNIT_10_OUNCE_PACKAGE, symbol: UNIT_10_OUNCE_PACKAGE_SYMBOL, definition: "This is definition for 10 ounce package", metricType: MetricType.METRIC)
        ounce_10.addToSystemOfUnits(systemOfUnitsUsa)
        ounce_10.s()
        new StandardConversion(sourceUnit: ounce_10, targetUnit: milliGram, conversionFactor: UNIT_10_OUNCE_PACKAGE_CONVERSION_FACTOR).s()

        Unit ounce_16 = new Unit(name: UNIT_16_OUNCE_CAN, symbol: UNIT_16_OUNCE_CAN_SYMBOL, definition: "This is definition for 16 ounce can", metricType: MetricType.METRIC)
        ounce_16.addToSystemOfUnits(systemOfUnitsUsa)
        ounce_16.s()
        new StandardConversion(sourceUnit: ounce_16, targetUnit: milliGram, conversionFactor: UNIT_16_OUNCE_CAN_CONVERSION_FACTOR).s()

        Unit ounce_20 = new Unit(name: UNIT_20_OUNCE_PACKAGE, symbol: UNIT_20_OUNCE_PACKAGE_SYMBOL, definition: "This is definition for 20 ounce can", metricType: MetricType.METRIC)
        ounce_20.addToSystemOfUnits(systemOfUnitsUsa)
        ounce_20.s()
        new StandardConversion(sourceUnit: ounce_20, targetUnit: milliGram, conversionFactor: UNIT_20_OUNCE_PACKAGE_CONVERSION_FACTOR).s()

        Unit ounce = new Unit(name: UNIT_OUNCE, symbol: UNIT_OUNCE_SYMBOL, definition: "This is definition for Ounce", metricType: MetricType.METRIC)
        ounce.addToSystemOfUnits(systemOfUnitsUsa)
        ounce.s()
        new StandardConversion(sourceUnit: ounce, targetUnit: milliGram, conversionFactor: UNIT_OUNCE_CONVERSION_FACTOR).s()

        /* POPULATEING CALORY UNIT: */
        Unit calories = new Unit(name: UNIT_CALORIES, symbol: UNIT_CALORIES_SYMBOL, definition: "This is definition for calories", metricType: MetricType.METRIC)
        calories.addToSystemOfUnits(systemOfUnitsMetric)
        calories.addToSystemOfUnits(systemOfUnitsUsa)
        calories.s()
        new StandardConversion(sourceUnit: calories, targetUnit: calories, conversionFactor: UNIT_CALORIES_CONVERSION_FACTOR).s()

    }

    public void populateNutrients() {

        Unit calories = Unit.findByName(UNIT_CALORIES)
        Unit gram = Unit.findByName(UNIT_GRAM)
        Unit milliGram = Unit.findByName(UNIT_MILLI_GRAM)

        new Nutrient(name: NUTRIENT_CALORIES, preferredUnit: calories).s()
        new Nutrient(name: NUTRIENT_TOTAL_FAT, preferredUnit: gram).s()
        new Nutrient(name: NUTRIENT_SATURATED_FAT, preferredUnit: gram).s()
        new Nutrient(name: NUTRIENT_CHOLESTEROL, preferredUnit: milliGram).s()
        new Nutrient(name: NUTRIENT_SODIUM, preferredUnit: milliGram).s()
        new Nutrient(name: NUTRIENT_CARBOHYDRATES, preferredUnit: gram).s()
        new Nutrient(name: NUTRIENT_FIBER, preferredUnit: gram).s()
        new Nutrient(name: NUTRIENT_PROTEIN, preferredUnit: gram).s()
    }
}