package com.mp.domain.party

import com.mp.domain.party.DirectorCoach
import com.mp.domain.party.CoachSubscriber
import com.mp.domain.Permission

public class PtyTagLib {
  static namespace = "pty"
  def permissionService

  def hasRole = {attrs, body ->
    if (log.isDebugEnabled()) {
      log.debug "Checking hasRole"
    }
    def pty = attrs?.bean
    def retVal = false
    if (pty) {
      retVal = pty?.roles?.findAll {
        if (log.isDebugEnabled()) {
          log.debug "Comparing [${it.type}] to [${attrs.role}] got:${it.type == attrs.role}"
        }
        it.type == attrs.role
      }?.size() > 0

    }
    if (log.isDebugEnabled()) {
      log.debug "returning ${retVal}"
    }
    if (retVal) out << body()
  }
  def coaches = {attrs, body ->
    def var = attrs.var ? attrs.var : "coach"
    def party = attrs.party ? attrs.party : null
    def coaches = []
    def now = new Date();
    DirectorCoach.withSession {
      def c = DirectorCoach.createCriteria();
      coaches = c.list {
        client {
          idEq(party?.id)
        }
      }.findAll {
        it.activeFrom < now
      }.collect {
        it.supplier
      }
    }
    coaches.each {
      out << body((var): it)
    }
  }
  def clients = {attrs, body ->
    def var = attrs.var ? attrs.var : "client"
    def party = attrs.party ? attrs.party : null
    def clients = []
    CoachSubscriber.withSession {
      def c = CoachSubscriber.createCriteria();
      def now = new Date();
      clients = c.list {
        client {
          idEq(party?.id)
        }
//        ge(activeFrom:now)
      }.findAll {
        it.activeFrom < now
      }.collect {
        it.supplier
      }
    }
    clients.each {
      out << body((var): it)
    }
  }
  def hasPermission = {attrs, body ->
    if (log.isDebugEnabled()) {
      log.debug "Checking permission:${attrs?.permission}"
    }
    Permission permission = attrs['permission']
    def pty = attrs?.bean
    if (permission && permissionService.hasPermission(permission, null, pty)) {
      out << body()
    }
  }
}
