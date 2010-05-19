package com.mp

import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder

import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.fraction.ProperFractionFormat
import java.text.FieldPosition
import org.apache.commons.math.fraction.FractionFormat
import org.apache.lucene.document.NumberTools

import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.math.MathContext
import org.codehaus.groovy.grails.commons.ApplicationHolder
import static com.mp.MenuConstants.*


class UtilController {
    def excelService
    def userService
    def asynchronousMailService

    static config = ConfigurationHolder.config

    def deleteAllRecipes = {
        List<Recipe> recipes = Recipe.list()
        recipes*.delete(flush: true)
        render "All recipes deleted!!"
    }

    def index = {

        List<UserType>userRoles=UserType.list()*.name

        List<UserType> x =[]

        userRoles?.each{
            println "********** role: ${it}"
            x.add(UserType."${it}")
        }

        println x
        

        /*
        Unit unit1= Unit.findByName(UNIT_GRAM)
        Unit unit2= Unit.findByName(UNIT_MILLI_GRAM)
        String val1 = '1/2'
        String val2 = '1'

        Quantity q1 = StandardConversion.getMetricQuantity(val1, unit1)
        Quantity q2 = StandardConversion.getMetricQuantity(val2, unit2)

        Quantity q = addQuantities(val1, unit1, val2, unit2)

        render "Q1: ${q1.toString()} <br/>"
        render "Q2: ${q2.toString()} <br/>"
        render "SUM: value and saved unit: ${new Fraction(q.value)?.floatValue()} ${q.savedUnit.symbol}<br/>"

        String qUsa = StandardConversion.getUsaQuantityString(q)
        render "SUM: qUsa and unit: ${qUsa} ${q.unit.symbol}<br/>"
          */

//        asynchronousMailService.sendAsynchronousMail {
//            to 'aman@intelligrape.com'
//            subject 'created your profile! grails.'
//            html '<body><u>XYZ</u></body>'
//        }
//
//        Long l = 30l
//        render "metric: " + NumberTools.longToString(l)

    }

    def uploadRecipes = {
        render(view: 'uploadRecipes')
    }

    def populateRecipes = {
        CommonsMultipartFile file = params.recipeFile
        List<String> recipeLog = excelService.createLineItems(file.getInputStream())
        render(view: 'uploadResults', model: [result: recipeLog])
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
            results << excelService.decimalToFraction(it)
        }
        render testStr + [' Converted To: '] + results
    }
    def fd = {
        List<String> testStr = ["2 1/4", "1/4", "0 1/4"]
        List results = []
        testStr.each {
            results << excelService.fractionToDecimal(it)
        }
        render testStr + [' Converted To: '] + results
    }

    def test1 = {
        String recipeImageDirectory = config.imagesRootDir + "/recipes/"
        File file = new File(recipeImageDirectory)
        file.mkdirs()

        String bootStrapDirectory = "/bootstrapData/recipeImages/"
        String fileName = "xyz.txt"
        File sourceImage = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath(bootStrapDirectory + fileName))

        String targetImagePath = recipeImageDirectory + fileName
        new File(targetImagePath).withOutputStream {out ->
            out.write sourceImage.readBytes()
        }

        Image image = new Image(targetImagePath, "Some alternate text")
        render sourceImage.exists()
    }

    public static Quantity addQuantities(String usVal1, Unit displayUnit1, String usVal2, Unit displayUnit2) {
        Quantity resultantQuantity = new Quantity()
        Quantity q1 = StandardConversion.getMetricQuantity(usVal1, displayUnit1)
        Quantity q2 = StandardConversion.getMetricQuantity(usVal2, displayUnit2)

            String val1 = StandardConversion.getUsaQuantityString(q1)
            Unit unit1 = q1.unit
            String val2 = StandardConversion.getUsaQuantityString(q2)
            Unit unit2 = q2.unit
            if(q1?.savedUnit==q2?.savedUnit){
                Unit displayUnit = unit1
                if (StandardConversion.findBySourceUnit(unit1)?.conversionFactor > StandardConversion.findBySourceUnit(unit2)?.conversionFactor) {
                    displayUnit = unit2
                }
                Quantity metricQ1 = StandardConversion.getMetricQuantity(val1, unit1)
                Quantity metricQ2 = StandardConversion.getMetricQuantity(val2, unit2)
                resultantQuantity.value = metricQ1?.value + metricQ2?.value
                resultantQuantity.unit = displayUnit
                resultantQuantity.savedUnit = q1?.savedUnit
            }
        return resultantQuantity
    }

}