package com.fieldfreshmarket.api.usecase.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.auth.SignUpData
import com.fieldfreshmarket.api.services.UserService
import com.fieldfreshmarket.api.data.user.CreateUserData
import com.fieldfreshmarket.api.data.user.UpdateUserData
import com.fieldfreshmarket.api.usecase.user.UpdateUserUsecase
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SignupUsecase {

   @Autowired
   private lateinit var cognitoUserClient: CognitoUserClient

   @Autowired
   private lateinit var userService: UserService

   @Autowired
   private lateinit var updateUserUsecase: UpdateUserUsecase

   fun execute(data: SignUpData): User {
      val profile = userService.getProfileByEmail(data.email)

      if (profile?.cognitoSub != null)
         throw IllegalArgumentException("User with email: ${data.email} already exists")

      val cognitoSub = cognitoUserClient.signup(cognitoSignupInfo = data)

      return profile?.let {
         updateUserUsecase.execute(
            userProfile = it,
            data = UpdateUserData(
               email = data.email
            )
         )
      } ?: userService.create(
         data = CreateUserData(
            email = data.email,
            verified = false,
            cognitoSub = cognitoSub,
            firstName = null,
            lastName = null,
            phone = null
         )
      )
   }
}