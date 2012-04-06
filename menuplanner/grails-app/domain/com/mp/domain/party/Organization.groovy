package com.mp.domain.party
/**
 */

class Organization extends Party {
  
  static hasMany = [otherOrganizationNames:OrganizationName]
  String description
  static constraints = {
    description(nullable: true)
  }
  static transients = ['name','canViewItem', 'isEnabledString', 'email', 'password', 'userLogin', 'role', 'administrator', 'superAdmin', 'subscriber', 'director', 'coach']

    String getName(){
        return this.organizationName.name
    }
  String formatName(){
    return this.organizationName.name
  }


}