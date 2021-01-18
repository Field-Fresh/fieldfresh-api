package com.fieldfreshmarket.api.core.cognito.client

import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.core.cognito.client.models.CognitoUser
import org.springframework.boot.context.properties.bind.Bindable.mapOf
import org.springframework.http.HttpEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.*


class CognitoUserClient(
   private val identityProviderClient: CognitoIdentityProviderClient,
   private val cognitoProperties: CognitoProperties
) {

   private val token: String = "${cognitoProperties.cognitoUserDomain}/oauth2/token"
   fun signup(cognitoSignupInfo: CognitoSignupInfo): String {
      return identityProviderClient.signUp(
         SignUpRequest.builder()
            .clientId(cognitoProperties.cognitoUserClientId)
            .password(cognitoSignupInfo.password)
            .username(cognitoSignupInfo.email)
            .userAttributes(
               extractUserAttr(cognitoSignupInfo)
            ).build()
      ).userSub()
   }

   fun refresh(refreshToken: String): CognitoJWT {
      val authParams = mapOf(
          "REFRESH_TOKEN" to refreshToken
      )
      val authResponse: AdminInitiateAuthResponse = identityProviderClient.adminInitiateAuth(
          AdminInitiateAuthRequest.builder()
              .authFlow(AuthFlowType.REFRESH_TOKEN_AUTH)
              .userPoolId(cognitoProperties.cognitoUserPoolId)
              .clientId(cognitoProperties.cognitoUserClientId)
              .authParameters(authParams)
              .build()
      )

      val authenticationResult = authResponse.authenticationResult()
      return authenticationResult?.let {
         CognitoJWT(
             id_token = authenticationResult.idToken(),
             access_token = authenticationResult.accessToken(),
             refresh_token = authenticationResult.refreshToken(),
             expires_in = authenticationResult.expiresIn(),
             token_type = authenticationResult.tokenType()
         )
      } ?: throw IllegalStateException("Unable to use Refresh Token.")
   }

   fun changePassword(cognitoChangePasswordInfo: CognitoChangePasswordInfo): ChangePasswordResponse {
      return identityProviderClient.changePassword(
         ChangePasswordRequest.builder()
            .accessToken(cognitoChangePasswordInfo.token)
            .previousPassword(cognitoChangePasswordInfo.oldPassword)
            .proposedPassword(cognitoChangePasswordInfo.newPassword)
            .build()
      )
   }


   fun forgotPassword(cognitoForgotPasswordInfo: CognitoForgotPasswordInfo): ForgotPasswordResponse {
      return identityProviderClient.forgotPassword(
         ForgotPasswordRequest.builder()
            .clientId(cognitoProperties.cognitoUserClientId)
            .username(cognitoForgotPasswordInfo.email)
            .build()
      )
   }

   fun confirmForgotPassword(cognitoConfirmForgotPasswordInfo: CognitoConfirmForgotPasswordInfo): ConfirmForgotPasswordResponse {
      return identityProviderClient.confirmForgotPassword(
         ConfirmForgotPasswordRequest.builder()
            .clientId(cognitoProperties.cognitoUserClientId)
            .username(cognitoConfirmForgotPasswordInfo.email)
            .password(cognitoConfirmForgotPasswordInfo.newPassword)
            .confirmationCode(cognitoConfirmForgotPasswordInfo.code)
            .build())
   }

   fun update(info: CognitoUpdateInfo): AdminUpdateUserAttributesResponse {
      return identityProviderClient.adminUpdateUserAttributes(
         AdminUpdateUserAttributesRequest.builder()
            .userPoolId(cognitoProperties.cognitoUserPoolId)
            .username(info.email)
            .userAttributes(
               extractUpdateAttr(info)
            )
            .build()
      )
   }

   fun signin(cognitoSigninInfo: CognitoSigninInfo): CognitoJWT {
      val authParams = extractAuthParams(cognitoSigninInfo)
      val authResponse: InitiateAuthResponse = identityProviderClient.initiateAuth(
         InitiateAuthRequest.builder()
            .authFlow(AuthFlowType.USER_PASSWORD_AUTH)
            .clientId(cognitoProperties.cognitoUserClientId)
            .authParameters(authParams)
            .build()
      )

      val authenticationResult = authResponse.authenticationResult()
      return authenticationResult?.let {
         CognitoJWT(
            id_token = authenticationResult.idToken(),
            access_token = authenticationResult.accessToken(),
            refresh_token = authenticationResult.refreshToken(),
            expires_in = authenticationResult.expiresIn(),
            token_type = authenticationResult.tokenType()
         )
      } ?: throw IllegalStateException("Unable to Authenticate user: ${cognitoSigninInfo.email}")
   }

   fun signout(cognitoSignoutInfo: CognitoSignoutInfo): Boolean {
      try {
         val signoutResponse = identityProviderClient.adminUserGlobalSignOut(
            AdminUserGlobalSignOutRequest.builder()
               .userPoolId(cognitoProperties.cognitoUserPoolId)
               .username(cognitoSignoutInfo.email)
               .build()
         )
      } catch (e: Exception) {
         throw IllegalStateException("Signout Failed")
      }
      return true
   }

   fun fetchUser(acessToken: String): CognitoUser {
      val response: GetUserResponse = identityProviderClient.getUser(
         GetUserRequest.builder().accessToken(
            acessToken
         ).build()
      )
      val attrMap: Map<String, String> =
         response.userAttributes().map {
            it.name() to it.value()
         }.toMap()
      return CognitoUser(
         firstName = attrMap["given_name"],
         lastName = attrMap["family_name"],
         email = attrMap["email"] ?: error("Email must not be null"),
         username = response.username() ?: error("username must not be null"),
         sub = attrMap["sub"] ?: error("sub must not be null")
      )
   }

   fun verify(cognitoSignupConfirmationInfo: CognitoSignupConfirmationInfo) {
      identityProviderClient.confirmSignUp(
         ConfirmSignUpRequest.builder()
            .clientId(cognitoProperties.cognitoUserClientId)
            .username(cognitoSignupConfirmationInfo.email)
            .confirmationCode(cognitoSignupConfirmationInfo.code)
            .build()
      )
   }

   fun resendVerification(cognitoResendVerificationInfo: CognitoResendVerificationInfo) {
      val result = identityProviderClient.resendConfirmationCode(
         ResendConfirmationCodeRequest
            .builder()
            .clientId(cognitoProperties.cognitoUserClientId)
            .username(cognitoResendVerificationInfo.email)
            .build()
      )
   }

   fun getToken(
      code: String
   ): CognitoJWT? {
      val client = RestTemplate()
      val headers = LinkedMultiValueMap<String, String>()
      headers.add("Content-Type", "application/x-www-form-urlencoded")
      val req = HttpEntity(null, headers)
      val url = "$token?grant_type=authorization_code&client_id=${cognitoProperties.cognitoUserClientId}&code=$code&redirect_uri=${cognitoProperties.cognitoCallbackUrl}"
      return client.postForObject(url, req, CognitoJWT::class.java)
   }

   private fun extractAuthParams(cognitoSigninInfo: CognitoSigninInfo): Map<String, String?> =
      cognitoSigninInfo.let {
         mapOf(
            "USERNAME" to it.email,
            "PASSWORD" to it.password
         )
      }

   private fun extractUserAttr(cognitoSignupInfo: CognitoSignupInfo): List<AttributeType> =
      listOf(
         AttributeType.builder().name("email").value(cognitoSignupInfo.email).build()
      )

   private fun extractUpdateAttr(cognitoUpdateInfo: CognitoUpdateInfo): List<AttributeType> =
      listOfNotNull(
         cognitoUpdateInfo.firstName?.let {
            AttributeType.builder()
               .name("given_name")
               .value(it)
               .build()
         },
         cognitoUpdateInfo.lastName?.let {
            AttributeType.builder()
               .name("family_name")
               .value(cognitoUpdateInfo.lastName ?: "")
               .build()
         },
         cognitoUpdateInfo.phone?.let {
            AttributeType.builder()
               .name("phone_number")
               .value(cognitoUpdateInfo.phone ?: "")
               .build()
         }
      )
}