package com.mp.domain

import grails.test.*
import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.fraction.ProperFractionFormat


class StandardConversionTests extends GrailsUnitTestCase {
  protected void setUp() {
    super.setUp()
    mockDomain(Quantity)
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
17	0	26	0.0833333333333333	31
17	0	27	0.0833333333333333	31
17	0	28	0.0833333333333333	31
17	0	29	0.0833333333333333	31
"""
  def assertQuantityEquals(Quantity expectedQuantity, Quantity resultQuantity) {
    assert expectedQuantity
    assert resultQuantity
    assert expectedQuantity.value
    assert resultQuantity.value
    assertEquals(expectedQuantity.value, resultQuantity.value, 0.1f)
    assertEquals(expectedQuantity.unit, resultQuantity.unit)
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

  def static units = """1	0	TIME	mins	This is definition for minuts	Minutes	m
  2	0	TIME	hrs	This is definition for hours	Hours	h
  3	0	METRIC	mL	This is definition for millilitre	Milliliter
  4	0	METRIC	mg.	This is definition for milligram	Milligram
  5	0	METRIC	g.	This is definition for gram	Grams
  6	0	METRIC	Kg.	This is definition for kilogram	Kilograms
  8	0	METRIC	t	This is definition for Teaspoon	Teaspoon
  9	0	METRIC	T	This is definition for Tablespoon	Tablespoon
  10	0	METRIC	fl.oz.	This is definition for Fluid ounce	Fluid Ounce
  12	0	METRIC	c	This is definition for Cup	Cup
  13	0	METRIC	fifth	This is definition for fifth	Fifth
  14	0	METRIC	qt.	This is definition for Quart	Quart
  15	0	METRIC	gal.	This is definition for Gallon	Gallon
  16	0	METRIC	lb.	This is definition for Pound	Pound
  17	0	METRIC	can	This is definition for Can	Can
  25	0	METRIC	cal.	This is definition for calories	Calories
  26	0	METRIC	each	This is definition for each	Each
  27	0	METRIC	small	This is definition for small	Small
  28	0	METRIC	medium	This is definition for medium	Medium
  29	0	METRIC	large	This is definition for large	Large
  30	0	METRIC	package	This is definition for package	Package
  31	0	METRIC	dz	This is definition for dozen	Dozen"""

  protected void tearDown() {
    super.tearDown()
  }

  /* This test validates the method   getQuantityToSave() for valid values
  * The test fails if the method returns wrong values
  */

  void test_getQuantityToSave_Valid_Values() {
    Unit sourceUnit = Unit.findByName('Cup')
    sourceUnit.save()

    Quantity quantity = StandardConversionService.getQuantityToSave("1 1 / 2", sourceUnit)
    assertNotNull quantity
    assertEquals 'Cup', quantity.unit.name
    assertEquals "Milliliter", quantity.savedUnit.toString()
    assertEquals 1.5 * 240, quantity.value
  }

  /* This test validates the method   getQuantityToSave() for valid values where amount-fraction is a whole number
  * The test fails if the method returns the wrong values
  */

  void test_getQuantityToSave_Amount_Fraction_As_Whole_Number() {
    Unit sourceUnit = Unit.findByName('Cup')

    Quantity quantity = StandardConversionService.getQuantityToSave("2", sourceUnit)
    assertNotNull quantity
    assertEquals 'Cup', quantity.unit.name
    assertEquals "Milliliter", quantity.savedUnit.toString()
    assertEquals 2 * 240, quantity.value
  }

  /* In this test we are trying to call method getQuantityToSave() for invalid values
  * The test fails if the method saves something and returns any value
  */

  void test_getQuantityToSave_Amount_Null() {
    Unit myUnit = Unit.findByName("Cup")
    Quantity quantity = StandardConversionService.getQuantityToSave(null, myUnit)
    assertNull quantity
  }

  /* In this test we are trying to call method getQuantityToSave() for invalid values
  * The test fails if the method saves something and returns any value
  */

  void test_getQuantityToSave_DisplayUnit_Null() {
    Quantity quantity = StandardConversionService.getQuantityToSave("1 1 / 2", null)
    assertNotNull quantity
    assertEquals 1.5, quantity.value
    assertEquals null, quantity.unit
  }

  /* In this test we are trying to call method getQuantityToSave() for invalid values
 * The test fails if the method saves something and returns any value
  */

  void test_getQuantityToSave_All_BLANK_VALUES() {
    Quantity quantity = StandardConversionService.getQuantityToSave(null, null)
    assertNull quantity
  }


  void test_getQuantityToSave_Standard_Conversion_Null() {
    Unit sourceUnit = getMetricTypeUnit("Large")

    Quantity quantity = StandardConversionService.getQuantityToSave("1 1 / 2", sourceUnit)
    assertNotNull quantity
    assertEquals 'Large', quantity.unit.name
    assertEquals 1.5, quantity.value
  }


  public Unit getMetricTypeUnit(String name) {
    Unit unit = new Unit(name: name, symbol: "nu", definition: "This is definition for test-unit", metricType: MetricType.METRIC)
    return unit
  }

}
