package com.mp.domain

import grails.converters.JSON
import org.grails.comments.Comment
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.grails.plugins.imagetools.ImageTool
import com.mp.domain.party.Party
import com.mp.tools.UserTools
import com.mp.domain.ndb.NDBFood
import com.mp.domain.ndb.NDBWeight
import com.mp.domain.ndb.ItemNutritionLink
import com.mp.domain.ndb.NutritionLink
import java.text.NumberFormat
import java.text.DecimalFormat


class MenuplannerTagLib {

  static namespace = 'mp'
  def permissionService
  def recipeService
  def analyticsService
  static config = ConfigurationHolder.config


  def showEditRecipe = {attrs ->
    Long recipeId = attrs['recipeId']?.toLong()
    Recipe recipe = Recipe.get(recipeId)
    if (permission.hasPermission(permission: Permission.UPDATE_RECIPE, recipe: recipe)) {
      out << g.render(template: '/recipe/isEditableRecipe', model: [isEditable: true, recipeId: recipeId])
    }
  }

  def usedProducts = {attrs, body ->
    def var = attrs.var ? attrs.var : "ingredient"
    def unmatched = attrs.unmatched ? attrs.unmatched : "unmatched"
    def status = attrs.status ? attrs.status : "windex"
    def id = attrs.in ? attrs.in : null
    def ingredients
    RecipeIngredient.withSession {
      def measurableProduct = Item.get(id)

      def query = "select distinct ri from RecipeIngredient as ri \
      left join ri.quantity as q \
      left join q.unit as recipeUnit where ri.ingredient = :product "
      def unmatchedClause = " and not exists                                            \
       (from ItemNutritionLink as link                             \
         left join link.unit link_unit \
         where link.product = ri.ingredient                          \
          and ( (link.prep is null  and ri.preparationMethod is null ) or link.prep = ri.preparationMethod )\
           and ((link_unit is null  and recipeUnit is null) or link_unit = q.unit \
               or (link_unit.isConvertible=1 and recipeUnit.isConvertible=1))      \
       ) "
      println "Query: ${query + unmatched ? unmatchedClause : ""}"
      ingredients = RecipeIngredient.executeQuery(query, [product: measurableProduct])

    }

    ingredients.eachWithIndex {obj, index ->
      out << body((var): obj, (status): index)
    }
  }
  def weights = {attrs, body ->
    def var = attrs.var ? attrs.var : "weight"
    def status = attrs.status ? attrs.status : "windex"
    def foodId = attrs.in ? attrs.in : null
    def weights
    NDBFood.withSession {
      def ndbfood = NDBFood.get(foodId)
      weights = ndbfood?.weights
    }

    weights?.sort {it.seq}.eachWithIndex {obj, index ->
      out << body((var): obj, (status): index)
    }
  }

  def showFavorite = {attrs ->
    Long recipeId = attrs['recipeId']?.toLong()
    LoginCredential user = UserTools.currentUser
    Recipe recipe = Recipe.get(recipeId)
    out << g.render(template: '/recipe/showAddToFavorite', model: [isAdded: (recipe in user?.party?.favourites)])
  }
  def menuPlanDropdown = {
    analyticsService.recordIntervalIn(System.currentTimeMillis(), request.'appRequestCO', "menu-plan-find", '')
    LoginCredential user = UserTools.currentUser
    def menuPlans = user ? MenuPlan.executeQuery('select id, name from MenuPlan as s where s.owner = ?',[user?.party]):[]
    analyticsService.recordIntervalOut(System.currentTimeMillis(), request.'appRequestCO', "menu-plan-find")
    out << g.render(template: '/layouts/menuPlanDropdown', model: [menuPlans: menuPlans])
  }

  def loggedUserDropDown = {attrs ->
    out << g.render(template: '/layouts/loggedUserDropDown', model: [loggedUser: UserTools.currentUser])
  }

