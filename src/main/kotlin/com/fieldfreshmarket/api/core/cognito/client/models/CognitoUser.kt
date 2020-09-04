package com.fieldfreshmarket.api.core.cognito.client.models

data class CognitoUser(
   val firstName: String?,
   val lastName: String?,
   val email: String,
   val username: String,
   val sub: String
)