package com.mp.domain.themes


class HomePage {

    String name
    String leftBar
    String centralText
    String categories
    Date activeTo
    Date activeFrom
    Date lastModified =new Date()

    static constraints = {
        name(unique: true, blank: false)
        leftBar(nullable: true, blank: true, maxSize: 5000)
        categories(nullable: true, blank: true, maxSize: 5000)
        centralText(nullable: true, blank: true, maxSize: 5000)
        lastModified(nullable: true, blank: true)
        activeTo(nullable: true, blank: true)
    }

}
