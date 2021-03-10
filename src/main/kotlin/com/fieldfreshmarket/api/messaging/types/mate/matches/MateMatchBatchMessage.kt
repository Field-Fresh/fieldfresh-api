package com.fieldfreshmarket.api.messaging.types.mate.matches

import com.fieldfreshmarket.api.core.messaging.SNSMessage
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import java.time.Instant

data class MateMatchBatchMessage(
    val batchId: Long,
    val totalMatches: Int,
    val matches: List<MateMatchMessage>
) : SNSMessage