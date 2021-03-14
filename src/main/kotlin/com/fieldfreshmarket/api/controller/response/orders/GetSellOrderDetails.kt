package com.fieldfreshmarket.api.controller.response.orders

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.view.order.MatchView
import com.fieldfreshmarket.api.view.order.OrderView
import com.fieldfreshmarket.api.view.order.buy.BuyProductView
import com.fieldfreshmarket.api.view.order.sell.SellProductView

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class GetSellOrderDetails (
   sellProduct: SellProduct
) {
    val order: SellProductView = SellProductView(sellProduct)
    val matches: List<MatchView> = sellProduct.matches.map { MatchView(it) }
}