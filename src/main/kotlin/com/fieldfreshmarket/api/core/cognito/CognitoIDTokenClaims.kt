package com.fieldfreshmarket.api.core.cognito

import java.util.*

data class CognitoIDTokenClaims (
   val tokenUse: String,
   val issuer: String,
   val issued: Date,
   val expire: Date,
   val firstName: String,
   val lastName: String,
   val email: String,
   val sub: String
)