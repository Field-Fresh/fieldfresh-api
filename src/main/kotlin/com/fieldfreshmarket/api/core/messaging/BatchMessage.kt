package com.fieldfreshmarket.api.core.messaging

data class BatchMessage(
    val batchId: String,
    val totalMessageCount: Long,
    val message: SNSMessage
) : SNSMessage