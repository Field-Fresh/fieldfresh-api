package com.fieldfreshmarket.api.view.order

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import java.time.Instant

@JsonSerialize
open class OrderDetailView (
   order: Order
) {
   val side: OrderSide = order.side
   val isActive: Boolean = order.isActive
   val roundUpdatedTimestamp: Instant = order.roundUpdatedTimestamp
   val round: Int = order.round
   val proxyId: String = order.proxy.id!!
}