package com.fieldfreshmarket.api.messaging.types

import com.fieldfreshmarket.api.core.messaging.SNSMessage
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import java.time.Instant

data class BuyOrderMessage(
    val id: String,
    val status: OrderStatus,
    val earliestDate: Instant?,
    val latestDate: Instant?,
    val maxPriceCents: Long,
    val volume: Int,
    val productId: String,
    val proxyId: String,
    val lat: Double,
    val long: Double,
) : SNSMessage

fun BuyProduct.toMessage() = BuyOrderMessage(
    id = id!!,
    status = status,
    earliestDate = earliestDate,
    latestDate = latestDate,
    maxPriceCents = maxPriceCents,
    volume = volume,
    productId = product.id!!,
    proxyId = buyOrder.proxy.id!!,
    lat = buyOrder.proxy.latitude,
    long = buyOrder.proxy.longitude
)