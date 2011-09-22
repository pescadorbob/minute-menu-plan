package com.mp.ndb
/**
 * Created on Apr 11, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */
 
public class NDBFoodFileColumnMappingTest extends GroovyTestCase
{
  public void testFoodMapping(){
    assert NDBFoodFileColumnMapping.foodColumnMapping().size() ==53
    assert NDBFoodFileColumnMapping.foodColumnMapping()['0'] == 'ndbNo'
    assert NDBFoodFileColumnMapping.foodColumnMapping()['1'] == 'shrtDesc'

    assert NDBFoodFileColumnMapping.weightColumnMapping().size() == 7
    assert NDBFoodFileColumnMapping.weightColumnMapping()['0'] == 'ndbNo'
    assert NDBFoodFileColumnMapping.weightColumnMapping()['1'] == 'seq'

  }

}