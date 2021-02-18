package com.fieldfreshmarket.api.controller.request.products

import com.fieldfreshmarket.api.data.products.GetProductFilterOptionsData
import com.fieldfreshmarket.api.data.products.GetProductsData
import com.fieldfreshmarket.api.model.ClassType


class GetProductFilterOptionsRequest(
    val category: String?,
    val family: String?,
    val type: String?,
    val classType: ClassType?
) {
    fun toData() = GetProductFilterOptionsData(
        category, family, type, classType
    )
}
