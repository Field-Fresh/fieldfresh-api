package com.fieldfreshmarket.api.data.orders.matches

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Match
import org.springframework.jdbc.BadSqlGrammarException

data class GetMatchDetailsData(
    val matchId: String,
    val grant: AccessGrant,
    val proxyId: String
)
