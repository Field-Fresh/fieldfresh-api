package com.fieldfreshmarket.api.usecase.user

import com.fieldfreshmarket.api.data.user.CreateUserData
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateUserUsecase {

   @Autowired
   private lateinit var userProfileRepository: UsersRepository

   fun execute(data: CreateUserData): User {
      if (userProfileRepository.findByEmail(data.email) != null)
         throw IllegalArgumentException("A Profile with email ${data.email} already exists")
      data.cognitoSub?.let {
         if (userProfileRepository.findByCognitoSub(it) != null)
            throw IllegalArgumentException("A Profile with cognitoId $it already exists")
      }
      return User(
         email = data.email,
         cognitoSub = data.cognitoSub,
         verified = data.verified,
         firstName = data.firstName,
         lastName = data.lastName,
         phone = data.phone
      ).let {
         userProfileRepository.save(it)
      }
   }
}