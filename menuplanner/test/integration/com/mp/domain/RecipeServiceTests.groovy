package com.mp.domain

import grails.test.*
import com.mp.domain.party.Party
import com.mp.domain.Product
import com.mp.domain.Item
import com.mp.domain.RecipeIngredient
import com.mp.domain.Recipe
import com.mp.MenuConstants

class RecipeServiceTests extends GrailsUnitTestCase {
  Party party1
  Party party2
  Party partyAlcoholic
  Party partyNonAlcoholic
  RecipeService recipeService
  protected void setUp() {
      super.setUp()
      mockConfig '''bootstrapMode:true'''
      Object.metaClass.s = MenuConstants.s
      mockDomain(Quantity)
      mockDomain(Unit)
      mockDomain(Recipe)
      mockDomain(Item)
      mockDomain(Product)
      mockDomain(MeasurableProduct)
      mockDomain(RecipeIngredient)
      mockDomain(Party)
      mockDomain(Aisle)
      party1 = new Party(name: "party_1")
      party1.save()
      party2 = new Party(name: "party_2")
      party2.save()
      partyAlcoholic = new Party(name: "party_Alcoholic", showAlcoholicContent: true)
      partyAlcoholic.save()
      partyNonAlcoholic = new Party(name: "party_Alcoholic", showAlcoholicContent: false)
      partyNonAlcoholic.save()
      Item item = new Item(name: "TestItem")
      item.save()
      Product.metaClass.isProductAlcoholic = {return delegate.isAlcoholic}
      recipeService = new RecipeService()
  }


  protected void tearDown() {
      super.tearDown()
  }

  void testItemEdit() {
    Item item = Item.findByName('TestItem')
    assert item
    item.setName("Changed")
    item.save(flush: true)
    item = Item.findByname('Changed')
    assert item
  }
  void test_getItemsForUser_Valid_Recipes() {
      createRecipeInstances(10, party1)
      List<Item> recipeByUser = recipeService.getItemsForUser(party1)
      assertEquals "Unable to create recipes for user", 10, recipeByUser.size()
  }

  void test_getItemsForUser_First_User_Recipe_Invisible_To_Another() {
      def recs = createRecipeInstances(10, party1)
      assertEquals "Recipes weren't created",10,recs.size()
      List<Item> recipeByUser = recipeService.getItemsForUser(party1)
      assertEquals "Unable to create recipes for user", 10, recipeByUser.size()
      List<Item> recipeForUser = recipeService.getItemsForUser(party2)
      assertEquals "Invisible recipe visible in other user's list", 0, recipeForUser.size()
  }

  void test_getItemsForUser_First_User_Recipe_Visible_To_Another() {
      def recs = createRecipeInstances(10, party1,true)
      assertEquals "Recipes weren't created",10,recs.size()
      List<Item> recipeByUser = recipeService.getItemsForUser(party1)
      assertEquals "Unable to get recipes for user", 10, recipeByUser.size()
      List<Item> recipeForUser = recipeService.getItemsForUser(party2)
      assertEquals "Invisible recipe visible in other user's list", 10, recipeForUser.size()
  }

  void test_getItemsForUser_Products_Visible_ToUser() {
      createProductInstances(10, party1)
      List<Item> productByUser = recipeService.getItemsForUser(party1)
      assertEquals "Unable to create products for user", 10, productByUser.size()
  }

  void test_getItemsForUser_Products_Like_Query() {
      createProductInstances_Diff_Name(party1)
      List<Item> butterProducts = recipeService.getItemsForUser(party1, '%But%')
      assertEquals "Unable to create products-Butter for user", 3, butterProducts.size()
      List<Item> milkProducts = recipeService.getItemsForUser(party1, '%Mil%')
      assertEquals "Unable to create products-Milk for user", 2, milkProducts.size()
  }

  void test_getItemsForUser_Products_Ilike_Query() {
      createProductInstances_Diff_Name(party1)
      List<Item> butterProducts = recipeService.getItemsForUser(party1, '%but%')
      assertEquals "Unable to create products-Butter for user", 3, butterProducts.size()
      List<Item> milkProducts = recipeService.getItemsForUser(party1, '%mil%')
      assertEquals "Unable to create products-Milk for user", 2, milkProducts.size()
  }

