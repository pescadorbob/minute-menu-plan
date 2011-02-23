package com.mp.access

import static com.mp.MenuConstants.*
import com.mp.domain.LoginCredential
import com.mp.domain.party.Party
import com.mp.domain.subscriptions.Subscription
import com.mp.domain.subscriptions.ControllerActionFeature
import com.mp.domain.subscriptions.FeatureSubscription
import com.mp.domain.subscriptions.Feature
import com.mp.domain.party.Subscriber
import com.mp.domain.subscriptions.ProductOffering
import groovy.time.TimeCategory
import com.mp.domain.access.AccessFilter
import com.mp.domain.access.AccessFilterType
import com.mp.domain.Recipe
import com.mp.domain.Permission
import com.mp.domain.access.SecurityRole
import com.mp.domain.access.PermissionLevel
import com.mp.tools.UserTools

/**
 * Created on Nov 28, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Access-Wealth, LLC.
 * All rights reserved.
 */

public class PermissionService {

  boolean transactional = true


      private Boolean isPermitted(Long level, Recipe recipe, Party party) {
          if (level == UNRESTRICTED_ACCESS_PERMISSION_LEVEL) {
              return true
          } else if ((level % ACCESS_IF_OWNS_RECIPE_PERMISSION_LEVEL) == 0) {
              return validateOwnsRecipe(recipe)
          } else if ((level % ACCESS_IF_OWNS_USER_PERMISSION_LEVEL) == 0) {
              return validateOwnsUser(party)
          }
          return false
      }

      private Boolean validateOwnsRecipe(Recipe recipe) {
          LoginCredential user = UserTools.currentUser
          return (user && (recipe in user?.party?.contributions))
      }

      private Boolean validateOwnsUser(Party party) {
          LoginCredential user = UserTools.currentUser
          return (user && (user.party == party))
      }

      public Boolean hasPermission(Permission permission, Recipe recipe = null, Party party = null) {
          LoginCredential user = UserTools.currentUser
          if (!user) {
              return false
          }
          List<SecurityRole> roles = SecurityRole.findAllByNameInList(user.party.roles*.type.name)
          Boolean result = roles?.any {SecurityRole role ->
              PermissionLevel permissionLevel = PermissionLevel.findByPermissionAndRole(permission, role)
              if (!permissionLevel) {return false}
              Long level = permissionLevel.level
              return isPermitted(level, recipe, party)
          }
          return result
      }

  // runs through all user permissions to see if the user has permission
  public boolean verifyPermission(user, controllerName, actionName, uri, now) {
    Party theParty = user.party
    def accessFilters = AccessFilter.withCriteria {
      filterFor {
        eq('type', AccessFilterType.PERMISSION)
        gt('activeFrom', now)
        or {
          isNull('activeTo')
          lt('activeTo', now)
        }
      }
    }
    boolean requiresAccess = accessFilters.find{
                      (!it.controllerFilter || controllername ==~ it.controllerFilter) &&
                              (!it.actionFilter || actionName ==~ it.actionFilter) &&
                              (!it.uriFilter || uri ==~ it.uriFilter)
                  }.size()>0
    if(!requiresAccess) return true // its a resource unprotected by access control
    def retValue = false
    def c = Subscription.createCriteria()
    c.list {
      subscriptionFor {
        party {
          idEq(theParty.id)
        }
      }
      le('activeFrom', now)
      ge('activeTo', now - 1)
    }.each {subscription ->
      def feature = subscription.subscribedFeature
      if (feature.class == ControllerActionFeature.class) {
        if (log.isDebugEnabled()) {
          log.debug """feature filters:
            action:${!feature.actionFilter || actionName ==~ feature.actionFilter}
            uri:${(!feature.uriFilter || uri ==~ feature.uriFilter)}
            controller:${!feature.controllerFilter || controllerName ==~ feature.controllerFilter}
            activeFrom:${feature.activeFrom <= new Date()}
            activeTo:${(!feature.activeTo || (feature.activeTo > now))}"""
        }
        retValue |= ((!feature.uriFilter || uri ==~ feature.uriFilter)
                && (!feature.actionFilter || actionName ==~ feature.actionFilter)
                && (!feature.controllerFilter || controllerName ==~ feature.controllerFilter)
                && (feature.activeFrom <= now)
                && (!feature.activeTo || (feature.activeTo > now)))
      }

    }
    retValue
  }

}
