package com.mp.domain.party

import com.mp.util.CommonUtil

class OrganizationName {
  String name
  String description

  static constraints = {
    description(nullable: true)
  }

  def beforeInsert = {
    name = CommonUtil.initCap(name)
    removeUnwantedChar()
  }

  def beforeUpdate = {
    name = CommonUtil.initCap(name)
    removeUnwantedChar()
  }

  private void removeUnwantedChar() {
    List removeChars = ["'", '"']   // to remove ' and "
    name = CommonUtil.removeSpecialCharacters(name, removeChars)
  }
}
