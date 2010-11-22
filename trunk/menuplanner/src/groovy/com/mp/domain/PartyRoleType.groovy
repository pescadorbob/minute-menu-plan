package com.mp.domain

public enum PartyRoleType {

  Director("Director"),

  SuperAdmin("Super Admin"),
  Admin("Admin"),
  Subscriber("Subscriber"),
  Coach("Coach"),
  Guest("Guest")

  String name

  PartyRoleType(String name) {
    this.name = name
  }


  public static List<PartyRoleType> list() {
    return [SuperAdmin, Admin, Subscriber, Director, Coach, Guest]
  }

  String toString() {
    return name
  }

}