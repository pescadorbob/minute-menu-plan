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

    def index = { }
}
