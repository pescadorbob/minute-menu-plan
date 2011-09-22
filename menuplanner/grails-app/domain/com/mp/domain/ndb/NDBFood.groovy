package com.mp.domain.ndb
/**
 * Created on Apr 10, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */

public class NDBFood {
static searchable = [only: ['shrtDesc']]
  NDBFileInfo fileInfo
  String fileVersion
  String ndbNo
  String shrtDesc
  Float water
  Float energKcal
  Float protein
  Float lipidTot
  Float ash
  Float carbohydrt
  Float fiberTD
  Float sugarTot
  Float calcium
  Float iron
  Float magnesium
  Float phosphorus
  Float potassium
  Float sodium
  Float zinc
  Float copper
  Float manganese
  Float selenium
  Float vitC
  Float thiamin
  Float riboflavin
  Float niacin
  Float pantoAcid
  Float vitB6
  Float folateTot
  Float folicAcid
  Float foodFolate
  Float folateDFE
  Float cholineTot
  Float vitB12
  Float vitA_IU
  Float vitA_RAE
  Float retinol
  Float alphaCarot
  Float betaCarot
  Float betaCrypt
  Float lycopene
  Float lutZea
  Float vitE
  Float vitD_mcg
  Float vitD_IU
  Float vitK
  Float faSat
  Float faMono
  Float faPoly
  Float cholestrl
  Float gmWt1
  String gmWtDesc1
  Float gmWt2
  String gmWtDesc2
  Float refusePct
     static hasMany = [weights:NDBWeight]
  static constraints = {
     ndbNo(unique:'fileVersion')

     refusePct(nullable:true)
     water(nullable:true)
     energKcal(nullable:true)
     protein(nullable:true)
     lipidTot(nullable:true)
     ash(nullable:true)
     carbohydrt(nullable:true)
     fiberTD(nullable:true)
     sugarTot(nullable:true)
     calcium(nullable:true)
     iron(nullable:true)
     magnesium(nullable:true)
     phosphorus(nullable:true)
     potassium(nullable:true)
     sodium (nullable:true)
     zinc(nullable:true)
     copper (nullable:true)
     manganese(nullable:true)
     selenium (nullable:true)
     vitC    (nullable:true)
     thiamin (nullable:true)
     riboflavin(nullable:true)
     niacin    (nullable:true)
     pantoAcid (nullable:true)
     vitB6    (nullable:true)
     folateTot(nullable:true)
     folicAcid(nullable:true)
     foodFolate(nullable:true)
     folateDFE(nullable:true)
     cholineTot(nullable:true)
     vitB12  (nullable:true)
     vitA_IU(nullable:true)
     vitA_RAE (nullable:true)
     retinol  (nullable:true)
     alphaCarot(nullable:true)
     betaCarot (nullable:true)
     betaCrypt (nullable:true)
     lycopene (nullable:true)
     lutZea  (nullable:true)
     vitE   (nullable:true)
     vitD_mcg (nullable:true)
     vitD_IU (nullable:true)
     vitK   (nullable:true)
     faSat  (nullable:true)
     faMono (nullable:true)
     faPoly  (nullable:true)
     cholestrl(nullable:true)
     gmWt1  (nullable:true)
     gmWtDesc1 (nullable:true)
     gmWt2  (nullable:true)
     gmWtDesc2 (nullable:true)

  }

    static mapping = {
        ndbNo index:'NDB_No_Idx'
        shrtDesc index:'DESCRIPTION_Idx'
    }

}