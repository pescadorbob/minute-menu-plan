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
//        storedName(unique: 'path')
    }

    public static String createTempImage(byte[] fileContents, String extension){
        String filePath = config.tempDir
        String fileName = 'Img_' + System.currentTimeMillis()?.toString() + '.' + extension
        String path = createImageFile(fileContents, filePath, fileName).absolutePath as String
        return path
    }

    public static File createImageFile(byte[] fileContents, String filePath, String fileName){
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, fileName)
        actualFile.withOutputStream {out -> out.write fileContents }
        return actualFile
    }

    public static Image createFile(Long id, String relativePath, String fileName, byte[] fileContents, String altText) {
        String filePath = config.imagesRootDir + relativePath
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, id.toString())
        actualFile.withOutputStream {out ->
            out.write fileContents
        }
        Image image = Image.findByStoredNameAndPath(id.toString(), filePath)
        if (!image) {image = new Image()}
        image.storedName = id.toString()
        image.actualName = fileName.tokenize('.').first()
        image.extension = fileName.tokenize('.').tail().join('.')
        image.path = filePath
        image.altText = (altText) ? altText : image.actualName
        image.s()
        return image
    }

    public byte[] readFile() {
        String filePath = path
        File file = new File(filePath)
        File actualFile = new File(file, storedName)
        return actualFile.readBytes()
    }

    public Image() {}

    public Image(String recipeImagePath, String targetPath, String recipeId, String altText = "Some alt text") {
        File imageFile = new File(recipeImagePath)
        storedName = recipeId + '.' +imageFile.name.tokenize('.').tail().join('.')
        actualName = imageFile.name.tokenize('.').first()
        extension = imageFile.name.tokenize('.').tail().join('.')
        path = targetPath
        this.altText = altText
    }

    //ImageOwner could be a Recipe/Subscriber
    public static boolean updateOwnerImage(def imageOwner, String imagePath){
        if (!imagePath) {
            imageOwner.deleteImage()
            return false
        } else {
            File sourceImage = new File(imagePath)
            String targetImageDirectory = imageOwner.imageDir
            String extension = sourceImage.name.tokenize('.').tail().join('.')
            String fileName = imageOwner?.id + '.' + extension
            String targetImagePath = targetImageDirectory + fileName
            if (sourceImage.exists() && (imagePath != targetImagePath)) {
                imageOwner.deleteImage()
                Image.createImageFile(sourceImage.readBytes(), targetImageDirectory, fileName)
                imageOwner.image = new Image(imagePath, targetImageDirectory, imageOwner?.id?.toString(), imageOwner?.toString())
                imageOwner.image.s()
            }
        }
        return true
    }
}
