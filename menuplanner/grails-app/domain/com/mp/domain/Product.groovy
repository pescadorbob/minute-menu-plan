package com.mp.domain

import org.apache.lucene.document.NumberTools
import com.mp.domain.party.Party

class Product extends Item {
    static searchable = true

    Boolean isVisible = false

    String toString() {
        return name
    }

    static constraints = {
        name(unique: true, nullable: false)
    }

    static mapping = {
        tablePerHierarchy false
        sort 'name'
    }

    def beforeInsert = {
        isAlcoholic = isProductAlcoholic()
        return true
    }

    def beforeUpdate = {
        isAlcoholic = isProductAlcoholic()
        return true //This line is required, else changes are not saved when isAlcoholic is false
    }
    static transients = ['detailString', 'contributorsString', 'contributors']

    String getContributorsString() {
        String searchString = ''
        if (!org.codehaus.groovy.grails.commons.ConfigurationHolder.config.bootstrapMode) {
            List<Party> parties = contributors
            List<String> searchList = []
            if (parties) {
                parties.each {Party p ->
                    searchList.add(NumberTools.longToString(p?.id))
                }
                searchString = searchList.join(' , ')
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


    Boolean isProductAlcoholic() {
        Boolean result = false
        String productString = getDetailString()
        List<String> alcoholicStrings = org.codehaus.groovy.grails.commons.ConfigurationHolder.config?.alcoholicContentList ? org.codehaus.groovy.grails.commons.ConfigurationHolder.config?.alcoholicContentList : []
        alcoholicStrings = alcoholicStrings*.toLowerCase()
        result = alcoholicStrings.any {
            def pattern = /\b${it}\b/
            def matcher = productString =~ pattern
            return matcher.getCount() ? true : false
        }
        return result
    }

    String getDetailString() {
        List<String> allStrings = []
        String detailedString = ''
        allStrings.add(name)
        allStrings.add(suggestedAisle.toString())
        detailedString = allStrings.join(' , ')
        return detailedString.toLowerCase()
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Product other = Product.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }

}
