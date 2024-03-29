package com.fieldfreshmarket.api.usecase.auth

import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.auth.SignInData
import com.fieldfreshmarket.api.data.auth.SignInResponseData
import com.fieldfreshmarket.api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.fieldfreshmarket.api.model.Proxy
import javax.persistence.EntityNotFoundException

@Component
class SigninUsecase {

   @Autowired
   private lateinit var cognitoUserClient: CognitoUserClient

   @Autowired
   private lateinit var userService: UserService

   fun execute(data: SignInData): SignInResponseData {
      val profile = userService.getProfileByEmail(data.email)
         ?: throw EntityNotFoundException("No profile find with email ${data.email}.")

      val token: CognitoJWT?

      val defaultProxy: Proxy = profile.proxies.firstOrNull() ?: throw IllegalStateException("Default Proxy is null on signin for user ${profile.id}")

      token = if (profile.verified) {
         cognitoUserClient.signin(data)
      } else {
         null
      }

      return SignInResponseData(
         tokens = token,
         verificationRequired = token == null,
         user = token?.let { profile },
         defaultProxy = defaultProxy
      )
   }
}