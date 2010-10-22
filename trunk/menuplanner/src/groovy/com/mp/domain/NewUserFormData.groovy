package com.mp.domain

class NewUserFormData {
    String name
    String city
    String email
    String password
    String confirmPassword
    Integer mouthsToFeed

    public static NewUserFormData getDefaultNewUserFormData() {
        NewUserFormData newUserFormData = new NewUserFormData()
        newUserFormData.email = "qa.menuplanner+${System.currentTimeMillis()}@gmail.com"
        newUserFormData.password = "1234"
        newUserFormData.confirmPassword = "1234"
        newUserFormData.name = "Menuplanner User"
        newUserFormData.city = "city"
        newUserFormData.mouthsToFeed = 4
        return newUserFormData
    }
}
