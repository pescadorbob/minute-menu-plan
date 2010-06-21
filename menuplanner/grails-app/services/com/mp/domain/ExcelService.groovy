package com.mp.domain

import jxl.*
import static com.mp.MenuConstants.*
import org.apache.commons.math.fraction.Fraction
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ExcelService {

    boolean transactional = true
    static config = ConfigurationHolder.config

    public List<String> createLineItems(File file) {
        println "Creating line items.."
        WorkbookSettings workbookSettings
        Workbook workbook
        DateCell dateCell
        List<String> recipeLog = []
        workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale(new Locale("en", "EN"));
        workbook = Workbook.getWorkbook(file, workbookSettings);

        workbook?.sheets?.eachWithIndex {Sheet sheet, Integer index ->
            println "**************************************** Sheet:${index} " + sheet.name + " ****************************************"
            if (index > 0) {
                recipeLog.add("SHEET ${index + 1}: ${sheet.name}")
                createRecipe(sheet, recipeLog);
                recipeLog.add("")
            }
        }
        return recipeLog
    }


    private void createRecipe(Sheet sheet, List<String> recipeLog) {
        List<List<String>> recipe = []
        List<List<String>> directions = []
        List<List<String>> ingredients = []
        String categories = []
        (0..5).eachWithIndex {Integer position, Integer index ->
            String valueOne = sheet.getCell(0, position).contents.toString().trim()
            String valueTwo = sheet.getCell(1, position).contents.toString().trim()
            String valueThree = sheet.getCell(2, position).contents.toString().trim()
            if (valueOne) {
                recipe.add([valueOne, valueTwo, valueThree])
            }
        }
        categories = sheet.getCell(1, 6).contents.toString().trim()
        (10..19).eachWithIndex {Integer position, Integer index ->
            String valueOne = sheet.getCell(0, position).contents.toString().trim()
            String valueTwo = sheet.getCell(1, position).contents.toString().trim()
            String valueThree = sheet.getCell(2, position).contents.toString().trim()
            String valueFour = sheet.getCell(3, position).contents.toString().trim()
            String valueFive = sheet.getCell(4, position).contents.toString().trim()
            if (valueTwo || valueThree || valueFour || valueFive) {
                ingredients.add([valueOne, valueTwo, valueThree, valueFour, valueFive])
            }
        }
        (22..31).eachWithIndex {Integer position, Integer index ->
            String valueOne = sheet.getCell(0, position).contents.toString().trim()
            String valueTwo = sheet.getCell(1, position).contents.toString().trim()
            if (valueTwo) {
                directions.add([valueOne, valueTwo])
            }
        }
        Recipe recipeObj = makeRecipe(recipe, directions, ingredients, categories)
    }

    public Recipe makeRecipe(List<List<String>> recipe, List<List<String>> directions, List<List<String>> ingredients, String categories) {
        try {
            Recipe recipeInstance = new Recipe()
            recipeInstance.name = recipe[0].getAt(1)

            recipeInstance.servings = null
            if (recipe[1].getAt(1)) {
                recipeInstance.servings = recipe[1].getAt(1).toInteger()
            }

            recipeInstance.preparationTime = null
            if (recipe[2].getAt(1)) {
                Quantity prep
                Unit unit
                if (recipe[2].getAt(2).toLowerCase() == 'mins.') {
                    unit = Unit.findByName(TIME_UNIT_MINUTES)
                }
                else if (recipe[2].getAt(2).toLowerCase() == 'hrs.') {
                    unit = Unit.findByName(TIME_UNIT_HOURS)
                }
                prep = StandardConversion.getQuantityToSave(recipe[2].getAt(1), unit)
                prep?.s()
                recipeInstance.preparationTime = prep
            }

            recipeInstance.cookingTime = null
            if (recipe[3].getAt(1)) {
                Quantity cook
                Unit unit
                if (recipe[3].getAt(2).toLowerCase() == 'mins.') {
                    unit = Unit.findByName(TIME_UNIT_MINUTES)
                }
                else if (recipe[3].getAt(2).toLowerCase() == 'hrs.') {
                    unit = Unit.findByName(TIME_UNIT_HOURS)
                }
                cook = StandardConversion.getQuantityToSave(recipe[3].getAt(1), unit)
                cook?.s()
                recipeInstance.cookingTime = cook
            }

            recipeInstance.difficulty = null
            if (recipe[4].getAt(1).toLowerCase() == 'easy') {
                recipeInstance.difficulty = RecipeDifficulty.EASY
            }
            else if (recipe[4].getAt(1).toLowerCase() == 'medium') {
                recipeInstance.difficulty = RecipeDifficulty.MEDIUM
            }
            else if (recipe[4].getAt(1).toLowerCase() == 'hard') {
                    recipeInstance.difficulty = RecipeDifficulty.HARD
                }
            if ((recipe[5].getAt(1).toLowerCase() == 'yes') || (recipe[5].getAt(1).toLowerCase() == 'no')) {
                recipeInstance.shareWithCommunity = (recipe[5].getAt(1).toLowerCase() == 'yes')
            }

            createCategories(categories, recipeInstance)
            createDirections(directions, recipeInstance)
            createIngredients(ingredients, recipeInstance)

            recipeInstance.s()
            attachImageToRecipe(recipeInstance)
            return recipeInstance
        }
        catch (Exception e) {
            e.printStackTrace()
            return null
        }
    }

    public boolean attachImageToRecipe(Recipe recipe) {
        try {
            String bootStrapDirectory = "/bootstrapData/recipeImages/"
            String fileName = recipe?.name.trim() + '.jpg'
            File sourceImage = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(bootStrapDirectory + fileName))

            if (sourceImage) {
                String recipeImageDirectory = config.imagesRootDir + "/recipes/" + recipe?.id + '/'
                File file = new File(recipeImageDirectory)
                file.mkdirs()
                String targetImagePath = recipeImageDirectory + recipe?.id + '.' + sourceImage?.name?.tokenize('.')?.tail()?.join('.')
                new File(targetImagePath).withOutputStream {out ->
                    out.write sourceImage.readBytes()
                }
                com.mp.domain.Image image = new com.mp.domain.Image(bootStrapDirectory + fileName, recipeImageDirectory, recipe?.id?.toString(), "")
                recipe.image = image
                image.s()
                recipe.s()
                return true
            }
        } catch (ex) {
            return false
        }
    }

    public boolean createCategories(String categories, Recipe recipe) {
        try {
            Set<String> categoryList = categories?.tokenize(',') as Set
            categoryList.each {String categoryName ->
                Category category = Category.findByName(categoryName)
                if (category) {
                    RecipeCategory recipeCategory = new RecipeCategory()
                    recipeCategory.recipe = recipe
                    recipeCategory.category = category
                    recipe.addToRecipeCategories(recipeCategory)
                }
            }
        }
        catch (ex) {
            return false
        }
        return true
    }

    public boolean createDirections(List<List<String>> directions, Recipe recipe) {
        try {
            List<String> directionList = []
            directions.eachWithIndex {List<String> directionRow, Integer index ->
                directionList.add(directionRow.getAt(1))
            }
            recipe.directions = directionList
        }
        catch (ex) {
            return false
        }
        return true
    }

    public boolean createIngredients(List<List<String>> ingredients, Recipe recipe) {
        try {
            List<RecipeIngredient> recipeIngredients = []
            ingredients.eachWithIndex {List<String> ingredientRow, Integer i ->
                RecipeIngredient recipeIngredient = new RecipeIngredient()
                Item item = Item.findByName(ingredientRow.getAt(3))
                if (!item) {
                    item = new Product(name: ingredientRow.getAt(3))
                    item.isVisible = true
                    String aisleName = ingredientRow.getAt(4)
                    if (aisleName) {
                        Aisle aisle = Aisle.findByName(aisleName)
                        if (!aisle) {
                            aisle = new Aisle(name: aisleName).s()
                        }
                        item.suggestedAisle = aisle
                    }
                    item.s()
                }
                recipeIngredient.ingredient = item

                Aisle aisle = Aisle.findByName(ingredientRow.getAt(4))
                if (aisle) {
                    recipeIngredient.aisle = aisle
                } 



                Quantity quantity = new Quantity()
                if (ingredientRow.getAt(1) && ingredientRow.getAt(2)) {  // if amount and unit both are Specified:

                    Unit unit = Unit.findBySymbol(ingredientRow.getAt(2))
                    if (!unit) { unit = Unit.findByName(ingredientRow.getAt(2)) }
                    if (!unit) {
                        println "Unknown unit: " + ingredientRow.getAt(2)
                        /*
                        unit = new Unit(name: "${ingredientRow.getAt(2)}", symbol: "${ingredientRow.getAt(2)}", definition: "This is definition", metricType: MetricType.METRIC)
                        unit.addToSystemOfUnits(SystemOfUnit.findBySystemName(SYSTEM_OF_UNIT_USA))
                        unit.s()

                        StandardConversion standardConversion = new StandardConversion()
                        standardConversion.targetUnit = Unit.findByName(UNIT_MILLI_LITRE)
                        standardConversion.sourceUnit = unit
                        standardConversion.conversionFactor = 1.0
                        standardConversion.s()
                        */
                    }
                    quantity = StandardConversion.getQuantityToSave(ingredientRow.getAt(1), unit)
                } else if (ingredientRow.getAt(1)) {  // only Amount is specified:
                    quantity = StandardConversion.getQuantityToSave(ingredientRow.getAt(1), null)
                }
                quantity?.s()
                recipeIngredient.quantity = quantity
                recipeIngredients.add(recipeIngredient)
            }
            recipeIngredients?.eachWithIndex {RecipeIngredient recipeIngredient, Integer index ->
                recipeIngredient.recipe = recipe
                recipe.addToIngredients(recipeIngredient)
            }
        }
        catch (ex) {
            ex.printStackTrace()
            return false
        }
        return true
    }

    List<String> fractionToDecimal(String input) {
        String res1, res2;
        def resFloat;

        List<String> myList = []
        String[] temp = input.split("\\ ");
        if (temp.size() == 1) {
            myList.add('0')
            myList.add(temp[0])
        }
        else {
            myList.add(temp[0])
            myList.add(temp[1])
        }
        String[] mySubList = myList[1].split("\\/");
        if (mySubList[1].contains('.')) {
            return input
        }
        res1 = myList[0]
        resFloat = Float.parseFloat(mySubList[0]) / Float.parseFloat(mySubList[1]);
        def mySubSubList = resFloat.toString().split("\\.");
        res2 = mySubSubList[1]
        List<String> result = [res1, res2]
        return result
    }

    List<String> decimalToFraction(String input) {
        Integer res1, res2, res3 = 1;
        String[] myList = input.split("\\.");
        if (myList[0] == '') myList[0] = '0'
        res1 = myList[0].toInteger()
        res2 = myList[1].toInteger()

        (1..myList[1].length()).each {
            res3 *= 10
        }
        Integer HCF = hcf_function(res2, res3)
        res2 = res2 / HCF
        res3 = res3 / HCF
        List<String> result = [res1.toString(), res2.toString(), res3.toString()]
        return result;
    }

    Integer hcf_function(Integer m, Integer n) {
        Integer temp, reminder;
        if (m < n) {
            temp = m;
            m = n;
            n = temp;
        }
        while (1) {
            reminder = m % n;
            if (reminder == 0)
                return n;
            else
                m = n;
            n = reminder;
        }
    }

}
        