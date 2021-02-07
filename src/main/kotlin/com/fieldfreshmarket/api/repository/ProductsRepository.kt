package com.fieldfreshmarket.api.repository

import com.fieldfreshmarket.api.model.Product
import java.util.*

interface ProductsRepository : BaseRepository<Product> {
    fun findById(id: String): Product?
}