package com.mp.tools

import com.mp.domain.ndb.ItemNutritionLink
import com.mp.domain.ndb.NDBFileInfo
import com.mp.domain.ndb.NDBFood
import com.mp.domain.ndb.NDBWeight
import com.mp.tools.UnitUtil
import grails.test.GrailsUnitTestCase
import com.mp.domain.*
import com.mp.domain.ndb.NutritionLink
import org.apache.commons.math.fraction.ProperFractionFormat
import org.apache.commons.math.fraction.Fraction

/**
 */

public class UnitUtilTests extends GrailsUnitTestCase {
  protected void setUp() {
    super.setUp()
    Fraction.metaClass.constructor << {String stringToParse ->
      new ProperFractionFormat().parse(stringToParse)
    }

    def unitInstances = createUnitInstancesFromText(units)
    mockDomain(Unit, unitInstances)
    mockDomain(StandardConversion,getStandardConversions(conversions))
  }

  def getStandardConversions(conversionString) {
    def conversionInstances = []
    new StringReader(conversionString).splitEachLine(/\t/) {parts ->
      StandardConversion conversion = new StandardConversion(id: parts[0].toLong(),
              sourceUnit: Unit.get(parts[2].toLong()),
              targetUnit: Unit.get(parts[4].toLong()),
              conversionFactor: parts[3].toFloat())
      conversionInstances << conversion
    }
    conversionInstances
  }

  //id,--,sourceUnit,conversionFactor,targetUnit
  def static conversions = """1	0	1	1.0000000000	1
2	0	2	60.0000000000	1
3	0	3	1.0000000000	3
4	0	4	0.0010000000	3
5	0	5	1.0000000000	3
6	0	6	1000.0000000000	3
8	0	8	5.0000000000	3
9	0	9	15.0000000000	3
10	0	10	30.0000000000	3
12	0	12	240.0000000000	3
13	0	13	768.0000000000	3
14	0	14	960.0000000000	3
15	0	15	3840.0000000000	3
16	0	16	480.0000000000	3
17	0	31	12.0	26
17	0	31	12.0	27
17	0	31	12.0	28
17	0	31	12.0	29
18	0	6	1000.0	5
19	0	5	1000.0	4
20	0	16	16.0	10
21	0	15	4.0	14
21	0	14	2.0	31
"""
  def assertQuantityEquals(Quantity expectedQuantity, Quantity resultQuantity) {
    assert expectedQuantity
    assert resultQuantity
    assert expectedQuantity.value
    assert resultQuantity.value
    assertEquals(expectedQuantity.unit, resultQuantity.unit)    
    assertEquals(expectedQuantity.value, resultQuantity.value, 0.1f)
  }


  def createUnitInstancesFromText(units) {
    def unitInstances = []
    new StringReader(units).splitEachLine(/\t/) {parts ->
      Unit unit = new Unit(metricType: MetricType.METRIC, isWeightUnit: true, isConvertible: true, id: parts[0].toLong(),
              symbol: parts[3], definition: parts[4], name: parts[5], small: parts[6])
      unitInstances << unit
    }
    unitInstances
  }

  def static units = """1	0	TIME	mins	This is definition for minutes	Minutes	m
  2	0	TIME	hrs	This is definition for hours	Hours	h
  3	0	METRIC	mL	This is definition for millilitre	Milliliter
  4	0	METRIC	mg.	This is definition for milligram	Milligram
  5	0	METRIC	g.	This is definition for gram	Grams
  6	0	METRIC	Kg.	This is definition for kilogram	Kilograms
  8	0	METRIC	t	This is definition for Teaspoon	Teaspoon
  9	0	METRIC	T	This is definition for Tablespoon	Tablespoon
  10	0	METRIC	oz.	This is definition for Ounce	Ounce
  11	0	METRIC	fl oz.	This is definition for Fluid Ounce	Fluid Ounce
  12	0	METRIC	c	This is definition for Cup	Cup
  13	0	METRIC	fifth	This is definition for fifth	Fifth
  14	0	METRIC	qt.	This is definition for Quart	Quart
  15	0	METRIC	gal.	This is definition for Gallon	Gallon
  16	0	METRIC	lb.	This is definition for Pound	Pound
  17	0	METRIC	can	This is definition for Can	Can
  25	0	METRIC	cal.	This is definition for calories	Calorie
  26	0	METRIC	each	This is definition for each	Each
  27	0	METRIC	small	This is definition for small	Small
  28	0	METRIC	medium	This is definition for medium	Medium
  29	0	METRIC	large	This is definition for large	Large
  30	0	METRIC	package	This is definition for package	Package
  31	0	METRIC	dz	This is definition for dozen	Dozen
  32	0	METRIC	pt	This is definition for pint	Pint
"""


