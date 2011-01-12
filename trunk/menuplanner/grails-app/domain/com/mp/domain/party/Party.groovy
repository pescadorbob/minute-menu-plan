package com.mp.domain.party

import org.grails.comments.Comment
import com.mp.domain.FacebookAccount
import com.mp.domain.LoginCredential
import com.mp.domain.Recipe
import com.mp.domain.MenuPlan
import com.mp.domain.ShoppingList
import com.mp.domain.Aisle
import com.mp.domain.Product
import com.mp.domain.Item
import com.mp.domain.UserLogin
import com.mp.domain.party.Subscriber
import com.mp.domain.PartyRoleType
import com.mp.domain.party.Coach
import com.mp.domain.CommentAbuse
import com.mp.domain.RecipeAbuse
import com.mp.domain.PartyRoleType

public class Party {
    String name
    Date joiningDate
    FacebookAccount facebookAccount
    Boolean isEnabled
    Boolean showAlcoholicContent = false
    String uniqueId = UUID.randomUUID().toString()
    Date lastLogin

    Set<PartyRole> roles = []
    Set<LoginCredential> loginCredentials = []
    Set<Recipe> contributions = []
    Set<MenuPlan> menuPlans = []
    Set<Recipe> favourites = []
    Set<ShoppingList> shoppingLists = []
    List<Aisle> aisles = []
    List<Product> ingredients = []

    static transients = ['isEnabledString', 'email', 'password', 'userLogin', 'role', 'administrator', 'superAdmin', 'subscriber', 'director', 'coach']

    static hasMany = [favourites: Recipe, contributions: Recipe, menuPlans: MenuPlan, aisles: Aisle, ingredients: Product,
            roles: PartyRole, loginCredentials: LoginCredential, shoppingLists: ShoppingList]

    def beforeInsert = {
        joiningDate = new Date()
    }

    Boolean canViewItem(Item item) {
        return (item.shareWithCommunity || (item?.id in contributions*.id) || (item?.id in ingredients*.id))
    }

    def beforeDelete = {
    }

    String toString() {
        return name
    }

    String getEmail() {
        return UserLogin.findByParty(this)?.email
    }

    String getPassword() {
        return UserLogin.findByParty(this).password
    }

    UserLogin getUserLogin() {
        UserLogin userLogin = loginCredentials?.toList()?.find {LoginCredential loginCredential ->
            return (loginCredential instanceof UserLogin)
        }
        return userLogin
    }

    static mapping = {
        tablePerHierarchy false
        menuPlans cascade: "all-delete-orphan"
        shoppingLists cascade: "all-delete-orphan"
        loginCredentials cascade: "all-delete-orphan"
    }
    static fetchMode = [roles:'eager']
    static constraints = {
        lastLogin(nullable: true)
        facebookAccount(nullable: true)
        joiningDate(nullable: true, blank: true)
        uniqueId(nullable: true, blank: true)
        isEnabled(nullable: true)
    }

    String getIsEnabledString() {
        if (isEnabled == null) {
            return 'Awaiting Verification'
        } else {
            return (isEnabled ? 'Enabled' : 'Disabled')
        }
    }

    Subscriber getSubscriber() {
        return getRole(PartyRoleType.Subscriber)
    }

    Administrator getAdministrator() {
        return getRole(PartyRoleType.Admin)
    }

    SuperAdmin getSuperAdmin() {
        return getRole(PartyRoleType.SuperAdmin)
    }

    Director getDirector() {
        return getRole(PartyRoleType.Director)
    }

    Coach getCoach() {
        return getRole(PartyRoleType.Coach)
    }

    PartyRole getRole(PartyRoleType type) {
        PartyRole partyRole = roles?.find {it.type == type}
        return partyRole
    }

    def getRoleTypes() {
        return (roles*.type)
    }

    def getAbusiveCommentsMap() {
        List<Comment> allComments = Comment.findAllByPoster(this)
        List<Comment> abusiveCommentsList = []
        if (allComments) {
            List<CommentAbuse> x = CommentAbuse.findAllByCommentInList(allComments)
            abusiveCommentsList = x*.comment
        }

        Map map = (abusiveCommentsList) ? abusiveCommentsList.groupBy {it} : [:]
        if (map) {
            map.keySet().each {key ->
                map[key] = map[key].size()
            }
        }
        return map
    }

    def getAbusiveRecipesMap() {
        List<Recipe> abusiveRecipesList = []
        if (this.contributions) {
            List<RecipeAbuse> recipeAbuses = RecipeAbuse.findAllByRecipeInList(this.contributions?.toList())
            abusiveRecipesList = recipeAbuses*.recipe
        }
        Map map = (abusiveRecipesList) ? abusiveRecipesList.groupBy {it} : [:]
        if (map) {
            map.keySet().each {key ->
                map[key] = map[key].size()
            }
        }
        return map
    }

    def getInappropriateFlagsCount() {
        Integer total = 0
        if (abusiveRecipesMap) { total += abusiveRecipesMap.values()?.sum {it}}
        if (abusiveCommentsMap) { total += abusiveCommentsMap.values()?.sum {it}}
        return total
    }

    boolean equals(final Object o) {
        if (this.is(o)) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;
        final Party other = Party.class.cast(o);
        return id == null ? false : id.equals(other.id);
    }
}
