package com.mp.domain

import com.mp.domain.Product
import com.mp.domain.Unit
import com.mp.domain.ndb.ItemNutritionLink
import com.mp.domain.ndb.ItemNutritionLink

class MeasurableProduct extends Product{
    static searchable = true
              
    Unit preferredUnit
    String toString() {
        return name
    }

    static constraints = {
        name()
        preferredUnit()
    }

    static mapping = {
        tablePerHierarchy false
        sort 'name'
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final MeasurableProduct other = MeasurableProduct.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }

}
