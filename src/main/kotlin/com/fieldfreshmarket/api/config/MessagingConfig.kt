package com.fieldfreshmarket.api.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fieldfreshmarket.api.core.messaging.MessageReceiver
import com.fieldfreshmarket.api.messaging.MatePublisher
import com.fieldfreshmarket.api.messaging.SQSMessageRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.sns.SnsAsyncClient

@Configuration
class MessagingConfig {

    @Bean
    fun sqsMessageReceiver(router: SQSMessageRouter): MessageReceiver =
        object : MessageReceiver(
            router = router,
            objectMapper = jacksonObjectMapper()
        ) {}

    @Bean
    fun mateMessagingConfig(
        snsAsyncClient: SnsAsyncClient,
        fieldFreshProperties: FieldFreshProperties
    ): MatePublisher = MatePublisher(
        snsAsyncClient, fieldFreshProperties
    )
}