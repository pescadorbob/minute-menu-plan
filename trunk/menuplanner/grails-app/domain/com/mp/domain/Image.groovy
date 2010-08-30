package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.imagetools.ImageTool

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

    public static String createTempImage(byte[] fileContents, String extension) {
        String filePath = config.tempDir
        String fileName = 'Img_' + System.currentTimeMillis()?.toString() + '.' + extension
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, fileName)
        actualFile.withOutputStream {out -> out.write fileContents }
        return actualFile.absolutePath
    }

    public static String createImageFile(byte[] fileContents, String filePath, String fileName, List<Integer> imageSizes = null) {
        File file = new File(filePath)
        file.mkdirs()
        generateResizedImages(fileContents, filePath, fileName, imageSizes)
        return filePath
    }

    private static void generateResizedImages(byte[] fileContents, String filePath, String fileName, List<Integer> imageSizes) {
        ImageTool imageTool = new ImageTool()
        imageTool.load(fileContents)
        int firstIndex = fileName.indexOf('.')
        String name = fileName.substring(0, firstIndex)
        imageSizes.each { Integer size ->
            String newFileCompletePath = filePath + name + "_${size}.jpg"
            imageTool.thumbnail(size)
            imageTool.writeResult(newFileCompletePath, "JPEG")
        }
    }

    public byte[] readFile(String size = null) {
        String filePath = path
        File actualFile
        if (size) {
            int firstIndex = storedName.indexOf('.')
            String newName = (storedName.substring(0, firstIndex)) + "_${size}.jpg"
            actualFile = new File(filePath + newName)
            if (!actualFile.exists()) {
                actualFile = new File(filePath + storedName)
            }
        } else {
            actualFile = new File(filePath + storedName)
        }
        return actualFile.readBytes()
    }

    public Image() {}

    public Image(String recipeImagePath, String targetPath, String recipeId, String altText = "Some alt text") {
        File imageFile = new File(recipeImagePath)
        storedName = recipeId + '.' + imageFile.name.tokenize('.').tail().join('.')
        actualName = imageFile.name.tokenize('.').first()
        extension = imageFile.name.tokenize('.').tail().join('.')
        path = targetPath
        this.altText = altText
    }

    //ImageOwner could be a Recipe/Subscriber

    public static boolean updateOwnerImage(def imageOwner, String imagePath, List<Integer> imageSizes) {
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
                Image.createImageFile(sourceImage.readBytes(), targetImageDirectory, fileName, imageSizes)
                imageOwner.image = new Image(imagePath, targetImageDirectory, imageOwner?.id?.toString(), imageOwner?.toString())
                imageOwner.image.s()
            }
        }
        return true
    }
}
