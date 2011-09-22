package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.ApplicationHolder

class ImageController {

    def grailsApplication
    def static config = ConfigurationHolder.config

    def image = {
        Image image
        if (params?.image){
          image = params.image
        } else if (params?.id) {
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
        String imagePath = params.imagePath
        String noImage = params.noImage
        if (imagePath) {
            imageFile = new File(imagePath)
            if (!imageFile.exists()) {
                imagePath = config.imagesRootDir + imagePath
                imageFile = new File(imagePath)
            }
        } else if (noImage) {
            imageFile = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/images/${noImage}"))
        }
        byte[] imageFileContent = getImageBytes(imageFile, imagePath)
        response.setContentLength(imageFileContent.size())
        response.setContentType("image/${imageFile.name.tokenize('.').tail().join('.')}")
        OutputStream out = response.getOutputStream()
        out.write(imageFileContent)
        out.flush()
        out.close()

    }

    public byte[] getImageBytes(File imageFile, String imagePath) {
        File file = getImageFileToRead(imageFile, imagePath)
        return file.readBytes()
    }

    public File getImageFileToRead(File imageFile, String imagePath) {
        if (imageFile.exists()) {
            return imageFile
        } else {
            int lastIndex1 = imagePath?.indexOf('_')
            int lastIndex2 = imagePath?.indexOf('.')
            if (lastIndex1 > 0) {
                String newPath1 = (imagePath?.substring(0, lastIndex1)) + "_640.jpg"
                imageFile = new File(newPath1)
            } else {
                String newPath1 = (imagePath?.substring(0, lastIndex2)) + "_640.jpg"
                imageFile = new File(newPath1)
            }
            if (imageFile.exists()) {
                return imageFile
            } else {
                int lastIndex = (lastIndex1 > 0) ? lastIndex1 : lastIndex2
                String newPath2 = (imagePath.substring(0, lastIndex)) + ".jpg"
                imageFile = new File(newPath2)
                return imageFile
            }
        }
    }

    def uploadImage = {
        String extension = params.Filename.tokenize('.').last()
        String path = Image.createTempImage(params.Filedata.bytes, extension)
        render path
    }

}
