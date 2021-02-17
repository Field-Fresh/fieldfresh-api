package com.fieldfreshmarket.api.controller.request.orders.buy

import com.fieldfreshmarket.api.data.orders.buy.CreateBuyProductData
import com.fieldfreshmarket.api.data.orders.sell.CreateSellProductData
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.lang.NonNull
import java.time.LocalDate

class CreateBuyProductRequest(
    @get:NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val earliestDate: LocalDate,
    @get:NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val latestDate: LocalDate,
    @get:NonNull
    val maxPriceCents: Long,
    @get:NonNull
    val volume: Double,
    @get:NonNull
    val productId: String,
    @get:NonNull
    val serviceRadius: Double
) {
    fun toData(): CreateBuyProductData =
        CreateBuyProductData(
            earliestDate = earliestDate,
            latestDate = latestDate,
            maxPriceCents = maxPriceCents,
            volume = volume,
            productId = productId,
            serviceRadius = serviceRadius
        )
}
