package com.fieldfreshmarket.api.view.order.buy

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.view.ProductView
import java.time.Instant

@JsonSerialize
class BuyProductView (
   buyProduct: BuyProduct
) {
   val earliestDate: Instant? = buyProduct.earliestDate
   val latestDate: Instant? = buyProduct.latestDate
   val maxPriceCents: Long = buyProduct.maxPriceCents
   val volume: Double = buyProduct.volume
   val serviceRadius: Double = buyProduct.serviceRadius
   val product: ProductView = ProductView(buyProduct.product)
   val buyOrderId: String = buyProduct.buyOrder.id!!
}