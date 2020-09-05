package com.fieldfreshmarket.api.config

import com.fieldfreshmarket.api.core.cognito.CognitoJwtProcessor
import com.fieldfreshmarket.api.core.cognito.client.CognitoProperties
import com.fieldfreshmarket.api.core.cognito.client.CognitoUserClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient

@Configuration
class CognitoConfig {

   @Bean
   fun cognitoClient(
      properties: CognitoProperties,
      cognitoJwtProcessor: CognitoJwtProcessor
   ): CognitoUserClient =
      CognitoUserClient(
         identityProviderClient = CognitoIdentityProviderClient.create(),
         cognitoProperties = properties
      )
}