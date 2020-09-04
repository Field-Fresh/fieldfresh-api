package com.fieldfreshmarket.api.usecase.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.auth.ConfirmForgotPasswordData
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ConfirmForgotPasswordUsecase {

  @Autowired
  private lateinit var repository: UsersRepository

  @Autowired
  private lateinit var cognitoUserClient: CognitoUserClient


  fun execute(data: ConfirmForgotPasswordData): User {
    val profile = repository.findByEmail(data.email)
       ?: throw IllegalArgumentException("User with email: ${data.email} does not exists. Password reset verification faild.")
    cognitoUserClient.confirmForgotPassword(data)
    return profile
  }
}