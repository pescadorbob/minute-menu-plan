package com.mp.domain.party

import java.text.SimpleDateFormat

class DirectorCoachController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index = {
    redirect(action: "list", params: params)
  }
  def showCoaches = {
    GregorianCalendar c = new GregorianCalendar()
    c.set(Calendar.AM_PM, Calendar.AM)
    c.set(Calendar.HOUR, 0) //set the AM_PM and HOUR fields...
    c.set(Calendar.MINUTE, 00)
    c.set(Calendar.SECOND, 0)
    def from = params.from ? params.from : new Date(c.timeInMillis) - 30
    def thru = params.thru ? params.thru : new Date(c.timeInMillis)

    def nameFilter = params.nameFilter ? params.nameFilter : ""
    def criteria = DirectorCoach.createCriteria();
    def directorCoachList = criteria.list {
      gt("activeFrom", from)
      lt("activeFrom", thru)
      gt('commission',0.0f)
      client {
        party {
          idEq(params.long('id'))
        }
      }
      supplier {
        party {
          like('name', "%${nameFilter}%")
        }
      }
    }
    [party: Party.get(params.id), directorCoachList: directorCoachList, from: from, thru: thru, nameFilter: nameFilter]
  }
  def list = {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [directorCoachList: DirectorCoach.list(params), directorCoachTotal: DirectorCoach.count()]
  }

  def create = {
    def directorCoach = new DirectorCoach()
    directorCoach.properties = params
    return [directorCoach: directorCoach]
  }

  def save = {
    def directorCoach = new DirectorCoach(params)
    if (directorCoach.save(flush: true)) {
      flash.message = "${message(code: 'default.created.message', args: [message(code: 'directorCoach.label', default: 'DirectorCoach'), directorCoach.id])}"
      redirect(action: "show", id: directorCoach.id)
    }
    else {
      render(view: "create", model: [directorCoach: directorCoach])
    }
  }

  def show = {
    def directorCoach = DirectorCoach.get(params.id)
    if (!directorCoach) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'directorCoach.label', default: 'DirectorCoach'), params.id])}"
      redirect(action: "list")
    }
    else {
      [directorCoach: directorCoach]
    }
  }

  def edit = {
    def directorCoach = DirectorCoach.get(params.id)
    if (!directorCoach) {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'directorCoach.label', default: 'DirectorCoach'), params.id])}"
      redirect(action: "list")
    }
    else {
      return [directorCoach: directorCoach]
    }
  }

  def update = {
    def directorCoach = DirectorCoach.get(params.id)
    if (directorCoach) {
      if (params.version) {
        def version = params.version.toLong()
        if (directorCoach.version > version) {

          directorCoach.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'directorCoach.label', default: 'DirectorCoach')] as Object[], "Another user has updated this DirectorCoach while you were editing")
          render(view: "edit", model: [directorCoach: directorCoach])
          return
        }
      }
      directorCoach.properties = params
      if (!directorCoach.hasErrors() && directorCoach.save(flush: true)) {
        flash.message = "${message(code: 'default.updated.message', args: [message(code: 'directorCoach.label', default: 'DirectorCoach'), directorCoach.id])}"
        redirect(action: "show", id: directorCoach.id)
      }
      else {
        render(view: "edit", model: [directorCoach: directorCoach])
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'directorCoach.label', default: 'DirectorCoach'), params.id])}"
      redirect(action: "list")
    }
  }

  def delete = {
    def directorCoach = DirectorCoach.get(params.id)
    if (directorCoach) {
      try {
        directorCoach.delete(flush: true)
        flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'directorCoach.label', default: 'DirectorCoach'), params.id])}"
        redirect(action: "list")
      }
      catch (org.springframework.dao.DataIntegrityViolationException e) {
        flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'directorCoach.label', default: 'DirectorCoach'), params.id])}"
        redirect(action: "show", id: params.id)
      }
    }
    else {
      flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'directorCoach.label', default: 'DirectorCoach'), params.id])}"
      redirect(action: "list")
    }
  }
}
