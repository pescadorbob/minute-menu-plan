package com.mp.domain

import grails.test.*

class ImageTests extends GrailsUnitTestCase {

    static final String folder1Path = '/tmp/mpUnitTests/1/'
    static final String folder2Path = '/tmp/mpUnitTests/2/'

    String image1 = "1.jpg"
    String image2 = "2.jpg"
    String image2_100 = "2_100.jpg"
    String image2_200 = "2_200.jpg"
    String image2_1080 = "2_1080.jpg"

    Image imageObject1 = getImageObjectForFolder(image1, folder1Path)


    protected void setUp() {
        //Reading a file from bootstrap data
        // creating first directory and writing file on it
        String bootStrapDirectory = "/web-app/bootstrapData/recipeImages/"
        String fileName = 'Hootenany.jpg'
        String appDir = System.properties['base.dir']
        File imageFile = new File(appDir + bootStrapDirectory + fileName)
        byte[] fileContents = imageFile.readBytes()

        File file1 = new File(folder1Path)
        file1.mkdirs()
        File actualFile = new File(file1, image1)
        actualFile.withOutputStream {out -> out.write fileContents }

        // creating second directory and writing files on it
        File file2 = new File(folder2Path)
        file2.mkdirs()

        File actualFile1 = new File(file2, image2_100)
        actualFile1.withOutputStream {out -> out.write fileContents }

        fileName = 'French Fry Burger Pie.jpg'
        File imageFile2 = new File(appDir + bootStrapDirectory + fileName)
        byte[] fileContents2 = imageFile2.readBytes()

        File actualFile2 = new File(file2, image2_200)
        actualFile2.withOutputStream {out -> out.write fileContents2 }

        fileName = 'Swedish Bread.jpg'
        File imageFile3 = new File(appDir + bootStrapDirectory + fileName)
        byte[] fileContents3 = imageFile3.readBytes()

        File actualFile3 = new File(file2, image2_1080)
        actualFile3.withOutputStream {out -> out.write fileContents3 }
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    /* These test-cases tests the readFile method.
       The folder "one" contains a file 1.jpg
       The folder "two" contains three files 2_100.jpg, 2_200.jpg, 2_1080.jpg of different sizes.
    */


    void test_readFile_Folder1_Without_Size() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)
        String fileName = imageObject1.getFileToBeRead().name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder1_With_File_Size_100() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)
        String fileName = imageObject1.getFileToBeRead('100').name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder1_With_File_Size_200() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)
        String fileName = imageObject1.getFileToBeRead('200').name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder1_With_File_Size_1080() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)
        String fileName = imageObject1.getFileToBeRead('1080').name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder2_Without_File_Size() {
        String actualFileName = getActualFileNameForFolder(image2_1080, folder2Path)
        Image image = getImageObjectForFolder(image2_1080, folder2Path)
        String fileName = image.getFileToBeRead().name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder2_With_File_Size_100() {
        String actualFileName = getActualFileNameForFolder(image2_100, folder2Path)
        Image image = getImageObjectForFolder(image2_100, folder2Path)
        String fileName = image.getFileToBeRead('100').name
        assertEquals actualFileName, fileName
    }


    void test_readFile_Folder2_With_File_Size_200() {
        String actualFileName = getActualFileNameForFolder(image2_200, folder2Path)
        Image image = getImageObjectForFolder(image2_200, folder2Path)
        String fileName = image.getFileToBeRead('200').name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder2_With_File_Size_1080() {
        String actualFileName = getActualFileNameForFolder(image2_1080, folder2Path)
        Image image = getImageObjectForFolder(image2_1080, folder2Path)
        String fileName = image.getFileToBeRead('1080').name
        assertEquals actualFileName, fileName
    }

    public Image getImageObjectForFolder(String name, String path) {
        Image image = new Image(storedName: name, actualName: "testImage", path: path, extension: 'jpg', altText: 'testImage')
        return image
    }

    public String getActualFileNameForFolder(String fileName, String path) {
        File file2 = new File(path)
        File actualFile2 = new File(file2, fileName)
        return actualFile2.name
    }
}
