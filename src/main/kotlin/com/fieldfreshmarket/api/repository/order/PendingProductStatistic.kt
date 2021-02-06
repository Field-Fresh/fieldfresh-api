package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.Product

interface PendingProductStatistic {
    val product: Product
    val totalVolume: Double
}