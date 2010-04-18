package com.mp.domain

class CalendarController {

    def index = {

    }

    def calendar = {
        List<Week> weeks = Week.list()
        render(view: 'calendar', model:[ weeks: weeks])
    }


}
