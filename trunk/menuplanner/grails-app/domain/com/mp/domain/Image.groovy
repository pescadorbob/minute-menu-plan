package com.mp.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder as CH
import org.grails.plugins.imagetools.ImageTool
import static com.mp.MenuConstants.*

class Image {

    String storedName
    String actualName
    String path
    String extension
    String altText

    static transients = ['fileToBeRead']

    static constraints = {
//        storedName(unique: 'path')
    }

    public static String createTempImage(byte[] fileContents, String extension) {
        String filePath = CH.config.tempDir
        String fileName = 'Img_' + System.currentTimeMillis()?.toString() + '.' + extension
        File file = new File(filePath)
        file.mkdirs()
        File actualFile = new File(file, fileName)
        actualFile.withOutputStream {out -> out.write fileContents }
        return actualFile.absolutePath
    }

    public static String createImageFile(byte[] fileContents, String filePath, String fileName, List<Integer> imageSizes = null) {
        String completePath = CH.config.imagesRootDir + filePath
        File file = new File(completePath)
        file.mkdirs()
        generateResizedImages(fileContents, completePath, fileName, imageSizes)
        return filePath
    }

    private static void generateResizedImages(byte[] fileContents, String filePath, String fileName, List<Integer> imageSizes) {
        ImageTool imageTool = new ImageTool()
        imageTool.load(fileContents)
        int firstIndex = fileName.indexOf('.')
        String name = fileName.substring(0, firstIndex)
        imageSizes.each { Integer size ->
            String newFileCompletePath = filePath + name + "_${size}.jpg"
            if (size == 640) {
                imageTool.thumbnailSpecial(640, 480, IMAGE_INTER_POLATION_TYPE, IMAGE_RENDERING_TYPE)
            } else {
                imageTool.thumbnail(size)
            }
            imageTool.writeResult(newFileCompletePath, "JPEG")
        }
    }

    public File getFileToBeRead(String filePath, String size = null) {
        File actualFile
        int firstIndex = storedName.indexOf('.')
        if (size) {
            String newName = (storedName.substring(0, firstIndex)) + "_${size}.jpg"
            actualFile = new File(filePath + newName)
            if (!actualFile.exists()) {
                actualFile = new File(filePath + (storedName.substring(0, firstIndex) + "_640.jpg"))
            }
            if (!actualFile.exists()) {
                actualFile = new File(filePath + storedName)
            }
        } else {
            actualFile = new File(filePath + (storedName.substring(0, firstIndex) + "_640.jpg"))
            if (!actualFile.exists()) {
                actualFile = new File(filePath + storedName)
            }
        }
        return actualFile
    }

    public byte[] readFile(String size = null) {
        String filePath = CH.config.imagesRootDir + path
        File file = getFileToBeRead(filePath, size)
        return file.readBytes()
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
            String targetImagePath = CH.config.imagesRootDir + targetImageDirectory + fileName
            if (sourceImage.exists() && (imagePath != targetImagePath)) {
                imageOwner.deleteImage()
                Image.createImageFile(sourceImage.readBytes(), targetImageDirectory, fileName, imageSizes)
                imageOwner.image = new Image(imagePath, targetImageDirectory, imageOwner?.id?.toString(), imageOwner?.toString())
                imageOwner.image.s()
            }
        }
        return true
    }

    public static Image uploadHomePageImage(byte[] fileContents, String fileName) {
        String fileDir = CH.config.homepageRootDir
        String completePath = CH.config.imagesRootDir + fileDir
        if (fileContents?.size()) {
            File file = new File(completePath)
            file.mkdirs()
            File actualFile = new File(file, fileName)
            actualFile.withOutputStream {out -> out.write fileContents }
            if (actualFile.exists()) {
                Image image = new Image()
                image.storedName = fileName
                image.path = fileDir
                image.altText = fileName
                image.actualName = fileName
                image.extension = fileName?.tokenize('.')?.tail()?.join('.')
                image.save()
                return image
            }
        }
        return null
    }
}
