package com.fieldfreshmarket.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.services.sns.SnsAsyncClient
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Configuration
class SNSConfig {

   @Bean
   fun snsClient(properties: FieldFreshProperties): SnsAsyncClient =
       SnsAsyncClient.builder()
         .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
         .build()
}