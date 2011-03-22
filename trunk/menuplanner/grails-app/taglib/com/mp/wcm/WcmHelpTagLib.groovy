package com.mp.wcm

import org.weceem.html.WcmHTMLContent

public class WcmHelpTagLib {
    static namespace = "wcmhelp"
    def wcmContentRepositoryService

    def eachPage = { attrs, body ->
        def var = attrs.var ? attrs.var : "content"
        def space = wcmContentRepositoryService.findDefaultSpace()
        if (space) {
            def pages = wcmContentRepositoryService.findAllContent(wcmContentRepositoryService.findDefaultSpace())
            pages.each {
                if (it instanceof WcmHTMLContent && it.status.getPublicContent()) {
                    out << body((var): it)
                }
//     out << """${ConfigurationHolder.config.weceem.content.prefix}${content.absoluteURI}">${content.title}</a>"""

            }
        }
    }
}
