coverage {
	exclusions = ['**/plugins/**', '**/functionaltestplugin/**', '**/org/grails/**', '**/org/*/grails/**', '**/*Filters*/**', '**/*TagLib*/**', '**/*Config*/**', '**/*Searchable*/**', '**/*JQueryService*/**', '**/*asynchronousmail*/**']
}
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.plugin.location.'tiny-mce'="custom-plugins/tiny-mce-0.1"
grails.plugin.location.'image-tools'="custom-plugins/image-tools-1.0.4"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {        
        grailsPlugins()
        grailsHome()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.5'
    }


}
grails.war.resources = { stagingDir, args ->
    copy file: "grails-app/migrations/changelog.xml", tofile: "${stagingDir}/migrations/changelog.xml", overwrite: true
}
