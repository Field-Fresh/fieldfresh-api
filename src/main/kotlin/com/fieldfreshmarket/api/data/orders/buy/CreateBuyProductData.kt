package com.fieldfreshmarket.api.data.orders.buy

import java.time.LocalDate

data class CreateBuyProductData(
    val earliestDate: LocalDate,
    val latestDate: LocalDate,
    val maxPriceCents: Long,
    val volume: Double,
    val productId: String,
)