  def shoppingListDropDown = {
    def now = System.currentTimeMillis()
    def shoppingLists = request.'shoppingLists'
    if(!shoppingLists) {
      analyticsService.recordIntervalIn(now, request.'appRequestCO', "shopping-list-find-tag", '')
      LoginCredential user = UserTools.currentUser

      shoppingLists = user ? ShoppingList.findAllByParty(user?.party) : []
      analyticsService.recordIntervalOut(System.currentTimeMillis(), request.'appRequestCO', "shopping-list-find-tag")
    }
    analyticsService.recordIntervalIn(System.currentTimeMillis(), request.'appRequestCO', "shopping-list-render-call", '')
    out << g.render(template: '/layouts/shoppingListDropDown', model: [shoppingLists: shoppingLists])
    analyticsService.recordIntervalOut(System.currentTimeMillis(), request.'appRequestCO', "shopping-list-render-call")
  }

  def adminDropDown = {
    out << g.render(template: '/layouts/adminDropDown')
  }

  def checkGeneralInfoTabError = {attrs ->
    def bean = attrs['bean']
    def fieldsToCheck = attrs['fields']

    if (bean) {

      def fieldsHavingError = bean?.errors?.allErrors.collect {it.properties['field']}
      if (fieldsHavingError.any {it.toString() in fieldsToCheck}) {
        out << "color:red;"
      } else {
        out << ""
      }
    }
  }

  def image = {attrs ->
    String alt = attrs['alt']
    String title = attrs['title']
    String height = attrs['height']
    String width = attrs['width']
    String size = attrs['size']
    Long id = attrs['id']
    String noImage = (attrs['noImage']) ? attrs['noImage'] : 'no-img.gif'
    String clas = attrs['class']
    String imgTag = "<img "
    if (title) {imgTag += "title='${title}' "}
    if (alt) {imgTag += "alt='${alt}' "}
    if (clas) {imgTag += "class='${clas}' "}
    if (height) {imgTag += "height='${height}' "}
    if (width) {imgTag += "width='${width}' "}
    imgTag += "src='"
    if (Image.exists(id)) {
      out << imgTag + createLink(controller: 'image', action: 'image', params: [id: id, size: size]) + "'/>"
    } else {
      out << imgTag + createLink(controller: 'image', action: 'imageByPath', params: [noImage: noImage]) + "'/>"
    }
  }

  def printRecipeImage = {attrs ->
    String height = attrs['height']
    String width = attrs['width']
    String size = (attrs['size']) ? attrs['size'] : 640
    Long id = attrs['id']
    String noImage = (attrs['noImage']) ? attrs['noImage'] : 'no-img.gif'

    List<Integer> heightWidth = []
    Image recipeImage = com.mp.domain.Image.get(id)
    if (recipeImage) {
      int firstIndex = recipeImage.storedName.indexOf('.')
      String name = recipeImage.storedName.substring(0, firstIndex)
      String imageDir = config.imagesRootDir + recipeImage.path
      String completePath = imageDir + name + "_${size}.jpg"
      File imageFile = new File(completePath)
      if (imageFile.exists()) {
        heightWidth = getHeightAndWidthOfImage(imageFile, 200)
      } else {
        imageFile = new File(imageDir + recipeImage.storedName)
        if (imageFile.exists()) {
          heightWidth = getHeightAndWidthOfImage(imageFile, 200)
        }
      }
      if (heightWidth.size() == 2) {
        height = heightWidth.first().toString()
        width = heightWidth.last().toString()
      }
    }
    String clas = attrs['class']
    String imgTag = "<img "
    if (clas) {imgTag += "class='${clas}' "}
    if (height) {imgTag += "height='${height}' "}
    if (width) {imgTag += "width='${width}' "}
    imgTag += "src='"
    if (com.mp.domain.Image.exists(id)) {
      out << imgTag + createLink(controller: 'image', action: 'image', params: [id: id, size: size]) + "'/>"
    } else {
      out << imgTag + createLink(controller: 'image', action: 'imageByPath', params: [noImage: noImage]) + "'/>"
    }
  }

