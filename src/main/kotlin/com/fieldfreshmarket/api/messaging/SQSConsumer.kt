package com.fieldfreshmarket.api.messaging

import com.fieldfreshmarket.api.config.FieldFreshProperties
import com.fieldfreshmarket.api.core.messaging.AbstractSqsConsumer
import com.fieldfreshmarket.api.core.messaging.MessageReceiver
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import javax.annotation.PreDestroy

@Lazy
@Component
class SQSConsumer (
  sqsClient: SqsAsyncClient,
  properties: FieldFreshProperties,
  messageReceiver: MessageReceiver
): AbstractSqsConsumer(
        sqsClient,
        properties.sqsQueueUrl,
        properties.sqsWorkersCount,
        messageReceiver
) {
    @PreDestroy
    fun destroy() {
        stop()
    }
}