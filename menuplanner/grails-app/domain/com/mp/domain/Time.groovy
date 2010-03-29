package com.mp.domain

class Time {
    Integer minutes
    TimeUnit preferredUnit
    String toString(){
        if(preferredUnit.name=='Minutes'){
               return "${minutes} Minutes"
        } else {
            return "${minutes/60} Hours"
        }
    }
    static constraints = {
        minutes()
        preferredUnit()
    }
}
