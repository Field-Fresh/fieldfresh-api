package com.fieldfreshmarket.api.data.orders.sell

import java.time.LocalDate

data class CreateSellProductData (
    val earliestDate: LocalDate,
    val latestDate: LocalDate,
    val minPriceCents: Long,
    val volume: Double,
    val productId: String,
    val serviceRadius: Double
)