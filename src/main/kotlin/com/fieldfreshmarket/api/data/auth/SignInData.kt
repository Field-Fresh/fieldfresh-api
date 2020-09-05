package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoSigninInfo

data class SignInData (
   override val email: String,
   override val password: String
): CognitoSigninInfo