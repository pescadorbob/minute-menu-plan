package com.mp.ndb

import com.mp.domain.ndb.NDBFileInfo
import com.mp.domain.ndb.NDBFood
import com.mp.domain.ndb.NDBWeight
import com.mp.ndb.NdbService
import com.mp.test.AbstractPersistenceTestCase
import grails.test.GrailsUnitTestCase

class NdbServiceTests extends GrailsUnitTestCase {
  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testSomething() {
    mockDomain(NDBFileInfo,[])
    mockDomain(NDBFood,[])
    mockDomain(NDBWeight,[])
    // this is too long to run...
//    fail("This is just too long to run")
    if(true)return // comment this out to retry this very long test...

    def ndbTestService = new NdbService()
//    ndbTestService.sessionFactory = sessionFactory
    def f = new File("./web-app/bootstrapData/ABBREV.txt")
    ndbTestService.importFoodData(f, "23", new Date(), new Date())
    def w = new File("./web-app/bootstrapData/WEIGHT.txt")
    ndbTestService.importWeight(w, "23", new Date(), new Date())

    assert NDBFileInfo.count() == 1
    assertEquals(100, NDBFood.count())
    assertEquals(10, NDBWeight.count())
  }
}
