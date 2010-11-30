package com.mp.tools

import com.mp.domain.themes.HomePage
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created on Nov 29, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of ${COMPANY}
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 ${COMPANY}* All rights reserved.
 */

public class ThemeTools {
  static config = ConfigurationHolder.config

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
