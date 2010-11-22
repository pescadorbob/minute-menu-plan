package com.mp.domain

public class CoachFormData {
    String email
    String password
    String confirmPassword
    String name
    boolean isEnabled

    public static CoachFormData getDefaultCoachFormData() {
        CoachFormData coachFormData = new CoachFormData()
        coachFormData.email = "qa.menuplanner+${System.currentTimeMillis()}@gmail.com"
        coachFormData.password = "1234"
        coachFormData.confirmPassword = "1234"
        coachFormData.name = "sub director user"
        coachFormData.isEnabled = true
        return coachFormData
    }
}