package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.model.Proxy

data class SignInResponseData(
   val tokens: CognitoJWT?,
   val verificationRequired: Boolean,
   val user: User? = null,
   val defaultProxy: Proxy? = null
)