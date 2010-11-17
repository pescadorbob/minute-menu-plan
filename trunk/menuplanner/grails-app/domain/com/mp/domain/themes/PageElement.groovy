package com.mp.domain.themes

import com.mp.domain.ElementLocation
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created on Nov 15, 2010
 * Created by Brent Fisher
 *
 */

public class PageElement {
  static config = ConfigurationHolder.config

  static belongsTo = Theme

  Theme elementFor
  String name
  String text
  ElementLocation location
  String controllerFilter
  String actionFilter
  String uriFilter
  String contextRule // an extra rule if needed...
  boolean isTemplate

  Long listOrder
  Date lastModified = new Date()

  static constraints = {
    name(unique: true, blank: false)
    text(nullable: true, blank: true, maxSize: 20000)
    controllerFilter(nullable: true, blank: true)
    actionFilter(nullable: true, blank: true)
    uriFilter(nullable: true, blank: true)
    contextRule(nullable: true, blank: true)
    lastModified(nullable: true, blank: true)
  }
  static transients = ['imageDir', 'activePage']

  String getImageDir() {
    return (config.homepageRootDir + this?.id + '/')
  }

  static HomePage getActivePage() {
    HomePage homePage = HomePage.list().find {
      (it.activeFrom <= new Date()) && (!it.activeTo || (it.activeTo > new Date()))
    }
    return homePage
  }

}