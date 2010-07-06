package com.mp

import com.mp.domain.*
import org.grails.comments.Comment
import static com.mp.MenuConstants.*
import org.apache.commons.math.fraction.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.text.FieldPosition
import org.apache.lucene.document.NumberTools
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.math.MathContext


class UtilController {
    def excelService
    def userService
    def asynchronousMailService
    def facebookConnectService

    static config = ConfigurationHolder.config

    def deleteAllRecipes = {
        List<Recipe> recipes = Recipe.list()
        recipes*.delete(flush: true)
        render "All recipes deleted!!"
    }

    Quantity doSomething(Quantity q1){
        StandardConversion x = StandardConversion.get(8)
        Quantity q2 = new Quantity(value: (3*x.conversionFactor), unit: Unit.get(8), savedUnit: q1.savedUnit).s()
        Quantity q3 = Quantity.get(q2.id)
        Float float2 = q2.value.round(3)
        println "Value 3: " + float2
        StandardConversion standardConversion = StandardConversion.findByTargetUnitAndConversionFactorLessThanEquals(q2.savedUnit, float2, [sort: 'conversionFactor', order: 'desc'])
        println "Standard Conversion: ${standardConversion.id}, ${standardConversion.sourceUnit}"
        Fraction f1 = new Fraction(float2)
        Fraction f2 = new Fraction(standardConversion.conversionFactor)
        Fraction f3 = f1.divide(f2)
        println "F1: " + f1
        println "F2: " + f2
        println "New Value: " + f3 + " " + standardConversion.sourceUnit
        println "F3: " + f3 + "  =  " + new Fraction(f3.toFloat().round(2))
        println "F3: " + f3 + "  =  " + new Fraction((float2 / standardConversion.conversionFactor).round(2))
        return q2

        


//        println "********* " + x.conversionFactor



//        def y = StandardConversion.findByConversionFactorLessThanEquals(x.conversionFactor, [sort: 'conversionFactor', order: 'desc'])
//        def y = StandardConversion.list().find{it.conversionFactor == x.conversionFactor}
//        println "**y: " + y







        return q1
    }

    def index = {
        Quantity quantity = Quantity.get(16)
        render "Quantity: ${doSomething(quantity)}"
    }

    def triggerFacebookSync = {
        FacebookProfileSyncJob.triggerNow()
        render "Job Triggered"
    }

    def test = {

        Unit unit1 = Unit.findByName(UNIT_16_OUNCE_CAN)
        Unit unit2 = Unit.findByName(UNIT_16_OUNCE_CAN)
        String val1 = '2'
        String val2 = '3'

        unit1 = null
        unit2 = Unit.findByName(UNIT_16_OUNCE_CAN)
        val1 = ''
        val2 = '3'

        Quantity q1 = StandardConversion.getQuantityToSave(val1, unit1)
        render "Q1: ${q1} <br/>"

        Quantity q2 = StandardConversion.getQuantityToSave(val2, unit2)
        render "Q2: ${q2} <br/>"

        Quantity q = addQuantities(val1, unit1, val2, unit2)
        render "SUM: ${q.toString() ? q : 'QUANTITIES CAN NOT BE ADDED..'} <br/>"
        /*
        asynchronousMailService.sendAsynchronousMail {
            to 'aman@intelligrape.com'
            subject 'created your profile! grails.'
            html '<body><u>XYZ</u></body>'
        }

        Long l = 30l
        render "metric: " + NumberTools.longToString(l)
        */
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

    public static Quantity addQuantities(String usVal1, Unit displayUnit1, String usVal2, Unit displayUnit2) {
        Quantity resultantQuantity = new Quantity()
        Quantity q1 = StandardConversion.getQuantityToSave(usVal1, displayUnit1)
        Quantity q2 = StandardConversion.getQuantityToSave(usVal2, displayUnit2)
        if (q1.toString() && !q2.toString()) { return q1 }
        if (!q1.toString() && q2.toString()) { return q2 }
        if (q1?.savedUnit == q2?.savedUnit) {
            Unit displayUnit = q1?.unit
            if (displayUnit) {
                if (StandardConversion.findBySourceUnit(q1?.unit)?.conversionFactor > StandardConversion.findBySourceUnit(q2?.unit)?.conversionFactor) {
                    displayUnit = q2?.unit
                }
            }
            resultantQuantity?.value = q1?.value + q2?.value
            resultantQuantity?.savedUnit = q1?.savedUnit
            resultantQuantity?.unit = displayUnit
        }
        return resultantQuantity
    }
}