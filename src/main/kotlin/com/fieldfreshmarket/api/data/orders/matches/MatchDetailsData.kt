package com.fieldfreshmarket.api.data.orders.matches

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.OrderSide

data class MatchDetailsData(
    val match: Match,
    val matchedProxy: Proxy,
    val side: OrderSide
)
