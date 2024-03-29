package com.mp

import com.mp.domain.PartyRoleType
import com.mp.domain.PartyRoleType
import org.apache.commons.math.fraction.ProperFractionFormat
import java.text.NumberFormat

class MenuConstants {

    public static final String MMP_OPERATIONAL_ACCOUNT = 'MMP Operational Account'

    public static final String SYSTEM_OF_UNIT_USA = "United States Customary System"
    public static final String SYSTEM_OF_UNIT_USA_STANDARDIZATION_BODY = "EEUU"
    public static final String SYSTEM_OF_UNIT_METRIC = "Metric System"
    public static final String SYSTEM_OF_UNIT_METRIC_STANDARDIZATION_BODY = "MS"

    public static final String TIME_UNIT_HOURS = "Hours"
    public static final String TIME_UNIT_HOURS_SYMBOL = "hrs"
    public static final Float TIME_UNIT_MINUTES_TO_MINUTES_CONVERSION_FACTOR = 1

    public static final String TIME_UNIT_MINUTES = "Minutes"
    public static final String TIME_UNIT_MINUTES_SYMBOL = "mins"
    public static final Float TIME_UNIT_HOURS_TO_MINUTES_CONVERSION_FACTOR = 60

    public static final String UNIT_TABLE_SPOON = "Table spoon"
    public static final String UNIT_TABLE_SPOON_SYMBOL = "T"
//    public static final Float UNIT_TABLE_SPOON_CONVERSION_FACTOR = 14.8
    public static final Float UNIT_TABLE_SPOON_CONVERSION_FACTOR = 15

    public static final String UNIT_TEA_SPOON = "Tea spoon"
    public static final String UNIT_TEA_SPOON_SYMBOL = "t"
    public static final Float UNIT_TEA_SPOON_CONVERSION_FACTOR = (UNIT_TABLE_SPOON_CONVERSION_FACTOR / 3)

    public static final String UNIT_FLUID_OUNCE = "Fluid Ounce"
    public static final String UNIT_FLUID_OUNCE_SYMBOL = "fl.oz."
    public static final Float UNIT_FLUID_OUNCE_CONVERSION_FACTOR = (2 * UNIT_TABLE_SPOON_CONVERSION_FACTOR)

    public static final String UNIT_GILL = "gill"
    public static final String UNIT_GILL_SYMBOL = "gi."
    public static final Float UNIT_GILL_CONVERSION_FACTOR = (4 * UNIT_FLUID_OUNCE_CONVERSION_FACTOR)

    public static final String UNIT_CUP = "Cup"
    public static final String UNIT_CUP_SYMBOL = "c"
    public static final Float UNIT_CUP_CONVERSION_FACTOR = (2 * UNIT_GILL_CONVERSION_FACTOR)

    public static final String UNIT_POUND = "Pound"
    public static final String UNIT_POUND_SYMBOL = "lb."
    public static final Float UNIT_POUND_CONVERSION_FACTOR = (2 * UNIT_CUP_CONVERSION_FACTOR)

    public static final String UNIT_FIFTH = "Fifth"
    public static final String UNIT_FIFTH_SYMBOL = "fifth"
    public static final Float UNIT_FIFTH_CONVERSION_FACTOR = (1.6 * UNIT_POUND_CONVERSION_FACTOR)

    public static final String UNIT_QUART = "Quart"
    public static final String UNIT_QUART_SYMBOL = "qt."
    public static final Float UNIT_QUART_CONVERSION_FACTOR = (2 * UNIT_POUND_CONVERSION_FACTOR)

    public static final String UNIT_GALLON = "Gallon"
    public static final String UNIT_GALLON_SYMBOL = "gal."
    public static final Float UNIT_GALLON_CONVERSION_FACTOR = (4 * UNIT_QUART_CONVERSION_FACTOR)

    public static final String UNIT_CAN = "Can"
    public static final String UNIT_CAN_SYMBOL = "can"

    public static final String UNIT_PACKAGE = "Package"
    public static final String UNIT_PACKAGE_SYMBOL = "package"

    public static final String UNIT_MILLI_LITRE = "Milli Litre"
    public static final String UNIT_MILLI_LITRE_SYMBOL = "mL"
    public static final Float UNIT_MILLI_LITRE_CONVERSION_FACTOR = 1

    public static final String UNIT_MILLI_GRAM = "Milli Grams"
    public static final String UNIT_MILLI_GRAM_SYMBOL = "mg."
    public static final Float UNIT_MILLI_GRAM_CONVERSION_FACTOR = (UNIT_MILLI_LITRE_CONVERSION_FACTOR / 1000)

