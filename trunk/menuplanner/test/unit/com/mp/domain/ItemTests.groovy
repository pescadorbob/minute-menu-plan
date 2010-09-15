package com.mp.domain

import grails.test.*

class ItemTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
        def instances = []
        mockDomain(Item, instances)
        def riInstances = []
        mockDomain(RecipeIngredient, riInstances)
        def partyInstances = []
        mockDomain(Party, partyInstances)
        mockDomain(Aisle)
        Party party1 = new Party(name: "party_1")
        party1.save()
        Party party2 = new Party(name: "party_2")
        party2.save()
        Party partyAlcoholic = new Party(name: "party_Alcoholic", showAlcoholicContent: true)
        partyAlcoholic.save()
        Party partyNonAlcoholic = new Party(name: "party_Alcoholic", showAlcoholicContent: false)
        partyNonAlcoholic.save()
        Item item = new Item(name: "TestItem")
        item.save()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void test_getItemsForUser_Valid_Recipes() {
        Party party1 = Party.get(1)
        createRecipeInstances(10, party1)
        List<Item> recipeByUser = Item.getItemsForUser(party1)
        assertEquals "Unable to create recipes for user", 10, recipeByUser.size()
    }

    void test_getItemsForUser_First_User_Recipe_Invisible_To_Another() {
        Party party1 = Party.get(1)
        Party party2 = Party.get(2)
        createRecipeInstances(10, party1)
        List<Item> recipeByUser = Item.getItemsForUser(party1)
        assertEquals "Unable to create recipes for user", 10, recipeByUser.size()
        List<Item> recipeForUser = Item.getItemsForUser(party2)
        assertEquals "Invisible recipe visible in other user's list", 0, recipeForUser.size()
    }

    void test_getItemsForUser_First_User_Recipe_Visible_To_Another() {
        Party party1 = Party.get(1)
        Party party2 = Party.get(2)
        createRecipeInstances(10, party1, true)
        List<Item> recipeByUser = Item.getItemsForUser(party1)
        assertEquals "Unable to create recipes for user", 10, recipeByUser.size()
        List<Item> recipeForUser = Item.getItemsForUser(party2)
        assertEquals "Invisible recipe visible in other user's list", 10, recipeForUser.size()
    }

    void test_getItemsForUser_Products_Visible_ToUser() {
        Party party1 = Party.get(1)
        createProductInstances(10, party1)
        List<Item> productByUser = Item.getItemsForUser(party1)
        assertEquals "Unable to create products for user", 10, productByUser.size()
    }

    void test_getItemsForUser_Products_Like_Query() {
        Party party1 = Party.get(1)
        createProductInstances_Diff_Name(party1)
        List<Item> butterProducts = Item.getItemsForUser(party1, '%But%')
        assertEquals "Unable to create products-Butter for user", 3, butterProducts.size()
        List<Item> milkProducts = Item.getItemsForUser(party1, '%Mil%')
        assertEquals "Unable to create products-Milk for user", 2, milkProducts.size()
    }

    void test_getItemsForUser_Products_Ilike_Query() {
        Party party1 = Party.get(1)
        createProductInstances_Diff_Name(party1)
        List<Item> butterProducts = Item.getItemsForUser(party1, '%but%')
        assertEquals "Unable to create products-Butter for user", 3, butterProducts.size()
        List<Item> milkProducts = Item.getItemsForUser(party1, '%mil%')
        assertEquals "Unable to create products-Milk for user", 2, milkProducts.size()
    }

    void test_getItemsForUser_Products_InVisible_To_Other_User() {
        Party party1 = Party.get(1)
        Party party2 = Party.get(2)
        createProductInstances(10, party1)
        createProductInstances(20, party2)
        List<Item> productByUser1 = Item.getItemsForUser(party1)
        assertEquals "Unable to create products for user", 10, productByUser1.size()
        List<Item> productByUser2 = Item.getItemsForUser(party2)
        assertEquals "Unable to create products for user", 20, productByUser2.size()
    }

    void test_getItemsForUser_Items_Visible_To_All() {
        Party party1 = Party.get(1)
        createItemInstances(10, true)
        List<Item> items = Item.getItemsForUser(party1)
        assertEquals "Unable to create Items ", 10, items.size()
    }

    void test_getItemsForUser_Items_Invisible_To_All() {
        Party party1 = Party.get(1)
        createItemInstances(10)
        List<Item> items = Item.getItemsForUser(party1)
        assertEquals "Unable to create Items ", 0, items.size()
    }

    void test_getItemsForUser_All_User_Items() {
        Party party1 = Party.get(2)
        Party party2 = Party.get(2)
        createItemInstances(20, true)
        List<Item> items1 = Item.getItemsForUser(party1)
        List<Item> items2 = Item.getItemsForUser(party2)
        assertEquals "Unable to create/show items for user 1 ", 20, items1.size()
        assertEquals "Unable to create/show Items for user 2 ", 20, items2.size()
    }

    void test_getItemsForUser_All() {
        Party party1 = Party.get(1)
        createRecipeInstances(10, party1)
        createProductInstances(10, party1)
        createItemInstances(20, true)
        List<Item> totalItems = Item.getItemsForUser(party1)
        assertEquals "Unable to create Instances for user", 40, totalItems.size()
    }


    void test_getItemsForUser_All_Items_Alcoholic() {
        Party partyAlcoholic = Party.get(3)
        createProductInstances(10, partyAlcoholic, true)
        createItemInstances(20, true)
        List<Item> totalItems = Item.getItemsForUser(partyAlcoholic)
        assertEquals "Unable to get Items for user", 30, totalItems.size()
    }

    void test_getItemsForUser_All_Items_Non_Alcoholic() {
        Party partyAlcoholic = Party.get(3)
        createProductInstances(10, partyAlcoholic, false)
        createItemInstances(20, true)
        List<Item> totalItems = Item.getItemsForUser(partyAlcoholic)
        assertEquals "Unable to get Items for user", 30, totalItems.size()
    }

    void test_getItemsForUser_All_Items_Alcoholic_Party_Non_Alcoholic() {
        Party partyNonAlcoholic = Party.get(4)
        createProductInstances(10, partyNonAlcoholic, true)
        createItemInstances(20, true, true)
        List<Item> totalItems = Item.getItemsForUser(partyNonAlcoholic)
        assertEquals "Got wrong items for user", 0, totalItems.size()
    }

    void test_getItemsForUser_All_Items_NonAlcoholic_Party_NonAlcoholic() {
        Party partyNonAlcoholic = Party.get(4)
        createProductInstances(10, partyNonAlcoholic, false)
        createItemInstances(20, true, false)
        List<Item> totalItems = Item.getItemsForUser(partyNonAlcoholic)
        assertEquals "Got wrong items for user", 30, totalItems.size()
    }

    void test_getItemsForUser_Recipe_Alcoholic_Party_NonAlcoholic() {
        Party partyNonAlcoholic = Party.get(4)
        createRecipeInstances(10, partyNonAlcoholic, true, true)
        List<Item> totalItems = Item.getItemsForUser(partyNonAlcoholic)
        assertEquals "Got wrong recipes for user", 0, totalItems.size()
    }

    void test_getItemsForUser_Recipe_Non_Alcoholic_Party_NonAlcoholic() {
        Party partyNonAlcoholic = Party.get(4)
        createRecipeInstances(10, partyNonAlcoholic, true, false)
        List<Item> totalItems = Item.getItemsForUser(partyNonAlcoholic)
        assertEquals "Got wrong recipes for user", 10, totalItems.size()
    }

    void createRecipeInstances(Integer count, Party party, Boolean shareWithCommunity = false, Boolean containsAlcohol = false) {
        (1..count).each {
            Item item = Item.get(1)
            RecipeIngredient recipeIngredient = new RecipeIngredient(ingredient: item)
            Recipe recipe = new Recipe(name: "testRecipe_${it}", servings: 2, ingredients: [recipeIngredient], directions: ['How To Cook'], isAlcoholic: containsAlcohol)
            if (shareWithCommunity) {
                recipe.shareWithCommunity = true
            }
            if (recipe.hasErrors() || !recipe.save()) {
                recipe.errors.allErrors.each {
                    println it
                }
            }
            party.contributions += recipe
            party.save()
        }
    }

    void createProductInstances(Integer count, Party party, Boolean containsAlcohol = false) {
        (1..count).each {
            Product product = new Product(name: "Product_${it}", isAlcoholic: containsAlcohol)
            product.save()
            party.ingredients += product
        }
    }

    void createProductInstances_Diff_Name(Party party) {
        List<String> strings = ['Butter', 'Butter Milk', 'Milk', 'Butter Naan']
        strings.each { String name ->
            Product product = new Product(name: name)
            product.save()
            party.ingredients += product
        }
    }

    void createItemInstances(Integer count, Boolean shareWithCommunity = false, Boolean containsAlcohol = false) {
        (1..count).each {
            Item item = new Item(name: "Item_${it}", isAlcoholic: containsAlcohol)
            if (shareWithCommunity) {
                item.shareWithCommunity = true
            }
            item.save()
        }
    }
}
