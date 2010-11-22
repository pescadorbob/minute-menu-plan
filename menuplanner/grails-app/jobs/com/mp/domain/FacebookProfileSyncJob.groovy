package com.mp.domain

import com.mp.domain.party.Party


class FacebookProfileSyncJob {
    static triggers = {
        cron name: 'myTrigger', cronExpression: "0 0 2 * * ?", startDelay: 60 * 1000
    }
    def userService

    def execute() {
        println "Executing Facebook Profile Sync Job"
        List facebookUsers = FacebookAccount.list()
        if (facebookUsers) {
            List<Party> parties = facebookUsers*.party
            parties?.each {Party party ->
                userService.updateProfile(party)
            }
        }

    }
}
