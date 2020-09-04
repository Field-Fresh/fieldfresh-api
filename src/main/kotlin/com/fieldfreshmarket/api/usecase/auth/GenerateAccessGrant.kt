package com.fieldfreshmarket.api.usecase.auth

import com.fieldfreshmarket.api.core.cognito.CognitoAccessTokenClaims
import com.fieldfreshmarket.api.core.cognito.CognitoAuthToken
import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.core.cognito.client.models.CognitoUser
import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.services.UserService
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class GenerateAccessGrant {

   @Autowired
   private lateinit var userService: UserService

   @Autowired
   private lateinit var cognitoUserClient: CognitoUserClient

   fun execute(): AccessGrant {
      val token: CognitoAuthToken = getToken()
      val cognitoUser: CognitoUser
      try {
         cognitoUser = cognitoUserClient.fetchUser(token.token)
      } catch (e: Exception) {
         throw AccessDeniedException("Access token is not valid")
      }
      val tokenClaims: CognitoAccessTokenClaims = token.principal
      if (tokenClaims.sub != cognitoUser.sub)
         throw AccessDeniedException("Access token is not valid")

      val profile: User = userService.getProfileBySub(tokenClaims.sub)
         ?: throw IllegalStateException("User Profile not found for sub: ${tokenClaims.sub}")
      return AccessGrant(
         user = profile,
         accessTokenClaims = tokenClaims
      )
   }

   private fun getToken(): CognitoAuthToken {
      val authentication = SecurityContextHolder.getContext().authentication
      return authentication as CognitoAuthToken
   }
}