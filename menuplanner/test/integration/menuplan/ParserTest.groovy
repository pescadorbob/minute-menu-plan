package com.mp.domain

import grails.test.*

class ParserTest extends TagLibUnitTestCase {
    def userText = '''1/2 cup flour -- sifted
1 egg
2 large potatoes
4 cups sugar'''

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {
        def quantity = parse(userText)
        assertEquals 3, quantity.size
    }

  def sampleUnits = ['cup','teaspoon','tablespoon']

  Object parse(text) {
    userText.splitEachLine()
  }
}
