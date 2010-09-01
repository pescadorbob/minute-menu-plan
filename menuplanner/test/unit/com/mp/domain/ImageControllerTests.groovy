package com.mp.domain

class ImageControllerTests extends ImageTests {

    protected void setUp() {
        super.setUp()
    }


    protected void tearDown() {
        super.tearDown()
    }

    void test_getImageBytes_valid_Values_Folder1() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)

        ImageController imageController = new ImageController()
        File file2 = new File(folder1Path)
        File actualFile2 = new File(file2, image1)

        File file = imageController.getImageFileToRead(actualFile2, folder1Path + image1)
        assertEquals file.name, actualFileName
    }

    void test_getImageBytes_Actual_File_Null_Folder1() {
        String actualFileName = getActualFileNameForFolder(image1, folder1Path)

        ImageController imageController = new ImageController()
        File file2 = new File(folder1Path)
        File actualFile2 = new File(file2, 'abc.jpg')

        File file = imageController.getImageFileToRead(actualFile2, folder1Path + image1)
        assertEquals file.name, actualFileName
    }

    void test_getImageBytes_Valid_Values_Folder2() {
        String actualFileName = getActualFileNameForFolder(image2_1080, folder2Path)

        ImageController imageController = new ImageController()
        File file2 = new File(folder2Path)
        File actualFile2 = new File(file2, image2)

        File file = imageController.getImageFileToRead(actualFile2, folder2Path + image2)
        assertEquals file.name, actualFileName
    }

    void test_getImageBytes_Actual_File_Null_Folder2() {
        String actualFileName = getActualFileNameForFolder(image2_1080, folder2Path)

        ImageController imageController = new ImageController()
        File file2 = new File(folder2Path)
        File actualFile2 = new File(file2, 'xyz.jpg')

        File file = imageController.getImageFileToRead(actualFile2, folder2Path + image2)
        assertEquals file.name, actualFileName
    }

    void test_getImageBytes_Valid_Values_Image_1080_Folder2() {
        String actualFileName = getActualFileNameForFolder(image2_1080, folder2Path)

        ImageController imageController = new ImageController()
        File file2 = new File(folder2Path)
        File actualFile2 = new File(file2, image2_1080)

        File file = imageController.getImageFileToRead(actualFile2, folder2Path + image2)
        assertEquals file.name, actualFileName
    }

    void test_getImageBytes_Valid_Values_Image_200_Folder2() {
        String actualFileName = getActualFileNameForFolder(image2_200, folder2Path)

        ImageController imageController = new ImageController()
        File file2 = new File(folder2Path)
        File actualFile2 = new File(file2, image2_200)

        File file = imageController.getImageFileToRead(actualFile2, folder2Path + image2)
        assertEquals file.name, actualFileName
    }

    void test_getImageBytes_Actual_File_Null_And_Image1080_Path_Folder2() {
        String actualFileName = getActualFileNameForFolder(image2_1080, folder2Path)

        ImageController imageController = new ImageController()
        File file2 = new File(folder2Path)
        File actualFile2 = new File(file2, 'abc.jpg')

        File file = imageController.getImageFileToRead(actualFile2, folder2Path + image2_1080)
        assertEquals file.name, actualFileName
    }

    void test_getImageBytes_Actual_File_Null_And_Image200_Path_Folder2() {
        String actualFileName = getActualFileNameForFolder(image2_1080, folder2Path)

        ImageController imageController = new ImageController()
        File file2 = new File(folder2Path)
        File actualFile2 = new File(file2, 'abc.jpg')

        File file = imageController.getImageFileToRead(actualFile2, folder2Path + image2_200)
        assertEquals file.name, actualFileName
    }
}
