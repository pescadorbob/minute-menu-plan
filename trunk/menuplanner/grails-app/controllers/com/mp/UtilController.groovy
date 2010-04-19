package com.mp

import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.fraction.ProperFractionFormat
import java.text.FieldPosition
import org.apache.commons.math.fraction.FractionFormat
import org.apache.lucene.document.NumberTools
import static com.mp.MenuConstants.*

import jxl.*
import org.springframework.web.multipart.commons.CommonsMultipartFile

class UtilController {

    List<String> recipeLog = []

    static config = ConfigurationHolder.config

    def deleteAllRecipes = {
        List<Recipe> recipes = Recipe.list()
        recipes*.delete(flush:true)
        render "All recipes deleted!!"
    }

    def index = {
        render "x:" + (NumberTools.longToString(120l))
    }
    def uploadRecipes = {
        render(view: 'uploadRecipes')
    }
    def populateRecipes = {
        CommonsMultipartFile file = params.recipeFile
        Map results = createLineItems(file.getInputStream())
        render(view: 'uploadResults', model: [result: recipeLog])
    }

    public Recipe makeRecipe(List<List<String>> recipe, List<List<String>> directions, List<List<String>> ingredients) {
        Recipe recipe1 = new Recipe()
        try {
            recipe1.name = recipe[0].getAt(1)
            recipe1.servings = recipe[1].getAt(1).toInteger()

            Quantity prep = new Quantity()
            prep.value = recipe[2].getAt(1).toInteger()
            if (recipe[2].getAt(2).toLowerCase() == 'mins.') {
                prep.unit = Unit.findByName(TIME_UNIT_MINUTES)
            }
            else if (recipe[2].getAt(2).toLowerCase() == 'hrs.') {
                prep.unit = Unit.findByName(TIME_UNIT_HOURS)
            }
            prep.s()
            recipe1.preparationTime = prep
            Quantity cook = new Quantity()
            cook.value = recipe[3].getAt(1).toInteger()
            if (recipe[3].getAt(2).toLowerCase() == 'mins.') {
                cook.unit = Unit.findByName(TIME_UNIT_MINUTES)
            }
            else if (recipe[3].getAt(2).toLowerCase() == 'hrs.') {
                cook.unit = Unit.findByName(TIME_UNIT_HOURS)
            }
            cook.s()
            recipe1.cookingTime = cook
            if (recipe[4].getAt(1).toLowerCase() == 'easy') {
                recipe1.difficulty = RecipeDifficulty.EASY
            }
            else if (recipe[4].getAt(1).toLowerCase() == 'medium') {
                recipe1.difficulty = RecipeDifficulty.MEDIUM
            }
            else if (recipe[4].getAt(1).toLowerCase() == 'hard') {
                    recipe1.difficulty = RecipeDifficulty.HARD
                }
            recipe1.shareWithCommunity = (recipe[5].getAt(1).toLowerCase() == 'yes')
            recipe1.s()
        }
        catch (ex) {
            recipeLog.add(ex.toString())
            return null
        }
        return recipe1
    }

    public boolean createIDirections(List<List<String>> directions, Recipe recipe) {
        try {
            directions.eachWithIndex {List<String> directionRow, Integer index ->
                RecipeDirection recipeDirection = new RecipeDirection()
                recipeDirection.step = directionRow.getAt(1)
                recipe.addToDirections(recipeDirection)
            }
        }
        catch (ex) {
            return false
        }
        return true
    }

    public boolean createIngredients(List<List<String>> ingredients, Recipe recipe) {
        try {
            ingredients.eachWithIndex {List<String> ingredientRow, Integer i ->
                RecipeIngredient recipeIngredient = new RecipeIngredient()
                Item item = Item.findByName(ingredientRow.getAt(3))
                if (!item) {
                    item = new Product(name: ingredientRow.getAt(3))
                    item.isVisible = true
                    item.s()
                }
                recipeIngredient.ingredient = item

                Quantity quantity = new Quantity()
                quantity.value = ingredientRow.getAt(1).toBigDecimal()
                Unit unit = Unit.findBySymbol(ingredientRow.getAt(2))
                if (!unit) {
                    unit = Unit.findByName(ingredientRow.getAt(2))
                }
                if (!unit) {
                    unit = new Unit(name: "${ingredientRow.getAt(2)}", symbol: "${ingredientRow.getAt(2)}", definition: "This is definition for some", metricType: MetricType.METRIC)
                    unit.addToSystemOfUnits(SystemOfUnit.findBySystemName(com.mp.MenuConstants.SYSTEM_OF_UNIT_USA))
                    unit.s()
                }
                quantity.unit = unit
                quantity.s()
                recipeIngredient.quantity = quantity
                recipe.addToIngredients(recipeIngredient)
                recipe.s()
            }
        }
        catch (ex) {
            return false
        }
        return true
    }

