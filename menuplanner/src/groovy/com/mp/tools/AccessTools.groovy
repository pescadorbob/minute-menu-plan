package com.mp.tools

import com.mp.domain.access.AccessFilter
import com.mp.domain.access.AccessFilterType
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.Cookie
import grails.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * Created on Dec 4, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Access-Wealth, LLC
 * All rights reserved.
 */

public class AccessTools {
  static boolean isUnrestrictedAccess(String controller, String action, String uri, HttpServletResponse response,
                                      HttpServletRequest request, Cookie guestVisitor) {
    Date now = new Date()
    def unrestrictedAccessFilters = AccessFilter.withCriteria {
      filterFor {
        eq('type', AccessFilterType.UNRESTRICTED_ACCESS)
        lt('activeFrom', now)
        or {
          isNull('activeTo')
          lt('activeTo', now)
        }
      }
    }
    if (GrailsUtil.environment != GrailsApplication.ENV_PRODUCTION) {
        println("Access Log:${new Date()}:uri[${uri}] controller:[${controller}] action[${action}]");
    }
    boolean isUnrestrictedAccess = (unrestrictedAccessFilters && unrestrictedAccessFilters.findAll {
      (!it.controllerFilter || controller ==~ it.controllerFilter) &&
              (!it.actionFilter || action ==~ it.actionFilter) &&
              (!it.uriFilter || uri ==~ it.uriFilter)
    }.size() > 0)
//    return true
    return isUnrestrictedAccess
  }

}