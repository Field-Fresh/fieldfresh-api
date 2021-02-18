package com.fieldfreshmarket.api.controller.response.orders

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.view.order.OrderView

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class GetOrdersResponse (
   orders: List<Order>
) {
    val count: Int = orders.size
    val orders: List<OrderView> = orders.map { OrderView(it) }
}