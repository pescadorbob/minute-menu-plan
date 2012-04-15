package com.mp.domain

import static com.mp.MenuConstants.*
import com.mp.IngredientItemVO
import com.mp.domain.party.Party
import com.mp.domain.subscriptions.ProductOffering
import com.mp.domain.subscriptions.ProductOfferingSubscription
import com.mp.domain.subscriptions.CommunitySubscription
import com.mp.subscriptions.SubscriptionStatus
import com.mp.domain.subscriptions.RecurringCharge
import com.mp.MenuConstants
import com.mp.domain.subscriptions.ControllerActionFeature
import com.mp.domain.subscriptions.Feature
import com.mp.domain.subscriptions.ContributionRequirement
import com.mp.domain.subscriptions.SubscriptionContributionRequirement

class UtilService {

  def accountingService
  def subscriptionService
  String createPartyWithSubscription(UserCO userCO,int productId){

    ProductOffering po = ProductOffering.get(productId)
    String item_name = po.name
    Party party = userCO.createParty()
    if(po.basePrice?.value <= 0){
      allocatePendingCommunitySubscription(party)
      party?.lastLogin = new Date()
      party?.s()
    } 
    party.uniqueId
  }
  void allocateTrialSubscription(Party party) {
    accountingService.findOrCreateNewAccount(party)
    def trialSubscriptionCreationClosure = {productOffering, subscriber, start, endDate ->
      new ProductOfferingSubscription(subscribedProductOffering: productOffering, subscriptionFor: subscriber,
              originalProductOffering: productOffering?.name, activeFrom: start, activeTo: endDate)
    }
    subscriptionService.createSubscriptionForUserSignUp(party, ProductOffering.findByName(TRIAL_SUBSCRIPTION).id,
            trialSubscriptionCreationClosure)
  }

  void allocatePendingCommunitySubscription(party) {
    party.isEnabled = true
    party.s()
    accountingService.findOrCreateNewAccount(party)
    def communitySubscriptionClosure = { productOffering, subscriber, start, endDate ->
      def contributionRequirements = ContributionRequirement.withCriteria {
          pricingFor {
            idEq(productOffering.id)
          }
      }
      def subscription = CommunitySubscription.findBySubscriptionFor(subscriber)
      if(!subscription){
          subscription = new CommunitySubscription(status:SubscriptionStatus.PENDING,
                  contribution: null,
                  subscribedProductOffering: productOffering,
              subscriptionFor: subscriber,
              originalProductOffering: productOffering?.name, activeFrom: start, activeTo: endDate,
          )
        def subscriptionRequirements = contributionRequirements.collect { contributionRequirement ->
           new SubscriptionContributionRequirement(created:new Date(),requiredFor:subscription,
                      requires:contributionRequirement)
        }
        subscriptionRequirements.each{
          subscription.addToRequirements(it)
        }
      }
      subscription
    }
    subscriptionService.createSubscriptionForUserSignUp(party, ProductOffering.findByName(COMMUNITY_SUBSCRIPTION).id, communitySubscriptionClosure)
  }

  void allocatePaypalSubscription(party) {
    party.isEnabled = true
    party.s()
    accountingService.findOrCreateNewAccount(party)
    def communitySubscriptionClosure = { productOffering, subscriber, start, endDate ->
      def contributionRequirements = ContributionRequirement.withCriteria {
          pricingFor {
            idEq(productOffering.id)
          }
      }
      def subscription = CommunitySubscription.findBySubscriptionFor(subscriber)
      if(!subscription){
          subscription = new CommunitySubscription(status:SubscriptionStatus.PENDING,
                  contribution: null,
                  subscribedProductOffering: productOffering,
              subscriptionFor: subscriber,
              originalProductOffering: productOffering?.name, activeFrom: start, activeTo: endDate,
          )
        def subscriptionRequirements = contributionRequirements.collect { contributionRequirement ->
           new SubscriptionContributionRequirement(created:new Date(),requiredFor:subscription,
                      requires:contributionRequirement)
        }
        subscriptionRequirements.each{
          subscription.addToRequirements(it)
        }
      }
      subscription
    }
    subscriptionService.createSubscriptionForUserSignUp(party, ProductOffering.findByName(COMMUNITY_SUBSCRIPTION).id, communitySubscriptionClosure)
  }

  List<IngredientItemVO> parseIngredients(String rawIngredients) {
    List list = rawIngredients.readLines()
    List<IngredientItemVO> ingredientItemVos = []

    IngredientItemVO ingredientItemVo
    list.each {
      ingredientItemVo = new IngredientItemVO()
      def matcher = it =~ /[-]+.+[:]*/
      ingredientItemVo.preparationMethod = matcher ? matcher[0]?.replace("--", "")?.replace(":", "")?.trim() : ''

      println ""

      matcher = it =~ /[:].+/
      ingredientItemVo.aisle = matcher ? matcher[0]?.replace(":", "")?.trim() : ''

      matcher = it =~ /\d+[\s]*([\d]*[\/][\d])*/
      ingredientItemVo.amount = matcher ? matcher[0][0]?.trim() : ''

      matcher = it =~ /[a-zA-Z]+/
      ingredientItemVo.measure = matcher ? matcher[0]?.trim() : ''

      matcher = it =~ /[a-zA-z]+\s?(\s*[a-zA-Z]+.*[--]*)/
      ingredientItemVo.ingredient = matcher ? matcher[0][1]?.trim()?.replace("--", "") : ''

      ingredientItemVos += ingredientItemVo
    }

    return ingredientItemVos

  }
}
