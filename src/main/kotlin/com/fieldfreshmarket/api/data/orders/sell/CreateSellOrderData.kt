package com.fieldfreshmarket.api.data.orders.sell

import com.fieldfreshmarket.api.data.AccessGrant

data class CreateSellOrderData (
    val grant: AccessGrant,
    val proxyId: String,
    val sellProducts: List<CreateSellProductData>,
)