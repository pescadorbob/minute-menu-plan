package com.mp.domain

import java.util.regex.Pattern
import com.mp.domain.party.Party
import com.mp.tools.UserTools

class Item {
    static searchable = true

    Float density = 1.0f
    String name
    Aisle suggestedAisle
    Boolean shareWithCommunity = false
    Boolean isAlcoholic = false

    String toString() {
        return name
    }

    static constraints = {
        suggestedAisle(nullable: true)
    }

    static mapping = {
        tablePerHierarchy false
        sort 'name'
        density(sqlType: 'numeric(15, 10)')
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Item other = Item.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }

    public static List<Item> getItemsForCurrentUser(String matches = "%%") {
        Party party = UserTools.currentUser?.party
        List<Item> itemsByUser = getItemsForUser(party, matches)
        return itemsByUser
    }

    public static List<Item> getItemsForUser(Party party, String matchString = "%%") {
        Boolean showAlcoholicContent = party.showAlcoholicContent
        List<Item> totalItems = []
        List<Item> items = []
        List<Item> itemsByUser = []
        List<Item> recipesByUser = []
        if (!party.showAlcoholicContent) {
            if(matchString == '%%'){
                items = Item?.findAllByIsAlcoholic(false) as List
                items.addAll(Recipe?.findAllByIsAlcoholic(false) as List)
                items.addAll(Product?.findAllByIsAlcoholic(false) as List)
                items.addAll(MeasurableProduct?.findAllByIsAlcoholic(false) as List)
            } else {
                items = Item?.findAllByNameIlikeAndIsAlcoholic(matchString, false) as List
                items.addAll(Recipe?.findAllByNameIlikeAndIsAlcoholic(matchString, false) as List)
                items.addAll(Product?.findAllByNameIlikeAndIsAlcoholic(matchString, false) as List)
                items.addAll(MeasurableProduct?.findAllByNameIlikeAndIsAlcoholic(matchString, false) as List)
            }
            items = items?.findAll {it?.shareWithCommunity} as List
            matchString = matchString.replace("%", ".*")
            itemsByUser = party?.ingredients?.findAll {!it.isAlcoholic && it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
            recipesByUser = (party?.contributions as List).findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE)) && (!it.isAlcoholic)} as List
        } else {
            items = Item?.findAllByNameIlike(matchString)?.findAll {it?.shareWithCommunity} as List
            matchString = matchString.replace("%", ".*")
            itemsByUser = party?.ingredients?.findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
            recipesByUser = (party?.contributions as List).findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
        }
        totalItems = items + itemsByUser + recipesByUser
        totalItems.unique()
        return totalItems.sort {it.name}
    }

    public static List<Item> getProductsForCurrentUser(String matches = "%%"){
        Party party = UserTools.currentUser?.party
        List<Item> itemsByUser = getProductsForUser(party, matches)
        return itemsByUser
    }

    public static List<Item> getProductsForUser(Party party, String matchString = "%%") {
        List<Item> totalItems = []
        List<Item> items = []
        List<Item> itemsByUser = []
        if (!party.showAlcoholicContent) {
            items = Product?.findAllByNameIlikeAndIsAlcoholic(matchString, false)?.findAll {it?.shareWithCommunity} as List
            items+=MeasurableProduct?.findAllByNameIlikeAndIsAlcoholic(matchString, false)?.findAll {it?.shareWithCommunity} as List
            matchString = matchString.replace("%", ".*")
            itemsByUser = party?.ingredients?.findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE)) && (!it.isAlcoholic)} as List
        } else {
            items = Product?.findAllByNameIlike(matchString)?.findAll {it?.shareWithCommunity} as List
            items+=MeasurableProduct?.findAllByNameIlikeAndIsAlcoholic(matchString, false)?.findAll {it?.shareWithCommunity} as List
            matchString = matchString.replace("%", ".*")
            itemsByUser = party?.ingredients?.findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
        }
        totalItems = items + itemsByUser
        return totalItems.sort {it.name}
    }

}
