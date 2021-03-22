package com.fieldfreshmarket.api.data.orders.matches

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Match

data class MatchDetailsData(
    val match: Match,
    val matchedProxy: Proxy
)
