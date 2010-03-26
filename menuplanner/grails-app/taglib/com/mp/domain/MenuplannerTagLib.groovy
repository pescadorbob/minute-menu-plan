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

}
