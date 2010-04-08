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
        storedName(unique: 'path')
    }

    public static Image createFile(Long id, String relativePath, String fileName, byte[] fileContents,String altText) {
        String filePath = config.imagesRootDir  + relativePath
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
        String filePath = path
        File file = new File(filePath)
        File actualFile = new File(file, storedName)
        return actualFile.readBytes()
    }

    public Image(){}

    public Image(String selectRecipeImagePath, String altText="Some alt text"){
        File file=new File(selectRecipeImagePath)
        String filePath = config.imagesRootDir  + "/recipes"
        String fileName=file.name
        storedName = fileName
        actualName = fileName.tokenize('.').first()
        extension = fileName.tokenize('.').tail().join('.')
        path = filePath
        this.altText =altText
    }
}
