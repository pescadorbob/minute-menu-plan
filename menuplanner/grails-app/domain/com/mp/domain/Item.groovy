package com.mp.domain

class Item {
    static searchable = true

    String name
    Aisle suggestedAisle

    String toString() {
        return name
    }

    static constraints = {
        suggestedAisle(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
        sort 'name'
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Item other = Item.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }
 
}
