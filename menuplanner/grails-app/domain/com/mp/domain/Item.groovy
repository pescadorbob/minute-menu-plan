package com.mp.domain

import java.util.regex.Pattern

class Item {
    static searchable = true

    Float density = 1.0f
    String name
    Aisle suggestedAisle
    Boolean shareWithCommunity = false

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
        Party party = LoginCredential.currentUser?.party
        List<Item> itemsByUser = getItemsForUser(party, matches)
        return itemsByUser
    }

    public static List<Item> getItemsForUser(Party party, String matchString = "%%") {
        List<Item> totalItems = []
        List<Item> items = Item?.findAllByNameIlike(matchString)?.findAll {it?.shareWithCommunity} as List
        matchString = matchString.replace("%", ".*")
        List<Item> itemsByUser = party?.ingredients?.findAll {it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
        List<Item> recipesByUser = (party?.contributions as List).findAll {!it.shareWithCommunity && it?.name?.matches(Pattern.compile(matchString, Pattern.CASE_INSENSITIVE))} as List
        totalItems = items + itemsByUser + recipesByUser
        return totalItems.sort {it.name}
    }
}
