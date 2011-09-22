package com.mp.analytics

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import org.apache.commons.beanutils.BeanUtils

/**
 * Created on Jun 19, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 Access-Wealth, LLC
 * All rights reserved.
 */

public class AnalyticsService {

  def transactional = false
  def threadPool = Executors.newFixedThreadPool(4)

  public AppRequestCO recordRequestIn(long requestIn, String params, TestScenarioCO scenario) {
    if (!scenario) {
      def scenarioData
      try {
        scenarioData = TestScenario.find('from TestScenario s where s.active=:active', [active: true])
      } catch (Throwable t) {
        log.error("Couldn't retrieve any active Test Scenario", t)
      }
      if (!scenarioData) scenario = new TestScenarioCO(name: 'undefined', description: '...', notes: '', active: true)
      else scenario = new TestScenarioCO(id: scenarioData.id, name: scenarioData.name, description: scenarioData.description, notes: scenarioData.notes, active: true)
    }
    AppRequestCO r = new AppRequestCO(within: scenario)
    scenario.requests << r
    TestIntervalCO c = new TestIntervalCO(within: r, name: "request", inTime: requestIn, details: params)
    r.calls << c
    r
  }

  public void recordRequestDone(long requestOut, final AppRequestCO r) {
    if (r) {
      TestIntervalCO c = r.calls.find {call ->
        call.name.equals("request")
      }
      c.outTime = requestOut
      c.total = c.outTime - c.inTime

      runAsync {
        try {
          TestScenario ts
          if (!r.within.id) {
            ts = new TestScenario(name: r.within.name, description: r.within.description, notes: r.within.notes, active: r.within.active);
            ts.s()
            r.within.id = ts.id
          } else {
            ts = TestScenario.get(r.within.id)
          }
          TestScenarioCO scenarioCO = r.within
          AppRequest appRequest = new AppRequest(within: ts,uniqueId:r.uniqueId)
          appRequest.save()
          ts.addToRequests(appRequest)
          ts.save()
          r.calls.each {callCO ->
            TestInterval callData = new TestInterval(within: appRequest, name: callCO.name, inTime: callCO.inTime,
                    outTime: callCO.outTime, total: callCO.total, details: callCO.details)
            appRequest.addToCalls(callData)
            callData.save()
            appRequest.save()
          }
          
        } catch (e) {
          println(e + " unable to log timings")
        }
      }
    }
    
  }

  public AppRequestCO recordIntervalIn(long inp, AppRequestCO r, String name, String details) {
    if (r) {
      TestIntervalCO c = new TestIntervalCO(name: name, inTime: inp, details: details)
      c.within = r
      r.calls.add(c)
    }
    r
  }

  public AppRequestCO recordIntervalOut(long out, AppRequestCO r, String name) {
    if (r) {
      TestIntervalCO c = r.calls.find { it.name == name}
      if (c) {
        c.outTime = out
        c.total = c.outTime - c.inTime
      }
    }
    r
  }

}
