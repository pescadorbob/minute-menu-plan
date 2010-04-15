package com.mp.domain

class Item {

    String name

    String toString(){
        return name
    }

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }
}
