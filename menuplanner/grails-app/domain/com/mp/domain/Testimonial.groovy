package com.mp.domain

class Testimonial {
    String description
    Boolean showOnHomepage = false

    static constraints = {
        description(nullable: false, blank: false, maxSize: 5000)
    }

    String toString() {
        return description
    }
}