    private void createRecipe(Sheet sheet, Map results) {
        List<List<String>> recipe = []
        List<List<String>> directions = []
        List<List<String>> ingredients = []
        (0..6).eachWithIndex {Integer position, Integer index ->
            String valueOne = sheet.getCell(0, position).contents.toString()
            String valueTwo = sheet.getCell(1, position).contents.toString()
            String valueThree = sheet.getCell(2, position).contents.toString()
            if (valueOne) {
                recipe.add([valueOne, valueTwo, valueThree])
            }
        }
        (10..19).eachWithIndex {Integer position, Integer index ->
            String valueOne = sheet.getCell(0, position).contents.toString()
            String valueTwo = sheet.getCell(1, position).contents.toString()
            String valueThree = sheet.getCell(2, position).contents.toString()
            String valueFour = sheet.getCell(3, position).contents.toString()
            if (valueTwo || valueThree || valueFour) {
                ingredients.add([valueOne, valueTwo, valueThree, valueFour])
            }
        }
        (22..31).eachWithIndex {Integer position, Integer index ->
            String valueOne = sheet.getCell(0, position).contents.toString()
            String valueTwo = sheet.getCell(1, position).contents.toString()
            if (valueTwo) {
                directions.add([valueOne, valueTwo])
            }
        }
        Recipe recipeObj = makeRecipe(recipe, directions, ingredients)
        if (recipeObj) {
            recipeLog.add("Created Recipe: ${recipeObj?.name}")
            if (createIDirections(directions, recipeObj)) {recipeLog.add("Added Directions for Recipe: ${recipeObj?.name}")}
            else {recipeLog.add("@: Error while Adding Directions for Recipe: ${recipeObj?.name}")}
            if (createIngredients(ingredients, recipeObj)) {recipeLog.add("Added Ingredients for Recipe: ${recipeObj?.name}")}
            else {recipeLog.add("@: Error while Adding Ingredients for Recipe: ${recipeObj?.name}")}
        }
        else {
            recipeLog.add("@: Error while Creating Recipe.")
        }
    }

    public Map<String, List<String>> createLineItems(InputStream fileInputStream) {
        println "Creating line items.."
        WorkbookSettings workbookSettings
        Workbook workbook
        DateCell dateCell
        Map results = [:]
        results['success'] = []
        results['failure'] = []
        workbookSettings = new WorkbookSettings();
        workbookSettings.setLocale(new Locale("en", "EN"));
        workbook = Workbook.getWorkbook(fileInputStream, workbookSettings);

        workbook?.sheets?.eachWithIndex {Sheet sheet, Integer index ->
            println "**************************************** Sheet:${index} " + sheet.name + "****************************************"
            if (index > 0) {
                recipeLog.add("SHEET ${index + 1}: ${sheet.name}")
                createRecipe(sheet, results);
                recipeLog.add("")
            }
        }
        return results
    }

    def fractionTest = {
        Fraction f1 = new ProperFractionFormat().parse("3  1/2")
        Fraction f2 = new ProperFractionFormat().parse("7/2")
        Fraction f3 = new ProperFractionFormat().parse("1/3")
        render "<br/> ------------------------Created Fraction using String value--------"
        render "<br/>Converting Fraction '3 1/2' to Decimal Value: ${f1.floatValue()}"
        render "<br/>Converting Fraction '7/2' to Decimal Value: ${f2.floatValue()}"
        render "<br/>Converting Fraction '1/3' to Decimal Value: ${f3.floatValue()}"
        render "<br/>"
        render "<br/> ------------Format using ProperFractionFormat-----------------"
        render "<br/>Formatting Fraction '3 1/2' as string: ${new ProperFractionFormat().format(f1, new StringBuffer(), new FieldPosition(0))}"
        render "<br/>Formatting Fraction '7/2' as string: ${new ProperFractionFormat().format(f2, new StringBuffer(), new FieldPosition(0))}"
        render "<br/>Formatting Fraction '1/3'  as string: ${new ProperFractionFormat().format(f3, new StringBuffer(), new FieldPosition(0))}"
        render "<br/>"
        render "<br/> ------------------------Format using FractionFormat------------------------------"
        render "<br/>Formatting Fraction '3 1/2' as string: ${new FractionFormat().format(f1)}"
        render "<br/>Formatting Fraction '7/2' as string: ${new FractionFormat().format(f2)}"
        render "<br/>Formatting Fraction '1/3'  as string: ${new FractionFormat().format(f3)}"
        Fraction f4 = new Fraction(3.5)
        render "<br/>"
        render "<br/> ------------------------Created Fraction using double value 3.5------------------------------"
        render "<br/>Converting Fraction 3.5 to Decimal Value: ${f4.floatValue()}"
        render "<br/>Formatting Fraction 3.5 as string(using ProperFractionFormat): ${new ProperFractionFormat().format(f4, new StringBuffer(), new FieldPosition(0))}"
        render "<br/>Formatting Fraction 3.5 as string(using FractionFormat): ${new FractionFormat().format(f4)}"
        render "<br/>"
        render "<br/> ----------Multiplication of 1/3 and 3---------------------------------------"
        Fraction f6 = new ProperFractionFormat().parse("1/3")
        Fraction f5 = new Fraction(3).multiply(f6)
        render "<br/>Decimal result : ${f5.floatValue()}"
        render "<br/>Formatting  as string(using FractionFormat): ${new FractionFormat().format(f5)}"
        render "<br/>Formatting  as string(using ProperFractionFormat) :${new ProperFractionFormat().format(f5, new StringBuffer(), new FieldPosition(0))}"

    }

