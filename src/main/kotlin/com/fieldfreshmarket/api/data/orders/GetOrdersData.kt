package com.fieldfreshmarket.api.data.orders

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus

data class GetOrdersData (
   val side: OrderSide?,
   val status: OrderStatus,
   val grant: AccessGrant
)