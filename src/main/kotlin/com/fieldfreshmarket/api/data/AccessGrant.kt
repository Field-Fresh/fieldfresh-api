package com.fieldfreshmarket.api.data

import com.fieldfreshmarket.api.core.cognito.CognitoAccessTokenClaims
import com.fieldfreshmarket.api.model.User

abstract class Grant(val user: User)

class AccessGrant(
    user: User,
    val accessTokenClaims: CognitoAccessTokenClaims
) : Grant(user)

class OfflineGrant(
    user: User
): Grant(user)



