package com.fieldfreshmarket.api.data.orders.buy

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.orders.CreateOrderData

data class CreateBuyOrderData (
    override val grant: AccessGrant,
    override val proxyId: String,
    override val orderProducts: List<CreateBuyProductData>,
) : CreateOrderData<CreateBuyProductData>