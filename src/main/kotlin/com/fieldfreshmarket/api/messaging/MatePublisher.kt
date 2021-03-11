package com.fieldfreshmarket.api.messaging

import com.fieldfreshmarket.api.config.FieldFreshProperties
import com.fieldfreshmarket.api.core.messaging.AbstractSnsPublisher
import com.fieldfreshmarket.api.core.messaging.SNSMessage
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.sns.SnsAsyncClient
import javax.annotation.PreDestroy
import kotlin.coroutines.CoroutineContext

@Component
class MatePublisher(
    snsClient: SnsAsyncClient,
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
        batchId: String? = null,
        batchSize: Int = 25,
    ) {
        sendBatchMessage(properties.mateTopicArn, type, messages, totalCount, batchSize, batchId)
    }
}