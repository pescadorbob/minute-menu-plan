package com.mp.domain.party

class PartyRelationship {

  PartyRole client
  PartyRole supplier

  Date activeFrom = new Date()
  Date activeTo

  static belongsTo = [client: PartyRole]

  static constraints = {
    activeTo(nullable: true)
  }

  static mapping = {
    tablePerHierarchy false
  }

}
