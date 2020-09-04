package com.fieldfreshmarket.api.usecase.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.auth.ForgotPasswordData
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ForgotPasswordUsecase {

  @Autowired
  private lateinit var repository: UsersRepository

  @Autowired
  private lateinit var cognitoUserClient: CognitoUserClient


  fun execute(data: ForgotPasswordData): User {
    val profile = repository.findByEmail(data.email)
       ?: throw IllegalArgumentException("User with email: ${data.email} does not exists. Password reset faild.")
    if(profile.verified.equals(false)){
      throw IllegalArgumentException("Email: ${data.email} needs to be verified to reset password.")
    }
    cognitoUserClient.forgotPassword(data)
    return profile
  }
}
