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
import com.mp.domain.subscriptions.ProductOffering
import com.mp.domain.subscriptions.RecurringCharge
import com.mp.domain.themes.HomePage
import com.mp.domain.themes.Theme
import com.mp.domain.themes.PageElement
import com.mp.domain.accounting.Account
import com.mp.domain.accounting.AccountRole
import com.mp.domain.accounting.AccountRoleType
import com.mp.domain.accounting.AccountTransactionType
import com.mp.domain.party.Party
import com.mp.accounting.AccountingService
import com.mp.domain.accounting.OperationalAccount
import com.mp.domain.subscriptions.ControllerActionFeature
import com.mp.domain.access.PermissionLevel
import com.mp.domain.access.SecurityRole
import com.mp.domain.access.AccessFilterSet
import com.mp.domain.access.AccessFilterType
import com.mp.domain.access.AccessFilter
import com.mp.domain.subscriptions.ProductOfferingApplicability
import com.mp.domain.party.SuperAdmin
import com.mp.domain.subscriptions.BasePrice
import com.mp.ndb.NdbService
import com.mp.domain.subscriptions.FeaturedOfferingApplicability
import com.mp.domain.subscriptions.Feature

class MasterDataBootStrapService implements ApplicationContextAware {

    boolean transactional = false
    AccountingService accountingService
    NdbService ndbService
    static config = ConfigurationHolder.config
    def messageSource
    Object[] testArgs = {}

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

        /* POPULATING NEW UNITS*/

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

    public void populateProductOfferings() {
        Date activeFrom = new Date();
        Date activeTo = activeFrom + 100;
        def f1 = new ControllerActionFeature(controllerFilter: ".*", actionFilter: ".*", uriFilter: ".*",
                activeTo: activeTo, activeFrom: activeFrom, description: "Full Access to everything",
                name: "Full Access", rule: "true").s()
        ProductOffering communitySubscriptionOffering = new ProductOffering(name: COMMUNITY_SUBSCRIPTION, activeTo: activeTo,
                activeFrom: activeFrom).s()
        BasePrice basePriceForCommunitySubscription = new BasePrice(pricingFor: communitySubscriptionOffering, activeTo: activeTo,
                activeFrom: activeFrom, value: 0.0, name: "Community Subscription", description: "Subscription for Contributing Community Members").s();
        communitySubscriptionOffering.addToPricing(basePriceForCommunitySubscription)
        ControllerActionFeature contributionDescription = new ControllerActionFeature(activeFrom:activeFrom,name:CONTRIBUTION,
                description:"Contribute your favorite Recipe.",rule:"[action:'create',controller:'recipe']",
        controller:'recipe',action:'create')
        contributionDescription.s()
        FeaturedOfferingApplicability foa = new FeaturedOfferingApplicability(availableFor:communitySubscriptionOffering,
                 describedBy:contributionDescription,
                applicableFrom:'subscriptionDate',
                applicableFromDescription:'Applicable from date of subscription',
                applicableThru:'subscriptionDate+1y',
                applicableThruDescription:'Applicable from one year of date of subscription.')
        foa.s()
        communitySubscriptionOffering.s()
        ProductOffering year = new ProductOffering(name: "Annual Subscription", activeTo: activeTo, activeFrom: activeFrom)
        year.s()
        ProductOffering freeTrial = new ProductOffering(name: TRIAL_SUBSCRIPTION, activeTo: activeTo, activeFrom: activeFrom)
        freeTrial.s()
        BasePrice basePriceForFreeTrail = new BasePrice(pricingFor: freeTrial, activeTo: activeTo, activeFrom: activeFrom, value: 0.0, name: "Free Trial Subscription", description: "Free trial subscription for existing users").s();
        freeTrial.addToPricing(basePriceForFreeTrail)
        freeTrial.s()
        BasePrice basePriceForYear = new BasePrice(pricingFor: year, activeTo: activeTo, activeFrom: activeFrom, value: 50.0, name: "Annual Subscription Sign-up charge", description: "Annual Subscription Sign-up charge").s();
        RecurringCharge recurringChargeForYear = new RecurringCharge(recurrence: "1.year", pricingFor: year, activeTo: activeTo, activeFrom: activeFrom, value: 50, name: "\$50 year", description: "50 dollars every year after the first year").s();
        year.addToPricing(basePriceForYear)
        year.addToPricing(recurringChargeForYear)
        year.s()
        new ProductOfferingApplicability(availableFor: year, applicableFrom: 'startDate', applicableFromDescription: 'Start Date', applicableThru: 'startDate + 1.year', applicableThruDescription: 'Valid for 1 year').s()
        new ProductOfferingApplicability(availableFor: freeTrial, applicableFrom: 'startDate', applicableFromDescription: 'Start Date', applicableThru: 'startDate + 1.month', applicableThruDescription: 'Valid for 1 month').s()
        new ProductOfferingApplicability(availableFor: communitySubscriptionOffering, applicableFrom: 'startDate', applicableFromDescription: 'Start Date', applicableThru: 'startDate + 1.year', applicableThruDescription: 'Valid for 1 year').s()
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
            new PermissionLevel(role: role, permission: Permission.CREATE_RECIPE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_RECIPE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.DELETE_RECIPE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_USER_ROLES, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.LIST_USERS, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_USERS, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.DELETE_USERS, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.MANAGE_SUBSCRIBER, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.MANAGE_ADMIN, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.MANAGE_AFFILIATE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.MANAGE_SUB_AFFILIATE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.REMOVE_RECIPE_FROM_FAVOURITES, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.REMOVE_RECIPE_ABUSE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.REMOVE_COMMENT_ABUSE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
        }

        if (!SecurityRole.countByName(SECURITY_ROLE_SUBSCRIBER)) {
            println "Populating Security role ${SECURITY_ROLE_SUBSCRIBER}"
            SecurityRole role = new SecurityRole(name: SECURITY_ROLE_SUBSCRIBER, description: 'Subscriber').s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_RECIPE, level: ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.REMOVE_RECIPE_FROM_FAVOURITES, level: ACCESS_IF_OWNS_USER_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_USERS, level: ACCESS_IF_OWNS_USER_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.MANAGE_SUBSCRIBER, level: ACCESS_IF_OWNS_USER_PERMISSION_LEVEL).s()
        }

