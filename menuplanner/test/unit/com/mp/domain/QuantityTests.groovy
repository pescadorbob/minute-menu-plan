package com.mp.domain

import grails.test.*
import org.hibernate.classic.Session
import org.apache.commons.lang.math.Fraction
import com.mp.tools.UnitUtil

class QuantityTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

//

  void test1_checkForNulls_add(){
    def instances = []
    def scInstances = []
    mockDomain(Unit,instances)
    mockDomain(StandardConversion,scInstances)
    Unit savedUnit = new Unit( name:'savedUnitname',symbol:'S',definition:'def', metricType:MetricType.UNIT)
    Unit unit = new Unit( name:'unitname',symbol:'S',definition:'def', metricType:MetricType.UNIT)
    Quantity quant1 = new Quantity(value:10.0,unit:unit,savedUnit:savedUnit)
    Quantity quant2
      def stanCon = mockFor(StandardConversionService,true)
    String str= "1 1 / 2"
      def unitUtilCon = mockFor(UnitUtil,true)
          unitUtilCon.demand.static.getQuantityValueString(1..2) { Quantity quantity,Float c -> return str }
          stanCon.demand.static.getQuantityToSave(1..2) {String a, Unit b,Float c -> return new Quantity(value:15.0,unit:unit,savedUnit:savedUnit) }
      Quantity quant = Quantity.add(quant1,quant2,scInstances)
    assertNotNull quant
    assertEquals 10.0,quant.value

    quant2= quant1
    quant1=null

    quant = Quantity.add(quant1,quant2)
    assertNotNull quant
    assertEquals 10.0,quant.value

  }
   void test1_valid_add(){
    def instances = []
    def unitInstances = []
    def scInstances = []
    mockDomain(StandardConversion,scInstances)
    mockDomain(SystemOfUnit,unitInstances)
    mockDomain(Unit,instances)
    mockDomain(StandardConversion)

    Unit unit = new Unit( name:'unitname',symbol:'S',definition:'def', metricType:MetricType.UNIT)
    Unit savedUnit = new Unit( name:'savedUnitname',symbol:'S',definition:'def', metricType:MetricType.UNIT)

    Quantity quant1 = new Quantity(value:10.0,unit:unit,savedUnit:savedUnit)
    Quantity quant2 = new Quantity(value:20.0,unit:unit,savedUnit:savedUnit)

    def stanCon = mockFor(StandardConversionService,true)
    String str= "1 1 / 2"
     def unitUtilCon = mockFor(UnitUtil,true)
         unitUtilCon.demand.static.getQuantityValueString(1..2) { Quantity quantity,Float c -> return str }
    stanCon.demand.static.getQuantityToSave(1..2) {String a, Unit b,Float c -> return new Quantity(value:15.0,unit:unit,savedUnit:savedUnit) }

    Quantity quant = Quantity.add(quant1,quant2,scInstances)
    assertNotNull quant
    assertEquals 30,quant.value
  }

  void test1_Invalid_add(){
    def instances = []
    def unitInstances = []

    mockDomain(SystemOfUnit,unitInstances)
    mockDomain(Unit,instances)
    mockDomain(StandardConversion)

    Unit unit = new Unit( name:'unitname',symbol:'S',definition:'def', metricType:MetricType.UNIT)
    Unit savedUnit = new Unit( name:'savedUnitname',symbol:'S',definition:'def', metricType:MetricType.UNIT)

    Quantity quant1 = new Quantity(value:null,unit:null,savedUnit:savedUnit)
    Quantity quant2 = new Quantity(value:null,unit:null,savedUnit:savedUnit)


    def stanCon = mockFor(StandardConversionService,true)
    String str= "1 1 / 2"
    def unitUtilCon = mockFor(UnitUtil,true)
        unitUtilCon.demand.static.getQuantityValueString(1..2) { Quantity quantity,Float c -> return str }
    stanCon.demand.static.getQuantityToSave(1..2) {String a, Unit b -> return null }

    Quantity quant = Quantity.add(quant1,quant2)
    assertNull quant
  }

   //todo yet to be worked upon
  void test2_valid_add(){
//    def instances = []
//    def unitInstances = []
//
//    mockDomain(SystemOfUnit,unitInstances)
//    mockDomain(Unit,instances)
//    mockDomain(StandardConversion)
//
//    Unit unit = new Unit( name:'unitname',symbol:'S',definition:'def', metricType:MetricType.UNIT)
//    Unit savedUnit = new Unit( name:'savedUnitname',symbol:'S',definition:'def', metricType:MetricType.UNIT)
//
//    Quantity quant1 = new Quantity(savedUnit:savedUnit)
//    Quantity quant2 = new Quantity(savedUnit:savedUnit)
//
//
//    def stanCon = mockFor(StandardConversion,true)
//    String str= "1 1 / 2"
//    stanCon.demand.static.getQuantityValueString(1..2) { Quantity quantity -> return str }
//    stanCon.demand.static.getQuantityToSave(1..2) {String a, Unit b -> return new Quantity(value:15.0,unit:unit,savedUnit:savedUnit) }
//
//    Quantity quant = Quantity.add(quant1,quant2)
//    assertNotNull quant
//    assertEquals 30,quant.value
  }




}
