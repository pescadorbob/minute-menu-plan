package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Image {

    static config = ConfigurationHolder.config

    String storedName
    String actualName
    String path
    String extension
    String altText


    static constraints = {
        name(unique: path)
    }

    public static Image createFile(Long id, String path, String fileName, byte[] fileContents,String altText) {
        String filePath = config.imagesRootDir  + path
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, id.toString())
        actualFile.withOutputStream {out ->
            out.write fileContents
        }
        Image image = Image.findByStoredNameAndPath(id.toString(), filePath)
        if(!image){image = new Image()}
        image.storedName = id.toString()
        image.actualName = fileName.tokenize('.').first()
        image.extension = fileName.tokenize('.').tail().join('.')
        image.path = filePath
        image.altText = (altText)? altText: image.actualName
        image.s()
        return image
    }

    public readFile() {
        String filePath = config.imagesRootDir + path
        File file = new File(filePath)
        File actualFile = new File(file, storedName)
        return actualFile.readBytes()
    }
}
