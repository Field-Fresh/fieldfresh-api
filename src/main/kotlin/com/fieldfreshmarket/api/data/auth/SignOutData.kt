package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoSignoutInfo

data class SignOutData (
   override val email: String
): CognitoSignoutInfo