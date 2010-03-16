package com.mp.domain

class ImageController {

  def imgMeasuredProduct= {
              if(params.id)
              {
              MeasuredProduct temp = MeasuredProduct.get( params.id )
              byte[] image = temp.image
              response.outputStream << image
              }
          }

  def imgRecipe= {
              if(params.id)
              {
              Recipe temp = Recipe.get( params.id )
              byte[] image = temp.image
              response.outputStream << image
              }
          }

  def index = { }
}
