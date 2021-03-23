package com.fieldfreshmarket.api.messaging

import com.fieldfreshmarket.api.config.FieldFreshProperties
import com.fieldfreshmarket.api.core.messaging.AbstractSnsPublisher
import com.fieldfreshmarket.api.core.messaging.SNSMessage
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sns.SnsAsyncClient
import software.amazon.awssdk.services.sns.SnsClient
import javax.annotation.PreDestroy
import kotlin.coroutines.CoroutineContext

@Component
class MatePublisher(
    snsClient: SnsClient,
    val properties: FieldFreshProperties
) : AbstractSnsPublisher(
    snsClient
) {

    @PreDestroy
    fun destroy() {
        stop()
    }

    fun sendBatchMessage(
        type: String,
        messages: List<SNSMessage>,
        totalCount: Long,
        batchId: String? = null
    ) {
        sendBatchMessage(properties.mateTopicArn, type, messages, totalCount, batchId)
    }
}