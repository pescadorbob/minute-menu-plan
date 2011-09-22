package com.mp.themes

import com.mp.domain.Permission
import com.mp.domain.themes.Theme
import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import groovy.text.Template

/**
 * Created on Nov 16, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Norkom, Inc.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Norkom, Inc.
 * All rights reserved.
 */

public class ThemeTagLib {
  def themeService
  GroovyPagesTemplateEngine groovyPagesTemplateEngine
  static namespace = "theme"

  def gadget = {attrs, body ->
    def gadgets = new ArrayList()
    def start = System.currentTimeMillis()
    Theme.withSession {
      def themes = Theme.list()?.findAll {theme ->
        if (log.isDebugEnabled()) {
//          log.debug """Theme filters:
//            action:${!theme.actionFilter || actionName ==~ theme.actionFilter}
//            uri:${(!theme.uriFilter || request.requestURI ==~ theme.uriFilter)}
//            controller:${!theme.controllerFilter || controllerName ==~ theme.controllerFilter}
//            activeFrom:${theme.activeFrom <= new Date()}
//            activeTo:${(!theme.activeTo || (theme.activeTo > new Date()))}"""
        }
        def retValue = ((!theme.uriFilter || request.requestURI ==~ theme.uriFilter)
                && (!theme.actionFilter || actionName ==~ theme.actionFilter)
                && (!theme.controllerFilter || controllerName ==~ theme.controllerFilter)
                && (theme.activeFrom <= new Date())
                && (!theme.activeTo || (theme.activeTo > new Date())))
        retValue
      }
      themes.each { current ->
        def foundGadgets = current?.pageElements?.findAll {
          def gadget = it
          if (log.isDebugEnabled()) {
//            log.debug """Gadget filters:
//              location:${gadget.location == attrs.location}
//              uri:${(!gadget.uriFilter || request.requestURI ==~ gadget.uriFilter)}
//              action:${!gadget.actionFilter || actionName ==~ gadget.actionFilter}
//              controller:${!gadget.controllerFilter || controllerName ==~ gadget.controllerFilter}
//              context:${(!attrs.contextRule || attrs.contextRule ==~ gadget.contextRule)}"""
          }
          def retVal = (it.location == attrs.location
                  && (!gadget.uriFilter || request.requestURI ==~ gadget.uriFilter)
                  && (!it.actionFilter || actionName ==~ it.actionFilter)
                  && (!it.controllerFilter || controllerName ==~ it.controllerFilter)
                  && (!attrs.contextRule || attrs.contextRule ==~ it.contextRule))
          retVal
        }?.sort {it.listOrder}
        gadgets.addAll(foundGadgets)
      }
    }
    gadgets?.each {
      if (it.isTemplate) {
        try {
          Template t = groovyPagesTemplateEngine.createTemplate(it.text, it.name);
          t.make().writeTo(out)
          if (log.isDebugEnabled()) {
            log.debug "Processed page element [${it.name}]: ${it.text}"
          }
        } catch (Exception e) {
          log.error("Couldn't process the template ${it.name}", e)
        }
      } else {
        out << it.text      
      }

    }
    def end = System.currentTimeMillis()
    println "Time to process Themes:${end-start}"
  }
}