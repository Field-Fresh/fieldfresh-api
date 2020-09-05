package com.fieldfreshmarket.api.usecase.auth

import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.core.cognito.CognitoJwtProcessor
import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.user.CreateUserData
import com.fieldfreshmarket.api.data.user.UpdateUserData
import com.fieldfreshmarket.api.services.UserService
import com.fieldfreshmarket.api.usecase.user.UpdateUserUsecase
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ExchangeOathCodeUsecase {

   @Autowired
   private lateinit var cognitoUserClient: CognitoUserClient

   @Autowired
   private lateinit var cognitoJwtProcessor: CognitoJwtProcessor

   @Autowired
   private lateinit var updateUserUsecase: UpdateUserUsecase

   @Autowired
   private lateinit var userService: UserService

   fun execute(code: String): CognitoJWT {
      val cognitoJWT = cognitoUserClient.getToken(code)
      return cognitoJWT?.let {jwt ->
         val claims = cognitoJwtProcessor.extractIdentityClaims(jwt.id_token)

         val profile: User? =
            userService.getProfileBySub(claims.sub) ?:
               userService.getProfileByEmail(claims.email)

         profile?.let {
            updateUserUsecase.execute(
               userProfile = it,
               data = UpdateUserData(
                   email = claims.email,
                   firstName = claims.firstName,
                   lastName = claims.lastName
               )
            )
         } ?: userService.create(
            data = CreateUserData(
               firstName = claims.firstName,
               lastName = claims.lastName,
               cognitoSub = claims.sub,
               phone = null,
               email = claims.email,
               verified = true
            )
         )

         jwt
      } ?: throw IllegalStateException("Unable to exchange token")
   }
}