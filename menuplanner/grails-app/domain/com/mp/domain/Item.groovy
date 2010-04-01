package com.mp.domain

class Item {

    String name
    static hasMany = [recipes:Recipe]

    String toString(){
        return "${name}";
    }

    static constraints = {
    }
}
