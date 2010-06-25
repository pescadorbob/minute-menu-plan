package com.mp.domain

class Product extends Item{
    static searchable = true

    Boolean isVisible = false

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true, nullable:false)
    }

    static mapping = {
        tablePerHierarchy false
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Product other = Product.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }

}
