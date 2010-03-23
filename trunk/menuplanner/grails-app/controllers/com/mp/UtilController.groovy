package com.mp
import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder

class UtilController {

    static config = ConfigurationHolder.config
    
    def index = {
        render config.imageRootDir
    }

    
}
