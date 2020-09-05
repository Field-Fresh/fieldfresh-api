package com.fieldfreshmarket.api.config

import com.fieldfreshmarket.api.core.cognito.CognitoJwtProcessor
import com.fieldfreshmarket.api.core.cognito.client.CognitoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtProcessor {

    @Bean
    fun cognitoJwtProcessor(properties: CognitoProperties): CognitoJwtProcessor =
       CognitoJwtProcessor(
          properties = properties
       )
}