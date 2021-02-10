package com.fieldfreshmarket.api.controller.request.orders

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.orders.GetOrdersRequestData
import com.fieldfreshmarket.api.data.proxy.CreateProxyData
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import org.springframework.lang.NonNull

class GetOrdersRequest(
    val side: OrderSide?,
    @get:NonNull
    val status: OrderStatus?
) {
    fun toData() = GetOrdersRequestData(
        side, status!!, null
    )
}
