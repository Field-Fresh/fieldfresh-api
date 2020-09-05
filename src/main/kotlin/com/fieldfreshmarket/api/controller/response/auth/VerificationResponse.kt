package com.fieldfreshmarket.api.controller.response.auth

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.data.auth.VerificationResponseData
import com.fieldfreshmarket.api.view.UserView

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class VerificationResponse (
   data: VerificationResponseData
) {
   val cognitoJWT: CognitoJWT? = data.tokens
   val userProfile: UserView = UserView(data.user)
}