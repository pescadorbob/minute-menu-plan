package com.mp.domain

class ImageController {

    def recipeImage = {
        if (params.id) {
            Image image = Image.get(params.id)
            byte[] imageBytes = image.readFile()
            response.setContentLength(imageBytes.size());
            OutputStream out = response.getOutputStream();
            out.write(imageBytes)
            out.close();
        }
    }

    def showImage = {
        Image image
        if (params.selectRecipeImagePath) {
            image = new Image(params.selectRecipeImagePath, "Some alt Text")
        }
        if (params.id) {
            image = Recipe.get(params.id.toLong()).image
        }
        if (image) {
            byte[] fileContent = image.readFile()
            String fileName = image.actualName + "." + image.extension
            response.setContentLength(fileContent.size())
            response.setHeader("Content-disposition", "attachment; filename=" + fileName)
            response.setContentType("image/${image.extension}")
            OutputStream out = response.getOutputStream()
            out.write(fileContent)
            out.flush()
            out.close()
        }
    }

    def index = { }
}
