package com.fieldfreshmarket.api.usecase.auth

import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.auth.SignInData
import com.fieldfreshmarket.api.data.auth.SignUpConfirmationData
import com.fieldfreshmarket.api.data.auth.VerificationResponseData
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.usecase.auth.SigninUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityNotFoundException

@Component
class VerifyCodeUsecase {

   @Autowired
   private lateinit var cognitoUserClient: CognitoUserClient

   @Autowired
   private lateinit var signinUsecase: SigninUsecase

   @Autowired
   private lateinit var repository: UsersRepository

   fun execute(data: SignUpConfirmationData): VerificationResponseData {
      val profile = repository.findByEmail(data.email)
         ?: throw EntityNotFoundException()
      cognitoUserClient.verify(data)
      profile.verified = true
      repository.save(profile)
      if(data.password != null) {
         return signinUsecase.execute(
             data = SignInData(
                 email = data.email,
                 password = data.password
             )
         ).let {
            VerificationResponseData(
               tokens = it.tokens,
               user = it.user!!
            )
         }
      }
      else {
         return VerificationResponseData(
            tokens = null,
            user = profile
         )
      }
   }
}