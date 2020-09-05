package com.fieldfreshmarket.api.usecase.user

import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.user.ChangePasswordData
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ChangePasswordUsecase {
  @Autowired
  private lateinit var repository: UsersRepository

  @Autowired
  private lateinit var cognitoUserClient: CognitoUserClient


  fun execute(data: ChangePasswordData): User {
    val profile = repository.findByCognitoSub(data.sub)
       ?: throw IllegalArgumentException("User with sub: ${data.sub} does not exists in db. Password change failed.")
    cognitoUserClient.changePassword(data)
    return profile
  }
}