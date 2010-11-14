import MenuPlannerFunctionalTests
import com.mp.domain.Item

class ItemFunctionalTests extends MenuPlannerFunctionalTests {

    void test_ItemEdit() {
      Item item = Item.get(210)
      assert item
      item.setName("Changed")
      item.save(flush: true)
      item = Item.findByname('Changed')
      assert item
       item = Item.get(210)

        loginBySuperAdmin()
        get('/item/show/210')
        def editButton = byClass('edit')
        editButton.click()
      def nameEdit = byClass('name')
        form('itemEdit') {
            name = "New name"
            click("_action_edit")
        }
      item = Item.get(210)
      assertEquals('The item name isn\'t changed',"New name",item.name);
        logout()
    }

}
