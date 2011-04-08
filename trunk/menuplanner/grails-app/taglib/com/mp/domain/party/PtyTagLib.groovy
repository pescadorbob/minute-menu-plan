package com.mp.domain.party

import com.mp.domain.Permission
import com.mp.tools.UserTools

public class PtyTagLib {
  static namespace = "pty"
  def permissionService

  def hasRole = {attrs, body ->
    def pty = attrs?.bean
    def role = attrs?.role
    if (UserTools.hasRole(role)) {
      out << body()
    }
  }

  def coachID = {attrs, body ->
    out << UserTools.getCurrentUser().party.uniqueId
  }


  def coaches = {attrs, body ->
    def var = attrs.var ? attrs.var : "coach"
    def status = attrs.status ? attrs.status : "index"
    def party = attrs.party ? attrs.party : null
    def coaches = []
    def now = new Date()
    DirectorCoach.withSession {
      coaches = DirectorCoach.findAllByFrumAndActiveFromLessThan(party.director, now)?.collect {it.to}
    }
    coaches.eachWithIndex {obj, index ->
      out << body((var): obj, (status): index)
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
        frum {
          party {
            idEq(party?.id)
          }
        }
        lt('activeFrom', now)
        or {
          isNull('activeTo')
          gt('activeTo', now)
        }
//        fetchMode('to', FetchMode.EAGER)
//        fetchMode('to.party', FetchMode.EAGER)
      }
    }
    relationships.each {
      out << body((lhs): "${it.to.type}:${it.to.party.name}", (rhs): "${it.frum.type}:(you)")
    }
  }
  def clients = {attrs, body ->
    def var = attrs.var ? attrs.var : "to"
    def status = attrs.status ? attrs.status : "index"
    def party = attrs.party ? attrs.party : null
    def clients = []
    CoachSubscriber.withSession {
      def now = new Date();
      clients = CoachSubscriber.findAllByFrumAndActiveFromLessThan(party.coach, now)?.collect {it.to}
    }
    clients.eachWithIndex {obj, index ->
      out << body((var): obj, (status): index)
    }
  }
  def clientCount = {attrs, body ->
    def party = attrs.party ? attrs.party : null
    def clients = 0
    CoachSubscriber.withSession {
      def now = new Date();
      def c = CoachSubscriber.createCriteria()
      clients = c.get {
          projections {
              countDistinct "to"
          }
          eq("frum",party.coach)
          lt("activeFrom",now)
      }
    }
    out << clients
  }
  def emailClients = {attrs ->
    def party = attrs.party ? attrs.party : null
    def clients = []
    CoachSubscriber.withSession {
      def now = new Date();
      clients = CoachSubscriber.findAllByFrumAndActiveFromLessThan(party.coach, now)?.collect {it.to}
    }
    out << "<input type=\"hidden\" value=\""
    clients.eachWithIndex {obj, index ->
      out << obj?.party?.email << ";"
    }
    out << "\">email all</a> "
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
