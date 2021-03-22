package com.fieldfreshmarket.api.data.orders.matches

data class CreateMatchData(
    val sellProductId: String,
    val buyProductId: String,
    val quantity: Int,
    val unitPriceCents: Long,
    val round: Long
)
