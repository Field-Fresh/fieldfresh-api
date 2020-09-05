package com.fieldfreshmarket.api.data

import com.fieldfreshmarket.api.core.cognito.CognitoAccessTokenClaims
import com.fieldfreshmarket.api.model.User

data class AccessGrant(
   val user: User,
   val accessTokenClaims: CognitoAccessTokenClaims
)