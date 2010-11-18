package com.mp.domain

import grails.test.*

class UnitTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_getSortedMetricUnits() {
        def instances = []
        mockDomain(SystemOfUnit, instances)

        def unitInstances = []
        mockDomain(Unit, unitInstances)
        Integer unitsCount = 10
        List<Unit> totalUnitObjects = getUnitObjects(unitsCount)
        List<Unit> returnedUnits = Unit.getSortedMetricUnits()
        assertEquals "Metric units count is not equals to Metric Unit created", unitsCount, returnedUnits.size()
        assertEquals "Total Metric units count is not equals to Metric Unit created", unitsCount * 3, totalUnitObjects.size()

    }

    List<Unit> getUnitObjects(Long total) {
        SystemOfUnit systemOfUnit = new SystemOfUnit(systemName: 'Test', standardizationBody: 'Test')
        systemOfUnit.save()
        List<Unit> unitsList = []
        (1..total).each {
            Unit unit = new Unit(name: "testunitmetric-${it}", metricType: MetricType.METRIC, symbol: 'symbol', definition: 'definition')
            unit.systemOfUnits = [systemOfUnit] as Set
            unit.save()
            unitsList.add(unit)
        }
        (1..total).each {
            Unit unit = new Unit(name: "testunittime-${it}", metricType: MetricType.TIME, symbol: 'symbol', definition: 'definition')
            unit.systemOfUnits = [systemOfUnit] as Set
            unit.save()
            unitsList.add(unit)
        }
        (1..total).each {
            Unit unit = new Unit(name: "testunitunit-${it}", metricType: MetricType.UNIT, symbol: 'symbol', definition: 'definition')
            unit.systemOfUnits = [systemOfUnit] as Set
            if (unit.hasErrors() || !unit.save()) {
                unit.errors.each {println it}
            }
            unitsList.add(unit)
        }
        return unitsList
    }
}
