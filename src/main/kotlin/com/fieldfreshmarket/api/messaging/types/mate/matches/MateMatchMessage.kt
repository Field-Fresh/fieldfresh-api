package com.fieldfreshmarket.api.messaging.types.mate.matches

import com.fieldfreshmarket.api.core.messaging.SNSMessage
import com.fieldfreshmarket.api.data.orders.matches.CreateMatchData
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import java.time.Instant

data class MateMatchMessage(
    val matchId: Long,
    val round: Long,
    val buyOrder: String,
    val sellOrder: String,
    val volume: Int,
    val priceCents: Long
) : SNSMessage

fun MateMatchMessage.toData() =
    CreateMatchData(
        sellProductId = sellOrder,
        buyProductId = buyOrder,
        round = round,
        quantity = volume,
        unitPriceCents = priceCents
    )