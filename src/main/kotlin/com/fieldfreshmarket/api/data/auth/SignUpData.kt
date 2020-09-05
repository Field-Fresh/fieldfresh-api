package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoSignupInfo

data class SignUpData (
   override val email: String,
   override val password: String
): CognitoSignupInfo