  List<Integer> getHeightAndWidthOfImage(File imageFile, Integer size) {
    List<Integer> heigthWidth = []
    byte[] fileContents = imageFile.readBytes()
    ImageTool imageTool = new ImageTool()
    imageTool.load(fileContents)
    Integer imgH = imageTool.getHeight()
    Integer imgW = imageTool.getWidth()
    if (imgH > size || imgW > imgH) {
      Long divH = size
      Long divW = size
      Float imgRatio = imgH / imgW
      Float divRatio = divH / divW
      if (imgRatio > divRatio) {
        imgH = divH
        imgW = divW / imgRatio
      }
      else {
        imgW = divW
        imgH = divH * imgRatio
      }
      heigthWidth.add(imgH)
      heigthWidth.add(imgW)
    } else {
      heigthWidth.add(imgH)
      heigthWidth.add(imgW)
    }
    return heigthWidth
  }

  def getSelectedCategoriesAsJSON = {attrs ->
    if (attrs['prePopulated']) {
      List<Category> categories = Category.getAll([attrs['prePopulated']].flatten()*.toLong())
      List categoriesJson = categories.collect { [id: it.id, name: it.name] }
      println categoriesJson as JSON
      out << categoriesJson
    }
  }

  def mealItems = {attrs ->
    String alttext = attrs['alttext']
    MealType mealType = attrs['type']
    Week week = attrs['week']
    String image = attrs['image']
    String weekIndex = attrs['weekIndex']
    out << g.render(template: '/menuPlan/mealItems', model: [week: week, weekIndex: weekIndex, alttext: alttext, mealType: mealType, image: image])
  }


  def comments = {attrs ->
    Recipe recipe = Recipe.get(attrs['recipeId'])
    List<Comment> nonAbusiveComments = recipe?.comments
    List<Comment> abusiveComments = []
    if (recipe.comments) {
      abusiveComments = CommentAbuse.findAllByCommentInList(recipe.comments)
      if (abusiveComments) {
        Map x = abusiveComments.groupBy {it.comment.id}
        abusiveComments = abusiveComments?.findAll {(x[it.id]?.size()) >= 3}
        nonAbusiveComments = recipe.comments.findAll {(!(it.id in abusiveComments.id))}
      }
    }
    out << g.render(template: "/recipe/comments", model: [recipe: recipe, comments: nonAbusiveComments])
  }

  def commentsForPrinting = {attrs ->
    Recipe recipe = Recipe.get(attrs['recipeId'])
    List<Comment> nonAbusiveComments = recipe?.comments
    List<Comment> abusiveComments = []
    List<Comment> lastThreeComments = []
    if (recipe.comments) {
      abusiveComments = CommentAbuse.findAllByCommentInList(recipe.comments)
      if (abusiveComments) {
        Map x = abusiveComments.groupBy {it.comment.id}
        abusiveComments = abusiveComments?.findAll {(x[it.id]?.size()) >= 3}
        nonAbusiveComments = recipe.comments.findAll {(!(it.id in abusiveComments.id))}
      }
      Integer listSize = nonAbusiveComments.size()
      if (listSize > 3) {
        ((listSize - 2)..listSize).each {
          lastThreeComments.add(nonAbusiveComments[it - 1])
        }
      } else {
        nonAbusiveComments.each {Comment comment ->
          lastThreeComments.add(comment)
        }
      }
      out << g.render(template: "/recipe/comments", model: [recipe: recipe, comments: lastThreeComments, isPrintable: true])
    }
  }

  def showRecipeAbuse = {attrs ->
    Recipe recipe = Recipe.get(attrs['recipeId'])
    LoginCredential user = UserTools.currentUser
    Boolean reported = false

    List<RecipeAbuse> recipeAbuses = RecipeAbuse.findAllByReporterAndRecipe(user?.party, recipe)
    if (!recipeAbuses) {
      out << g.render(template: "/recipe/showRecipeAbuse", model: [reported: reported, recipeId: recipe?.id])
    } else {
      reported = true
      out << g.render(template: "/recipe/showRecipeAbuse", model: [reported: reported])
    }
  }

  def reportCommentAbuse = {attrs ->
    Comment comment = attrs['comment']
    Recipe recipe = Recipe.get(attrs['recipeId'])
    LoginCredential user = UserTools.currentUser
    Boolean alreadyReported = CommentAbuse.countByCommentAndReporter(comment, user?.party) as Boolean
    out << g.render(template: "/recipe/reportCommentAbuse", model: [comment: comment, user: user, recipe: recipe, alreadyReported: alreadyReported])
  }

