package com.fieldfreshmarket.api.controller.response.auth

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.data.auth.SignInResponseData
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.view.UserView

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class SignInResponse (
   data: SignInResponseData
) {
   val cognitoJWT: CognitoJWT? = data.tokens
   val requireVerification: Boolean = data.verificationRequired
   val user: UserView? = data.user?.let{ UserView(it) }
   val defaultProxy: String? = data.defaultProxy?.id
}