package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoSignupConfirmationInfo

data class SignUpConfirmationData (
   override val email: String,
   override val code: String,
   override val password: String?
): CognitoSignupConfirmationInfo