  def firstTimeUser = {attrs, body ->
    LoginCredential user = UserTools.currentUser
    if (user && (user?.party?.menuPlans?.size() == 0)) {
      out << body()
    }
  }

  def menuPlanActions = {
    List<String> menuplanActions = ['Print Monthly Menu Plan', 'Print Weekly Menu Plan', 'Create Shopping List', 'Delete Menu Plan']
    out << g.render(template: "/menuPlan/menuPlanActions", model: [menuplanActions: menuplanActions])
  }

  def recipeIngredients = {attrs ->
    Recipe recipe = Recipe.read(attrs['recipeId'])
    Integer customServings = attrs['customServings']
    customServings = customServings ? customServings : recipe?.servings
    if (recipe) {
      List<RecipeIngredient> customRecipeIngredients = []
      customRecipeIngredients = recipeService.getRecipeIngredientsWithCustomServings(recipe, customServings)
      out << g.render(template: "/recipe/recipeIngredients", model: [ingredients: customRecipeIngredients])
    }
  }

  def recipeIngredientsForRecipeCard = {attrs ->
    Recipe recipe = Recipe.get(attrs['recipeId'])
    List<Item> items = []
    Item item
    if (recipe) {
      recipe.ingredients.each {RecipeIngredient recipeIngredient ->
        item = recipeIngredient?.ingredient
        items.add(item)
      }
      out << g.render(template: "/recipe/recipeIngredientsForRecipeCard", model: [items: items])
    }
  }

  def serveWithItems = {attrs ->
    Recipe recipe = Recipe.get(attrs['recipeId'])
    Party party
    List<Item> items = []
    if (recipe) {
      recipe.items.each {Item item1 ->
        items.add(item1)
      }
      out << g.render(template: "/recipe/serveWithItems", model: [items: items])
    }
  }
  def fact = {attrs ->
    def fact = attrs['fact']
    RecipeIngredient ri = fact[1]
    NutritionLink link = fact[0]
    def conversionFactor = fact[2]
    def fat = ""
    if (ri.quantity?.unit && conversionFactor) {
      fat = link.nutrition.getWeightFor().'faSat' * ri.quantity?.value / conversionFactor * link.nutrition.gmWgt / 100
    } else {
      fat = link.nutrition.getWeightFor().'faSat' * ri.quantity?.value * link.nutrition.gmWgt / 100
    }
    out << "fat:${fat}g"
  }
  def facts = {attrs ->
    def facts = attrs['nutrition']
    def factName = attrs['fact'] // name of the fact
    def total = 0
    def servings = 1
    facts.each {key, fact ->
      RecipeIngredient ri = fact[1]
      servings = ri.recipe.servings
      NutritionLink link = fact[0]
      StandardConversion link_conversion = fact[2]
      def quantity
      factName.split(',').each {it ->
        def weightFor = link.nutrition.getWeightFor()[it]==null?0:link.nutrition.getWeightFor()[it]
        if (ri.quantity?.unit && link_conversion) {
          quantity = weightFor * (ri.quantity?.value / link_conversion.conversionFactor) * link.nutrition.gmWgt / 100
        } else {
          quantity = weightFor * ri.quantity?.value * link.nutrition.gmWgt / 100
        }
        total += quantity
      }
    }
    total = total / servings
    NumberFormat nf = new DecimalFormat("#0")
    out << "${nf.format(total)}"
  }

  def shareThis = {attrs ->
    String coachId = attrs['coachId']
    out << """<script type="text/javascript">var switchTo5x=false;</script><script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
            <script type="text/javascript">stLight.options({publisher:'${ConfigurationHolder.config.externalKeys.shareThisKey}',popup:true});</script>
            <span class="st_sharethis" displayText="ShareThis" st_url="${attrs['shareUrl']}?guestVisitor=true${(coachId ? ('&coachId=' + coachId) : '')}"></span> """
  }
}
