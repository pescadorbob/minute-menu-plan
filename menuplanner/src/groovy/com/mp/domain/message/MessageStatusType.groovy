package com.mp.domain.message

/**
 * Created on Apr 8, 2011
 * Created by Brent Fisher
 *
 * This file contains proprietary information of COMPANY
 * Copying or reproduction without prior written approval is prohibited.
 * (c) Copyright 1998-2011 COMPANY
 * All rights reserved.
 */
 public enum MessageStatusType {
    READ('READ'),
    SENT('SENT')

    final String name

    MessageStatusType(String name) {
        this.name = name
    }


   public String toString() {
        return name
    }
}