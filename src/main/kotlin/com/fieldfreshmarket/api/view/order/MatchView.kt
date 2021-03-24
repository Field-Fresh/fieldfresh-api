package com.fieldfreshmarket.api.view.order

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.view.ProductView
import com.fieldfreshmarket.api.view.order.buy.BuyProductView
import com.fieldfreshmarket.api.view.order.sell.SellProductView

@JsonSerialize
class MatchView (
   match: Match
) {
   val id: String  = match.id!!
   val quantity: Int = match.quantity
   val unitPriceCents: Long = match.unitPriceCents
   val sellProduct: SellProductView = SellProductView(match.sellProduct)
   val buyProduct: BuyProductView = BuyProductView(match.buyProduct)
   val round: Long = match.round
   val product: ProductView = ProductView(match.sellProduct.product)
   val originalSellQuantity: Int = match.sellProduct.originalVolume
   val originalBuyQuantity: Int = match.buyProduct.originalVolume
}