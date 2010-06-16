package com.mp.domain

import grails.test.*
import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.fraction.ProperFractionFormat


class StandardConversionTests extends GrailsUnitTestCase {
  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }


  void test1_valid_getQuantityToSave() {
    String amountFraction
    Unit displayUnit
    def instances = []
    def fracInstances = []

    mockDomain(Unit, instances)           
    mockDomain(Quantity)
     Fraction.metaClass.constructor << {String stringToParse ->
            new ProperFractionFormat().parse(stringToParse)
        }
     def scMock = mockFor(StandardConversion)
    Unit kiloGram = new Unit(name: 'Kilo Grams', symbol: 'Kg.', definition: "This is definition for kilogram", metricType: MetricType.METRIC)

    Unit milliLitres = new Unit(name: "Milli Litre", symbol: "mL", definition: "This is definition for millilitre", metricType: MetricType.METRIC)
    StandardConversion sc = new StandardConversion(sourceUnit: kiloGram, targetUnit: milliLitres, conversionFactor: 1000000.0)
    scMock.demand.static.findBySourceUnit(){Unit unit-> return sc}

    Quantity quant = StandardConversion.getQuantityToSave("1 1 / 2", kiloGram)
    assertNotNull quant
    assertEquals 'Kilo Grams', quant.unit.name
    assertEquals "Milli Litre", quant?.savedUnit.toString()
    assertEquals 1.5 * 1000000.0, quant.value

  }

  void test2_AMOUNT_FRACTION_AS_WHOLENUMBER_valid_getQuantityToSave() {
    String amountFraction
    Unit displayUnit
    def instances = []
    def fracInstances = []

    mockDomain(Unit, instances)
    mockDomain(Quantity)
     Fraction.metaClass.constructor << {String stringToParse ->
            new ProperFractionFormat().parse(stringToParse)
        }
     def scMock = mockFor(StandardConversion)
    Unit kiloGram = new Unit(name: 'Kilo Grams', symbol: 'Kg.', definition: "This is definition for kilogram", metricType: MetricType.METRIC)

    Unit milliLitres = new Unit(name: "Milli Litre", symbol: "mL", definition: "This is definition for millilitre", metricType: MetricType.METRIC)
    StandardConversion sc = new StandardConversion(sourceUnit: kiloGram, targetUnit: milliLitres, conversionFactor: 1000000.0)
    scMock.demand.static.findBySourceUnit(){Unit unit-> return sc}

    Quantity quant = StandardConversion.getQuantityToSave("2", kiloGram)
    assertNotNull quant
    assertEquals 'Kilo Grams', quant.unit.name
    assertEquals "Milli Litre", quant?.savedUnit.toString()
    assertEquals 2 * 1000000.0, quant.value

  }

   void test1_invalid_getQuantityToSave() {
    String amountFraction
    Unit displayUnit
    def instances = []
    def fracInstances = []

    mockDomain(Unit, instances)
    mockDomain(Quantity)
     Fraction.metaClass.constructor << {String stringToParse ->
            new ProperFractionFormat().parse(stringToParse)
        }
     def scMock = mockFor(StandardConversion)
    Unit kiloGram = new Unit(name: 'Kilo Grams', symbol: 'Kg.', definition: "This is definition for kilogram", metricType: MetricType.METRIC)

    scMock.demand.static.findBySourceUnit(){Unit unit-> return null}

    Quantity quant = StandardConversion.getQuantityToSave(null, kiloGram)
    assertNull quant

  }

  void test2_invalid_getQuantityToSave() {
    String amountFraction
    Unit displayUnit
    def instances = []
    def fracInstances = []

    mockDomain(Unit, instances)
    mockDomain(Quantity)
     Fraction.metaClass.constructor << {String stringToParse ->
            new ProperFractionFormat().parse(stringToParse)
        }
     def scMock = mockFor(StandardConversion)
    scMock.demand.static.findBySourceUnit(){Unit unit-> return null}
    Quantity quant = StandardConversion.getQuantityToSave("1 1 / 2", null)
    assertNotNull quant
    assertEquals 1.5,quant.value

  }
}
