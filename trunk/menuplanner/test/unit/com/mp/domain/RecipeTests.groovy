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

    void test_RecipeCO_Valid_Recipe_Name1() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO(name: "va'lidNa'me")
        rco.validate()
        assertEquals 2, rco.errors.allErrors.size()
    }

    void test_RecipeCO_Add_Recipe_With_Quotes_In_Name() {
        mockForConstraintsTests(RecipeCO)
        String recipeName= ''' McDonald's "Grilled" Chicken '''
        RecipeCO rco = new RecipeCO(name: recipeName)
        rco.validate()
        assertEquals 2, rco.errors.allErrors.size()
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

    void test_RecipeCO_VALID1_IngredientQuantities() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO()
        rco.ingredientQuantities = ['1']
        rco.validate()
        assertNull(rco.errors["ingredientQuantities"])
    }

    void test_RecipeCO_VALID2_IngredientQuantities() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO()
        rco.ingredientQuantities = ['1 / 2']
        rco.validate()
        assertNull(rco.errors["ingredientQuantities"])
    }

    void test_RecipeCO_VALID3_IngredientQuantities() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO()
        rco.ingredientQuantities = ['1 1/2']
        rco.validate()
        assertNull(rco.errors["ingredientQuantities"])
    }

    void test_RecipeCO_VALID4_IngredientQuantities() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO()
        rco.ingredientQuantities = ['1.5']
        rco.validate()
        assertNull(rco.errors["ingredientQuantities"])
    }
    void test_RecipeCO_Blank_IngredientQuantities() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO()
        rco.ingredientQuantities = ['']
        rco.validate()
        assertNull(rco.errors["ingredientQuantities"])
    }

    void test_RecipeCO_INVALID1_IngredientQuantities() {
        mockForConstraintsTests(RecipeCO)
        RecipeCO rco = new RecipeCO()
        rco.ingredientQuantities = ['10 grams']
        rco.validate()
        rco.errors.allErrors.each {
            println it
        }
        assertNotNull(rco.errors["ingredientQuantities"])
    }
}
