package com.fieldfreshmarket.api.data.auth

import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.model.User

data class VerificationResponseData(
   val tokens: CognitoJWT?,
   val user: User
)