  def testConvert_CupOfFlour() {
    NDBFileInfo fileInfo = new NDBFileInfo(id: 1, fileVersion: 23, frum: new Date(), importedDate: new Date())
    mockDomain(NDBFileInfo, [fileInfo])
    mockDomain(Quantity, [])
    mockDomain(MeasurableProduct, [new MeasurableProduct(id: 7734, name: 'All-purpose Flour')])
    def standardConversions = getStandardConversions(conversions)
    mockDomain(StandardConversion, standardConversions)
    def weight = new NDBWeight(id: 11497, amount: 1, msreDesc: 'cup', gmWgt: 125)
    mockDomain NDBWeight, [weight]

    Unit fromUnit = Unit.findByName('Cup')
    assert fromUnit
    def fromProduct = MeasurableProduct.findByName("All-purpose Flour")
    def preparationMethod = null;
    // half way
    mockDomain(NDBFood, [new NDBFood(id: 4121, fileInfo: fileInfo, shrtDesc: "WHEAT FLR,WHITE,ALL-PURPOSE,ENR,BLEACHED")])
    mockDomain(ItemNutritionLink, [new ItemNutritionLink(id: 12, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)])
    mockDomain(NutritionLink, [new ItemNutritionLink(id: 12, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)])
    Quantity fromQuantity = StandardConversionService.getQuantityToSave('5', fromUnit)
    assert fromQuantity.save();
    Grams: {
      Unit toUnit = Unit.findByName('Grams')
      assert toUnit
      // 2
      Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, toUnit)
      assert resultQuantity
      Quantity expectedQuantity = StandardConversionService.getQuantityToSave('625', toUnit)
      // 3
      assert expectedQuantity.save()
      assertQuantityEquals(expectedQuantity, resultQuantity)
    }
    kilograms: {
      Unit toUnit = Unit.findByName('Kilograms')
      assert toUnit
      // 2
      Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, toUnit)
      assert resultQuantity
      Quantity expectedQuantity = StandardConversionService.getQuantityToSave('0.625', toUnit)
      // 3
      assert expectedQuantity.save()
      assertQuantityEquals(expectedQuantity, resultQuantity)
    }
  }
  def testConvert_TeaspoonOfBakingSoda() {
    NDBFileInfo fileInfo = new NDBFileInfo(id: 1, fileVersion: 23, frum: new Date(), importedDate: new Date())
    mockDomain(NDBFileInfo, [fileInfo])
    mockDomain(Quantity, [])
    mockDomain(MeasurableProduct, [new MeasurableProduct(id: 6851, name: 'baking soda')])
    def standardConversions = getStandardConversions(conversions)
    mockDomain(StandardConversion, standardConversions)
    def weight = new NDBWeight(id: 10404, amount: 1, msreDesc: 'tsp', gmWgt: 4.6)
    mockDomain NDBWeight, [weight]

    Unit fromUnit = Unit.findByName('Teaspoon')
    assert fromUnit
    def fromProduct = MeasurableProduct.findByName("baking soda")
    def preparationMethod = null;
    // half way
    mockDomain(NDBFood, [new NDBFood(id: 5937, fileInfo: fileInfo, shrtDesc: "LEAVENING AGENTS,BAKING SODA")])
    ItemNutritionLink link = new ItemNutritionLink(id: 12, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)
    mockDomain(ItemNutritionLink, [link])
    mockDomain(NutritionLink, [link])
    Quantity fromQuantity = StandardConversionService.getQuantityToSave('1', fromUnit)
    assert fromQuantity.save();
    Unit ounceUnit = Unit.findByName('Ounce')
    assert ounceUnit
    Ounces: {
      // 2
      Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, ounceUnit)
      assert resultQuantity
      Quantity expectedQuantity = StandardConversionService.getQuantityToSave('0.15333333', ounceUnit)
      // 3
      assert expectedQuantity.save()
      assertQuantityEquals(expectedQuantity, resultQuantity)
    }
    Pounds: {
      Unit poundUnit = Unit.findByName('Pound')
      assert poundUnit
      // 2
      Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, poundUnit)
      assert resultQuantity

      Quantity expectedQuantity = StandardConversionService.getQuantityToSave('0.0095833333333333', poundUnit) // 1/16 of the ounce evaluation
      assert expectedQuantity.save()

      Quantity expectedNormalizedQuantity = StandardConversionService.getQuantityToSave('0.15333333', ounceUnit)
      assert expectedNormalizedQuantity.save()

      Quantity resultNormalizedQuantity = UnitUtil.normalizeQuantity(resultQuantity)

      assertQuantityEquals(expectedNormalizedQuantity, resultNormalizedQuantity)
    }
  }

  def testConvertLargeTomatoes() {
    NDBFileInfo fileInfo = new NDBFileInfo(id: 1, fileVersion: 23, frum: new Date(), importedDate: new Date())
    mockDomain(NDBFileInfo, [fileInfo])
    mockDomain(Quantity, [])
    mockDomain(MeasurableProduct, [new MeasurableProduct(id: 7734, name: 'tomatoes')])
    def standardConversions = getStandardConversions(conversions)
    mockDomain(StandardConversion, standardConversions)
    def weight = new NDBWeight(id: 5858, amount: 1, msreDesc: 'large whole (3" dia)', gmWgt: 182)
    mockDomain NDBWeight, [weight]

    Unit fromUnit = Unit.findByName('Large')
    assert fromUnit
    def fromProduct = MeasurableProduct.findByName("tomatoes")
    def preparationMethod = null;
    // half way
    mockDomain(NDBFood, [new NDBFood(id: 1265, fileInfo: fileInfo, shrtDesc: "TOMATOES,RED,RIPE,RAW,YEAR RND AVERAGE")])
    ItemNutritionLink itemNutritionLink = new ItemNutritionLink(id: 12, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)
    mockDomain(ItemNutritionLink, [itemNutritionLink])
    mockDomain(NutritionLink, [itemNutritionLink])

    Quantity fromQuantity = StandardConversionService.getQuantityToSave('1', fromUnit)
    assert fromQuantity.save()
    Unit toUnit = Unit.findByName('Grams')
    assert toUnit
    // 2
    Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, toUnit)
    assert resultQuantity
    Quantity expectedQuantity = StandardConversionService.getQuantityToSave('182', toUnit)
    // 3

    assert expectedQuantity.save()
    assertQuantityEquals(expectedQuantity, resultQuantity)
  }

  def testConvertEggs() {
    NDBFileInfo fileInfo = new NDBFileInfo(id: 1, fileVersion: 23, frum: new Date(), importedDate: new Date())
    mockDomain(NDBFileInfo, [fileInfo])
    mockDomain(Quantity, [])
    mockDomain(MeasurableProduct, [new MeasurableProduct(id: 6766, name: 'egg')])
    def standardConversions = getStandardConversions(conversions)
    mockDomain(StandardConversion, standardConversions)

    Unit fromUnit = Unit.findByName('Large')
    assert fromUnit
    def fromProduct = MeasurableProduct.findByName("egg")
    def preparationMethod = null;
    // half way
    def weight = new NDBWeight(id: 5858, amount: 1, msreDesc: 'large', gmWgt: 50)
    mockDomain NDBWeight, [weight]
    mockDomain(NDBFood, [new NDBFood(id: 1265, fileInfo: fileInfo, shrtDesc: "EGG,WHL,RAW,FRSH")])
    mockDomain(ItemNutritionLink, [new ItemNutritionLink(id: 4, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)])
    mockDomain(NutritionLink, [new ItemNutritionLink(id: 4, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)])

    Quantity fromQuantity = StandardConversionService.getQuantityToSave('1', fromUnit)
    assert fromQuantity.save()
    Unit toUnit = Unit.findByName('Grams')
    assert toUnit
    // 2
    Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, toUnit)
    assert resultQuantity
    Quantity expectedQuantity = StandardConversionService.getQuantityToSave('50', toUnit)
    // 3

    assert expectedQuantity.save()
    assertQuantityEquals(expectedQuantity, resultQuantity)
  }

  def testConvert_dozenEggs() {
    NDBFileInfo fileInfo = new NDBFileInfo(id: 1, fileVersion: 23, frum: new Date(), importedDate: new Date())
    mockDomain(NDBFileInfo, [fileInfo])
    mockDomain(Quantity, [])
    mockDomain(MeasurableProduct, [new MeasurableProduct(id: 6766, name: 'egg')])
    def standardConversions = getStandardConversions(conversions)
    mockDomain(StandardConversion, standardConversions)

    Unit fromUnit = Unit.findByName('Large')
    assert fromUnit
    def fromProduct = MeasurableProduct.findByName("egg")
    def preparationMethod = null;
    // half way
    def weight = new NDBWeight(id: 5858, amount: 1, msreDesc: 'large', gmWgt: 50)
    mockDomain NDBWeight, [weight]
    mockDomain(NDBFood, [new NDBFood(id: 1265, fileInfo: fileInfo, shrtDesc: "EGG,WHL,RAW,FRSH")])
    mockDomain(ItemNutritionLink, [new ItemNutritionLink(id: 4, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)])
    mockDomain(NutritionLink, [new ItemNutritionLink(id: 4, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)])

    Quantity fromQuantity = StandardConversionService.getQuantityToSave('12', fromUnit)
    assert fromQuantity.save()
    Unit toUnit = Unit.findByName('Dozen')
    assert toUnit
    // 2
    Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, toUnit)
    assert resultQuantity
    Quantity expectedQuantity = StandardConversionService.getQuantityToSave('1', toUnit)
    // 3

    assert expectedQuantity.save()
    assertQuantityEquals(expectedQuantity, resultQuantity)
  }

  def testConvertMilk() {
    NDBFileInfo fileInfo = new NDBFileInfo(id: 1, fileVersion: 23, frum: new Date(), importedDate: new Date())
    mockDomain(NDBFileInfo, [fileInfo])
    mockDomain(Quantity, [])
    mockDomain(MeasurableProduct, [new MeasurableProduct(id: 6718, name: 'Milk')])
    def standardConversions = getStandardConversions(conversions)
    mockDomain(StandardConversion, standardConversions)

    Unit fromUnit = Unit.findByName('Cup')
    assert fromUnit
    def fromProduct = MeasurableProduct.findByName("Milk")
    assert fromProduct
    def preparationMethod = null;
    // half way
    //There will be no matching found for milk, to negative test
    mockDomain NDBWeight, []
    mockDomain(NDBFood, [])
    mockDomain(ItemNutritionLink, [])
    mockDomain(NutritionLink, [])

    Quantity fromQuantity = StandardConversionService.getQuantityToSave('1', fromUnit)
    assert fromQuantity.save()
    Unit toUnit = Unit.findByName('Gallon')
    assert toUnit
    // 2
    Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, toUnit)
    assert resultQuantity
    Quantity expectedQuantity = StandardConversionService.getQuantityToSave('50', toUnit)
    // 3

    assert expectedQuantity.save()
    assertQuantityEquals(fromQuantity, resultQuantity)
  }

  def testConvertMilk_WithUnit() {
    NDBFileInfo fileInfo = new NDBFileInfo(id: 1, fileVersion: 23, frum: new Date(), importedDate: new Date())
    mockDomain(NDBFileInfo, [fileInfo])
    mockDomain(Quantity, [])
    mockDomain(MeasurableProduct, [new MeasurableProduct(id: 6718, name: 'Milk')])
    def standardConversions = getStandardConversions(conversions)
    mockDomain(StandardConversion, standardConversions)

    Unit fromUnit = Unit.findByName('Cup')
    assert fromUnit
    def fromProduct = MeasurableProduct.findByName("Milk")
    assert fromProduct
    def preparationMethod = null;
    // half way
    //This one will convert from cups to Gallons...
    def weight = new NDBWeight(id: 246, amount: 1, msreDesc: 'cup', gmWgt: 245)
    mockDomain NDBWeight, [weight]
    mockDomain(NDBFood, [new NDBFood(id: 6805, fileInfo: fileInfo, shrtDesc: "MILK,LOWFAT,FLUID,1% MILKFAT,W/ ADD NONFAT MILK SOL,VIT A/ D")])
    mockDomain(ItemNutritionLink, [new ItemNutritionLink(id: 1, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)])
    mockDomain(NutritionLink, [new ItemNutritionLink(id: 1, nutrition: weight, unit: fromUnit,
            product: fromProduct, prep: preparationMethod)])

    Quantity fromQuantity = StandardConversionService.getQuantityToSave('25', fromUnit)
    assert fromQuantity.save()
    Unit toUnit = Unit.findByName('Gallon')
    assert toUnit
    // 2
    Quantity resultQuantity = UnitUtil.convert(fromQuantity, preparationMethod, fromProduct, toUnit)
    assert resultQuantity
    Quantity expectedQuantity = StandardConversionService.getQuantityToSave('1.5950521', toUnit)
    // 3

    assert expectedQuantity.save()
    assertQuantityEquals(expectedQuantity, resultQuantity)
  }
}