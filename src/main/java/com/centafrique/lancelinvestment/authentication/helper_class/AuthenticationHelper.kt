package com.centafrique.lancelinvestment.authentication.helper_class

import com.centafrique.lancelinvestment.authentication.entity.UserDetails
import com.centafrique.lancelinvestment.authentication.service_class.impl.UserDetailsServiceImpl
import org.springframework.security.core.context.SecurityContextHolder

class AuthenticationHelper {

    fun getAuthDetails(userDetailsServiceImpl: UserDetailsServiceImpl): UserDetails {

        val authentication = SecurityContextHolder.getContext().authentication
        val emailAddress = authentication.name
        return userDetailsServiceImpl.getUserDetailsByEmailAddress(emailAddress)

    }

}