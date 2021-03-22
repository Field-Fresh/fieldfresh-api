package com.fieldfreshmarket.api.messaging.types

import com.fieldfreshmarket.api.core.messaging.SNSMessage
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import java.time.Instant

data class SellOrderMessage(
    val id: String,
    val status: OrderStatus,
    val earliestDate: Instant?,
    val latestDate: Instant?,
    val minPriceCents: Long,
    val volume: Int,
    val serviceRadius: Double,
    val productId: String,
    val proxyId: String,
    val lat: Double,
    val long: Double,
) : SNSMessage

fun SellProduct.toMessage() = SellOrderMessage(
    id = id!!,
    status = status,
    earliestDate = earliestDate,
    latestDate = latestDate,
    minPriceCents = minPriceCents,
    serviceRadius = serviceRadius,
    volume = volume,
    productId = product.id!!,
    proxyId = sellOrder.proxy.id!!,
    lat = sellOrder.proxy.latitude,
    long = sellOrder.proxy.longitude
)