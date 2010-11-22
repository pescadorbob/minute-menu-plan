package com.mp.domain

import com.mp.domain.party.Party

class Company extends Party{

    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}
