package com.fieldfreshmarket.api.data.products

import com.fieldfreshmarket.api.model.ClassType

data class ProductFilterOptions(
    var categories: List<String> = listOf(),
    var families: List<String> = listOf(),
    var types: List<String> = listOf(),
    var classTypes: List<ClassType> = listOf()
)
