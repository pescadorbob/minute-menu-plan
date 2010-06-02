package com.mp.domain

class Item {
    static searchable = true
    
    String name

    String toString(){
        return name
    }

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
		sort 'name'
    }

    public Item shallowClone(){
        Item item = new Item()
        item.name=this.name
        return item
    }
}