  void test_getItemsForUser_Products_InVisible_To_Other_User() {
      createProductInstances(10, party1)
      createProductInstances(20, party2)
      List<Item> productByUser1 = recipeService.getItemsForUser(party1)
      assertEquals "Unable to create products for user", 10, productByUser1.size()
      List<Item> productByUser2 = recipeService.getItemsForUser(party2)
      assertEquals "Unable to create products for user", 20, productByUser2.size()
  }

  void test_getItemsForUser_Items_Visible_To_All() {
      createItemInstances(10, true)
      List<Item> items = recipeService.getItemsForUser(party1)
      assertEquals "Unable to create Items ", 10, items.size()
  }

  void test_getItemsForUser_Items_Invisible_To_All() {
      createItemInstances(10)
      List<Item> items = recipeService.getItemsForUser(party1)
      assertEquals "Unable to create Items ", 0, items.size()
  }

  void test_getItemsForUser_All_User_Items() {
      createItemInstances(20, true)
      List<Item> items1 = recipeService.getItemsForUser(party1)
      List<Item> items2 = recipeService.getItemsForUser(party2)
      assertEquals "Unable to create/show items for user 1 ", 20, items1.size()
      assertEquals "Unable to create/show Items for user 2 ", 20, items2.size()
  }

  void test_getItemsForUser_All() {
      createRecipeInstances(10, party1)
      createProductInstances(10, party1)
      createItemInstances(20, true)
      List<Item> totalItems = recipeService.getItemsForUser(party1)
      assertEquals "Unable to create Instances for user", 40, totalItems.size()
  }


  void test_getItemsForUser_All_Items_Alcoholic() {
      createProductInstances(10, partyAlcoholic, true)
      createItemInstances(20, true)
      List<Item> totalItems = recipeService.getItemsForUser(partyAlcoholic)
      assertEquals "Unable to get Items for user", 30, totalItems.size()
  }

  void test_getItemsForUser_All_Items_Non_Alcoholic() {
      createProductInstances(10, partyAlcoholic, false)
      createItemInstances(20, true)
      List<Item> totalItems = recipeService.getItemsForUser(partyAlcoholic)
      assertEquals "Unable to get Items for user", 30, totalItems.size()
  }

  void test_getItemsForUser_All_Items_Alcoholic_Party_Non_Alcoholic() {
      partyNonAlcoholic.ingredients = []
      partyNonAlcoholic.contributions = []
      createProductInstances(10, partyNonAlcoholic, true)
      createItemInstances(20, true, true)
      List<Item> totalItems = recipeService.getItemsForUser(partyNonAlcoholic)
      assertEquals "Got wrong items for user", 0, totalItems.size()
  }

  void test_getItemsForUser_All_Items_NonAlcoholic_Party_NonAlcoholic() {
      createProductInstances(10, partyNonAlcoholic, false)
      createItemInstances(20, true, false)
      List<Item> totalItems = recipeService.getItemsForUser(partyNonAlcoholic)
      assertEquals "Got wrong items for user", 30, totalItems.size()
  }

  void test_getItemsForUser_Recipe_Alcoholic_Party_NonAlcoholic() {
      createRecipeInstances(10, partyNonAlcoholic, true, true)
      List<Item> totalItems = recipeService.getItemsForUser(partyNonAlcoholic)
      assertEquals "Got wrong recipes for user", 0, totalItems.size()
  }

  void test_getItemsForUser_Recipe_Non_Alcoholic_Party_NonAlcoholic() {
      createRecipeInstances(10, partyNonAlcoholic, true, false)
      List<Item> totalItems = recipeService.getItemsForUser(partyNonAlcoholic)
      assertEquals "Got wrong recipes for user", 10, totalItems.size()
  }

  void test_getProductsForUser_Only_Recipes() {
      createRecipeInstances(10, party1)
      List<Item> result = Item.getProductsForUser(party1)
      assertEquals "Found recipes(wrong items) for user", 0, result.size()
  }

