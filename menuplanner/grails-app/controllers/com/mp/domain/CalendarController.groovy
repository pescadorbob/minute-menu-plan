package com.mp.domain

class CalendarController {

    def index = {

    }

    def calendar = {
        List<Week> weeks = Week.list()
//        println "Weeks: ${weeks}"
        render(view: 'calendar', model:[ weeks: weeks])
    }


}
