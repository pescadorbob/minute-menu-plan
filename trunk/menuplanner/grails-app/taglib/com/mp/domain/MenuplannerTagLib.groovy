package com.mp.domain

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
            out << "<img id='recipeImage'  border='0' width='195' src=" + '"' + "${g.createLink(controller: 'recipe', action: 'showImage', id: attrs['id'])} " + '"' + "/>"
        }
    }

    def recipeImageByPath = {attrs ->
        if (attrs['selectRecipeImagePath']) {
            out << "<img id='recipeImage'  border='0' width='195' src=" + '"' + "${g.createLink(controller: 'recipe', action: 'showImage', params:[selectRecipeImagePath: attrs['selectRecipeImagePath']])} " + '"' + "/>"
        }
    }
}
