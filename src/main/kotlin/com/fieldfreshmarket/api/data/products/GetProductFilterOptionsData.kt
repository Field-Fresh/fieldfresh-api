package com.fieldfreshmarket.api.data.products

import com.fieldfreshmarket.api.model.ClassType

data class GetProductFilterOptionsData(
    val category: String? = null,
    val family: String? = null,
    val type: String? = null,
    val classType: ClassType? = null
)
