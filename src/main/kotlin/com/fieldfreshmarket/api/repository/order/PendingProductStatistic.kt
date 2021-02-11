package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.Product

interface PendingProductStatistic {
    val id: String
    val totalVolume: Double
}