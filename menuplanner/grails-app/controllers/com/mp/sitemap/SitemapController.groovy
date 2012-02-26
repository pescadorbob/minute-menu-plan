package com.mp.sitemap

import com.mp.domain.Recipe
import org.weceem.html.WcmHTMLContent
import com.mp.domain.party.Party
import com.mp.MenuConstants
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.mp.domain.MenuPlan

class SitemapController {

    def wcmContentRepositoryService

    def ror = {
        render(contentType: 'text/xml', encoding: 'UTF-8') {
            mkp.yieldUnescaped '<?xml version="1.0" encoding="UTF-8"?>'
            rdf("RDF") {
                Resource {
                    type('Main')
                    title('Minute Menu Plan')
                    url("http://www.minutemenuplan.com")
                    desc("Organize your meals, grocery shopping, budget and browse thousands of recipes")
                    keywords("grocery list maker, meal planning website, plan to eat, monthly meal planner, organized living")
                    image("http://www.minutemenuplan.com/images/logo__v914.gif")
                    updated("${new Date().toGMTString()}")
                    updatePeriod("day")
                }
                Resource {
                    type("Recipe")
                    seeAlso {
                        rdf(resource: "${g.createLink(uri: "/rdf/recipes")}")
                    }
                }
                Resource {
                    type("People")
                    seeAlso {
                        rdf(resource: "${g.createLink(uri: "/rdf/people")}")
                    }
                }
                Resource {
                    type("Menu Plans")
                    seeAlso {
                        rdf(resource: "${g.createLink(uri: "/rdf/menuPlans")}")
                    }
                }
                Resource {
                    type("Articles")
                    seeAlso {
                        rdf(resource: "${g.createLink(uri: "/rdf/articles")}")
                    }
                }
            }
        }

    }
    def menuPlans = {
        render(contentType: 'text/xml', encoding: 'UTF-8') {
            mkp.yieldUnescaped '<?xml version="1.0" encoding="UTF-8"?>'
            rdf("RDF") {
                MenuPlan.list().sort {it.id }.each { plan ->
                    Resource {
                        type('Menu Plan')
                        title(plan.name)
                        url(g.createLink(absolute: true, controller: 'menuPlan', action: 'show', id: plan.id))
                    }
                }
            }
        }
    }
    def people = {
        render(contentType: 'text/xml', encoding: 'UTF-8') {
            mkp.yieldUnescaped '<?xml version="1.0" encoding="UTF-8"?>'
            rdf("RDF") {
                Party.list().sort {it.id }.each { user ->
                    Resource {
                        type('Person')
                        title(user.name)
                        url(g.createLink(absolute: true, controller: 'user', action: 'show', id: user.id))
                      imageURL(g.createLink(absolute: true,controller: 'image', action: 'image', id: user.subscriber?.image?.id))
                    }
                }
            }
        }
    }
    def recipes = {
        render(contentType: 'text/xml', encoding: 'UTF-8') {
            mkp.yieldUnescaped '<?xml version="1.0" encoding="UTF-8"?>'
            rdf("RDF") {
                Recipe.list().sort {it.id }.each { recipe ->
                    Resource {
                        type('Recipe')
                        title(recipe.name)
                        url(g.createLink(absolute: true, controller: 'recipe', action: 'show', id: recipe.id))
                        desc(recipe.description)
                        created(((Date)recipe.dateCreated).toGMTString())
                        recipe.ingredients.each { ing ->
                            ingredient(ing.ingredient.name)
                        }
                        directions(recipe.directions)

                        cookingTime(recipe.cookingTime.toReadableTimeString())
                        prepTime(recipe.preparationTime.toReadableTimeString())
                        totalTime(recipe.totalTime.toReadableTimeString())
                        contributor(recipe.contributor?.name)
                        servings(recipe.servings)
                        serveWith(recipe.serveWithString)
                      imageUrl(recipe.imagePath)
                    }
                }
            }
        }
    }
    def articles = {
        render(contentType: 'text/xml', encoding: 'UTF-8') {
            mkp.yieldUnescaped '<?xml version="1.0" encoding="UTF-8"?>'
            rdf("RDF") {

                def space = wcmContentRepositoryService.findDefaultSpace()
                if (space) {
                    def pages = wcmContentRepositoryService.findAllContent(wcmContentRepositoryService.findDefaultSpace())
                    pages.each { page ->
                        if (page instanceof WcmHTMLContent && page.status.getPublicContent()) {
                            Resource {
                                type('Article')
                                title(page.title)
                                url(g.createLink(action: page.absoluteURI, absolute: true, controller: ConfigurationHolder.config.weceem.content.prefix))
                                desc("Organize your meals, grocery shopping, budget and browse thousands of recipes")
                                keywords(page.keywords)
                                updated(((java.sql.Timestamp)page.changedOn).toGMTString())
                                updatePeriod("weekly")
                            }
                        }
                    }
                }
            }
        }
    }
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
                                loc(g.createLink(action: page.absoluteURI, absolute: true, controller: ConfigurationHolder.config.weceem.content.prefix))
//                                loc("${ConfigurationHolder.config.grails.serverURL}/${ConfigurationHolder.config.weceem.content.prefix}/${it.absoluteURI}")
                                changefreq('hourly')
                                priority(0.8)
                            }
                        }
//     out << """${ConfigurationHolder.config.weceem.content.prefix}${content.absoluteURI}">${content.title}</a>"""

                    }
                }
                Recipe.list().sort {it.id }.each { recipe ->
                    url {
                        loc(g.createLink(absolute: true, controller: 'recipe', action: 'show', id: recipe.id))
                        changefreq('hourly')
                        priority(1.0)
                    }
                }
                Party.list().sort {it.id }.each { user ->
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
