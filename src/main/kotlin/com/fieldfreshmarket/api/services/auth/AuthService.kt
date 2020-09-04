package com.fieldfreshmarket.api.services.auth

import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.auth.*
import com.fieldfreshmarket.api.usecase.auth.*
import com.fieldfreshmarket.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AuthService {

   @Autowired
   private lateinit var cognitoUserClient: CognitoUserClient

   @Autowired
   private lateinit var signinUsecase: SigninUsecase

   @Autowired
   private lateinit var verifyCodeUsecase: VerifyCodeUsecase

   @Autowired
   private lateinit var signupUsecase: SignupUsecase

   @Autowired
   private lateinit var forgotPasswordUsecase: ForgotPasswordUsecase

   @Autowired
   private lateinit var confirmForgotPasswordUsecase: ConfirmForgotPasswordUsecase

   @Autowired
   private lateinit var exchangeOathCodeUsecase: ExchangeOathCodeUsecase

   @Autowired
   private lateinit var generateAccessGrant: GenerateAccessGrant

   fun signin(signInData: SignInData): SignInResponseData {
      return signinUsecase.execute(signInData)
   }

   fun signout(signoutData: SignOutData): Boolean =
      cognitoUserClient.signout(signoutData)

   fun signup(signUpData: SignUpData): User =
      signupUsecase.execute(signUpData)

   fun forgotPassword(forgotPasswordData: ForgotPasswordData): User =
       forgotPasswordUsecase.execute(forgotPasswordData)

   fun confirmForgotPassword(confirmForgotPasswordData: ConfirmForgotPasswordData): User =
       confirmForgotPasswordUsecase.execute(confirmForgotPasswordData)

   fun verify(data: SignUpConfirmationData): VerificationResponseData {
      return verifyCodeUsecase.execute(data)
   }

   fun resendVerification(data: ResendVerificationData) {
      return cognitoUserClient.resendVerification(data)
   }

   fun getToken(code: String): CognitoJWT? =
      exchangeOathCodeUsecase.execute(code)

   fun getGrantForAuth(): AccessGrant =
      generateAccessGrant.execute()

}