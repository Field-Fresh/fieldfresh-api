package com.fieldfreshmarket.api.data.products

import com.fieldfreshmarket.api.model.Product

data class PendingProductData(
    val product: Product,
    val volume: Double,
    val unit: String
)
