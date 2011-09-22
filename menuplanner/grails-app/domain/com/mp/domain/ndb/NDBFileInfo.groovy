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
 
public class NDBFileInfo {
   String fileVersion
  Date importedDate
  Date frum
  Date thru
   static constraints = {
        thru(nullable: true)
     fileVersion(unique: true)
    }
}