package com.mp.domain

class UserFormData {
    String email
    String password
    String confirmPassword
    String name
    String city
    String mouthsToFeed
    String introduction
    boolean isEnabled
    boolean isSuperAdmin
    boolean isAdmin
    boolean isUser

    public static UserFormData getDefaultUserFormData() {
        UserFormData userFormData = new UserFormData()
        userFormData.email = "qa.menuplanner+${System.currentTimeMillis()}@gmail.com"
        userFormData.password = "1234"
        userFormData.confirmPassword = "1234"
        userFormData.name = "testuser1"
        userFormData.city = "city"
        userFormData.mouthsToFeed = "6"
        userFormData.introduction = "Some description"
        userFormData.isEnabled = true
        userFormData.isSuperAdmin = false
        userFormData.isAdmin = false
        userFormData.isUser = true
        return userFormData
    }
}