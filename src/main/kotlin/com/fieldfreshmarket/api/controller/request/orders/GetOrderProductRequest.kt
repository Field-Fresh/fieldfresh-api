package com.fieldfreshmarket.api.controller.request.orders

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.orders.GetOrderProductsData
import com.fieldfreshmarket.api.data.orders.GetOrdersData
import com.fieldfreshmarket.api.data.orders.buy.CreateBuyProductData
import com.fieldfreshmarket.api.data.orders.sell.CreateSellProductData
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.lang.NonNull
import java.time.LocalDate

class GetOrderProductRequest(
    @get:NonNull
    val status: OrderStatus?,
    @get:NonNull
    val proxyId: String?
) {
    fun toData(grant: AccessGrant): GetOrderProductsData =
        GetOrderProductsData(
            status = status!!,
            proxyId = proxyId!!,
            grant = grant
        )
}
