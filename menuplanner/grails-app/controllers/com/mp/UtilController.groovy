package com.mp

import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.fraction.ProperFractionFormat
import java.text.FieldPosition
import org.apache.commons.math.fraction.FractionFormat
import org.apache.lucene.document.NumberTools

import jxl.*

class UtilController {

    static config = ConfigurationHolder.config

    def index = {
        render "x:" + (NumberTools.longToString(120l))
    }

    def readXls = {
        File myXls = new File('/home/neeraj/mp/recipeSpreadsheet.xls')
        importLineItems(myXls)
    }










    public void makeRecipe(List<List<String>> recipe) {
        Recipe recipe1 = new Recipe()
        recipe1.name = recipe[0].getAt(1)
        recipe1.servings = recipe[1].getAt(1).toInteger()
        recipe1.difficulty = RecipeDifficulty.EASY
        recipe1.cookingTime = Recipe.findByName('recipe11').cookingTime
        recipe1.preparationTime = Recipe.findByName('recipe11').preparationTime
        recipe1.s()

        recipe.each {
            println "${it.getAt(0)} : ${it.getAt(1)}"
        }
    }

    public void createIDirections(List<List<String>> directions, String recipeName) {

        println "Recipe: ${recipeName}"
        println "Directions:"
        directions.eachWithIndex {def directionRow, Integer i ->
            if (directionRow.getAt(0)) {
                RecipeDirection recipeDirection = new RecipeDirection()
                println "seq:${i + 1} step:${directionRow.getAt(0)}"
                recipeDirection.sequence = i + 1
                recipeDirection.step = directionRow.getAt(0)
                recipeDirection.recipe = Recipe.findByName(recipeName)
                recipeDirection.s()
            }
        }
    }

    public void createIngredients(List<List<String>> ingredients, String recipeName) {

        println "Recipe: ${recipeName}"
        println "Ingredients:"
        ingredients.eachWithIndex {def ingredientRow, Integer i ->
            RecipeIngredient recipeIngredient = new RecipeIngredient()
            recipeIngredient.recipe = Recipe.findByName(recipeName)
            if (ingredientRow.getAt(0)) {
                println "seq:${i + 1} quantity:${ingredientRow.getAt(0)}, unit:${ingredientRow.getAt(1)}, product:${ingredientRow.getAt(2)}"
                recipeIngredient.sequence = i + 1
                MeasurableProduct measurableProduct = new MeasurableProduct()
                measurableProduct.name = ingredientRow.getAt(2).toString()
                measurableProduct.preferredUnit = Unit.get(10)
                measurableProduct.s()
                recipeIngredient.ingredient = measurableProduct
                Quantity quantity = new Quantity()
                quantity.value = ingredientRow.getAt(0).toBigDecimal()
                quantity.unit = (Unit.findBySymbol(ingredientRow.getAt(1)))
                if (!Unit.findBySymbol(ingredientRow.getAt(1))) {
                    Unit unit = new Unit(name: "some_${ingredientRow.getAt(1)}", symbol: "${ingredientRow.getAt(1)}", definition: "This is definition for some", metricType: MetricType.METRIC)
                    unit.addToSystemOfUnits(SystemOfUnit.findBySystemName(com.mp.MenuConstants.SYSTEM_OF_UNIT_USA))
                    unit.s()
                    quantity.unit = unit
                }
                quantity.s()
                recipeIngredient.quantity = quantity
                recipeIngredient.s()
            }
        }
    }

    private void createRecipe(Sheet sheet, Map results) {

        List<List<String>> recipe = []
        List<List<String>> directions = []
        List<List<String>> ingredients = []


        (0..1).each {Integer index ->
            List<String> rowContents = sheet.getRow(index).toList()*.contents
            recipe.add([rowContents.getAt(0), rowContents.getAt(1).toString()])
        }
        (17..26).each {Integer index ->
            List<String> rowContents = sheet.getRow(index).toList()*.contents
            directions.add([rowContents.getAt(1)])
        }
        (5..14).each {Integer index ->
            List<String> rowContents = sheet.getRow(index).toList()*.contents
            ingredients.add([rowContents.getAt(1), rowContents.getAt(2), rowContents.getAt(3)])
        }

        makeRecipe(recipe)
        createIDirections(directions, recipe[0].get(1).toString())
        createIngredients(ingredients, recipe[0].get(1).toString())

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

        workbook?.sheets?.each {Sheet sheet ->

            println "******************************************************************* Sheet: " + sheet.name

            switch (sheet.name.trim().toLowerCase()) {

                case 'recipe':
                    createRecipe(sheet, results);
                    break;
            }
        }
        return results
    }

    public void importLineItems(File file) {
        file.withInputStream {
            createLineItems(it);
        }
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