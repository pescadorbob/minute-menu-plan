package com.mp.domain

import grails.test.*
import org.apache.commons.math.fraction.Fraction
import org.apache.commons.math.fraction.ProperFractionFormat


class StandardConversionTests extends GrailsUnitTestCase {

    protected void setUp() {
        super.setUp()
        def instances = []
        mockDomain(Unit, instances)
        mockDomain(Quantity)

        Fraction.metaClass.constructor << {String stringToParse ->
            new ProperFractionFormat().parse(stringToParse)
        }
    }

    protected void tearDown() {
        super.tearDown()
    }

    /* This test validates the method   getQuantityToSave() for valid values
     * The test fails if the method returns wrong values
     */

    void test_getQuantityToSave_Valid_Values() {
        Unit sourceUnit = getMetricTypeUnit("SourceUnit")
        Unit targetUnit = getMetricTypeUnit("TargetUnit")
        StandardConversion sc = getStandardConversion(sourceUnit, targetUnit, 1000000.0f)

        def scMock = mockFor(StandardConversion)
        scMock.demand.static.findBySourceUnit() {Unit unit -> return sc}

        Quantity quantity = StandardConversion.getQuantityToSave("1 1 / 2", sourceUnit)
        assertNotNull quantity
        assertEquals 'SourceUnit', quantity.unit.name
        assertEquals "TargetUnit", quantity.savedUnit.toString()
        assertEquals 1.5 * 1000000.0, quantity.value
    }

    /* This test validates the method   getQuantityToSave() for valid values where amount-fraction is a whole number
        * The test fails if the method returns the wrong values
        */

    void test_getQuantityToSave_Amount_Fraction_As_Whole_Number() {
        Unit sourceUnit = getMetricTypeUnit('SourceUnit')
        Unit targetUnit = getMetricTypeUnit('TargetUnit')
        StandardConversion sc = getStandardConversion(sourceUnit, targetUnit, 1000000.0f)

        def scMock = mockFor(StandardConversion)
        scMock.demand.static.findBySourceUnit() {Unit unit -> return sc}

        Quantity quantity = StandardConversion.getQuantityToSave("2", sourceUnit)
        assertNotNull quantity
        assertEquals 'SourceUnit', quantity.unit.name
        assertEquals "TargetUnit", quantity.savedUnit.toString()
        assertEquals 2 * 1000000.0, quantity.value
    }

    /* In this test we are trying to call method getQuantityToSave() for invalid values
    * The test fails if the method saves something and returns any value
    */

    void test_getQuantityToSave_Amount_Null() {
        Unit myUnit = getMetricTypeUnit('myUnit')

        def scMock = mockFor(StandardConversion)
        scMock.demand.static.findBySourceUnit() {Unit unit -> return null}

        Quantity quantity = StandardConversion.getQuantityToSave(null, myUnit)
        assertNull quantity
    }

    /* In this test we are trying to call method getQuantityToSave() for invalid values
    * The test fails if the method saves something and returns any value
    */

    void test_getQuantityToSave_DisplayUnit_Null() {
        def scMock = mockFor(StandardConversion)
        scMock.demand.static.findBySourceUnit() {Unit unit -> return null}

        Quantity quantity = StandardConversion.getQuantityToSave("1 1 / 2", null)
        assertNotNull quantity
        assertEquals 1.5, quantity.value
        assertEquals null, quantity.unit
    }

    /* In this test we are trying to call method getQuantityToSave() for invalid values
    * The test fails if the method saves something and returns any value
     */

    void test_getQuantityToSave_All_BLANK_VALUES() {
        def scMock = mockFor(StandardConversion)
        scMock.demand.static.findBySourceUnit() {Unit unit -> return null}
        Quantity quantity = StandardConversion.getQuantityToSave(null, null)
        assertNull quantity
    }

    /* In this test we are calling method getQuantityToSave() for valid values
       and the source unit is Weight Unit. Also we are passing density
    * The test fails if the method unable to save values  or if it doesn't use density in calculation.
    */

    void test_getQuantityToSave_Valid_Density_IsWeightUnit() {
        Unit sourceUnit = getMetricTypeUnit("SourceUnit")
        sourceUnit.isWeightUnit = true
        Unit targetUnit = getMetricTypeUnit("TargetUnit")
        StandardConversion sc = getStandardConversion(sourceUnit, targetUnit, 1000000.0f)

        def scMock = mockFor(StandardConversion)
        scMock.demand.static.findBySourceUnit() {Unit unit -> return sc}

        Quantity quantity = StandardConversion.getQuantityToSave("1 1 / 2", sourceUnit, 2.0f)
        assertNotNull quantity
        assertEquals 'SourceUnit', quantity.unit.name
        assertEquals "TargetUnit", quantity.savedUnit.toString()
        assertEquals 1.5 * 1000000.0 / 2.0, quantity.value
    }

    /* In this test we are calling method getQuantityToSave() for valid values
       and the source unit is Not a Weight Unit. Also we are passing density
    * The test fails if the method unable to save values  or if it uses use density in calculation.
    */

    void test_getQuantityToSave_Valid_Density_Not_WeightUnit() {
        Unit sourceUnit = getMetricTypeUnit("SourceUnit")
        sourceUnit.isWeightUnit = false
        Unit targetUnit = getMetricTypeUnit("TargetUnit")
        StandardConversion sc = getStandardConversion(sourceUnit, targetUnit, 1000000.0f)

        def scMock = mockFor(StandardConversion)
        scMock.demand.static.findBySourceUnit() {Unit unit -> return sc}

        Quantity quantity = StandardConversion.getQuantityToSave("1 1 / 2", sourceUnit, 2.0f)
        assertNotNull quantity
        assertEquals 'SourceUnit', quantity.unit.name
        assertEquals "TargetUnit", quantity.savedUnit.toString()
        assertEquals 1.5 * 1000000.0, quantity.value
    }

    void test_getQuantityToSave_Standard_Conversion_Null() {
        Unit sourceUnit = getMetricTypeUnit("SourceUnit")
        StandardConversion sc = null

        def scMock = mockFor(StandardConversion)
        scMock.demand.static.findBySourceUnit() {Unit unit -> return sc}

        Quantity quantity = StandardConversion.getQuantityToSave("1 1 / 2", sourceUnit)
        assertNotNull quantity
        assertEquals 'SourceUnit', quantity.unit.name
        assertEquals 1.5, quantity.value
    }


    public Unit getMetricTypeUnit(String name) {
        Unit unit = new Unit(name: name, symbol: "nu", definition: "This is definition for test-unit", metricType: MetricType.METRIC)
        return unit
    }

    public StandardConversion getStandardConversion(Unit sourceUnit, Unit targetUnit, Float conversionFactor) {
        StandardConversion standardConversion = new StandardConversion(sourceUnit: sourceUnit, targetUnit: targetUnit, conversionFactor: conversionFactor)
        return standardConversion
    }
}
