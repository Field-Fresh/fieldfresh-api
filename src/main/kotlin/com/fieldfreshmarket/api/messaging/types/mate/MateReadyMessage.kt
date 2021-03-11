package com.fieldfreshmarket.api.messaging.types.mate

import com.fieldfreshmarket.api.core.messaging.SNSMessage
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import java.time.Instant

data class MateReadyMessage(
    val readyTimeUTCSeconds: Long,
    val round: Int
) : SNSMessage