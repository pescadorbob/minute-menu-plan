package com.mp.domain.party

import com.mp.domain.party.DirectorCoach
import com.mp.domain.party.CoachSubscriber
import com.mp.domain.Permission
import com.mp.domain.PartyRoleType
import org.hibernate.FetchMode
import com.mp.tools.UserTools

public class PtyTagLib {
  static namespace = "pty"
  def permissionService

  def hasRole = {attrs, body ->
    def pty = attrs?.bean
    def role = attrs?.role
    if(UserTools.hasRole(role)){
      out << body()
    }
  }
  def coachID = {attrs, body ->
    out << UserTools.getCurrentUser().party.uniqueId
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
  def relationships = {attrs, body ->
    def rhs = attrs.rhs ? attrs.rhs : "rhs"
    def lhs = attrs.lhs ? attrs.lhs : "lhs"
    def party = attrs.party ? attrs.party : null
    def relationships = []
    def now = new Date();

    PartyRelationship.withSession {
      def c = PartyRelationship.createCriteria();
      relationships = c.list {
        supplier {
          party {
            idEq(party?.id)
          }
        }
        lt('activeFrom',now)
        or {
          isNull('activeTo')
          gt('activeTo',now)
        }
//        fetchMode('client', FetchMode.EAGER)
//        fetchMode('client.party', FetchMode.EAGER)
      }
    }
    relationships.each {
      out << body((lhs):"${it.client.type}:${it.client.party.name}", (rhs):"${it.supplier.type}:(you)")
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
