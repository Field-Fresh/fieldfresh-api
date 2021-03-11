package com.fieldfreshmarket.api.data.orders

import java.time.LocalDate

interface CreateProductOrderData {
    val earliestDate: LocalDate
    val latestDate: LocalDate
    val productId: String
}