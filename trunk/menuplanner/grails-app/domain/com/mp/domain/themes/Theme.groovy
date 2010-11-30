package com.mp.domain.themes

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.domain.SessionUtils

/**
 * Created on Nov 15, 2010
 * Created by Brent Fisher
 *
 */

public class Theme {
  static config = ConfigurationHolder.config
  static hasMany = [pageElements: PageElement]
  Set<PageElement> pageElements
  String name
  Date activeTo
  Date activeFrom
  String controllerFilter
  String actionFilter
  String uriFilter
  Date lastModified = new Date()
  static constraints = {
    name(unique: true, blank: false)
    lastModified(nullable: true, blank: true)
    activeTo(nullable: true, blank: true)
    controllerFilter(nullable: true, blank: true)
    actionFilter(nullable: true, blank: true)
    uriFilter(nullable: true, blank: true)
  }

  static mapping = {
    pageElements lazy: false
  }
}