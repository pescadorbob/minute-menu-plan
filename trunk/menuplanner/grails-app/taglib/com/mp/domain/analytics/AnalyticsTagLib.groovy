package com.mp.domain.analytics

import com.mp.analytics.AppRequestCO
import com.mp.analytics.TestIntervalCO
import com.mp.analytics.AnalyticsService
import com.mp.analytics.AppRequest

class AnalyticsTagLib {

  static namespace = "analytics"
  AnalyticsService analyticsService

  def time = {attrs, body ->
      long endTime = System.currentTimeMillis()
      AppRequestCO req = request.getAttribute('appRequestCO')
      if(req && !request.'reset-scenario'){
        analyticsService.recordIntervalOut(endTime, req, "after-view")
        analyticsService.recordRequestDone(endTime, req)
        if(req.within.id) session.'active-scenario' = req.within
        TestIntervalCO controller = req?.calls.find {it.name == "controller"}
        TestIntervalCO view = req?.calls.find {it.name == "view"}
        TestIntervalCO afterView = req?.calls.find {it.name == "after-view"}
        TestIntervalCO requestTime = req?.calls.find {it.name == "request"}
        out << """<a href="${createLink(controller: 'appRequest', action: 'show', params: [uniqueId: req.uniqueId])}">
            ${controller?.total}+${view?.total}+${afterView?.total}=${requestTime?.total}ms
          </a>"""
        println("Request Total[${(requestTime?.total)}ms]");
      }
  }
  def profile = {attrs, body ->
    def name = attrs?.name
    def details = attrs?.details
    def now = System.currentTimeMillis()
    analyticsService.recordIntervalIn(now,request.'appRequestCO',name,details)
    out << body()
    now = System.currentTimeMillis()
    analyticsService.recordIntervalOut(now,request.'appRequestCO',name)
  }
  def recordIn = {attrs, body ->
    def name = attrs?.name
    def details = attrs?.details

    def now = System.currentTimeMillis()
    analyticsService.recordIntervalIn(now,request.'appRequestCO',name,details)
  }
  def recordOut = {attrs, body ->
    def name = attrs?.name
    
    def now = System.currentTimeMillis()
    analyticsService.recordIntervalOut(now,request.'appRequestCO',name)
  }

}
