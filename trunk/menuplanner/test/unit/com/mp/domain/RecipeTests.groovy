package com.mp.domain

import grails.test.*

class RecipeTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_RecipeCO_Blank_Recipe_Name() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO(name: '')
        rco.validate()
        assertEquals 3, rco.errors.allErrors.size()
        assertEquals "recipeCO.name.blank.error.name", rco.errors["name"]
    }

    void test_RecipeCO_Valid_Recipe_Name() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO(name: 'validName')
        rco.validate()
        assertEquals 2, rco.errors.allErrors.size()
    }

    void test_RecipeCO_Invalid_Recipe_Name() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO(name: "va'lidNa'me")
        rco.validate()
        assertEquals 3, rco.errors.allErrors.size()
        assertEquals "recipeCO.name.singleQuote.error.message", rco.errors["name"]
    }

    void test_RecipeCO_Valid_Recipe_NameWithDoubleQuotes() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO(name: 'val"idNa"me')
        rco.validate()
        assertEquals 2, rco.errors.allErrors.size()
    }

    void test_RecipeCO_Valid_Recipe_NameWithSpecialCharacters() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO(name: 'ÄËÎÆzqÅ%$^*(!@@#"')
        rco.validate()
        assertEquals 2, rco.errors.allErrors.size()
    }
}
