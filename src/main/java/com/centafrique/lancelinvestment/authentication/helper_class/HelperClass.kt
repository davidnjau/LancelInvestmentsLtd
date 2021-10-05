package com.centafrique.lancelinvestment.authentication.helper_class

import com.centafrique.lancelinvestment.authentication.entity.UserDetails


data class Results(
    val statusCode: Int,
    val details: Any
)
data class ErrorMessage(
    val error: String
)
data class SuccessfulLogin(
    val userDetails: UserDetails,
    val accessToken : String
)