package com.fieldfreshmarket.api.view

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.core.cognito.CognitoJWT

@JsonSerialize
class CognitoJWTView (
   cognitoJWT: CognitoJWT?
) {
   val idToken: String? = cognitoJWT?.id_token
   val accessToken: String? = cognitoJWT?.access_token
   val refreshToken: String? = cognitoJWT?.refresh_token
   val expiresTn: Int? = cognitoJWT?.expires_in
   val tokenType: String? = cognitoJWT?.token_type
}