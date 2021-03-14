package com.fieldfreshmarket.api.controller.response.orders

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.view.order.MatchView
import com.fieldfreshmarket.api.view.order.OrderView
import com.fieldfreshmarket.api.view.order.buy.BuyProductView

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class GetBuyOrderDetails (
   buyProduct: BuyProduct
) {
    val order: BuyProductView = BuyProductView(buyProduct)
    val matches: List<MatchView> = buyProduct.matches.map { MatchView(it) }
}