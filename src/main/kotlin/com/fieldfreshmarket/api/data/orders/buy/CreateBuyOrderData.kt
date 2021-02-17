package com.fieldfreshmarket.api.data.orders.buy

import com.fieldfreshmarket.api.data.AccessGrant

data class CreateBuyOrderData (
    val grant: AccessGrant,
    val proxyId: String,
    val buyProducts: List<CreateBuyProductData>,
)