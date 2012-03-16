package com.mp.tools
/**
 * User: Brent Fisher
 * Date: Mar 9, 2012
 * Time: 8:46:35 PM
 */

public class CurrencyUtils {
  public static BigDecimal getRoundedAmount(BigDecimal amount,places = 2) {
      return amount.setScale(places, java.math.RoundingMode.HALF_EVEN)
    }

}
