package com.fieldfreshmarket.api.view.order.sell

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.view.ProductView
import com.fieldfreshmarket.api.view.order.OrderDetailView
import java.time.Instant

@JsonSerialize
class SellProductView (
   sellProduct: SellProduct
) {
   val earliestDate: Instant? = sellProduct.earliestDate
   val latestDate: Instant? = sellProduct.latestDate
   val pictureUrl: String? = sellProduct.pictureUrl
   val minPriceCents: Long = sellProduct.minPriceCents
   val volume: Double = sellProduct.volume
   val product: ProductView = ProductView(sellProduct.product)
   val sellOrderId: String = sellProduct.sellOrder.id!!
}