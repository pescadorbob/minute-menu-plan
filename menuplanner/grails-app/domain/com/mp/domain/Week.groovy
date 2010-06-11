package com.mp.domain

class Week {

    List<Day> days = []

    static belongsTo = [menuPlan: MenuPlan]
    static hasMany = [days: Day]

    static constraints = {
    }

    public Week shallowClone(MenuPlan menuPlan) {
        Week week = new Week(menuPlan: menuPlan)
        week.days = []
        this.days.each {Day day ->
            week.days += day.shallowClone(week)
        }
        return week
    }

    boolean equals(o) {
        if (this.is(o)) return true;

        if (!o || getClass() != o.class) return false;

        Week week = (Week) o;

        if (!id.equals(week.id)) return false;

        return true;
    }

}
