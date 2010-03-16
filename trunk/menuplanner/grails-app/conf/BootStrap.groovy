import com.mp.domain.*
import org.codehaus.groovy.grails.commons.ApplicationHolder

class BootStrap {

    def init = {servletContext ->

        // Inject the helper s() method
        Object.metaClass.s = {
            def object = delegate.save(flush: true)
            if (!object) {
                delegate.errors.allErrors.each {
                    println it
                }
            }
            object
        }

        //Populating DOMAIN:Metric
        List<Metric> metrics = []
        metrics << new Metric(name:'drop' , symbol:'-' , definition:'1/96 tsp.' ).s()
        metrics << new Metric(name:'teaspoon' , symbol:'tsp' , definition:'1/3 tbsp.' ).s()
        metrics << new Metric(name:'tablespoon' , symbol:'T' , definition:'1/2 fl.oz.' ).s()
        metrics << new Metric(name:'fluid ounce' , symbol:'oz.' , definition:'1/128 gal.' ).s()
        metrics << new Metric(name:'jigger' , symbol:'-' , definition:'1 1/2 oz.' ).s()
        metrics << new Metric(name:'gill' , symbol:'gi' , definition:'1/2 cup' ).s()
        metrics << new Metric(name:'cup' , symbol:'C' , definition:'1/2 pint' ).s()
        metrics << new Metric(name:'fifth' , symbol:'-' , definition:'1/5 gal.' ).s()
        metrics << new Metric(name:'quart' , symbol:'qt' , definition:'1/4 gal.' ).s()
        metrics << new Metric(name:'gallon' , symbol:'gal' , definition:'231 in3' ).s()
        metrics << new Metric(name:'single' , symbol:'' , definition:'one of anything' ).s()

        //Populating DOMAIN:Quantity
        List<Quantity> quantities = []
        metrics.each{Metric metric ->
           quantities << new Quantity(amount: new Random().nextInt(10), metric: metric).s()
        }

        //Populating DOMAIN:MeasuredProduct
        List<String> productNames = ['vegetable oil', 'sugar', 'brown sugar', 'egg', 'vanilla', 'all-purpose flour', 'baking soda', 'salt', 'semisweet chocolate chips', 'walnuts']
        List<MeasuredProduct> products = []
        productNames.eachWithIndex {String productName, Integer index ->
            //byte[] tempImg = new File("/home/neeraj/Pictures/sampleForProjects/img11.jpg").readBytes()
            products << new MeasuredProduct(name: productName, preferredMetric: metrics[index]).s()
        }

        //Populating DOMAIN:Recipe
        byte[]  recipeImage = new File(ApplicationHolder.application.parentContext.servletContext.getRealPath("/bootstrapData/img12.jpg")).readBytes()
        Recipe cookieSticks = new Recipe(name: 'Cookie Sticks', image: recipeImage).s()

        //Populating DOMAIN:RecipeIngradients
        new RecipeIngredient(sequence: 1, recipe: cookieSticks, ingredient: products[new Random().nextInt(products.size())], quantity: quantities[new Random().nextInt(quantities.size())]).s()
        new RecipeIngredient(sequence: 2, recipe: cookieSticks, ingredient: products[new Random().nextInt(products.size())], quantity: quantities[new Random().nextInt(quantities.size())]).s()
        new RecipeIngredient(sequence: 3, recipe: cookieSticks, ingredient: products[new Random().nextInt(products.size())], quantity: quantities[new Random().nextInt(quantities.size())]).s()

        //Populating DOMAIN:RecipeDirection
        new RecipeDirection(step: 'boil Milk', sequence: 1, recipe: cookieSticks).s()
        new RecipeDirection(step: 'add Sugar', sequence: 2, recipe: cookieSticks).s()


    }
    def destroy = {
    }
} 