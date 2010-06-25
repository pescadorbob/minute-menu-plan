package com.mp.domain

class MeasurableProduct extends Product{
    static searchable = true
              
    static hasMany = [possibleUnits: Unit]
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
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final MeasurableProduct other = MeasurableProduct.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }

}
