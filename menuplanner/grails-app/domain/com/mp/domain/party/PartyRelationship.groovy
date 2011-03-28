package com.mp.domain.party

class PartyRelationship {

  PartyRole to
  PartyRole frum

  Date activeFrom = new Date()
  Date activeTo

  static belongsTo = [to: PartyRole]

  static constraints = {
    activeTo(nullable: true)
  }

  static mapping = {
    tablePerHierarchy false
  }

}
