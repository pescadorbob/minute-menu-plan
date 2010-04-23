package com.mp.domain

class StandardConversion {
    Unit sourceUnit
    Unit targetUnit
    Double conversionFactor

    //TODO: Implement static method convert(from)

    public static Quantity convert(Quantity fromQuantity){
        Quantity result = new Quantity()
        //TODO: implement Conversion function here
        result = fromQuantity
        return result
    }

    static constraints = {
    }
}
