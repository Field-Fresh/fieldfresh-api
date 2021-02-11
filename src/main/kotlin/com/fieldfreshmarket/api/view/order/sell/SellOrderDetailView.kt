package com.fieldfreshmarket.api.view.order.sell

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.view.order.OrderDetailView
import java.time.Instant

@JsonSerialize
class SellOrderDetailView (
   order: SellOrder
): OrderDetailView(order) {
   val sellProducts: List<SellProductView> = order.sellProducts.map { SellProductView(it) }
}