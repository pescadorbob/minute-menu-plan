package menuplan

import grails.test.GrailsUnitTestCase
import com.mp.domain.UserService

/**
 * Created on Nov 11, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Norkom, Inc.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Norkom, Inc.
 * All rights reserved.
 */
 
public class PasswordTests extends GrailsUnitTestCase {
  void testSomething() {
    String password = '1234'

     assertEquals "433534==", UserService.encode(password)
  }

}