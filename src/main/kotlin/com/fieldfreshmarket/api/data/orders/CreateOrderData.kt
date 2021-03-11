package com.fieldfreshmarket.api.data.orders

import com.fieldfreshmarket.api.data.AccessGrant

interface CreateOrderData <I : CreateProductOrderData> {
    val grant: AccessGrant
    val proxyId: String
    val orderProducts: List<I>
}