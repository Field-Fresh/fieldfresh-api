package com.fieldfreshmarket.api.view

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.data.products.PendingProductData
import com.fieldfreshmarket.api.model.ClassType
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.User

@JsonSerialize
class PendingProductView(
    pendingProduct: PendingProductData
) {
    val product: ProductView = ProductView(pendingProduct.product)
    val totalVolume: Double = pendingProduct.volume
    val unit: String = pendingProduct.unit
}