class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
      "/"(controller: 'login', action: 'index')
	  "500"(view:'/error')
        "/urllist.txt"(controller:"sitemap",action:'urllist')
        "/rdf/recipes.xml"(controller:"sitemap",action:'recipes')
        "/rdf/people.xml"(controller:"sitemap",action:'people')
        "/rdf/menuPlans.xml"(controller:"sitemap",action:'menuPlans')
        "/rdf/articles.xml"(controller:"sitemap",action:'articles')
        "/rdf/recipes"(controller:"sitemap",action:'recipes')
        "/rdf/people"(controller:"sitemap",action:'people')
        "/rdf/menuPlans"(controller:"sitemap",action:'menuPlans')
        "/rdf/articles"(controller:"sitemap",action:'articles')
        "/ror.xml" {
            controller = 'sitemap'
            action = 'ror'
        }
        "/sitemap.xml"{
            controller = 'sitemap'
            action = 'sitemap'
        }
	}
}
