package com.mp.domain

import com.mp.domain.party.Party

class Company extends Party{

  static transients = ['canViewItem', 'isEnabledString', 'email', 'password', 'userLogin', 'role', 'administrator', 'superAdmin', 'subscriber', 'director', 'coach']
    static constraints = {
    }

    static mapping = {
        tablePerHierarchy false
    }

}
