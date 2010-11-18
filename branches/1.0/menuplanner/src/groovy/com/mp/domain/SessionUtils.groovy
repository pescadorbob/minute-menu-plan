package com.mp.domain

import org.springframework.web.context.request.RequestContextHolder

class SessionUtils {
	public static session
    public static request

    // method for setting session during tests
    public static void setSession(def mockSession) {
        session = mockSession
    }

    // method for setting request during tests
	public static void setRequest(def mockRequest) {
		request = mockRequest
	}

	public static def getSession() {
		if (session) {
			return session
		}

		return RequestContextHolder?.currentRequestAttributes()?.getSession()
	}

    public static def getRequest() {
        if (request) {
            return request
        }
        return RequestContextHolder?.currentRequestAttributes()?.getCurrentRequest()
    }
}
