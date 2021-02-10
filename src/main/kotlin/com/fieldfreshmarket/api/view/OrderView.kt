package com.fieldfreshmarket.api.view

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide

@JsonSerialize
class OrderView (
   order: Order
) {
   val id: String = order.id!!
   val side: OrderSide = order.side
}