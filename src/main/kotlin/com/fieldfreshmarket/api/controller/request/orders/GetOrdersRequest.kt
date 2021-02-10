package com.fieldfreshmarket.api.controller.request.orders

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.orders.GetOrdersData
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import org.springframework.lang.NonNull

class GetOrdersRequest(
    val side: OrderSide?,
    @get:NonNull
    val status: OrderStatus?
) {
    fun toData(grant: AccessGrant) = GetOrdersData(
        side, status!!, grant
    )
}
