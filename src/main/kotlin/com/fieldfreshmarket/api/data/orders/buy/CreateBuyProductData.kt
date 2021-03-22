package com.fieldfreshmarket.api.data.orders.buy

import com.fieldfreshmarket.api.data.orders.CreateProductOrderData
import java.time.LocalDate

data class CreateBuyProductData(
    override val earliestDate: LocalDate,
    override val latestDate: LocalDate,
    override val productId: String,
    val maxPriceCents: Long,
    val volume: Int,
): CreateProductOrderData