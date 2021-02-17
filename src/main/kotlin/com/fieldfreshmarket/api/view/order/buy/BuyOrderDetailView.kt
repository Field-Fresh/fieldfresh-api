package com.fieldfreshmarket.api.view.order.buy

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyOrder
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.view.order.OrderDetailView
import java.time.Instant

@JsonSerialize
class BuyOrderDetailView(
    order: BuyOrder
) : OrderDetailView(order) {
    val buyProducts: List<BuyProductView> = order.buyProducts.map { BuyProductView(it) }
}