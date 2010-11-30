package com.mp.domain

import com.mp.domain.subscriptions.Subscription
import com.mp.tools.UserTools

class SubscriptionController {

  def index = { }

  def createSubscription = {
    String userId = params?.userId
    String item_name = "Monthly Subscription"
    String item_description = "Monthly Subscription of MenuPlanner"
    String item_price = "5.00"
    String item_currency = "USD"
    String item_quantity = "1"
    render(view: 'connectToGoogleCheckout', model: [item_name: item_name,
            item_description: item_description, item_price: item_price, item_currency: item_currency,
            item_quantity: item_quantity, userId: userId.toLong()])
  }
  def list = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    def c = Subscription.createCriteria()
    def subscriptionList = c.list(params) {
      subscriptionFor {
        party {
          idEq(UserTools.currentUser.party.id)
        }
      }
    }
    [subscriptionList: subscriptionList, subscriptionTotal: subscriptionList.size() ]
  }


}