    public static final String UNIT_GRAM = "Grams"
    public static final String UNIT_GRAM_SYMBOL = "g."
    public static final Float UNIT_GRAM_CONVERSION_FACTOR = UNIT_MILLI_LITRE_CONVERSION_FACTOR

    public static final String UNIT_KILO_GRAM = "Kilo Grams"
    public static final String UNIT_KILO_GRAM_SYMBOL = "Kg."
    public static final Float UNIT_KILO_GRAM_CONVERSION_FACTOR = (1000 * UNIT_MILLI_LITRE_CONVERSION_FACTOR)

    public static final String UNIT_SMALL = "Small"
    public static final String UNIT_SMALL_SYMBOL = "small"
    public static final Float UNIT_SMALL_CONVERSION_FACTOR = UNIT_MILLI_LITRE_CONVERSION_FACTOR

    public static final String UNIT_MEDIUM = "Medium"
    public static final String UNIT_MEDIUM_SYMBOL = "medium"
    public static final Float UNIT_MEDIUM_CONVERSION_FACTOR = UNIT_MILLI_LITRE_CONVERSION_FACTOR

    public static final String UNIT_LARGE = "Large"
    public static final String UNIT_LARGE_SYMBOL = "large"
    public static final Float UNIT_LARGE_CONVERSION_FACTOR = UNIT_MILLI_LITRE_CONVERSION_FACTOR

    public static final String UNIT_EACH = "Each"
    public static final String UNIT_EACH_SYMBOL = "each"
    public static final Float UNIT_EACH_CONVERSION_FACTOR = UNIT_MILLI_LITRE_CONVERSION_FACTOR

    public static final String UNIT_CALORIES = "Calories"
    public static final String UNIT_CALORIES_SYMBOL = "cal."
    public static final Float UNIT_CALORIES_CONVERSION_FACTOR = 1

    public static final String NUTRIENT_CALORIES = "Calories"
    public static final String NUTRIENT_TOTAL_FAT = "Total Fat"
    public static final String NUTRIENT_SATURATED_FAT = "Saturated Fat"
    public static final String NUTRIENT_CHOLESTEROL = "Cholesterol"
    public static final String NUTRIENT_SODIUM = "Sodium"
    public static final String NUTRIENT_CARBOHYDRATES = "Carbohydrates"
    public static final String NUTRIENT_FIBER = "Fiber"
    public static final String NUTRIENT_PROTEIN = "Protein"

    public static final String SECURITY_ROLE_SUBSCRIBER = PartyRoleType.Subscriber.toString()
    public static final String SECURITY_ROLE_ADMIN = PartyRoleType.Admin.toString()
    public static final String SECURITY_ROLE_SUPER_ADMIN = PartyRoleType.SuperAdmin.toString()
    public static final String SECURITY_ROLE_DIRECTOR = PartyRoleType.Director.toString()
    public static final String SECURITY_ROLE_COACH = PartyRoleType.Coach.toString()

    public static final Long NOT_AUTHORIZED_PERMISSION_LEVEL = 0
    public static final Long UNRESTRICTED_ACCESS_PERMISSION_LEVEL = 1
    public static final Long ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL = 2
    public static final Long ACCESS_IF_OWNS_USER_PERMISSION_LEVEL = 3


    public static final List<String> AISLES = ["produce", "frozen food", "bulk food", "baking", "breads",
            "meat and seafood", "deli", "bakery", "dairy", "pasta and rice", "ethnic foods", "canned foods",
            "condiments", "snacks", "cereal", "beverages", "household items", "health and beauty", "spices and herbs"]

    public static final List<String> PREPARATION_METHODS = ['Baking', 'Blanching', 'Boiling', 'Braising', 'Broiling',
            'Canning', 'Creaming', 'Deep-fry', 'Fermenting', 'Freezing', 'Frying', 'Grilling', 'Outdoor Cooking',
            'Pickling', 'Poaching', 'Pressure Cooking', 'Roasting', 'Salting', 'Sauteing', 'Scalding', 'Simmering',
            'Smoking', 'Steaming', 'Stir-frying', 'Tempering']

    public static final List<String> RECIPES = ['Appetizers', 'Beverages', 'Bread', 'Breakfast', 'Confections',
            'Desserts', 'Meats', 'Pasta', 'Rice', 'Salads', 'Sandwiches', 'Sauces', 'Seafood', 'Side Dishes',
            'Soups', 'Stews', 'Stocks', 'Sushi', 'Vegan', 'Vegetarian', 'Easy Dinners', 'Camping Recipes',
            'Holiday Recipes', 'Recipes In Metric Measurements']

