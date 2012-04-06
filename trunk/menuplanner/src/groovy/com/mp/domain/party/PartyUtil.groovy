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

public class PartyUtil {

    Boolean canViewItem(Item item) {
        return (item.shareWithCommunity || (item?.id in contributions*.id) || (item?.id in ingredients*.id))
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


    String getIsEnabledString() {
        if (isEnabled == null) {
            return 'Awaiting Verification'
        } else {
            return (isEnabled ? 'Enabled' : 'Disabled')
        }
    }

    Subscriber getSubscriber() {
        return Subscriber.findByParty(this)
    }

    Administrator getAdministrator() {
        return Administrator.findByParty(this)
    }

    SuperAdmin getSuperAdmin() {
        return SuperAdmin.findByParty(this)
    }

    Director getDirector() {
        return Director.findByParty(this)
    }

    Coach getCoach() {
        return Coach.findByParty(this)
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
  Set<Address> getAddresses() {
    return PartyAddress.findAllByParty(this).collect {it.address} as Set
  }

  boolean hasAddress(Address address) {
    return PartyAddress.countByPartyAndAddress(this, address) > 0
  }

  void removeFromAddresses(Address address, boolean flush = false) {
    PartyAddress.remove(this, address, flush)
  }

  boolean addToAddresses(Address address, Date fromDate, Date thruDate = null, Boolean flush = false) {
    return PartyAddress.create(this, address, fromDate, thruDate, flush)
  }

  Set<Address> getCurrentAddresses() {
    Date date = Date.parse('MM/dd/yyyy', new Date().format('MM/dd/yyyy'))
    def partyAddressCriteria = PartyAddress.createCriteria()
    List partyAddresses = partyAddressCriteria.list {
      eq("party", this)
      or {
        isNull("thruDate")
        ge("thruDate", date)
      }
    }
    return partyAddresses ? Address.getAll(partyAddresses*.address.id) as Set : null
  }

}
