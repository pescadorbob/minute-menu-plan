package com.mp.domain

class SubAffiliateFormData {
    String email
    String password
    String confirmPassword
    String name
    boolean isEnabled

    public static SubAffiliateFormData getDefaultSubAffiliateFormData() {
        SubAffiliateFormData subAffiliateFormData = new SubAffiliateFormData()
        subAffiliateFormData.email = "qa.menuplanner+${System.currentTimeMillis()}@gmail.com"
        subAffiliateFormData.password = "1234"
        subAffiliateFormData.confirmPassword = "1234"
        subAffiliateFormData.name = "sub affiliate user"
        subAffiliateFormData.isEnabled = true
        return subAffiliateFormData
    }
}