  void test_getProductsForUser_Only_Recipes_By_Other_User() {
      createRecipeInstances(10, party2)
      List<Item> result = Item.getProductsForUser(party1)
      assertEquals "Found recipes(wrong items) for user", 0, result.size()
  }

  void test_getProductsForUser_Only_Recipes_By_Both_Users() {
      createRecipeInstances(10, party1)
      createRecipeInstances(10, party2)
      List<Item> result = Item.getProductsForUser(party1)
      assertEquals "Found recipes(wrong items) for user", 0, result.size()
  }

  void test_getProductsForUser_Only_Recipes_By_Other_User_Share_With_Community() {
      createRecipeInstances(10, party2, true)
      List<Item> result = Item.getProductsForUser(party1)
      assertEquals "Found recipes(wrong items) for user", 0, result.size()
  }

  void test_getProductsForUser_Only_Products() {
      createProductInstances(10, party1)
      List<Item> result = Item.getProductsForUser(party1)
      assertEquals "Unable to get products for user", 10, result.size()
  }

  void test_getProductsForUser_Another_User_Product() {
      createProductInstances(10, party1)
      createProductInstances(10, party2)
      List<Item> result1 = Item.getProductsForUser(party1)
      List<Item> result2 = Item.getProductsForUser(party2)
      assertEquals "Unable to get products for user-1", 10, result1.size()
      assertEquals "Unable to get products for user-2", 10, result2.size()
  }

  void test_getProductsForUser_Products_Like_Query() {
      createProductInstances_Diff_Name(party1)
      List<Item> butterProducts = Item.getProductsForUser(party1, '%But%')
      assertEquals "Unable to create products-Butter for user", 3, butterProducts.size()
      List<Item> milkProducts = Item.getProductsForUser(party1, '%Mil%')
      assertEquals "Unable to create products-Milk for user", 2, milkProducts.size()
  }

  void test_getProductssForUser_Products_Ilike_Query() {
      createProductInstances_Diff_Name(party1)
      List<Item> butterProducts = Item.getProductsForUser(party1, '%but%')
      assertEquals "Unable to create products-Butter for user", 3, butterProducts.size()
      List<Item> milkProducts = Item.getProductsForUser(party1, '%mil%')
      assertEquals "Unable to create products-Milk for user", 2, milkProducts.size()
  }

  void test_getProductsForUser_All_User_Products() {
      createProductInstances(20, party1, true)
      createProductInstances(10, party2, false)
      List<Item> items1 = Item.getProductsForUser(party1)
      List<Item> items2 = Item.getProductsForUser(party2)
      assertEquals "Unable to create/show items for user 1 ", 0, items1.size()
      assertEquals "Unable to create/show Items for user 2 ", 10, items2.size()
  }

  void test_getProductssForUser_All_Products_Alcoholic() {
      createProductInstances(10, partyAlcoholic, true)
      List<Item> totalItems = Item.getProductsForUser(partyAlcoholic)
      assertEquals "Unable to get Items for user", 10, totalItems.size()
  }

  void test_getProductsForUser_Products_Mixed_Party_Alcoholic() {
      createProductInstances(10, partyAlcoholic, false)
      createProductInstances(20, partyAlcoholic, true)
      List<Item> totalItems = Item.getProductsForUser(partyAlcoholic)
      assertEquals "Unable to get Items for user", 30, totalItems.size()
  }

  void test_getProductsForUser_Products_Mixed_Party_Non_Alcoholic() {
      createProductInstances(10, partyNonAlcoholic, false)
      createProductInstances(20, partyNonAlcoholic, true)
      List<Item> totalItems = Item.getProductsForUser(partyNonAlcoholic)
      assertEquals "Unable to get Items for user", 10, totalItems.size()
  }

  void test_getProductsForUser_All_Products_Alcoholic_Party_Non_Alcoholic() {
      createProductInstances(10, partyNonAlcoholic, true)
      createItemInstances(20, true, true)
      List<Item> totalItems = Item.getProductsForUser(partyNonAlcoholic)
      assertEquals "Got wrong items for user", 0, totalItems.size()
  }


  def createRecipeInstances(Integer count, Party party, Boolean shareWithCommunity = false, Boolean containsAlcohol = false) {
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
    party.contributions
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
