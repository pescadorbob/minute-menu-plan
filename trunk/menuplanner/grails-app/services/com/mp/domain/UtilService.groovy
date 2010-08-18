package com.mp.domain

import com.mp.IngredientItemVO

class UtilService {


    List<IngredientItemVO> parseIngredients(String rawIngredients){
        List list=rawIngredients.readLines()
        List<IngredientItemVO> ingredientItemVos = []

        IngredientItemVO ingredientItemVo
        list.each{
            ingredientItemVo = new IngredientItemVO()
            def matcher= it =~ /[-]+.+[:]*/
            ingredientItemVo.preparationMethod=matcher?matcher[0]?.replace("--","")?.replace(":","")?.trim():''

            println ""

            matcher= it =~ /[:].+/
            ingredientItemVo.aisle=matcher?matcher[0]?.replace(":","")?.trim():''

            matcher= it =~ /\d+[\s]*([\d]*[\/][\d])*/
            ingredientItemVo.amount=matcher?matcher[0][0]?.trim():''

            matcher= it =~ /[a-zA-Z]+/
            ingredientItemVo.measure=matcher?matcher[0]?.trim():''

            matcher= it =~ /[a-zA-z]+\s?(\s*[a-zA-Z]+.*[--]*)/
            ingredientItemVo.ingredient=matcher?matcher[0][1]?.trim()?.replace("--",""):''

            ingredientItemVos+=ingredientItemVo
        }

         return ingredientItemVos 
        
    }
}
