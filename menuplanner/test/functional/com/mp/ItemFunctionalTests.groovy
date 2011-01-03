package com.mp

import com.mp.domain.Item
import com.mp.domain.Product

class ItemFunctionalTests extends MenuPlannerFunctionalTests {

    void test_ItemEdit() {
        Item item = Product.list().last()
        assert item
        String oldName = System.currentTimeMillis().toString()
        item.name = oldName
        item.save(flush: true)
        item = Item.findById(item.id)
        assert item
        loginBySuperAdmin()
        get('/item/edit/'+item.id)
        String newName = System.currentTimeMillis().toString()
        form('itemEdit') {
            name = newName
            byName("_action_update").click()
        }
        item = item.refresh()
        assertEquals('The item name isn\'t changed', newName, item.name);
    }

}
