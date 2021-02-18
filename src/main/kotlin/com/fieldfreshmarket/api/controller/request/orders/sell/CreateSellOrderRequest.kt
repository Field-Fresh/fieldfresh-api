package com.fieldfreshmarket.api.controller.request.orders.sell

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.orders.sell.CreateSellOrderData
import org.springframework.lang.NonNull

class CreateSellOrderRequest(

    @get:NonNull
    val proxyId: String,
    @get:NonNull
    val sellProducts: List<CreateSellProductRequest>,
) {
    fun toData(grant: AccessGrant): CreateSellOrderData =
        CreateSellOrderData(
            grant = grant,
            proxyId = proxyId,
            sellProducts = sellProducts.map { it.toData() }
        )
}
