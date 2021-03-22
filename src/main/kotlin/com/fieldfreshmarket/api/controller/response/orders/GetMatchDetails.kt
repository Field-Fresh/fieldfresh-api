package com.fieldfreshmarket.api.controller.response.orders

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.data.orders.matches.GetMatchDetailsData
import com.fieldfreshmarket.api.data.orders.matches.MatchDetailsData
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.view.ProxyView
import com.fieldfreshmarket.api.view.order.MatchView
import com.fieldfreshmarket.api.view.order.OrderView
import com.fieldfreshmarket.api.view.order.buy.BuyProductView
import com.fieldfreshmarket.api.view.order.sell.SellProductView

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class GetMatchDetails(
    data: MatchDetailsData,
) {
    val match: MatchView = MatchView(data.match)
    val sellProduct: SellProductView = SellProductView(data.match.sellProduct)
    val buyProduct: BuyProductView = BuyProductView(data.match.buyProduct)
    val matchedProxy: ProxyView = ProxyView(data.matchedProxy)
}