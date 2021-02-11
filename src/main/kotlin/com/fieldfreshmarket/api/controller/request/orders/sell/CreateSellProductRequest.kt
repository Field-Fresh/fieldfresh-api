package com.fieldfreshmarket.api.controller.request.orders.sell

import com.fieldfreshmarket.api.data.orders.sell.CreateSellProductData
import com.fieldfreshmarket.api.data.proxy.CreateProxyData
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.lang.NonNull
import java.time.LocalDate

class CreateSellProductRequest(
    @get:NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val earliestDate: LocalDate,
    @get:NonNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val latestDate: LocalDate,
    @get:NonNull
    val minPriceCents: Long,
    @get:NonNull
    val volume: Double,
    @get:NonNull
    val productId: String
) {
    fun toData(): CreateSellProductData =
        CreateSellProductData(
            earliestDate = earliestDate,
            latestDate = latestDate,
            minPriceCents = minPriceCents,
            volume = volume,
            productId = productId
        )
}
