package com.mp.domain
public enum DisplayType {

    DECIMALS("Decimals"),
    FRACTIONS("Fractions")


    String name

   DisplayType(String name){
        this.name = name
    }

    String toString(){
        return name
    }
}