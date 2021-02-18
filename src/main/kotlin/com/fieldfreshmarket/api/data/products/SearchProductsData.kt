package com.fieldfreshmarket.api.data.products

data class SearchProductsData(
    val searchText: String,
    val limit: Int?
)
