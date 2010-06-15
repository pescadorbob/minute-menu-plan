package com.mp.domain

class LoginFormData {
    String email
    String password

    public static LoginFormData getDefaultLoginFormData() {
        LoginFormData loginFormData = new LoginFormData()
        loginFormData.email = "qa.menuplanner+user1@gmail.com"
        loginFormData.password = "1234"
        return loginFormData
    }
}
