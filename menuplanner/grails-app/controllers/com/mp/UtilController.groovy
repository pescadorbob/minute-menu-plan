package com.mp

import com.mp.domain.*

import static com.mp.MenuConstants.*
import org.apache.commons.math.fraction.*

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.text.FieldPosition

import org.springframework.web.multipart.commons.CommonsMultipartFile
import com.mp.domain.party.Party
import com.mp.domain.access.PermissionLevel
import com.mp.domain.access.SecurityRole

class UtilController {
    def excelService
    def userService
    def asynchronousMailService
    def facebookConnectService
    def utilService
    def masterDataBootStrapService
    def messageSource
    Object[] testArgs = {}

    static config = ConfigurationHolder.config

    def paypal = {
        println "Success"
        render "Success"
    }

    def cancelPaypal = {
        println "Cancelled"
        render "Cancelled"
    }

    def index = {
        render "success"
    }


    def updateSecurityRole={
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

    def updateUniqueId={
        Party.list().each{
            if(!it.uniqueId){
                it.uniqueId=UUID.randomUUID().toString()
            }
        }
        render "Updated UUID"
    }

    String getMessage(String key) {
        def keyValue = messageSource.resolveCode(key, new java.util.Locale("EN"))
        return keyValue?.format(testArgs)
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