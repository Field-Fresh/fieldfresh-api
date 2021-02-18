package com.fieldfreshmarket.api.controller.request.orders.buy

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.orders.buy.CreateBuyOrderData
import com.fieldfreshmarket.api.data.orders.sell.CreateSellOrderData
import org.springframework.lang.NonNull

class CreateBuyOrderRequest(

    @get:NonNull
    val proxyId: String,
    @get:NonNull
    val buyProducts: List<CreateBuyProductRequest>,
) {
    fun toData(grant: AccessGrant): CreateBuyOrderData =
        CreateBuyOrderData(
            grant = grant,
            proxyId = proxyId,
            buyProducts = buyProducts.map { it.toData() }
        )
}
