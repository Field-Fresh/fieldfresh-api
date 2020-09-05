package com.fieldfreshmarket.api.usecase.user

import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.user.UpdateUserData
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UpdateUserUsecase {

   @Autowired
   private lateinit var repository: UsersRepository

   @Autowired
   private lateinit var cognitoUserClient: CognitoUserClient


   fun execute(userProfile: User, data: UpdateUserData): User {
      if (userProfile.cognitoSub == null)
         throw IllegalArgumentException("User with email: ${userProfile.email} does not exists and can not be updated")
      //update cognito attributes here
      cognitoUserClient.update(data)

      return userProfile.apply {
         firstName = data.firstName ?: firstName
         lastName = data.lastName ?: lastName
         phone = data.phone ?: phone
      }.let {
         repository.save(it)
      }
   }
}