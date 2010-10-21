package com.mp.domain

import grails.test.*
import org.codehaus.groovy.grails.plugins.GrailsPluginManager
import org.codehaus.groovy.grails.plugins.PluginManagerHolder
class ImageTests extends GrailsUnitTestCase {

    static final String folder1Path = '/tmp/mpUnitTests/1/'
    static final String folder2Path = '/tmp/mpUnitTests/2/'

    String image1 = "1.jpg"
    String image2 = "2.jpg"
    String image2_100 = "2_100.jpg"
    String image2_200 = "2_200.jpg"
    String image2_640 = "2_640.jpg"

    Image imageObject1 = getImageObjectForFolder(image1, folder1Path)


    protected void setUp() {
        PluginManagerHolder.pluginManager = [hasGrailsPlugin: { String name -> true }] as GrailsPluginManager
        mockConfig('''
homepageRootDir = 'homepage/'
imagesRootDir = System.getProperty("java.io.tmpdir") + "/mpImages/"
        ''')

        String fileName
        byte[] fileContents = System.currentTimeMillis().toString().bytes

        File file1 = new File(folder1Path)
        file1.mkdirs()
        File actualFile = new File(file1, image1)
        actualFile.withOutputStream {out -> out.write fileContents }

        // creating second directory and writing files on it
        File file2 = new File(folder2Path)
        file2.mkdirs()

        File actualFile1 = new File(file2, image2_100)
        actualFile1.withOutputStream {out -> out.write fileContents }

        byte[] fileContents2 = System.currentTimeMillis().toString().bytes

        File actualFile2 = new File(file2, image2_200)
        actualFile2.withOutputStream {out -> out.write fileContents2 }

        byte[] fileContents3 = System.currentTimeMillis().toString().bytes

        File actualFile3 = new File(file2, image2_640)
        actualFile3.withOutputStream {out -> out.write fileContents3 }
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
        PluginManagerHolder.pluginManager = null
    }

    /* These test-cases tests the readFile method.
       The folder "one" contains a file 1.jpg
       The folder "two" contains three files 2_100.jpg, 2_200.jpg, 2_640.jpg of different sizes.
    */


    void test_readFile_Folder1_Without_Size() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)
        String fileName = imageObject1.getFileToBeRead(folder1Path).name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder1_With_File_Size_100() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)
        String fileName = imageObject1.getFileToBeRead(folder1Path, '100').name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder1_With_File_Size_200() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)
        String fileName = imageObject1.getFileToBeRead(folder1Path, '200').name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder1_With_File_Size_640() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)
        String fileName = imageObject1.getFileToBeRead(folder1Path, '640').name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder2_Without_File_Size() {
        String actualFileName = getActualFileNameForFolder(image2_640, folder2Path)
        Image image = getImageObjectForFolder(image2_640, folder2Path)
        String fileName = image.getFileToBeRead(folder1Path).name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder2_With_File_Size_100() {
        String actualFileName = getActualFileNameForFolder(image2_100, folder2Path)
        Image image = getImageObjectForFolder(image2_100, folder2Path)
        String fileName = image.getFileToBeRead(folder2Path, '100').name
        assertEquals actualFileName, fileName
    }


    void test_readFile_Folder2_With_File_Size_200() {
        String actualFileName = getActualFileNameForFolder(image2_200, folder2Path)
        Image image = getImageObjectForFolder(image2_200, folder2Path)
        String fileName = image.getFileToBeRead(folder2Path, '200').name
        assertEquals actualFileName, fileName
    }

    void test_readFile_Folder2_With_File_Size_640() {
        String actualFileName = getActualFileNameForFolder(image2_640, folder2Path)
        Image image = getImageObjectForFolder(image2_640, folder2Path)
        String fileName = image.getFileToBeRead(folder2Path, '640').name
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

    public void testUploadHomePageImage_Valid_Values() {
        mockDomain(Image)
        byte[] bytes = System.currentTimeMillis().toString().bytes
        String fileName = 'somefile.jpg'
        Image image = Image.uploadHomePageImage(bytes, fileName)
        assertNotNull (image)
        assertNotNull (image.getId())
        assertEquals image.getStoredName(), fileName
        assertEquals image.getActualName(), fileName
        assertEquals image.getAltText(), fileName
    }

    public void testUploadHomePageImage_Invalid_Value() {
        mockDomain(Image)
        byte[] bytes = null
        String fileName = 'somefile.jpg'
        Image image = Image.uploadHomePageImage(bytes, fileName)
        assertNull (image)
    }
}
