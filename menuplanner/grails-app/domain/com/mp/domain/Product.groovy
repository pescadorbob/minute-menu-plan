package com.mp.domain

import org.apache.lucene.document.NumberTools
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Product extends Item {
    static searchable = true

    Boolean isVisible = false
    static config = ConfigurationHolder.config

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true, nullable: false)
    }

    static mapping = {
        tablePerHierarchy false
    }

    static transients = ['contributorsString', 'contributors']

    String getContributorsString() {
        String searchString = ''
        if (!config.bootstrapMode) {
            List<Party> parties = contributors
            List<String> searchList = []
            if (parties) {
                parties.each {Party p ->
                    searchList.add(NumberTools.longToString(p?.id))
                }
                searchString = searchList.join(', ')
            } else {
                searchString = NumberTools.longToString(0L)
            }
        }
        return searchString
    }

    def getContributors() {
        return Party.createCriteria().list {
            ingredients {
                eq('id', this.id)
            }
        }
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Product other = Product.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }

}
