package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoSignupInfo

data class SignUpData (
   override val email: String,
   override val password: String,
   val firstName: String? = null,
   val lastName: String? = null,
   val phone: String? = null
): CognitoSignupInfo