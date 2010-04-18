package com.mp.domain

import grails.converters.JSON

class MenuplannerTagLib {

    static namespace = 'mp'

    def tagInput = {attrs ->
        Map model = [:]
        attrs.id = attrs.id ?: attrs.name
        model.id = attrs.id
        model.name = attrs.name
        model.controller = attrs.controller
        model.action = attrs.action
        model.htmlClass = attrs.class ?: " "
        model.prePopulated=attrs.prePopulated
        println "<<<<<<<<<<<<<<<<<< ${model.prePopulated as JSON}"
        if (attrs.multiselect == "false") {
            model.tokenLimit = 1
        }
        out << g.render(template: '/tagInput/tagInput', model: model)
    }

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

    def recipeImageById = {attrs ->
        if (attrs['id']) {
            out << "${g.createLink(controller: 'recipe', action: 'showImage', id: attrs['id'])} "
        }
    }

    def recipeImageByPath = {attrs ->
        if (attrs['selectRecipeImagePath']) {
            out << "<img id='recipeImage'  border='0' width='195' src=" + '"' + "${g.createLink(controller: 'recipe', action: 'showImage', params:[selectRecipeImagePath: attrs['selectRecipeImagePath']])} " + '"' + "/>"
        }
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
        MealType mealType = attrs['type']
        Week week = attrs['week']
        String image = attrs['image']
        out << g.render(template: '/calendar/mealItems', model: [week: week, mealType: mealType, image: image])
    }

}
