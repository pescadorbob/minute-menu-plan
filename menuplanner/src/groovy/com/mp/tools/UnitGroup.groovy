package com.mp.tools

import com.mp.domain.Unit
import static com.mp.MenuConstants.*
import com.mp.MenuConstants

/**
 */

public class UnitGroup {

  def values = []
  int index
  public static UnitGroup getGroup(Unit unit) {
    def retVal = null
    MenuConstants.unitGroups.each {key,value->
      def units = value.split(',')
       def index = units.findIndexOf{it == unit.name}
      if(index>=0){
        retVal = new UnitGroup(values:units,index:index)        
      }
    }
    retVal
  }

  Unit down(Unit unit) {
    if(index>0){
      Unit.findByName(values[index-1])
    } else null
  }

  Unit up(Unit unit) {
    if(index<values.length()-1){
      Unit.findByName(values[index+1])
    } else null
  }
}