package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder

class ImageController {

    def grailsApplication
    def static config = ConfigurationHolder.config

    def image = {
        Image image
        if (params?.id) {
            image = Image.get(params.id)
        }
        byte[] fileContent
        String extension = image?.extension
        if (image) {
            fileContent = image.readFile(params?.size)
        }
        else if (params.noImage) {
            File noImageFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/images/${params?.noImage}"))
            if (noImageFile.exists()) {
                fileContent = noImageFile.readBytes()
                extension = noImageFile?.name?.tokenize('.')?.tail()?.join('.')
            }
        }
        if (fileContent) {
            response.setContentLength(fileContent.size())
            response.setContentType("image/${extension}")
            OutputStream out = response.getOutputStream()
            out.write(fileContent)
            out.flush()
            out.close()
        }
    }
    def imageByPath = {
        File imageFile
        if (params.imagePath) {
            imageFile = new File(params.imagePath)
        } else if (params.noImage) {
            imageFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/images/${params.noImage}"))
        }
        if (imageFile.exists()) {
            byte[] fileContent = imageFile?.readBytes()
            response.setContentLength(fileContent.size())
            response.setContentType("image/${imageFile.name.tokenize('.').tail().join('.')}")
            OutputStream out = response.getOutputStream()
            out.write(fileContent)
            out.flush()
            out.close()
        }
    }

    def uploadImage = {
        String extension = params.Filename.tokenize('.').last()
        String path = Image.createTempImage(params.Filedata.bytes, extension)
        render path
    }

}
