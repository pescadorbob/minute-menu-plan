package com.mp.sitemap

import com.mp.domain.Recipe
import org.weceem.html.WcmHTMLContent
import com.mp.domain.party.Party
import com.mp.MenuConstants
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class SitemapController {

    def wcmContentRepositoryService

    def sitemap = {
        render(contentType: 'text/xml', encoding: 'UTF-8') {
            mkp.yieldUnescaped '<?xml version="1.0" encoding="UTF-8"?>'
            urlset(xmlns: "http://www.sitemaps.org/schemas/sitemap/0.9",
                    'xmlns:xsi': "http://www.w3.org/2001/XMLSchema-instance",
                    'xsi:schemaLocation': "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd") {
                url {
                    loc(g.createLink(absolute: true))
                    changefreq('hourly')
                    priority(1.0)
                }

                def space = wcmContentRepositoryService.findDefaultSpace()
                if (space) {
                    def pages = wcmContentRepositoryService.findAllContent(wcmContentRepositoryService.findDefaultSpace())
                    pages.each { page ->
                        if (page instanceof WcmHTMLContent && page.status.getPublicContent()) {
                            url {
                                loc(g.createLink(action:page.absoluteURI, absolute: true, controller: ConfigurationHolder.config.weceem.content.prefix ))
//                                loc("${ConfigurationHolder.config.grails.serverURL}/${ConfigurationHolder.config.weceem.content.prefix}/${it.absoluteURI}")
                                changefreq('hourly')
                                priority(0.8)
                            }
                        }
//     out << """${ConfigurationHolder.config.weceem.content.prefix}${content.absoluteURI}">${content.title}</a>"""

                    }
                }
                Recipe.list().sort{it.id }.each { recipe ->
                    url {
                        loc(g.createLink(absolute: true, controller: 'recipe', action: 'show', id: recipe.id))
                        changefreq('hourly')
                        priority(1.0)
                    }
                }
                Party.list().sort{it.id }.each { user ->
                    url {
                        loc(g.createLink(absolute: true, controller: 'user', action: 'show', id: user.id))
                        changefreq('monthly')
                        priority(0.8)
                    }
                }
            }
        }

    }
}
