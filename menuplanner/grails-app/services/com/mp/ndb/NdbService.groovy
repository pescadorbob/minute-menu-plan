package com.mp.ndb

import com.mp.domain.RecipeIngredient
import com.mp.domain.ndb.NDBFileInfo
import com.mp.domain.ndb.NDBFood
import com.mp.domain.ndb.NDBWeight
import com.mp.ndb.NDBFoodFileColumnMapping
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin
import org.compass.gps.device.hibernate.HibernateGpsDeviceException
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

/**
 * Created on Apr 10, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */

public class NdbService implements ApplicationContextAware {

  boolean transactional = false
  def sessionFactory
  def propertyInstanceMap = org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin.PROPERTY_INSTANCE_MAP
  static config = ConfigurationHolder.config
  def messageSource
  def applicationContext

  def cleanUpGorm() {

    try {
      def session = sessionFactory.currentSession
      session.flush()
      session.clear()
      propertyInstanceMap.get().clear()
    } catch (HibernateGpsDeviceException e){
       println "Error clearing session, but ignoring: $e"
    }
  }

  def getIngredients(filter){
    def c = RecipeIngredient.createCriteria()
    c.list {
      ingredient {
        like("name","%flour%")
        order("name")
      }
    }
//    c.list {
//        like("name", "%${filter.name}%")
//        maxResults(10)
//        order("name")
//    }

  }
  def getNDBFoodList(filter){
    def params = [min:filter.min,max:filter.max]
    NDBFood.list(params)
  }

  /**
   *  imports food file, returns number of food data items imported.
   */
  def importFoodData(foodFile, version, importedDate, frum) {
    NDBFileInfo fileInfo = new NDBFileInfo(fileVersion: version, importedDate: importedDate, frum: frum)
    fileInfo.save()
    def foodColumnMapping = NDBFoodFileColumnMapping.foodColumnMapping()
    long startTime = System.currentTimeMillis()
    def foods = []
    def total = 0
    foodFile.eachLine {line, index ->
      def columns = line.split("\\^")
      NDBFood food = new NDBFood(fileVersion:version)
      food.fileInfo = fileInfo
      columns.eachWithIndex {col, colIndex ->
        if (col.length() == 0) {
          food."${foodColumnMapping[colIndex.toString()]}" = null
        } else if (col.indexOf('~') > -1) {
          col = col.substring(col.indexOf('~') + 1, col.lastIndexOf('~'))
          food."${foodColumnMapping[colIndex.toString()]}" = col
        } else {
          food."${foodColumnMapping[colIndex.toString()]}" = col.toFloat()
        }
      }
      while(food.id == null){
        try {
          food.save()
        } catch (Exception e){
          def tempFood = food.clone()
          def session = sessionFactory.currentSession
          if(food.id) session.delete(food)
          println "Error Saving $food.shrtDesc $e"
          food = tempFood
          sleep 1
        }
      }
      if (index % 1000 == 0) {
        cleanUpGorm()
        System.println("${index} Food ${food.ndbNo}:${food.shrtDesc} has ${food.faSat}g sat fat. in 100g.  ${food.gmWtDesc1} weighs ${food.gmWt1} grams   [${System.currentTimeMillis() - startTime}] ms")
        startTime = System.currentTimeMillis()
      }
      total = index
    }
    cleanUpGorm()
    System.println("${total} [${System.currentTimeMillis() - startTime}] ms")
    total

  }

  def importWeight(weightFile, version, importedDate, frum) {
    NDBFileInfo fileInfo = NDBFileInfo.findByFileVersion(version)
    assert fileInfo
    def weightColumnMapping = NDBFoodFileColumnMapping.weightColumnMapping()
    long startTime = System.currentTimeMillis()
    def total = 0
    weightFile.eachLine {line, index ->
      def columns = line.split("\\^")
      NDBWeight weight = new NDBWeight()
      columns.eachWithIndex {col, colIndex ->
        if (colIndex == 0) {
          def ndbNo = col.substring(col.indexOf('~') + 1, col.lastIndexOf('~'))
          NDBFood food = NDBFood.findByNdbNo(ndbNo)
          weight.weightFor = food
        } else if (col.length() == 0) {
          weight."${weightColumnMapping[colIndex.toString()]}" = null
        } else if (col.indexOf('~') > -1) {
          col = col.substring(col.indexOf('~') + 1, col.lastIndexOf('~'))
          weight."${weightColumnMapping[colIndex.toString()]}" = col
        } else {
          weight."${weightColumnMapping[colIndex.toString()]}" = new Float(col)
        }
      }
      while(weight.id == null){
        try {
          weight.save()
        } catch (Exception e){
          println "Error Saving $weight.msreDesc $e"
          sleep 1
        }
      }
      if (index % 1000 == 0) {
        cleanUpGorm()
        System.println("inserted last ${index} weights ${weight?.weightFor?.ndbNo}:${weight?.weightFor?.shrtDesc} weighs ${weight?.gmWgt} grams for ${weight?.amount} ${weight?.msreDesc} in ${System.currentTimeMillis() - startTime} ms")
        startTime = System.currentTimeMillis()
      }
      total = index

    }
    cleanUpGorm()
    System.println("${total} [${System.currentTimeMillis() - startTime}] ms")

  }


  void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    ConfigurationHolder.config.applicationContext = applicationContext;
  }

}