        if (!SecurityRole.countByName(SECURITY_ROLE_DIRECTOR)) {
            println "Populating Security role ${SECURITY_ROLE_DIRECTOR}"
            SecurityRole role = new SecurityRole(name: SECURITY_ROLE_DIRECTOR, description: 'Director').s()
            new PermissionLevel(role: role, permission: Permission.MANAGE_SUB_AFFILIATE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.MANAGE_AFFILIATE, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.CAN_VIEW_SUB_AFFILIATES, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_USERS, level: ACCESS_IF_OWNS_USER_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.UPDATE_RECIPE, level: ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.REMOVE_RECIPE_FROM_FAVOURITES, level: ACCESS_IF_OWNS_USER_PERMISSION_LEVEL).s()
        }

        if (!SecurityRole.countByName(SECURITY_ROLE_COACH)) {
            println "Populating Security role ${SECURITY_ROLE_COACH}"
            SecurityRole role = new SecurityRole(name: SECURITY_ROLE_COACH, description: 'Coach').s()
            new PermissionLevel(role: role, permission: Permission.MANAGE_SUB_AFFILIATE, level: ACCESS_IF_OWNS_USER_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.CAN_VIEW_INVITATION_URL, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
            new PermissionLevel(role: role, permission: Permission.CAN_VIEW_CLIENTS, level: UNRESTRICTED_ACCESS_PERMISSION_LEVEL).s()
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
                Aisle aisle = (aisleString && Aisle.findByName(aisleString)) ? Aisle.findByName(aisleString) : new Aisle(name: aisleString).s()
                Item.findByName(itemString) ?: new Product(name: itemString, suggestedAisle: aisle, shareWithCommunity: true).s()
            }
        }
        Date d2 = new Date()
        println "Time Taken: " + (d2.time - d1.time) / 1000
    }

    public List<String> populateAlcoholicContentList() {
        String filterFileName = "/bootstrapData/alcoholic_filtering.xls"
        File filterExcelFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(filterFileName))
        List<String> elements = []
        WorkbookSettings workbookSettings
        Workbook workbook
        workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale(new Locale("en", "EN"));
        workbook = Workbook.getWorkbook(filterExcelFile, workbookSettings);
        workbook?.sheets?.each {Sheet sheet ->
            sheet.rows.times {Integer index ->
                String valueOne = sheet.getCell(0, index).contents.toString().trim()
                if (valueOne) {
                    elements.add(valueOne)
                }
            }
        }
        println 'Alcoholic elements:-' + elements
        config.alcoholicContentList = elements
        return elements
    }

    String getMessage(String key) {
        def keyValue = messageSource.resolveCode(key, new java.util.Locale("EN"))
        return keyValue?.format(testArgs)
    }

    public void populateHomePageData() {
        HomePage homePage = new HomePage()
        String titleText = getMessage("homepage.bootstrapped.titleText")
        String centralText = getMessage("homepage.bootstrapped.centralText")
        String centralTextVideo = getMessage("homepage.bootstrapped.centralText.video")
        homePage.name = "Homepage"
        homePage.centralText = titleText + centralText + centralTextVideo
        homePage.leftBar = getMessage('homepage.bootstrapped.leftbarText')
        String categories = getMessage('homepage.bootstrapped.categories')
        homePage.categories = categories + '<br/><br/>' + categories + '<br/><br/>' + categories + '<br/><br/>' + categories
        homePage.activeFrom = new Date()
        homePage.activeTo = new Date() + 100
        homePage.lastModified = new Date()
        homePage.s()
    }

    public void populateTestimonials() {
        (0..3).each {
            Testimonial testimonial = new Testimonial()
            String description = getMessage('homepage.bootstrapped.testimonial')
            testimonial.description = description
            testimonial.showOnHomepage = true
            testimonial.s()
        }
    }

    public void populateThemes() {
        Theme theme = new Theme(name: "Default", activeFrom: new Date(), controllerFilter: '.*',
                actionFilter: '.*', uriFilter: '.*')
        theme.s();
        new PageElement(elementFor: theme, name: "Footer",
                text: """<div id="copyright">&copy; 2010 MinuteMenuPlan.com</div>""",
                location: ElementLocation.FOOTER,
                controllerFilter: '.*', actionFilter: '.*', uriFilter: '.*',
                isTemplate: false,
                listOrder: 1).s()
        new PageElement(elementFor: theme, name: "Blog Feed",
                text: """<div><script type='text/javascript' charset='utf-8' src='http://scripts.hashemian.com/jss/feed.js?print=yes&numlinks=10&summarylen=50&seedate=yes&url=http:%2F%2Fblog.minutemenuplan.com%2Ffeed%2F'></script></div>""",
                location: ElementLocation.FOOTER,
                controllerFilter: '.special*', actionFilter: '.*', uriFilter: '.*',
                isTemplate: false,
                listOrder: 1).s()

        new PageElement(elementFor: theme, name: "Featured Menu",
                text: """<g:link controller="menuPlan" action="show" id="1">Halloween</g:link>""",
                location: ElementLocation.MENU,
                controllerFilter: '.*',
                actionFilter: '.*',
                uriFilter: '.*',
                contextRule: '.*Featured Menus.*',
                isTemplate: true,
                listOrder: 3).s()
    }

    def populateAccounts() {
        Date today = new Date()
        OperationalAccount opAcct = OperationalAccount.findByName(MMP_OPERATIONAL_ACCOUNT)
        if (!opAcct) {
            opAcct = new OperationalAccount(name: MMP_OPERATIONAL_ACCOUNT).s()
            def superAdmin = SuperAdmin.list().first().party
            new AccountRole(roleFor: superAdmin, describes: opAcct, type: AccountRoleType.OWNER).s()
        }
        Party.list().each { party ->
            Account account = new Account(name: "General Account:${party.name}").s()
            new AccountRole(roleFor: party, describes: account, type: AccountRoleType.OWNER).s()
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today - 60, 0.0, "Opening Balance", AccountTransactionType.OPENING_BALANCE)
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today - 59, 5.95, "Initial Payment made through Click Bank: ***-2335 THANK YOU", AccountTransactionType.FUNDING)
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today - 58, -5.95, "\$5/month subscription", AccountTransactionType.SUBSCRIPTION_PAYMENT)
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today - 30, 5.95, "Payment made through Click Bank: ***-2335 THANK YOU", AccountTransactionType.FUNDING)
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today - 29, -5.95, "\$5/month subscription", AccountTransactionType.SUBSCRIPTION_PAYMENT)
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today - 28, 5.95 * 0.2, "Director Collection: Cecil Barlow: Congratulations!", AccountTransactionType.AFFILIATE_PAYMENT)
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today - 27, 5.95 * 0.2, "Director Collection: Cecil Barlow: Congratulations!", AccountTransactionType.AFFILIATE_PAYMENT)
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today - 2, 5.95, "Payment made through Click Bank: ***-2335 THANK YOU", AccountTransactionType.FUNDING)
            accountingService.createTxn(opAcct.accountNumber, account.accountNumber, today, -5.95, "\$5/month subscription", AccountTransactionType.SUBSCRIPTION_PAYMENT)
        }
    }

    def populateAccessFilterSets() {
        def unrestrictedSet = new AccessFilterSet(name: "Default", description: "Default Unlimited Access Filter Set",
                activeFrom: new Date(), type: AccessFilterType.UNRESTRICTED_ACCESS).s();
        new AccessFilter(name: "Browse Recipes", description: "Allows Free browsing of all of the recipes",
                controllerFilter: "recipe", actionFilter: "create|edit|list|search|printRecipes|index|browse|view|show", filterFor: unrestrictedSet).s()
        new AccessFilter(name: "View Menu Plans", description: "Allows viewing menu plans",
                controllerFilter: "menuPlan", actionFilter: "view|show|printerFriendlyMenuPlan", filterFor: unrestrictedSet).s()
        new AccessFilter(name: "View Shopping Lists", description: "Allows viewing shopping lists",
                controllerFilter: "shoppingList", actionFilter: "show|printerFriendlyShoppingList|emailShoppingList", filterFor: unrestrictedSet).s()
        new AccessFilter(name: "Signup Functions", description: "Allows All of the signup features",
                controllerFilter: "user", actionFilter: "create|createUser|createFreeUser|chooseSubscription|newFreeUserSignUp|enableUser|newUserCheckout|welcome|facebookConnect|verify", filterFor: unrestrictedSet).s()
        new AccessFilter(name: "Signup Functions", description: "Allows All of the signup features",
                controllerFilter: "user", actionFilter: "create|createUser|createFreeUser|chooseSubscription|newFreeUserSignUp|enableUser|newUserCheckout|welcome|facebookConnect|verify", filterFor: unrestrictedSet).s()
        new AccessFilter(name: "Misc util", description: "Miscellaneous stuff",
                controllerFilter: "util|login|image|subscription", actionFilter: ".*", filterFor: unrestrictedSet).s()
        new AccessFilter(name: "WeCeem CMS", description: "All of the WeCeem content",
                controllerFilter: "wcmContent", actionFilter: ".*", filterFor: unrestrictedSet).s()

        def permissionSet = new AccessFilterSet(name: "Default PermissionList", description: "Default Permission Access Filter Set",
                activeFrom: new Date(), type: AccessFilterType.PERMISSION).s();
        new AccessFilter(name: "Themes", description: "This requires permissions for themes",
                controllerFilter: "pageElement|permissionDenialPage|subscriptionOfferingPage|theme|webPage",
                actionFilter: ".*", filterFor: permissionSet).s()
        new AccessFilter(name: "Products", description: "This requires permissions for Product Suites",
                controllerFilter: "basePrice|content|contentSubscription|controllerActionFeature|feature|featuredOfferingApplicability",
                actionFilter: ".*", filterFor: permissionSet).s()
        new AccessFilter(name: "More Products", description: "This requires permissions for More Product Suites",
                controllerFilter: "featureSubscription|pricingComponent|productOffering|recurringCharge|subscription",
                actionFilter: ".*", filterFor: permissionSet).s()
        new AccessFilter(name: "Party", description: "This requires permissions for Party Controller",
                controllerFilter: "directorCoach|subscriber",
                actionFilter: ".*", filterFor: permissionSet).s()
        new AccessFilter(name: "Accounting", description: "This requires permissions for Accounting",
                controllerFilter: "account|accountRole|accountTransaction",
                actionFilter: ".*", filterFor: permissionSet).s()
        new AccessFilter(name: "Access", description: "This requires permissions for Access Control",
                controllerFilter: "accessFilter|accessFilterSet|roleAccess",
                actionFilter: ".*", filterFor: permissionSet).s()
        new AccessFilter(name: "RecipeData", description: "This requires permissions for Recipe data",
                controllerFilter: "aisle|category|homePage|measurableProduct|product|quickFill|recipeCategory|recipeIngredient|securityRole|testimonial|unit",
                actionFilter: ".*", filterFor: permissionSet).s()
        new AccessFilter(name: "User Admin", description: "User Administration Access Control",
                controllerFilter: "user",
                actionFilter: "index|delete|changeStatus|list|enableUser", filterFor: permissionSet).s()

        def subscriptionSet = new AccessFilterSet(name: "Default Subscription List", description: "Default Subscription Access Filter Set",
                activeFrom: new Date(), type: AccessFilterType.SUBSCRIPTION).s();
        new AccessFilter(name: "Everything", description: "This requires everything to have a valid subscription",
                controllerFilter: ".*", actionFilter: ".*", filterFor: subscriptionSet).s()

    }

  def populateNDBFood = {
    String productsFileName = "/bootstrapData/ABBREV.txt"
    File foodFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(productsFileName))
    ndbService.importFoodData(foodFile, "23", new Date(), new Date())
    String weightFileName = "/bootstrapData/WEIGHT.txt"
    File weightFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(weightFileName))
    ndbService.importWeight(weightFile, "23", new Date(), new Date())
  }
}
