package com.fieldfreshmarket.api.controller.request.products

import com.fieldfreshmarket.api.data.products.GetProductsData
import com.fieldfreshmarket.api.model.ClassType


class GetProductsRequest(
    val category: String?,
    val family: String?,
    val type: String?,
    val classType: ClassType?,
    val limit: Int?
) {
    fun toData(
        category: String? = this.category,
        family: String? = this.family,
        type: String? = this.type,
        classType: ClassType? = this.classType
    ) = GetProductsData(
        category, family, type, classType, limit
    )
}
