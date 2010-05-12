package com.mp.domain

import grails.converters.JSON

class MenuplannerTagLib {

    static namespace = 'mp'

    def checkGeneralInfoTabError = {attrs ->
        def bean = attrs['bean']
        def fieldsToCheck = attrs['fields']

        if (bean) {

            def fieldsHavingError = bean?.errors?.allErrors.collect {it.properties['field']}
            if (fieldsHavingError.any {it.toString() in fieldsToCheck}) {
                out << "color:red;"
            } else {
                out << ""
            }
        }

    }

    def recipeImage = {attrs ->
        String height= attrs['height']
        String width= attrs['width']
        String id = attrs['id']
        String clas = attrs['class']
        out << "<img class='${clas}' height='${height}' width='${width}' src='" + createLink(controller: 'image', action: 'image', id: id) + "'/>"
    }

    def getSelectedCategoriesAsJSON={attrs->
        if(attrs['prePopulated']) {
            List<Category> categories = Category.getAll([attrs['prePopulated']].flatten()*.toLong())
            List categoriesJson = categories.collect { [id: it.id, name: it.name] }
            println categoriesJson as JSON
            out<<categoriesJson
        }
    }

    def mealItems = {attrs ->
        String weekIndex = attrs['weekIndex']
        MealType mealType = attrs['type']
        Week week = attrs['week']
        String image = attrs['image']
        out << g.render(template: '/menuPlan/mealItems', model: [week: week, weekIndex: weekIndex, mealType: mealType, image: image])
    }

}
