package com.fieldfreshmarket.api.usecase.auth

import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.auth.SignInResponseData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RefreshUsecase {

   @Autowired
   private lateinit var cognitoUserClient: CognitoUserClient

   fun execute(token: String): SignInResponseData {
      val refreshedToken: CognitoJWT? = cognitoUserClient.refresh(token)
      return SignInResponseData(
         tokens = refreshedToken,
         verificationRequired = false
      )
   }
}