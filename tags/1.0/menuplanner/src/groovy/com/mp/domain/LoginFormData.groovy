package com.mp.domain

import static com.mp.domain.TestConstants.*

class LoginFormData {
    String email
    String password

    public static LoginFormData getDefaultLoginFormData() {
        LoginFormData loginFormData = new LoginFormData()
        loginFormData.email = USER_1
        loginFormData.password = USER_PASSWORD
        return loginFormData
    }
}
