package com.mp.domain

class ImageController {

  def recipeImage= {
              if(params.id)
              {
              RecipeImage recipeImage = RecipeImage.get( params.id )
              byte[] image = recipeImage.image
              response.outputStream << image
              }
          }
  def index = { }
}
