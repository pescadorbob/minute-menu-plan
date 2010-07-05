package com.mp.domain


class FacebookProfileSyncJob {
    static triggers = {
    cron name: 'myTrigger', cronExpression: "0 0 2 * * ?", startDelay: 60*1000
  }
    def userService
    def execute() {
        println "Executing Facebook Profile Sync Job"
        List<User> users = User.findAllByUidIsNotNullAndFbOauthTokenIsNotNull()
        users?.each{User user ->
            userService.updateProfile(user)

        }
    }
}
