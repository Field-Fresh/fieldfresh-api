package com.fieldfreshmarket.api.core.cognito

import java.util.*

data class CognitoAccessTokenClaims (
   val tokenUse: String,
   val issuer: String,
   val issued: Date,
   val expire: Date,
   val email: String,
   val sub: String,
   val token: String
)