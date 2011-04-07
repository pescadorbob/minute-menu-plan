package com.mp

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.web.multipart.commons.CommonsMultipartFile
import com.mp.domain.party.Party
import com.mp.domain.access.PermissionLevel
import com.mp.domain.access.SecurityRole
import com.mp.domain.party.Subscriber
import com.mp.domain.subscriptions.ProductOffering
import com.mp.domain.subscriptions.ProductOfferingSubscription

class UtilController {
    def excelService
    def userService
    def facebookConnectService
    def utilService
    def masterDataBootStrapService
    def messageSource
    def subscriptionService
    def accountingService

    static config = ConfigurationHolder.config

    def index = {
        render "No code here"
    }

    def updateSecurityRole = {
        //Delete security roles and permissions, then create again
        SecurityRole.list().each {SecurityRole securityRole ->
            List<PermissionLevel> tempPermissionLevels = securityRole.permissionLevels as List
            securityRole.permissionLevels = []
            tempPermissionLevels.each {
                it.delete(flush: true)
            }
            securityRole.delete(flush: true)
        }
        if (!SecurityRole.count()) {masterDataBootStrapService.populatePermissions()}
        render "Updated Roles"
    }

    def uploadRecipes = {
        render(view: 'uploadRecipes')
    }

    def populateRecipes = {
        CommonsMultipartFile file = params.recipeFile
        List<String> recipeLog = excelService.createLineItems(file.getInputStream())
        render(view: 'uploadResults', model: [result: recipeLog])
    }

}

class IngredientItemVO {
    String amount;
    String measure
    String ingredient
    String preparationMethod
    String aisle
}