    def fractionTestMetaProgramming = {
        Fraction f1 = new Fraction("3  1/2")
        Fraction f2 = new Fraction("7/2")
        Fraction f3 = new Fraction("1/3")
        render "<br/> ------------------------Created Fraction using String value--------"
        render "<br/>Converting Fraction '3 1/2' to Decimal Value: ${f1.floatValue()}"
        render "<br/>Converting Fraction '7/2' to Decimal Value: ${f2.floatValue()}"
        render "<br/>Converting Fraction '1/3' to Decimal Value: ${f3.floatValue()}"
        render "<br/>"
        render "<br/> ------------Format using ProperFractionFormat-----------------"
        render "<br/>Formatting Fraction '3 1/2' as string: ${f1.myFormatUsingProperFractionFormat()}"
        render "<br/>Formatting Fraction '7/2' as string: ${f2.myFormatUsingProperFractionFormat()}"
        render "<br/>Formatting Fraction '1/3'  as string: ${f3.myFormatUsingProperFractionFormat()}"
        render "<br/>"
        render "<br/> ------------------------Format using FractionFormat------------------------------"
        render "<br/>Formatting Fraction '3 1/2' as string: ${f1.myFormatUsingFractionFormat()}"
        render "<br/>Formatting Fraction '7/2' as string: ${f2.myFormatUsingFractionFormat()}"
        render "<br/>Formatting Fraction '1/3'  as string: ${f3.myFormatUsingFractionFormat()}"
        Fraction f4 = new Fraction(3.5)
        render "<br/>"
        render "<br/> ------------------------Created Fraction using double value 3.5------------------------------"
        render "<br/>Converting Fraction 3.5 to Decimal Value: ${f4.floatValue()}"
        render "<br/>Formatting Fraction 3.5 as string(using ProperFractionFormat): ${f4.myFormatUsingProperFractionFormat()}"
        render "<br/>Formatting Fraction 3.5 as string(using FractionFormat): ${f4.myFormatUsingFractionFormat()}"
        render "<br/>"
        render "<br/> ----------Multiplication of 1/3 and 3---------------------------------------"
        Fraction f5 = new Fraction("1/3")
        Fraction f6 = new Fraction(3).multiply(f5)
        render "<br/>Decimal result : ${f6.floatValue()}"
        render "<br/>Formatting  as string(using ProperFractionFormat) :${f6.myFormatUsingProperFractionFormat()}"
        render "<br/>Formatting  as string(using FractionFormat): ${f6.myFormatUsingFractionFormat()}"

    }


    def df = {
        List<String> testStr = ["1.50", ".25", "0.45", "12.5"]
        List results = []
        testStr.each {
            results << decimalToFraction(it)
        }
        render testStr + [' Converted To: '] + results
    }
    def fd = {
        List<String> testStr = ["2 1/4", "1/4", "0 1/4"]
        List results = []
        testStr.each {
            results << fractionToDecimal(it)
        }
        render testStr + [' Converted To: '] + results
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
        res1 = myList[0]
        resFloat = Float.parseFloat(mySubList[0]) / Float.parseFloat(mySubList[1]);
        def mySubSubList = resFloat.toString().split("\\.");
        res2 = mySubSubList[1]
        List<String> result = [res1, res2]
        return result
    }

}