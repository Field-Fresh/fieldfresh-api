package com.fieldfreshmarket.api.data.orders.sell

import com.fieldfreshmarket.api.data.orders.CreateProductOrderData
import java.time.LocalDate

data class CreateSellProductData (
    override val earliestDate: LocalDate,
    override val latestDate: LocalDate,
    override val productId: String,
    val minPriceCents: Long,
    val volume: Double,
    val serviceRadius: Double
): CreateProductOrderData