package com.mp.util

import java.util.regex.*
import org.codehaus.groovy.grails.commons.ApplicationHolder
/**
 * Created by IntelliJ IDEA.
 * User: amit
 * Date: 1 Oct, 2009
 * Time: 12:50:48 PM
 * To change this template use File | Settings | File Templates.
 */

public class CommonUtil {

  public static boolean updateProperty(Map params, String updateProperty, String property) {
    if (params."${property}")
      params."${updateProperty}" = params."${property}"
    else if (params."${updateProperty}" == "Other")
      return false

    return true
  }

  public static String initCap(String str) {
    return str ? str[0].toUpperCase() + str.substring(1) : str
  }

  public static String maskAccountNumber(String str) {
    int lastCharsToBeShown = 4
    int startingIndex = str.length() - lastCharsToBeShown
    String mask = ""
    (1..startingIndex).each {mask += "X"}
    String maskedString = mask + str.substring((startingIndex > 0) ? startingIndex : 0, str.length())
    return maskedString
  }

  public static int findUpperIndex(int offset, int max, int total) {
    max = offset + max - 1
    if (max >= total) {
      max -= max - total + 1
    }
    return max
  }

  public static String maskIntialNumbers(String str, int lastCharsToBeShown = 4, String maskChar = "x") {
    str = str.trim()
    int len = str.length()
    int startingIndex = len - lastCharsToBeShown
    if (startingIndex > 0)
      return maskChar + str.substring(len - lastCharsToBeShown - 1, len)
    return str
  }

  /** This functions remove the charaters passed as a list from the given string
   * @param removeFromStr
   * @param charters List of characters
   * @return string string without the list of characters found
   */
  public static String removeSpecialCharacters(String removeFromStr, List characters) {
    String chars = characters.join()
    return removeFromStr?.replaceAll(/[$chars]/, "")
  }

  /** This function truncates and pads the string as per the given parameters
   * @return truncated string with padding
   */
  public static String truncPadAndToUpperCase(String str, int number, String padChar = " ", boolean padRight = true, boolean truncFromRight = true) {
    if (str.size() > number) {
      if (truncFromRight)
        str = str.substring(0, number)
      else
        str = str.substring(str.size() - number, str.size())
    }

    if (padRight)
      str = str.padRight(number, padChar)
    else
      str = str.padLeft(number, padChar)

    return str.toUpperCase()
  }


  public static BigDecimal getRoundedAmount(BigDecimal amount) {
    return amount.setScale(2, java.math.RoundingMode.HALF_EVEN)
  }

  public static String getPathOf(String folder) {
    File layoutFolder = ApplicationHolder.application.parentContext.getResource(folder).file
    String absolutePath = layoutFolder.absolutePath
    return absolutePath
  }
  /**
   * This path is used to locate jrxml file, while printing pdf reports
   */
  public static String getReportsPath() {
    String path = getPathOf("reports")
    path += File.separator
    return path
  }

  public static List getPaginatedList(List records, int max, int offset) {
    if (records) {
      int total = records.size()
      int limit = findUpperIndex(offset, max, total)
      if (offset > total)
        return []
      records = records.getAt(offset..limit)
    }
    return records
  }
}