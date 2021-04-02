package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoSignupInfo
import com.fieldfreshmarket.api.data.proxy.CreateProxyData

data class SignUpData (
   override val email: String,
   override val password: String,
   val proxy: CreateProxyData,
   val firstName: String? = null,
   val lastName: String? = null,
   val phone: String? = null
): CognitoSignupInfo