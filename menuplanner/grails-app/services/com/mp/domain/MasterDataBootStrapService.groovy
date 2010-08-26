package com.mp.domain

import static com.mp.MenuConstants.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.Sheet

class MasterDataBootStrapService implements ApplicationContextAware {

    boolean transactional = false

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

    public void populateNewUnitsAndStandardConversions() {

        SystemOfUnit systemOfUnitsMetric = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_METRIC)
        SystemOfUnit systemOfUnitsUsa = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)
        Unit milliLitres = Unit.findByName(UNIT_MILLI_LITRE)

        Unit eachUnit = new Unit(name: UNIT_EACH, symbol: UNIT_EACH_SYMBOL, definition: "This is definition for each", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: false)
        eachUnit.addToSystemOfUnits(systemOfUnitsUsa)
        eachUnit.addToSystemOfUnits(systemOfUnitsMetric)
        eachUnit.s()

        Unit small = new Unit(name: UNIT_SMALL, symbol: UNIT_SMALL_SYMBOL, definition: "This is definition for small", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: false)
        small.addToSystemOfUnits(systemOfUnitsUsa)
        small.addToSystemOfUnits(systemOfUnitsMetric)
        small.s()

        Unit medium = new Unit(name: UNIT_MEDIUM, symbol: UNIT_MEDIUM_SYMBOL, definition: "This is definition for medium", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: false)
        medium.addToSystemOfUnits(systemOfUnitsUsa)
        medium.addToSystemOfUnits(systemOfUnitsMetric)
        medium.s()

        Unit large = new Unit(name: UNIT_LARGE, symbol: UNIT_LARGE_SYMBOL, definition: "This is definition for large", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: false)
        large.addToSystemOfUnits(systemOfUnitsUsa)
        large.addToSystemOfUnits(systemOfUnitsMetric)
        large.s()

        Unit pkg = new Unit(name: UNIT_PACKAGE, symbol: UNIT_PACKAGE_SYMBOL, definition: "This is definition for package", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: false)
        pkg.addToSystemOfUnits(systemOfUnitsUsa)
        pkg.addToSystemOfUnits(systemOfUnitsMetric)
        pkg.s()

    }
    public void populateUnitsAndStandardConversions() {

        /* POPULATEING METRIC UNITS: */
        SystemOfUnit systemOfUnitsMetric = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_METRIC)

        Unit milliLitres = new Unit(name: UNIT_MILLI_LITRE, symbol: UNIT_MILLI_LITRE_SYMBOL, definition: "This is definition for millilitre", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: true)
        milliLitres.addToSystemOfUnits(systemOfUnitsMetric)
        milliLitres.s()
        new StandardConversion(sourceUnit: milliLitres, targetUnit: milliLitres, conversionFactor: UNIT_MILLI_LITRE_CONVERSION_FACTOR).s()

        Unit milliGram = new Unit(name: UNIT_MILLI_GRAM, symbol: UNIT_MILLI_GRAM_SYMBOL, definition: "This is definition for milligram", metricType: MetricType.METRIC, isWeightUnit: true, isConvertible: true)
        milliGram.addToSystemOfUnits(systemOfUnitsMetric)
        milliGram.s()
        new StandardConversion(sourceUnit: milliGram, targetUnit: milliLitres, conversionFactor: UNIT_MILLI_GRAM_CONVERSION_FACTOR).s()

        Unit gram = new Unit(name: UNIT_GRAM, symbol: UNIT_GRAM_SYMBOL, definition: "This is definition for gram", metricType: MetricType.METRIC, isWeightUnit: true, isConvertible: true)
        gram.addToSystemOfUnits(systemOfUnitsMetric)
        gram.s()
        new StandardConversion(sourceUnit: gram, targetUnit: milliLitres, conversionFactor: UNIT_GRAM_CONVERSION_FACTOR).s()

        Unit kiloGram = new Unit(name: UNIT_KILO_GRAM, symbol: UNIT_KILO_GRAM_SYMBOL, definition: "This is definition for kilogram", metricType: MetricType.METRIC, isWeightUnit: true, isConvertible: true)
        kiloGram.addToSystemOfUnits(systemOfUnitsMetric)
        kiloGram.s()
        new StandardConversion(sourceUnit: kiloGram, targetUnit: milliLitres, conversionFactor: UNIT_KILO_GRAM_CONVERSION_FACTOR).s()

        /* POPULATEING USA UNITS: */
        SystemOfUnit systemOfUnitsUsa = SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA)

        Unit teaspoon = new Unit(name: UNIT_TEA_SPOON, symbol: UNIT_TEA_SPOON_SYMBOL, definition: "This is definition for Teaspoon", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: true)
        teaspoon.addToSystemOfUnits(systemOfUnitsUsa)
        teaspoon.s()
        new StandardConversion(sourceUnit: teaspoon, targetUnit: milliLitres, conversionFactor: UNIT_TEA_SPOON_CONVERSION_FACTOR).s()

        Unit tableSpoon = new Unit(name: UNIT_TABLE_SPOON, symbol: UNIT_TABLE_SPOON_SYMBOL, definition: "This is definition for Tablespoon", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: true)
        tableSpoon.addToSystemOfUnits(systemOfUnitsUsa)
        tableSpoon.s()
        new StandardConversion(sourceUnit: tableSpoon, targetUnit: milliLitres, conversionFactor: UNIT_TABLE_SPOON_CONVERSION_FACTOR).s()

        Unit fluidOunce = new Unit(name: UNIT_FLUID_OUNCE, symbol: UNIT_FLUID_OUNCE_SYMBOL, definition: "This is definition for Fluid ounce", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: true)
        fluidOunce.addToSystemOfUnits(systemOfUnitsUsa)
        fluidOunce.s()
        new StandardConversion(sourceUnit: fluidOunce, targetUnit: milliLitres, conversionFactor: UNIT_FLUID_OUNCE_CONVERSION_FACTOR).s()

        Unit cup = new Unit(name: UNIT_CUP, symbol: UNIT_CUP_SYMBOL, definition: "This is definition for Cup", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: true)
        cup.addToSystemOfUnits(systemOfUnitsUsa)
        cup.s()
        new StandardConversion(sourceUnit: cup, targetUnit: milliLitres, conversionFactor: UNIT_CUP_CONVERSION_FACTOR).s()

        Unit fifth = new Unit(name: UNIT_FIFTH, symbol: UNIT_FLUID_OUNCE_SYMBOL, definition: "This is definition for fifth", metricType: MetricType.METRIC, isWeightUnit: true, isConvertible: true)
        fifth.addToSystemOfUnits(systemOfUnitsUsa)
        fifth.s()
        new StandardConversion(sourceUnit: fifth, targetUnit: milliLitres, conversionFactor: UNIT_FIFTH_CONVERSION_FACTOR).s()

        Unit quart = new Unit(name: UNIT_QUART, symbol: UNIT_QUART_SYMBOL, definition: "This is definition for Quart", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: true)
        quart.addToSystemOfUnits(systemOfUnitsUsa)
        quart.s()
        new StandardConversion(sourceUnit: quart, targetUnit: milliLitres, conversionFactor: UNIT_QUART_CONVERSION_FACTOR).s()

        Unit gallon = new Unit(name: UNIT_GALLON, symbol: UNIT_GALLON_SYMBOL, definition: "This is definition for Gallon", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: true)
        gallon.addToSystemOfUnits(systemOfUnitsUsa)
        gallon.s()
        new StandardConversion(sourceUnit: gallon, targetUnit: milliLitres, conversionFactor: UNIT_GALLON_CONVERSION_FACTOR).s()

        Unit pound = new Unit(name: UNIT_POUND, symbol: UNIT_POUND_SYMBOL, definition: "This is definition for Pound", metricType: MetricType.METRIC, isWeightUnit: true, isConvertible: true)
        pound.addToSystemOfUnits(systemOfUnitsUsa)
        pound.s()
        new StandardConversion(sourceUnit: pound, targetUnit: milliLitres, conversionFactor: UNIT_POUND_CONVERSION_FACTOR).s()

        Unit can = new Unit(name: UNIT_CAN, symbol: UNIT_CAN_SYMBOL, definition: "This is definition for Can", metricType: MetricType.METRIC, isWeightUnit: false, isConvertible: false)
        can.addToSystemOfUnits(systemOfUnitsUsa)
        can.s()

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

    public void populatePermissions() {
        if (!SecurityRole.countByName(SECURITY_ROLE_SUPER_ADMIN)) {
            println "Populating Security role ${SECURITY_ROLE_SUPER_ADMIN}"
            SecurityRole role = new SecurityRole(name: SECURITY_ROLE_SUPER_ADMIN, description: 'Super Admin with all permissions').s()
            Permission.values().each {Permission permission ->
                new PermissionLevel(role: role, permission: permission, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            }
        } else {
            verifySecurityAccess()
        }

        if (!SecurityRole.countByName(SECURITY_ROLE_ADMIN)) {
            println "Populating Security role ${SECURITY_ROLE_ADMIN}"
            SecurityRole role = new SecurityRole(name: SECURITY_ROLE_ADMIN, description: 'Admin to manage subscribers content').s()
            Permission.values().each {Permission permission ->
                new PermissionLevel(role: role, permission: permission, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            }
        }

        if (!SecurityRole.countByName(SECURITY_ROLE_SUBSCRIBER)) {
            println "Populating Security role ${SECURITY_ROLE_SUBSCRIBER}"
            SecurityRole role = new SecurityRole(name: SECURITY_ROLE_SUBSCRIBER, description: 'Subscriber').s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_RECIPE, level: ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.REMOVE_RECIPE_FROM_FAVOURITES, level: ACCESS_IF_OWNS_USER_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_USERS, level: ACCESS_IF_OWNS_USER_PERMISSION_LEVEL).s()
        }

    }

    public static void verifySecurityAccess() {
        List<SecurityRole> roles = SecurityRole.list()
        Boolean hasSecurityAccess = roles.any {SecurityRole securityRole ->
            ([Permission.CREATE_SECURITY_ROLE, Permission.READ_SECURITY_ROLE,
                    Permission.UPDATE_SECURITY_ROLE, Permission.DELETE_SECURITY_ROLE]).every {
                (it in securityRole.permissionLevels*.permission)
            }
        }

        if (!hasSecurityAccess) {
            SecurityRole securityRole = SecurityRole.findByName(SECURITY_ROLE_SUPER_ADMIN)
            new PermissionLevel(role: securityRole, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL, permission: Permission.CREATE_SECURITY_ROLE).s()
            new PermissionLevel(role: securityRole, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL, permission: Permission.READ_SECURITY_ROLE).s()
            new PermissionLevel(role: securityRole, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL, permission: Permission.UPDATE_SECURITY_ROLE).s()
            new PermissionLevel(role: securityRole, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL, permission: Permission.DELETE_SECURITY_ROLE).s()
        }
    }

    public void populateAisles() {
        AISLES.each {String name ->
            new Aisle(name: name).s()
        }
    }

    public void populateCategories() {
        SUB_CATEGORIES.each {key, value ->
            Category category = Category.findByName(key) ?: new Category(name: key).s()
            value.each {String name ->
                if (!SubCategory.countByNameAndCategory(name, category)) {
                    new SubCategory(name: name, category: category).s()
                }
            }
        }
    }

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurationHolder.config.applicationContext = applicationContext;
    }


    public void populateProductsWithAisles() {
        String productsFileName = "/bootstrapData/store-items.xls"
        File productsFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(productsFileName))
        WorkbookSettings workbookSettings
        Workbook workbook
        workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale(new Locale("en", "EN"));
        workbook = Workbook.getWorkbook(productsFile, workbookSettings);
        Date d1 = new Date()
        workbook?.sheets?.each {Sheet sheet ->
            sheet.rows.times {Integer index ->
                String aisleString = sheet.getCell(0, index).contents.toString().trim()
                String itemString = sheet.getCell(1, index).contents.toString().replace("'", "").trim()
                Aisle aisle = Aisle.findByName(aisleString) ?: new Aisle(name: aisleString).s()
                Item.findByName(itemString) ?: new Product(name: itemString, suggestedAisle: aisle).s()
            }
        }
        Date d2 = new Date()
        println "Time Taken: " + (d2.time - d1.time) / 1000
    }

}