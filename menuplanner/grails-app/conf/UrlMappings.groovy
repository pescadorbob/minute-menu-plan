class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
      "/"(controller: 'login', action: 'index')
	  "500"(view:'/error')
        "/rdf/$action"(controller:"sitemap")
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
