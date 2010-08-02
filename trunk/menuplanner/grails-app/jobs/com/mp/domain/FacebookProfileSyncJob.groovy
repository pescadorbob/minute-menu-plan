package com.mp.domain


class FacebookProfileSyncJob {
    static triggers = {
        cron name: 'myTrigger', cronExpression: "0 0 2 * * ?", startDelay: 60 * 1000
    }
    def userService

    def execute() {
        println "Executing Facebook Profile Sync Job"
        List facebookUsers = FacebookAccount.list()
        if (facebookUsers) {
            List<Subscriber> users = facebookUsers*.party.subscriber
            users?.each {Subscriber user ->
                userService.updateProfile(user)
            }
        }

    }
}
