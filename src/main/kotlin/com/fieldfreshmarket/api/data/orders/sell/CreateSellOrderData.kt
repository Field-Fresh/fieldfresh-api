package com.fieldfreshmarket.api.data.orders.sell

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.orders.CreateOrderData

data class CreateSellOrderData (
    override val grant: AccessGrant,
    override val proxyId: String,
    override val orderProducts: List<CreateSellProductData>,
) : CreateOrderData<CreateSellProductData>