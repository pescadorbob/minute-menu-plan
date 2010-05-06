package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder

class ImageController {

def grailsApplication
    def static config = ConfigurationHolder.config

    def image = {
        Image image
        if(params.id){
            image = Image.get(params.id)
        }
        byte[] fileContent
        String extension = image?.extension
        if (image) {
            fileContent = image.readFile()
        } else{
            File noImageFile =new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/images/no-img.gif"))
            fileContent = noImageFile.readBytes()
            extension = 'gif'
        }
        response.setContentLength(fileContent.size())
        response.setContentType("image/${extension}")
        OutputStream out = response.getOutputStream()
        out.write(fileContent)
        out.flush()
        out.close()
    }
}
