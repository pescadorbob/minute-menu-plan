package com.mp
import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

class BootstrapService {

    boolean transactional = true

    public void populateMetrics(Integer count) {
        (1..count).each {Integer index ->
            new Metric(name: "Metric-${index}", symbol: "Symbol-${index}", definition: "This is definition for Metrics-${index}").s()
        }
    }

    public void populateQuantities(Integer count) {
        (1..count).each {Integer index ->
            new Quantity(amount: new Random().nextInt(10)+1, metric: Metric.get(new Random().nextInt(Metric.count()) + 1)).s()
        }
    }

    public void populateProduct(Integer count) {
        (1..count).each {Integer index ->
            Product product=new Product(name: "Product-${index}").s()
            def image = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/img13.jpg")).readBytes()
            ProductImage productImage = new ProductImage(productId: product.id, image: image).s()
        }
    }

    public void populateMeasuredProduct(Integer count) {
        (1..count).each {Integer index ->
            MeasuredProduct measuredProduct = new MeasuredProduct()
            measuredProduct.product = Product.get(new Random().nextInt(Product.count()) + 1)
            measuredProduct.preferredMetric = Metric.get(new Random().nextInt(Metric.count()) + 1)
            //measuredProduct.possibleMetrics= Metric.get(new Random().nextInt(Metric.count())+1)
            measuredProduct.s()

        }
    }

    public void populateRecipe(Integer count) {
        (1..count).each {Integer index ->
            Recipe recipe = new Recipe(name: "Recipe-${index}").s()
            def image = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/coffee.jpg")).readBytes()
            RecipeImage recipeImage = new RecipeImage(recipeId: recipe.id, image: image).s()
            populateRecipeIngredient(recipe)
            populateRecipeDirections(recipe)

        }

    }

    public void populateRecipeIngredient(Recipe recipe) {
        (1..3).each {Integer index ->
            RecipeIngredient ingredient = new RecipeIngredient()
            ingredient.recipe = recipe
            ingredient.sequence = index
            ingredient.ingredient = MeasuredProduct.get(new Random().nextInt(MeasuredProduct.count()) + 1)
            ingredient.quantity = Quantity.get(new Random().nextInt(Quantity.count()) + 1)
            ingredient.s()
        }
    }

    public void populateRecipeDirections(Recipe recipe) {
        (1..5).each {Integer index ->
            RecipeDirection recipeDirection=new RecipeDirection(step: "for ${recipe.name} step-${index}", sequence: index, recipe: recipe).s()
            def image = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/img11.jpg")).readBytes()
            RecipeDirectionImage recipeDirectionImage = new RecipeDirectionImage(recipeDirectionId: recipeDirection.id, image: image).s()
        }
    }
}