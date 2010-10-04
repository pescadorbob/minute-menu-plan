package com.mp.domain

class HomePage {

    String leftBar
    String testimonial
    String centralText
    String categories

    static constraints = {
        leftBar(nullable: true, blank: true, maxSize: 5000)
        testimonial(nullable: true, blank: true, maxSize: 5000)
        categories(nullable: true, blank: true, maxSize: 5000)
        centralText(nullable: true, blank: true, maxSize: 5000)
    }
}
