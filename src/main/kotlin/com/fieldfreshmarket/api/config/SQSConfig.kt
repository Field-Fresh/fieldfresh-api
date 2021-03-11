package com.fieldfreshmarket.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider
import software.amazon.awssdk.services.sqs.SqsAsyncClient

@Configuration
class SQSConfig {

    @Bean
    fun sqsClient(properties: FieldFreshProperties): SqsAsyncClient =
        SqsAsyncClient.builder()
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .build()
}