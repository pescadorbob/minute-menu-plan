package com.mp.tools

import com.mp.domain.Unit
import static com.mp.MenuConstants.*
import com.mp.MenuConstants

/**
 */

public class UnitGroup {

// if the unit were Gram, then values = ['Milligram','Gram','Kilogram']
  def values = []
  // if the unit were 'Gram' index = 1
  int index

  public static UnitGroup getGroup(Unit unit) {
    def retVal = null

    MenuConstants.unitGroups.each {key, value ->
//     'md':'Milligram,Gram,Kilogram',
//     'dz':'Each:Small:Medium:Large,Dozen'
      def units = value.split(',')
      units.eachWithIndex {unitNameList,index ->
        def possibleUnits = unitNameList.split(':')
        possibleUnits.each {unitName ->
          if (unitName == unit.name) {
            retVal = new UnitGroup(values: units, index: index)
          }
        }

      }
    }
//    if(!retVal){
//        throw new RuntimeException('''An unexpected unrecoverable error occurred why determining the Unit Group,
//(used for determining weight).  No matching weight group was found for the unit ${unit.name}''')
//    }
    retVal
  }

  Unit down(Unit unit) {
    if (index > 0) {
      Unit.findByName(values[index - 1])
    } else null
  }

  Unit up(Unit unit) {
    if (index < values.length() - 1) {
      Unit.findByName(values[index + 1])
    } else null
  }
}