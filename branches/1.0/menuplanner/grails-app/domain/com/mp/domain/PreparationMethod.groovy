package com.mp.domain

class PreparationMethod {
    String name

    String toString(){
        name
    }

    static constraints = {
        name(unique: true)
    }
}
