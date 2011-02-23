package com.mp.tools
/**
 * Created on Dec 29, 2010
 * Created by Brent Fisher
 *
 * This file contains proprietary information of ${COMPANY}
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2010 ${COMPANY}
 * All rights reserved.
 */
 
public class WcmSecurityDelegate {

  static def delegate = [
            getUserName: {->
              UserTools.getCurrentUser()?.email
            },
            getUserEmail: {->
              UserTools.getCurrentUser()?.email
            },
            getUserRoles: {->
              UserTools.getCurrentUser()?.party?.roles.collect{
                it
              }
            },
            getUserPrincipal: {->
              [id: UserTools.getCurrentUser()?.id, name: UserTools.getCurrentUser()?.party.name, email: UserTools.getCurrentUser()?.email]
            }
    ]
}