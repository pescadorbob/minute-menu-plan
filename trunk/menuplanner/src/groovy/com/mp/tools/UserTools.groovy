package com.mp.tools

import com.mp.domain.party.Party
import com.mp.domain.LoginCredential
import com.mp.domain.SessionUtils

/**
 * Created on Nov 29, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of Access-Wealth, LLC.
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 Access-Wealth, LLC.
 * All rights reserved.
 */
 
public class UserTools {

    static LoginCredential getCurrentUser() {
        Long userId = SessionUtils?.session?.loggedUserId?.toLong()
        LoginCredential current
        if (userId) {
            LoginCredential.withSession {
                Party currentParty = com.mp.domain.party.Party.get(userId)
                if (currentParty) {
                    current = LoginCredential.findByParty(currentParty)
                }
            }
        }
        return current
    }
}
