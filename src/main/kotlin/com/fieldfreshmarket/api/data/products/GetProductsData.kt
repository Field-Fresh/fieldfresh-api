package com.fieldfreshmarket.api.data.products

import com.fieldfreshmarket.api.model.ClassType

data class GetProductsData(
    val category: String?,
    val family: String?,
    val type: String?,
    val classType: ClassType?,
    val resultLimit: Int?
)