    public static final List<String> BASIC_FOOD_GROUPS = ['Dairy Products & Eggs', 'Cereals & Grains', 'Fruit',
            'Meat & Poultry', 'Fats & Oils', 'Nuts & Seeds', 'Seafood', 'Spices & Herbs', 'Sweeteners', 'Vegetables']

    public static final List<String> NATIONAL_AND_ETHNIC_CUISINES = ['African Cuisines', 'British Cuisines',
            'Caribbean Cuisines', 'East Asian Cuisines', 'European Cuisines', 'Mediterranean Cuisines',
            'Middle Eastern Cuisines', 'Pacific Cuisines', 'North American Cuisines', 'South American Cuisines',
            'South Asian Cuisines', 'South-East Asian Cuisines']

    public static final List<String> SPECIAL_DIETS = ['Low-carb (Atkins, Zone, etc.)', 'Gluten-Free',
            'Healthful Eating', 'Halal', 'High Protein', 'Kosher', 'Low-Calorie Diet', 'Low-GI Diet (Diabetic)',
            'Vegan', 'Vegetarian']


    public static final List<String> NUTRITION = ['Antioxidants', 'Calories', 'Carbohydrates', 'Fats', 'Fiber',
            'Minerals', 'Organic', 'Protein', 'Vitamins']

    public static final List<String> INGREDIENTS = ['Baking Soda', 'Beans & Other Legumes', 'Bread', 'Grain', 'Chiles',
            'Chocolate', 'Coffee', 'Eggs', 'Flour', 'Herbs', 'Jams & Jellies', 'Milk', 'Pasta', 'Peppers', 'Potatoes',
            'Rice', 'Salt', 'Spices', 'Sugar']

    public static final List<String> CATEGORIES = ['Breakfast', 'Lunch', 'Dinner', 'Cooking Techniques', 'Recipes', 'Basic Food Groups',
            'National and Ethnic Cuisines', 'Special Diets', 'Nutrition', 'Ingredients']

    public static final Map<String, List<String>> SUB_CATEGORIES = ['Meal Types': ['Breakfast', 'Lunch', 'Dinner'],
            'Cooking Techniques': PREPARATION_METHODS,
            'Recipes': RECIPES, 'Basic Food Groups': BASIC_FOOD_GROUPS,
            'National and Ethnic Cuisines ': NATIONAL_AND_ETHNIC_CUISINES,
            'Special Diets ': SPECIAL_DIETS, 'Nutrition': NUTRITION, 'Ingredients': INGREDIENTS]

    public static final List<Integer> RECIPE_IMAGE_SIZES = [100, 200, 640]

    public static final Integer IMAGE_INTER_POLATION_TYPE = 1

    public static final Integer IMAGE_RENDERING_TYPE = 1

    public static final List<Integer> USER_IMAGE_SIZES = [50, 200]
    public static final String TRIAL_SUBSCRIPTION = '1 Month Free Trial'
    public static final String COMMUNITY_SUBSCRIPTION = 'Community Subscription'
    public static final String PREVIEW_TRACKER = 'previewTracker'

    public static final s = {
        def object = delegate.save(flush: true)
        if (!object) {
            delegate.errors.allErrors.each {
                println it
            }
        }
        object
    }
    // 3 weeks = 3weeks*7days/week*24hours/day*60min/hour*60sec/hour*1000ms/sec
    static Integer SESSION_TIMEOUT = 3 * 7 * 24 * 60 * 60 * 1000
    static final String CONTRIBUTION = "Contribution"
    public static final String ENGLISH_WET = 'ew'
    public static final String ENGLISH_DRY = 'ed'
    public static final String METRIC_WET = 'mw'
    public static final String METRIC_DRY = 'md'
    public static final String DOZENAL = 'dz'
    public static final Map unitGroups = [
            'ew': 'Fluid Ounce,Pint,Quart,Gallon',
            'ed': 'Ounce,Pound',
            'mw': 'Milliliter,Liter',
            'md': 'Milligram,Gram,Kilogram',
            'dz': 'Each:Small:Medium:Large,Dozen']
    public static final ProperFractionFormat fractionFormat = new ProperFractionFormat(NumberFormat.getInstance())
    static {
        fractionFormat.setMaximumIntegerDigits(1)
    }

}
