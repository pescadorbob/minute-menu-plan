package menuplan

import grails.test.GrailsUnitTestCase
import com.mp.domain.UserService

/**
 * Created on Nov 11, 2010
 * Created by Brent Fisher
 *
 */
 
public class PasswordTests extends GrailsUnitTestCase {
  void testSomething() {
    String password = '1234'

     assertEquals "433534==", UserService.encode(password)
  }

}