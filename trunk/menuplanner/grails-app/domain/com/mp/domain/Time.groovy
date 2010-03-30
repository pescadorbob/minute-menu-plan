package com.mp.domain

class Time {
    Integer minutes
    TimeUnit preferredUnit

    //TODO: Use Constants for Minutes and Hours
    String toString() {
        return ((preferredUnit.name == 'Minutes') ? "${minutes} Minutes" : "${minutes / 60} Hours")
    }

    static constraints = {
    }
}
