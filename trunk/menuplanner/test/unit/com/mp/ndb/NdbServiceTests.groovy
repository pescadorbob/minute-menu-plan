package com.mp.ndb

import com.mp.domain.ndb.NDBFileInfo
import com.mp.domain.ndb.NDBFood
import com.mp.domain.ndb.NDBWeight
import com.mp.ndb.NdbService
import com.mp.test.AbstractPersistenceTestCase

class NdbServiceTests extends AbstractPersistenceTestCase {
  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testSomething() {
    def ndbTestService = new NdbService()
    ndbTestService.sessionFactory = sessionFactory
    def f = new File("./web-app/bootstrapData/ABBREV.txt")
    ndbTestService.importFoodData(f, "23", new Date(), new Date())
    def w = new File("./web-app/bootstrapData/WEIGHT.txt")
    ndbTestService.importWeight(w, "23", new Date(), new Date())

    assert NDBFileInfo.count() == 1
    assertEquals(7637, NDBFood.count())
    assertEquals(13199, NDBWeight.count())
  }

  public List getDomainClasses() {
    return [NDBFileInfo.class ,NDBFood.class, NDBWeight.class];  
  }


}
